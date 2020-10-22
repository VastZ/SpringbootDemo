package com.zoro.mapper;

import com.zoro.dto.WaitAck;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AckMapper {

    List<WaitAck> getList(@Param("start") int start);

    int updateById(WaitAck waitAck);


}
