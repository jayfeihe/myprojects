package com.tera.report.model;

/**
 * 分公司存量统计实体
 * @author QYANZE
 *
 */
public class BranchStockQBean {

	private String orgId; // 机构id
	private String orgName; // 机构名称
	private String product; // 产品
	private String productName; // 产品名称
	private int noExpireNum; // 未到期未收回笔数 
	private double noExpireAmt; // 未到期未收回金额
	private int expireNum; // 到期未收回笔数 
	private double expireAmt; // 到期未收回金额
	private int totalNum;  // 总笔数
	private double totalAmt; // 总金额
	private int tranNum; // 转贷笔数
	private double tranAmt; // 转贷金额
	private double tranRate; // 转贷比
	private double low3Amt; // 转贷次数小于等于3次的金额
	private double high3Amt; // 转贷次数大于3次的金额
	
	private int noExpireTotalNum; // 未到期未收回笔数合计 
	private double noExpireTotalAmt; // 未到期未收回金额合计
	private int expireTotalNum; // 到期未收回笔数合计 
	private double expireTotalAmt; // 到期未收回金额合计
	private int totalTotalNum; // 总笔数合计
	private double totalTotalAmt; // 总金额合计
	private int tranTotalNum; // 转贷笔数合计
	private double tranTotalAmt; // 转贷金额合计
	
	private String createDate; // 时间

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getNoExpireNum() {
		return noExpireNum;
	}

	public void setNoExpireNum(int noExpireNum) {
		this.noExpireNum = noExpireNum;
	}

	public double getNoExpireAmt() {
		return noExpireAmt;
	}

	public void setNoExpireAmt(double noExpireAmt) {
		this.noExpireAmt = noExpireAmt;
	}

	public int getExpireNum() {
		return expireNum;
	}

	public void setExpireNum(int expireNum) {
		this.expireNum = expireNum;
	}

	public double getExpireAmt() {
		return expireAmt;
	}

	public void setExpireAmt(double expireAmt) {
		this.expireAmt = expireAmt;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getTranNum() {
		return tranNum;
	}

	public void setTranNum(int tranNum) {
		this.tranNum = tranNum;
	}

	public double getTranAmt() {
		return tranAmt;
	}

	public void setTranAmt(double tranAmt) {
		this.tranAmt = tranAmt;
	}

	public double getTranRate() {
		return tranRate;
	}

	public void setTranRate(double tranRate) {
		this.tranRate = tranRate;
	}

	public double getLow3Amt() {
		return low3Amt;
	}

	public void setLow3Amt(double low3Amt) {
		this.low3Amt = low3Amt;
	}

	public double getHigh3Amt() {
		return high3Amt;
	}

	public void setHigh3Amt(double high3Amt) {
		this.high3Amt = high3Amt;
	}

	public int getNoExpireTotalNum() {
		return noExpireTotalNum;
	}

	public void setNoExpireTotalNum(int noExpireTotalNum) {
		this.noExpireTotalNum = noExpireTotalNum;
	}

	public double getNoExpireTotalAmt() {
		return noExpireTotalAmt;
	}

	public void setNoExpireTotalAmt(double noExpireTotalAmt) {
		this.noExpireTotalAmt = noExpireTotalAmt;
	}

	public int getExpireTotalNum() {
		return expireTotalNum;
	}

	public void setExpireTotalNum(int expireTotalNum) {
		this.expireTotalNum = expireTotalNum;
	}

	public double getExpireTotalAmt() {
		return expireTotalAmt;
	}

	public void setExpireTotalAmt(double expireTotalAmt) {
		this.expireTotalAmt = expireTotalAmt;
	}

	public int getTotalTotalNum() {
		return totalTotalNum;
	}

	public void setTotalTotalNum(int totalTotalNum) {
		this.totalTotalNum = totalTotalNum;
	}

	public double getTotalTotalAmt() {
		return totalTotalAmt;
	}

	public void setTotalTotalAmt(double totalTotalAmt) {
		this.totalTotalAmt = totalTotalAmt;
	}

	public int getTranTotalNum() {
		return tranTotalNum;
	}

	public void setTranTotalNum(int tranTotalNum) {
		this.tranTotalNum = tranTotalNum;
	}

	public double getTranTotalAmt() {
		return tranTotalAmt;
	}

	public void setTranTotalAmt(double tranTotalAmt) {
		this.tranTotalAmt = tranTotalAmt;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
