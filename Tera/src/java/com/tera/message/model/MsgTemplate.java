package com.tera.message.model;

import com.tera.util.DateUtils;

/**
 * 
 * 模板表实体类
 * <b>功能：</b>TemplateDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 11:58:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class MsgTemplate {

	//属性部分
	private int id; //id
	private String type; //类型（个人，群发）
	private String name; //模板名称
	private String content; //内容
	private String state; //状态
	private java.sql.Timestamp remindTime; //提醒时间
	private String remindTimeStr; //提醒时间
	private String remark; //备注
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getType () {
		return this.type;
	}
	public String getName () {
		return this.name;
	}
	public String getContent () {
		return this.content;
	}
	public String getState () {
		return this.state;
	}
	public java.sql.Timestamp getRemindTime () {
		return this.remindTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getRemindTimeStr () {
		return DateUtils.formatDate(this.remindTime,"HH:mm:ss");
	}
	public String getRemark () {
		return this.remark;
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateUid () {
		return this.updateUid;
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setContent (String content) {
		this.content=content;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setRemindTime (java.sql.Timestamp remindTime) {
		this.remindTime=remindTime;
	}
	public void setRemindTimeStr (String remindTimeStr) {
		this.remindTimeStr=remindTimeStr;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

