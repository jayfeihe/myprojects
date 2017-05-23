package com.hikootech.mqcash.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author Administrator
 * @date 2013年10月17日
 * 
 */
public class EntityUtils {

	public static Boolean isNotEmpty(String value) {
		return value != null && !value.trim().equals("");
	}

	public static Boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}
	public static Boolean isEmpty(Object value) {
		return value == null ;
	}
	public static Boolean isNotEmpty(Object[] objects) {
		return objects != null && objects.length > 0;
	}
	
	public static Boolean isNotEmpty(Object object) {
		return object != null && object.toString() != "";
	}

	public static Boolean isEmpty(Object[] objects) {
		return objects == null || objects.length == 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static Boolean isNotEmpty(Collection collection) {
		return collection != null && collection.size() > 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static Boolean isEmpty(Collection collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * 所有字符串都不为空
	 * 
	 * @param firstValue
	 * @param values
	 * @return
	 */
	public static Boolean allAreNotEmpty(String firstValue, String... values) {
		if (isEmpty(firstValue))
			return false;

		if (values != null)
			for (String value : values)
				if (isEmpty(value))
					return false;

		return true;
	}

	/**
	 * 所有字符串中至少有一个不为空
	 * 
	 * @param firstValue
	 * @param values
	 * @return
	 */
	public static Boolean oneIsNotEmpty(String firstValue, String... values) {
		if (!isEmpty(firstValue))
			return true;

		if (values != null)
			for (String value : values)
				if (!isEmpty(value))
					return true;

		return false;
	}
	
	/**
	 * 空字符串转换
	 * @param value
	 * @return
	 */
	public static String replaceNull(String value){
		if(isEmpty(value)){
			return "";
		}else{
			return value;
		}
	}
	
	public static String date2String(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(date2String(new Date()));
	}
	
}
