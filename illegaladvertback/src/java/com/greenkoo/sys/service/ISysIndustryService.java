package com.greenkoo.sys.service;

import java.util.List;

import com.greenkoo.sys.model.SysIndustry;

/**
 * 行业数据字典Service接口
 * 
 * @author QYANZE
 *
 */
public interface ISysIndustryService {

	/**
	 * 根据id查询
	 * 
	 * @param industryId
	 * @return
	 */
	SysIndustry queryByIndustryId(int industryId);
	
	/**
	 * 查询所有的
	 * 
	 * @return
	 */
	List<SysIndustry> queryList();
}
