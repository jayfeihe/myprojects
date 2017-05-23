package com.hikootech.mqcash.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {
	
	/**
	 * 将a=b&c=d转换成key-value（a-b）的map形式
	 * @param str
	 * @return
	 */
	public static Map<String, String> parseStringToMap(String str){
		Map<String, String> ret = new HashMap<String, String>();
		String[] strs = str.split("&");
		for (String string : strs) {
			String[] pair = string.split("=");
			if(pair.length == 2){
				ret.put(pair[0], pair[1]);
			}else{
				ret.put(pair[0], "");
			}
		}
		return ret;
	}
	
	/**
	 * Convert byte[] to hex string
	 * 将数组以0xFF的形式转化
	 * @param src
	 * @return hex string
	 */
	public static String byteArray2HexStr(byte[] src) {
		if (src == null || src.length <= 0) {
			return  "";
		}
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			stringBuilder.append("0x");
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv.toUpperCase());
		}
		return stringBuilder.toString();
	}

	
	/** 
	* join<br/> 
	*  TODO(将数组转化为字符串，用separator分隔) 
	* @param o
	* @param separator 分隔符
	* @return String
	* @author zhaohefei
	* @2015年12月16日
	* @return String	返回类型 
	*/
	public static String join( Object[] o , String separator ){
        StringBuffer str_buff = new StringBuffer();
        int len=o.length;
        for(int i=0 ; i<len ; i++){
            str_buff.append( String.valueOf( o[i] ) );
            if(i<len-1)str_buff.append( separator );
        }
        return str_buff.toString(); 
    }
	
	/** 
	* @Title get 
	* @Description 从paramMap中获取key为paramName的value值，当value为空时，返回defaultValue
	* @param 参数列表 
	* @param paramMap
	* @param paramName
	* @param defaultValue
	* @return 返回类型 boolean	
	*/
	public static boolean get(Map<String, Object> paramMap, String paramName, boolean defaultValue) {
		if(paramMap == null){
			return defaultValue;
		}
		Object value = paramMap.get(paramName);
		
		if(value instanceof java.lang.Boolean){
			return (boolean) value;
		}else if(value instanceof java.lang.String){
			if("true".equalsIgnoreCase(String.valueOf(value))){
				return true;
			}else if("false".equalsIgnoreCase(String.valueOf(value))){
				return false;
			}else{
				return Boolean.valueOf(String.valueOf(value));
			}
			//throw new RuntimeException(paramName + "字符串无法根据true格式和false格式转换为Boolean对象");
		}
		
		if(value == null || "".equals(value.toString().trim())){
			return defaultValue;
		}
		
		return false;
	}
	
	/** 
	* @Title get 
	* @Description 从paramMap中获取key为paramName的value值，当value为空时，返回defaultValue
	* @param 参数列表 
	* @param map
	* @param key
	* @param defaultValue
	* @return 返回类型 String	
	*/
	public static String get(Map<String, String> map, String key, String defaultValue){
		if(map == null || map.size() == 0){
			return defaultValue;
		}
		
		String value = map.get(key);
		if(value == null || "".equals(value.trim())){
			return defaultValue;
		}
		
		return value;
	}


}
