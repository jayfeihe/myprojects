/**
 * 2012-11-29 下午07:57:35 by asme
 */
package com.asme.collector.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author asme
 *
 * 2012-11-29 下午07:57:35
 */
public class LikeBase64 {

	// 64个字符
	private static final char[] CHARS = { 
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
		'w', 'x', 'y', 'z', '0', '1', '2', '3', 
		'4', '5', '6', '7', '8', '9', '-', '_' };

	// 64个字符的index map
	private static final int[] CHARS_MAP = new int[128];
	static { for(int i = 0; i < 64; CHARS_MAP[CHARS[i]] = i++); }

	/**
	 * 加密
	 * @param src 明文
	 * @return [盐, 密文]
	 * @throws UnsupportedEncodingException
	 */
	public static final String[] encrypt(String src) {
		Random r = new Random();
		int seqSet = 0;
		for (int i = 0; i < 16; i++) {
			int seq = r.nextInt(31);
			if ((seqSet & (1 << seq)) > 0) continue;
			seqSet |= 1 << seq;
		}

		byte[] bs = null;
		try {
			bs = src.getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();

		int status = 0, left = 0, index = 0;
		for (byte b : bs) {
			switch (status) {
			case 0:
				index = (b >>> 2) & 0x3F;
				if((seqSet & (1 << index)) > 0 || (seqSet & (1 << (63 - index))) > 0) index = 63 - index;
				sb.append(CHARS[index]);
				left |= (b & 0x03) << 4;
				status = 1;
				break;
			case 1:
				left |= (b >>> 4) & 0x0F;
				index = left;
				if((seqSet & (1 << index)) > 0 || (seqSet & (1 << (63 - index))) > 0) index = 63 - index;
				sb.append(CHARS[index]);
				left = (b & 0x0F) << 2;
				status = 2;
				break;
			case 2:
				left |= (b >>> 6) & 0x03;
				index = left;
				if((seqSet & (1 << index)) > 0 || (seqSet & (1 << (63 - index))) > 0) index = 63 - index;
				sb.append(CHARS[index]);
				index = b & 0x3F;
				if((seqSet & (1 << index)) > 0 || (seqSet & (1 << (63 - index))) > 0) index = 63 - index;
				sb.append(CHARS[index]);

				// 状态复原
				status = left = index = 0;
			}
		}
		if (status > 0) {
			index = left;
			if((seqSet & (1 << index)) > 0 || (seqSet & (1 << (63 - index))) > 0) index = 63 - index;
			sb.append(CHARS[index]);
		}

		return new String[] { String.valueOf(seqSet), sb.toString() };
	}

	/**
	 * 解密
	 * @param src 密文
	 * @param salt 盐
	 * @return 明文
	 * @throws UnsupportedEncodingException
	 */
	public static final String decrypt(String salt, String src) {
		int seqSet = Integer.parseInt(salt);
		char[] chars = src.toCharArray();
		int status = 0, dstIndex = 0, len = chars.length * 6 >>> 3;
		byte[] dst = new byte[len];
		for(char c : chars) {
			int index = CHARS_MAP[c];
			if((seqSet & (1 << index)) > 0 || (seqSet & (1 << (63 - index))) > 0) index = 63 - index;
			switch(status) {
			case 0:
				dst[dstIndex] |= index << 2;
				status = 1;
				break;
			case 1:
				dst[dstIndex++] |= (index >>> 4) & 0x03;
				if(len == dstIndex) break;
				dst[dstIndex] |= (index & 0x0F) << 4;
				status = 2;
				break;
			case 2:
				dst[dstIndex++] |= (index >>> 2) & 0x0F;
				if(len == dstIndex) break;
				dst[dstIndex] |= (index & 0x03) << 6;
				status = 3;
				break;
			case 3:
				dst[dstIndex++] |= index;
				status = 0;
			}
		}
		try {
			return new String(dst, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String[] a = encrypt("12333-http://www.sina.com");
		System.out.println(a[0] + "-" + a[1]);
		System.out.println(decrypt(a[0], a[1]));
	}
}
