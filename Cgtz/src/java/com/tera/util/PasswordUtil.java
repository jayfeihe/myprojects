
package com.tera.util;

import java.security.MessageDigest;

/**
 * @author Tera
 *
 */
public class PasswordUtil {

	/**
	 * MD5加密
	 * @param str str
	 * @return String
	 */
	public static String md5(String str) {
		if (str == null) {
			return null;
		}
		String rtn = "";
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
			rtn = hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

}
