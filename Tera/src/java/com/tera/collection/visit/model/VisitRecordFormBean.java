package com.tera.collection.visit.model;

public class VisitRecordFormBean {
	private String id;				//记录号
	private String appId;			// 
	private String contractNo;		//合同号
	private String relation;		//关系
	private String name;			//联系人
	
	private String mobile;			//联系方式
	private String remark;			//备注
	private String phoneSummary; 	//电催摘要
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhoneSummary() {
		return phoneSummary;
	}
	public void setPhoneSummary(String phoneSummary) {
		this.phoneSummary = phoneSummary;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
}
