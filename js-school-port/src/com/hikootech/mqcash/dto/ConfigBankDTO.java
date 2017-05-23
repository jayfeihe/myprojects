package com.hikootech.mqcash.dto;

/**
 * @author yuwei
 * 2015年8月18日
 * 查询银行列表VO对象
 */
public class ConfigBankDTO {
	
	private String relationBankId;
	private String thirdPartyId;
	private Integer bankType;
	private Integer paymentStatus;
	private Integer chargeStatus;
	
	public ConfigBankDTO() {
		// TODO Auto-generated constructor stub
	}

	public ConfigBankDTO(String thirdPartyId, Integer bankType) {
		super();
		this.thirdPartyId = thirdPartyId;
		this.bankType = bankType;
	}
	public ConfigBankDTO(String thirdPartyId,Integer paymentStatus, Integer chargeStatus) {
		super();
		this.thirdPartyId = thirdPartyId;
		this.paymentStatus = paymentStatus;
		this.chargeStatus = chargeStatus;
	}
	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

	public String getRelationBankId() {
		return relationBankId;
	}

	public void setRelationBankId(String relationBankId) {
		this.relationBankId = relationBankId;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

 
	
}
