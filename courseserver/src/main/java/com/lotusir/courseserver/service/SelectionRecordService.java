package com.lotusir.courseserver.service;

import com.lotusir.courseserver.domain.SelectionRecord;
import com.lotusir.courseserver.mapper.SelectionRecordMapper;

import java.util.List;

public interface SelectionRecordService {

    SelectionRecordMapper selectionRecordMapper = null;

    List<SelectionRecord> selectByStudentId(Integer studentId);

    int deleteByStudentId(Integer studentId);
}
