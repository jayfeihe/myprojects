package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月31日
 * 逾期配置表
 */
public class ConfigOverdue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String configOverdueId;
	private BigDecimal overdueRate;
	private Integer status;
	private String createTime;
	private Date updateTime;
	private String operator;
	
	public ConfigOverdue() {
		// TODO Auto-generated constructor stub
	}

	public String getConfigOverdueId() {
		return configOverdueId;
	}

	public void setConfigOverdueId(String configOverdueId) {
		this.configOverdueId = configOverdueId;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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
