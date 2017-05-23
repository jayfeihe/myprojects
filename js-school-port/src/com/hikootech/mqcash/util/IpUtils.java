package com.hikootech.mqcash.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yuwei
 * 2015年8月13日
 * 关于ip的处理工具类
 */
public class IpUtils {
	
	/**
	 * 查询ip归属地的url 
	 */
	private static String ipAttributionGetUrl = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";
	
	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
	
	public static Map<String, String> queryIpAttributionMap(String ip){
		Map<String, String> retMap = new HashMap<String, String>();
		
		String json = null;
		HttpClientUtil clientUtil = new HttpClientUtil();
		try {
			json = clientUtil.doGet(ipAttributionGetUrl + ip, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(json == null || "".equals(json)){
			retMap.put("code", "-1");
			retMap.put("desc", "没有查询结果");
			return retMap;
		}
		
		String[] arr = json.split("\\s");
		
		
		if(arr.length <= 3){
			retMap.put("code", "-1");
			retMap.put("desc", "没有国家、省份、城市、地区查询结果");
			return retMap;
		}else if(arr.length == 4){
			retMap.put("code", "0");
			retMap.put("country", arr[3]);
		}else if(arr.length == 5){
			retMap.put("code", "0");
			retMap.put("country", arr[3]);
			retMap.put("province", arr[4]);
		}else if(arr.length == 6){
			retMap.put("code", "0");
			retMap.put("country", arr[3]);
			retMap.put("province", arr[4]);
			retMap.put("city", arr[5]);
		}else if(arr.length >= 7){
			retMap.put("code", "0");
			retMap.put("country", arr[3]);
			retMap.put("province", arr[4]);
			retMap.put("city", arr[5]);
			retMap.put("district", arr[6]);
		}
		
		return retMap;
	}
	
	public static String queryIpAttribution(String ip){
		String json = null;
		HttpClientUtil clientUtil = new HttpClientUtil();
		try {
			json = clientUtil.doGet(ipAttributionGetUrl + ip, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(json == null || "".equals(json)){
			return null;
		}
		
		String[] arr = json.split("\\s");
		
		String result = null;
		if(arr.length <= 3){
			return result;
		}else if(arr.length == 4){
			result = arr[3];
		}else if(arr.length == 5){
			result = arr[3] + "," + arr[4];
		}else if(arr.length == 6){
			result = arr[3] + "," + arr[4] + "," + arr[5];
		}else if(arr.length >= 7){
			result = arr[3] + "," + arr[4] + "," + arr[5] + "," + arr[6];
		}
		
		return result;
	}

	/**
	 * 通过左移位操作（<<）给每一段的数字加权
	 * 第一段的权为2的24次方 
	 * 第二段的权为2的16次方 
	 * 第三段的权为2的8次方 
	 * 最后一段的权为1
	 * 
	 * @param ip
	 * @return int
	 */
	public static int ipToInt(String ip) {
		String[] ips = ip.split("\\.");
		return (Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16)
				+ (Integer.parseInt(ips[2]) << 8) + Integer.parseInt(ips[3]);
	}

	/**
	 * 将整数值进行右移位操作（>>） 
	 * 右移24位，右移时高位补0，得到的数字即为第一段IP 
	 * 右移16位，右移时高位补0，得到的数字即为第二段IP
	 * 右移8位，右移时高位补0，得到的数字即为第三段IP 
	 * 最后一段的为第四段IP
	 * 
	 * @param i
	 * @return String
	 */
	public static String intToIp(int i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
				+ ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

	
	public static void main(String[] args) {
		Map<String, String> retMap = queryIpAttributionMap("222.128.15.134");
		Iterator<String> iterator = retMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = retMap.get(key);
			System.out.println(key + " : " + value);
		}
		
		System.out.println(queryIpAttribution("222.128.15.134"));
		
		System.out.println(ipToInt("255.255.255.255"));
	}
	
}
