package com.hikootech.mqcash.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.ConfigKv;

/** 
* @ClassName CommonKV 
* @Description 通用KV缓存对象 
* @author 余巍 yuweiqwe@126.com 
* @date 2015年12月10日 下午4:56:10 
*  
*/
public class CommonKVContext {
	
	/** 
	* @Fields kvCache : t_config_kv配置表缓存数据
	*/ 
	public static Map<String, String> kvCache;
	
	public static Map<String, String> unipayCityDic;
	
	public static void init(Map<String, String> cache){
		kvCache = cache;
	}
	
	public static void initUnipayCity(Map<String, String> cache){
		unipayCityDic = cache;
	}
	
	public static void init(List<ConfigKv> list){
		if(list == null || list.size() == 0){
			return;
		}
		
		kvCache = new HashMap<String, String>();
		
		for (ConfigKv kv : list) {
			kvCache.put(kv.getKvKey(), kv.getKvValue());
		}
	}
	
	public static String get(String key){
		return kvCache.get(key);
	}
	
}
