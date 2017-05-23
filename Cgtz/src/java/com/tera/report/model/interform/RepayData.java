package com.tera.report.model.interform;

/**
 * 线上返回还款具体数据
 * @author QYANZE
 *
 */
public class RepayData {

	private String project_id; // 项目编号
	private String serial_number; // 线下债权编号
	private String title; // 项目标题
	private String start_time; // 合同开始时间
	private String end_time; // 合同结束时间
	private String online_time; // 项目开始时间
	private double sale_amout_money; // 已销售金额
	private String original_creditor_name; // 债权人
	private String area; // 区域
	private String borrower_people_name; // 借款人
	private String project_days; // 项目天数
	private String current_period; // 本期天数
	private String check_annualized_rate; // 利率
	private String debt_interest_no; // 付息期数
	private String debt_type; // 债权类型 -- 0债权转让，1直投
	private String rate_end_time; // 应付息时间
	private double sum_payable_interest; // 应付息金额
	private double payable_principal; // 应付本金额
	
	private String rateTimeMin; // 付息开始时间
	private String rateTimeMax; // 付息结束时间
	
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getOnline_time() {
		return online_time;
	}
	public void setOnline_time(String online_time) {
		this.online_time = online_time;
	}
	public double getSale_amout_money() {
		return sale_amout_money;
	}
	public void setSale_amout_money(double sale_amout_money) {
		this.sale_amout_money = sale_amout_money;
	}
	public String getOriginal_creditor_name() {
		return original_creditor_name;
	}
	public void setOriginal_creditor_name(String original_creditor_name) {
		this.original_creditor_name = original_creditor_name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBorrower_people_name() {
		return borrower_people_name;
	}
	public void setBorrower_people_name(String borrower_people_name) {
		this.borrower_people_name = borrower_people_name;
	}
	public String getProject_days() {
		return project_days;
	}
	public void setProject_days(String project_days) {
		this.project_days = project_days;
	}
	public String getCurrent_period() {
		return current_period;
	}
	public void setCurrent_period(String current_period) {
		this.current_period = current_period;
	}
	public String getCheck_annualized_rate() {
		return check_annualized_rate;
	}
	public void setCheck_annualized_rate(String check_annualized_rate) {
		this.check_annualized_rate = check_annualized_rate;
	}
	public String getDebt_interest_no() {
		return debt_interest_no;
	}
	public void setDebt_interest_no(String debt_interest_no) {
		this.debt_interest_no = debt_interest_no;
	}
	public String getDebt_type() {
		return debt_type;
	}
	public void setDebt_type(String debt_type) {
		this.debt_type = debt_type;
	}
	public String getRate_end_time() {
		return rate_end_time;
	}
	public void setRate_end_time(String rate_end_time) {
		this.rate_end_time = rate_end_time;
	}
	public double getSum_payable_interest() {
		return sum_payable_interest;
	}
	public void setSum_payable_interest(double sum_payable_interest) {
		this.sum_payable_interest = sum_payable_interest;
	}
	public double getPayable_principal() {
		return payable_principal;
	}
	public void setPayable_principal(double payable_principal) {
		this.payable_principal = payable_principal;
	}
	public String getRateTimeMin() {
		return rateTimeMin;
	}
	public void setRateTimeMin(String rateTimeMin) {
		this.rateTimeMin = rateTimeMin;
	}
	public String getRateTimeMax() {
		return rateTimeMax;
	}
	public void setRateTimeMax(String rateTimeMax) {
		this.rateTimeMax = rateTimeMax;
	}
}
