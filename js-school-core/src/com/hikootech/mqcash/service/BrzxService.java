package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.bfd.facade.MerchantServer;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserBrSpeciallistC;
import com.hikootech.mqcash.po.UserInfo;

public interface BrzxService {

	/** 
	* LoginBr<br/> 
	*  TODO  调用百融的登陆接口最终返回tokenId 
	* @param ms
	* @return
	* @throws Exception String
	* @author zhaohefei
	* @2016年2月19日
	* @return String	返回类型 
	*/
	public String LoginBr(MerchantServer ms) throws Exception;
	/**  
	 * transMsgToBr(调用查询报告接口,返回百融查询结果)  
	 * @param brRequestPo 包含查询内容的类
	 * @return
	 * @throws Exception   
	 * UserBrSpeciallistC 
	 * @create time： 2016年5月13日 下午2:09:26 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserBrSpeciallistC transMsgToBr(UserBrRequest brRequestPo);
	
	/**  
	 * testTransMsgToBr(后台征信测试返回结果)  
	 * @param brRequestPo
	 * @return
	 * @throws Exception   
	 * String 
	 * @create time： 2016年5月16日 下午4:52:13 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String testTransMsgToBr(UserBrRequest brRequestPo) throws Exception ;
	
	public List<UserInfo> queryUserInfoList();
	public void updateBrMsg(UserInfo userDetailVo);
	
	/**  
	 * reqBr(请求百荣公用方法)  
	 * @param brRequestPo
	 * @return
	 * @throws Exception   
	 * String 
	 * @create time： 2016年5月25日 下午2:57:39 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String reqBr(UserBrRequest brRequestPo) throws Exception;
	
	public Map<String, Object> requestBR(Map<String, Object> paramMap) throws Exception;
	
}


