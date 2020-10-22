package com.zoro.controller;

import com.zoro.dto.WaitAck;
import com.zoro.mapper.AckMapper;
import com.zoro.service.WaitAckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhang.wenhan
 * @description CheckCodeController
 * @date 2019/8/15 14:52
 */
@RestController
@RequestMapping("/ack")
public class AckController {

    private final static Logger logger = LoggerFactory.getLogger(AckController.class);

    @Autowired
    WaitAckService waitAckService;

    @Autowired
    AckMapper ackMapper;

    @GetMapping("/repush")
    public int lifeAck(){
        List<WaitAck> list = ackMapper.getList(0);
        for(int i = 0; i < list.size(); i++){
            System.out.println("开始处理第" + i + "条");
            waitAckService.lifeAck(list.get(i));
        }
        return list.size();
    }
}
