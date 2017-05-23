package com.tera.contract.model;

import com.tera.util.DateUtils;

/**
 * 
 * 实体类
 * <b>功能：</b>SealDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-29 13:26:30<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Seal {

	//属性部分
	private int id; //ID
	private int codeType; //1出借端2借款端
	private String contractNo; //合同号
	private String sealNo; //合同章编号
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
	public int getCodeType () {
		return this.codeType;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getSealNo () {
		return this.sealNo;
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
	public void setCodeType (int codeType) {
		this.codeType=codeType;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setSealNo (String sealNo) {
		this.sealNo=sealNo;
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

