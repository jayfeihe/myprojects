package com.tera.feature.asset.model;

import com.tera.util.DateUtils;

/**
 * 
 * 价值变动表实体类
 * <b>功能：</b>CollateralPriceChangeDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 11:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollateralPriceChange {

	//属性部分
	private int id; //ID
	private int collateralId; //抵押物ID
	private double newAmt; //变动金额
	private String remark; //变动说明
	private String operUid; //操作人
	private java.sql.Timestamp operTime; //操作时间
	private String operTimeStr; //操作时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public int getCollateralId () {
		return this.collateralId;
	}
	public double getNewAmt () {
		return this.newAmt;
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
	public void setNewAmt (double newAmt) {
		this.newAmt=newAmt;
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

}

