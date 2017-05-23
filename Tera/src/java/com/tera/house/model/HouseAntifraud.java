package com.tera.house.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款反欺诈表实体类
 * <b>功能：</b>houseAntifraudDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class HouseAntifraud {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String submitInfo; //提交说明
	private String result; //结果
	private String resultInfo; //结果说明
	private String submitOperator; //提交人
	private String operator; //操作员
	private String state; //状态
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//save保存,submit提交,decision拒贷,antifraud反欺诈,relieveAntifraud解除反欺诈
	private String buttonType;
	
	private String operatorName; // 处理人名称
	private String submitOperatorName; // 提交人名称

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getSubmitInfo () {
		return this.submitInfo;
	}
	public String getResult () {
		return this.result;
	}
	public String getResultInfo () {
		return this.resultInfo;
	}
	public String getSubmitOperator () {
		return this.submitOperator;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getState () {
		return this.state;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	public String getButtonType() {
		return buttonType;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public String getSubmitOperatorName() {
		return submitOperatorName;
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
	public void setSubmitInfo (String submitInfo) {
		this.submitInfo=submitInfo;
	}
	public void setResult (String result) {
		this.result=result;
	}
	public void setResultInfo (String resultInfo) {
		this.resultInfo=resultInfo;
	}
	public void setSubmitOperator (String submitOperator) {
		this.submitOperator=submitOperator;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setState (String state) {
		this.state=state;
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
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public void setSubmitOperatorName(String submitOperatorName) {
		this.submitOperatorName = submitOperatorName;
	}
}

