package com.tera.report.credit.model;

import com.tera.report.excel.ExcelResource;

/**违约统计Bean
 * @author QYANZE
 *
 */
public class ViolateReportBean {

	@ExcelResource(title="应还总额",order=1)
	private double totalAmount; // 应还总额
	
	@ExcelResource(title="已还总额",order=2)
	private double repayAmount; // 已还金额
	
	@ExcelResource(title="违约金额",order=3)
	private double violateAmount; // 违约金额
	
	@ExcelResource(title="违约期数",order=4)
	private int violatePeriod; // 违约总期数
	
	@ExcelResource(title="正常期数",order=5)
	private int normalPeriod; // 正常还款总期数
	
	@ExcelResource(title="机构名称",order=6)
	private String orgName; // 机构名称
	
	private String orgId; // 机构码
	
	private String violateDateStart; // 统计开始日期
	
	private String violateDateEnd; // 统计结束日期

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public double getViolateAmount() {
		return violateAmount;
	}

	public void setViolateAmount(double violateAmount) {
		this.violateAmount = violateAmount;
	}

	public int getViolatePeriod() {
		return violatePeriod;
	}

	public void setViolatePeriod(int violatePeriod) {
		this.violatePeriod = violatePeriod;
	}

	public int getNormalPeriod() {
		return normalPeriod;
	}

	public void setNormalPeriod(int normalPeriod) {
		this.normalPeriod = normalPeriod;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getViolateDateStart() {
		return violateDateStart;
	}

	public void setViolateDateStart(String violateDateStart) {
		this.violateDateStart = violateDateStart;
	}

	public String getViolateDateEnd() {
		return violateDateEnd;
	}

	public void setViolateDateEnd(String violateDateEnd) {
		this.violateDateEnd = violateDateEnd;
	}
}
