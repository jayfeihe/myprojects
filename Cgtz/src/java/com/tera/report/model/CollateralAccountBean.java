package com.tera.report.model;

import java.util.Date;

import com.tera.util.DateUtils;

//抵押物台账
public class CollateralAccountBean {
	private String product;//项目类型

	private String contractId;//线下合同编号
	private String orgId;//分公司id

	private String orgName;//分公司名称
	private String salesMan;//业务员
	private String saleName;//业务员名称
	
	//业务状态
	private String isLate;//是否逾期
	private String isRenew;//是否续贷
	
	private String lawName;//法律意见书签字律师
	private String loanId;//申请id

	private String loanName;//借款人姓名
	private String tel;//借款人联系方式
	private String lendUserId;//出借人id

	private String lendName;//出借人姓名

    private double loanAmt;//借款金额

    private Date startDate;//出借时间
    private String startDateStr;//
    //时间区间
    private Date minStartDate;
    private Date maxStartDate;
  
    private Date endDate;//还款时间
    private String endDateStr;//
    
    private Date minEndDate;
    private Date maxEndDate;

    private double rate;//年化利率
    //抵押还是质押
    private String isSet;//担保物权是否设立
    private String frameCode;//车架号
  
    private String license;//车牌号
    private String housePropertyCode;//房产证号
    
    private String assetAddr;//仓库/房产地址
 
    private double evalPrice;//估价
    private String reserveDes;//保管物品
    private String remark;//说明
    private String state;//合同状态
    private String colState;//押品状态(1库存中2正常出库3资产处置)
    
    //getter部分 
    public String getProduct() {
		return product;
	}
	public String getContractId() {
		return contractId;
	}
	public String getOrgId() {
		return orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getSalesMan() {
		return salesMan;
	}
	public String getSaleName() {
		return saleName;
	}
	public String getIsLate() {
		return isLate;
	}
	public String getIsRenew() {
		return isRenew;
	}
	public String getLawName() {
		return lawName;
	}
	public String getLoanId() {
		return loanId;
	}
	public String getLoanName() {
		return loanName;
	}
	public String getTel() {
		return tel;
	}
	public String getLendUserId() {
		return lendUserId;
	}
	public String getLendName() {
		return lendName;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public Date getStartDate() {
		return startDate;
	}
	public String getStartDateStr() {
		return DateUtils.formatDate(startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getEndDateStr() {
		return DateUtils.formatDate(endDate);
	}
	public double getRate() {
		return rate;
	}
	public String getIsSet() {
		return isSet;
	}
	public String getFrameCode() {
		return frameCode;
	}
	public String getLicense() {
		return license;
	}
	public String getHousePropertyCode() {
		return housePropertyCode;
	}
	public String getAssetAddr() {
		return assetAddr;
	}
	public double getEvalPrice() {
		return evalPrice;
	}
	public String getReserveDes() {
		return reserveDes;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	public Date getMinStartDate() {
		return minStartDate;
	}
	public Date getMaxStartDate() {
		return maxStartDate;
	}
	public Date getMinEndDate() {
		return minEndDate;
	}
	public Date getMaxEndDate() {
		return maxEndDate;
	}
	
	public String getState() {
		return state;
	}
	public String getColState(){
		return colState;
	}
	//setter
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public void setIsLate(String isLate) {
		this.isLate = isLate;
	}
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	public void setLawName(String lawName) {
		this.lawName = lawName;
	}
	
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setLendUserId(String lendUid) {
		this.lendUserId = lendUid;
	}
	public void setLendName(String lendName) {
		this.lendName = lendName;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public void setIsSet(String isSet) {
		this.isSet = isSet;
	}
	public void setFrameCode(String frameCode) {
		this.frameCode = frameCode;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setHousePropertyCode(String housePropertyCode) {
		this.housePropertyCode = housePropertyCode;
	}
	public void setAssetAddr(String assetAddr) {
		this.assetAddr = assetAddr;
	}
	public void setEvalPrice(double evalPrice) {
		this.evalPrice = evalPrice;
	}
	public void setReserveDes(String reserveDes) {
		this.reserveDes = reserveDes;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}
	public void setMaxStartDate(Date maxStartDate) {
		this.maxStartDate = maxStartDate;
	}
	public void setMinEndDate(Date minEndDate) {
		this.minEndDate = minEndDate;
	}
	public void setMaxEndDate(Date maxEndDate) {
		this.maxEndDate = maxEndDate;
	}
	public void setState(String state) {
		this.state = state;
	}
    public void setColState(String colState){
    	this.colState=colState;
    }
}
