package com.hikootech.mqcash.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.BlackList;
import com.hikootech.mqcash.po.BrApplyLoan;
import com.hikootech.mqcash.po.UserBrRequest;
import com.hikootech.mqcash.po.UserBrSpeciallistC;
import com.hikootech.mqcash.po.UserCreditInfo;
import com.hikootech.mqcash.po.UserCreditModelRecord;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.po.UserCreditRequestRecord;
import com.hikootech.mqcash.po.UserJyzxRecordItem;
import com.hikootech.mqcash.po.UserMobileCreditDetailInfo;
import com.hikootech.mqcash.po.UserMobileCreditInfo;
import com.hikootech.mqcash.po.UserQhzxBlackList;
import com.hikootech.mqcash.po.UserQhzxLoan;
import com.hikootech.mqcash.po.WhiteList;

public interface UserCreditDataDAO {
	
	public List<UserCreditInfo> querySingleUserCreditInfo(Map<String, String> paramMap);
	
	public UserMobileCreditInfo querySingleUserMobileCreditInfo(String mobileNumber);
	
	public void insertUserMobileCreditInfo(UserMobileCreditInfo userMobileCreditInfo);
	
	public void insertUserMobileCreditDetailInfo(UserMobileCreditDetailInfo userMobileCreditDetailInfo);
	
	/**  
	 * insertUserCreditInfo(插入学历用户征信信息表)  
	 * @param userCreditInfo   
	 * void 
	 * @create time： Oct 9, 2015 4:36:07 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertUserCreditInfo(UserCreditInfo userCreditInfo);

	/**  
	 * insertUserCreditRequestRecord(插入用户征信请求记录表)  
	 * @param userCreditInfo   
	 * void 
	 * @create time： Oct 9, 2015 4:36:04 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertUserCreditRequestRecord(UserCreditRequestRecord userCreditRequestRecord);

	/**  
	 * queryExistInstalment(查询该用户是否有未完成的分期单)  
	 * @param instalmentMap
	 * @return   
	 * Integer 
	 * @create time： Nov 20, 2015 11:39:43 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Integer queryExistInstalment(Map<String, Object> instalmentMap);

	/**  
	 * insertUserCreditRecord(插入征信结果记录表)  
	 * @param creditRecord   
	 * void 
	 * @create time： Nov 25, 2015 7:47:31 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertUserCreditRecord(UserCreditRecord creditRecord);

	/** 
	* @Title updateUserCreditRecord 
	* @Description TODO(调用征信接口成功，根据返回结果更新征信状态) 
	* @param 参数列表
	* @param creditRecord 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public void updateUserCreditRecord(UserCreditRecord creditRecord);

	/** 
	* @Title queryExistInstalmentByTel 
	* @Description TODO(根据用户手机下单，未还清并且下单时间在90天内的不可以继续下单) 
	* @param 参数列表
	* @param instalmentMap key：手机号
	* @return 设定文件 
	* @return  ArrayList<Map<String,String>>	返回类型 
	* @throws 
	*/
	public ArrayList<Map<String,String>> queryExistInstalmentByTel(Map<String, Object> instalmentMap);

	/** 
	* @Title queryWhiteList 
	* @Description TODO(查询白名单) 
	* @param 参数列表
	* @param map
	* @return 设定文件 
	* @return int	返回类型 
	* @throws 
	*/
	public int queryWhiteList(Map<String, String> map);
	
	/** 
	 * @Title queryWhiteList 
	 * @Description TODO(查询白名单) 
	 * @param 参数列表
	 * @param map
	 * @return 设定文件 
	 * @return int	返回类型 
	 * @throws 
	 */
	public WhiteList querySchoolWhiteList(Map<String, String> map);

	/** 
	* @Title queryBlackList 
	* @Description TODO(查询黑名单) 
	* @param 参数列表
	* @param map
	* @return 设定文件 
	* @return BlackList	返回类型 
	* @throws 
	*/
	public BlackList queryBlackList(Map<String, String> map) throws Exception;

	/** 
	* @Title insertCreditModelRecord 
	* @Description TODO(增加征信模快信息) 
	* @param 参数列表
	* @param record 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public void insertCreditModelRecord(UserCreditModelRecord record);

	/** 
	* @Title updateUserCreditRecordByInfoId 
	* @Description TODO(更新征信记录表信息) 
	* @param 参数列表
	* @param creditRecord 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public void updateUserCreditRecordByInfoId(UserCreditRecord creditRecord);
	/** 
	* @Title queryIllegalCount 
	* @Description TODO(查询非法姓名配置表) 
	* @param 参数列表
	* @param name
	* @return 设定文件 
	* @return int	返回类型 
	* @throws 
	*/
	public int queryIllegalCount(String name);		
	/** 
	* @Title updateUserCreditRequestRecordCount 
	* @Description TODO(更新国政通查询次数记录) 
	* @param 参数列表
	* @param key：身份证号
	* @return 设定文件 
	* @return int	返回类型 
	* @throws 
	*/
	public void updateUserCreditRequestRecordCount(UserCreditInfo userCreditInfo);

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
	 * queryIsBlackUser(查询是否命中黑名单)  
	 * @param idCard
	 * @param name
	 * @return   
	 * UserCreditRecord
	 * @create time： 2016年3月30日 下午5:28:47 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserCreditRecord queryBlackUser(Map map);

	/**  
	 * queryExist91LoanList(查询91征信记录)  
	 * @param queryMap
	 * @return   
	 * List<UserJyzxRecordItem> 
	 * @create time： 2016年7月13日 下午8:12:06 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserJyzxRecordItem> queryExist91LoanList(Map<String, Object> queryMap);

	/**  
	 * queryExistQhBlackListData(是否存在前海黑名单数据)  
	 * @param queryMap
	 * @return   
	 * List<UserQhzxBlackList> 
	 * @create time： 2016年7月13日 下午8:57:50 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserQhzxBlackList> queryExistQhBlackListData(Map<String, Object> queryMap);

	/**  
	 * queryExistBrBlackListData(查询百融征信信息)  
	 * @param brRequestPo
	 * @return   
	 * List<UserBrSpeciallistC> 
	 * @create time： 2016年7月13日 下午9:26:22 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserBrSpeciallistC> queryExistBrBlackListData(UserBrRequest brRequestPo);

	/**  
	 * queryExistQhLoanList(查询是否存在前海常贷客信息 )  
	 * @param userName
	 * @param idCard
	 * @return   
	 * List<UserQhzxLoan> 
	 * @create time： 2016年7月14日 上午9:45:37 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserQhzxLoan> queryExistQhLoanList(Map<String,String> map);

	/**  
	 * queryExistBrApplyList(查询是否存在百融多次申请信息)  
	 * @param brRequestPo
	 * @return   
	 * List<BrApplyLoan> 
	 * @create time： 2016年7月14日 上午10:26:25 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<BrApplyLoan> queryExistBrApplyList(UserBrRequest brRequestPo);
	
	/**  
	 * updateBusApplicationOfReason(修改业务表征信结果)  
	 * @param queryMap
	 * @return   
	 * @create time： 2016年7月14日 上午10:26:25 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateBusApplicationOfReason(Map<String, Object> queryMap);

	/**  
	 * queryJyRecordId(查询91主表主键)  
	 * @param trxNo
	 * @return   
	 * String 
	 * @create time： 2016年8月4日 下午3:16:16 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String queryJyRecordId(String trxNo);
}
