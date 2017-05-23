package com.greenkoo.record.model.form;

import java.util.Date;

/**
 * 整改反馈表单bean
 * 
 * @author QYANZE
 *
 */
public class AdFeedBackBean {

	private String infoId;
	private Date correctionDate;
	private String remark;
	
	public AdFeedBackBean() {
		super();
	}
	public AdFeedBackBean(String infoId, Date correctionDate, String remark) {
		super();
		this.infoId = infoId;
		this.correctionDate = correctionDate;
		this.remark = remark;
	}
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public Date getCorrectionDate() {
		return correctionDate;
	}
	public void setCorrectionDate(Date correctionDate) {
		this.correctionDate = correctionDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
