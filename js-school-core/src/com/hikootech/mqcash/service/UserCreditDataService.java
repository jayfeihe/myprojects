package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.BlackList;
import com.hikootech.mqcash.po.PartnerUserOrder;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserCreditInfo;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserJyzxRecordItem;
import com.hikootech.mqcash.po.UserMobileCreditInfo;
import com.hikootech.mqcash.po.UserQhzxLoan;
import com.hikootech.mqcash.po.UserQhzxVerify;
import com.hikootech.mqcash.po.WhiteList;

/**  
 *   
 * UserCreditDataService  
 *   
 * @function:(查询用户信用信息Service)  
 * @create time:Oct 9, 2015 11:06:01 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public interface UserCreditDataService {
	
	/**  
	 * querySingleGbossUserCreditInfo(查询国政通单个用户学历数据)  
	 * @param userName (用户姓名)
	 * @param identityCard (身份证号码)
	 * @return   
	 * UserCreditInfo 
	 * @create time： Oct 9, 2015 11:05:53 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserCreditInfo queryGbossResult(String userName, String identityCard) ;
	
	/**  
	 * querySingleUserMobileCreditInfo(查询国政通单个手机信息数据)  
	 * @param mobileNumber (调用国政通电信信用用户的手机号)
	 * @param uniqueCode (调用国政通电信信用用户的唯一标识)
	 * @return   
	 * UserMobileCreditInfo 
	 * @create time： Oct 9, 2015 11:05:55 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserMobileCreditInfo querySingleGbossUserMobileCreditInfo(String mobileNumber, String uniqueCode) throws Exception;

	/**  
	 * queryExistInstalment(查询该用户是否有未完成的分期订单)  
	 * @param instalmentMap
	 * @return   
	 * int 
	 * @create time： Nov 20, 2015 11:38:35 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Integer queryExistInstalment(Map<String, Object> instalmentMap);

	/**  
	 * insertUserCreditRecord(插入征信结果记录表)  
	 * @param creditRecord   
	 * void 
	 * @create time： Nov 25, 2015 7:46:43 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertUserCreditRecord(UserCreditRecord creditRecord);

	/**  
	 * returnCreditResult(将国政通数据传给网厅，网厅通过企信查询个人征信，返回用户是否通过征信)     
	 * void 
	 * @create time： Oct 20, 2015 8:00:55 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, String>  returnCreditResult(UserCreditRecord creditRecord ,PartnerUserOrder partnerUserOrder,Map<String, String> crmMap) ;
	/** 
	* @Title requestQXCredit 
	* @Description TODO(请求征信引擎，查询征信信息) 
	* @param 参数列表
	* @param digestMap
	* @return 设定文件 
	* @return Map<String,String>	返回类型 
	* @throws 
	*/
	public Map<String, String>  requestQXCredit(Map<String, String> digestMap);

	/** 
	* @Title isAllowInstalment 
	* @Description TODO(是否允许进行下单) 
	* @param 参数列表
	* @param instalmentMap
	* @return 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean isAllowInstalment(Map<String, Object> instalmentMap);
	
	/** 
	* @Title isCreditByAge 
	* @Description TODO(根据年龄征信前判断是否可以征信) 
	* @param 参数列表
	* @param partnerUserOrder
	* @return 设定文件 
	* @return Map<String, String>	返回类型 
	* @throws 
	*/
	public boolean isCreditByAge(String idCard,String configAgeSection);
	/** 
	* @Title isCreditByName 
	* @Description TODO(根据姓名征信前判断是否可以征信) 
	* @param 参数列表
	* @param name 用户姓名
	* @return 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean isCreditByName(String name);
	
	/** 
	* @Title requestJYCredit 
	* @Description TODO(请求91征信) 
	* @param 参数列表
	* @param userName 姓名	
	* @param idCard 身份证号
	* @param telNumber 手机号
	* @param totalInfoId 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public  void  requestJYCredit(String userName,String idCard,String telNumber,String totalInfoId,String callType) ;
	/** 
	 * @Title requestHDCredit 
	 * @Description TODO(请求好贷征信) 
	 * @param 参数列表
	 * @param userName 姓名	
	 * @param idCard 身份证号
	 * @param telNumber 手机号
	 * @param totalInfoId 设定文件 
	 * @return void	返回类型 
	 * @throws 
	 */
	public  void  requestHDCredit(String userName,String idCard,String telNumber,String totalInfoId) ;
			

	/** 
	* @Title queryBlackList 
	* @Description TODO(根据黑名单判断用户是否可以继续征信) 
	* @param 参数列表
	* @param name
	* @param idCard
	* @return 设定文件 
	* @return BlackList	返回类型 
	* @throws 
	*/
	public BlackList queryBlackList(String idCard,String telPhone)  throws Exception;
	/** 
	* @Title queryWhiteList 
	* @Description TODO(根据白名单判断用户是否可以继续征信) 
	* @param 参数列表
	* @param name
	* @param idCard
	* @param telPhone
	* @param partnerId
	* @return
	* @throws Exception 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean queryWhiteList(String name, String idCard,String telPhone, String partnerId)  throws Exception;
	/**  
	 * @Description TODO(根据白名单判断用户是否可以继续征信) 
	 * @param map
	 * @return
	 * @throws Exception   
	 * WhiteList 
	 * @create time： 2016年8月3日 下午4:28:28 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public WhiteList queryWhiteList(Map<String,String> map)  throws Exception;
	
	/** 
	* @Title insertCreditModelRecord 
	* @Description TODO(增加征信模快) 
	* @param 参数列表
	* @param record 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public void insertCreditModelRecord(String creditId,String _CROpen,String creditModel,String creditModelType, int creditResult,String resultDesc);

	/** 
	* @Title queryCreditIdByTotalId 
	* @Description TODO(通过业务id查询征信记录id) 
	* @param 参数列表
	* @param totalId
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String queryCreditIdByTotalId(String totalId);
	
	/**  
	 * isBlackUser(查询该用户是否是黑名单用户)  
	 * @param idCard
	 * @param name
	 * @return   
	 * UserCreditRecord 
	 * @create time： 2016年3月30日 上午10:53:52 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserCreditRecord isBlackUser(String idCard,String name);

	/**  
	 * updateCR(修改征信结果)  
	 * @param creditRecord
	 * @param creditIsPass
	 * @param creditWlRecordPass   
	 * void 
	 * @create time： 2016年7月13日 上午10:22:14 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateCR(UserCreditRecord creditRecord, Integer creditIsPass, String creditWlRecordPass);
	
	/**  
	 * queryVerify(查询一鉴通征信是否通过)  
	 * @param idCard
	 * @param userName
	 * @param telNumber
	 * @param infoId
	 * @return   
	 * String 
	 * @create time： 2016年7月13日 下午1:33:23 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserQhzxVerify queryVerify(String idCard, String userName, String telNumber,String infoId) ;

	/**  
	 * queryJyCreditResult(查询91征信结果)  
	 * @param userName
	 * @param idCard
	 * @return   
	 * List<UserJyzxRecordItem> 
	 * @create time： 2016年7月13日 下午8:07:24 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserJyzxRecordItem> queryJyCreditResult(String userName, String idCard);
	
	
	/**  
	 * queryExistQhBlackListData(是否存在前海黑名单数据)  
	 * @return   
	 * List<UserQhzxBlackList> 
	 * @create time： 2016年7月13日 下午8:56:54 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, Object> queryExistQhBlackListData(String name ,String idCard,String infoId) throws Exception;

	/**  
	 * queryExistBrBlackListData(查询百融征信信息 )  
	 * @param brRequestPo
	 * @return   
	 * List<UserBrSpeciallistC> 
	 * @create time： 2016年7月13日 下午9:25:46 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, Object> queryExistBrBlackListData(UserBrRequest brRequestPo)  throws Exception;
	
	/**  
	 * loanCreditCompare(常贷客规则比较)  
	 * @param userQhzxMsc8037
	 * @param data
	 * @param type
	 * @return   
	 * boolean 
	 * @create time： 2016年7月13日 下午9:36:06 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public boolean loanCreditCompare(UserQhzxLoan userQhzxMsc8037,String data,String type);
	
	/**  
	 * loanCreditResult(常贷客征信结果)  
	 * @param userQhzxMsc8037
	 * @param creditRecord
	 * @param type
	 * @param _CROpen
	 * @return   
	 * Map<String,String> 
	 * @create time： 2016年7月13日 下午9:37:02 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String,String> loanCreditResult(UserQhzxLoan userQhzxMsc8037,UserCreditRecord creditRecord,String type,String _CROpen);
	
	/**  
	 * applyLoanCreditCompare(判断是否符合百融多次申请核查拒贷条件)  
	 * @param brBankNum
	 * @param brBankNumConfig
	 * @return   
	 * boolean 
	 * @create time： 2016年7月13日 下午9:37:37 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public boolean applyLoanCreditCompare(Integer brBankNum,String brBankNumConfig);
	
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
	public Map<String,String> applyLoanCreditResult(UserCreditRecord creditRecord,String _CROpen);

	/**  
	 * queryExistQhLoanList(查询是否存在前海常贷客信息 )  
	 * @param userName
	 * @param idCard
	 * @return   
	 * Map<String, Object> 
	 * @create time： 2016年7月14日 上午9:44:56 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, Object> queryExistQhLoanList(String userName, String idCard,String infoId) throws Exception;

	/**  
	 * queryExistBrApplyList(查询是否存在百融多次申请信息)  
	 * @param brRequestPo
	 * @return   
	 *  Map<String, Object> brApplyLoanMap
	 * @create time： 2016年7月14日 上午10:24:47 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public  Map<String, Object> queryExistBrApplyList(UserBrRequest brRequestPo) throws Exception;

	/**
	 * queryQhCreditList 查询是否存在前海好信度信息
	 * @param idCard
	 * @param userName
	 * @param telNumber
	 * @return
	 */
	public  Map<String, Object> queryQhCreditList(String userName , String idCard ,String telNumber ,String bankCardNumber ,String busId) throws Exception;

	/**  
	 * queryJyRecordId(查询91主表主键)  
	 * @param trxNo
	 * @return   
	 * String 
	 * @create time： 2016年8月4日 下午3:06:12 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String queryJyRecordId(String trxNo);
	
}
