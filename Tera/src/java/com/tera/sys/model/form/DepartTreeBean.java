package com.tera.sys.model.form;

import java.util.List;


/**
 * 组织树实体类
 * @author QYANZE
 *
 */
public class DepartTreeBean {

	private String id; // 将机构作为id，用于页面机构value
	private String departName; 
	private String level; 
	private int parentId; 
	private String uId; // 表主键id 
	private String text;
	private List<DepartTreeBean> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getUId() {
		return uId;
	}
	public void setUId(String uId) {
		this.uId = uId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<DepartTreeBean> getChildren() {
		return children;
	}
	public void setChildren(List<DepartTreeBean> children) {
		this.children = children;
	}
}
