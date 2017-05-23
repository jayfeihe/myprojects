package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.JsschoolEducationScore;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserQhzxLoan;

/**  
 *   
 * CollegeCreditDataService  
 *   
 * @function:(查询用户信用信息Service)  
 * @create time:Oct 9, 2015 11:06:01 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public interface CreditCollegeService {
	

	/**  
	 * requestCredit()     
	 * void 
	 * @create time： Oct 20, 2015 8:00:55 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, String>  requestCredit(Map<String, String> reqMap) ;
	
	/**  
	 * isBlackUser(查询是否命中历史黑名单)  
	 * @param idCard
	 * @param name
	 * @return
	 * @throws Exception   
	 * UserCreditRecord 
	 * @create time： 2016年7月13日 下午4:33:00 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserCreditRecord isBlackUser(String idCard,String name);
	
	/**  
	 * queryJyCreditResult(查询91征信结果)  
	 * @param credit_id
	 * @return   
	 * String 
	 * @create time： 2016年7月13日 下午7:50:25 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String queryJyCreditResult(String credit_id);
	
	/**  
	 * queryCollegeScore(学历评分是否通过)  
	 * @param districtName
	 * @param department
	 * @param entranctDate
	 * @return   
	 * JsschoolEducationScore 
	 * @create time： 2016年7月14日 下午4:15:07 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public JsschoolEducationScore queryCollegeScore(String districtName, String department, String entranctDate,String busId) ;
	
	
	/**
	 * 查询前海好信度
	 * @param reqMap
	 * @return
	 */
	public Map<String, String> requestQhCredit(Map<String, String> reqMap);
	
	/** 
	* @Title catchQhCreditData 
	* @Description 获取前海好信度数据（3个月内的）
	* @param 参数列表 
	* @param reqMap
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, String> catchQhCreditData(Map<String, String> reqMap);
	
	/**
	 * 更新征信结果
	 * @param creditRecord
	 * @param isPass
	 * @param reason
	 */
	public void updateCR(UserCreditRecord creditRecord ,Integer isPass,String reason);
	
	/**
	 * 添加征信记录
	 * @param creditId
	 * @param _CROpen
	 * @param creditModel
	 * @param creditModelType
	 * @param creditResult
	 * @param resultDesc
	 */
	public void insertShoolCreditModelRecord(String creditId,String _CROpen,String creditModel, String creditModelType, int creditResult,String resultDesc,String dataId);	
	/**  
	 * updateJsSchoolBusCreditResult(修改江苏校园业务表中征信结果)  
	 * @param busId
	 * @return   
	 * String 
	 * @create time： 2016年7月14日 下午8:17:17 
	 * @author：张海达  
	 * @since  1.0.0
	 */

	public void updateJsSchoolBusCreditResult(String busId,String creditReason);
	
	/**
	 * requestCheckSchool 查询学籍信息
	 * @param queryMap
	 * @return Map<String, Object>
	 */
	public Map<String, String> requestCheckSchool(Map<String, String> queryMap) throws Exception;
	
	/**  
	 * loanCreditResult(常贷客征信结果)  
	 * @param userQhzxMsc8037
	 * @param creditRecord
	 * @param type
	 * @param _CROpen
	 * @return   
	 * Map<String,String> 
	 * @create time： 2016年8月3日 下午5:26:01 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String,String> loanCreditResult(UserQhzxLoan userQhzxMsc8037,UserCreditRecord creditRecord,String type,String _CROpen);
	
	/**  
	 * applyLoanCreditResult(百融多次申请核查征信结果)  
	 * @param creditRecord
	 * @param _CROpen
	 * @return   
	 * Map<String,String> 
	 * @create time： 2016年7月13日 下午9:37:56 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String,String> applyLoanCreditResult(BrApplyLoan brApplyLoan,UserCreditRecord creditRecord,String _CROpen);

	/**  
	 * validateSchoolSendCodeParams(验证发送验证码征信的参数)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： 2016年8月5日 上午9:32:12 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, Object> validateSchoolCreditParams(Map<String, String> busMap);
	
	/** 
	* @Title: creditStudentWhiteList 
	* @Description: 新生白名单征信判断
	* @author yuwei
	* @date 2016年8月30日
	* @param busMap
	* @return    设定文件 
	* @return 返回类型 Map<String,Object>
	*/
	public Map<String, Object> creditStudentWhiteList(Map<String, String> busMap);
	
	/** 
	* @Title: creditStudentWhiteList 
	* @Description: 判断是那种类型的历史黑名单
	* @author yuwei
	* @date 2016年8月30日
	* @param busMap
	* @return    设定文件 
	* @return 返回类型 Map<String,Object>
	*/
	public String getBlackUserData(String idCard,String name) throws Exception;
}
