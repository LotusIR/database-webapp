package com.lotusir.courseserver.domain;

public class AvrGrade {
    private String courseName;
    private Integer grade;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "AvrGrade{" +
                "courseName='" + courseName + '\'' +
                ", grade=" + grade +
                '}';
    }
}
