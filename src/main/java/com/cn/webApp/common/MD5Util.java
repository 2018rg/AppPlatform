package com.cn.webApp.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Util {
	
	
	
	/**
	 * 获得输入流对应MD5
	 * @param file
	 * @return
	 */
	public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte[] b = md.digest();
            return HexConvert.bytesToHexString(b);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return null;
        }
    }
	
	 public static void main(String[] args) {
		System.out.println(md5("111111"));
	}
 

}
