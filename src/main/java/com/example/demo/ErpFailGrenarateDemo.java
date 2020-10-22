package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class ErpFailGrenarateDemo {
	public static void main(String[] args) {
		readFileByLines("C:/Users/AA/Desktop/erp_fail.txt");
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			FileWriter fw = new FileWriter("C:/Users/AA/Desktop/erp_fail_orderNo.txt", true);
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读一行，读入null时文件结束

			while ((tempString = reader.readLine()) != null) {
				// 把当前行号显示出来
				System.out.println("line " + line + ": " + tempString);
				if(StringUtils.isNotBlank(tempString)){
				    if(tempString.startsWith(",")){
				        fw.write(tempString.substring(5, 25) + "\n");
				    } else{
				        fw.write(tempString.substring(4, 24) + "\n");
				    }
				   
				}
				
				line++;
			}
			fw.flush();// flush()方法刷新缓冲区
			fw.close();
			reader.close();
		} catch (IOException e) {
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
	}
}
