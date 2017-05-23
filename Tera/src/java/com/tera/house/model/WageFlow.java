package com.tera.house.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款申请人工资流水实体类
 * <b>功能：</b>WageFlowDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-06 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class WageFlow {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String type; //类型
	private double n0Amount; //N0金额
	private double n1Amount; //N1金额
	private double n2Amount; //N2金额
	private double n3Amount; //N3金额
	private double n4Amount; //N4金额
	private double n5Amount; //N5金额
	private String n0Check="0"; //N0选择	1选中0未选中
	private String n1Check="0"; //N1选择	1选中0未选中
	private String n2Check="0"; //N2选择	1选中0未选中
	private String n3Check="0"; //N3选择	1选中0未选中
	private String n4Check="0"; //N4选择	1选中0未选中
	private String n5Check="0"; //N5选择	1选中0未选中
	private double restAmount; //余额
	private double avgAmount; //均值
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
	public String getAppId () {
		return this.appId;
	}
	public String getType () {
		return this.type;
	}
	public double getN0Amount () {
		return this.n0Amount;
	}
	public double getN1Amount () {
		return this.n1Amount;
	}
	public double getN2Amount () {
		return this.n2Amount;
	}
	public double getN3Amount () {
		return this.n3Amount;
	}
	public double getN4Amount () {
		return this.n4Amount;
	}
	public double getN5Amount () {
		return this.n5Amount;
	}
	public String getN0Check () {
		return this.n0Check;
	}
	public String getN1Check () {
		return this.n1Check;
	}
	public String getN2Check () {
		return this.n2Check;
	}
	public String getN3Check () {
		return this.n3Check;
	}
	public String getN4Check () {
		return this.n4Check;
	}
	public String getN5Check () {
		return this.n5Check;
	}
	public double getRestAmount () {
		return this.restAmount;
	}
	public double getAvgAmount () {
		return this.avgAmount;
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
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setN0Amount (double n0Amount) {
		this.n0Amount=n0Amount;
	}
	public void setN1Amount (double n1Amount) {
		this.n1Amount=n1Amount;
	}
	public void setN2Amount (double n2Amount) {
		this.n2Amount=n2Amount;
	}
	public void setN3Amount (double n3Amount) {
		this.n3Amount=n3Amount;
	}
	public void setN4Amount (double n4Amount) {
		this.n4Amount=n4Amount;
	}
	public void setN5Amount (double n5Amount) {
		this.n5Amount=n5Amount;
	}
	public void setN0Check (String n0Check) {
		this.n0Check=n0Check;
	}
	public void setN1Check (String n1Check) {
		this.n1Check=n1Check;
	}
	public void setN2Check (String n2Check) {
		this.n2Check=n2Check;
	}
	public void setN3Check (String n3Check) {
		this.n3Check=n3Check;
	}
	public void setN4Check (String n4Check) {
		this.n4Check=n4Check;
	}
	public void setN5Check (String n5Check) {
		this.n5Check=n5Check;
	}
	public void setRestAmount (double restAmount) {
		this.restAmount=restAmount;
	}
	public void setAvgAmount (double avgAmount) {
		this.avgAmount=avgAmount;
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

