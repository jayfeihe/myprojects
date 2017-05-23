package com.tera.lend.model.form;

public class TrustProductQBean {

	private int id; //ID
	private String productId; //产品
	private int period; //期限
	private String company; //机构名称
	private double interestRate; //利率
	private double startAmount; //起点金额
	private String useage; //用途
	private String trustProname; //产品名称
	private String prostartMoneyMin;//起点金额最小值
	private String prostartMoneyMax;//起点金额最大之
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
	}
	public String getUseage() {
		return useage;
	}
	public void setUseage(String useage) {
		this.useage = useage;
	}
	
	
	
	public String getTrustProname() {
		return trustProname;
	}
	public void setTrustProname(String trustProname) {
		this.trustProname = trustProname;
	}
	public String getProstartMoneyMin() {
		return prostartMoneyMin;
	}
	public void setProstartMoneyMin(String prostartMoneyMin) {
		this.prostartMoneyMin = prostartMoneyMin;
	}
	public String getProstartMoneyMax() {
		return prostartMoneyMax;
	}
	public void setProstartMoneyMax(String prostartMoneyMax) {
		this.prostartMoneyMax = prostartMoneyMax;
	}
	
	
}
