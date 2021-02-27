package com.lotusir.courseserver.controller;

import com.lotusir.courseserver.domain.Course;
import com.lotusir.courseserver.domain.response.Response;
import com.lotusir.courseserver.domain.request.ReqDelStudent;
import com.lotusir.courseserver.domain.request.ReqStudentInfo;
import com.lotusir.courseserver.domain.request.ReqUpdGrade;
import com.lotusir.courseserver.service.CourseService;
import com.lotusir.courseserver.service.SelectionRecordService;
import com.lotusir.courseserver.service.StudentService;
import com.lotusir.courseserver.utils.CourseEnum;
import com.lotusir.courseserver.utils.StudentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    StudentService studentService = null;

    @Autowired
    CourseService courseService = null;

    @Autowired
    SelectionRecordService selectionRecordService = null;

    @RequestMapping("/deleteCourse")
    public Response deleteCourse(@RequestBody Map<String,Object> params, HttpServletRequest request) {
        Integer courseId = new Integer((Integer) params.get("courseId"));
        CourseEnum status = courseService.deleteCourse(courseId);
        if (status == CourseEnum.DELETE_SUCCESS) {
            return Response.success("删除课程成功");
        }
        else if (status == CourseEnum.DELETE_FAIL_REFERENCE_ERR) {
            return Response.error("有学生已选该课程，不可删除");
        }
        else return Response.error("找不到该课程");
    }

    @RequestMapping("/getStudentList")
    public Response getStudentList(@RequestBody Map<String,Object> params, HttpServletRequest request) {
        Integer courseId = new Integer((Integer) params.get("courseId"));
        return Response.success("查询成功",studentService.getStudentGrade(courseId));
    }

    @RequestMapping("/updateGrade")
    public Response updateGrade(@RequestBody ReqUpdGrade req,HttpServletRequest request) {
        courseService.updateGrade(req.getStudentId(),req.getCourseId(),req.getGrade());
        return Response.success("成绩录入成功");
    }

    @RequestMapping("/createCourse")
    public Response createCourse(@RequestBody Course course,HttpServletRequest request) {
        CourseEnum status = courseService.insertCourse(course);
        if (status == CourseEnum.CREATE_FAIL_ID_EXIST) {
            return Response.error("课程号已存在");
        }
        else return Response.success("课程添加成功");
    }

    @RequestMapping("/getAllStudents")
    public Response getAllStudents(HttpServletRequest request) {
        return Response.success(null,studentService.getAllStudents());
    }

    @RequestMapping("/deleteStudent")
    public Response deleteStudent(@RequestBody ReqDelStudent req, HttpServletRequest request) {
        Integer studentId = req.getStudentId();
        if (!req.isForced()) {
            StudentEnum status = studentService.deleteStudent(studentId);
            if (status == StudentEnum.DELETE_SUCCESS) {
                return Response.success("删除学生成功");
            } else if (status == StudentEnum.DELETE_FAIL_REFERENCE_ERR) {
                return Response.respond(501, null, null);
            } else return Response.error("找不到该学生");
        }
        else {
            int num = selectionRecordService.deleteByStudentId(studentId);
            StudentEnum status = studentService.deleteStudent(studentId);
            if (status == StudentEnum.DELETE_SUCCESS) return Response.success("删除成功，同时删除了"+num+"个选课记录");
            else return Response.error("删除失败，未知错误");
        }
    }

    @RequestMapping("/createStudent")
    public Response createStudent(@RequestBody ReqStudentInfo req, HttpServletRequest request) {
        StudentEnum status = studentService.createStudent(req);
        if (status == StudentEnum.CREATE_FAIL_ID_EXIST) {
            return Response.error("学号已存在");
        }
        else return Response.success("添加成功");
    }

    @RequestMapping("/getAllAvrGrade")
    public Response getAllAvrGrade(HttpServletRequest request) {
        return Response.success(null,courseService.selectAllAvrGrade());
    }
}
