package com.hikootech.mqcash.dao;

import java.util.Map;

import com.hikootech.mqcash.po.SysAlarm;
import com.hikootech.mqcash.po.SysAlarmRule;

/** 
* @ClassName SysAlarmDAO 
* @Description TODO(系统告警dao) 
* @author HAI DA
* @date 2016年3月2日 上午11:30:04 
*  
*/
public interface SysAlarmDAO {
	
	/** 
	* @Title insertSysAlarmPo 
	* @Description TODO(插入告警表) 
	* @param 参数列表
	* @param sysAlarm
	* @return
	* @throws Exception 设定文件 
	* @return int	返回类型 
	* @throws 
	*/
	public int insertSysAlarmPo(SysAlarm sysAlarm) throws Exception;
	/** 
	* @Title querySysAlarmRule 
	* @Description TODO(查询规则配置参数) 
	* @param 参数列表
	* @param ruleType
	* @return 设定文件 
	* @return SysAlarmRule	返回类型 
	* @throws 
	*/
	public SysAlarmRule querySysAlarmRule(int ruleType);

	/** 
	* @Title queryCreditSucList 
	* @Description TODO(根据监控时段，查询该时段征信通过率) 
	* @param 参数列表
	* @param map
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String queryCreditSucList(Map<String, Object> map);
	/** 
	* @Title queryBindingCardList 
	* @Description TODO(根据监控时段，查询该时绑卡为绑定中的概率) 
	* @param 参数列表
	* @param map
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String queryBindingCardList(Map<String, Object> map);
	/** 
	* @Title queryOrderFinishList 
	* @Description TODO(查询下单完成率) 
	* @param 参数列表
	* @param map
	* @return 设定文件 
	* @return String	返回类型 
	* @throws 
	*/
	public String queryOrderFinishList(Map<String, Object> map);

}
