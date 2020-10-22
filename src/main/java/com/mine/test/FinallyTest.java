package com.mine.test;

/**
 * @author zhang.wenhan
 * @description FinallyTest
 * @date 2019/8/28 11:44
 */
public class FinallyTest {

    public static void main(String[] args) {
        System.out.println(hello("hello1"));
    }

    public static String hello(String word){
        if("hello".equals(word)){
            return word;
        }
        try{
            return "hello, " + word;
        } finally {
            System.out.println("我是finally");
            return "我是finally, " + word;
        }
    }

}
