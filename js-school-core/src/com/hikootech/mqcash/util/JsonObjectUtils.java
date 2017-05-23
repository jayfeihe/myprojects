package com.hikootech.mqcash.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName JsonObjectUtils 
* @Description TODO(这里用一句话描述这个类的作用) 
* @author 余巍 yuweiqwe@126.com 
* @date 2016年5月26日 下午4:21:24 
*  
*/
public class JsonObjectUtils {
	
	private static Logger logger = LoggerFactory.getLogger(JsonObjectUtils.class);
	
	public static String getString(Object value){
		if(value == null){
			return null;
		}
		return value.toString();
	}
	
	public static Integer getInteger(Object value){
		if(value == null){
			return null;
		}
		
		Integer ret =  null;
		try {
			ret = Integer.parseInt(value.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.error("字符串转换数字出错，字符串值：" + value.toString(), e);
		}
		return ret;
	}

}
