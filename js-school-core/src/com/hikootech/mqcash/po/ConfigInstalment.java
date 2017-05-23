package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月6日
 * 分期配置信息
 */
public class ConfigInstalment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String configInstalmentId;
	private String planId;
	private String instalmentName;
	private Integer instalmentCount;
	private BigDecimal perServiceRate;
	private Integer status;//配置状态：0无效、1有效
	private Date createTime;
	private Date updateTime;
	private String operator;
	
	public ConfigInstalment() {
		// TODO Auto-generated constructor stub
	}

	public String getConfigInstalmentId() {
		return configInstalmentId;
	}

	public void setConfigInstalmentId(String configInstalmentId) {
		this.configInstalmentId = configInstalmentId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getInstalmentName() {
		return instalmentName;
	}

	public void setInstalmentName(String instalmentName) {
		this.instalmentName = instalmentName;
	}

	public Integer getInstalmentCount() {
		return instalmentCount;
	}

	public void setInstalmentCount(Integer instalmentCount) {
		this.instalmentCount = instalmentCount;
	}

	public BigDecimal getPerServiceRate() {
		return perServiceRate;
	}

	public void setPerServiceRate(BigDecimal perServiceRate) {
		this.perServiceRate = perServiceRate;
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
