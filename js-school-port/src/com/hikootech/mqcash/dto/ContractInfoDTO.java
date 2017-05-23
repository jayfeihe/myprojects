package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 订单信息展现对象
 * */
public class ContractInfoDTO  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//*****************top****************
	private String instalmentId; //账单id
	private String loanDate; //借款日期
	private Integer periodMax;//期数
	
	//*****************center****************
	private String partnerOrderId;//商家订单号
	private String productName; //商品名称
	private BigDecimal productPrice; //商品价格
	private Integer productNum;//购买数量	
	private String productUrl;//商品url
	private String productImgUrl;//商品图片url
	private String partnerName;
	private String partnerLogoUrl;//合作商logo URL
	
	private BigDecimal loanAmount;//借款金额 订单金额+服务费
	private Integer protocolStatus; //合同状态
	private String bankCardNum;
	private String bankCardId;
	 
	public ContractInfoDTO(){
		
	}

	public String getInstalmentId() {
		return instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public Integer getPeriodMax() {
		return periodMax;
	}

	public void setPeriodMax(Integer periodMax) {
		this.periodMax = periodMax;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}


	public String getPartnerLogoUrl() {
		return partnerLogoUrl;
	}

	public void setPartnerLogoUrl(String partnerLogoUrl) {
		this.partnerLogoUrl = partnerLogoUrl;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
 

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getProtocolStatus() {
		return protocolStatus;
	}

	public void setProtocolStatus(Integer protocolStatus) {
		this.protocolStatus = protocolStatus;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getProductImgUrl() {
		return productImgUrl;
	}

	public void setProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}
	
}
