package com.tera.report.credit.model;

import java.util.Date;

import com.tera.report.excel.ExcelResource;

/**放款统计bean
 * @author QYANZE
 *
 */
public class LendReportBean {

	@ExcelResource(title="申请编号",order=1)
	private String appId; // 申请号
	
	@ExcelResource(title="产品",order=2)
	private String product; // 产品
	
	@ExcelResource(title="放款金额",order=1)
	private double lendAmount; // 放款金额
	
	@ExcelResource(title="城市",order=3)
	private String city; // 城市
	
	@ExcelResource(title="放款完成日期 ",order=4)
	private Date lendDate; // 放款完成日期 
	
	private String lendDateStart; // 放款日期开始
	private String lendDateEnd; // 放款日期结束
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getLendAmount() {
		return lendAmount;
	}
	public void setLendAmount(double lendAmount) {
		this.lendAmount = lendAmount;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getLendDate() {
		return lendDate;
	}
	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}
	public String getLendDateStart() {
		return lendDateStart;
	}
	public void setLendDateStart(String lendDateStart) {
		this.lendDateStart = lendDateStart;
	}
	public String getLendDateEnd() {
		return lendDateEnd;
	}
	public void setLendDateEnd(String lendDateEnd) {
		this.lendDateEnd = lendDateEnd;
	}
}
