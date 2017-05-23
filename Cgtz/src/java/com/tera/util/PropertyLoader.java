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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Wallace Chu
 * Properties.load采用了默认编码，采用中文编码的时候出现问题，此类可以很好的解决该问题。
 */
public class PropertyLoader {

	/**
	 * 后缀名
	 */
	private static final String SUFFIX = ".properties";

	/**
	 * 加载properties文件
	 * @param filename
	 *            根据文件名(绝对路径以及文件名)
	 * @return Properties
	 */
	public static Properties loadPropertiesByFilename(String filename) {
		Properties props = null;
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filename));
		} catch (FileNotFoundException ioe) {
			ioe.printStackTrace();
		}
		if (in != null) {
			props = new Properties();
			try {
				props.load(in);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return props;
	}

	/**
	 * 加载properties文件
	 * @param name
	 *            property文件名
	 * @param loader
	 *            ClassLoader
	 * @return Properties
	 */
	public static Properties loadProperties(String name, ClassLoader loader) {
		if (name == null) {
			throw new IllegalArgumentException("null input: name");
		}
		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (name.endsWith(SUFFIX)) {
			name = name.substring(0, name.length() - SUFFIX.length());
		}
		Properties result = null;
		InputStream in = null;
		try {
			if (loader == null) {
				loader = ClassLoader.getSystemClassLoader();
			}
			name = name.replace('.', '/');
			if (!name.endsWith(SUFFIX)) {
				name = name.concat(SUFFIX);
			}
			in = loader.getResourceAsStream(name);
			if (in != null) {
				result = new Properties();
				result.load(in);
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable ignore) {
					ignore.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * Properties.load采用了默认编码，在Eclipse中使用中文properties会出现问题。
	 * 在使用properties文件的时候点击：
	 * Windows->Preferences->General->Content Types标签：
	 * 选择Text->Java Properties File->Default encoding填入：GBK
	 * （一般在Eclipse中，Java源文件采用GBK编码）
	 * @param name name
	 * @param loader loader
	 * @param encoding encoding
	 * @return Properties
	 */
	public static Properties loadProperties(String name, ClassLoader loader, String encoding) {
		if (name == null) {
			throw new IllegalArgumentException("null input: name");
		}
		if (name.startsWith("/")) {
			name = name.substring(1);
		}
		if (name.endsWith(SUFFIX)) {
			name = name.substring(0, name.length() - SUFFIX.length());
		}
		Properties result = new Properties();
		InputStream in = null;
		try {
			if (loader == null) {
				loader = ClassLoader.getSystemClassLoader();
			}
			name = name.replace('.', '/');
			if (!name.endsWith(SUFFIX)) {
				name = name.concat(SUFFIX);
			}
			in = loader.getResourceAsStream(name);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
			if (reader != null) {
				String str;
				while ((str = reader.readLine()) != null) {
					str = str.trim();
					if (str.length() == 0 || str.startsWith("#")) {
						continue;
					}
					int index = str.indexOf('=');
					String key = str.substring(0, index).trim();
					String value = str.substring(index + 1).trim();
					result.setProperty(key, value);
				}
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable ignore) {
					ignore.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * @param name name
	 * @return Properties
	 */
	public static Properties loadProperties(String name) {
		return loadProperties(name, Thread.currentThread()
				.getContextClassLoader());
	}

	/**
	 * @param name name
	 * @param encoding encoding
	 * @return Properties
	 */
	public static Properties loadProperties(String name, String encoding) {
		return loadProperties(name, Thread.currentThread()
				.getContextClassLoader(), encoding);
	}

}
