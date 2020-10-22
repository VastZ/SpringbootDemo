package com.example.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        testOne();
//        testTwo();
    }

    public static void testOne() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        new Thread(new ThreadCondition(conditionA, lock), "A1").start();
        new Thread(new ThreadCondition(conditionA, lock), "A2").start();
        Thread.sleep(2000);
        lock.lock();
        conditionA.signal();
        System.out.println("释放条件A的一个等待");
        conditionA.signal();
        System.out.println("释放条件A的一个等待");
        lock.unlock();
    }

    public static void testTwo() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        new Thread(new ThreadCondition(conditionA, lock), "A1").start();
        new Thread(new ThreadCondition(conditionA, lock), "A2").start();
        new Thread(new ThreadCondition(conditionB, lock), "B1").start();
        new Thread(new ThreadCondition(conditionB, lock), "B2").start();
        Thread.sleep(2000);
        lock.lock();
        conditionB.signal();
        System.out.println("释放条件B的一个等待");
        lock.unlock();
        Thread.sleep(2000);
        lock.lock();
        conditionA.signalAll();
        System.out.println("释放条件A的所有等待");
        lock.unlock();
        Thread.sleep(2000);
        lock.lock();
        conditionB.signal();
        System.out.println("再次释放条件B的一个等待");
        lock.unlock();
    }

}

class ThreadCondition implements Runnable{

    private Condition condition ;

    private Lock lock;

    public ThreadCondition(Condition condition, Lock lock){
        this.condition = condition;
        this.lock = lock;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("当前线程: " + threadName + "开始执行");
        try {
            lock.lock();
            System.out.println("当前线程: " + threadName + "开始等待");
            condition.await();
            System.out.println("当前线程: " + threadName + "结束等待，执行完毕");
        } catch (InterruptedException e) {
            System.out.println("当前线程: " + threadName + "异常");
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
