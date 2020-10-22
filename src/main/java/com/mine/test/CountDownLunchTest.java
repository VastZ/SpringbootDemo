package com.mine.test;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhang.wenhan
 * @description CountDownLunchTest
 * @date 2019/8/15 16:38
 */
public class CountDownLunchTest {

    public static final ThreadPoolExecutor es = new ThreadPoolExecutor(5, 5,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());


    public static void main(String[] args) {
        countdown();
    }

    public static void countdown(){
        CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int i =0; i < 20; i ++){
            es.execute(()->{
                System.out.println(countDownLatch.getCount());
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
            System.out.println("处理完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countdown();
    }

}
