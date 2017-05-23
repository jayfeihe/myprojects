package com.tera.rule.model;

import java.util.ArrayList;
import java.util.List;

import com.tera.util.DateUtils;

/**
 * 
 * 规则日志实体类
 * <b>功能：</b>RuleProlessLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-09-28 11:28:18<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RuleProlessLog {

	//属性部分
	private int id; //主键
	private String appno; //申请单号
	private String callpoint; //调用节点
	private String finalruleresult; //最终规则结果
	private String exception="0"; //是否异常
	private String exceptiontype; //异常类型
	private String begintime; //开始时间
	private String endtime; //结束时间
	private long timelong; //执行时长
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	// 规则执行信息集合
	private List<RuleInfo> ruleInfoList = new ArrayList<RuleInfo>();


	/**
	 * 添加 规则执行信息
	 * 
	 * @param ruleCode
	 *            规则编码
	 * @param ruleName
	 *            规则名称
	 * @param ruleResult
	 *            规则结果
	 * @param describeInfo
	 *            提示信息
	 * @param ruleSetName
	 *            规则集名称
	 * @param ruleType
	 *            规则分类
	 */
	public void addRuleInfo(String ruleCode, String ruleName,
			String ruleResult, String describeInfo, String ruleSetName,
			String ruleType) {
		RuleInfo ruleInfo = new RuleInfo();
		ruleInfo.setRulecode(ruleCode);
		ruleInfo.setRulename(ruleName);
		ruleInfo.setRuleresult(ruleResult);
		ruleInfo.setDescribeinfo(describeInfo);
		ruleInfo.setRulesetname(ruleSetName);
		ruleInfo.setRuletype(ruleType);
		ruleInfo.setOperator(operator);
		ruleInfo.setOrgId(orgId);
		this.ruleInfoList.add(ruleInfo);
	}

	/**
	 * 添加 规则执行信息
	 * 
	 * @param ruleCode
	 *            规则编码
	 * @param ruleName
	 *            规则名称
	 * @param ruleResult
	 *            规则结果
	 * @param describeInfo
	 *            提示信息
	 */
	public void addRuleInfo(String ruleCode, String ruleName,
			String ruleResult, String describeInfo) {
		RuleInfo ruleInfo = new RuleInfo();
		ruleInfo.setRulecode(ruleCode);
		ruleInfo.setRulename(ruleName);
		ruleInfo.setRuleresult(ruleResult);
		ruleInfo.setDescribeinfo(describeInfo);
		ruleInfo.setOperator(operator);
		ruleInfo.setOrgId(orgId);
		this.ruleInfoList.add(ruleInfo);
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppno () {
		return this.appno;
	}
	public String getCallpoint () {
		return this.callpoint;
	}
	public String getFinalruleresult () {
		return this.finalruleresult;
	}
	public String getException () {
		return this.exception;
	}
	public String getExceptiontype () {
		return this.exceptiontype;
	}
	public String getBegintime () {
		return this.begintime;
	}
	public String getEndtime () {
		return this.endtime;
	}
	public long getTimelong () {
		return this.timelong;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	public List<RuleInfo> getRuleInfoList() {
		return ruleInfoList;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppno (String appno) {
		this.appno=appno;
	}
	public void setCallpoint (String callpoint) {
		this.callpoint=callpoint;
	}
	public void setFinalruleresult (String finalruleresult) {
		this.finalruleresult=finalruleresult;
	}
	public void setException (String exception) {
		this.exception=exception;
	}
	public void setExceptiontype (String exceptiontype) {
		this.exceptiontype=exceptiontype;
	}
	public void setBegintime (String begintime) {
		this.begintime=begintime;
	}
	public void setEndtime (String endtime) {
		this.endtime=endtime;
	}
	public void setTimelong (long timelong) {
		this.timelong=timelong;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}
	public void setRuleInfoList(List<RuleInfo> ruleInfoList) {
		this.ruleInfoList = ruleInfoList;
	}

}

