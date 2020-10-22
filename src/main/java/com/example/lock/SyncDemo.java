package com.example.lock;

/**
 * @author zhang.wenhan
 * @description SyncDemo
 * @date 2019/12/20 9:53
 */
public class SyncDemo {

    public static void main(String[] args) {
//        testDiffObject();
//        testSameObject();
        testClass();
    }

    public static void testDiffObject() {
        User user = new User();
        new Thread(() -> user.syncObject(), "testDiffObject Thread 1").start();
        User user2 = new User();
        new Thread(() -> user2.syncObject(), "testDiffObject Thread 2").start();
    }

    public static void testSameObject() {
        User user = new User();
        new Thread(() -> user.syncObject(), "testSameObject Thread 1").start();
        new Thread(() -> user.syncObject(), "testSameObject Thread 2").start();
    }

    public static void testClass() {
        User user = new User();
        new Thread(() -> user.syncClass(), "testClass Thread 1").start();
        User user2 = new User();
        new Thread(() -> user2.syncClass(), "testClass Thread 2").start();
    }

}

class User {

    // 修饰实例方法，作用于当前实例加锁，进入同步代码前要获得当前实例的锁
    public synchronized void syncMethod() {
        try {
            System.out.println(Thread.currentThread().getName() + " start syncMethod");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " end syncMethod");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 修饰代码块，指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
    public void syncObject() {
        synchronized (this) {
            try {
                System.out.println(Thread.currentThread().getName() + " start syncObject");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " end syncObject");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁
    public void syncClass() {
        synchronized (User.class) {
            try {
                System.out.println(Thread.currentThread().getName() + " start syncClass");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " end syncClass");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁
    public synchronized static void syncStatic() {
        try {
            System.out.println(Thread.currentThread().getName() + " start syncStatic");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " end syncStatic");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}