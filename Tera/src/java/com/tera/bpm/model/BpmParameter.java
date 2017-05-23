package com.tera.bpm.model;

import com.tera.util.DateUtils;

/**
 * 
 * 流程参数实体类
 * <b>功能：</b>BpmParameterDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-10-30 15:12:24<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class BpmParameter {

	//属性部分
	private long id; //ID
	private String type; //类型:1,流程实例变量；2,流程节点变量
	private long defId; //流程定义ID
	private long taskId; //流程实例ID
	private String bizKey; //业务KEY
	private String state; //节点
	private String paramName; //参数名称
	private String paramValue; //参数值
	
	private String processName;/** 流程名称 */

	//getter部分
	public long getId () {
		return this.id;
	}
	public String getType () {
		return this.type;
	}
	public long getDefId () {
		return this.defId;
	}
	public long getTaskId () {
		return this.taskId;
	}
	public String getBizKey () {
		return this.bizKey;
	}
	public String getState () {
		return this.state;
	}
	public String getParamName () {
		return this.paramName;
	}
	public String getParamValue () {
		return this.paramValue;
	}

	//setter部分
	public void setId (long id) {
		this.id=id;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setDefId (long defId) {
		this.defId=defId;
	}
	public void setTaskId (long taskId) {
		this.taskId=taskId;
	}
	public void setBizKey (String bizKey) {
		this.bizKey=bizKey;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setParamName (String paramName) {
		this.paramName=paramName;
	}
	public void setParamValue (String paramValue) {
		this.paramValue=paramValue;
	}

	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}

}

