package com.tera.house.model;

import com.tera.util.DateUtils;

/**
 * 
 * 房产信息表实体类
 * <b>功能：</b>HouseInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-20 17:01:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class HouseInfo {

	//属性部分
	private int id; //主键
	private String appId; //申请ID
	private String houseSpace; //房屋面积
	private String houseOrientation; //房屋朝向
	private String houseCertificateNumber; //房产证号
	private String landCertificateNumber; //土地证号
	private String houseYearlyLimit; //使用年限
	private String houseExistProvince; //房屋所在省份
	private String houseExistCity; //房屋所在城市
	private String houseExistCounty; //房屋所在市区
	private String houseExistAddress; //房屋所在地址
	private String state; //状态
	private String orgId; //所属机构
	private String operator; //操作员
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getHouseSpace () {
		return this.houseSpace;
	}
	public String getHouseOrientation () {
		return this.houseOrientation;
	}
	public String getHouseCertificateNumber () {
		return this.houseCertificateNumber;
	}
	public String getLandCertificateNumber () {
		return this.landCertificateNumber;
	}
	public String getHouseYearlyLimit () {
		return this.houseYearlyLimit;
	}
	public String getHouseExistProvince () {
		return this.houseExistProvince;
	}
	public String getHouseExistCity () {
		return this.houseExistCity;
	}
	public String getHouseExistCounty () {
		return this.houseExistCounty;
	}
	public String getHouseExistAddress () {
		return this.houseExistAddress;
	}
	public String getState () {
		return this.state;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getOperator () {
		return this.operator;
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
	public void setHouseSpace (String houseSpace) {
		this.houseSpace=houseSpace;
	}
	public void setHouseOrientation (String houseOrientation) {
		this.houseOrientation=houseOrientation;
	}
	public void setHouseCertificateNumber (String houseCertificateNumber) {
		this.houseCertificateNumber=houseCertificateNumber;
	}
	public void setLandCertificateNumber (String landCertificateNumber) {
		this.landCertificateNumber=landCertificateNumber;
	}
	public void setHouseYearlyLimit (String houseYearlyLimit) {
		this.houseYearlyLimit=houseYearlyLimit;
	}
	public void setHouseExistProvince (String houseExistProvince) {
		this.houseExistProvince=houseExistProvince;
	}
	public void setHouseExistCity (String houseExistCity) {
		this.houseExistCity=houseExistCity;
	}
	public void setHouseExistCounty (String houseExistCounty) {
		this.houseExistCounty=houseExistCounty;
	}
	public void setHouseExistAddress (String houseExistAddress) {
		this.houseExistAddress=houseExistAddress;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setOperator (String operator) {
		this.operator=operator;
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

