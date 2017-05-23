package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月6日
 *
 */
public class UserAccessRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String recordId;
	private String partnerId;
	private String partnerOrderId;
	private String partnerName;
	private BigDecimal partnerOrderAmount;
	private Date partnerOrderTime;
	private String receivingAddress;
	private String ip;
	private String ipAttribution;
	private String ipInt;
	private String name;
	private String idCard;
	private String idCardAddress;
	private String contactPhone;
	private Date createTime;
	
	public UserAccessRecord() {
		// TODO Auto-generated constructor stub
	}

	public UserAccessRecord(String recordId, String partnerId,
			String partnerOrderId, String partnerName,
			BigDecimal partnerOrderAmount, Date partnerOrderTime,
			String receivingAddress, String ip, String ipAttribution,
			String ipInt, String name, String idCard, String idCardAddress,
			String contactPhone, Date createTime) {
		super();
		this.recordId = recordId;
		this.partnerId = partnerId;
		this.partnerOrderId = partnerOrderId;
		this.partnerName = partnerName;
		this.partnerOrderAmount = partnerOrderAmount;
		this.partnerOrderTime = partnerOrderTime;
		this.receivingAddress = receivingAddress;
		this.ip = ip;
		this.ipAttribution = ipAttribution;
		this.ipInt = ipInt;
		this.name = name;
		this.idCard = idCard;
		this.idCardAddress = idCardAddress;
		this.contactPhone = contactPhone;
		this.createTime = createTime;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public BigDecimal getPartnerOrderAmount() {
		return partnerOrderAmount;
	}

	public void setPartnerOrderAmount(BigDecimal partnerOrderAmount) {
		this.partnerOrderAmount = partnerOrderAmount;
	}

	public Date getPartnerOrderTime() {
		return partnerOrderTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpAttribution() {
		return ipAttribution;
	}

	public void setIpAttribution(String ipAttribution) {
		this.ipAttribution = ipAttribution;
	}

	public String getIpInt() {
		return ipInt;
	}

	public void setIpInt(String ipInt) {
		this.ipInt = ipInt;
	}

	public void setPartnerOrderTime(Date partnerOrderTime) {
		this.partnerOrderTime = partnerOrderTime;
	}

	public String getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(String receivingAddress) {
		this.receivingAddress = receivingAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCardAddress() {
		return idCardAddress;
	}

	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
