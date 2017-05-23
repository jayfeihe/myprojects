package com.hikootech.mqcash.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuwei
 * 2015年9月7日
 * 记录用户登陆错误次数工具类
 */
public class UserLoginErrorUtils {
	
	private static Map<String, Integer> records = 
			Collections.synchronizedMap(new HashMap<String, Integer>());
	
	/**自增出错次数+1：不考虑同步问题
	 * @param key
	 */
	public static void increase(String key){
		Integer value = records.get(key);
		if(value == null){
			records.put(key, 1);
		}else{
			records.put(key, (value + 1));
		}
	}
	
	/**获取当前出错次数
	 * @param key
	 * @return
	 */
	public static Integer get(String key){
		Integer val = records.get(key);
		return EntityUtils.isEmpty(val) ? 0 : val;
	}
	
	/**
	 * 清楚所有出错登陆次数
	 */
	public static void clear(){
		records.clear();
	}
}
