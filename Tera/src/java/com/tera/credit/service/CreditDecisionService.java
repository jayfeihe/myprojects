package com.tera.credit.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.dao.BpmLogDao;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.constant.Constants;
import com.tera.credit.dao.CreditDecisionDao;
import com.tera.credit.model.CreditAntifraud;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditCallLog;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.CreditExt;
import com.tera.credit.model.CreditHousing;
import com.tera.credit.model.CreditInterview;
import com.tera.credit.model.CreditReport;
import com.tera.credit.model.CreditSummary;
import com.tera.credit.model.WageFlow;
import com.tera.credit.model.form.ApprovalFBean;
import com.tera.credit.model.form.SpecialApprovalFBean;
import com.tera.credit.model.form.VerifyFBean;
import com.tera.match.model.Loan2match;
import com.tera.match.service.Loan2matchService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.renhang.model.RhPublicDetail;
import com.tera.renhang.model.RhQuery;
import com.tera.renhang.model.RhQueryDetail;
import com.tera.renhang.model.RhReport;
import com.tera.renhang.model.RhSummary;
import com.tera.renhang.model.RhTransDefault;
import com.tera.renhang.model.RhTransDetail;
import com.tera.renhang.service.RhPublicDetailService;
import com.tera.renhang.service.RhQueryDetailService;
import com.tera.renhang.service.RhQueryService;
import com.tera.renhang.service.RhReportService;
import com.tera.renhang.service.RhSummaryService;
import com.tera.renhang.service.RhTransDefaultService;
import com.tera.renhang.service.RhTransDetailService;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;
import com.tera.util.MathUtils;

