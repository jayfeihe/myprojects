package com.tera.message.model;

import com.tera.util.DateUtils;

/**
 * 
 * 短信日志表实体类
 * <b>功能：</b>MsglogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-07-01 16:57:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Msglog {

	//属性部分
	private int id; //id
	private int templateId; //模板ID
	private String msgId; //信息ID
	private String type; //类型
	private String contractNo; //合同编号
	private String customerName; //客户姓名
	private String idType; //证件类型
	private String idNo; //证件号码
	private String mobileTel; //联系方式
	private java.util.Date repaymentDate; //还款日
	private String repaymentDateStr; //还款日
	private int days; //天数
	private String msgContent; //信息内容
	private String sendState; //发送状态
	private java.sql.Timestamp sendTime; //发送时间
	private String sendTimeStr; //发送时间
	private String receiveState; //接收状态
	private java.sql.Timestamp receiveTime; //接收时间
	private String receiveTimeStr; //接收时间
	private String remark; //备注
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间


	private String sendTimeMin; //接收时间
	private String sendTimeMax; //接收时间
	private String receiveTimeMin; //接收时间
	private String receiveTimeMax; //接收时间
	private String templateName; //模板名称
	//getter部分
	public int getId () {
		return this.id;
	}
	public int getTemplateId () {
		return this.templateId;
	}
	public String getMsgId () {
		return this.msgId;
	}
	public String getType () {
		return this.type;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getCustomerName () {
		return this.customerName;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getMobileTel () {
		return this.mobileTel;
	}
	public java.util.Date getRepaymentDate () {
		return this.repaymentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRepaymentDateStr () {
		return DateUtils.formatDate(this.repaymentDate);
	}
	public int getDays () {
		return this.days;
	}
	public String getMsgContent () {
		return this.msgContent;
	}
	public String getSendState () {
		return this.sendState;
	}
	public java.sql.Timestamp getSendTime () {
		return this.sendTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getSendTimeStr () {
		return DateUtils.formatTime(this.sendTime);
	}
	public String getReceiveState () {
		return this.receiveState;
	}
	public java.sql.Timestamp getReceiveTime () {
		return this.receiveTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getReceiveTimeStr () {
		return DateUtils.formatTime(this.receiveTime);
	}
	public String getRemark () {
		return this.remark;
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
	public void setTemplateId (int templateId) {
		this.templateId=templateId;
	}
	public void setMsgId (String msgId) {
		this.msgId=msgId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setCustomerName (String customerName) {
		this.customerName=customerName;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setMobileTel (String mobileTel) {
		this.mobileTel=mobileTel;
	}
	public void setRepaymentDate (java.util.Date repaymentDate) {
		this.repaymentDate=repaymentDate;
	}
	public void setRepaymentDateStr (String repaymentDateStr) {
		this.repaymentDateStr=repaymentDateStr;
	}
	public void setDays (int days) {
		this.days=days;
	}
	public void setMsgContent (String msgContent) {
		this.msgContent=msgContent;
	}
	public void setSendState (String sendState) {
		this.sendState=sendState;
	}
	public void setSendTime (java.sql.Timestamp sendTime) {
		this.sendTime=sendTime;
	}
	public void setSendTimeStr (String sendTimeStr) {
		this.sendTimeStr=sendTimeStr;
	}
	public void setReceiveState (String receiveState) {
		this.receiveState=receiveState;
	}
	public void setReceiveTime (java.sql.Timestamp receiveTime) {
		this.receiveTime=receiveTime;
	}
	public void setReceiveTimeStr (String receiveTimeStr) {
		this.receiveTimeStr=receiveTimeStr;
	}
	public void setRemark (String remark) {
		this.remark=remark;
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
	public String getSendTimeMin() {
		return sendTimeMin;
	}
	public void setSendTimeMin(String sendTimeMin) {
		this.sendTimeMin = sendTimeMin;
	}
	public String getSendTimeMax() {
		return sendTimeMax;
	}
	public void setSendTimeMax(String sendTimeMax) {
		this.sendTimeMax = sendTimeMax;
	}
	public String getReceiveTimeMin() {
		return receiveTimeMin;
	}
	public void setReceiveTimeMin(String receiveTimeMin) {
		this.receiveTimeMin = receiveTimeMin;
	}
	public String getReceiveTimeMax() {
		return receiveTimeMax;
	}
	public void setReceiveTimeMax(String receiveTimeMax) {
		this.receiveTimeMax = receiveTimeMax;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}

