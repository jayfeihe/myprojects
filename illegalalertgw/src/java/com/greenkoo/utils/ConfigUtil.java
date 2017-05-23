package com.greenkoo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 2014年1月7日 cn.com.speed.agentutil.utilConfigUtils.java
 * 
 * @author yuwei 读取配置文件
 */
public class ConfigUtil {

	private static Properties p;

	/**
	 * @param filename
	 *            配置文件
	 * @param key
	 *            属性
	 * @return 值
	 * @throws IOException
	 */
	public static String getProperty(String key) {
		if(p != null){
			return p.getProperty(key);
		}
		
		synchronized(ConfigUtil.class){
			if(p != null){
				return p.getProperty(key);
			}
			
			InputStream in = ConfigUtil.class.getClassLoader()
					.getResourceAsStream("config.properties");

			p = new Properties();
			try {
				p.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return p.getProperty(key).trim();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(ConfigUtil.getProperty("db_id"));
	}

}
