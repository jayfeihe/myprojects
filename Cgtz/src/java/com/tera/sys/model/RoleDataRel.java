package com.tera.sys.model;

/**
 * 
 * 机构的数据权限，和T_USER_EXT中的内容不一样实体类
 * <b>功能：</b>RoleDataRelDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 19:16:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RoleDataRel {

	//属性部分
	private String loginId; //登录名
	private String orgId; //机构代码
	private String roleId; //角色id
	private String orgName;//机构名称

	public RoleDataRel() {
		super();
	}
	public RoleDataRel(String loginId, String orgId, String roleId) {
		super();
		this.loginId = loginId;
		this.orgId = orgId;
		this.roleId = roleId;
	}
	public RoleDataRel(String loginId, String orgId, String roleId,String orgName){
		super();
		this.loginId = loginId;
		this.orgId = orgId;
		this.roleId = roleId;
		this.orgName=orgName;
	}
	//getter部分
	public String getLoginId () {
		return this.loginId;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getRoleId () {
		return this.roleId;
	}
    
	public String getOrgName() {
		return orgName;
	}
	//setter部分
	public void setLoginId (String loginId) {
		this.loginId=loginId;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setRoleId (String roleId) {
		this.roleId=roleId;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    
	
}

