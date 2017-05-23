package com.hikootech.mqcash.dto;

/**
 * @author yuwei
 * 2015年8月10日
 * 查询分期配置信息的DTO对象
 */
public class ConfigInstalmentDTO {
	
	private String partnerId;
	private String configInstalmentId;
	private Integer status;
	private Integer planStatus;
	
	public ConfigInstalmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public ConfigInstalmentDTO(String partnerId, String configInstalmentId,
			Integer status, Integer planStatus) {
		super();
		this.partnerId = partnerId;
		this.configInstalmentId = configInstalmentId;
		this.status = status;
		this.planStatus = planStatus;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getConfigInstalmentId() {
		return configInstalmentId;
	}

	public void setConfigInstalmentId(String configInstalmentId) {
		this.configInstalmentId = configInstalmentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(Integer planStatus) {
		this.planStatus = planStatus;
	}

}
