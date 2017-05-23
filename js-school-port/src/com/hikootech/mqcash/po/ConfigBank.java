package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月5日
 * 秒趣银行信息与第三方对应关系
 */
public class ConfigBank implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String bankId;
	private String thirdPartyBankId;
	private String bankName;
	private String bankImgUrl;
	private Integer status;//配置状态：0无效、1有效
	private Date createTime;
	
	public ConfigBank() {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
