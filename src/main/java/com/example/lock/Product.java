package com.example.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhang.wenhan
 * @description User
 * @date 2019/12/19 16:08
 */
public class Product {

    private int number = 0;
    private Lock lock = new ReentrantLock();

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void increase() {
        this.number++;
    }

    public synchronized void increaseSync() {
        this.number++;
    }

    public  void increaseSync2() {
        synchronized (this){
            this.number++;
        }
    }

    public void increaseLock() {
        try {
            lock.lock();
            this.number++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
