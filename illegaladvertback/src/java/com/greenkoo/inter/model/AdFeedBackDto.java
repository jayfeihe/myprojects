package com.greenkoo.inter.model;

public class AdFeedBackDto {

	private String infoId; // 信息的唯一标识
	private String status; // 反馈的状态（0-未知、1-已整改）
	private String remark; // 备注信息
	private String sign;   // 签名
	
	public AdFeedBackDto() {
		super();
	}
	public AdFeedBackDto(String infoId, String status, String remark) {
		super();
		this.infoId = infoId;
		this.status = status;
		this.remark = remark;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
