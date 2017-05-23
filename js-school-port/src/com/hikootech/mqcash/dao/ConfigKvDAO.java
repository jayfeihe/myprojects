package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.ConfigKv;

/** 
* @ClassName ConfigKvDAO 
* @Description TODO(秒趣配置表：存放系统中使用key value配置项DAO) 
* @author HAI DA
* @date Dec 10, 2015 3:24:29 PM 
*  
*/
public interface ConfigKvDAO {
	/** 
	* @Title queryAll 
	* @Description TODO(查询配置表所有信息) 
	* @param 参数列表
	* @return 设定文件 
	* @return List<ConfigKv>	返回类型 
	* @throws 
	*/
	public List<ConfigKv> queryAll();
	
}
