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


/**
 * @author Administrator
 */
public class BpmDefine {

	/** 流程定义ID */
	private int id;
	/** 流程定义文件路径 */
	private String processDefFile = "";
	/** 流程名称 */
	private String processName = "";
	/** 备注 */
	private String remark = "";

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
	 * @return the processDefFile
	 */
	public String getProcessDefFile() {
		return processDefFile;
	}
	/**
	 * @param processDefFile the processDefFile to set
	 */
	public void setProcessDefFile(String processDefFile) {
		this.processDefFile = processDefFile;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("BpmDefine{");
		buffer.append("id: ").append(id).append(", ");
		buffer.append("processDefFile: ").append(processDefFile).append(", ");
		buffer.append("processName: ").append(processName).append(", ");
		buffer.append("remark: ").append(remark).append(", ");
		buffer.append("}");
		return buffer.toString();
	}
}
