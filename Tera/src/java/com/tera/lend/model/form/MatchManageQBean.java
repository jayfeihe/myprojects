package com.tera.lend.model.form;

public class MatchManageQBean {

	private int id; //ID
	private String appId; //申请号
	private String type; //类型-回款再投/
	private String matchType; //撮合类型
	private String appTimeStr; //申请时间
	private double amount; //金额
	private String product; //产品
	private double interestRate; //利率
	private double serviceRate; //服务费率
	private int period; //期限
	private String startDateStr; //开始日期
	private String endDateStr; //结束日期
	private String orgId; //机构代码
	private String useage; //用途
	private double contractAmount; //合同金额
	private String contractStartDateStr; //合同开始日期
	private String contractEndDateStr; //合同结束日期
	private String state; //状态
	private int times; //次数
	private int lockFlag; //锁
	private String operator; //操作员
	private String orgId2; //所属机构
	private String createTimeStr; //创建日期
	private String updateTimeStr; //修改日期
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getAppTimeStr() {
		return appTimeStr;
	}
	public void setAppTimeStr(String appTimeStr) {
		this.appTimeStr = appTimeStr;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getServiceRate() {
		return serviceRate;
	}
	public void setServiceRate(double serviceRate) {
		this.serviceRate = serviceRate;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUseage() {
		return useage;
	}
	public void setUseage(String useage) {
		this.useage = useage;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getContractStartDateStr() {
		return contractStartDateStr;
	}
	public void setContractStartDateStr(String contractStartDateStr) {
		this.contractStartDateStr = contractStartDateStr;
	}
	public String getContractEndDateStr() {
		return contractEndDateStr;
	}
	public void setContractEndDateStr(String contractEndDateStr) {
		this.contractEndDateStr = contractEndDateStr;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(int lockFlag) {
		this.lockFlag = lockFlag;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOrgId2() {
		return orgId2;
	}
	public void setOrgId2(String orgId2) {
		this.orgId2 = orgId2;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

}
