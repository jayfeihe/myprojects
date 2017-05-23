package com.tera.interfaces.model;

import com.tera.util.DateUtils;

/**
 * 
 * 接口对接,日志实体类
 * <b>功能：</b>InterfaceLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-17 10:07:47<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class InterfaceLog {

	//属性部分
	private int id; //
	private String type; //接口类型，对应URL
	private String bizKey; //业务ID
	private String para; //参数
	private int count; //发送次数
	private String rspCode; //反馈代码
	private String rspMsg; //反馈信息
	private String state; //状态（1未处理，2成功3失败）
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getType () {
		return this.type;
	}
	public String getBizKey () {
		return this.bizKey;
	}
	public String getPara () {
		return this.para;
	}
	public int getCount () {
		return this.count;
	}
	public String getRspCode () {
		return this.rspCode;
	}
	public String getRspMsg () {
		return this.rspMsg;
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

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setBizKey (String bizKey) {
		this.bizKey=bizKey;
	}
	public void setPara (String para) {
		this.para=para;
	}
	public void setCount (int count) {
		this.count=count;
	}
	public void setRspCode (String rspCode) {
		this.rspCode=rspCode;
	}
	public void setRspMsg (String rspMsg) {
		this.rspMsg=rspMsg;
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

}