/**
 * 
 * 信用贷款决策表服务类
 * <b>功能：</b>CreditDecisionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:02:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditDecisionService")
public class CreditDecisionService {

	private final static Logger log = Logger.getLogger(CreditDecisionService.class);

	@Autowired(required=false)
    CreditDecisionDao dao;
	@Autowired(required=false)
    ContractService contractService;
	@Autowired(required=false) //自动注入
	CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	CreditContactService creditContactService;
	@Autowired(required=false) //自动注入
	CreditExtService creditExtService;
	@Autowired(required=false) //自动注入
	CreditReportService creditReportService;
	@Autowired(required=false) //自动注入
	CreditAntifraudService creditAntifraudService;
	@Autowired(required=false) //自动注入
	CreditSummaryService creditSummaryService;
	@Autowired(required=false) //自动注入
	CreditHousingService creditHousingService;
	@Autowired(required=false) //自动注入
	CreditInterviewService creditInterviewService;
	@Autowired(required=false) //自动注入
	CreditCallLogService creditCallLogService;
	@Autowired(required=false) //自动注入
	ProcessService processService;
	@Autowired(required=false) //自动注入
	Loan2matchService<Loan2match> loan2matchService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	UserService userService;
	@Autowired(required=false) //自动注入
	WageFlowService wageFlowService;
	@Autowired(required=false) //自动注入
	private RhReportService rhReportService;
	@Autowired(required=false) //自动注入
	private RhSummaryService rhSummaryService;
	@Autowired(required=false) //自动注入
	private RhPublicDetailService rhPublicDetailService;
	@Autowired(required=false) //自动注入
	private RhQueryService rhQueryService;
	@Autowired(required=false) //自动注入
	private RhQueryDetailService rhQueryDetailService;
	@Autowired(required=false) //自动注入
	private RhTransDetailService rhTransDetailService;
	@Autowired(required=false) //自动注入
	private RhTransDefaultService rhTransDefaultService;
	
	@Autowired(required=false) //自动注入
	LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	AccounttingService<Accountting> accounttingService;
	@Autowired(required=false) //自动注入
	PaymentService<Payment> paymentService;
	@Autowired(required=false) //自动注入
	private CreditAppHandlerService creditAppHandlerService;
	@Autowired(required=false) //自动注入
	private BpmLogDao bpmLogDao;
	
	@Transactional
	public void add(CreditDecision... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditDecision obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditDecision obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditDecision obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<CreditDecision> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditDecision queryByKey(Object id) throws Exception {
		return (CreditDecision)dao.queryByKey(id);
	}
	
	/**
	 * 审核流程跳转
	 * @param appId			申请ID
	 * @param nextState		下一个节点名称
	 * @param operator		当前流程 实际处理人（当前登录用户）
	 * @param processer		下个流程的 待处理人
	 */
	@Transactional
	public void decision(String appId,String nextState,String operator,String processer,
				String logContent1,String logContent2,String logContent3,String logContent4,String logContent5,
				String logContent6,String logContent7,String logContent8,String logContent9,String logContent10){
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
		BpmTask task=taskList.get(0);
		task.setOperator(operator);
		task.setVariable("logContent1",logContent1);
		task.setVariable("logContent2",logContent2);
		task.setVariable("logContent3",logContent3);
		task.setVariable("logContent4",logContent4);
		task.setVariable("logContent5",logContent5);
		task.setVariable("logContent6",logContent6);
		task.setVariable("logContent7",logContent7);
		task.setVariable("logContent8",logContent8);
		task.setVariable("logContent9",logContent9);
		task.setVariable("logContent10",logContent10);
		task = processService.goNext(task, nextState, processer);
		
	}
	/**
	 * 审核 数据保存
	 * @param verifyFBean
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Transactional
	public void verifyUpdate(VerifyFBean verifyFBean,String loginId,String orgId)  throws Exception {
		
		String appId=verifyFBean.getAppId();
		CreditApp app=creditAppService.queryByKey(verifyFBean.getId());
		Timestamp newTime=new Timestamp(System.currentTimeMillis());
		
		////保存开始////
		List<CreditExt> extList=new ArrayList<CreditExt>();
		if(verifyFBean.getType2Exts()!=null){
			extList.addAll(verifyFBean.getType2Exts());
		}
		if(verifyFBean.getType3Exts()!=null){
			extList.addAll(verifyFBean.getType3Exts());
		}
		if(verifyFBean.getType4Exts()!=null){
			extList.addAll(verifyFBean.getType4Exts());
		}
		if(verifyFBean.getType5Exts()!=null){
			extList.addAll(verifyFBean.getType5Exts());
		}
		//信息核查
		for (CreditExt creditExt : extList) {
			if(creditExt.getId()==0 && "1".equals(creditExt.getState())){
				creditExt.setOperator(loginId);
				creditExt.setOrgId(orgId);
				creditExt.setState("1");
				creditExt.setCreateTime(newTime);
				creditExt.setUpdateTime(newTime);
				creditExtService.add(creditExt);
			}else if(creditExt.getId()!=0){
				creditExt.setUpdateTime(newTime);
				creditExt.setOperator(loginId);
				creditExtService.updateOnlyChanged(creditExt);
			}
		}
		//影像摘要
		CreditSummary summary=verifyFBean.getSummary();
		if(null != summary){
			if(summary.getId()==0 && "1".equals(summary.getState())){
				summary.setState("1");
				summary.setOperator(loginId);
				summary.setOrgId(orgId);
				summary.setCreateTime(newTime);
				summary.setUpdateTime(newTime);
				creditSummaryService.add(summary);
			}else if(summary.getId()!=0){
				summary.setUpdateTime(newTime);
				summary.setOperator(loginId);
				creditSummaryService.updateOnlyChanged(summary);
			}			
		}
		List<WageFlow> wageFlowList = verifyFBean.getWageFlowList();
		if(null != wageFlowList){
			for (WageFlow wageFlow : wageFlowList) {
				if(wageFlow.getId()==0 && "1".equals(wageFlow.getState())){
					wageFlow.setOperator(loginId);
					wageFlow.setOrgId(orgId);
					wageFlow.setCreateTime(newTime);
					wageFlow.setUpdateTime(newTime);
					wageFlowService.add(wageFlow);
				}else if(wageFlow.getId()!=0){
					wageFlow.setUpdateTime(newTime);
					wageFlow.setOperator(loginId);
					wageFlowService.updateOnlyChanged(wageFlow);
				}
			}
		}
		//信用卡
		CreditReport creditReport=verifyFBean.getCreditReport();
		if(null != creditReport){			
			if(creditReport.getId()==0&& "1".equals(creditReport.getState())){
				creditReport.setType("1");
				creditReport.setOperator(loginId);
				creditReport.setOrgId(orgId);
				creditReport.setCreateTime(newTime);
				creditReport.setUpdateTime(newTime);
				creditReportService.add(creditReport);
			}else if(creditReport.getId()!=0){
				creditReport.setUpdateTime(newTime);
				creditReport.setOperator(loginId);
				creditReportService.updateOnlyChanged(creditReport);
			}
		}
		//贷款
		CreditReport loanReport=verifyFBean.getLoanReport();
		if(null != loanReport){
			if(loanReport.getId()==0 && "1".equals(loanReport.getState())){
				loanReport.setType("2");
				loanReport.setOperator(loginId);
				loanReport.setOrgId(orgId);
				loanReport.setCreateTime(newTime);
				loanReport.setUpdateTime(newTime);
				creditReportService.add(loanReport);
			}else if(loanReport.getId()!=0){
				loanReport.setUpdateTime(newTime);
				loanReport.setOperator(loginId);
				creditReportService.updateOnlyChanged(loanReport);
			}
		}
		// 房产信息
		List<CreditHousing> housingList=verifyFBean.getHousingList();
		if(null != housingList){
			for (CreditHousing creditHousing : housingList) {
				if(creditHousing.getId()==0 && "1".equals(creditHousing.getState())){
					creditHousing.setOperator(loginId);
					creditHousing.setOrgId(orgId);
					creditHousing.setCreateTime(newTime);
					creditHousing.setUpdateTime(newTime);
					creditHousingService.add(creditHousing);
				}else if(creditHousing.getId()!=0){
					creditHousing.setUpdateTime(newTime);
					creditHousing.setOperator(loginId);
					creditHousingService.updateOnlyChanged(creditHousing);
				}
			}
		}
		// 信用报告
		RhReport rhReport = verifyFBean.getRhReport();										//一、个人基本信息
		if(null != rhReport){
			if(rhReport.getId()==0 && "1".equals(rhReport.getState())){
				rhReport.setOperator(loginId);
				rhReport.setOrgId(orgId);
				rhReport.setCreateTime(newTime);
				rhReport.setUpdateTime(newTime);
				rhReportService.add(rhReport);
			}else if(rhReport.getId()!=0){
				rhReport.setUpdateTime(newTime);
				rhReport.setOperator(loginId);
				rhReportService.updateOnlyChanged(rhReport);
			}	
		}
		//非白户时才去修改下面信用报告的内容
		if(null != rhReport && "1".equals(rhReport.getFlag())){
			RhSummary rhSummary = verifyFBean.getRhSummary();									//二、信息概要
			if(null != rhSummary){
				if(rhSummary.getId()==0 && "1".equals(rhSummary.getState())){
					rhSummary.setOperator(loginId);
					rhSummary.setOrgId(orgId);
					rhSummary.setCreateTime(newTime);
					rhSummary.setUpdateTime(newTime);
					rhSummaryService.add(rhSummary);
				}else if(rhSummary.getId()!=0){
					rhSummary.setUpdateTime(newTime);
					rhSummary.setOperator(loginId);
					rhSummaryService.updateOnlyChanged(rhSummary);
				}	
			}
			List<RhTransDetail> rhTransDetail01s = verifyFBean.getType01RhTransDetailList();	//贷款
			if(null != rhTransDetail01s){
				for (RhTransDetail rhTransDetail : rhTransDetail01s) {
					if(rhTransDetail.getId()==0 && "1".equals(rhTransDetail.getState())){
						rhTransDetail.setOperator(loginId);
						rhTransDetail.setOrgId(orgId);
						rhTransDetail.setCreateTime(newTime);
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetailService.add(rhTransDetail);
					}else if(rhTransDetail.getId()!=0){
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetail.setOperator(loginId);
						rhTransDetailService.updateOnlyChanged(rhTransDetail);
					}
					List<RhTransDefault> rhTransDefaultList = rhTransDetail.getRhTransDefaultList();
					if(null != rhTransDefaultList){
						for (RhTransDefault rhTransDefault : rhTransDefaultList) {
							rhTransDefault.setTransId(rhTransDetail.getId());
							if(rhTransDefault.getId()==0 && "1".equals(rhTransDefault.getState())){
								rhTransDefault.setOperator(loginId);
								rhTransDefault.setOrgId(orgId);
								rhTransDefault.setCreateTime(newTime);
								rhTransDefault.setUpdateTime(newTime);
								rhTransDefaultService.add(rhTransDefault);
							}else if(rhTransDefault.getId()!=0){
								rhTransDefault.setUpdateTime(newTime);
								rhTransDefault.setOperator(loginId);
								rhTransDefaultService.updateOnlyChanged(rhTransDefault);
							}
						}						
					}
				}
			}
			List<RhTransDetail> rhTransDetail02s = verifyFBean.getType02RhTransDetailList();	//贷记卡
			if(null != rhTransDetail02s){
				for (RhTransDetail rhTransDetail : rhTransDetail02s) {
					if(rhTransDetail.getId()==0 && "1".equals(rhTransDetail.getState())){
						rhTransDetail.setOperator(loginId);
						rhTransDetail.setOrgId(orgId);
						rhTransDetail.setCreateTime(newTime);
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetailService.add(rhTransDetail);
					}else if(rhTransDetail.getId()!=0){
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetail.setOperator(loginId);
						rhTransDetailService.updateOnlyChanged(rhTransDetail);
					}	
					List<RhTransDefault> rhTransDefaultList = rhTransDetail.getRhTransDefaultList();
					if(null != rhTransDefaultList){
						for (RhTransDefault rhTransDefault : rhTransDefaultList) {
							rhTransDefault.setTransId(rhTransDetail.getId());
							if(rhTransDefault.getId()==0 && "1".equals(rhTransDefault.getState())){
								rhTransDefault.setOperator(loginId);
								rhTransDefault.setOrgId(orgId);
								rhTransDefault.setCreateTime(newTime);
								rhTransDefault.setUpdateTime(newTime);
								rhTransDefaultService.add(rhTransDefault);
							}else if(rhTransDefault.getId()!=0){
								rhTransDefault.setUpdateTime(newTime);
								rhTransDefault.setOperator(loginId);
								rhTransDefaultService.updateOnlyChanged(rhTransDefault);
							}
						}						
					}
				}
			}
			List<RhTransDetail> rhTransDetail03s = verifyFBean.getType03RhTransDetailList();	//准贷记卡
			if(null != rhTransDetail03s){
				for (RhTransDetail rhTransDetail : rhTransDetail03s) {
					if(rhTransDetail.getId()==0 && "1".equals(rhTransDetail.getState())){
						rhTransDetail.setOperator(loginId);
						rhTransDetail.setOrgId(orgId);
						rhTransDetail.setCreateTime(newTime);
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetailService.add(rhTransDetail);
					}else if(rhTransDetail.getId()!=0){
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetail.setOperator(loginId);
						rhTransDetailService.updateOnlyChanged(rhTransDetail);
					}	
					List<RhTransDefault> rhTransDefaultList = rhTransDetail.getRhTransDefaultList();
					if(null != rhTransDefaultList){
						for (RhTransDefault rhTransDefault : rhTransDefaultList) {
							rhTransDefault.setTransId(rhTransDetail.getId());
							if(rhTransDefault.getId()==0 && "1".equals(rhTransDefault.getState())){
								rhTransDefault.setOperator(loginId);
								rhTransDefault.setOrgId(orgId);
								rhTransDefault.setCreateTime(newTime);
								rhTransDefault.setUpdateTime(newTime);
								rhTransDefaultService.add(rhTransDefault);
							}else if(rhTransDefault.getId()!=0){
								rhTransDefault.setUpdateTime(newTime);
								rhTransDefault.setOperator(loginId);
								rhTransDefaultService.updateOnlyChanged(rhTransDefault);
							}
						}						
					}
				}
			}
			List<RhTransDetail> rhTransDetail04s = verifyFBean.getType04RhTransDetailList();	//担保信息
			if(null != rhTransDetail04s){
				for (RhTransDetail rhTransDetail : rhTransDetail04s) {
					if(rhTransDetail.getId()==0 && "1".equals(rhTransDetail.getState())){
						rhTransDetail.setOperator(loginId);
						rhTransDetail.setOrgId(orgId);
						rhTransDetail.setCreateTime(newTime);
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetailService.add(rhTransDetail);
					}else if(rhTransDetail.getId()!=0){
						rhTransDetail.setUpdateTime(newTime);
						rhTransDetail.setOperator(loginId);
						rhTransDetailService.updateOnlyChanged(rhTransDetail);
					}	
				}
			}
			RhPublicDetail rhPublicDetail = verifyFBean.getRhPublicDetail();					//七、公共信息明细
			if(null != rhPublicDetail){
				if(rhPublicDetail.getId()==0 && "1".equals(rhPublicDetail.getState())){
					rhPublicDetail.setOperator(loginId);
					rhPublicDetail.setOrgId(orgId);
					rhPublicDetail.setCreateTime(newTime);
					rhPublicDetail.setUpdateTime(newTime);
					rhPublicDetailService.add(rhPublicDetail);
				}else if(rhPublicDetail.getId()!=0){
					rhPublicDetail.setUpdateTime(newTime);
					rhPublicDetail.setOperator(loginId);
					rhPublicDetailService.updateOnlyChanged(rhPublicDetail);
				}					
			}
		}
		RhQuery rhQuery = verifyFBean.getRhQuery();											//八、查询记录汇总
		if(null != rhQuery){
			if(rhQuery.getId()==0 && "1".equals(rhQuery.getState())){
				rhQuery.setOperator(loginId);
				rhQuery.setOrgId(orgId);
				rhQuery.setCreateTime(newTime);
				rhQuery.setUpdateTime(newTime);
				rhQueryService.add(rhQuery);
			}else if(rhQuery.getId()!=0){
				rhQuery.setUpdateTime(newTime);
				rhQuery.setOperator(loginId);
				rhQueryService.updateOnlyChanged(rhQuery);
			}				
		}
		List<RhQueryDetail> rhQueryDetails = verifyFBean.getRhQueryDetailList();			//八、查询记录明细
		if(null != rhQueryDetails){
			for (RhQueryDetail rhQueryDetail : rhQueryDetails) {
				rhQueryDetail.setQueryId(rhQuery.getId());
				if(rhQueryDetail.getId()==0 && "1".equals(rhQueryDetail.getState())){
					rhQueryDetail.setOperator(loginId);
					rhQueryDetail.setOrgId(orgId);
					rhQueryDetail.setCreateTime(newTime);
					rhQueryDetail.setUpdateTime(newTime);
					rhQueryDetailService.add(rhQueryDetail);
				}else if(rhQueryDetail.getId()!=0){
					rhQueryDetail.setUpdateTime(newTime);
					rhQueryDetail.setOperator(loginId);
					rhQueryDetailService.updateOnlyChanged(rhQueryDetail);
				}
			}
		}
		// 审核决策
		CreditDecision decision=verifyFBean.getDecision();
		if(null != decision){			
			if(decision.getId()==0 && "1".equals(decision.getState())){
				decision.setType("1");
				decision.setOperator(loginId);
				decision.setOrgId(orgId);
				decision.setCreateTime(newTime);
				decision.setUpdateTime(newTime);
				this.add(decision);
			}else if(decision.getId()!=0){
				decision.setUpdateTime(newTime);
				decision.setOperator(loginId);
				this.updateOnlyChanged(decision);
			}	
		}
		//面审信息
		List<CreditInterview> interviewList=new ArrayList<CreditInterview>();
		if(null != verifyFBean.getType01InterviewList())
			interviewList.addAll(verifyFBean.getType01InterviewList());
		if(null != verifyFBean.getType02InterviewList())
			interviewList.addAll(verifyFBean.getType02InterviewList());
		if(null != verifyFBean.getType03InterviewList())
			interviewList.addAll(verifyFBean.getType03InterviewList());
		if(null != verifyFBean.getType04InterviewList())
			interviewList.addAll(verifyFBean.getType04InterviewList());
		if(null != verifyFBean.getType05InterviewList())
			interviewList.addAll(verifyFBean.getType05InterviewList());
		for (CreditInterview creditInterview : interviewList) {
			if(creditInterview.getId()==0 && "1".equals(creditInterview.getState())){
				creditInterview.setOperator(loginId);
				creditInterview.setOrgId(orgId);
				creditInterview.setCreateTime(newTime);
				creditInterview.setUpdateTime(newTime);
				creditInterviewService.add(creditInterview);
			}else if(creditInterview.getId()!=0){
				creditInterview.setUpdateTime(newTime);
				creditInterview.setOperator(loginId);
				creditInterviewService.updateOnlyChanged(creditInterview);
			}
			List<CreditCallLog> callLogList = creditInterview.getCallLogList();
			for (CreditCallLog creditCallLog : callLogList) {
				if(0 == creditCallLog.getId() && "1".equals(creditCallLog.getState())){
					creditCallLog.setOperator(loginId);
					creditCallLog.setOrgId(orgId);
					creditCallLog.setCreateTime(newTime);
					creditCallLog.setUpdateTime(newTime);
					creditCallLog.setInterviewingId(creditInterview.getId());
					creditCallLog.setAppId(creditInterview.getAppId());
					creditCallLogService.add(creditCallLog);
				}else if(0 != creditCallLog.getId()){
					creditCallLog.setUpdateTime(newTime);
					creditCallLog.setOperator(loginId);
					creditCallLogService.updateOnlyChanged(creditCallLog);
				}
			}
		}
		
		
		////保存完成处理提交/////
		if("submit".equals(verifyFBean.getButtonType())){
		//提交
			String nextState=null,processer="";
			String logContent1=null,logContent2=null, logContent3=null, logContent4=null, logContent5=null,
					logContent6=null,logContent7=null, logContent8=null, logContent9=null, logContent10=null;
			if("00".equals(verifyFBean.getDecision().getDecision())){
				nextState=Constants.PROCESS_STATE_APP;
				logContent1="退回";
				logContent2=verifyFBean.getDecision().getReturnMsg();
				logContent3= "退回码";
				logContent4 = "退回码1：" + decision.getDecisionCode5() + "——退回码2：" + decision.getDecisionCode6();
				logContent5 = verifyFBean.getDecision().getRemarks();
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(app.getAppId(), "录入申请");
				processer = bpmLogList.get(0).getOperator();
				app.setState("2");//状态  录入申请 退回
				String state = userService.getUser(processer).getState();
				if("0".equals(state)){//不在职（挂起不变）
					List<User> users = userService.getUserByOrgAndRoleAndDepart(app.getOrgId(), new String[]{Constants.ROLE_FXZY},null);
					if (users.size() > 0) {
						User user = users.get(new Random().nextInt(users.size()));
						processer = user.getLoginId();
						app.setCustomerManager(processer);
					}
				}
				this.decision(appId, nextState, loginId, processer, logContent1, logContent2, logContent3, logContent4, logContent5, logContent6, logContent7, logContent8, logContent9, logContent10);
			}else if("02".equals(verifyFBean.getDecision().getDecision())){
				nextState=Constants.PROCESS_STATE_APPROVAL;
				logContent1="拟通过";
				logContent3="违例码";
				logContent4="违例码1：" + verifyFBean.getDecision().getDecisionCode1() + "——违例码2：" + verifyFBean.getDecision().getDecisionCode2();
				logContent5 = verifyFBean.getDecision().getRemarks();
				app.setState("6");//状态  审批
				//流程跳转，取log，判断是否退回过
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(appId, "审批");
				if(bpmLogList.size() != 0){//是退回件
					//判断	bpmLogList.get(0).getOperator()	是否可用，若不可用，提交至分单
					String nextUser = bpmLogList.get(0).getOperator();
			        String state = userService.getUser(nextUser).getState();
					if("0".equals(state)){//（挂起不变）
						nextUser = "";
					}
					this.decision(appId, nextState, loginId, nextUser, logContent1, logContent2, logContent3, logContent4, logContent5, logContent6, logContent7, logContent8, logContent9, logContent10);
				}else if(bpmLogList.size() == 0){//不是退回件
					this.decision(appId, nextState, loginId, processer, logContent1, logContent2, logContent3, logContent4, logContent5, logContent6, logContent7, logContent8, logContent9, logContent10);
				}
			}else if("03".equals(verifyFBean.getDecision().getDecision())){
				nextState=Constants.PROCESS_STATE_APPROVAL;
				logContent1="拟拒贷";
				logContent3="违例码";
				logContent4="违例码1：" + verifyFBean.getDecision().getDecisionCode1() + "——违例码2：" + verifyFBean.getDecision().getDecisionCode2();
				logContent5 = verifyFBean.getDecision().getRemarks();
				app.setState("6");//状态  审批
				//流程跳转
				this.decision(appId, nextState, loginId, processer, logContent1, logContent2, logContent3, logContent4, logContent5, logContent6, logContent7, logContent8, logContent9, logContent10);
			}
//			//流程跳转
//			this.decision(appId, nextState, loginId, processer, logContent1, logContent2, logContent3, null);
		}else if("decision".equals(verifyFBean.getButtonType())){
		//拒贷
			
			String nextState=Constants.PROCESS_STATE_REJECT;
			this.decision(appId, nextState, loginId, "", "拒贷",verifyFBean.getDecision().getDecisionCode3(), verifyFBean.getDecision().getDecisionCode4(), null, verifyFBean.getDecision().getRemarks(), null, null, null, null, null);
			app.setState("24");//状态  拒贷
			this.decision(appId, Constants.PROCESS_END_APP, loginId, BpmConstants.SYSTEM_SYS, null, null, null, null, null, null, null, null, null, null);
		}
		/*else if("antifraud".equals(verifyFBean.getButtonType())){
		//反欺诈
			String nextState=Constants.PROCESS_STATE_ANTIFRAUD;
			this.decision(appId, nextState, loginId, loginId, null, null, null, null);
			app.setState("5");//状态 反欺诈（审核提交）
		}*/
		
		/*
		 * 精英贷测试方案
		 * 规则：        1)	如判断客户性别为女性但是上传影像资料中不包括H类，则显示“女性”
		 * 		 2)	如判断客户性别为女性并且上传影像中包括H类学历证明资料则显示“女性、提供学历证明”
		 *   	 3)	判断客户性别为男性但是上传影像中包括H类学历证明资料则显示“提供学历证明“
		 */
		CreditDecision verifyDecision = verifyFBean.getDecision();
		if(verifyDecision!=null){
			if (!app.getProduct().contains("精英贷")) {
				String gradeRemind = "";
				if (verifyDecision.getProduct().contains("精英贷")) {
					gradeRemind = creditAppHandlerService
							.getGradeRemind(app);
					app.setGradeRemind(gradeRemind);
				}
			}
		}
		
		app.setOperator(loginId);
		app.setUpdateTime(newTime);
		creditAppService.updateOnlyChanged(app);		//更新  申请的 状态 与操作员
	}
	
	/**
	 * 审批决策   数据保存
	 * @param verifyFBean
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Transactional
	public void approvalUpdate(ApprovalFBean approvalFBean,String loginId,String orgId)  throws Exception {
		CreditDecision decision = approvalFBean.getDecision();
		Product pro = productService.queryByName(decision.getProduct());
		double contractAmount = MathUtils
				.div(decision.getAmount(),
						MathUtils.sub(1,
								MathUtils.div(pro.getSreviceFeeRate(), 100.0)));
		//取整百
		if(contractAmount%100>0) {
			contractAmount = ((int)((contractAmount+100)/100))*100;
		}
		decision.setContractAmount(contractAmount); // 合同金额
		CreditApp app = creditAppService.queryByKey(approvalFBean.getId());
		Timestamp newTime=new Timestamp(System.currentTimeMillis());	
		if(decision.getId()==0 && "1".equals(decision.getState())){
			decision.setType("2");
			decision.setOperator(loginId);
			decision.setOrgId(orgId);
			decision.setCreateTime(newTime);
			decision.setUpdateTime(newTime);
			dao.add(decision);
		}else if(decision.getId()!=0){
			decision.setUpdateTime(newTime);
			decision.setOperator(loginId);
			dao.updateOnlyChanged(decision);
		}
		if("submit".equals(approvalFBean.getButtonType())){
			//提交
				String nextState=null;
				String creditVerifyOperator = "";//审核人
				String decisionType = null;//决策
				String noPassCause = null;//拒绝原因
				String noPassCode = null;//拒绝码或者是拒绝码标识
				String decisionCode = null;//违约码或者是拒绝码
				String decisionRemark = decision.getRemarks();//备注
				if("00".equals(decision.getDecision())){//不通过到审批决策
					nextState=Constants.PROCESS_STATE_VERIFY;
					decisionType = "退回";
					noPassCause = decision.getReturnMsg(); //退回原因
					noPassCode = "违例码";
					decisionCode = "违例码1：" + decision.getDecisionCode1() + "——违例码2：" + decision.getDecisionCode2();
					app.setState("4");//状态  审核（退回）
					
					List<BpmLog> bpmLogList = processService.getProcessHistoryLog(app.getAppId(), "审核");
					creditVerifyOperator = bpmLogList.get(0).getOperator();
					String state = userService.getUser(creditVerifyOperator).getState();
					if("0".equals(state)){//不在职 退到审核分单（挂起不变）
						creditVerifyOperator = "";
					}
					//在职 退到原审核人
				}else if("01".equals(decision.getDecision())){//通过到签约
					List<BpmLog> bpmLogList = processService.getProcessHistoryLog(app.getAppId(), "录入申请");
					creditVerifyOperator = bpmLogList.get(0).getOperator();
					nextState=Constants.PROCESS_STATE_SIGN;
					decisionType = "通过";
					app.setState("13");//状态  签约
					//最终决策  
					saveEndSpecialAndLoan2match(decision, loginId, orgId, newTime, app);
					String state = userService.getUser(creditVerifyOperator).getState();
					if("0".equals(state)){//不在职 随机分配原信用风险专员所在部门的信用风险专员（挂起不变）
						List<User> users = userService.getUserByOrgAndRoleAndDepart(app.getOrgId(), new String[]{Constants.ROLE_FXZY},null);
						if (users.size() > 0) {
							User user = users.get(new Random().nextInt(users.size()));
							creditVerifyOperator = user.getLoginId();
							app.setCustomerManager(creditVerifyOperator);
						}
					}
					//在职 提交到原风险专员签约
				}else if("05".equals(decision.getDecision())){//拒贷到拒件
					nextState=Constants.PROCESS_STATE_REJECT;
					decisionType = "拒贷";
					noPassCause = approvalFBean.getFeedbackDescribe(); //反馈销售描述
					noPassCode = "拒贷码";
					decisionCode = "拒贷码1：" + decision.getDecisionCode3() + "——拒贷码2：" + decision.getDecisionCode4();
					app.setState("24");//状态  拒贷
				}else if("04".equals(decision.getDecision())){//到特殊审批
					nextState=Constants.PROCESS_STATE_SPECIAL;
					decisionType = "特殊审批";
					creditVerifyOperator = approvalFBean.getHigtManagerPeople();
					app.setState("10");//状态  特殊审批
				} else if("06".equals(decision.getDecision())){ // 到推送管理
					nextState=Constants.PROCESS_STATE_PUSH;
					decisionType = "推送管理";
					creditVerifyOperator = approvalFBean.getHigtManagerPeople();
					app.setState("12");//状态  推送管理
					//最终决策  
					saveEndSpecialAndLoan2match(decision, loginId, orgId, newTime, app);
				}
				//流程跳转
				this.decision(decision.getAppId(), nextState, loginId, creditVerifyOperator, decisionType, noPassCause, noPassCode, decisionCode, decisionRemark, null, null, null, null, null);
				if("05".equals(decision.getDecision()))
					this.decision(decision.getAppId(), Constants.PROCESS_END_APP, loginId, BpmConstants.SYSTEM_SYS, null, null, null, null, null, null, null, null, null, null);
				app.setOperator(loginId);
				app.setUpdateTime(newTime);
				creditAppService.updateOnlyChanged(app);		//更新  申请的 状态 与操作员
			}
	}
	
	/**
	 * 特殊审批   数据保存
	 * @param verifyFBean
	 * @param loginId
	 * @param orgId
	 * @param buttonType
	 * @throws Exception
	 */
	@Transactional
	public void specialApprovalUpdate(SpecialApprovalFBean specialApprovalFBean,String loginId,String orgId)  throws Exception {
		CreditDecision decision = specialApprovalFBean.getDecision();
		CreditApp app = creditAppService.queryByKey(specialApprovalFBean.getId());
		Timestamp newTime=new Timestamp(System.currentTimeMillis());
		if(decision.getId()==0 && "1".equals(decision.getState())){
			decision.setType("3");
			decision.setOperator(loginId);
			decision.setOrgId(orgId);
			decision.setCreateTime(newTime);
			decision.setUpdateTime(newTime);
			dao.add(decision);
		}else if(decision.getId()!=0){
			decision.setUpdateTime(newTime);
			decision.setOperator(loginId);
			dao.updateOnlyChanged(decision);
		}
		if("submit".equals(specialApprovalFBean.getButtonType())){
		//提交
			String nextState=null;
			String creditApprovalOperator = "";//审批决策人
			String decisionType = null;//决策
			String noPassCause = null;//拒绝原因
			String noPassCode = null;//拒绝码标识
			String decisionCode = null;//拒绝码
			String decisionRemark = decision.getRemarks();//备注
			if("00".equals(decision.getDecision())){//不通过到审批决策
				nextState=Constants.PROCESS_STATE_APPROVAL;
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(app.getAppId(), "审批");
				creditApprovalOperator = bpmLogList.get(0).getOperator();
				decisionType = "退回";
				noPassCause = decision.getReturnMsg(); //退回原因
				app.setState("7");//状态  审批退回
				String state = userService.getUser(creditApprovalOperator).getState();
				if("0".equals(state)){//不在职 调到审批分单（挂起不变）
					creditApprovalOperator = "";
				}
				//在职 退回给原审批人
			}else if("01".equals(decision.getDecision())){//通过到签约
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(app.getAppId(), "录入申请");
				creditApprovalOperator = bpmLogList.get(0).getOperator();
				nextState=Constants.PROCESS_STATE_SIGN;
				decisionType = "通过";
				app.setState("13");//状态  签约
				//最终决策
				saveEndSpecialAndLoan2match(decision, loginId, orgId, newTime, app);
				String state = userService.getUser(creditApprovalOperator).getState();
				if("0".equals(state)){//不在职 随机分配原信用风险专员所在部门的信用风险专员（挂起不变）
					List<User> users = userService.getUserByOrgAndRoleAndDepart(app.getOrgId(), new String[]{Constants.ROLE_FXZY},null);
					if (users.size() > 0) {
						User user = users.get(new Random().nextInt(users.size()));
						creditApprovalOperator = user.getLoginId();
						app.setCustomerManager(creditApprovalOperator);
					}
				}
				//在职 提交到原风险专员签约
			}else if("05".equals(decision.getDecision())){//拒贷到拒件
				nextState=Constants.PROCESS_STATE_REJECT;
				decisionType = "拒贷";
				noPassCause = specialApprovalFBean.getFeedbackDescribe(); //反馈销售描述
				noPassCode = "拒贷码";
				decisionCode = "拒贷码1：" + decision.getDecisionCode3() + "——拒贷码2：" + decision.getDecisionCode4();
				app.setState("24");//状态  拒贷
			} else if("06".equals(decision.getDecision())) { // 到推送管理
				nextState=Constants.PROCESS_STATE_PUSH;
				decisionType = "推送管理";
				app.setState("12");//状态  推送管理
				//最终决策
				saveEndSpecialAndLoan2match(decision, loginId, orgId, newTime, app);
			}
			//流程跳转
			this.decision(decision.getAppId(), nextState, loginId, creditApprovalOperator, decisionType, noPassCause, noPassCode, decisionCode, decisionRemark, null, null, null, null, null);
			if("05".equals(decision.getDecision()))
				this.decision(decision.getAppId(), Constants.PROCESS_END_APP, loginId, BpmConstants.SYSTEM_SYS, null, null, null, null, null, null, null, null, null, null);
			app.setOperator(loginId);
			app.setUpdateTime(newTime);
			creditAppService.updateOnlyChanged(app);		//更新  申请的 状态 与操作员
		}
	}
	/**
	 * 审核、审批决策、特殊审批通过的时候存入最终决策和借款信息
	 * @param decision
	 * @param loginId
	 * @param orgId
	 * @param newTime
	 * @param app
	 * @throws Exception
	 */
	@Transactional
	public void saveEndSpecialAndLoan2match(CreditDecision decision,String loginId,String orgId, Timestamp newTime, CreditApp app) throws Exception{
		//最终决策  
		decision.setOperator(loginId);
		decision.setOrgId(orgId);
		decision.setCreateTime(newTime);
		decision.setUpdateTime(newTime);
		decision.setId(0);
		decision.setType("0");
		dao.add(decision);
		
/*		//撮合队列 添加 借款信息
		Product pro=productService.queryByName(decision.getProduct());
		
		Loan2match loan2Match=new Loan2match();
		loan2Match.setLoanAppId(app.getAppId());
		loan2Match.setType("1"); // 1 新增  2差额
		loan2Match.setMatchType("0");
		loan2Match.setAppTime(app.getInputTime());
		
		double ceje=MathUtils.div(decision.getAmount(),MathUtils.sub(1,MathUtils.div(pro.getSreviceFeeRate(), 100.0)));
		loan2Match.setLoanAmount(ceje); //借款金额
		
		loan2Match.setLoanProduct(decision.getProduct());
		loan2Match.setLoanPeriod(decision.getPeriod());
		loan2Match.setLoanInterestRate(pro.getInterestRate());
//		loan2Match.setLoanServiceRate(pro.getServiceRate());
		loan2Match.setOrgId(app.getOrgId());
//		loan2Match.setUseage();
		loan2Match.setState("1");//初始状态 带撮合
		loan2Match.setOrgId2(orgId);
		loan2matchService.add(loan2Match);
		log.info(loan2Match);*/
	}
	
	/**
	 * 反欺诈处理
	 * @param creditAntifraud
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Transactional
	public void antifraudUpdate(CreditAntifraud creditAntifraud,String loginId,String orgId)  throws Exception {
		String appId=creditAntifraud.getAppId();
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", creditAntifraud.getAppId());
		List<CreditApp> creditAppList = creditAppService.queryList(fmap);
		CreditApp app=creditAppList.get(0);
		
		Timestamp newTime=new Timestamp(System.currentTimeMillis());
		//反欺诈
		if("antifraud".equals(creditAntifraud.getButtonType())){
			String nextState=Constants.PROCESS_STATE_ANTIFRAUD;
			String submitInfo=creditAntifraud.getSubmitInfo();
			
			// 生成随机欺诈处理人
			String processer = this.creditAppHandlerService.generateRandProcesser(Constants.ROLE_QZZY, orgId);
			log.info("============申请" + appId + "提交到反欺诈===============");
			this.decision(appId, nextState, loginId, processer, null, null, null, null, submitInfo, null, null, null, null, null);

//			if(app.getState()=="3"||"3".equals(app.getState())){
//				app.setState("5");//状态 反欺诈（审核提交）
//			}else if(app.getState()=="4"||"4".equals(app.getState())){
//				app.setState("5"); 
//			}else if(app.getState()=="6"||"6".equals(app.getState())){
//				app.setState("9");
//			}else if(app.getState()=="7"||"7".equals(app.getState())){
//				app.setState("9");
//			}else if(app.getState()=="10"||"10".equals(app.getState())){
//				app.setState("12");
//			}else if(app.getState()=="13"||"13".equals(app.getState())){
//				app.setState("16");
//			}else if(app.getState()=="14"||"14".equals (app.getState())){
//				app.setState("16");
//			}else if(app.getState()=="15"||"15".equals(app.getState())){
//				app.setState("16");
//			}
		}
		
		//解除反欺诈
		if("relieveAntifraud".equals(creditAntifraud.getButtonType())){
			String nextState = null;
			String result = creditAntifraud.getResult();
			String resultInfo = creditAntifraud.getResultInfo();
			
			// 审核节点（审核和审批退回）
			if ("3".equals(app.getState()) || "4".equals(app.getState())) {
				nextState=Constants.PROCESS_STATE_VERIFY;
			}
			// 审批节点（审批和特殊审批退回）
			if ("6".equals(app.getState()) || "7".equals(app.getState())) {
				nextState=Constants.PROCESS_STATE_APPROVAL;
			}
			// 特殊审批节点
			if ("10".equals(app.getState())) {
				nextState=Constants.PROCESS_STATE_SPECIAL;
			}
			// 签约节点（签约、撮合完成、合同复核退回）
			if ("13".equals(app.getState()) || "14".equals(app.getState()) || "15".equals(app.getState())) {
				nextState=Constants.PROCESS_STATE_SIGN;
			}
			
			/*if(app.getState()=="3"||"3".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_VERIFY;
			}else if(app.getState()=="4"||"4".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_VERIFY;

			}else if(app.getState()=="6"||"6".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_APPROVAL;
			}else if(app.getState()=="7"||"7".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_APPROVAL;

			}else if(app.getState()=="10"||"10".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_SPECIAL;

			}else if(app.getState()=="13"||"13".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_SIGN;
			}else if(app.getState()=="14"||"14".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_SIGN;
			}else if(app.getState()=="15"||"15".equals(app.getState())){
				nextState=Constants.PROCESS_STATE_SIGN;
			}*/
