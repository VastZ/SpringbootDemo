package com.qibaidu;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	//移动端加密
	private  static String EncodingAESKey = "$QiBaiDu$@SsBd@!";
	public static String encrypt(String strEncpt) {
		try {
			
			byte[] bytIn = strEncpt.getBytes("UTF8");
			SecretKeySpec skeySpec = new SecretKeySpec(EncodingAESKey.getBytes("UTF8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] bytOut = cipher.doFinal(bytIn);
			String ecrOut = new sun.misc.BASE64Encoder().encode(bytOut);
			return ecrOut;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String strDencpt) throws Exception {
//		try {
			byte[] bytIn;
			String ecrOut = "lN5YGmQQhOUJVS57qPRjwn7MfdTrM9Mjn+kP8WR4AKs=";
			bytIn = new sun.misc.BASE64Decoder().decodeBuffer(strDencpt);
			SecretKeySpec skeySpec = new SecretKeySpec(EncodingAESKey.getBytes("UTF8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] bytOut = cipher.doFinal(bytIn);
			ecrOut = new String(bytOut, "UTF8");
			return ecrOut;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return null;
//		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(AES.encrypt("{\"status\":\"加密错误\"}"));
		System.out.println(AES.decrypt("TgdzpS1PzBfPWZl7ntpE0A=="));
	}
}
