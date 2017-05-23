package com.sunseetech.model;

public class Img {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String category; //分类
	private String subCategory; //子分类
	private String fileName; //文件名
	private String imgPath; //影像路径
	private String contract; //合同号
	private String processState; //流程节点
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	
	private String categoryName;//分类名称

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getCategory () {
		return this.category;
	}
	public String getSubCategory () {
		return this.subCategory;
	}
	public String getFileName () {
		return this.fileName;
	}
	public String getImgPath () {
		return this.imgPath;
	}
	public String getContract () {
		return this.contract;
	}
	public String getProcessState () {
		return this.processState;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}


	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setCategory (String category) {
		this.category=category;
	}
	public void setSubCategory (String subCategory) {
		this.subCategory=subCategory;
	}
	public void setFileName (String fileName) {
		this.fileName=fileName;
	}
	public void setImgPath (String imgPath) {
		this.imgPath=imgPath;
	}
	public void setContract (String contract) {
		this.contract=contract;
	}
	public void setProcessState (String processState) {
		this.processState=processState;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}

