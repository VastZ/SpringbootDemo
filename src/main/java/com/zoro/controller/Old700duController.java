package com.zoro.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoro.dto.OldLost;
import com.zoro.mapper.ClaimMapper;
import com.zoro.service.OldLostService;

/**
 * @author zhang.wenhan
 * @description Old700duController
 * @date 2019/7/18 13:23
 */
@RestController
@RequestMapping("/old")
public class Old700duController {

    @Autowired
    OldLostService oldLostService;

    @Autowired
    ClaimMapper mapper;

    public static final ThreadPoolExecutor es = new ThreadPoolExecutor(20, 20,
                                      0L,TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    @GetMapping("/send")
    public String send() {
        long now = System.currentTimeMillis();
        List<OldLost> list = mapper.getOldLostData();
        for (OldLost oldLost : list) {
            es.execute(new Thread(() -> oldLostService.sendRequest(oldLost)));
        }
        long end = System.currentTimeMillis();

//        es.getActiveCount() < 5

        return "success, 用时" + (end - now)/1000 ;
    }
   

//    @Scheduled( cron = "0 10/20 * * * ?")
    public void sendTask() {
        List<OldLost> list = mapper.getOldLostData();
        System.out.println(new Date() + " 开始执行条数： " + list.size());
        for (OldLost oldLost : list) {
            es.execute(new Thread(() -> oldLostService.sendRequest(oldLost)));
        }
    }



}
