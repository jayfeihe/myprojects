package com.tera.interfaces.model;

/**
 * 评审会成员对象
 * @author QYANZE
 *
 */
public class AppJudgeUser {

	private String userId;
	private String userName;
	
	public AppJudgeUser() {
		super();
	}
	public AppJudgeUser(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
