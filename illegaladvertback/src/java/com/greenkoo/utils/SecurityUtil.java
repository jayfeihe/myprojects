package com.greenkoo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecurityUtil {
	
	/**
	 * 多字符串MD5加密
	 * @param strSrc 加密前原串
	 * @param key md5密钥
	 * @param charset 字符集
	 * @return
	 */
	public static String md5StrByKey(String strSrc, String key, String charset) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes(charset));

			String result = "";
			byte[] temp;
			temp = md5.digest(key.getBytes(charset));
			for (byte aTemp : temp) {
				result += Integer
						.toHexString((0x000000ff & aTemp) | 0xffffff00)
						.substring(6);
			}

			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * MD5加密
	 * @param strSrc 加密前原串
	 * @return
	 */
	public static String md5Str(String str) {
		if (str == null)
			return null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] md5Bytes = md.digest();
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**base64加密
     * @param hexStr
     * @return
     */
	public static String BASE64Encode(String s) {
		if (s == null) return null;
		BASE64Encoder encoder = new BASE64Encoder();
		try {
		String b = encoder.encode(s.getBytes());
		return b;
		} catch (Exception e) {
		return null;
		}
	}
	
    /**base64解密
     * @param hexStr
     * @return
     */
	public static String BASE64Decode(String s) {
		if (s == null) return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
		byte[] b = decoder.decodeBuffer(s);
		return new String(b);
		} catch (Exception e) {
		return null;
		}
	}
	
	/**
	 * URL解密
	 * 
	 * @param s
	 * @param enc
	 * @return
	 */
	public static String URLDecode(String s,String enc) {
		if (StringUtil.isEmpty(s)) {
			return "";
		}
		String ss = "";
		try {
			ss = URLDecoder.decode(s, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return ss;
	}
	
	/**
	 * URL加密
	 * 
	 * @param s
	 * @param enc
	 * @return
	 */
	public static String URLEncode(String s,String enc) {
		if (StringUtil.isEmpty(s)) {
			return "";
		}
		String ss = "";
		try {
			ss = URLEncoder.encode(s, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return ss;
	}
}
