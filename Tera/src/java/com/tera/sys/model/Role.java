package com.tera.sys.model;

import java.io.Serializable;

/**
 * 
 * <br>
 * <b>功能：</b>RoleDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-26 14:09:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Role implements Serializable {

	private int id;//ID	private String name;//名称	private String description;//描述	private String type="1";//类型    1是菜单  2 是职务
	private String orgRoleLever;//职务等级
	private String leverName;
	private String flag; //标记	public int getId() {		return this.id;	}	public void setId(int id) {		this.id=id;	}	public String getName() {		return this.name;	}	public void setName(String name) {		this.name=name;	}	public String getDescription() {		return this.description;	}	public void setDescription(String description) {		this.description=description;	}	public String getType() {		return this.type;	}	public void setType(String type) {		this.type=type;	}
	public String getOrgRoleLever() {
		return orgRoleLever;
	}
	public void setOrgRoleLever(String orgRoleLever) {
		this.orgRoleLever = orgRoleLever;
	}
	public String getLeverName() {
		return leverName;
	}
	public void setLeverName(String leverName) {
		this.leverName = leverName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}