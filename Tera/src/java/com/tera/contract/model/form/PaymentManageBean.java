package com.tera.contract.model.form;

public class PaymentManageBean {

		public String contractNo;		//合同编码
		public String processer;		//流程待处理人
		public String orgId;			//机构编码
		public String states;			//包含状态 字符串 数组 
		public String nonStates;		//不包含状态 字符串 数组 
		public String name;				//姓名
		public String idType;			//证件号码
		public String idNo ;			//证件号码
		
		public int contractId;				//合同ID
		public String buttonType ;			//按钮类型        abatement 减免 ；realTime 实时还款 ；advancePayment 提前一次性还清
		public double abatementAmount;		//减免金额
		public double realTimeAmount;		//实时还款金额
		public double dghkAmount;			//对公还款金额
		public double publicAdvanceAmount;	//对公一次性还款金额
		private String paymentState;		//还款状态
		
		private int isAsc;//控制违约期数 0,不排序;1,正序;2,倒序;
		
		/*------------还款管理查询条件-------------*/
		private String loanProduct;
		private String repayMentDateMin;
		private String repayMentDateMax;
		private String isOverdue;
		private String bankAccountName;
		private String loanIdNo;
		private String belongChannel;
		
		public double getAbatementAmount() {
			return abatementAmount;
		}
		public void setAbatementAmount(double abatementAmount) {
			this.abatementAmount = abatementAmount;
		}
		public double getRealTimeAmount() {
			return realTimeAmount;
		}
		public void setRealTimeAmount(double realTimeAmount) {
			this.realTimeAmount = realTimeAmount;
		}
		public int getContractId() {
			return contractId;
		}
		public void setContractId(int contractId) {
			this.contractId = contractId;
		}
		public String getButtonType() {
			return buttonType;
		}
		public void setButtonType(String buttonType) {
			this.buttonType = buttonType;
		}
		public String getContractNo() {
			return contractNo;
		}
		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}
		public String getProcesser() {
			return processer;
		}
		public void setProcesser(String processer) {
			this.processer = processer;
		}
		public String getOrgId() {
			return orgId;
		}
		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
		public String getStates() {
			return states;
		}
		public void setStates(String states) {
			this.states = states;
		}
		public String getNonStates() {
			return nonStates;
		}
		public void setNonStates(String nonStates) {
			this.nonStates = nonStates;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIdType() {
			return idType;
		}
		public void setIdType(String idType) {
			this.idType = idType;
		}
		public String getIdNo() {
			return idNo;
		}
		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}
		public String getLoanProduct() {
			return loanProduct;
		}
		public void setLoanProduct(String loanProduct) {
			this.loanProduct = loanProduct;
		}
		
		public String getRepayMentDateMin() {
			return repayMentDateMin;
		}
		public void setRepayMentDateMin(String repayMentDateMin) {
			this.repayMentDateMin = repayMentDateMin;
		}
		public String getRepayMentDateMax() {
			return repayMentDateMax;
		}
		public void setRepayMentDateMax(String repayMentDateMax) {
			this.repayMentDateMax = repayMentDateMax;
		}
		public String getIsOverdue() {
			return isOverdue;
		}
		public void setIsOverdue(String isOverdue) {
			this.isOverdue = isOverdue;
		}
		public String getBankAccountName() {
			return bankAccountName;
		}
		public void setBankAccountName(String bankAccountName) {
			this.bankAccountName = bankAccountName;
		}
		public String getLoanIdNo() {
			return loanIdNo;
		}
		public void setLoanIdNo(String loanIdNo) {
			this.loanIdNo = loanIdNo;
		}
		public double getDghkAmount() {
			return dghkAmount;
		}
		public void setDghkAmount(double dghkAmount) {
			this.dghkAmount = dghkAmount;
		}
		public double getPublicAdvanceAmount() {
			return publicAdvanceAmount;
		}
		public void setPublicAdvanceAmount(double publicAdvanceAmount) {
			this.publicAdvanceAmount = publicAdvanceAmount;
		}
		public String getBelongChannel() {
			return belongChannel;
		}
		public void setBelongChannel(String belongChannel) {
			this.belongChannel = belongChannel;
		}
		public String getPaymentState() {
			return paymentState;
		}
		public void setPaymentState(String paymentState) {
			this.paymentState = paymentState;
		}
		public int getIsAsc() {
			return isAsc;
		}
		public void setIsAsc(int isAsc) {
			this.isAsc = isAsc;
		}
	
}
