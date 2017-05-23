package com.greenkoo.record.model;

import java.util.Date;

/**
 * 整改信息反馈
 * 
 * @author QYANZE
 *
 */
public class AdFeedBack {

	private String correctionId; // 整改反馈主键id
	private long infoId;     	 // 信息的唯一标识
	private int roleType;    	 // 主体角色类型（1：广告主，2：媒体）
	private String roleUrl; 	 // 主体url
	private int status;      	 // 反馈的状态（0-未知、1-已整改）
	private String remark;   	 // 备注信息
	private Date correctionTime; // 整改时间
	private Date createTime; 	 // 创建时间
	private Date updateTime;     // 更新时间
	
	public String getCorrectionId() {
		return correctionId;
	}
	public void setCorrectionId(String correctionId) {
		this.correctionId = correctionId;
	}
	public long getInfoId() {
		return infoId;
	}
	public void setInfoId(long infoId) {
		this.infoId = infoId;
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public String getRoleUrl() {
		return roleUrl;
	}
	public void setRoleUrl(String roleUrl) {
		this.roleUrl = roleUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCorrectionTime() {
		return correctionTime;
	}
	public void setCorrectionTime(Date correctionTime) {
		this.correctionTime = correctionTime;
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
	
	/**主体类型-广告主*/
	public static final Integer ROLE_TYPE_ADVERTISER = 1;
	/**主体类型-媒体*/
	public static final Integer ROLE_TYPE_MEDIA = 2;
	/**状态-未知*/
	public static final Integer STATUS_UNKNOW = 0;
	/**状态-已整改*/
	public static final Integer STATUS_CORRECTED = 1;
}
