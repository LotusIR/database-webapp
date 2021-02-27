package com.lotusir.courseserver.service;

import com.lotusir.courseserver.domain.CourseHistory;
import com.lotusir.courseserver.domain.Student;
import com.lotusir.courseserver.domain.StudentGrade;
import com.lotusir.courseserver.domain.request.ReqStudentInfo;
import com.lotusir.courseserver.utils.CourseEnum;
import com.lotusir.courseserver.utils.StudentEnum;

import java.util.List;

public interface StudentService {

    Student checkPassword(String username,String password);

    CourseEnum selectCourse(Integer studentId,Integer courseId);

    CourseEnum dropCourse(Integer studentId,Integer courseId);

    Student getStudentInfo(Integer studentId);

    List<CourseHistory> getCourseHistory(Integer studentId);

    List<StudentGrade> getStudentGrade(Integer CourseId);

    List<Student> getAllStudents();

    StudentEnum deleteStudent(Integer studentId);

    StudentEnum createStudent(ReqStudentInfo studentInfo);
}
