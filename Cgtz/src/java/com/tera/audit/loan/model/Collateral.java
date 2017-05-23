package com.tera.audit.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * 质押、抵押物信息实体类
 * <b>功能：</b>CollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Collateral {
	
	//属性部分
	private int id; //ID
	private String loanId; //申请ID
	private String type; //类型
	private String var; //品种
	private String size; //规格
	private String remark; //说明
	private String carType; //车辆型号
	private String license; //车牌号
	private String carAge; //车辆年限
	private String engCode; //发动机号
	private String mile; //里程
	private String frameCode; //车架号
	private double billPrice; //开票价格
	private String tranTimes; //过户次数
	private java.util.Date proDate; //出厂日期
	private String proDateStr; //出厂日期
	private java.util.Date buyDate; //购买日期
	private String buyDateStr; //购买日期
	private String housePropertyCode; //房产证号
	private String landCode; //土地证号
	private String area; //房屋面积
	private String direction; //房屋朝向
	private String prvn; //所在省
	private String city; //所在市
	private String ctry; //所在县
	private String addr; //所在地址
	private double evalPrice; //评估金额
	private String evalName; //评估者
	private String evalRemark; //评估说明
	private String isSet; //担保物权是否设定
	private int warehouseId; //资产所在仓库
	private String assetRemark; //资产说明
	private String assetAddr; //资产所在地址（仓库写仓库地址，房子就是自己地址）
	private String auditPriceState; //最新核价结果    0:未处理,1:相符,2:不相符
	private String auditPriceRemark; //核价说明
	private java.sql.Timestamp auditPriceTime; //核价时间
	private String auditPriceTimeStr; //核价时间
	private String auditPriceUid; //核价人
	private String isValueChange; //价值是否变动（1是，0否）
	private double latestPrice; //最新金额
	private String changeRemark; //变动说明
	private java.sql.Timestamp changeTime; //变动时间
	private String changeTimeStr; //变动时间
	private String changeUid; //变动操作人
	private String latestCheck; //最新检查情况
	private java.sql.Timestamp checkTime; //检查时间
	private String checkTimeStr; //检查时间
	private String checkRemark; //检查说明
	private String checkUid; //检查操作人
	private String state; //状态
	private double sellAmt; //处置金额
	private String sellInputUid; //处置录入人
	private String sellWay; //处置渠道
	private String sellOrg; //处置负责部门
	private String sellRemark; //处置说明
	private java.sql.Timestamp sellTime; //处置时间
	private String sellTimeStr; //处置时间
	private String createUid; //
	private java.sql.Timestamp createTime; //
	private String createTimeStr; //
	private String updateUid; //
	private java.sql.Timestamp updateTime; //
	private String updateTimeStr; //
	private String reserveDes; // 保管物品说明

	private String warehousePrvn; //仓库所在省-显示用
	private String warehouseCity; // 仓库所在市-显示用
	private String warehouseName; // 仓库名-显示用
	
	private String isOrig; // 是否是原先申请标识
	
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getType () {
		return this.type;
	}
	public String getVar () {
		return this.var;
	}
	public String getSize () {
		return this.size;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getCarType () {
		return this.carType;
	}
	public String getLicense () {
		return this.license;
	}
	public String getCarAge () {
		return this.carAge;
	}
	public String getEngCode () {
		return this.engCode;
	}
	public String getMile () {
		return this.mile;
	}
	public String getFrameCode () {
		return this.frameCode;
	}
	public double getBillPrice () {
		return this.billPrice;
	}
	public String getTranTimes () {
		return this.tranTimes;
	}
	public java.util.Date getProDate () {
		return this.proDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getProDateStr () {
		return DateUtils.formatDate(this.proDate);
	}
	public java.util.Date getBuyDate () {
		return this.buyDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getBuyDateStr () {
		return DateUtils.formatDate(this.buyDate);
	}
	public String getHousePropertyCode () {
		return this.housePropertyCode;
	}
	public String getLandCode () {
		return this.landCode;
	}
	public String getArea () {
		return this.area;
	}
	public String getDirection () {
		return this.direction;
	}
	public String getPrvn () {
		return this.prvn;
	}
	public String getCity () {
		return this.city;
	}
	public String getCtry () {
		return this.ctry;
	}
	public String getAddr () {
		return this.addr;
	}
	public double getEvalPrice () {
		return this.evalPrice;
	}
	public String getEvalName () {
		return this.evalName;
	}
	public String getEvalRemark () {
		return this.evalRemark;
	}
	public String getIsSet () {
		return this.isSet;
	}
	public int getWarehouseId () {
		return this.warehouseId;
	}
	public String getAssetRemark () {
		return this.assetRemark;
	}
	public String getAssetAddr () {
		return this.assetAddr;
	}
	public String getAuditPriceState () {
		return this.auditPriceState;
	}
	public String getAuditPriceRemark () {
		return this.auditPriceRemark;
	}
	public java.sql.Timestamp getAuditPriceTime () {
		return this.auditPriceTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getAuditPriceTimeStr () {
		return DateUtils.formatTime(this.auditPriceTime);
	}
	public String getAuditPriceUid () {
		return this.auditPriceUid;
	}
	public String getIsValueChange () {
		return this.isValueChange;
	}
	public double getLatestPrice () {
		return this.latestPrice;
	}
	public String getChangeRemark () {
		return this.changeRemark;
	}
	public java.sql.Timestamp getChangeTime () {
		return this.changeTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getChangeTimeStr () {
		return DateUtils.formatTime(this.changeTime);
	}
	public String getChangeUid () {
		return this.changeUid;
	}
	public String getLatestCheck () {
		return this.latestCheck;
	}
	public java.sql.Timestamp getCheckTime () {
		return this.checkTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCheckTimeStr () {
		return DateUtils.formatTime(this.checkTime);
	}
	public String getCheckRemark () {
		return this.checkRemark;
	}
	public String getCheckUid () {
		return this.checkUid;
	}
	public String getState () {
		return this.state;
	}
	public double getSellAmt () {
		return this.sellAmt;
	}
	public String getSellInputUid () {
		return this.sellInputUid;
	}
	public String getSellWay () {
		return this.sellWay;
	}
	public String getSellOrg () {
		return this.sellOrg;
	}
	public String getSellRemark () {
		return this.sellRemark;
	}
	public java.sql.Timestamp getSellTime () {
		return this.sellTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getSellTimeStr () {
		return DateUtils.formatTime(this.sellTime);
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateUid () {
		return this.updateUid;
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
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setVar (String var) {
		this.var=var;
	}
	public void setSize (String size) {
		this.size=size;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setCarType (String carType) {
		this.carType=carType;
	}
	public void setLicense (String license) {
		this.license=license;
	}
	public void setCarAge (String carAge) {
		this.carAge=carAge;
	}
	public void setEngCode (String engCode) {
		this.engCode=engCode;
	}
	public void setMile (String mile) {
		this.mile=mile;
	}
	public void setFrameCode (String frameCode) {
		this.frameCode=frameCode;
	}
	public void setBillPrice (double billPrice) {
		this.billPrice=billPrice;
	}
	public void setTranTimes (String tranTimes) {
		this.tranTimes=tranTimes;
	}
	public void setProDate (java.util.Date proDate) {
		this.proDate=proDate;
	}
	public void setProDateStr (String proDateStr) {
		this.proDateStr=proDateStr;
	}
	public void setBuyDate (java.util.Date buyDate) {
		this.buyDate=buyDate;
	}
	public void setBuyDateStr (String buyDateStr) {
		this.buyDateStr=buyDateStr;
	}
	public void setHousePropertyCode (String housePropertyCode) {
		this.housePropertyCode=housePropertyCode;
	}
	public void setLandCode (String landCode) {
		this.landCode=landCode;
	}
	public void setArea (String area) {
		this.area=area;
	}
	public void setDirection (String direction) {
		this.direction=direction;
	}
	public void setPrvn (String prvn) {
		this.prvn=prvn;
	}
	public void setCity (String city) {
		this.city=city;
	}
	public void setCtry (String ctry) {
		this.ctry=ctry;
	}
	public void setAddr (String addr) {
		this.addr=addr;
	}
	public void setEvalPrice (double evalPrice) {
		this.evalPrice=evalPrice;
	}
	public void setEvalName (String evalName) {
		this.evalName=evalName;
	}
	public void setEvalRemark (String evalRemark) {
		this.evalRemark=evalRemark;
	}
	public void setIsSet (String isSet) {
		this.isSet=isSet;
	}
	public void setWarehouseId (int warehouseId) {
		this.warehouseId=warehouseId;
	}
	public void setAssetRemark (String assetRemark) {
		this.assetRemark=assetRemark;
	}
	public void setAssetAddr (String assetAddr) {
		this.assetAddr=assetAddr;
	}
	public void setAuditPriceState (String auditPriceState) {
		this.auditPriceState=auditPriceState;
	}
	public void setAuditPriceRemark (String auditPriceRemark) {
		this.auditPriceRemark=auditPriceRemark;
	}
	public void setAuditPriceTime (java.sql.Timestamp auditPriceTime) {
		this.auditPriceTime=auditPriceTime;
	}
	public void setAuditPriceTimeStr (String auditPriceTimeStr) {
		this.auditPriceTimeStr=auditPriceTimeStr;
	}
	public void setAuditPriceUid (String auditPriceUid) {
		this.auditPriceUid=auditPriceUid;
	}
	public void setIsValueChange (String isValueChange) {
		this.isValueChange=isValueChange;
	}
	public void setLatestPrice (double latestPrice) {
		this.latestPrice=latestPrice;
	}
	public void setChangeRemark (String changeRemark) {
		this.changeRemark=changeRemark;
	}
	public void setChangeTime (java.sql.Timestamp changeTime) {
		this.changeTime=changeTime;
	}
	public void setChangeTimeStr (String changeTimeStr) {
		this.changeTimeStr=changeTimeStr;
	}
	public void setChangeUid (String changeUid) {
		this.changeUid=changeUid;
	}
	public void setLatestCheck (String latestCheck) {
		this.latestCheck=latestCheck;
	}
	public void setCheckTime (java.sql.Timestamp checkTime) {
		this.checkTime=checkTime;
	}
	public void setCheckTimeStr (String checkTimeStr) {
		this.checkTimeStr=checkTimeStr;
	}
	public void setCheckRemark (String checkRemark) {
		this.checkRemark=checkRemark;
	}
	public void setCheckUid (String checkUid) {
		this.checkUid=checkUid;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setSellAmt (double sellAmt) {
		this.sellAmt=sellAmt;
	}
	public void setSellInputUid (String sellInputUid) {
		this.sellInputUid=sellInputUid;
	}
	public void setSellWay (String sellWay) {
		this.sellWay=sellWay;
	}
	public void setSellOrg (String sellOrg) {
		this.sellOrg=sellOrg;
	}
	public void setSellRemark (String sellRemark) {
		this.sellRemark=sellRemark;
	}
	public void setSellTime (java.sql.Timestamp sellTime) {
		this.sellTime=sellTime;
	}
	public void setSellTimeStr (String sellTimeStr) {
		this.sellTimeStr=sellTimeStr;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}
	public String getReserveDes() {
		return reserveDes;
	}
	public void setReserveDes(String reserveDes) {
		this.reserveDes = reserveDes;
	}
	public String getWarehousePrvn() {
		return warehousePrvn;
	}
	public void setWarehousePrvn(String warehousePrvn) {
		this.warehousePrvn = warehousePrvn;
	}
	public String getWarehouseCity() {
		return warehouseCity;
	}
	public void setWarehouseCity(String warehouseCity) {
		this.warehouseCity = warehouseCity;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getIsOrig() {
		return isOrig;
	}
	public void setIsOrig(String isOrig) {
		this.isOrig = isOrig;
	}

	/**类型-车*/
	public final static String TYPE_CAR = "01";
	/**类型-车商*/
	public final static String TYPE_CARDEALER = "02";
	/**类型-房*/
	public final static String TYPE_HOUSE = "03";
	/**类型-红木*/
	public final static String TYPE_ROSEWOOD = "04";
	/**类型-海鲜*/
	public final static String TYPE_SEAFOOD = "05";
	/**类型-其他*/
	public final static String TYPE_OTHER = "99";
}

