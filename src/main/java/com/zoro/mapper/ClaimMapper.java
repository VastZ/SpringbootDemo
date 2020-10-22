package com.zoro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zoro.dto.OldLost;

public interface ClaimMapper {
    List<OldLost> getOldLostData();

    int updateOldLostData(@Param("id") int id);
}
