package com.greenkoo.record.model;

import java.util.Date;

/**
 * 整改反馈日志
 * 
 * @author QYANZE
 *
 */
public class AdFeedBackLog {

	private String id             ; // 主键id
	private String correctionId   ; // 整改反馈id
	private int status            ; // 状态
	private String remark         ; // 备注
	private Date correctionTime   ; // 整改时间
	private Date createTime       ; // 创建时间
	private String operator       ; // 操作人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorrectionId() {
		return correctionId;
	}
	public void setCorrectionId(String correctionId) {
		this.correctionId = correctionId;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
