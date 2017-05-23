package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月5日
 * 用户绑定的银行卡记录对象
 */
public class UserBankCard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 绑定银行记录id
	 */
	private String bankCardId;
	/**
	 * 第三方绑定流水号
	 */
	private String thirdParyBindingId;
	/**
	 * 绑定人
	 */
	private String userId;
	/**
	 * 卡片对应银行id
	 */
	private String relationBankId;
	/**
	 * 持卡人身份证号
	 */
	private String ownerIdCard;
	/**
	 * 持卡人姓名
	 */
	private String ownerName;
	/**
	 * 卡片类型 0储蓄卡 1信用卡
	 */
	private Integer cardType;
	/**
	 * 卡号
	 */
	private String cardNumber;
	/**
	 * 预留手机号
	 */
	private String reserveMobile;
	/**
	 * 卡片绑定状态：0未绑定 1绑定 2解绑
	 */
	private Integer bindStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	public UserBankCard() {
		// TODO Auto-generated constructor stub
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getThirdParyBindingId() {
		return thirdParyBindingId;
	}

	public void setThirdParyBindingId(String thirdParyBindingId) {
		this.thirdParyBindingId = thirdParyBindingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRelationBankId() {
		return relationBankId;
	}

	public void setRelationBankId(String relationBankId) {
		this.relationBankId = relationBankId;
	}

	public String getOwnerIdCard() {
		return ownerIdCard;
	}

	public void setOwnerIdCard(String ownerIdCard) {
		this.ownerIdCard = ownerIdCard;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getReserveMobile() {
		return reserveMobile;
	}

	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}

	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
