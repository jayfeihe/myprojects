package com.hikootech.mqcash.vo;

import java.io.Serializable;

/**
 * @author yuwei
 * 2015年8月5日
 * 秒趣银行信息与第三方对应关系
 */
public class BankVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String relationBankId;
	private String bankId;
	private String thirdPartyBankId;
	private String bankCode;
	private String bankName;
	private Integer bankType; 
	private String bankImgUrl;
	
	public BankVO() {
		// TODO Auto-generated constructor stub
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getThirdPartyBankId() {
		return thirdPartyBankId;
	}

	public void setThirdPartyBankId(String thirdPartyBankId) {
		this.thirdPartyBankId = thirdPartyBankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankImgUrl() {
		return bankImgUrl;
	}

	public void setBankImgUrl(String bankImgUrl) {
		this.bankImgUrl = bankImgUrl;
	}

	public String getRelationBankId() {
		return relationBankId;
	}

	public void setRelationBankId(String relationBankId) {
		this.relationBankId = relationBankId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

}
