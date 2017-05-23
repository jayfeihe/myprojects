package com.tera.sys.model;

import com.tera.util.DateUtils;

/**
 * 
 * 用户信息扩展表实体类
 * <b>功能：</b>UserExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 16:47:09<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class UserExt {

	//属性部分
	private String loginId; //
	private String orgId; //
	private int deptId; //
	private int roleId; //

	public UserExt() {
		super();
	}
	public UserExt(String loginId, String orgId, int roleId) {
		super();
		this.loginId = loginId;
		this.orgId = orgId;
		this.roleId = roleId;
	}
	public UserExt(String loginId, String orgId, int deptId, int roleId) {
		super();
		this.loginId = loginId;
		this.orgId = orgId;
		this.deptId = deptId;
		this.roleId = roleId;
	}
	//getter部分
	public String getLoginId () {
		return this.loginId;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public int getDeptId () {
		return this.deptId;
	}
	public int getRoleId () {
		return this.roleId;
	}

	//setter部分
	public void setLoginId (String loginId) {
		this.loginId=loginId;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setDeptId (int deptId) {
		this.deptId=deptId;
	}
	public void setRoleId (int roleId) {
		this.roleId=roleId;
	}

}

