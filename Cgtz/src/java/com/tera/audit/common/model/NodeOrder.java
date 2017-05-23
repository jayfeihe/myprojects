package com.tera.audit.common.model;

/**
 * 
 * 流程环节表实体类
 * <b>功能：</b>NodeOrderDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-09 14:19:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class NodeOrder {

	//属性部分
	private int id; //ID
	private String type; //
	private int num; //
	private String name; //名称
	
	private String keyProp; // 用于页面下拉显示
	private String keyValue; // 用于页面下拉显示

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getType () {
		return this.type;
	}
	public int getNum () {
		return this.num;
	}
	public String getName () {
		return this.name;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setNum (int num) {
		this.num=num;
	}
	public void setName (String name) {
		this.name=name;
	}
	public String getKeyProp() {
		return name;
	}
	public String getKeyValue() {
		return name;
	}
}

