package com.tera.customer.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-30 16:22:42<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Customer {


	
	private String customerTypeName;//字典值
	
	
	private String idTypeName;//证件类型名称
	
	public String getCustomerLever() {
		return customerLever;
	}
	public void setCustomerLever(String customerLever) {
		this.customerLever = customerLever;
	}
	public String getEngName() {
	public String getBirthdayStr() {
		return DateUtils.formatDate(this.birthday);
	}
	public String getIdIssueDateStr() {
		return DateUtils.formatDate(this.idIssueDate);
	}
	public String getIdExpiryDateStr() {
		return DateUtils.formatDate(this.idExpiryDate);
	}
	public String getCreateTimeStr() {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateTimeStr() {
		return DateUtils.formatTime(this.updateTime);
	}
	public String getAppTimeStr() {
		return DateUtils.formatTime(this.appTime);
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	public void setIdIssueDateStr(String idIssueDateStr) {
		this.idIssueDateStr = idIssueDateStr;
	}
	public void setIdExpiryDateStr(String idExpiryDateStr) {
		this.idExpiryDateStr = idExpiryDateStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	public void setAppTimeStr(String appTimeStr) {
		this.appTimeStr = appTimeStr;
	}
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}

}
