package com.tera.feature.olstatus.model;

import java.sql.Timestamp;
import java.util.Date;

import com.tera.util.DateUtils;

/**
 * 
 * OnLineStates实体类
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class OnLineStates {
	// 属性部分
	private int id;
	private String loanId;
	private String contractId;
	private String isCur;//是否当前状态(0否1是)
	private String state;//状态
	private Timestamp logTime;//日志时间
	private String logTimeStr;
	private String remarks;//备注
	private Timestamp createTime;//创建时间
	private String createTimeStr;
	
	//搜索区间
	private Date minLogDate;
	private Date maxLogDate;
	
	//关联字段
	private String org;
	private String orgName;
	private Double loanAmt;//合同金额
	private String conState;//合同状态
	private String loanName;//申请人/机构名称
	
		
	//getter部分
	public int getId() {
		return id;
	}
	public String getLoanId() {
		return loanId;
	}
	public String getContractId() {
		return contractId;
	}
	public String getIsCur() {
		return isCur;
	}
	public String getState() {
		return state;
	}
	public Timestamp getLogTime() {
		return logTime;
	}
	public String getLogTimeStr() {
		return DateUtils.formatTime(logTime);
	}
	public String getRemarks() {
		return remarks;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public String getCreateTimeStr() {
		return DateUtils.formatTime(createTime);
	}
	//区间
	public Date getMinLogDate() {
		return minLogDate;
	}
	public Date getMaxLogDate() {
		return maxLogDate;
	}
	
	public String getOrg() {
		return org;
	}
	public String getOrgName() {
		return orgName;
	}
	public Double getLoanAmt() {
		return loanAmt;
	}
	public String getConState() {
		return conState;
	}
	public String getLoanName() {
		return loanName;
	}
	//setter部分
	public void setId(int id) {
		this.id = id;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setIsCur(String isCur) {
		this.isCur = isCur;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}
	public void setLogTimeStr(String logTimeStr) {
		this.logTimeStr = logTimeStr;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	//区间
	public void setMinLogDate(Date minLogDate) {
		this.minLogDate = minLogDate;
	}
	public void setMaxLogDate(Date maxLogDate) {
		this.maxLogDate = maxLogDate;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setLoanAmt(Double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setConState(String conState) {
		this.conState = conState;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	
}
