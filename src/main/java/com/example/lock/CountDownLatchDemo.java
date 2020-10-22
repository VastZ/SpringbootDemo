package com.example.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhang.wenhan
 * @description CountDownLatchDemo
 * @date 2019/12/19 19:31
 */
public class CountDownLatchDemo {

    /**
     * countdownlatch 是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完毕再执行。从命
     * 名可以解读到 countdown 是倒数的意思，类似于我们倒计时的概念。
     * countdownlatch 提供了两个方法，一个是 countDown， 一个是 await，
     * countdownlatch 初始化的时候需要传入一 个整数，在这个整数倒数到 0 之前，
     * 调用了 await 方法的程序都必须要等待，然后通过 countDown 来倒数。
     * 保证所有子线程执行完毕后再执行
     */
    public static void main(String[] args) throws InterruptedException {
        test();
        testCount();
        testCount2();
    }

    public static void test() {
        AtomicInteger number = new AtomicInteger();
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                number.getAndIncrement();
            }).start();
        }
        System.out.println("test 无countDown结果为:" +  number);
    }

    public static void testCount() throws InterruptedException {
        AtomicInteger number = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(500);
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                number.getAndIncrement();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("testCount 有countDown结果为:" +  number);
    }

    public static void testCount2() throws InterruptedException {
        AtomicInteger number = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(499);
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                number.getAndIncrement();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("testCount2 有countDown结果为:" +  number);
//        Thread.sleep(1000);
//        System.out.println("testCount4 有countDown结果为:" +  number);
    }
}
