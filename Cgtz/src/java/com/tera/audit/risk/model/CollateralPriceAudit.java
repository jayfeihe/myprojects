package com.tera.audit.risk.model;

import com.tera.util.DateUtils;

/**
 * 
 * 核价表实体类
 * <b>功能：</b>CollateralPriceAuditDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-09 18:57:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollateralPriceAudit {

	//属性部分
	private int id; //ID
	private int collateralId; //抵押物ID
	private double amt; //金额
	private String result; //核价结果
	private String remark; //核价说明
	private String operUid; //操作人
	private java.sql.Timestamp operTime; //操作时间
	private String operTimeStr; //操作时间
	
	
	private String operName; // 操作人

	//getter部分
	public int getId () {
		return this.id;
	}
	public int getCollateralId () {
		return this.collateralId;
	}
	public double getAmt () {
		return this.amt;
	}
	public String getResult () {
		return this.result;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getOperUid () {
		return this.operUid;
	}
	public java.sql.Timestamp getOperTime () {
		return this.operTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getOperTimeStr () {
		return DateUtils.formatTime(this.operTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setCollateralId (int collateralId) {
		this.collateralId=collateralId;
	}
	public void setAmt (double amt) {
		this.amt=amt;
	}
	public void setResult (String result) {
		this.result=result;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setOperUid (String operUid) {
		this.operUid=operUid;
	}
	public void setOperTime (java.sql.Timestamp operTime) {
		this.operTime=operTime;
	}
	public void setOperTimeStr (String operTimeStr) {
		this.operTimeStr=operTimeStr;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
}

