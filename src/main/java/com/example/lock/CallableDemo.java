package com.example.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo implements Callable<String>{

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CallableDemo cd = new CallableDemo();
        FutureTask<String> ft = new FutureTask<>(cd);
        for(int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+" 的循环变量i的值"+i);
            if (i == 20) {
                new Thread(ft, "有返回值的线程").start();
            }
        }
        System.out.println("子线程的返回值："+ft.get());

    }

    @Override
    public String call() throws Exception {

        return "123";
    }

}

