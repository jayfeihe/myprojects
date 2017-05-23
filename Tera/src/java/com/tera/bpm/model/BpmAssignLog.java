package com.tera.bpm.model;

import com.tera.util.DateUtils;

/**
 * 
 * 流程任务分配日志实体类
 * <b>功能：</b>BpmAssignLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-03 11:38:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class BpmAssignLog {

	//属性部分
	private int id; //ID
	private int defId; //流程定义ID
	private int taskId; //流程实例ID
	private String bizKey; //业务KEY
	private String state; //节点
	private String fromProcesser; //原处理人
	private String toProcesser; //现处理人
	private java.sql.Timestamp updateTime; //更新时间
	private String updateTimeStr; //更新时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public int getDefId () {
		return this.defId;
	}
	public int getTaskId () {
		return this.taskId;
	}
	public String getBizKey () {
		return this.bizKey;
	}
	public String getState () {
		return this.state;
	}
	public String getFromProcesser () {
		return this.fromProcesser;
	}
	public String getToProcesser () {
		return this.toProcesser;
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
	public void setDefId (int defId) {
		this.defId=defId;
	}
	public void setTaskId (int taskId) {
		this.taskId=taskId;
	}
	public void setBizKey (String bizKey) {
		this.bizKey=bizKey;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setFromProcesser (String fromProcesser) {
		this.fromProcesser=fromProcesser;
	}
	public void setToProcesser (String toProcesser) {
		this.toProcesser=toProcesser;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

