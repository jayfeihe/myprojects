package com.tera.sys.model;

import java.util.List;

import com.tera.util.DateUtils;

/**
 * 
 * 组织管理实体类
 * <b>功能：</b>DepartDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-22 18:05:02<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Depart {

	//属性部分
	private int id; //ID
	private String departCode; //组织代码
	private String departName; //组织名称
	private String remarks; //说明
	private String level; //级别
	private int orderNum; //排序
	private String operator; //操作员
	private String state; //状态
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期
	private int parentId; //父组织ID
	private String orgId; //数据权限
	
	
	private String text;//组织名称(combotree用)
	private List<Depart> children;//组织的孩子集合
	private int noId;//修改的时候选择上级菜单要去掉其本身及以下的自孩子, 此为节点本身的ID

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getDepartCode () {
		return this.departCode;
	}
	public String getDepartName () {
		return this.departName;
	}
	public String getRemarks () {
		return this.remarks;
	}
	public String getLevel () {
		return this.level;
	}
	public int getOrderNum () {
		return this.orderNum;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getState () {
		return this.state;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}
	public int getParentId () {
		return this.parentId;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setDepartCode (String departCode) {
		this.departCode=departCode;
	}
	public void setDepartName (String departName) {
		this.departName=departName;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
	}
	public void setLevel (String level) {
		this.level=level;
	}
	public void setOrderNum (int orderNum) {
		this.orderNum=orderNum;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}
	public void setParentId (int parentId) {
		this.parentId=parentId;
	}
	public List<Depart> getChildren() {
		return children;
	}
	public void setChildren(List<Depart> children) {
		this.children = children;
	}
	public String getText() {
		return this.departName;
	}
	public void setText(String text) {
		this.text = this.departName;
	}
	public int getNoId() {
		return noId;
	}
	public void setNoId(int noId) {
		this.noId = noId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}

