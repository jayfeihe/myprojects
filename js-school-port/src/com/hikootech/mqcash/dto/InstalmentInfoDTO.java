package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hikootech.mqcash.po.UserRepaymentPlans;

/**
 * 订单信息展现对象
 * */
public class InstalmentInfoDTO  implements Serializable,Cloneable {
	public Object clone() {  
		InstalmentInfoDTO o = null;  
        try {  
            o = (InstalmentInfoDTO) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }  
	private static final long serialVersionUID = 1L;
	//*****************top****************
	private String instalmentId; //账单id
	private String partnerOrderId; //合作方订单id
	private String partnerName; //合作方名称
	private String productName; //商品名称
	private String loanDate; //借款日期
	private Date loanDateForCompare; //借款日期(用于同一综合状态的分期单排序用)
	private Integer instalmentIdStatus; //账单实际状态（1下单完成 2还款完成）
	private Integer planState; //当前账单与计划的综合状态(  )
	private Integer lastDay;//剩余天数
	private Integer periodMax;//期数
	private BigDecimal loanAmount;//借款总额  订单金额+服务费
	private BigDecimal loanPrincipalAmount;//   订单金额 
	private BigDecimal overdueRate;//订单罚息费率
	private BigDecimal allOverDueAmount;//逾期总金额
	private int sort;//显示顺序  ，根据该字段针对不同类型的账单进行上下排序
	//*****************center****************
	private List<UserRepaymentPlans> userPlanList;
	private String repaymentAmount;//当期应还金额
	private Integer instalmentNum;//当前分期号
	private String repaymentPrincipal;//应还本金
	private String repaymentService;//服务费
	private String repaymentOverdue;//逾期罚息
	private String overDueDay;//逾期天数
	private String repayDay;//当期还款日
	private String repaymentPlanId;//当期还款编码
	private String curPlanStatus;
	private String curOverDueStatus;
	private BigDecimal repaymentReduce;//逾期减免
	
	//*****************bottom****************
	private UserRepaymentPlans userPlans;
	private String lastAmount;
	private String latPeriodNum;

	public InstalmentInfoDTO(){
		
	}
	
	public String getInstalmentId() {
		return instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}


	public Integer getPlanState() {
		return planState;
	}

	public void setPlanState(Integer planState) {
		this.planState = planState;
	}

	public Integer getLastDay() {
		return lastDay;
	}

	public void setLastDay(Integer lastDay) {
		this.lastDay = lastDay;
	}


	public Integer getPeriodMax() {
		return periodMax;
	}


	public void setPeriodMax(Integer periodMax) {
		this.periodMax = periodMax;
	}


	public BigDecimal getLoanAmount() {
		return loanAmount;
	}


	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getRepaymentAmount() {
		return repaymentAmount;
	}


	public void setRepaymentAmount(String repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}


	public Integer getInstalmentNum() {
		return instalmentNum;
	}


	public void setInstalmentNum(Integer instalmentNum) {
		this.instalmentNum = instalmentNum;
	}

	public String getRepaymentPrincipal() {
		return repaymentPrincipal;
	}

	public void setRepaymentPrincipal(String repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}

	public String getRepaymentService() {
		return repaymentService;
	}

	public void setRepaymentService(String repaymentService) {
		this.repaymentService = repaymentService;
	}

	public String getRepaymentOverdue() {
		return repaymentOverdue;
	}

	public void setRepaymentOverdue(String repaymentOverdue) {
		this.repaymentOverdue = repaymentOverdue;
	}

	public String getOverDueDay() {
		return overDueDay;
	}

	public void setOverDueDay(String overDueDay) {
		this.overDueDay = overDueDay;
	}

	public String getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(String repayDay) {
		this.repayDay = repayDay;
	}

	public UserRepaymentPlans getUserPlans() {
		return userPlans;
	}

	public void setUserPlans(UserRepaymentPlans userPlans) {
		this.userPlans = userPlans;
	}

	public String getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(String lastAmount) {
		this.lastAmount = lastAmount;
	}

	public String getLatPeriodNum() {
		return latPeriodNum;
	}

	public void setLatPeriodNum(String latPeriodNum) {
		this.latPeriodNum = latPeriodNum;
	}

	public String getRepaymentPlanId() {
		return repaymentPlanId;
	}

	public void setRepaymentPlanId(String repaymentPlanId) {
		this.repaymentPlanId = repaymentPlanId;
	}

	public List<UserRepaymentPlans> getUserPlanList() {
		return userPlanList;
	}

	public void setUserPlanList(List<UserRepaymentPlans> userPlanList) {
		this.userPlanList = userPlanList;
	}

	public BigDecimal getLoanPrincipalAmount() {
		return loanPrincipalAmount;
	}

	public void setLoanPrincipalAmount(BigDecimal loanPrincipalAmount) {
		this.loanPrincipalAmount = loanPrincipalAmount;
	}

	public Integer getInstalmentIdStatus() {
		return instalmentIdStatus;
	}

	public void setInstalmentIdStatus(Integer instalmentIdStatus) {
		this.instalmentIdStatus = instalmentIdStatus;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getCurPlanStatus() {
		return curPlanStatus;
	}

	public void setCurPlanStatus(String curPlanStatus) {
		this.curPlanStatus = curPlanStatus;
	}

	public String getCurOverDueStatus() {
		return curOverDueStatus;
	}

	public void setCurOverDueStatus(String curOverDueStatus) {
		this.curOverDueStatus = curOverDueStatus;
	}

	public BigDecimal getAllOverDueAmount() {
		return allOverDueAmount;
	}

	public void setAllOverDueAmount(BigDecimal allOverDueAmount) {
		this.allOverDueAmount = allOverDueAmount;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getLoanDateForCompare() {
		return loanDateForCompare;
	}

	public void setLoanDateForCompare(Date loanDateForCompare) {
		this.loanDateForCompare = loanDateForCompare;
	}

	public BigDecimal getRepaymentReduce() {
		return repaymentReduce;
	}

	public void setRepaymentReduce(BigDecimal repaymentReduce) {
		this.repaymentReduce = repaymentReduce;
	}
	
}
