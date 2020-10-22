package com.qibaidu;

import com.alibaba.fastjson.JSON;
import com.zoro.dto.OrderMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhang.wenhan
 * @description Test
 * @date 2019/11/7 15:44
 */
public class Test {


    public static void main(String[] args) {
        File file = new File("C:/Users/AA/Desktop/refundError.txt");
        BufferedReader reader = null;
        Set<String> set = new HashSet<>(256);
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读一行，读入null时文件结束
            while ((tempString = reader.readLine()) != null) {
//                System.out.println("line " + line + ": " + tempString);
                String str = AES.decrypt(tempString.substring(tempString.indexOf("请求参数:")).substring(6));
//                set.add(JSON.parseObject(str).getJSONObject("map").getString("orderUuid"));
                String orderUuid = JSON.parseObject(str).getJSONObject("map").getString("orderUuid");
                System.out.println(orderUuid);
                set.add(orderUuid);
                line++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        System.out.println(set.size());
        System.out.println(JSON.toJSONString(set));
    }



}
