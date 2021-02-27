package com.lotusir.courseserver.service.impl;

import com.lotusir.courseserver.domain.*;
import com.lotusir.courseserver.domain.request.ReqStudentInfo;
import com.lotusir.courseserver.mapper.CourseMapper;
import com.lotusir.courseserver.mapper.SelectionRecordMapper;
import com.lotusir.courseserver.mapper.StudentMapper;
import com.lotusir.courseserver.service.StudentService;
import com.lotusir.courseserver.utils.CourseEnum;
import com.lotusir.courseserver.utils.StudentEnum;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SelectionRecordMapper selectionRecordMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Student checkPassword(String username, String password) {
        Student student = studentMapper.selectByUsername(username);
        if (student.getStudentPassword().equals(password)) {
            return student;
        }
        else return null;
    }

    @Override
    @Transactional
    public CourseEnum selectCourse(Integer studentId, Integer courseId) {
        //查询课程是否存在
        if (courseMapper.selectByPrimaryKey(courseId) == null) {
            return CourseEnum.SELECT_FAIL_COURSE_NOT_EXIST;
        }

        //查询记录是否存在
        SelectionRecordKey key = new SelectionRecordKey();
        key.setStudentId(studentId);
        key.setCourseId(courseId);
        SelectionRecord record = selectionRecordMapper.selectByPrimaryKey(key);

        if (record != null) {
            return CourseEnum.SELECT_FAIL_HAS;
        }
        else {
            SelectionRecord newRecord = new SelectionRecord();
            newRecord.setCourseId(courseId);
            newRecord.setStudentId(studentId);
            selectionRecordMapper.insertSelective(newRecord);
            return CourseEnum.SELECT_SUCCESS;
        }
    }

    @Override
    @Transactional
    public CourseEnum dropCourse(Integer studentId, Integer courseId) {
        //查询课程是否存在
        if (courseMapper.selectByPrimaryKey(courseId) == null) {
            return CourseEnum.DROP_FAIL_COURSE_NOT_EXIST;
        }

        //查询记录是否存在
        SelectionRecordKey key = new SelectionRecordKey();
        key.setStudentId(studentId);
        key.setCourseId(courseId);
        SelectionRecord record = selectionRecordMapper.selectByPrimaryKey(key);

        if (record == null) {
            return CourseEnum.DROP_FAIL_COURSE_NOT_HAS;
        }
        else {
            selectionRecordMapper.deleteByPrimaryKey(key);
            return CourseEnum.DROP_SUCCESS;
        }
    }

    @Override
    public Student getStudentInfo(Integer studentId) {
        Student student = studentMapper.selectByPrimaryKey(studentId);
        student.setStudentUsername(null);
        student.setStudentPassword(null);
        return student;
    }

    @Override
    public List<CourseHistory> getCourseHistory(Integer studentId) {
        return courseMapper.selectCourseHistory(studentId);
    }

    @Override
    public List<StudentGrade> getStudentGrade(Integer courseId) {
        return studentMapper.selectByCourseId(courseId);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.selectAllStudents();
    }

    @Override
    @Transactional
    public StudentEnum deleteStudent(Integer studentId) {
        Integer errCode = 0;
        Integer num = 0;
        try {
            num = studentMapper.deleteByPrimaryKey(studentId);
        }
        catch (DataIntegrityViolationException e) {
            errCode = ((SQLServerException)e.getCause()).getErrorCode();
        }
        if (errCode != 0) {
            return StudentEnum.DELETE_FAIL_REFERENCE_ERR;
        }
        else {
            if (num == 0) {
                return StudentEnum.DELETE_FAIL_INVALID_ID;
            } else return StudentEnum.DELETE_SUCCESS;
        }
    }

    @Override
    @Transactional
    public StudentEnum createStudent(ReqStudentInfo studentInfo) {
        Student student = new Student();
        student.setStudentId(studentInfo.getStudentId());
        student.setStudentName(studentInfo.getStudentName());
        student.setStudentSex(studentInfo.getStudentSex());
        student.setStudentAge(studentInfo.getStudentAge());
        student.setStudentDept(studentInfo.getStudentDept());
        student.setStudentUsername(studentInfo.getStudentId().toString());
        student.setStudentPassword("123456");
        if (studentMapper.insert(student) == 0) {
            return StudentEnum.CREATE_FAIL_ID_EXIST;
        }
        else return StudentEnum.CREATE_SUCCESS;
    }

}
