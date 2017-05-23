package com.tera.batch.errorLog.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>BatchErrorLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-11 16:12:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class BatchErrorLog {

	//属性部分
	private int id; //ID
	private String jobName; //JOB名称
	private String bizKey; //业务Key
	private String ext1; //扩展1
	private String ext2; //扩展2
	private java.lang.Object errorMsg; //错误原因
	private String remark; //备注
	private String operator; //操作员
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getJobName () {
		return this.jobName;
	}
	public String getBizKey () {
		return this.bizKey;
	}
	public String getExt1 () {
		return this.ext1;
	}
	public String getExt2 () {
		return this.ext2;
	}
	public java.lang.Object getErrorMsg () {
		return this.errorMsg;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getOperator () {
		return this.operator;
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
	public void setJobName (String jobName) {
		this.jobName=jobName;
	}
	public void setBizKey (String bizKey) {
		this.bizKey=bizKey;
	}
	public void setExt1 (String ext1) {
		this.ext1=ext1;
	}
	public void setExt2 (String ext2) {
		this.ext2=ext2;
	}
	public void setErrorMsg (java.lang.Object errorMsg) {
		this.errorMsg=errorMsg;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setOperator (String operator) {
		this.operator=operator;
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

