package com.lotusir.courseserver.service;

import com.lotusir.courseserver.domain.AvrGrade;
import com.lotusir.courseserver.domain.Course;
import com.lotusir.courseserver.domain.SelectionRecordKey;
import com.lotusir.courseserver.domain.response.ResAvrGrade;
import com.lotusir.courseserver.mapper.CourseMapper;
import com.lotusir.courseserver.mapper.SelectionRecordMapper;
import com.lotusir.courseserver.utils.CourseEnum;

import java.util.List;

public interface CourseService {

    CourseMapper courseMapper = null;

    SelectionRecordMapper selectionRecordMapper = null;

    List<Course> selectAllCourses();

    Course selectById(Integer id);

    List<Course> selectByStudentId(Integer studentId);

    List<Course> deSelectByStudentId(Integer studentId);

    CourseEnum deleteCourse(Integer courseId);

    int updateGrade(Integer studentId,Integer courseId, Integer grade);

    CourseEnum insertCourse(Course course);

    ResAvrGrade selectAllAvrGrade();

}
