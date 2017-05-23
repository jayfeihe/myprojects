package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.JsschoolEducationScore;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserQhzxLoan;

public interface NewStudentCreditService {
	
	/** 
	* @Title: validateCreditParameters 
	* @Description: 校验校园学生征信参数 
	* @author yuwei
	* @date 2016年8月29日
	* @param busMap
	* @return    设定文件 
	* @return 返回类型 Map<String,Object>
	*/
	public Map<String , String> validateCreditParameters(Map<String, String> busMap);
	
	/** 
	* @Title isCreditByName 
	* @Description TODO(根据姓名征信前判断是否可以征信) 
	* @param 参数列表
	* @param name 用户姓名
	* @return 设定文件 
	* @return boolean	返回类型 
	*/
	public boolean isCreditByName(String name);
	
	/** 
	* @Title: credit 
	* @Description: 校园学生征信
	* @author yuwei
	* @date 2016年8月30日
	* @param busMap
	* @return    设定文件 
	* @return 返回类型 Map<String,String>
	*/
	public Map<String, String> credit(Map<String, String> busMap);

}
