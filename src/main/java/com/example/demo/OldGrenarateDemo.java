package com.example.demo;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class OldGrenarateDemo {
	public static void main(String[] args) {
		readFileByLines("C:/Users/AA/Desktop/old.txt");
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			FileWriter fw = new FileWriter("C:/Users/AA/Desktop/old_insert.sql", true);
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读一行，读入null时文件结束

			while ((tempString = reader.readLine()) != null) {
				// 把当前行号显示出来
				System.out.println("line " + line + ": " + tempString);
				if(StringUtils.isNotBlank(tempString)){
					fw.write("insert into t_s_policy_sendlist (`policykey`, `createdate`, `validflag`, `finishflag`)" +
							"values('"+ tempString.trim() + "', '2019-09-18 16:50:00', 'Y', 'N');" + "\n");
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
