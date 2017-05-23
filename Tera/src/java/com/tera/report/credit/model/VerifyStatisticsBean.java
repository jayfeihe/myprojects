package com.tera.report.credit.model;

/**
 * 审核统计实体
 * @author QYANZE
 * @date 2015-5-15
 */
public class VerifyStatisticsBean {

	private String name; // 姓名
	private String totalAmount; // 处理总量
	private String passAmount; // 通过量
	private String denyAmount; // 拒贷量
	private String opreateAmount; // 待处理量
	private String backAmount1; // 回退量1
	private String backAmount2; // 回退量2
	private String passPercent; // 通过率
	private String backPercent; // 回退率
	private double loanAmount; // 放款金额
	
	private String verifyDateStart; // 审核开始日期
	private String verifyDateEnd; // 审核结束日期
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassAmount() {
		return passAmount;
	}
	public void setPassAmount(String passAmount) {
		this.passAmount = passAmount;
	}
	public String getDenyAmount() {
		return denyAmount;
	}
	public void setDenyAmount(String denyAmount) {
		this.denyAmount = denyAmount;
	}
	public String getOpreateAmount() {
		return opreateAmount;
	}
	public void setOpreateAmount(String opreateAmount) {
		this.opreateAmount = opreateAmount;
	}
	public String getBackAmount1() {
		return backAmount1;
	}
	public void setBackAmount1(String backAmount1) {
		this.backAmount1 = backAmount1;
	}
	public String getBackAmount2() {
		return backAmount2;
	}
	public void setBackAmount2(String backAmount2) {
		this.backAmount2 = backAmount2;
	}
	public String getPassPercent() {
		return passPercent;
	}
	public void setPassPercent(String passPercent) {
		this.passPercent = passPercent;
	}
	public String getBackPercent() {
		return backPercent;
	}
	public void setBackPercent(String backPercent) {
		this.backPercent = backPercent;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getVerifyDateStart() {
		return verifyDateStart;
	}
	public void setVerifyDateStart(String verifyDateStart) {
		this.verifyDateStart = verifyDateStart;
	}
	public String getVerifyDateEnd() {
		return verifyDateEnd;
	}
	public void setVerifyDateEnd(String verifyDateEnd) {
		this.verifyDateEnd = verifyDateEnd;
	}
}
