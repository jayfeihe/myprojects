package com.greenkoo.sys.model;

import java.util.Date;

/**
 * 登录日志记录
 * 
 * @author QYANZE
 *
 */
public class UserLogin {

	private String id               ; // 主键id
	private String account          ; // 登录账号
	private String ipAddress        ; // 用户IP
	private int loginResult         ; // 登录结果-1:成功，0：失败
	private String loginDescription ; // 失败时记录错误信息
	private Date loginTime          ; // 登录时间
	private Date createTime         ; // 创建时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(int loginResult) {
		this.loginResult = loginResult;
	}
	public String getLoginDescription() {
		return loginDescription;
	}
	public void setLoginDescription(String loginDescription) {
		this.loginDescription = loginDescription;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**登录结果-成功*/
	public static final int LOGIN_RESULT_SUCCESS = 1;
	/**登录结果-失败*/
	public static final int LOGIN_RESULT_FAIL = 0;
}
