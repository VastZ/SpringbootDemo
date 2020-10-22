package com.zoro.controller;

import com.zoro.dto.OrderMain;
import com.zoro.dto.Policy;
import com.zoro.mapper.CodeCheckMapper;
import com.zoro.service.CodeCheckService;
import com.zoro.service.impl.CodeCheckServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhang.wenhan
 * @description CheckCodeController
 * @date 2019/8/15 14:52
 */
@RestController
@RequestMapping("/code")
public class CheckCodeController {

    private final static Logger logger = LoggerFactory.getLogger(CheckCodeController.class);

    public static final ThreadPoolExecutor es = new ThreadPoolExecutor(100, 100,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    @Autowired
    CodeCheckService codeCheckService;

    @Autowired
    CodeCheckMapper codeCheckMapper;

    @GetMapping("/check")
    public String check() {
        codeCheckService.excute();
        return "success";
    }

    @GetMapping("/checkThread")
    public String checkThread(@RequestParam("start") int start) {
        long startTime = System.currentTimeMillis();
        this.codeCheck(start);
        long endTime = System.currentTimeMillis();
        logger.info("处理完毕！耗时：" + (endTime - startTime) + " 毫秒");
        return "success";
    }

    private void codeCheck(int start) {
        List<Policy> list = codeCheckMapper.getList(start);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("待处理列表为空， 处理完毕！");
            return;
        }
        logger.info("开始处理，size:{}", list.size());
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (Policy policy : list) {
            es.execute(new Thread(() -> codeCheckService.checkCode(policy, countDownLatch)));
        }
        try {
            countDownLatch.await();
            logger.info("start:{} 处理完毕", start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.checkThread(start + list.size());
    }

    @GetMapping("/checkThread2018")
    public String checkThread2018(@RequestParam("start") int start) {
        long startTime = System.currentTimeMillis();
        this.codeCheck2018(start);
        long endTime = System.currentTimeMillis();
        logger.info("处理完毕！耗时：" + (endTime - startTime) + " 毫秒");
        return "success";
    }

    private void codeCheck2018(int start) {
        List<OrderMain> list = codeCheckMapper.getOrderList(start);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("待处理列表为空， 处理完毕！");
            return;
        }
        logger.info("Order Main 开始处理，start:{}, size:{}", start, list.size());
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (OrderMain orderMain : list) {
            es.execute(new Thread(() -> codeCheckService.checkCode2018(orderMain, countDownLatch)));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("countDownLatch 异常", e);
        }
        this.codeCheck2018(start + list.size());
    }

    @GetMapping("/checkMarry")
    public String checkMarry() {
        List<OrderMain> list = codeCheckMapper.getMarryList();
        for(OrderMain orderMain : list){
            codeCheckService.checkMarry2018(orderMain);
        }

        return "success";
    }
}
