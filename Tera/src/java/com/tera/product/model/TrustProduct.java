package com.tera.product.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>TrustProductDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-08 00:48:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class TrustProduct {

	//属性部分
	private int id; //ID
	private String productId; //产品
	private int period; //期限
	private String company; //机构名称
	private double interestRate; //利率
	private double startAmount; //起点金额
	private String useage; //用途
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
	public String getProductId () {
		return this.productId;
	}
	public int getPeriod () {
		return this.period;
	}
	public String getCompany () {
		return this.company;
	}
	public double getInterestRate () {
		return this.interestRate;
	}
	public double getStartAmount () {
		return this.startAmount;
	}
	public String getUseage () {
		return this.useage;
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
	public void setProductId (String productId) {
		this.productId=productId;
	}
	public void setPeriod (int period) {
		this.period=period;
	}
	public void setCompany (String company) {
		this.company=company;
	}
	public void setInterestRate (double interestRate) {
		this.interestRate=interestRate;
	}
	public void setStartAmount (double startAmount) {
		this.startAmount=startAmount;
	}
	public void setUseage (String useage) {
		this.useage=useage;
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

