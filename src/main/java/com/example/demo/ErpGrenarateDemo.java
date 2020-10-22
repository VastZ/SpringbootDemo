package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ErpGrenarateDemo {

	public static void main(String[] args) {
		readFileByLines("C:/Users/AA/Desktop/erp.txt");
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		try (FileWriter fw = new FileWriter("C:/Users/AA/Desktop/erp_待导入.sql", true);
		        BufferedReader reader = new BufferedReader(new FileReader(file));){
			
			String tempString = null;
			int line = 1;
			// 一次读一行，读入null时文件结束

			while ((tempString = reader.readLine()) != null) {
				// 把当前行号显示出来
				System.out.println("line " + line + ": " + tempString);
				String[] res = tempString.split(",");
				fw.write(makeSql(res[0], res[1], res[2]));
				line++;
			}
			fw.flush();// flush()方法刷新缓冲区
			fw.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private static String makeSql(String ordermainuuid, String uuid, String productType) {
		// -- uuid ordermainuuid createdate lifeconfirmflag retransmissions
		// isEmail
		String sql = "INSERT INTO `erp_send_list_syn`"
				+ "(`uuid`, `oper`, `opetime`, `ordermainuuid`, `createdate`,`lifeconfirmflag`, `producttype`, `retransmissions`, `isEmail`) "
				+ "VALUES ( '" + uuid.trim() + "', 'zwh_1', now(), '" + ordermainuuid.trim() + "', now(), 'N', '" + productType.trim()
				+ "', 0, 0); \n";
		return sql;
	}

}
