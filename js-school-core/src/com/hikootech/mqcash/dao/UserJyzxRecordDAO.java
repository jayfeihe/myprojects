package com.hikootech.mqcash.dao;

import java.util.Map;

import com.hikootech.mqcash.po.UserJyzxRecord;

public interface UserJyzxRecordDAO {

	public void addUserJyzxRecord(UserJyzxRecord record);
	
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
	* @Title queryCallType 
	* @Description TODO(查询调用类型) 
	* @param 参数列表
	* @param trxNo
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String queryCallType(String trxNo);

	/**  
	 * query91DiffOrgConfig(91不同机构申请次数查询)  
	 * @param map
	 * @return   
	 * String 
	 * @create time： 2016年5月31日 下午3:33:47 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public String query91DiffOrgConfig(Map<String, String> map);
	
	public UserJyzxRecord queryUserJyzxRecordByTrxNo(String trxNo);
	
	public UserJyzxRecord queryLastestEffectiveJyzxRecord(Map<String, String> map);
	
	public UserJyzxRecord queryUserJyzxRecordByBusId(String busId);
}
