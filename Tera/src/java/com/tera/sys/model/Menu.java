package com.tera.sys.model;

import java.io.Serializable;

/**
 * 
 * <br>
 * <b>功能：</b>MenuDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-23 14:14:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@SuppressWarnings("serial")
public class Menu  implements Serializable{
	
	
	private int ordernum;//排序
	private int menuLevel;
	private int type=0;			// 0 模块 、1 页面、 2 按钮
	private String state="1"; //0 不可用  1 可用
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	public int getId() {
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
