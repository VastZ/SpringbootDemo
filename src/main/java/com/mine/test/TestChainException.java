package com.mine.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author zhang.wenhan
 * @description TestChainException
 * @date 2019/12/2 9:42
 */
public class TestChainException {

    public static void readFile() throws MyException {
        try {
            InputStream is = new FileInputStream("jjj.txt");
            Scanner scanner = new Scanner(is);
            while (scanner.hasNext()){
                System.out.println(scanner.next());
            }
        } catch (Exception e) {
            throw new MyException("文件在哪里", e);
        }
    }

    public static void invokeReadFile() throws MyException {
        try{
            readFile();
        } catch (MyException e) {
            throw new MyException("没有文件", e);
        }
    }

    public static void main(String[] args) {
        try {
            invokeReadFile();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

}

class MyException extends Exception{

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }
}