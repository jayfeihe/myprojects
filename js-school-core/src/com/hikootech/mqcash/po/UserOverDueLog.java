package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;



	/**
	* 此类描述的是：计算逾期数据时失败的记录表
	* @author: zhaohefei
	* @version: 2015年12月12日 上午10:34:13
	*/
	
public class UserOverDueLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String logId;
	private String repaymentPlanId;
	private Date createTime;
	private String descp;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getRepaymentPlanId() {
		return repaymentPlanId;
	}
	public void setRepaymentPlanId(String repaymentPlanId) {
		this.repaymentPlanId = repaymentPlanId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	
	
}
