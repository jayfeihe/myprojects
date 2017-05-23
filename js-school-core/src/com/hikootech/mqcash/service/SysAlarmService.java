package com.hikootech.mqcash.service;

import java.math.BigDecimal;
import java.util.Map;

import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.SysAlarm;
import com.hikootech.mqcash.po.SysAlarmRule;

/** 
* @ClassName SysAlarmService 
* @Description TODO(系统告警service) 
* @author HAI DA
* @date 2016年3月2日 上午11:30:04 
*  
*/
public interface SysAlarmService {
	
	/** 
	* @Title insertSysAlarmPo 
	* @Description TODO(这里用一句话描述这个方法的作用) 
	* @param 参数列表
	* @param alarmSystem 哪个系统发出的告警
	* @param alarmLevel	告警等级 低中高
	* @param alarmNoticeType  告警通知类型， 邮件，短信，邮件短信
	* @param alarmContent		告警内容
	* @param sendStatus			告警发送状态
	* @return
	* @throws Exception 设定文件 
	* @return int	返回类型 
	* @throws 
	*/
	public int insertSysAlarmPo(String alarmSystem,String alarmLevel,String alarmNoticeType ,String alarmContent,int emailType ,int sendStatus) throws Exception ;
	/** 
	* @Title querySysAlarmRule 
	* @Description TODO(根据规则类型，查询业务告警规则配置数据) 
	* @param 参数列表
	* @param ruleType
	* @return
	* @throws Exception 设定文件 
	* @return SysAlarmRule	返回类型 
	* @throws 
	*/
	public SysAlarmRule querySysAlarmRule(int creditSucRate) throws Exception ;

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
	
	/** 
	* @Title exceptionAlarm 
	* @Description TODO(插入告警表并返回异常) 
	* @param 参数列表
	* @param alarmContent
	* @return 设定文件 
	* @return Map<String,String>	返回类型 
	* @throws 
	*/
	public Map<String, String>  exceptionAlarm(String alarmContent);
	/** 
	* @Title alarm 
	* @Description TODO(告警) 
	* @param 参数列表
	* @param alarmContent
	* @return 设定文件 
	* @throws 
	*/
	public void  alarm(String alarmContent);
	
	/** 
	* @Title insertSysAlarm 
	* @Description 新增告警
	* @param 参数列表 
	* @param sysAlarm
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void insertSysAlarm(SysAlarm sysAlarm) throws MQException;
	
	/** 
	* @Title inwardCollectionAlarm 
	* @Description 代收失败告警
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> inwardCollectionAlarm(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title Alarm 
	* @Description 告警
	* @param 参数列表 
	* @param sysAlarm
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> Alarm(SysAlarm sysAlarm) throws MQException;
	
	/** 
	* @Title calOverdueFailedAlarm 
	* @Description 新增计算逾期罚息失败告警
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> calOverdueFailedAlarm(Map<String, Object> paramMap) throws MQException; 
	
}
