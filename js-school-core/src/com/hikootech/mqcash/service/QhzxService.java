package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.po.UserQhzxCredit;
import com.hikootech.mqcash.po.UserQhzxVerify;

/**
	* 此类描述的是：调用前海征信接口
	* @author: zhaohefei
	* @version: 2015年12月16日 下午4:53:23
	*/
	
public interface QhzxService {

	/** 
	* request8004<br/> 
	*  TODO(查询黑名单信息    ) 
	* @param idNo 身份证号
	* @param name 姓名
	* @return
	* @throws Exception UserQhzxMsc8004
	* @author zhaohefei
	* @2015年12月16日
	* @return UserQhzxMsc8004	返回类型 （判断返回对象不为空，且gradeQuery属性不为空，则证明为黑名单）
	*/
	public Map<String, Object> requestQhBlackList(String idNo, String name, String id,String totalInfoId) throws Exception;
	
	/** 
	* request8005<br/> 
	*  TODO(查询好信度信息) 
	* @param idNo 身份证号
	* @param name 姓名
	* @param cardNo 若不存在，可传null
	* @param mobileNum 手机号
	* @return
	* @throws Exception UserQhzxMsc8005
	* @author zhaohefei
	* @2015年12月16日
	* @return UserQhzxMsc8005	返回类型  （判断返回对象不为空，且credooScore属性不为空，则取该值即可）
	*/
	public Map<String, Object> requestQhCredit(String idNo, String name, String cardNo, String mobileNum,String totalInfoId,String id) throws Exception;

	/** 
	* @Title queryIsQhzxBlackList 
	* @Description TODO(根据参数判断是否命中黑名单) 
	* @param 参数列表
	* @param userQhzxMsc8004
	* @return
	* @throws Exception 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean queryIsQhzxBlackList(UserQhzxBlackList userQhzxMsc8004) throws Exception;
	
	/** 
	* @Title queryIsQhzxGoodRel 
	* @Description TODO(根据参数判断好信度是否通过) 
	* @param 参数列表
	* @param userQhzxMsc8005
	* @return
	* @throws Exception 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean queryIsQhzxGoodRel(UserQhzxCredit userQhzxMsc8005,String score_result) throws Exception ;

	/** 
	* reqQhzxVerify<br/> 
	*  TODO(请求好信一鉴通) 
	* @return UserVerifyInfo
	* @author zhaohefei
	* @2016年2月23日
	* @return UserVerifyInfo	返回类型 
	*/
	public Map<String, Object>  reqQhzxVerify(String idNo, String name,  String mobileNum, String id,
			String totalInfoId)  ;
	
	/**  
	 * requestQhLoanList(请求前海常贷客接口)  
	 * @param idNo
	 * @param idNo 身份证号
	 * @param name 姓名
	 * @param totalInfoId
	 * @return
	 * @throws Exception   
	 * Map<String,Object> 
	 * @create time： 2016年5月7日 下午2:09:27 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, Object> requestQhLoanList(String idNo, String name, String id, String totalInfoId) throws Exception;
}
