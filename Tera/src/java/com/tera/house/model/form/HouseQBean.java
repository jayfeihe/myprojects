package com.tera.house.model.form;


public class HouseQBean {

	private String appId;
	private String name;
	private String idNo;
	private String inputTimeMin;
	private String inputTimeMax;
	private String belongChannel;
	private String product;
	private String staffNo;
	private String stateTask;
	private String appState;
	
	private String processer; //当前处理人
	
	private String product1;  		//决策的产品
	private String contractNo;  	//合同编号
	private String appCode;  		//申请编号
	private String orgId;  			//营业部
	private String departId;        //组织id
	
	private String operator;//为复核添加
	private String updateTimeMin;//为复核，反欺诈添加
	private String updateTimeMax;
	
	/*----------------功能区 拒贷管理---------------------*/
	private String platformName; //平台选择
	
	public String getUpdateTimeMin() {
		return updateTimeMin;
	}
	public void setUpdateTimeMin(String updateTimeMin) {
		this.updateTimeMin = updateTimeMin;
	}
	public String getUpdateTimeMax() {
		return updateTimeMax;
	}
	public void setUpdateTimeMax(String updateTimeMax) {
		this.updateTimeMax = updateTimeMax;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBelongChannel() {
		return belongChannel;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getStateTask() {
		return stateTask;
	}
	public void setStateTask(String stateTask) {
		this.stateTask = stateTask;
	}
	public String getInputTimeMin() {
		return inputTimeMin;
	}
	public void setInputTimeMin(String inputTimeMin) {
		this.inputTimeMin = inputTimeMin;
	}
	public String getInputTimeMax() {
		return inputTimeMax;
	}
	public void setInputTimeMax(String inputTimeMax) {
		this.inputTimeMax = inputTimeMax;
	}
	public String getProcesser() {
		return processer;
	}
	public void setProcesser(String processer) {
		this.processer = processer;
	}
	public void setBelongChannel(String belongChannel) {
		this.belongChannel = belongChannel;
	}
	public String getProduct1() {
		return product1;
	}
	public void setProduct1(String product1) {
		this.product1 = product1;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getAppState() {
		return appState;
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
}
