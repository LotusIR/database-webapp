package com.lotusir.courseserver.mapper;

import com.lotusir.courseserver.domain.Student;
import com.lotusir.courseserver.domain.StudentGrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    int deleteByPrimaryKey(Integer studentId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer studentId);

    Student selectByUsername(String username);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    List<StudentGrade> selectByCourseId(Integer courseId);

    List<Student> selectAllStudents();
}