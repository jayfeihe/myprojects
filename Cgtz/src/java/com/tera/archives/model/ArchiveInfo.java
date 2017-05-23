package com.tera.archives.model;

import com.tera.util.DateUtils;

/**
 * 档案管理实体类
 * 
 * @author QYANZE
 *
 */
public class ArchiveInfo {

	private int id; // 主键          
	private String contractId; // 合同号 
	private String name; // 姓名       
	private String orgId;  // 机构    
	private java.util.Date startDate; // 合同开始时间  
	private java.util.Date endDate;   // 合同结束时间  
	
	private String startDateStr; //合同开始时间显示 
	private String endDateStr; //合同结束时间显示 
	
	private String type;  // 类型      
	private double loanAmt;  // 借款金额  
	private String remarks;  // 备注  
	
	private String startDateMin; // 合同开始时间
	private String startDateMax; // 合同开始时间
	private String endDateMin; // 合同结束时间
	private String endDateMax; // 合同结束时间
	
	private String orgName; // 分公司名称
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public java.util.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	public java.util.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	public String getStartDateStr() {
		return DateUtils.formatDate(startDate);
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return DateUtils.formatDate(endDate);
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStartDateMin() {
		return startDateMin;
	}
	public void setStartDateMin(String startDateMin) {
		this.startDateMin = startDateMin;
	}
	public String getStartDateMax() {
		return startDateMax;
	}
	public void setStartDateMax(String startDateMax) {
		this.startDateMax = startDateMax;
	}
	public String getEndDateMin() {
		return endDateMin;
	}
	public void setEndDateMin(String endDateMin) {
		this.endDateMin = endDateMin;
	}
	public String getEndDateMax() {
		return endDateMax;
	}
	public void setEndDateMax(String endDateMax) {
		this.endDateMax = endDateMax;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
