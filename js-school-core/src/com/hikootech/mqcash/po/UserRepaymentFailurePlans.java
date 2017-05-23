package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName UserRepaymentFailurePlans 
* @Description 用户还款失败还款计划列表
* @author 余巍 yuweiqwe@126.com 
* @date 2016年2月26日 上午10:53:09 
*  
*/
public class UserRepaymentFailurePlans implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String repaymentPlansId;
	private Date failureTime;
	private Integer repeatRepaymentTimes;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private String operator;
	
	public UserRepaymentFailurePlans() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepaymentPlansId() {
		return repaymentPlansId;
	}

	public void setRepaymentPlansId(String repaymentPlansId) {
		this.repaymentPlansId = repaymentPlansId;
	}

	public Date getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(Date failureTime) {
		this.failureTime = failureTime;
	}

	public Integer getRepeatRepaymentTimes() {
		return repeatRepaymentTimes;
	}

	public void setRepeatRepaymentTimes(Integer repeatRepaymentTimes) {
		this.repeatRepaymentTimes = repeatRepaymentTimes;
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
