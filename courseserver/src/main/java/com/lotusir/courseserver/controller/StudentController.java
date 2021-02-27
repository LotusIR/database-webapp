package com.lotusir.courseserver.controller;

import com.lotusir.courseserver.domain.*;
import com.lotusir.courseserver.domain.request.ReqLogin;
import com.lotusir.courseserver.domain.request.ReqSelect;
import com.lotusir.courseserver.domain.response.Response;
import com.lotusir.courseserver.service.CourseService;
import com.lotusir.courseserver.service.SelectionRecordService;
import com.lotusir.courseserver.service.StudentService;
import com.lotusir.courseserver.utils.CourseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService = null;

    @Autowired
    CourseService courseService = null;

    @Autowired
    SelectionRecordService selectionRecordService = null;

    @PostMapping("/login")
    public Response studentLogin(@RequestBody ReqLogin reqLogin, HttpServletRequest request) {
        Response res = new Response();
        Student student = studentService.checkPassword(reqLogin.getUsername(), reqLogin.getPassword());
        if (student == null) {
            res.setCode(500);
            res.setMessage("账户名或密码错误");
        }
        else {
            res.setCode(200);
            res.setObj(student);
        }
        return res;
    }

    @RequestMapping("/validCourses")
    public Response validCourses(Integer studentId,HttpServletRequest request) {
        Response res = new Response();
        List<Course> courseList = courseService.deSelectByStudentId(studentId);
        res.setCode(200);
        res.setObj(courseList);
        return res;
    }

    @RequestMapping("/hasCourses")
    public Response hasCourses(Integer studentId,HttpServletRequest request) {
        Response res = new Response();
        List<Course> courseList = courseService.selectByStudentId(studentId);
        res.setCode(200);
        res.setObj(courseList);
        return res;
    }

    @RequestMapping("/selectCourse")
    public Response selectCourse(@RequestBody ReqSelect reqSelect, HttpServletRequest request) {
        CourseEnum status = studentService.selectCourse(reqSelect.getStudentId(), reqSelect.getCourseId());
        if (status == CourseEnum.SELECT_SUCCESS) {
            return Response.success("选课成功");
        }
        else if (status == CourseEnum.SELECT_FAIL_HAS){
            return Response.error("已选该课程");
        }
        else{
            return Response.error("课程不存在");
        }
    }

    @RequestMapping("/dropCourse")
    public Response dropCourse(@RequestBody ReqSelect reqSelect, HttpServletRequest request) {
        CourseEnum status = studentService.dropCourse(reqSelect.getStudentId(), reqSelect.getCourseId());
        if (status == CourseEnum.DROP_SUCCESS) {
            return Response.success("退课成功");
        }
        else if (status == CourseEnum.DROP_FAIL_COURSE_NOT_HAS){
            return Response.error("未选该课程");
        }
        else{
            return Response.error("课程不存在");
        }
    }

    @RequestMapping("/getStudentInfo")
    public Response getStudentInfo(Integer studentId,HttpServletRequest request) {
        Student student = studentService.getStudentInfo(studentId);
        if (student.getStudentId() == null) {
            return Response.error("学号不存在");
        }
        else return Response.success(null,student);
    }

    @RequestMapping("/getCourseHistory")
    public Response getCourseHistory(Integer studentId,HttpServletRequest request) {
        if (studentService.getStudentInfo(studentId).getStudentId()==null) {
            return Response.error("学号不存在");
        }
        List<CourseHistory> courseHistories = studentService.getCourseHistory(studentId);
        return Response.success(null,courseHistories);
    }

    @RequestMapping("/getAllCourses")
    public Response getAllCourses() {
        return Response.success(null,courseService.selectAllCourses());
    }


}
