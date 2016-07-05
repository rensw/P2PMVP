package com.android.mvpp2p.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * 采用md5算法对文本进行数字摘要
	 * @param text
	 * @return
	 */
	public static String encode(String text){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[]  result = md.digest(text.getBytes());
			StringBuilder sb = new StringBuilder();
			for(byte b : result){
				int number = b & 0xff ;//- 2;//加盐
				String str = Integer.toHexString(number);
				if(str.length()==1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			//cant reach
			return "";
		}
	}
	
	/**
	 * 获取某个文件的md5值
	 * @param path 文件的绝对路径
	 */
	public static String getFileMd5(String path){
		try {
			//计算课程表的md5数字摘要。
			MessageDigest digest = MessageDigest.getInstance("md5");
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))!=-1){
				digest.update(buffer, 0, len);
			}
			byte[]  result = digest.digest();
			//把result的内容 打印出来 16进制
			StringBuilder sb = new StringBuilder();
			for(byte b : result){
				int number = b & 0xff ;//- 2;//加盐
				String str = Integer.toHexString(number);
				if(str.length()==1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
