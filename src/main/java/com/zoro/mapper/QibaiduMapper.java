package com.zoro.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QibaiduMapper {

    List<JSONObject> getApplyInfo(@Param("name") String name, @Param("applyMoney") String applyMoney);

}
