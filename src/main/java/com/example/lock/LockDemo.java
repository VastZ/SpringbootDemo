package com.example.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 有无lock的区别
 *
 * @author zhang.wenhan
 * @description LockDemo
 * @date 2019/12/10 14:03
 */
public class LockDemo {

    /**
     * 无锁时，多线程并发执行不能保证其数据准确
     */
    public static void testNoLock() throws InterruptedException {
        final Product product = new Product();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1000; i++) {
            fixedThreadPool.execute(() -> {
                product.increase();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        fixedThreadPool.shutdown();
        System.out.println("testNoLock 无锁时结果为:" + product.getNumber());
    }

    public static void testSync() throws InterruptedException {
        final Product product = new Product();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1000; i++) {
            fixedThreadPool.execute(() -> {
                product.increaseSync();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        fixedThreadPool.shutdown();
        System.out.println("testSync 使用了synchronized后结果为:" + product.getNumber());
    }

    public static void testWithLock() throws InterruptedException {
        final Product product = new Product();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1000; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    product.increaseLock();
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        fixedThreadPool.shutdown();
        System.out.println("testWithLock 使用了lock后结果为:" + product.getNumber());
    }

    public static void main(String[] args) throws InterruptedException {
        testNoLock();
        testSync();
        testWithLock();
    }

}
