package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信请求数据尾部签名
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:43:54
	*/
	
public class SecurityInfoReqQhzx {
		/*signatureValue  签名
		userName  虚拟用户
		userPassword  密码*/
	
	private String signatureValue;
	private String userName;
	private String userPassword;
	public String getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
