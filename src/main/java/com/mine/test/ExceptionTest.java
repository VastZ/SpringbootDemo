package com.mine.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author zhang.wenhan
 * @description ExceptionTest
 * @date 2019/12/2 9:25
 */
public class ExceptionTest {

    public static void main(String[] args) {
        try {
            File file = new File("C:/Users/AA/Desktop/111111111111.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getCause());

        }


    }


}
