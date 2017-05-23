package com.tera.car.model.form;

import java.util.List;

import com.tera.car.model.CarDecision;
import com.tera.car.model.CarExt;
import com.tera.car.model.CarHousing;
import com.tera.car.model.CarInterview;
import com.tera.car.model.CreditReport;
import com.tera.car.model.CarSummary;
import com.tera.car.model.WageFlow;
import com.tera.renhang.model.RhPublicDetail;
import com.tera.renhang.model.RhQuery;
import com.tera.renhang.model.RhQueryDetail;
import com.tera.renhang.model.RhReport;
import com.tera.renhang.model.RhSummary;
import com.tera.renhang.model.RhTransDetail;

public class VerifyFBean {
	
	private int id;				//申请的 ID
	private String appId;		//申请的 APPID
	
	private List<CarExt> type2Exts;		// 114查号台 
	private List<CarExt> type3Exts;		// 人法网  扩展信息
	private List<CarExt> type4Exts;		// 工商网   扩展信息
	private List<CarExt> type5Exts;		// 网查  扩展信息
	private CarSummary summary;			// 影像摘要
	private List<WageFlow> wageFlowList;    // 工资流水
	private CreditReport creditReport;		// 信用卡 人行信息
	private CreditReport loanReport;		// 贷款    人行信息
	private List<CarHousing> housingList;// 房产信息

	private CarDecision decision;		// 审核决策
	
	
	private List<CarInterview> type01InterviewList;// 面审调查 本人手机
	private List<CarInterview> type02InterviewList;// 面审调查 家庭固话
	private List<CarInterview> type03InterviewList;// 面审调查 单位电话
	private List<CarInterview> type04InterviewList;// 面审调查 常用联系人
	private List<CarInterview> type05InterviewList;// 面审调查 配偶手机
	
	/**
	 * 个人信用报告
	 */
	private RhReport rhReport;								//一、个人基本信息
	private RhSummary rhSummary;							//二、信息概要
	//三、信贷交易信息明细
	private List<RhTransDetail> type01RhTransDetailList;	//贷款  type为01
	private List<RhTransDetail> type02RhTransDetailList;	//四、贷记卡 type为02 
	private List<RhTransDetail> type03RhTransDetailList;	//五、准贷记卡 type为03
	private List<RhTransDetail> type04RhTransDetailList;	//六、担保信息 type为04
	
	private RhPublicDetail rhPublicDetail;					//七、公共信息明细
	
	private RhQuery rhQuery;								//八、查询记录
	private List<RhQueryDetail> rhQueryDetailList;			//信贷审批查询记录明细
	
	
	private String buttonType;			// save 保存，submit 提交，decision 拒贷,antifraud 反欺诈
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public List<CarExt> getType2Exts() {
		return type2Exts;
	}
	public void setType2Exts(List<CarExt> type2Exts) {
		this.type2Exts = type2Exts;
	}
	public List<CarExt> getType3Exts() {
		return type3Exts;
	}
	public void setType3Exts(List<CarExt> type3Exts) {
		this.type3Exts = type3Exts;
	}
	public List<CarExt> getType4Exts() {
		return type4Exts;
	}
	public void setType4Exts(List<CarExt> type4Exts) {
		this.type4Exts = type4Exts;
	}
	public List<CarExt> getType5Exts() {
		return type5Exts;
	}
	public void setType5Exts(List<CarExt> type5Exts) {
		this.type5Exts = type5Exts;
	}
	public CarSummary getSummary() {
		return summary;
	}
	public void setSummary(CarSummary summary) {
		this.summary = summary;
	}
	public CreditReport getCreditReport() {
		return creditReport;
	}
	public void setCreditReport(CreditReport creditReport) {
		this.creditReport = creditReport;
	}
	public CreditReport getLoanReport() {
		return loanReport;
	}
	public void setLoanReport(CreditReport loanReport) {
		this.loanReport = loanReport;
	}
	public List<CarHousing> getHousingList() {
		return housingList;
	}
	public void setHousingList(List<CarHousing> housingList) {
		this.housingList = housingList;
	}
	
	public CarDecision getDecision() {
		return decision;
	}
	public void setDecision(CarDecision decision) {
		this.decision = decision;
	}
	
	public List<CarInterview> getType01InterviewList() {
		return type01InterviewList;
	}
	public void setType01InterviewList(List<CarInterview> type01InterviewList) {
		this.type01InterviewList = type01InterviewList;
	}
	public List<CarInterview> getType02InterviewList() {
		return type02InterviewList;
	}
	public void setType02InterviewList(List<CarInterview> type02InterviewList) {
		this.type02InterviewList = type02InterviewList;
	}
	public List<CarInterview> getType03InterviewList() {
		return type03InterviewList;
	}
	public void setType03InterviewList(List<CarInterview> type03InterviewList) {
		this.type03InterviewList = type03InterviewList;
	}
	public List<CarInterview> getType04InterviewList() {
		return type04InterviewList;
	}
	public void setType04InterviewList(List<CarInterview> type04InterviewList) {
		this.type04InterviewList = type04InterviewList;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	public List<CarInterview> getType05InterviewList() {
		return type05InterviewList;
	}
	public void setType05InterviewList(List<CarInterview> type05InterviewList) {
		this.type05InterviewList = type05InterviewList;
	}
	public List<WageFlow> getWageFlowList() {
		return wageFlowList;
	}
	public void setWageFlowList(List<WageFlow> wageFlowList) {
		this.wageFlowList = wageFlowList;
	}
	public RhReport getRhReport() {
		return rhReport;
	}
	public void setRhReport(RhReport rhReport) {
		this.rhReport = rhReport;
	}
	public RhSummary getRhSummary() {
		return rhSummary;
	}
	public void setRhSummary(RhSummary rhSummary) {
		this.rhSummary = rhSummary;
	}
	
	public List<RhTransDetail> getType01RhTransDetailList() {
		return type01RhTransDetailList;
	}
	public void setType01RhTransDetailList(
			List<RhTransDetail> type01RhTransDetailList) {
		this.type01RhTransDetailList = type01RhTransDetailList;
	}
	public List<RhTransDetail> getType02RhTransDetailList() {
		return type02RhTransDetailList;
	}
	public void setType02RhTransDetailList(
			List<RhTransDetail> type02RhTransDetailList) {
		this.type02RhTransDetailList = type02RhTransDetailList;
	}
	public List<RhTransDetail> getType03RhTransDetailList() {
		return type03RhTransDetailList;
	}
	public void setType03RhTransDetailList(
			List<RhTransDetail> type03RhTransDetailList) {
		this.type03RhTransDetailList = type03RhTransDetailList;
	}
	public List<RhTransDetail> getType04RhTransDetailList() {
		return type04RhTransDetailList;
	}
	public void setType04RhTransDetailList(
			List<RhTransDetail> type04RhTransDetailList) {
		this.type04RhTransDetailList = type04RhTransDetailList;
	}
	public RhPublicDetail getRhPublicDetail() {
		return rhPublicDetail;
	}
	public void setRhPublicDetail(RhPublicDetail rhPublicDetail) {
		this.rhPublicDetail = rhPublicDetail;
	}
	public RhQuery getRhQuery() {
		return rhQuery;
	}
	public void setRhQuery(RhQuery rhQuery) {
		this.rhQuery = rhQuery;
	}
	public List<RhQueryDetail> getRhQueryDetailList() {
		return rhQueryDetailList;
	}
	public void setRhQueryDetailList(List<RhQueryDetail> rhQueryDetailList) {
		this.rhQueryDetailList = rhQueryDetailList;
	}
	
}
