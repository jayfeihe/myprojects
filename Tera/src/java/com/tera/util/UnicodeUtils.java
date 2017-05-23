/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
/**
 * 将中文properties文件，转成Unicode格式
 * @author Wallace chu
 *
 */
public class UnicodeUtils {

	/**
	 * @param args args
	 * @throws IOException IOException
	 */
	public static void main(String[] args) throws IOException {
		String upoPath = System.getProperty("user.dir");
//		String upoPath = null;
//		if (args.length > 0) {
//			upoPath = args[0].trim();
//		} else {
//			throw new RuntimeException("please input the path of AIS Project");
//		}
		String sourceFileName = upoPath
				+ "\\config\\i18n\\messages_source.txt";
		String[] enFileNames = new String[] {
//				upoPath + "\\config\\i18n\\messages.properties",
				upoPath + "\\config\\i18n\\messages_en.properties" };
		String[] zhFileNames = new String[] {
				upoPath + "\\config\\i18n\\messages_zh_CN.properties" };
		UnicodeUtils helper = new UnicodeUtils(sourceFileName, enFileNames,
				zhFileNames);
		helper.init();
		helper.sort();
		helper.encode();

		System.out.println("success!");
	}

	/**
	 * 原始properties文件列表
	 */
	private File sourceFile;

	/**
	 * 英文properties文件列表
	 */
	private File[] enFiles;

	/**
	 * 中文properties文件列表
	 */
	private File[] zhFiles;

	/**
	 * map
	 */
	@SuppressWarnings("unchecked")
	private Map map = new TreeMap();

	/**
	 * 构造函数
	 * @param sourceFileName 原始文件
	 * @param enFileNames 英文properties文件列表
	 * @param zhFileNames 中文properties文件列表
	 */
	UnicodeUtils(String sourceFileName, String[] enFileNames,
			String[] zhFileNames) {
		sourceFile = new File(sourceFileName);
		enFiles = new File[enFileNames.length];
		zhFiles = new File[zhFileNames.length];
		for (int i = 0; i < enFiles.length; i++) {
			enFiles[i] = new File(enFileNames[i]);
		}
		for (int i = 0; i < zhFiles.length; i++) {
			zhFiles[i] = new File(zhFileNames[i]);
		}
	}

	/**
	 * 初始化
	 * @throws IOException IOException
	 */
	@SuppressWarnings("unchecked")
	private void init() throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(sourceFile));
		String s;
		while ((s = bf.readLine()) != null) {
			s = s.trim();
			if (s.length() == 0 || s.startsWith("#")) {
				continue;
			}
			int index = s.indexOf('=');
			String key = s.substring(0, index).trim();
			String value = s.substring(index + 1).trim();

			String[] values = (String[]) map.get(key);
			if (values == null) {
				values = new String[2];
				values[0] = value;
				map.put(key, values);
			} else {
				values[1] = value;
			}
		}
		bf.close();
	}

	/**
	 * @throws IOException IOException
	 */
	@SuppressWarnings("unchecked")
	public void sort() throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(sourceFile));
		Iterator iterator = map.keySet().iterator();
		char capital = ' ';
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			char currentCapital = key.charAt(0);
			if (currentCapital != capital) {
				capital = currentCapital;
				out.println();
			}
			String[] values = (String[]) map.get(key);
			if (values[1] == null) {
				values[1] = key;
			}
			if (isEnglish(values[0]) && !isEnglish(values[1])) {
				String temp = values[1];
				values[1] = values[0];
				values[0] = temp;
			}
			out.println(key + "=" + values[0]);
			out.println(key + "=" + values[1]);
		}
		out.println();
		out.close();
	}

	/**
	 * 判断字符串是否英文
	 * @param message message
	 * @return boolean
	 */
	private boolean isEnglish(String message) {
		if (message == null) {
			return true;
		}
		for (int i = 0; i < message.length(); i++) {
			char c = message.charAt(i);
			if (c <= 0 || c >= 256) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 转换
	 * @throws IOException IOException
	 */
	@SuppressWarnings("unchecked")
	public void encode() throws IOException {
		PrintWriter[] zhOuts = new PrintWriter[zhFiles.length], enOuts = new PrintWriter[enFiles.length];
		for (int i = 0; i < enFiles.length; i++) {
			enFiles[i].delete();
			enOuts[i] = new PrintWriter(new FileWriter(enFiles[i]));
		}
		for (int i = 0; i < zhFiles.length; i++) {
			zhFiles[i].delete();
			zhOuts[i] = new PrintWriter(new FileWriter(zhFiles[i]));
		}
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String[] values = (String[]) map.get(key);
			for (int i = 0; i < zhOuts.length; i++) {
				zhOuts[i].print(key);
				zhOuts[i].print('=');
				zhOuts[i].println(toUnicode(values[0]));
			}
			for (int i = 0; i < enOuts.length; i++) {
				enOuts[i].print(key);
				enOuts[i].print('=');
				if (values[1] == null) {
					values[1] = key;
				}
				enOuts[i].println(values[1]);
			}
		}
		for (int i = 0; i < enFiles.length; i++) {
			enOuts[i].close();
		}
		for (int i = 0; i < zhFiles.length; i++) {
			zhOuts[i].close();
		}
	}

	/**
	 * 生成String的Unicode格式字符串
	 * @param inStr inStr
	 * @return unicode code
	 */
	static String toUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			int s = (int) myBuffer[i];
			if (s > 0 && s < 256) {
				sb.append(myBuffer[i]);
			} else {
				sb.append("\\u");
				String hexS = Integer.toHexString(s);
				sb.append(hexS);
			}
		}
		return sb.toString();
	}

	/**
	 * 返回html中用来标示中文的字符串
	 * @param inStr inStr
	 * @return "&#" + 该中文字符的10进制Unicode码 + ";"
	 */
	public static String getHtmlCode(String inStr) {
		char[] myBuffer = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			int s = (int) myBuffer[i];
			if (s < 93) {
				sb.append(myBuffer[i]);
			} else {
				sb.append("&#");
				String ten = Integer.toString(s);
				sb.append(ten);
				sb.append(";");
			}
		}
		return sb.toString();
	}

}
