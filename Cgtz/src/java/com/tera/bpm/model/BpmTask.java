/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tera.util.DateUtils;

/**
 * @author Administrator
 */
public class BpmTask {

	/** 序号 */
	private long id;
	/** 流程定义ID */
	private long defId;
	/** 流程名称 */
	private String processName = "";
	/** 外部引用ID */
	private String bizKey = "";
	/** 待处理人 */
	private String processer = "";
	/** 当前操作人，参数字段，不用序列化*/
	private String operator = "";
	/** 流程节点 */
	private String state = "";
	/** 最后处理时间 */
	private java.sql.Timestamp updateTime;
	/** 结束标志*/
	//默认流程未结束
	private String endFlag = "0";
	//任务数量
	private int taskNum;
	//当前决策 ，通过，不通过，或者其他的说明
	private String decision = "";
	//决策说明
	private String remark = "";

	/**默认系统用户*/
	public static final String SYS_USER = "sysauto";
	/**
	 * 流程变量相关
	 */
	private Map<String, Object> variables = new HashMap<String, Object>();
	/**
	 * 设置流程变量
	 * @param key key
	 * @param value value
	 */
	public void setVariable(String key, Object value) {
		variables.put(key, value);
	}
	/**
	 * 清除流程变量
	 */
	public void clearVariables() {
		variables = new HashMap<String, Object>();
	}
	/**
	 * @param key key
	 * @return String
	 */
	public String getStringVariable(String key) {
		return (String) variables.get(key);
	}
	/**
	 * @param key key
	 * @return int
	 */
	public int getIntVariable(String key) {
		return ((Integer) variables.get(key)).intValue();
	}
	/**
	 * @param key key
	 * @return double
	 */
	public double getDoubleVariable(String key) {
		return ((Double) variables.get(key)).doubleValue();
	}
	/**
	 * @param key key
	 * @return boolean
	 */
	public boolean getBooleanVariable(String key) {
		return ((Boolean) variables.get(key)).booleanValue();
	}
	/**
	 * @param key key
	 * @return Date
	 */
	public Date getDateVariable(String key) {
		return (Date) variables.get(key);
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the defId
	 */
	public long getDefId() {
		return defId;
	}
	/**
	 * @param defId the defId to set
	 */
	public void setDefId(long defId) {
		this.defId = defId;
	}

	/**
	 * @return bizKey
	 */
	public String getBizKey() {
		return bizKey;
	}
	/**
	 * @param bizKey bizKey
	 */
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	/**
	 * @return the processer
	 */
	public String getProcesser() {
		return processer;
	}
	/**
	 * @param processer the processer to set
	 */
	public void setProcesser(String processer) {
		this.processer = processer;
	}
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	/**
	 * @return the updateTime
	 */
	public java.sql.Timestamp getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the endFlag
	 */
	public String getEndFlag() {
		return endFlag;
	}
	/**
	 * @param endFlag the endFlag to set
	 */
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	/**
	 * @return the variables
	 */
	public Map<String, Object> getVariables() {
		return variables;
	}
	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}
	
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	
	
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
