package com.tera.rule.model;

import com.tera.util.DateUtils;

/**
 * 
 * 规则执行日志实体类
 * <b>功能：</b>RuleInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-09-28 15:01:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RuleInfo {

	//属性部分
	private int logid; //日志ID
	private String rulecode; //规则编码
	private String rulename; //规则名称
	private String ruleresult; //规则结果
	private String describeinfo; //提示信息
	private String rulesetname; //规则集名称
	private String ruletype; //规则分类
	private String arg1; //参数1
	private String arg2; //参数2
	private String arg3; //参数3
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getLogid () {
		return this.logid;
	}
	public String getRulecode () {
		return this.rulecode;
	}
	public String getRulename () {
		return this.rulename;
	}
	public String getRuleresult () {
		return this.ruleresult;
	}
	public String getDescribeinfo () {
		return this.describeinfo;
	}
	public String getRulesetname () {
		return this.rulesetname;
	}
	public String getRuletype () {
		return this.ruletype;
	}
	public String getArg1 () {
		return this.arg1;
	}
	public String getArg2 () {
		return this.arg2;
	}
	public String getArg3 () {
		return this.arg3;
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
	public void setLogid (int logid) {
		this.logid=logid;
	}
	public void setRulecode (String rulecode) {
		this.rulecode=rulecode;
	}
	public void setRulename (String rulename) {
		this.rulename=rulename;
	}
	public void setRuleresult (String ruleresult) {
		this.ruleresult=ruleresult;
	}
	public void setDescribeinfo (String describeinfo) {
		this.describeinfo=describeinfo;
	}
	public void setRulesetname (String rulesetname) {
		this.rulesetname=rulesetname;
	}
	public void setRuletype (String ruletype) {
		this.ruletype=ruletype;
	}
	public void setArg1 (String arg1) {
		this.arg1=arg1;
	}
	public void setArg2 (String arg2) {
		this.arg2=arg2;
	}
	public void setArg3 (String arg3) {
		this.arg3=arg3;
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

}

