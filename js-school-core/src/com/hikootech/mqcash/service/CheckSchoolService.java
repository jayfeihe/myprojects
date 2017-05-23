package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;


import com.hikootech.mqcash.po.BlackList;
import com.hikootech.mqcash.po.JsschoolEducationScore;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserSchoolRoll;

/**  
 *   
 * CheckSchoolService  
 *   
 * @function:(学籍核查信息Service)  
 * @create time:Oct 9, 2015 11:06:01 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public interface CheckSchoolService {
	
	/**
	 * 调用学籍核查接口
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryCheckSchool(Map<String, String> queryMap)  throws Exception;
	
	/**
	 * 添加学籍核查记录
	 * @param userSchoolRoll
	 */
	public void insertCheckSchoolInfo(UserSchoolRoll userSchoolRoll);
	
	/**
	 * 查询本地库中学籍信息
	 * @param queryMap
	 * @return
	 */
	public List<UserSchoolRoll> queryUserSchoolRollInfo(Map<String, String> queryMap);

	
}
