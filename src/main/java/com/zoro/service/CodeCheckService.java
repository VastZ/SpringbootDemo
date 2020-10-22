package com.zoro.service;

import com.zoro.dto.OrderMain;
import com.zoro.dto.Policy;

import java.util.concurrent.CountDownLatch;

public interface CodeCheckService {

     void checkCode(Policy policy);

     void excute();

     void checkCode(Policy policy, CountDownLatch countDownLatch);

     void checkCode2018(OrderMain orderMain);

     void checkCode2018(OrderMain orderMain, CountDownLatch countDownLatch);

     void checkMarry2018(OrderMain orderMain);

}
