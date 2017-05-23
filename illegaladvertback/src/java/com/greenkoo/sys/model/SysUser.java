package com.greenkoo.sys.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台登录用户
 * 
 * @author QYANZE
 *
 */
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;         // 用户主键id
	private String nickName;       // 昵称
	private String account;        // 登录账户
	private String pwd;            // 密码
	private Integer status;        // 状态：0：无效，1：有效
	private Date createTime;       // 创建时间
	private Date updateTime;       // 更新时间
	private String operator;       // 操作人
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
