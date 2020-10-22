package com.mine.test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhang.wenhan
 * @description BooleanDemo
 * @date 2019/12/30 9:34
 */
public class BooleanDemo {

    public static void main(String[] args) {
        AtomicBoolean  flag =  new AtomicBoolean();
        flag.set(true);
        changeBoolean(flag);
        System.out.println(flag);
    }

    private static void changeBoolean(AtomicBoolean flag) {
        flag.set(false);
    }

}
