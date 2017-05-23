package com.tera.interfaces.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tera.util.StringUtils;

public class GsonUtils {

	private static GsonUtils gu = new GsonUtils();
	
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
	private GsonUtils(){}
	
	public static GsonUtils getInstance() {
		if (gu == null) {
			gu = new GsonUtils();
		}
		return gu;
	}
	
	/**
	 * 对象转json
	 * @param target
	 * @return
	 */
	public String toJson(Object target) {
		return toJson(target, null, false, null, false);
	}
	
	/**
	 * 类型对象转json
	 * @param target
	 * @param targetType
	 * @return
	 */
	public String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, false);
	}
	
	/**
	 * 类型对象转json
	 * @param target
	 * @param targetType
	 * @param isSerializeNulls 是否序列化空值
	 * @return
	 */
	public String toJson(Object target, Type targetType,boolean isSerializeNulls) {
		return toJson(target, targetType, isSerializeNulls, null, false);
	}
	
	/**
	 * 类型对象转json
	 * @param target
	 * @param targetType
	 * @param datePattern 日期格式
	 * @return
	 */
	public String toJson(Object target, Type targetType,String datePattern) {
		return toJson(target, targetType, false, datePattern, false);
	}
	
	/**
	 * 类型对象转json
	 * @param target
	 * @param excludesFieldsWithoutExpose 是否排除未标有expose注解的属性
	 * @return
	 */
	public String toJson(Object target, boolean excludesFieldsWithoutExpose) {
		return toJson(target,null, false, null, excludesFieldsWithoutExpose);
	}
	
	/**
	 * 转换为json
	 * @param target 目标对象
	 * @param targetType 对象类型
	 * @param isSerializeNulls 是否序列化空值
	 * @param datePattern 日期格式
	 * @param excludesFieldsWithoutExpose 是否排除未标有expose注解的属性
	 * @return
	 */
	public String toJson(Object target, Type targetType, boolean isSerializeNulls, String datePattern,
			boolean excludesFieldsWithoutExpose) {
		if (target == null)
			return "";

		GsonBuilder builder = new GsonBuilder();
		if (isSerializeNulls)
			builder.serializeNulls();

		if (StringUtils.isNullOrEmpty(datePattern))
			datePattern = DEFAULT_DATE_PATTERN;
		builder.setDateFormat(datePattern);

		if (excludesFieldsWithoutExpose)
			builder.excludeFieldsWithoutExposeAnnotation();

		String result = "";
		Gson gson = builder.create();
		try {
			if (targetType != null) {
				result = gson.toJson(target, targetType);
			} else {
				result = gson.toJson(target);
			}
		} catch (Exception ex) {
			if (target instanceof Collection || target instanceof Iterator || target instanceof Enumeration
					|| target.getClass().isArray()) {
				result = "[]";
			} else
				result = "{}";
		}
		return result;
	}

	/**
	 * json转对象
	 * @param json json串
	 * @param clazz 对象类型
	 * @return
	 */
	public <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, null);
	}
	
	/**
	 * json转对象
	 * @param json json串
	 * @param clazz 对象类型
	 * @param datePattern 日期格式
	 * @return
	 */
	public <T> T fromJson(String json, Class<T> clazz, String datePattern) {
		if (StringUtils.isNullOrEmpty(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (StringUtils.isNullOrEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.setDateFormat(datePattern).create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param json
	 * @param token 适用于泛型对象
	 * @return
	 */
	public <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null);
	}
	
	/**
	 * 
	 * @param json
	 * @param token 适用于泛型对象
	 * @param datePattern 日期格式
	 * @return
	 */
	public <T> T fromJson(String json, TypeToken<T> token,    
         String datePattern) {    
     if (StringUtils.isNullOrEmpty(json)) {    
         return null;    
     }    
     GsonBuilder builder = new GsonBuilder();    
     if (StringUtils.isNullOrEmpty(datePattern)) {    
         datePattern = DEFAULT_DATE_PATTERN;    
     }    
     Gson gson = builder.setDateFormat(datePattern).create();    
     try {    
         return gson.fromJson(json, token.getType());    
     } catch (Exception ex) {    
         ex.printStackTrace();
    	 return null;    
     }    
 }

}
