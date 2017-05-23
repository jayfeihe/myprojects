package com.tera.accounting.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>AccounttingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-24 17:24:36<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Accountting {

	//属性部分
	private int id; //ID
	private String inOut; //收付
	private String account; //账户
	private String contractNo; //合同号
	private String subject; //科目
	private double planAmount; //计划金额
	private double actualAmount; //实际金额
	private String source; //来源
	private int periodNum; //期数
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getInOut () {
		return this.inOut;
	}
	public String getAccount () {
		return this.account;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getSubject () {
		return this.subject;
	}
	public double getPlanAmount () {
		return this.planAmount;
	}
	public double getActualAmount () {
		return this.actualAmount;
	}
	public String getSource () {
		return this.source;
	}
	public int getPeriodNum () {
		return this.periodNum;
	}
	public String getState () {
		return this.state;
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
	public void setId (int id) {
		this.id=id;
	}
	public void setInOut (String inOut) {
		this.inOut=inOut;
	}
	public void setAccount (String account) {
		this.account=account;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setSubject (String subject) {
		this.subject=subject;
	}
	public void setPlanAmount (double planAmount) {
		this.planAmount=planAmount;
	}
	public void setActualAmount (double actualAmount) {
		this.actualAmount=actualAmount;
	}
	public void setSource (String source) {
		this.source=source;
	}
	public void setPeriodNum (int periodNum) {
		this.periodNum=periodNum;
	}
	public void setState (String state) {
		this.state=state;
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

