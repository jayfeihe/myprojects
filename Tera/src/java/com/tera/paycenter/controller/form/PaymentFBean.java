package com.tera.paycenter.controller.form;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>PaymentDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-23 13:08:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class PaymentFBean {


	private String userType; // 客户类型 01 个人  02 机构
	private String idType; //证件类型
	private String idNo; //证件编码
	private String userName; //客户name
	private String vcode; //验证码
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

}

