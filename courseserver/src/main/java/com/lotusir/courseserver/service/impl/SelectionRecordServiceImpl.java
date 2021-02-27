package com.lotusir.courseserver.service.impl;

import com.lotusir.courseserver.domain.SelectionRecord;
import com.lotusir.courseserver.mapper.SelectionRecordMapper;
import com.lotusir.courseserver.service.SelectionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectionRecordServiceImpl implements SelectionRecordService {

    @Autowired
    SelectionRecordMapper selectionRecordMapper;

    @Override
    public List<SelectionRecord> selectByStudentId(Integer studentId) {
        return selectionRecordMapper.selectByStudentId(studentId);
    }

    @Override
    public int deleteByStudentId(Integer studentId) {
        return selectionRecordMapper.deleteByStudentId(studentId);
    }


}
