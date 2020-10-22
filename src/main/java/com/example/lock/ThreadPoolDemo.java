package com.example.lock;

import java.util.concurrent.*;

/**
 * @author zhang.wenhan
 * @description ThreadPoolDemo
 * @date 2019/12/20 11:00
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService es1 = Executors.newFixedThreadPool(10);
        ExecutorService es2 = Executors.newCachedThreadPool();
        ExecutorService es3 = Executors.newSingleThreadExecutor();
        ExecutorService es4 = Executors.newWorkStealingPool();
        ExecutorService es5 = Executors.newScheduledThreadPool(4);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 10,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

    }

}
