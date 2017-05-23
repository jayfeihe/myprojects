package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 *   
 * UserPaymentRecordItem  
 *   
 * @function:(用户还款记录项：记录用户还款记录对应还款明细（每期还款计划费用、逾期费用、催收费用）)  
 * @create time:Sep 15, 2015 10:56:59 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserPaymentRecordItem implements Serializable{

	private static final long serialVersionUID = 6276242245007490156L;
	
	/**  
	 * upRecordItemId:TODO（还款记录项id）  
	 */  
	private String upRecordItemId	   ;
	/**  
	 * repaymentPlansId:TODO（还款计划id）  
	 */  
	private String repaymentPlansId	 ;
	/**  
	 * userPaymentOrderId:TODO（用户主动和被动支付订单表id）  
	 */  
	private String userPaymentOrderId ;
	/**
	 * 分期单id
	 */
	private String instalmentId;
	
	/**  
	 * needPaymentAmount:TODO（记录项对应应还金额（可能此记录项对应的是部分还款））  
	 */  
	private BigDecimal needPaymentAmount	 ;
	/**  
	 * realPaymentAmount:TODO（记录项对应应还金额（可能此记录项对应的是部分还款））  
	 */  
	private BigDecimal realPaymentAmount	;
	/**  
	 * repaymentType:TODO（还款类型：0全部还款、1部分还款.(通过实际支付金额与应支付金额判断)）  
	 */  
	private Integer repaymentType	  ;
	/**
	 * 
	 * 支付状态：0未支付 1支付成功 2已退款 3重复还款
	 */
	private Integer paymentStatus;
	/**  
	 * paymentTime:TODO（还款时间）  
	 */  
	private Date paymentTime	       ;
	  
	private Date createTime	       ;
	private Date updateTime	;
	private String  operator;
	public String getUpRecordItemId() {
		return upRecordItemId;
	}
	public void setUpRecordItemId(String upRecordItemId) {
		this.upRecordItemId = upRecordItemId;
	}
	public String getRepaymentPlansId() {
		return repaymentPlansId;
	}
	public void setRepaymentPlansId(String repaymentPlansId) {
		this.repaymentPlansId = repaymentPlansId;
	}
	public String getUserPaymentOrderId() {
		return userPaymentOrderId;
	}
	public void setUserPaymentOrderId(String userPaymentOrderId) {
		this.userPaymentOrderId = userPaymentOrderId;
	}
	public String getInstalmentId() {
		return instalmentId;
	}
	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}
	public BigDecimal getNeedPaymentAmount() {
		return needPaymentAmount;
	}
	public void setNeedPaymentAmount(BigDecimal needPaymentAmount) {
		this.needPaymentAmount = needPaymentAmount;
	}
	public BigDecimal getRealPaymentAmount() {
		return realPaymentAmount;
	}
	public void setRealPaymentAmount(BigDecimal realPaymentAmount) {
		this.realPaymentAmount = realPaymentAmount;
	}
	public Integer getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}	 

}
