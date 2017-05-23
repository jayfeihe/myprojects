package com.tera.sys.model;

import com.tera.util.DateUtils;

/**
 * 
 * T_ROLE实体类
 * <b>功能：</b>RoleDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 13:46:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Role {

	//属性部分
	private int id; //ID
	private String name; //名称
	private String description; //描述
	private String lever; //角色等级(1总部，2分公司，3用户)
	private String flag; //标记(1有，0无)
	private String ext1; //
	private String ext2; //

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getName () {
		return this.name;
	}
	public String getDescription () {
		return this.description;
	}
	public String getLever () {
		return this.lever;
	}
	public String getFlag () {
		return this.flag;
	}
	public String getExt1 () {
		return this.ext1;
	}
	public String getExt2 () {
		return this.ext2;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setDescription (String description) {
		this.description=description;
	}
	public void setLever (String lever) {
		this.lever=lever;
	}
	public void setFlag (String flag) {
		this.flag=flag;
	}
	public void setExt1 (String ext1) {
		this.ext1=ext1;
	}
	public void setExt2 (String ext2) {
		this.ext2=ext2;
	}

}

