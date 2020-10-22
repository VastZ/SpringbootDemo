package com.mine.test;

import junit.framework.TestCase;

public class ThreadTest extends TestCase {

    int number;

    public void test() throws InterruptedException {
        number = 0;
        Thread t = new Thread(() -> {
            assertEquals(3, number);
        });
        number = 1;
        t.start();
        number++;
        t.join();
    }
    
    public static void main(String[] args) throws InterruptedException {
        
    }

}