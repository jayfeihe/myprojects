package com.asme.collector.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static ThreadLocal<MessageDigest> threadLocal = new ThreadLocal<MessageDigest>() {

		@Override
		protected MessageDigest initialValue() {
			try {
				MessageDigest mdInst = MessageDigest.getInstance("MD5");
				return mdInst;
			} catch (NoSuchAlgorithmException e) {
			}
			return null;
		}

	};

	public final static String MD5(String s) {
		MessageDigest mdInst = threadLocal.get();
		try {
			byte[] btInput = s.getBytes();
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));

			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String str = "test";
		System.out.println(MD5(str.trim()));
	}

}
