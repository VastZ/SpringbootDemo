package com.mine.test;

/**
 * @author zhang.wenhan
 * @description IdTest
 * @date 2019/10/21 13:48
 */
public class IdTest {

    public static void main(String[] args) {
        String idCode = "130125199109250015";
        String sex = (Integer.valueOf(idCode.substring(16, 17)) & 1) == 0 ? "0" : "1";

        System.out.println(sex);
        System.out.println( 5 & 1);
    }
}


