package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年9月7日
 * 用户登录记录表
 */
public class UserLoginRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String recordId;
	private String loginAccount;
	private String ipAddress;
	private Integer loginMethod;
	private Integer loginResult;
	private String  descp;
	private Date loginTime;
	private Date createTime;
	
	public UserLoginRecord() {
		// TODO Auto-generated constructor stub
	}

	public UserLoginRecord(String recordId, String loginAccount,
			String ipAddress, Integer loginMethod,Integer loginResult, String descp,Date loginTime,Date createTime) {
		super();
		this.recordId = recordId;
		this.loginAccount = loginAccount;
		this.ipAddress = ipAddress;
		this.loginMethod = loginMethod;
		this.loginResult=loginResult;
		this.descp=descp;
		this.loginTime = loginTime;
		this.createTime = createTime;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(Integer loginMethod) {
		this.loginMethod = loginMethod;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(Integer loginResult) {
		this.loginResult = loginResult;
	}
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
