package com.tera.house.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款申请房产信息实体类
 * <b>功能：</b>houseHousingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:32<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class HouseHousing {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String address; //房产地址
	private int area; //面积
	private int isMortgage; //是否抵押
	private java.util.Date acquisitionDate; //产权获取时间
	private String acquisitionDateStr; //产权获取时间
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//为审核的影像摘要添加
//	private int num; //房号
//	public int getNum () {
//		return this.num;
//	}
//	public void setNum (int num) {
//		this.num=num;
//	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getAddress () {
		return this.address;
	}
	public int getArea () {
		return this.area;
	}
	public int getIsMortgage () {
		return this.isMortgage;
	}
	public java.util.Date getAcquisitionDate () {
		return this.acquisitionDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getAcquisitionDateStr () {
		return DateUtils.formatDate(this.acquisitionDate);
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
	public void setAddress (String address) {
		this.address=address;
	}
	public void setArea (int area) {
		this.area=area;
	}
	public void setIsMortgage (int isMortgage) {
		this.isMortgage=isMortgage;
	}
	public void setAcquisitionDate (java.util.Date acquisitionDate) {
		this.acquisitionDate=acquisitionDate;
	}
	public void setAcquisitionDateStr (String acquisitionDateStr) {
		this.acquisitionDateStr=acquisitionDateStr;
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

