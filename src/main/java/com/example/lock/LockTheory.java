package com.example.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock 原理
 * @author zhang.wenhan
 * @description LockTheory
 * @date 2019/12/19 16:49
 */
public class LockTheory {

    // lock 原理 https://yq.aliyun.com/articles/640868
    public static void main(String[] args) {
        // sync = new NonfairSync();
        Lock lock = new ReentrantLock();
        // CAS unsafe.compareAndSwapInt(this, stateOffset, expect, update);
        // setExclusiveOwnerThread Sets the thread that currently owns exclusive access
        lock.lock();
        // unpark Successor 唤醒继任者
        // setExclusiveOwnerThread(null)
        lock.unlock();
        // AQS AbstractQueuedSynchronizer
        // https://blog.csdn.net/lmh_19941113/article/details/86684708

    }

}
