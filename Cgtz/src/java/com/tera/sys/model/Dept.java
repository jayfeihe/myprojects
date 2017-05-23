package com.tera.sys.model;

/**
 * 
 * 部门机构表实体类
 * <b>功能：</b>DeptDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 09:38:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Dept {

	//属性部分
	private int id; //ID
	private String name; //名称
	private String description; //描述

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

}

