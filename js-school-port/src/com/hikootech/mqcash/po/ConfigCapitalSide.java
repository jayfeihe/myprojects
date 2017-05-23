package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月6日
 * 配置类：资金方
 */
public class ConfigCapitalSide implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String capitalSideId;
	private String capitalSideName;
	private Integer status;//配置状态：0无效、1有效
	private Date createTime;
	private String creater;
	
	public ConfigCapitalSide() {
		// TODO Auto-generated constructor stub
	}

	public String getCapitalSideId() {
		return capitalSideId;
	}

	public void setCapitalSideId(String capitalSideId) {
		this.capitalSideId = capitalSideId;
	}

	public String getCapitalSideName() {
		return capitalSideName;
	}

	public void setCapitalSideName(String capitalSideName) {
		this.capitalSideName = capitalSideName;
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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

}
