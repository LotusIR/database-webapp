package com.lotusir.courseserver.mapper;

import com.lotusir.courseserver.domain.SelectionRecord;
import com.lotusir.courseserver.domain.SelectionRecordKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelectionRecordMapper {
    int deleteByPrimaryKey(SelectionRecordKey key);

    int insert(SelectionRecord record);

    int insertSelective(SelectionRecord record);

    SelectionRecord selectByPrimaryKey(SelectionRecordKey key);

    int updateByPrimaryKeySelective(SelectionRecord record);

    int updateByPrimaryKey(SelectionRecord record);

    List<SelectionRecord> selectByStudentId(Integer studentId);

    int deleteByStudentId(Integer studentId);
}