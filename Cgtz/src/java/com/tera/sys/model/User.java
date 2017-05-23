package com.tera.sys.model;

import java.io.Serializable;

/**
 * 
 * <br>
 * <b>功能：</b>UserDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-23 10:47:00<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class User  implements Serializable{
	
		private int id;//ID	private String loginId;//登录ID	private String name;//姓名	private String password;//密码	private String gender;//性别	private String email;//EMAIL	private String phone;//固定电话	private String mobile;//手机	private int isAdmin;//管理员	private String state;//状态
	//add bywangyongliang 20150623
	private int roleId;//角色id
	private String orgId;//机构id
	
	private String roleLevel; // 所属层次
			public int getId() {		return this.id;	}	public void setId(int id) {		this.id=id;	}	public String getLoginId() {		return this.loginId;	}	public void setLoginId(String loginId) {		this.loginId=loginId;	}	public String getName() {		return this.name;	}	public void setName(String name) {		this.name=name;	}	public String getPassword() {		return this.password;	}	public void setPassword(String password) {		this.password=password;	}	public String getGender() {		return this.gender;	}	public void setGender(String gender) {		this.gender=gender;	}	public String getEmail() {		return this.email;	}	public void setEmail(String email) {		this.email=email;	}	public String getPhone() {		return this.phone;	}	public void setPhone(String phone) {		this.phone=phone;	}	public String getMobile() {		return this.mobile;	}	public void setMobile(String mobile) {		this.mobile=mobile;	}	public int getIsAdmin() {		return this.isAdmin;	}	public void setIsAdmin(int isAdmin) {		this.isAdmin=isAdmin;	}	public String getState() {		return this.state;	}	public void setState(String state) {		this.state=state;	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}
}

