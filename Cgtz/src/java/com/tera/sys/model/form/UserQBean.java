/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.model.form;


/**
 * @author Wallace chu
 *
 */
public class UserQBean {

	/**
	 * 登录ID号
	 */
	private String loginId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 组织ID
	 */
//	private Integer orgID;
	/**
	 * 是否管理员
	 */
	private String isAdmin;

	/**
	 * 所在岗位
	 */
	private String roleNames;

	/**
	 * 是否挂起
	 */
	private String state;

	/**
	 * 所属机构
	 * @return
	 */
	private String orgId;
	
	/**
	 * 角色
	 * @return
	 */
	private String roleId;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * （非 Javadoc）
	 * @see java.lang.Object#toString()
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("User{").append("\n");
		buffer.append("loginID: ").append(loginId).append("\n");
		buffer.append("name: ").append(name).append("\n");
//		buffer.append("orgID: ").append(orgID).append("\n");
		buffer.append("isAdmin: ").append(isAdmin).append("\n");
		buffer.append("assignRoles: ").append("\n");
		buffer.append("}").append("\n");
		return buffer.toString();
	}
}
