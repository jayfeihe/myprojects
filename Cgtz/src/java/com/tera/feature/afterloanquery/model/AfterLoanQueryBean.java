package com.tera.feature.afterloanquery.model;


import java.util.Date;

import com.tera.util.DateUtils;


/**
 *功能:AfterLoanQueryBean  贷后综合查询bean
 *时间:2016年3月7日上午10:10:43
 *@author Ldh
 */
public class AfterLoanQueryBean {

	private String contractId;//合同号
	private String state;//合同状态
	private String loanId;//申请编号
	private String idType;
	private String idNo;//证件号
	private String loanType;//申请类型
	private String loanName;//借款人
	private String orgName;//业务机构名称
	private String org;//机构
	private String saleName;//业务员
	private String salesman;
	private double loanAmt;//借款金额
	private String tel;//借款人电话
	
	private String loginId;//登录id
	private String deptId;//部门id
	private String orgId;//机构id
	private String roleId;//角色id
	
	
	//合同日期
	private Date startDate;//合同开始日期
	private String startDateStr;
	//时间区间
	private Date minStartTime;
	private Date maxStartTime;
	private Date endDate;//
	private String endDateStr;
	private Date minEndTime;
	private Date maxEndTime;
    private Date minRetDate;
    private Date maxRetDate;
	private int lateNum;//合同当前期数
	private String retPlan;//还款计划

	//是否续贷
	private String isRenew;//
    //是否逾期
	private String isLate;
	
	//是否稽查
	private String isCheck;
	private String checkReportState;
	private String checkSource;
	//仓库
	private String warehouseId;
	private String warehouseName;

	//getter部分
	public String getContractId() {
		return contractId;
	}
	public String getState() {
		return state;
	}
	public String getLoanId() {
		return loanId;
	}
	public String getLoanName() {
		return loanName;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getOrg(){
		return org;
	}
	public String getSaleName() {
		return saleName;
	}
	public String getSalesman() {
		return salesman;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public String getTel() {
		return tel;
	}
	public String getLoginId() {
		return loginId;
	}
	public String getDeptId() {
		return deptId;
	}
	public String getOrgId() {
		return orgId;
	}
	public String getRoleId() {
		return roleId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public int getLateNum() {
		return lateNum;
	}
	public String getRetPlan() {
		return retPlan;
	}
	public String getIsRenew() {
		return isRenew;
	}
	public String getIsLate() {
		return isLate;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public String getCheckReportState() {
		return checkReportState;
	}
	public String getCheckSource() {
		return checkSource;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	
	public String getIdNo() {
		return idNo;
	}
	public String getLoanType() {
		return loanType;
	}
	
	public Date getMinStartTime() {
		return minStartTime;
	}
	public Date getMaxStartTime() {
		return maxStartTime;
	}
	public Date getMinEndTime() {
		return minEndTime;
	}
	public Date getMaxEndTime() {
		return maxEndTime;
	}
	
	public Date getMinRetDate() {
		return minRetDate;
	}
	public Date getMaxRetDate() {
		return maxRetDate;
	}
	
	public String getIdType() {
		return idType;
	}
	
	public String getStartDateStr() {
		return DateUtils.formatDate(startDate);
	}
	public String getEndDateStr() {
		return DateUtils.formatDate(endDate);
	}
	//setter
	public void setContracdId(String contractId) {
		this.contractId = contractId;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setOrg(String org){
		this.org=org;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setLateNum(int lateNum) {
		this.lateNum = lateNum;
	}
	public void setRetPlan(String retPlan) {
		this.retPlan = retPlan;
	}
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	public void setIsLate(String isLate) {
		this.isLate = isLate;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public void setCheckReportState(String checkReportState) {
		this.checkReportState = checkReportState;
	}
	public void setCheckSource(String checkResource) {
		this.checkSource = checkResource;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setMinStartTime(Date minStartTime) {
		this.minStartTime = minStartTime;
	}
	public void setMaxStartTime(Date maxStartTime) {
		this.maxStartTime = maxStartTime;
	}
	public void setMinEndTime(Date minEndTime) {
		this.minEndTime = minEndTime;
	}
	public void setMaxEndTime(Date maxEndTime) {
		this.maxEndTime = maxEndTime;
	}
	public void setMinRetDate(Date minRetDate) {
		this.minRetDate = minRetDate;
	}
	public void setMaxRetDate(Date maxRetDate) {
		this.maxRetDate = maxRetDate;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	
	
}
