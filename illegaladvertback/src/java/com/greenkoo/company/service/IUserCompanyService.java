package com.greenkoo.company.service;

import java.util.List;
import java.util.Map;

import com.greenkoo.company.model.UserCompany;
import com.greenkoo.company.model.form.UserCompanyVo;

public interface IUserCompanyService {

	/**
	 * 添加广告主或媒体
	 * 
	 * @param company
	 * @throws Exception
	 */
	void add(UserCompany company) throws Exception; 
	
	/**
	 * 更新广告主或媒体
	 * 
	 * @param company
	 * @return
	 * @throws Exception
	 */
	int update(UserCompany company) throws Exception;
	
	/**
	 * 更新状态
	 * 
	 * @param company
	 * @throws Exception
	 */
	void updateStatus(UserCompany company) throws Exception;
	
	/**
	 * 根据主键查询
	 * 
	 * @param companyId
	 * @return
	 */
	UserCompany queryByCompanyId(String companyId);
	
	/**
	 * 根据URL和类型查询查询
	 * 
	 * @param companyUrl
	 * @param type 类型
	 * @return
	 */
	UserCompany queryByCompanyUrlAndType(String companyUrl,String type);
	
	/**
	 * 查询列表数量
	 * 
	 * @param paramMap
	 * @return
	 */
	int queryCount(Map<String, Object> paramMap);
	
	/**
	 * 查询列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<UserCompanyVo> queryList(Map<String, Object> paramMap);
}
