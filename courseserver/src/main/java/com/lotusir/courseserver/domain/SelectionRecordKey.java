package com.lotusir.courseserver.domain;

import java.io.Serializable;

public class SelectionRecordKey implements Serializable {
    private Integer studentId;

    private Integer courseId;

    private static final long serialVersionUID = 1L;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}