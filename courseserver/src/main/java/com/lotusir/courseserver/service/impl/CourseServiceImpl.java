package com.lotusir.courseserver.service.impl;

import com.lotusir.courseserver.domain.AvrGrade;
import com.lotusir.courseserver.domain.Course;
import com.lotusir.courseserver.domain.SelectionRecord;
import com.lotusir.courseserver.domain.SelectionRecordKey;
import com.lotusir.courseserver.domain.response.ResAvrGrade;
import com.lotusir.courseserver.mapper.CourseMapper;
import com.lotusir.courseserver.mapper.SelectionRecordMapper;
import com.lotusir.courseserver.service.CourseService;
import com.lotusir.courseserver.utils.CourseEnum;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SelectionRecordMapper selectionRecordMapper;


    @Override
    public List<Course> selectAllCourses() {
        return courseMapper.selectAllCourses();
    }

    @Override
    public Course selectById(Integer id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Course> selectByStudentId(Integer studentId) {
        return courseMapper.selectByStudentId(studentId);
    }

    @Override
    public List<Course> deSelectByStudentId(Integer studentId) { return courseMapper.deSelectByStudentId(studentId); }

    @Override
    @Transactional
    public CourseEnum deleteCourse(Integer courseId) {
        Integer errCode = 0;
        Integer num = 0;
        try {
            num = courseMapper.deleteByPrimaryKey(courseId);
        }
        catch (DataIntegrityViolationException e) {
            errCode = ((SQLServerException)e.getCause()).getErrorCode();
        }
        if (errCode != 0) {
            return CourseEnum.DELETE_FAIL_REFERENCE_ERR;
        }
        else {
            if (num == 0) {
                return CourseEnum.DELETE_FAIL_INVALID_ID;
            } else return CourseEnum.DELETE_SUCCESS;
        }
    }

    @Override
    public int updateGrade(Integer studentId,Integer courseId, Integer grade) {
        SelectionRecord selectionRecord = new SelectionRecord();
        selectionRecord.setGrade(grade);
        selectionRecord.setStudentId(studentId);
        selectionRecord.setCourseId(courseId);
        return selectionRecordMapper.updateByPrimaryKeySelective(selectionRecord);

    }

    @Override
    public CourseEnum insertCourse(Course course) {
        if (courseMapper.selectByPrimaryKey(course.getCourseId()) != null) {
            return CourseEnum.CREATE_FAIL_ID_EXIST;
        }
        else {
            courseMapper.insert(course);
            return CourseEnum.CREATE_SUCCESS;
        }
    }

    @Override
    public ResAvrGrade selectAllAvrGrade() {
        ResAvrGrade resList = new ResAvrGrade();
        List<AvrGrade> avrGradeList = courseMapper.selectAllAvrGrade();
        for (AvrGrade avrGrade: avrGradeList) {
            resList.addAvrGrade(avrGrade);
        }
        return resList;
    }


}
