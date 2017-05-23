package com.tera.car.model.form;


/**
 * 查重实体类
 * 
 * @author QYANZE
 * @date 2015-5-22
 */
public class RepeatCheckQBean {

	private String appId; // 申请id
	private String idNo; // 身份证号
	private String name; // 借款人姓名
	private String spouseIdNo; // 配偶身份证号
	private String spouseName; // 配偶姓名
	
	private String flag; // 夫妻查重标志（0：借款人配偶是历史借款人；1：借款人自己是历史借款人配偶）
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpouseIdNo() {
		return spouseIdNo;
	}
	public void setSpouseIdNo(String spouseIdNo) {
		this.spouseIdNo = spouseIdNo;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
