package com.lotusir.courseserver.domain.request;

public class ReqDelStudent {
    private Integer studentId;
    private boolean forced;

    public Integer getStudentId() {
        return studentId;
    }

    public boolean isForced() {
        return forced;
    }
}
