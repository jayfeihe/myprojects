package com.tera.product.model;

import com.tera.util.DateUtils;

/**
 * 
 * 产品表实体类
 * <b>功能：</b>ProductDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-05 10:19:09<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Product {

	//属性部分
	private int id; //ID
	private String name; //产品名称
	private String type; //产品类型
	private String typeName; 			//
	private String repayMethodName;   	//
	private int period; //期限
	private double sreviceFeeRate; //服务费率
	private double sreviceFeeRate2; //账户管理费率
	private double sreviceFeeRate3; //融资服务费率
	private double interestRate; //利率
	private String repayMethod; //还款方式
	private double penaltyRate; //罚息费率
	private double delayRate;   //滞纳金费率
	private double defaultRate1; //违约金比例1
	private double defaultRate2; //违约金比例2
	private double defaultRate3; //违约金比例3
	private double defaultRate4; //违约金比例4
	private String remark; //备注
	private String belongChannel; //渠道
	private String belongChannelName; //渠道名称
	private String state; //备注
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	
	/*---------------用于返回下拉框回显值用的------------------*/
	private String productName;//产品名称

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getName () {
		return this.name;
	}
	public String getType () {
		return this.type;
	}
	public int getPeriod () {
		return this.period;
	}
	public double getSreviceFeeRate () {
		return this.sreviceFeeRate;
	}
	public double getSreviceFeeRate2 () {
		return this.sreviceFeeRate2;
	}
	public double getInterestRate () {
		return this.interestRate;
	}
	public String getRepayMethod () {
		return this.repayMethod;
	}
	public double getPenaltyRate () {
		return this.penaltyRate;
	}
	public double getDefaultRate1 () {
		return this.defaultRate1;
	}
	public double getDefaultRate2 () {
		return this.defaultRate2;
	}
	public double getDefaultRate3 () {
		return this.defaultRate3;
	}
	public double getDefaultRate4 () {
		return this.defaultRate4;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getBelongChannel() {
		return belongChannel;
	}
	public String getBelongChannelName() {
		return belongChannelName;
	}
	public String getState() {
		return state;
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
	public void setName (String name) {
		this.name=name;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setPeriod (int period) {
		this.period=period;
	}
	public void setSreviceFeeRate (double sreviceFeeRate) {
		this.sreviceFeeRate=sreviceFeeRate;
	}
	public void setSreviceFeeRate2 (double sreviceFeeRate2) {
		this.sreviceFeeRate2=sreviceFeeRate2;
	}
	public void setInterestRate (double interestRate) {
		this.interestRate=interestRate;
	}
	public void setRepayMethod (String repayMethod) {
		this.repayMethod=repayMethod;
	}
	public void setPenaltyRate (double penaltyRate) {
		this.penaltyRate=penaltyRate;
	}
	public void setDefaultRate1 (double defaultRate1) {
		this.defaultRate1=defaultRate1;
	}
	public void setDefaultRate2 (double defaultRate2) {
		this.defaultRate2=defaultRate2;
	}
	public void setDefaultRate3 (double defaultRate3) {
		this.defaultRate3=defaultRate3;
	}
	public void setDefaultRate4 (double defaultRate4) {
		this.defaultRate4=defaultRate4;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setBelongChannel(String belongChannel) {
		this.belongChannel = belongChannel;
	}
	public void setBelongChannelName(String belongChannelName) {
		this.belongChannelName = belongChannelName;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRepayMethodName() {
		return repayMethodName;
	}
	public void setRepayMethodName(String repayMethodName) {
		this.repayMethodName = repayMethodName;
	}
	public double getSreviceFeeRate3() {
		return sreviceFeeRate3;
	}
	public void setSreviceFeeRate3(double sreviceFeeRate3) {
		this.sreviceFeeRate3 = sreviceFeeRate3;
	}
	public double getDelayRate() {
		return delayRate;
	}
	public void setDelayRate(double delayRate) {
		this.delayRate = delayRate;
	}
	public String getProductName() {
		return name;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

}

