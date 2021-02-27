package com.lotusir.courseserver.mapper;

import com.lotusir.courseserver.domain.AvrGrade;
import com.lotusir.courseserver.domain.Course;
import com.lotusir.courseserver.domain.CourseHistory;
import com.lotusir.courseserver.domain.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    int deleteByPrimaryKey(Integer courseId);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer courseId);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectAllCourses();

    List<Course> selectByStudentId(Integer studentId);

    List<Course> deSelectByStudentId(Integer studentId);

    List<CourseHistory> selectCourseHistory(Integer studentId);

    List<AvrGrade> selectAllAvrGrade();
}