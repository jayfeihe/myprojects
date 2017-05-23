package com.hikootech.mqcash.util;


import com.hikootech.mqcash.service.impl.UserCreditDataServiceImpl;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DESUtils {
	private static Logger logger = LoggerFactory.getLogger(DESUtils.class);

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * DES算法，加密
	 *
	 * @param data 待加密字符串
	 * @param key  加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws InvalidAlgorithmParameterException 
	 * @throws Exception 
	 */
	public static String encode(String key,String data) {
	    if(data == null)
	        return null;
	    try{
	    	DESKeySpec dks = new DESKeySpec(key.getBytes("gb18030")); 
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//key的长度不能够小于8位字节 
			Key secretKey = keyFactory.generateSecret(dks); 
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); 
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());//向量 
			AlgorithmParameterSpec paramSpec = iv; 
			cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec); 
			byte[] bytes = cipher.doFinal(data.getBytes("gb18030")); 
			return Base64.encode(bytes);
	    }catch(Exception e){
	    	logger.error("DES加密异常");
	        e.printStackTrace();
	        return data;
	    }
	    
	    
	}

	/**
	 * DES算法，解密
	 *
	 * @param data 待解密字符串
	 * @param key  解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception 异常
	 */
	public static String decode(String key,String data) {
	    if(data == null)
	        return null;
	    try {
	    	// 创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key.getBytes("gb18030"));
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// // 将keyFactory对象转换成key对象 ,key 的长度不能够小于 8 位字节
			Key secretKey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(Base64.decode(data.getBytes())),"gb18030");
	    } catch (Exception e){
	    	logger.error("DES解密异常");
	        e.printStackTrace();
	        return data;
	    }
	}
}
