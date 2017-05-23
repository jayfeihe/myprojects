package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.po.GenArea;

/**
	* 此类描述的是：获取地区的DAO层
	* @author: zhaohefei
	* @version: 2015年10月17日 上午10:57:24
	*/
	
public interface GenAreaDAO {
	
	/** 
	* getAreaList<br/> 
	*  TODO(根据父级节点查询地区信息) 
	* @param parentCode
	* @return
	* @throws Exception List<GenArea>
	* @author zhaohefei
	* @2015年12月15日
	* @return List<GenArea>	返回类型 
	*/
	public List<GenArea> getAreaList(String parentCode) throws Exception;
	
}
