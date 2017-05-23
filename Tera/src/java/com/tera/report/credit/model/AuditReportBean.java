package com.tera.report.credit.model;

import com.tera.report.excel.ExcelResource;

/**
 * 信审统计实体类
 * @author QYANZE
 *
 */
public class AuditReportBean {

	@ExcelResource(title="处理总量",order=1)
	private Integer totalAmount;// 处理总量
	
	@ExcelResource(title="通过量",order=2)
	private Integer passAmount; // 通过量
	
	@ExcelResource(title="拒贷量",order=3)
	private Integer denyAmount; // 拒贷量
	
	@ExcelResource(title="其他量",order=4)
	private Integer otherAmount; // 其他
	
	@ExcelResource(title="决策人",order=5)
	private String auditUser; // 决策人
	/*
	 * 查询条件字段
	 */
	private String auditType; // 信审类型
	private String auditDateStart; // 信审开始日期
	private String auditDateEnd; // 信审结束日期
	
	public AuditReportBean() {
		super();
	}
	public AuditReportBean(Integer totalAmount, Integer passAmount, Integer denyAmount, Integer otherAmount) {
		super();
		this.totalAmount = totalAmount;
		this.passAmount = passAmount;
		this.denyAmount = denyAmount;
		this.otherAmount = otherAmount;
	}
	public Integer getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getPassAmount() {
		return passAmount;
	}
	public void setPassAmount(Integer passAmount) {
		this.passAmount = passAmount;
	}
	public Integer getDenyAmount() {
		return denyAmount;
	}
	public void setDenyAmount(Integer denyAmount) {
		this.denyAmount = denyAmount;
	}
	public Integer getOtherAmount() {
		return totalAmount - passAmount - denyAmount;
	}
	public void setOtherAmount(Integer otherAmount) {
		this.otherAmount = otherAmount;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getAuditDateStart() {
		return auditDateStart;
	}
	public void setAuditDateStart(String auditDateStart) {
		this.auditDateStart = auditDateStart;
	}
	public String getAuditDateEnd() {
		return auditDateEnd;
	}
	public void setAuditDateEnd(String auditDateEnd) {
		this.auditDateEnd = auditDateEnd;
	}
}
