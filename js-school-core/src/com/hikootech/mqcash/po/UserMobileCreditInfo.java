package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yuwei
 * 2015年8月12日
 * 用户手机信用信息：关于手机相关的信用信息(中金)
 */
public class UserMobileCreditInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mobileCreditId;
	private String mobileNumber;
	private String wybs;			//唯一标识（接口返回）
	private String source;
	private Integer queryStatus;
	private Integer resultStatus;
	private String hfBalance; //手机余额 
	private String hfAccDate; //入网时间
	private String hfBalSign; //欠费标识	1：欠费 0：不欠费
	private String hfUserStatus; //用户鉴权状态 1：异常 0：正常
	private String hfAuthDate;	//鉴权日期
	private String hfSegCardType;	//品牌代码
	private String telecomOperator;	//运营商
	private String onlineTimes;		//在网时长(月份)
	private String estimateIncomeMonth;	//收入估算
	private String estimateIncomeAcc;		//累计收入估算
	private String estimateStableIncomeMonth;	//稳定收入估算
	private String estimateStableIncomeAcc;		//累计稳定收入估算
	private String estimateOtherIncomeMonth;	//其他收入估算
	private String estimateOtherIncomeAcc;	//累计其他收入估算
	private String estimateOutgoMonth;		//支出估算
	private String estimateOutgoAcc;			//累计支出估算
	private String estimatePosOutgoMonth;		//消费类支出
	private String estimatePosOutgoAcc;		//累计消费类支出
	private String estimateOtherOutgoMonth;	//其他类支出
	private String estimateOtherOutgoAcc;		//累计其他类支出
	private Date createTime;
	
	private List<UserMobileCreditDetailInfo> UserMobileCreditDetailInfo;
	
	public UserMobileCreditInfo() {
		// TODO Auto-generated constructor stub
	}
	public String getMobileCreditId() {
		return mobileCreditId;
	}
	public void setMobileCreditId(String mobileCreditId) {
		this.mobileCreditId = mobileCreditId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getQueryStatus() {
		return queryStatus;
	}
	public void setQueryStatus(Integer queryStatus) {
		this.queryStatus = queryStatus;
	}
	public Integer getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getHfBalance() {
		return hfBalance;
	}
	public void setHfBalance(String hfBalance) {
		this.hfBalance = hfBalance;
	}
	public String getHfAccDate() {
		return hfAccDate;
	}
	public void setHfAccDate(String hfAccDate) {
		this.hfAccDate = hfAccDate;
	}
	public String getHfBalSign() {
		return hfBalSign;
	}
	public void setHfBalSign(String hfBalSign) {
		this.hfBalSign = hfBalSign;
	}
	public String getHfUserStatus() {
		return hfUserStatus;
	}
	public void setHfUserStatus(String hfUserStatus) {
		this.hfUserStatus = hfUserStatus;
	}
	public String getHfAuthDate() {
		return hfAuthDate;
	}
	public void setHfAuthDate(String hfAuthDate) {
		this.hfAuthDate = hfAuthDate;
	}
	public String getHfSegCardType() {
		return hfSegCardType;
	}
	public void setHfSegCardType(String hfSegCardType) {
		this.hfSegCardType = hfSegCardType;
	}
	public String getTelecomOperator() {
		return telecomOperator;
	}
	public void setTelecomOperator(String telecomOperator) {
		this.telecomOperator = telecomOperator;
	}
	public String getOnlineTimes() {
		return onlineTimes;
	}
	public void setOnlineTimes(String onlineTimes) {
		this.onlineTimes = onlineTimes;
	}
	public String getEstimateIncomeMonth() {
		return estimateIncomeMonth;
	}
	public void setEstimateIncomeMonth(String estimateIncomeMonth) {
		this.estimateIncomeMonth = estimateIncomeMonth;
	}
	public String getEstimateIncomeAcc() {
		return estimateIncomeAcc;
	}
	public void setEstimateIncomeAcc(String estimateIncomeAcc) {
		this.estimateIncomeAcc = estimateIncomeAcc;
	}
	public String getEstimateStableIncomeMonth() {
		return estimateStableIncomeMonth;
	}
	public void setEstimateStableIncomeMonth(String estimateStableIncomeMonth) {
		this.estimateStableIncomeMonth = estimateStableIncomeMonth;
	}
	public String getEstimateStableIncomeAcc() {
		return estimateStableIncomeAcc;
	}
	public void setEstimateStableIncomeAcc(String estimateStableIncomeAcc) {
		this.estimateStableIncomeAcc = estimateStableIncomeAcc;
	}
	public String getEstimateOtherIncomeMonth() {
		return estimateOtherIncomeMonth;
	}
	public void setEstimateOtherIncomeMonth(String estimateOtherIncomeMonth) {
		this.estimateOtherIncomeMonth = estimateOtherIncomeMonth;
	}
	public String getEstimateOtherIncomeAcc() {
		return estimateOtherIncomeAcc;
	}
	public void setEstimateOtherIncomeAcc(String estimateOtherIncomeAcc) {
		this.estimateOtherIncomeAcc = estimateOtherIncomeAcc;
	}
	public String getEstimateOutgoMonth() {
		return estimateOutgoMonth;
	}
	public void setEstimateOutgoMonth(String estimateOutgoMonth) {
		this.estimateOutgoMonth = estimateOutgoMonth;
	}
	public String getEstimateOutgoAcc() {
		return estimateOutgoAcc;
	}
	public void setEstimateOutgoAcc(String estimateOutgoAcc) {
		this.estimateOutgoAcc = estimateOutgoAcc;
	}
	public String getEstimatePosOutgoMonth() {
		return estimatePosOutgoMonth;
	}
	public void setEstimatePosOutgoMonth(String estimatePosOutgoMonth) {
		this.estimatePosOutgoMonth = estimatePosOutgoMonth;
	}
	public String getEstimatePosOutgoAcc() {
		return estimatePosOutgoAcc;
	}
	public void setEstimatePosOutgoAcc(String estimatePosOutgoAcc) {
		this.estimatePosOutgoAcc = estimatePosOutgoAcc;
	}
	public String getEstimateOtherOutgoMonth() {
		return estimateOtherOutgoMonth;
	}
	public void setEstimateOtherOutgoMonth(String estimateOtherOutgoMonth) {
		this.estimateOtherOutgoMonth = estimateOtherOutgoMonth;
	}
	public String getEstimateOtherOutgoAcc() {
		return estimateOtherOutgoAcc;
	}
	public void setEstimateOtherOutgoAcc(String estimateOtherOutgoAcc) {
		this.estimateOtherOutgoAcc = estimateOtherOutgoAcc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<UserMobileCreditDetailInfo> getUserMobileCreditDetailInfo() {
		return UserMobileCreditDetailInfo;
	}
	public void setUserMobileCreditDetailInfo(
			List<UserMobileCreditDetailInfo> userMobileCreditDetailInfo) {
		this.UserMobileCreditDetailInfo = userMobileCreditDetailInfo;
	}
	public String getWybs() {
		return wybs;
	}
	public void setWybs(String wybs) {
		this.wybs = wybs;
	}

}
