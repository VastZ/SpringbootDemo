package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class ErpFaileDemo {
	public static void main(String[] args) {
		readFileByLines("C:/Users/AA/Desktop/111111111111.txt");
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			FileWriter fw = new FileWriter("C:/Users/AA/Desktop/23456.txt", true);
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读一行，读入null时文件结束
			while ((tempString = reader.readLine()) != null) {
				// 把当前行号显示出来
				System.out.println("line " + line + ": " + tempString);
				if(StringUtils.isNotBlank(tempString)){
				    String[] strs = tempString.split(",");
				    String s = "update commission_product_rel set baseCommissionRate = '" + strs[1]
				            + "' where productMainUuid = '" + strs[0] + "';\n";
				    fw.write(s);
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
