package com.tera.accounting.model.form;

/**对账查询实体
 * @author QYANZE
 *
 */
public class AccounttingQBean {

	private String contractNo; // 合同编号
	private String inOut; // 收付（1：收，2：付）
	private String actualAmountMin; // 最小实际支付金额
	private String actualAmountMax; // 最大实际支付金额
	private String createTimeMin; // 最小支付日期
	private String createTimeMax; // 最大支付日期
	
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getInOut() {
		return inOut;
	}
	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
	public String getActualAmountMin() {
		return actualAmountMin;
	}
	public void setActualAmountMin(String actualAmountMin) {
		this.actualAmountMin = actualAmountMin;
	}
	public String getActualAmountMax() {
		return actualAmountMax;
	}
	public void setActualAmountMax(String actualAmountMax) {
		this.actualAmountMax = actualAmountMax;
	}
	public String getCreateTimeMin() {
		return createTimeMin;
	}
	public void setCreateTimeMin(String createTimeMin) {
		this.createTimeMin = createTimeMin;
	}
	public String getCreateTimeMax() {
		return createTimeMax;
	}
	public void setCreateTimeMax(String createTimeMax) {
		this.createTimeMax = createTimeMax;
	}
}
