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

import com.tera.util.DateUtils;

/**
 * @author Administrator
 */
public class BpmLog {
	/** 序号 */
	private long id;
	/** 流程定义ID */
	private long taskId;
	/** 流程定义名称 */
	private String processName;
	/** 外部引用ID */
	private String bizKey = "";
	/** 父任务外部引用ID */
	private String parentBizKey = "";
	/** 流程节点 */
	private String state = "";
	/** 待处理人 */
	private String processer = "";
	/** 处理人名称 */
	private String processerName = "";
	/** 当前操作人*/
	private String operator = "";
	/** 当前操作人名称 */
	private String operatorName = "";
	/** 流入时间 */
	private java.sql.Timestamp intime;
	/** 流出时间 */
	private java.sql.Timestamp outtime;
	/** 日志内容1 */
	private String logContent1 = "";
	/** 日志内容2 */
	private String logContent2 = "";
	/** 日志内容3 */
	private String logContent3 = "";
	/** 日志内容4 */
	private String logContent4 = "";
	/** 日志内容5 */
	private String logContent5 = "";
	/** 日志内容6 */
	private String logContent6 = "";
	/** 日志内容7 */
	private String logContent7 = "";
	/** 日志内容8 */
	private String logContent8 = "";
	/** 日志内容9 */
	private String logContent9 = "";
	/** 日志内容10 */
	private String logContent10 = "";
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
	 * @return the taskId
	 */
	public long getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}
	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	/**
	 * @return the bizKey
	 */
	public String getBizKey() {
		return bizKey;
	}
	/**
	 * @param bizKey the bizKey to set
	 */
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
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
	 * @return the processerName
	 */
	public String getProcesserName() {
		return processerName;
	}
	/**
	 * @param processerName the processerName to set
	 */
	public void setProcesserName(String processerName) {
		this.processerName = processerName;
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
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return the intime
	 */
	public java.sql.Timestamp getIntime() {
		return intime;
	}
	/**
	 * @param intime the intime to set
	 */
	public void setIntime(java.sql.Timestamp intime) {
		this.intime = intime;
	}
	/**
	 * @return the outtime
	 */
	public java.sql.Timestamp getOuttime() {
		return outtime;
	}
	/**
	 * @param outtime the outtime to set
	 */
	public void setOuttime(java.sql.Timestamp outtime) {
		this.outtime = outtime;
	}
	
	public String getParentBizKey() {
		return parentBizKey;
	}

	public void setParentBizKey(String parentBizKey) {
		this.parentBizKey = parentBizKey;
	}
	/**
	 * @return the logContent1
	 */
	public String getLogContent1() {
		return logContent1;
	}
	/**
	 * @param logContent1 the logContent1 to set
	 */
	public void setLogContent1(String logContent1) {
		this.logContent1 = logContent1;
	}
	/**
	 * @return the logContent2
	 */
	public String getLogContent2() {
		return logContent2;
	}
	/**
	 * @param logContent2 the logContent2 to set
	 */
	public void setLogContent2(String logContent2) {
		this.logContent2 = logContent2;
	}
	/**
	 * @return the logContent3
	 */
	public String getLogContent3() {
		return logContent3;
	}
	/**
	 * @param logContent3 the logContent3 to set
	 */
	public void setLogContent3(String logContent3) {
		this.logContent3 = logContent3;
	}
	/**
	 * @return the logContent4
	 */
	public String getLogContent4() {
		return logContent4;
	}
	/**
	 * @param logContent4 the logContent4 to set
	 */
	public void setLogContent4(String logContent4) {
		this.logContent4 = logContent4;
	}
	/**
	 * @return the logContent5
	 */
	public String getLogContent5() {
		return logContent5;
	}
	/**
	 * @param logContent5 the logContent5 to set
	 */
	public void setLogContent5(String logContent5) {
		this.logContent5 = logContent5;
	}
	/**
	 * @return the logContent6
	 */
	public String getLogContent6() {
		return logContent6;
	}
	/**
	 * @param logContent6 the logContent6 to set
	 */
	public void setLogContent6(String logContent6) {
		this.logContent6 = logContent6;
	}
	/**
	 * @return the logContent7
	 */
	public String getLogContent7() {
		return logContent7;
	}
	/**
	 * @param logContent7 the logContent7 to set
	 */
	public void setLogContent7(String logContent7) {
		this.logContent7 = logContent7;
	}
	/**
	 * @return the logContent8
	 */
	public String getLogContent8() {
		return logContent8;
	}
	/**
	 * @param logContent8 the logContent8 to set
	 */
	public void setLogContent8(String logContent8) {
		this.logContent8 = logContent8;
	}
	/**
	 * @return the logContent9
	 */
	public String getLogContent9() {
		return logContent9;
	}
	/**
	 * @param logContent9 the logContent9 to set
	 */
	public void setLogContent9(String logContent9) {
		this.logContent9 = logContent9;
	}
	/**
	 * @return the logContent10
	 */
	public String getLogContent10() {
		return logContent10;
	}
	/**
	 * @param logContent10 the logContent10 to set
	 */
	public void setLogContent10(String logContent10) {
		this.logContent10 = logContent10;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("BpmLog{");
		buffer.append("id: ").append(id).append(", ");
		buffer.append("taskId: ").append(taskId).append(", ");
		buffer.append("processName: ").append(processName).append(", ");
		buffer.append("bizKey: ").append(bizKey).append(", ");
		buffer.append("parentBizKey: ").append(parentBizKey).append(", ");		
		buffer.append("state: ").append(state).append(", ");
		buffer.append("processer: ").append(processer).append(", ");
		buffer.append("operateor: ").append(operator).append(", ");
		buffer.append("intime: ").append(DateUtils.toTimeString(intime)).append(", ");
		buffer.append("outtime: ").append(DateUtils.toTimeString(outtime)).append(", ");
		buffer.append("logContent1: ").append(logContent1).append(", ");
		buffer.append("logContent2: ").append(logContent2).append(", ");
		buffer.append("logContent3: ").append(logContent3).append(", ");
		buffer.append("logContent4: ").append(logContent4).append(", ");
		buffer.append("logContent5: ").append(logContent5).append(", ");
		buffer.append("logContent6: ").append(logContent6).append(", ");
		buffer.append("logContent7: ").append(logContent7).append(", ");
		buffer.append("logContent8: ").append(logContent8).append(", ");
		buffer.append("logContent9: ").append(logContent9).append(", ");
		buffer.append("logContent10: ").append(logContent10).append(", ");
		buffer.append("}");
		return buffer.toString();
	}

	//从logContent4得到 退回码|拒贷码
	public String[] getCodes(){
		if("退回码".equals(this.logContent3) || "拒贷码".equals(logContent3)){
			String code = this.getLogContent4();
			String code1 = code.substring(5, 8);
			String code2 = code.substring(15, 20);
			return new String[]{code1,code2};
		}
		return new String[]{null,null};
	}
	
	
}
