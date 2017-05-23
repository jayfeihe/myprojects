package com.greenkoo.account.model;

import java.util.Date;

/**
 * 用户账户
 * 
 * @author QYANZE
 *
 */
public class UserAccount {

	private String id          ; // 主键id           
	private String account     ; // 账号             
	private String pwd         ; // 密码             
	private String compayId   ; // 所属公司           
	private String userName    ; // 联系人            
	private String email       ; // 邮箱地址           
	private String fax         ; // 传真             
	private String mobile      ; // 手机             
	private String telephone   ; // 固定电话           
	private String qq          ; // QQ             
	private int status         ; // 状态（0：无效1:有效 ）  
	private Date createTime    ; // 创建时间     
	
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCompayId() {
		return compayId;
	}
	public void setCompayId(String compayId) {
		this.compayId = compayId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
