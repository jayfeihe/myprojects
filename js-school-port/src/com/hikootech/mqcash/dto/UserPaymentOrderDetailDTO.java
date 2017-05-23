package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 *   
 * UserPaymentOrderDetailDTO  
 *   
 * @function:(分期账单已还款明细)  
 * @create time:Sep 18, 2015 4:02:12 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserPaymentOrderDetailDTO implements Serializable {

	
	private static final long serialVersionUID = -4090472629954079600L;
	
	/** 商品订单信息 */
	private String instalmentId;
	/** 订单来源 */
	private String partnerChannel;
	/** 分期号 */
	private Integer instalmentNumber;
	/** 分期数 */
	private Integer instalmentCount;
	/** 还款金额 */
	private BigDecimal paymentAmount;
	/** 本期实收本金 */
	private BigDecimal receivedPrincipal ;
	/** 本期实收服务费 */
	private BigDecimal receivedService ;
	/** 本期实收逾期罚息 */
	private BigDecimal receivedOverdue ;
	/** 商户订单号 */
	private String partnerOrderId = "";
	 /**还款状态*/
	private Integer paymentStatus;
	
	 
	public String getInstalmentId() {
		return instalmentId;
	}
	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}
	public String getPartnerChannel() {
		return partnerChannel;
	}
	public void setPartnerChannel(String partnerChannel) {
		this.partnerChannel = partnerChannel;
	}
	public Integer getInstalmentNumber() {
		return instalmentNumber;
	}
	public void setInstalmentNumber(Integer instalmentNumber) {
		this.instalmentNumber = instalmentNumber;
	}
	public Integer getInstalmentCount() {
		return instalmentCount;
	}
	public void setInstalmentCount(Integer instalmentCount) {
		this.instalmentCount = instalmentCount;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public BigDecimal getReceivedPrincipal() {
		return receivedPrincipal;
	}
	public void setReceivedPrincipal(BigDecimal receivedPrincipal) {
		this.receivedPrincipal = receivedPrincipal;
	}
	public BigDecimal getReceivedService() {
		return receivedService;
	}
	public void setReceivedService(BigDecimal receivedService) {
		this.receivedService = receivedService;
	}
	public BigDecimal getReceivedOverdue() {
		return receivedOverdue;
	}
	public void setReceivedOverdue(BigDecimal receivedOverdue) {
		this.receivedOverdue = receivedOverdue;
	}
	public String getPartnerOrderId() {
		return partnerOrderId;
	}
	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
}
