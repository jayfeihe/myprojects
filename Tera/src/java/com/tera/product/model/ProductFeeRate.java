package com.tera.product.model;

import com.tera.util.DateUtils;

/**
 * 
 * 产品服务费率表实体类
 * <b>功能：</b>ProductFeeRateDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-11 14:28:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ProductFeeRate {

	//属性部分
	private int id; //ID
	private String name; //产品名称
	private int period; //期限
	private int periodNum; //期数
	private double sreviceFeeReduceRate; //费率减免率
	private String remark; //备注
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
	public String getName () {
		return this.name;
	}
	public int getPeriod () {
		return this.period;
	}
	public int getPeriodNum () {
		return this.periodNum;
	}
	public double getSreviceFeeReduceRate () {
		return this.sreviceFeeReduceRate;
	}
	public String getRemark () {
		return this.remark;
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
	public void setName (String name) {
		this.name=name;
	}
	public void setPeriod (int period) {
		this.period=period;
	}
	public void setPeriodNum (int periodNum) {
		this.periodNum=periodNum;
	}
	public void setSreviceFeeReduceRate (double sreviceFeeReduceRate) {
		this.sreviceFeeReduceRate=sreviceFeeReduceRate;
	}
	public void setRemark (String remark) {
		this.remark=remark;
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

