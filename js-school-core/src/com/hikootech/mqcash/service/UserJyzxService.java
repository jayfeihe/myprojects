package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hikootech.mqcash.po.UserJyzxRecord;
import com.hikootech.mqcash.po.UserJyzxRecordItem;
import com.xiaocui.entity.Pkg3002;

public interface UserJyzxService {

	/** 
	* @Title requestJyzx 
	* @Description TODO(请求91征信) 
	* @param 参数列表
	* @param record 设定文件 
	* @return UserJyzxRecord	返回类型 
	* @throws 
	*/
	public UserJyzxRecord requestJyzx(Map<String, String> map) ;
	public void addJyzxRecordItem(UserJyzxRecordItem recordItem);
	public List<UserJyzxRecordItem> addJyzxRecordItems(Pkg3002 pkg3002);
	
	/** 
	* modifyThirdPartCreditStatus<br/> 
	*  TODO(根据91返回征信结果状态，更改业务表数据状态) 
	* @param map void
	* @author zhaohefei
	* @2015年12月27日
	* @return void	返回类型 
	*/
	public void modifyThirdPartCreditStatus(String totalId, boolean creditStatus);
	public String queryTotalInfoId(String trxNo);
	
	/** 
	* modifyJyRecordResultStatus<br/> 
	*  TODO(根据交易流水号修改结果状态) 
	* @param record
	* @return int
	* @author zhaohefei
	* @2016年1月6日
	* @return int	返回类型 
	*/
	public int modifyJyRecordResultStatus(UserJyzxRecord record);
	/** 
	* @Title receiveCreditDataResult 
	* @Description TODO(获得91的征信结果) 
	* @param 参数列表 设定文件 
	* @return byte[] 	返回类型 
	* @throws 
	*/
	public byte[]  receiveCreditDataResult(HttpServletRequest request,String companyCode) throws Exception;
	
	/** 
	* @Title modifyCreditRecord 
	* @Description TODO(修改征信记录表信息) 
	* @param 参数列表
	* @param totalId
	* @param creditResult 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean modifyCreditRecord(String totalId, boolean creditResult);
	
	/** 
	* @Title queryJyCreditResult 
	* @Description TODO(查询91征信结果) 
	* @param 参数列表
	* @param pkg3002
	* @return 设定文件 
	* @return boolean	返回类型 
	* @throws 
	*/
	public boolean queryJyCreditResult(Pkg3002 pkg3002) ;
	
	/**  
	 * judgeStudentCreditInfos(判断学生的91征信是否通过)  
	 * @param totalId
	 * @param pkg3002   
	 * void 
	 * @create time： 2016年7月15日 下午1:36:53 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, Object> judgeStudentCreditInfos(String totalId, UserJyzxRecord jyzxRecord, List<UserJyzxRecordItem>  pkg3002);
	
	/**  
	 * jyStudentCreditRule(江苏校园，根据91征信规则，判断91征信结果)  
	 * @param pkg3002
	 * @return   
	 * boolean 
	 * @create time： 2016年7月15日 下午3:28:15 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public boolean jyStudentCreditRule(List<UserJyzxRecordItem> pkg3002);
	
	/**  
	* @Description TODO(修改征信记录表信息) 
	 * @param busId
	 * @param creditResult
	 * @return   
	 * boolean 
	 * @create time： 2016年8月4日 上午10:42:59 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public boolean modifySchoolCreditRecord(String busId, UserJyzxRecord jyzxRecord, List<UserJyzxRecordItem> pkg3002);
	
	/** 
	* @Title queryLastestEffectJyzxRecord 
	* @Description 根据用户身份证和姓名查询最新的91征信请求信息
	* @param 参数列表 
	* @param idCard
	* @param name
	* @return 返回类型 UserJyzxRecord	
	*/
	public UserJyzxRecord queryLastestEffectJyzxRecord(String idCard, String name);
	
	/** 
	* @Title queryUserJyzxRecordItemsByTrxNo 
	* @Description 根据trxNo查询91征信结果信息
	* @param 参数列表 
	* @param trxNo
	* @return 
	* @return 返回类型 List<UserJyzxRecordItem>	
	*/
	public List<UserJyzxRecordItem> queryUserJyzxRecordItemsByTrxNo(String trxNo);
	
}
