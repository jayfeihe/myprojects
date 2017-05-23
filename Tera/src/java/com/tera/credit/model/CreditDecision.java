package com.tera.credit.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款决策表实体类
 * <b>功能：</b>CreditDecisionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:02:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CreditDecision {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String type; //类型
	private double amount; //借款金额
	private double contractAmount;// 合同金额
	private String belongChannel; //所属渠道
	private String product; //产品
	private String firstTestCode; // 一级测试码
	private String secondTestCode; // 二级测试码
	private String secondTestValue; // 二级测试值
	private int period; //期限
	private String decision; //决策   不通过:00,通过:01,拟通过:02,拟拒贷:03,特殊审核:04,拒贷:05,推送：06
	private String remarks; //备注
	private String decisionCode1; //违例码1
	private String decisionCode2; //违例码2
	private String decisionCode3; //拒贷码1
	private String decisionCode4; //拒贷码2
	private String decisionCode5; //回退码1
	private String decisionCode6; //回退码2
	private String imageSummarys; //补充影像资料分类
	private String returnMsg; //退回原因
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	
	/* 拒贷款及查看详细信息时显示 */
	private String feedbackDescribe;  //反馈销售描述（实际存到t_bpm_log表里的log_content2字段里）

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getType () {
		return this.type;
	}
	public double getAmount () {
		return this.amount;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getBelongChannel() {
		return belongChannel;
	}
	public String getProduct () {
		return this.product;
	}
	public String getFirstTestCode() {
		return firstTestCode;
	}
	public void setFirstTestCode(String firstTestCode) {
		this.firstTestCode = firstTestCode;
	}
	public String getSecondTestCode() {
		return secondTestCode;
	}
	public void setSecondTestCode(String secondTestCode) {
		this.secondTestCode = secondTestCode;
	}
	public String getSecondTestValue() {
		return secondTestValue;
	}
	public void setSecondTestValue(String secondTestValue) {
		this.secondTestValue = secondTestValue;
	}
	public int getPeriod () {
		return this.period;
	}
	public String getDecision () {
		return this.decision;
	}
	public String getRemarks () {
		return this.remarks;
	}
	public String getDecisionCode1 () {
		return this.decisionCode1;
	}
	public String getDecisionCode2 () {
		return this.decisionCode2;
	}
	public String getDecisionCode3 () {
		return this.decisionCode3;
	}
	public String getDecisionCode4 () {
		return this.decisionCode4;
	}
	public String getReturnMsg () {
		return this.returnMsg;
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
	public String getFeedbackDescribe() {
		return feedbackDescribe;
	}
	public void setFeedbackDescribe(String feedbackDescribe) {
		this.feedbackDescribe = feedbackDescribe;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setBelongChannel(String belongChannel) {
		this.belongChannel = belongChannel;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setPeriod (int period) {
		this.period=period;
	}
	public void setDecision (String decision) {
		this.decision=decision;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
	}
	public void setDecisionCode1 (String decisionCode1) {
		this.decisionCode1=decisionCode1;
	}
	public void setDecisionCode2 (String decisionCode2) {
		this.decisionCode2=decisionCode2;
	}
	public void setDecisionCode3 (String decisionCode3) {
		this.decisionCode3=decisionCode3;
	}
	public void setDecisionCode4 (String decisionCode4) {
		this.decisionCode4=decisionCode4;
	}
	public void setReturnMsg (String returnMsg) {
		this.returnMsg=returnMsg;
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
	public String getDecisionCode5() {
		return decisionCode5;
	}
	public void setDecisionCode5(String decisionCode5) {
		this.decisionCode5 = decisionCode5;
	}
	public String getDecisionCode6() {
		return decisionCode6;
	}
	public void setDecisionCode6(String decisionCode6) {
		this.decisionCode6 = decisionCode6;
	}
	public String getImageSummarys() {
		return imageSummarys;
	}
	public void setImageSummarys(String imageSummarys) {
		this.imageSummarys = imageSummarys;
	}

}

