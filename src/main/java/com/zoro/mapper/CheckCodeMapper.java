package com.zoro.mapper;

import com.zoro.dto.CheckCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckCode record);

    int insertList(@Param("list") List<CheckCode> records);

    int insertSelective(CheckCode record);

    CheckCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckCode record);

    int updateByPrimaryKey(CheckCode record);
}