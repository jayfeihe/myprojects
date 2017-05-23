package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月14日
 * 配置表分期计划
 */
public class ConfigInstalmentPlan implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String planId;
	private String planName;
	private String configPartnerId;
	private Date createTime;
	private Integer status;
	private String creater;
	private String descp;
	
	public ConfigInstalmentPlan() {
		// TODO Auto-generated constructor stub
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getConfigPartnerId() {
		return configPartnerId;
	}

	public void setConfigPartnerId(String configPartnerId) {
		this.configPartnerId = configPartnerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

}
