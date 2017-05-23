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
	private int id;
	/** 流程定义ID */
	private int defId;
	/** 流程名称 */
	private String processName = "";
	/** 任务外部引用ID */
	private String bizKey = "";
	/** 父任务外部引用ID */
	private String parentBizKey = "";
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
	
	/**父任务实例ID*/
	private int parentId;

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
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the defId
	 */
	public int getDefId() {
		return defId;
	}
	/**
	 * @param defId the defId to set
	 */
	public void setDefId(int defId) {
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
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentBizKey() {
		return parentBizKey;
	}
	public void setParentBizKey(String parentBizKey) {
		this.parentBizKey = parentBizKey;
	}
	public boolean isSubTask() {
		return !(parentId==0);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("BpmTask{");
		buffer.append("id: ").append(id).append(", ");
		buffer.append("defId: ").append(defId).append(", ");
		buffer.append("processName: ").append(processName).append(", ");
		buffer.append("bizKey: ").append(bizKey).append(", ");
		buffer.append("parentBizKey: ").append(parentBizKey).append(", ");		
		buffer.append("processer: ").append(processer).append(", ");
		buffer.append("operateor: ").append(operator).append(", ");
		buffer.append("state: ").append(state).append(", ");
		buffer.append("updateTime: ").append(DateUtils.toTimeString(updateTime)).append(", ");
		buffer.append("endFlag: ").append(endFlag).append(", ");
		buffer.append("parentId: ").append(parentId).append(", ");
		buffer.append("variables: [");
		Iterator<String> iterator = variables.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = variables.get(key);
			buffer.append(key + ": ").append(value).append(", ");
		}
		buffer.append("]").append("}");
		return buffer.toString();
	}

}
