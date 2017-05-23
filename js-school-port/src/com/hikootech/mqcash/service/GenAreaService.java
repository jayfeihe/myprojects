package com.hikootech.mqcash.service;

import java.util.List;

import com.hikootech.mqcash.po.GenArea;

/**
	* 此类描述的是：获取地区信息
	* @author: zhaohefei
	* @version: 2015年10月17日 上午11:10:05
	*/
	
public interface GenAreaService {

	
		/**getAreaList
		* 此方法描述的是：根据父节点查找地域信息
		* @author: zhaohefei
		* @version: 2015年10月22日 下午2:33:36
		*/
		
	public List<GenArea> getAreaList(String parentCode);
}