//			if(app.getState()=="5"||"5".equals(app.getState())){
//				nextState=Constants.PROCESS_STATE_VERIFY;
//				app.setState("3");//状态 反欺诈（审核提交）
//			}else if(app.getState()=="9"||"9".equals(app.getState())){
//				nextState=Constants.PROCESS_STATE_APPROVAL;
//				app.setState("6");
//			}else if(app.getState()=="12"||"12".equals(app.getState())){
//				nextState=Constants.PROCESS_STATE_SPECIAL;
//				app.setState("10");
//			}else if(app.getState()=="16"||"16".equals(app.getState())){
//				fmap.put("state", "1");
//				List<Contract> contractList = contractDao.queryList(fmap);
//				if (contractList.isEmpty() == true || contractList.size() == 0 || "".equals(contractList)){
//					nextState=Constants.PROCESS_STATE_SIGN;
//					app.setState("13");
//				} else if (contractList.isEmpty() == false && contractList.size() > 0){
//					nextState=Constants.PROCESS_STATE_SIGN;
//					app.setState("14");
//				}
//			}
			
			// 获取上一节点处理人
			String processer = this.creditAppHandlerService.getPrevProcesser(appId);
			
			log.info("============申请" + appId + "解除欺诈返回===============");
			
			this.decision(appId, nextState, loginId, processer, result, null, null, null, resultInfo, null, null, null, null, null);
		}
		app.setOperator(loginId);
		app.setUpdateTime(newTime);
		creditAppService.updateOnlyChanged(app);
	}
	
	/***
	 * 销售端拒贷
	 * @param repelLoanFBean
	 * @param loginId
	 * @throws Exception 
	 * @return 
	 */
	@Transactional
	public boolean saleRefuse(CreditApp creditApp, String loginId) throws Exception {
		//取到app
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("appId", creditApp.getAppId());
		List<CreditApp> appList = creditAppService.queryList(appMap);
		CreditApp app = appList.get(0);
		Timestamp newTime=new Timestamp(System.currentTimeMillis());
		//更新app表
		app.setSaleRefuseDescribe(creditApp.getSaleRefuseDescribe());
		app.setSaleRefuseCode1(creditApp.getSaleRefuseCode1());
		app.setSaleRefuseCode2(creditApp.getSaleRefuseCode2());
		app.setOperator(loginId);
		app.setUpdateTime(newTime);
		app.setState("0");//状态 放弃
		creditAppService.updateOnlyChanged(app);
		//更新bpm_log表，更新bpm_task表
		String nextState=null;
		String saleRefuseOperator = "";//前端拒贷人
		String decisionType = null;//决策
		String noPassCause = null;//拒绝原因，反馈销售描述
		String noPassCode = null;//拒绝码或者是拒绝码标识
		String decisionCode = null;//违约码或者是拒绝码
		nextState=Constants.PROCESS_STATE_GIVEUP;
		decisionType = "客户放弃";
		noPassCause = creditApp.getSaleRefuseDescribe(); //反馈销售描述
		noPassCode = "拒贷码";
		decisionCode = "拒贷码1：" + creditApp.getSaleRefuseCode1() + "——拒贷码2：" + creditApp.getSaleRefuseCode2();
		//如果有合同
		Map<String, Object> cQuerymap = new HashMap<String, Object>();
		cQuerymap.put("loanAppId", creditApp.getAppId());
		cQuerymap.put("contractClass", "03");
		List<Contract> contractList = contractService.queryList(cQuerymap);
		if(null != contractList && contractList.size() > 0){
			Contract contract = contractList.get(0);
			contract.setState("0");
			contractService.updateOnlyChanged((Contract) contract);
			loanRepayPlanService.deleteByContractNo(contract.getContractNo());
		}
		this.decision(creditApp.getAppId(), nextState, loginId, saleRefuseOperator, decisionType, noPassCause, noPassCode, decisionCode, null, null, null, null, null, null);
		this.decision(creditApp.getAppId(), Constants.PROCESS_END_APP, loginId, BpmConstants.SYSTEM_SYS, null, null, null, null, null, null, null, null, null, null);
		return true;
	}
	
	/***
	 * 前端拒贷补录：销售端拒贷
	 * @param repelLoanFBean
	 * @param loginId
	 * @throws Exception 
	 * @return 
	 */
	@Transactional
	public boolean saleRefuseReplenish(CreditApp creditApp, String loginId) throws Exception {
		//取到app
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("appId", creditApp.getAppId());
		List<CreditApp> appList = creditAppService.queryList(appMap);
		CreditApp app = appList.get(0);
//		Timestamp newTime=new Timestamp(System.currentTimeMillis());
		//更新app表
		app.setSaleRefuseDescribe(creditApp.getSaleRefuseDescribe());
		app.setSaleRefuseCode1(creditApp.getSaleRefuseCode1());
		app.setSaleRefuseCode2(creditApp.getSaleRefuseCode2());
		creditAppService.updateOnlyChanged(app);
		//更新bpm_log表
		Map<String, Object> saleLogMap = new HashMap<String, Object>();
		saleLogMap.put("bizKey", app.getAppId());
		List<BpmLog>  logList = bpmLogDao.getBpmLog(saleLogMap);
		for(int i=0;i<logList.size();i++){
			if("放弃".equals(logList.get(i).getState())){
				String decisionCode = null;
				decisionCode = "拒贷码1：" + creditApp.getSaleRefuseCode1() + "——拒贷码2：" + creditApp.getSaleRefuseCode2();
				BpmLog needLog = logList.get(i+1);
				needLog.setLogContent1("客户放弃");
				needLog.setLogContent2(creditApp.getSaleRefuseDescribe());
				needLog.setLogContent3("拒贷码");
				needLog.setLogContent4(decisionCode);
				bpmLogDao.updateBpmLog(needLog);
			}
		}
		return true;
	}
}
