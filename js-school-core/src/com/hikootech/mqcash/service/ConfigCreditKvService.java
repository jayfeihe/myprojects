package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.ConfigCreditKv;
import com.hikootech.mqcash.po.ConfigKv;

/** 
* @ClassName ConfigCreditKvService 
* @Description TODO(秒趣配置表：存放系统中使用key value配置项) 
* @author HAI DA
* @date Dec 10, 2015 3:14:18 PM 
*  
*/
public interface ConfigCreditKvService {

	/** 
	* @Title queryAll 
	* @Description TODO(查询配置表所有信息) 
	* @param 参数列表
	* @return 设定文件 
	* @return List<ConfigCreditKv>	返回类型 
	* @throws 
	*/
	public List<ConfigCreditKv> queryAll();
	
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
	
	/** 
	* @Title refresh 
	* @Description TODO(这里用一句话描述这个方法的作用) 
	* @param 参数列表
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String refresh();
	
	/**
	 * 获取所有征信配置项：
	 * 1.先去memcached中查询
	 * 2.如果memcached中查询结果为空，去数据库中查询
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getAllCreditKv() throws Exception;

}
