package com.tera.car.model;

import com.tera.util.DateUtils;

/**
 * 
 * 车辆信息表实体类
 * <b>功能：</b>CarInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-11 17:13:38<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CarInfo {

	//属性部分
	private int id; //主键
	private String appId; //申请ID
	private String carBrand; //车辆品牌型号
	private String licensePlate; //车牌号码
	private String carYearlyLimit; //车辆年限
	private String engineNumber; //发动机号
	private String mileage; //里程
	private String vehicleIdentificationNumber; //车架号
	private String transferNum; //过户次数
	private double ticketPrice; //开票价
	private double marketPrice; //同类市场价格
	private java.util.Date productionDate; //出厂日期
	private String productionDateStr; //出厂日期
	private java.util.Date registerDate; //登记日期
	private String registerDateStr; //登记日期
	private java.util.Date purchaseDate; //购买日期
	private String purchaseDateStr; //购买日期
	private String state; //状态
	private String orgId; // 所属机构
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
	public String getCarBrand () {
		return this.carBrand;
	}
	public String getLicensePlate () {
		return this.licensePlate;
	}
	public String getCarYearlyLimit () {
		return this.carYearlyLimit;
	}
	public String getEngineNumber () {
		return this.engineNumber;
	}
	public String getMileage () {
		return this.mileage;
	}
	public String getVehicleIdentificationNumber () {
		return this.vehicleIdentificationNumber;
	}
	public String getTransferNum () {
		return this.transferNum;
	}
	public double getTicketPrice () {
		return this.ticketPrice;
	}
	public double getMarketPrice () {
		return this.marketPrice;
	}
	public java.util.Date getProductionDate () {
		return this.productionDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getProductionDateStr () {
		return DateUtils.formatDate(this.productionDate);
	}
	public java.util.Date getRegisterDate () {
		return this.registerDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRegisterDateStr () {
		return DateUtils.formatDate(this.registerDate);
	}
	public java.util.Date getPurchaseDate () {
		return this.purchaseDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getPurchaseDateStr () {
		return DateUtils.formatDate(this.purchaseDate);
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
	public void setCarBrand (String carBrand) {
		this.carBrand=carBrand;
	}
	public void setLicensePlate (String licensePlate) {
		this.licensePlate=licensePlate;
	}
	public void setCarYearlyLimit (String carYearlyLimit) {
		this.carYearlyLimit=carYearlyLimit;
	}
	public void setEngineNumber (String engineNumber) {
		this.engineNumber=engineNumber;
	}
	public void setMileage (String mileage) {
		this.mileage=mileage;
	}
	public void setVehicleIdentificationNumber (String vehicleIdentificationNumber) {
		this.vehicleIdentificationNumber=vehicleIdentificationNumber;
	}
	public void setTransferNum (String transferNum) {
		this.transferNum=transferNum;
	}
	public void setTicketPrice (double ticketPrice) {
		this.ticketPrice=ticketPrice;
	}
	public void setMarketPrice (double marketPrice) {
		this.marketPrice=marketPrice;
	}
	public void setProductionDate (java.util.Date productionDate) {
		this.productionDate=productionDate;
	}
	public void setProductionDateStr (String productionDateStr) {
		this.productionDateStr=productionDateStr;
	}
	public void setRegisterDate (java.util.Date registerDate) {
		this.registerDate=registerDate;
	}
	public void setRegisterDateStr (String registerDateStr) {
		this.registerDateStr=registerDateStr;
	}
	public void setPurchaseDate (java.util.Date purchaseDate) {
		this.purchaseDate=purchaseDate;
	}
	public void setPurchaseDateStr (String purchaseDateStr) {
		this.purchaseDateStr=purchaseDateStr;
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

