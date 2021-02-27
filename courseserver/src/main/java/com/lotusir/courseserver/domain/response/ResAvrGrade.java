package com.lotusir.courseserver.domain.response;

import com.lotusir.courseserver.domain.AvrGrade;

import java.util.ArrayList;
import java.util.List;

public class ResAvrGrade {
    private List<String> courseNames;
    private List<Integer> grades;

    public ResAvrGrade() {
        courseNames = new ArrayList<String>();
        grades = new ArrayList<Integer>();
    }

    public void addAvrGrade(AvrGrade avrGrade) {
        courseNames.add(avrGrade.getCourseName());
        grades.add(avrGrade.getGrade());
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(List<String> courseNames) {
        this.courseNames = courseNames;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }
}
