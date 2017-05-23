package com.tera.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppCollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-20 11:49:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanAppCollateral {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private int seqFlag; //序号
	private String mainFlag; //主借款人标识
	private String type; //抵押物类型
	private String certificate1; //房产证号/车辆号牌
	private String certificate2; //土地证号/登记证号
	private double appraisalAmount; //评估总价
	private double houseSize; //面积
	private String houseToward; //朝向
	private double useYears; //使用年限
	private String appraisalCompany; //评估公司
	private String addProvice; //省
	private String addCity; //市
	private String addCounty; //区县
	private String address; //地址
	private String postcode; //邮编
	private String holdingCompany; //股权公司全称
	private String holdingType; //股权类型
	private String idType; //股权证件类型
	private String idNo; //股权证件编号
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
	public int getSeqFlag () {
		return this.seqFlag;
	}
	public String getMainFlag () {
		return this.mainFlag;
	}
	public String getType () {
		return this.type;
	}
	public String getCertificate1 () {
		return this.certificate1;
	}
	public String getCertificate2 () {
		return this.certificate2;
	}
	public double getAppraisalAmount () {
		return this.appraisalAmount;
	}
	public double getHouseSize () {
		return this.houseSize;
	}
	public String getHouseToward () {
		return this.houseToward;
	}
	public double getUseYears () {
		return this.useYears;
	}
	public String getAppraisalCompany () {
		return this.appraisalCompany;
	}
	public String getAddProvice () {
		return this.addProvice;
	}
	public String getAddCity () {
		return this.addCity;
	}
	public String getAddCounty () {
		return this.addCounty;
	}
	public String getAddress () {
		return this.address;
	}
	public String getPostcode () {
		return this.postcode;
	}
	public String getHoldingCompany () {
		return this.holdingCompany;
	}
	public String getHoldingType () {
		return this.holdingType;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
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
	public void setSeqFlag (int seqFlag) {
		this.seqFlag=seqFlag;
	}
	public void setMainFlag (String mainFlag) {
		this.mainFlag=mainFlag;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setCertificate1 (String certificate1) {
		this.certificate1=certificate1;
	}
	public void setCertificate2 (String certificate2) {
		this.certificate2=certificate2;
	}
	public void setAppraisalAmount (double appraisalAmount) {
		this.appraisalAmount=appraisalAmount;
	}
	public void setHouseSize (double houseSize) {
		this.houseSize=houseSize;
	}
	public void setHouseToward (String houseToward) {
		this.houseToward=houseToward;
	}
	public void setUseYears (double useYears) {
		this.useYears=useYears;
	}
	public void setAppraisalCompany (String appraisalCompany) {
		this.appraisalCompany=appraisalCompany;
	}
	public void setAddProvice (String addProvice) {
		this.addProvice=addProvice;
	}
	public void setAddCity (String addCity) {
		this.addCity=addCity;
	}
	public void setAddCounty (String addCounty) {
		this.addCounty=addCounty;
	}
	public void setAddress (String address) {
		this.address=address;
	}
	public void setPostcode (String postcode) {
		this.postcode=postcode;
	}
	public void setHoldingCompany (String holdingCompany) {
		this.holdingCompany=holdingCompany;
	}
	public void setHoldingType (String holdingType) {
		this.holdingType=holdingType;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
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

