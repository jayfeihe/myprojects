package com.tera.interfaces.model;


/**
 * 债权基本信息
 * @author Jesse
 *
 */
public class PushDebt {
	private String debt_type;  //０债权,１直投，只接受０,1
	private String product_type;//１车２房３海鲜４红木99车商100其他
	private String serial_number;//平台在本系统内唯一编号
	private String appId;//10000.00格式
	private String amount;//债权形成时，合同利率
	private String contract_rate;//合同利率
	private String fee_rate;//手续费,3
	private String repayment;//1.按月付息２.按季付息３.一次还本４.等额本息５.等额本金
	private String start_time;//债权开始时间:2016-01-12
	private String end_time;//债权结束时间:2016-07-11
	
	private String channel = "002";
	
	public String getDebt_type() {
		return debt_type;
	}
	public void setDebt_type(String debtType) {
		debt_type = debtType;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String productType) {
		product_type = productType;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getContract_rate() {
		return contract_rate;
	}
	public void setContract_rate(String contractRate) {
		contract_rate = contractRate;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String endTime) {
		end_time = endTime;
	}
	public String getFee_rate() {
		return fee_rate;
	}
	public void setFee_rate(String feeRate) {
		fee_rate = feeRate;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
