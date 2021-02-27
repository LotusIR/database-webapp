package com.lotusir.courseserver.domain;

import java.io.Serializable;

public class SelectionRecord extends SelectionRecordKey implements Serializable {
    private Integer grade;

    private static final long serialVersionUID = 1L;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}