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
	
		private int id;//ID	private String name;//名称	private String description;//描述	private String url;//URL	private int parentId;//父ID
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
	public int getId() {		return this.id;	}	public void setId(int id) {		this.id=id;	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}	public String getName() {		return this.name;	}	public void setName(String name) {		this.name=name;	}	public String getDescription() {		return this.description;	}	public void setDescription(String description) {		this.description=description;	}	public String getUrl() {		return this.url;	}	public void setUrl(String url) {		this.url=url;	}	public int getOrdernum() {		return this.ordernum;	}	public void setOrdernum(int ordernum) {		this.ordernum=ordernum;	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}

