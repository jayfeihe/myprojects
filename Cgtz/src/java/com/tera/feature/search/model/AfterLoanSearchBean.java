package com.tera.feature.search.model;

import java.sql.Timestamp;
import java.util.Date;

import com.tera.util.DateUtils;

/**
 *功能:AfterLoanSearchBean  稽查清单/任务bean
 *时间:2016年3月7日上午10:40:58
 *@author Ldh
 */
public class AfterLoanSearchBean {
	private String loanId;//申请编号
	
	private String contractId;//合同号
	private String orgName;//业务机构名称
	private String org;//机构
	private String state;//合同状态
    private String loanName;//借款人
	
    private String remark;//稽查跟进内容，报表需要
	private String saleName;//业务员
	private String salesman;
	private String lateNum;//合同当前期数
	private String overdueType;//逾期类型
	private Date retDate;//应还日期
	private String retDateStr;
	private Date lastDate;//最后还款日期
	private String lastDateStr;
	private int lateDays;//逾期天数
	private int startDays;
	private int endDays;
	private double loanAmt;//借款金额
	private double retInterest;
	private double sumInterestBe;//当期之前逾期利息总额
	private double retCapital;
	private String checkReportState;
	private String isAccept;
	private String product;
	private String checkState;

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
	
    private String productType;
    private String projectName;
    private String overType;
    private String acceptState;
    private String csState;
    private String crState;


	//是否续贷
	private String isRenew;//
    //是否逾期
	private String isLate;
	
	//是否稽查
	private String isCheck;
	
	private String checkSource;
	//仓库
	private String warehouseId;
	private String warehouseName;
	
	//当期逾期金额
	private double retMoney;
	//逾期合同数
	private int counm;
	//getter部分
	public int getLateDays(){
		return lateDays;
	}
	public String getOverdueType(){
		return overdueType;
	}
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
	public String getRemark(){
		return remark;
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
	public String getLateNum() {
		return lateNum;
	}
	
	public String getCheckReportState() {
		return checkReportState;
	}
	public String getCheckSource() {
		return checkSource;
	}
    public String getRetDateStr(){
    	return DateUtils.formatDate(retDate);
    }
    
    //最后还款日期
    public Date getLastDate() {
		return lastDate;
	}
	public String getLastDateStr() {
		return DateUtils.formatDate(lastDate);
	}
	public String getProduct(){
    	return product;
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
	
	
	public String getProductType() {
		return productType;
	}
	public String getProjectName(){
		return projectName;
	}
	public String getOverType() {
		return overType;
	}
	public String getAcceptState() {
		return acceptState;
	}
	public String getCsState() {
		return csState;
	}
	public String getCrState() {
		return crState;
	}
	public String getStartDateStr() {
		return DateUtils.formatDate(startDate);
	}
	public String getEndDateStr() {
		return DateUtils.formatDate(endDate);
	}
	public Date getRetDate() {
		return retDate;
	}
	public void setRetDate(Date retDate) {
		this.retDate = retDate;
	}
	public double getRetInterest() {
		return retInterest;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public void setRetInterest(double retInterest) {
		this.retInterest = retInterest;
	}
	public double getSumInterestBe() {
		return sumInterestBe;
	}
	public double getRetCapital() {
		return retCapital;
	}
	public void setRetCapital(double retCapital) {
		this.retCapital = retCapital;
	}
	public String getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
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
	public String getWarehouseId() {
		return warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setRetDateStr(String retDateStr) {
		this.retDateStr = retDateStr;
	}
	public int getStartDays() {
		return startDays;
	}
	public void setStartDays(int startDays) {
		this.startDays = startDays;
	}
	public int getEndDays() {
		return endDays;
	}
	public void setEndDays(int endDays) {
		this.endDays = endDays;
	}
	
	//setter
	
	public double getRetMoney() {
		return retMoney;
	}
	public int getCounm() {
		return counm;
	}
	public void setLateDays(int lateDays){
		this.lateDays=lateDays;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public void setLastDateStr(String lastDateStr) {
		this.lastDateStr = lastDateStr;
	}
	public void setOverdueType(String overdueType){
		this.overdueType=overdueType;
	}
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
	public void setRemark(String remark){
		this.remark=remark;
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
	public void setSumInterestBe(double sumInterestBe) {
		this.sumInterestBe = sumInterestBe;
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
	public void setLateNum(String lateNum) {
		this.lateNum = lateNum;
	}
	public void setProduct(String product){
		this.product=product;
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
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public void setProjectName(String projectName){
		this.projectName=projectName;
	}
	public void setOverType(String overType) {
		this.overType = overType;
	}
	public void setAcceptState(String acceptState) {
		this.acceptState = acceptState;
	}
	public void setCsState(String cState) {
		this.csState = cState;
	}
	public void setCrState(String crState) {
		this.crState = crState;
	}
	public void setRetMoney(double retMoney) {
		this.retMoney = retMoney;
	}
	public void setCounm(int counm) {
		this.counm = counm;
	}
	
}
