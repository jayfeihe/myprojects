package com.greenkoo.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
/**
 * base64工具类
 * 
 * @author QYANZE
 *
 */
public class Base64Util {
	
	public static byte[] encodeBase64(byte[] binaryData) {
		return Base64.encodeBase64(binaryData);
	}
	
	public static String encodeBase64ToString(byte[] binaryData) {
		return StringUtils.newStringUtf8(Base64.decodeBase64(binaryData));
	}
	
	public static byte[] decodeBase64(byte[] binaryData) {
		return Base64.decodeBase64(binaryData);
	}
	
	public static byte[] decodeStringToBase64(String str) {
		return Base64.decodeBase64(StringUtils.getBytesUtf8(str));
	}
}
