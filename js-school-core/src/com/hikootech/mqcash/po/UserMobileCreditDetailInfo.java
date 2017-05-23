package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月12日
 * 用户手机信用明细信息：关于手机相关的每月信用明细信息(中金)
 */
public class UserMobileCreditDetailInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mobileCreditDetailId;
	private String mobileCreditId;
	private String mobile; //手机号
	private String month;	//月份
	private String bankNo;	//银行号
	private String cardNo;	//卡号	
	private String cardType;//卡类型	1：信用卡 2：储蓄卡
	private String stableIncome;	//稳定入账金额
	private String otherIncome;		//其他入账金额
	private String posOutgo;		//POS 出账金额
	private String otherOutgo;		//其他出账金额
	private Integer ifStable;		//工作状态	1：是 0：否
	private Integer ifPayOntime;	//是否按时还款还贷	1：是 0：否 9：未知
	private String remainAmt;	//剩余应还
	private Date createTime;
	
	public UserMobileCreditDetailInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getMobileCreditDetailId() {
		return mobileCreditDetailId;
	}

	public void setMobileCreditDetailId(String mobileCreditDetailId) {
		this.mobileCreditDetailId = mobileCreditDetailId;
	}

	public String getMobileCreditId() {
		return mobileCreditId;
	}

	public void setMobileCreditId(String mobileCreditId) {
		this.mobileCreditId = mobileCreditId;
	}


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getStableIncome() {
		return stableIncome;
	}

	public void setStableIncome(String stableIncome) {
		this.stableIncome = stableIncome;
	}

	public String getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(String otherIncome) {
		this.otherIncome = otherIncome;
	}

	public String getPosOutgo() {
		return posOutgo;
	}

	public void setPosOutgo(String posOutgo) {
		this.posOutgo = posOutgo;
	}

	public String getOtherOutgo() {
		return otherOutgo;
	}

	public void setOtherOutgo(String otherOutgo) {
		this.otherOutgo = otherOutgo;
	}

	public Integer getIfStable() {
		return ifStable;
	}

	public void setIfStable(Integer ifStable) {
		this.ifStable = ifStable;
	}

	public Integer getIfPayOntime() {
		return ifPayOntime;
	}

	public void setIfPayOntime(Integer ifPayOntime) {
		this.ifPayOntime = ifPayOntime;
	}

	public String getRemainAmt() {
		return remainAmt;
	}

	public void setRemainAmt(String remainAmt) {
		this.remainAmt = remainAmt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
