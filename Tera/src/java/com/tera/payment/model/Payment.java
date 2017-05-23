package com.tera.payment.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>PaymentDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-31 15:01:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Payment implements Cloneable {

	//属性部分
	private int id; //ID
	private String contractNo; //合同号
	private String inOut; //收付
	private String subject; //科目
	private java.util.Date repaymentDate; //还款日
	private String repaymentDateStr; //还款日
	private double planAmount; //计划金额
	private double actualAmount; //实际金额
	private String source; //来源
	private int periodNum; //期数
	private String detail; //明细
	private String sendFlag; //发盘 0，未发盘1，已发盘
	private String state; //状态 : 1 未支付,2 已发盘,3 发盘失败,4 发盘成功,5 支付成功,6 支付失败 ,9 未确认
	private String reason; //原因
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private boolean isRepayment; //是否付款
	
	
	private String loanAppId; 	//申请号
	private String idType;		//证件类型
	private String idNo;		//证件类型

	private String bankNo;		//总行代码
	private String cityNo;		//城市代码
	private String accountName; //账户名
	private String accounttNo;	//账号
	private String mobile;	//手机 ，富友支付接口必传参数
	
	private List<ThirdPaymentLog> thirdPaymentLogList;//对应的日志
	
	
	
	public List<ThirdPaymentLog> getThirdPaymentLogList() {
		return thirdPaymentLogList;
	}
	public void setThirdPaymentLogList(List<ThirdPaymentLog> thirdPaymentLogList) {
		this.thirdPaymentLogList = thirdPaymentLogList;
	}
	public void addThirdPaymentLog(ThirdPaymentLog thirdPaymentLog ) {
		if(thirdPaymentLogList==null){
			thirdPaymentLogList=new ArrayList<ThirdPaymentLog>();
		}
		this.thirdPaymentLogList.add(thirdPaymentLog);
	}
	
	
	
	
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getInOut () {
		return this.inOut;
	}
	public String getSubject () {
		return this.subject;
	}
	public java.util.Date getRepaymentDate () {
		return this.repaymentDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRepaymentDateStr () {
		return DateUtils.formatDate(this.repaymentDate);
	}
	public double getPlanAmount () {
		return this.planAmount;
	}
	public double getActualAmount () {
		return this.actualAmount;
	}
	public String getSource () {
		return this.source;
	}
	public int getPeriodNum () {
		return this.periodNum;
	}
	public String getDetail () {
		return this.detail;
	}
	public String getSendFlag () {
		return this.sendFlag;
	}
	public String getState () {
		return this.state;
	}
	public String getReason () {
		return this.reason;
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
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setInOut (String inOut) {
		this.inOut=inOut;
	}
	public void setSubject (String subject) {
		this.subject=subject;
	}
	public void setRepaymentDate (java.util.Date repaymentDate) {
		this.repaymentDate=repaymentDate;
	}
	public void setRepaymentDateStr (String repaymentDateStr) {
		this.repaymentDateStr=repaymentDateStr;
	}
	public void setPlanAmount (double planAmount) {
		this.planAmount=planAmount;
	}
	public void setActualAmount (double actualAmount) {
		this.actualAmount=actualAmount;
	}
	public void setSource (String source) {
		this.source=source;
	}
	public void setPeriodNum (int periodNum) {
		this.periodNum=periodNum;
	}
	public void setDetail (String detail) {
		this.detail=detail;
	}
	public void setSendFlag (String sendFlag) {
		this.sendFlag=sendFlag;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setReason (String reason) {
		this.reason=reason;
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
	public String getLoanAppId() {
		return loanAppId;
	}
	public void setLoanAppId(String loanAppId) {
		this.loanAppId = loanAppId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccounttNo() {
		return accounttNo;
	}
	public void setAccounttNo(String accounttNo) {
		this.accounttNo = accounttNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public void setRepayment(boolean isRepayment) {
		this.isRepayment = isRepayment;
	}
	/**
	 * 判断是否到达还款日， 到达还款日 return true
	 * 收到款后，客户可以接着支付下一期
	 * @return
	 */
	public boolean getIsRepayment(){
		if (repaymentDate == null) {
			return false;
		}
		Date now = DateUtils.addMonth(repaymentDate, -1);
		now = DateUtils.addDay(now, 1);
		return now.getTime() < System.currentTimeMillis();
	}


	@Override
	public Payment clone() throws CloneNotSupportedException {
		Payment payment=null;
		try{ 
			payment = (Payment)super.clone(); 
		}catch(CloneNotSupportedException e){ 
			e.printStackTrace(); 
		}
		return payment;
	}
	
	
	
}

