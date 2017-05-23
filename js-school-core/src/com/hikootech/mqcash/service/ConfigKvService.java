package com.hikootech.mqcash.service;

import java.util.List;

import com.hikootech.mqcash.po.ConfigKv;

/** 
* @ClassName ConfigKvService 
* @Description TODO(秒趣配置表：存放系统中使用key value配置项) 
* @author HAI DA
* @date Dec 10, 2015 3:14:18 PM 
*  
*/
public interface ConfigKvService {

	/** 
	* @Title queryAll 
	* @Description TODO(查询配置表所有信息) 
	* @param 参数列表
	* @return 设定文件 
	* @return List<ConfigKv>	返回类型 
	* @throws 
	*/
	public List<ConfigKv> queryAll();
	
	/** 
	* @Title get 
	* @Description TODO(通过配置ｋｅｙ查询对应的配置值) 
	* @param 参数列表
	* @param key
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String get(String key);

}
