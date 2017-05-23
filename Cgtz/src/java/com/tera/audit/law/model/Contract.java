package com.tera.audit.law.model;

import com.tera.util.DateUtils;

/**
 * 
 * 合同信息表实体类
 * <b>功能：</b>ContractDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-21 14:31:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Contract {

	//属性部分
	private int id; //ID
	private String loanId; //申请ID
	private String contractId; //合同ID
	private double loanAmt; //借款金额
	private java.util.Date startDate; //借款开始时间
	private String startDateStr; //借款开始时间
	private java.util.Date endDate; //借款结束时间
	private String endDateStr; //借款结束时间
	private int days; //合同天数
	private java.util.Date realEndDate; //合同实际结束时间
	private String realEndDateStr; //合同实际结束时间
	private int conIndex; //合同序号(默认是0，被拆分后累计标识)
	private int num; //合同总期数
	private String lendUserId; //借款人Id
	private String org; //所属机构
	private String getLoanWay; //融资方式（1直投2债权转让3线下）
	private String remark; //说明
	private double onlineRateIn; //推送线上利率
	private String isAllSet; //担保物权是否全部设定1是0否
	private String inputUid; //合同录入人
	private String lawName; //法律意见书律师
	private String isLate; //是否逾期1是0否
	private String isLateDis; //是否催收分配1是0否
	private String state; //1未生效2合同中3合同结清4被拆分
	private String checkState; //稽查标识(1未处理，2已处理，3移交法务)
	private String isAccept; //当期逾期是否受理
	private java.sql.Timestamp checkInTime; //进入稽查时间（每周初始化一次数据）
	private String checkInTimeStr; //进入稽查时间（每周初始化一次数据）
	private String isCheck; //稽查状态是否进入稽查（1是0否）
	private String checkSource; // 稽查案件来源（1新逾期2业务员新跟进3还款变化）
	private String checkReportState; //逾期书面报告状态（0未提交1未审核2通过3不通过）
	private int lateNum; //本次逾期的期数，第几期
	private String overdueType; //当期逾期类型（1利息逾期2本金逾期）
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getContractId () {
		return this.contractId;
	}
	public double getLoanAmt () {
		return this.loanAmt;
	}
	public java.util.Date getStartDate () {
		return this.startDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getStartDateStr () {
		return DateUtils.formatDate(this.startDate);
	}
	public java.util.Date getEndDate () {
		return this.endDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEndDateStr () {
		return DateUtils.formatDate(this.endDate);
	}
	public int getDays () {
		return this.days;
	}
	public java.util.Date getRealEndDate () {
		return this.realEndDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRealEndDateStr () {
		return DateUtils.formatDate(this.realEndDate);
	}
	public int getConIndex () {
		return this.conIndex;
	}
	public int getNum () {
		return this.num;
	}
	public String getLendUserId () {
		return this.lendUserId;
	}
	public String getOrg () {
		return this.org;
	}
	public String getGetLoanWay () {
		return this.getLoanWay;
	}
	public String getRemark () {
		return this.remark;
	}
	public double getOnlineRateIn () {
		return this.onlineRateIn;
	}
	public String getIsAllSet () {
		return this.isAllSet;
	}
	public String getInputUid () {
		return this.inputUid;
	}
	public String getLawName () {
		return this.lawName;
	}
	public String getIsLate () {
		return this.isLate;
	}
	public String getIsLateDis () {
		return this.isLateDis;
	}
	public String getState () {
		return this.state;
	}
	public String getCheckState () {
		return this.checkState;
	}
	public String getIsAccept () {
		return this.isAccept;
	}
	public java.sql.Timestamp getCheckInTime () {
		return this.checkInTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCheckInTimeStr () {
		return DateUtils.formatTime(this.checkInTime);
	}
	public String getIsCheck () {
		return this.isCheck;
	}
	public String getCheckSource () {
		return this.checkSource;
	}
	public String getCheckReportState () {
		return this.checkReportState;
	}
	public int getLateNum () {
		return this.lateNum;
	}
	public String getOverdueType () {
		return this.overdueType;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setContractId (String contractId) {
		this.contractId=contractId;
	}
	public void setLoanAmt (double loanAmt) {
		this.loanAmt=loanAmt;
	}
	public void setStartDate (java.util.Date startDate) {
		this.startDate=startDate;
	}
	public void setStartDateStr (String startDateStr) {
		this.startDateStr=startDateStr;
	}
	public void setEndDate (java.util.Date endDate) {
		this.endDate=endDate;
	}
	public void setEndDateStr (String endDateStr) {
		this.endDateStr=endDateStr;
	}
	public void setDays (int days) {
		this.days=days;
	}
	public void setRealEndDate (java.util.Date realEndDate) {
		this.realEndDate=realEndDate;
	}
	public void setRealEndDateStr (String realEndDateStr) {
		this.realEndDateStr=realEndDateStr;
	}
	public void setConIndex (int conIndex) {
		this.conIndex=conIndex;
	}
	public void setNum (int num) {
		this.num=num;
	}
	public void setLendUserId (String lendUserId) {
		this.lendUserId=lendUserId;
	}
	public void setOrg (String org) {
		this.org=org;
	}
	public void setGetLoanWay (String getLoanWay) {
		this.getLoanWay=getLoanWay;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setOnlineRateIn (double onlineRateIn) {
		this.onlineRateIn=onlineRateIn;
	}
	public void setIsAllSet (String isAllSet) {
		this.isAllSet=isAllSet;
	}
	public void setInputUid (String inputUid) {
		this.inputUid=inputUid;
	}
	public void setLawName (String lawName) {
		this.lawName=lawName;
	}
	public void setIsLate (String isLate) {
		this.isLate=isLate;
	}
	public void setIsLateDis (String isLateDis) {
		this.isLateDis=isLateDis;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setCheckState (String checkState) {
		this.checkState=checkState;
	}
	public void setIsAccept (String isAccept) {
		this.isAccept=isAccept;
	}
	public void setCheckInTime (java.sql.Timestamp checkInTime) {
		this.checkInTime=checkInTime;
	}
	public void setCheckInTimeStr (String checkInTimeStr) {
		this.checkInTimeStr=checkInTimeStr;
	}
	public void setIsCheck (String isCheck) {
		this.isCheck=isCheck;
	}
	public void setCheckSource (String checkSource) {
		this.checkSource=checkSource;
	}
	public void setCheckReportState (String checkReportState) {
		this.checkReportState=checkReportState;
	}
	public void setLateNum (int lateNum) {
		this.lateNum=lateNum;
	}
	public void setOverdueType (String overdueType) {
		this.overdueType=overdueType;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

		/**融资方式-线下*/
	public static final String LOAN_WAY_OFF_LINE = "3";
	/**融资方式-债权转让*/
	public static final String LOAN_WAY_ZQ = "2";
	/**融资方式-直投*/
	public static final String LOAN_WAY_ZT = "1";
	
}

