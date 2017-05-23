package com.tera.collection.reduce.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.dao.BpmTaskDao;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.BpmFactory;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.phone.service.CollectionBatchService;
import com.tera.collection.reduce.constants.RemissionConstants;
import com.tera.collection.reduce.dao.CollectionRemissionDao;
import com.tera.collection.reduce.model.CollectionRemission;
import com.tera.collection.reduce.model.CollectionRemissionDetail;
import com.tera.collection.reduce.model.form.RemissionJsonMsg;
import com.tera.collection.reduce.model.form.RemissionQBean;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

/**
 * 
 * 减免申请审批表服务类
 * <b>功能：</b>CollectionRemissionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:48:41<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionRemissionService")
public class CollectionRemissionService {

	private final static Logger log = Logger.getLogger(CollectionRemissionService.class);

	@Autowired(required=false)
    private CollectionRemissionDao dao;
	
	@Autowired(required=false)
	private CollectionBaseService collectionBaseService;
	
	@Autowired(required=false)
	private ProcessService processService;
	
	@Autowired(required=false)
	private RemissionHandlerService remissionHandlerService;

	@Autowired(required=false)
	private LoanRepayPlanService loanRepayPlanService;

	@Autowired(required=false)
	private AccounttingService<Accountting> accounttingService;

	@Autowired(required=false)
	private PaymentService<Payment> paymentService;
	
	@Autowired(required=false)
	private CollectionRemissionDetailService collectionRemissionDetailService;
	
	@Autowired(required=false)
	private CollectionBatchService collectionBatchService;
	
	@Autowired(required=false)
	private BpmTaskDao bpmTaskDao;
	

	@Transactional
	public void add(CollectionRemission... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollectionRemission obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CollectionRemission obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CollectionRemission obj)  throws Exception {
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
	
	public List<CollectionRemission> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CollectionRemission queryByKey(Object id) throws Exception {
		return (CollectionRemission)dao.queryByKey(id);
	}
	
	/**
	 * 查找最新一期的减免申请
	 * 
	 * @author QYANZE
	 * 
	 * @param contractNo
	 * @return
	 */
	public CollectionRemission queryLatestApplyByContractNo(String contractNo) {
		return dao.queryLatestApplyByContractNo(contractNo);
	}
	
	public int queryRemissionApplyCount(Map<String, Object> map) {
		return dao.queryRemissionApplyCount(map);
	}
	
	/**
	 * 减免申请列表查询
	 * 
	 * @author QYANZE
	 * 
	 * @param map
	 * @return
	 */
	public List<RemissionQBean> queryRemissionApplyList (Map<String, Object> map) {
		return dao.queryRemissionApplyList(map);
	}
	
	public int queryRemissionReviewCount(Map<String, Object> map) {
		return dao.queryRemissionReviewCount(map);
	}
	
	/**
	 * 减免复核列表查询
	 * 
	 * @author QYANZE
	 * 
	 * @param map
	 * @return
	 */
	public List<RemissionQBean> queryRemissionReviewList (Map<String, Object> map) {
		return dao.queryRemissionReviewList(map);
	}
	
	public int queryRemissionApprovalCount(Map<String, Object> map) {
		return dao.queryRemissionApprovalCount(map);
	}
	
	/**
	 * 减免审批/高级审批列表查询
	 * 
	 * @author QYANZE
	 * 
	 * @param map
	 * @return
	 */
	public List<RemissionQBean> queryRemissionApprovalList (Map<String, Object> map) {
		return dao.queryRemissionApprovalList(map);
	}
	
	/**
	 * 查询历史逾期的还款计划
	 * 
	 * @author QYANZE
	 * 
	 * @param map
	 * @return
	 */
	public List<LoanRepayPlan> queryLatePlanList(Map<String, Object> map) {
		return dao.queryLatePlanList(map);
	}
	
	
	/**
	 * 提交减免申请流程操作
	 * 
	 * @author QYANZE
	 * 
	 * @param contractNo 合同编号
	 * @param repayment 还款金额
	 * @param remissionReason 减免原因
	 * @param orgId 机构id
	 * @throws Exception
	 */
	@Transactional
	public RemissionJsonMsg submitRemissionApply(CollectionRemission remissionApply,
			String loginId, String orgId) throws Exception {
		
		String contractNo = remissionApply.getContractNo();
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", contractNo);
		CollectionBase collectionBase = collectionBaseService.queryList(queryMap).get(0);
		
		queryMap.put("states", new String[]{"1","3"});
		queryMap.put("existPenalty", "true");
		List<LoanRepayPlan> repayPlanList= loanRepayPlanService.queryListExt(queryMap);
		if(repayPlanList == null || repayPlanList.isEmpty()){
			return new RemissionJsonMsg(false, "不存在违约，没有罚息不能减免。");
		}
		
		// 判断是否正在减免申请过程中
		CollectionRemission latestRemission = this.queryLatestApplyByContractNo(contractNo);
		if (latestRemission != null) {
			String[] states = new String[] {
					RemissionConstants.REMISSION_STATE_REVIEW,
					RemissionConstants.REMISSION_STATE_APPROVAL,
					RemissionConstants.REMISSION_STATE_H_APPROVAL };
			List<String> tmpStates = Arrays.asList(states);
			// 如果还在复核、审批、高级审批状态，拒绝再次提起减免申请
			if (tmpStates.contains(latestRemission.getState())) {
				return  new RemissionJsonMsg(false, "已经申请减免");
			}
		}
		
		
		// 根据合同号和当前期数查找还款计划
		Map<String, Object> curMap = new HashMap<String, Object>();
		curMap.put("contractNo", contractNo);
		curMap.put("periodNum", collectionBase.getPeriodCur()+1);
		
		// 得到下一期的还款日期
		List<LoanRepayPlan> loanRepayPlanList = loanRepayPlanService.queryList(curMap);
		
		if (loanRepayPlanList != null && loanRepayPlanList.size() > 0) {
			Date nextRepaymentDate = loanRepayPlanList.get(0).getRepaymentDate();
			// 判断离下一次还款日是否差五天
			int days = DateUtils.getDayRange(new Date(),nextRepaymentDate);
			if (days < 5 && days >= 0) {
				return new RemissionJsonMsg(false, "离下一次还款日还剩" + days + "天，不允许提交减免，如需处理请联系相关人员人工处理");
			} 
		}
		// 罚息总额
		double penalty = collectionBase.getPenalty();
		// 滞纳金总额
		double delay = collectionBase.getDelay();
		// 减免金额
		double remissionAmount = MathUtils.sub(collectionBase.getAmountAll(),
				remissionApply.getReducedAmount()); 
		
		// 判断减免金额不能大于应还总额
		if (remissionAmount <= 0) {
			return new RemissionJsonMsg(false, "还款金额不能大于应还总额。");
		}
		
		// 判断减免金额是否超过罚息和滞纳金总额
		if (remissionAmount > MathUtils.add(penalty, delay)) {
			return new RemissionJsonMsg(false, "实际减免金额超过最高减免金额。");
		}
		
		remissionApply.setState(RemissionConstants.REMISSION_STATE_REVIEW);
		remissionApply.setCreateUid(loginId);
		remissionApply.setUpdateUid(loginId);
		this.add(remissionApply);
		
		List<BpmTask> bpmTasks = processService.getProcessInstanceByBizKey(
				RemissionConstants.REMISSION_PROCESS_NAME, contractNo);
		BpmTask bpmTask = null;
		if (bpmTasks != null && bpmTasks.size() > 0) {
			bpmTask = bpmTasks.get(0);
			bpmTask.setEndFlag("0");
			bpmTask.setState(RemissionConstants.REMISSION_BPM_STATE_START);
			bpmTask.setProcesser(BpmConstants.SYSTEM_SYS);
			bpmTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			bpmTaskDao.updateBpmTask(bpmTask);
		} else {
			bpmTask = processService.startProcessInstanceByName(
					RemissionConstants.REMISSION_PROCESS_NAME, contractNo);
		}
		
		log.info("=============减免流程：["+contractNo+"]已启动=============");
		
		String processer = this.getRandProcesser(RemissionConstants.REMISSION_ROLE_NAME_REVIEW, orgId);
		
		processService.goNext(bpmTask, RemissionConstants.REMISSION_BPM_STATE_REVIEW,processer);
		log.info("=============减免流程：["+contractNo+"]跳转至复核=============");
		
		// 记录减免明细
		int remissionId = remissionApply.getId();
		for (LoanRepayPlan loanRepayPlan : repayPlanList) {
			int loanRepayPlanId = loanRepayPlan.getId();
			collectionRemissionDetailService.recordRemissionDetail(contractNo,
					loanRepayPlanId, remissionId, loginId);
		}
		
		return new RemissionJsonMsg(true, "减免申请成功");
	}

	/**
	 * 随机生成处理人
	 * 
	 * @param roleName 角色名称
	 * @param orgId 机构id
	 * @return
	 */
	private String getRandProcesser(String roleName, String orgId) {
		return remissionHandlerService.generateRandProcesser(roleName, orgId);
	}

	/**
	 * 减免复核、审批流程操作
	 * 
	 * @author QYANZE
	 * 
	 * @param bean
	 * @param loginId
	 * @param orgId
	 * @param operateType 操作类型（复核、审批、高级审批）
	 * @throws Exception 
	 */
	@Transactional
	public RemissionJsonMsg submitWorkFlow(CollectionRemission bean, String loginId,String orgId,
			String operateType) throws Exception {
		
		RemissionJsonMsg jsonMsg = null;
		Timestamp time=new Timestamp(System.currentTimeMillis());
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", bean.getContractNo());
		CollectionBase collectionBase = collectionBaseService.queryList(queryMap).get(0);
		double remissionAmount = MathUtils.sub(collectionBase.getAmountAll(), bean.getReducedAmount());
		BpmTask currentTask = processService.getProcessInstanceByBizKey(
				RemissionConstants.REMISSION_PROCESS_NAME,
				bean.getContractNo()).get(0);
		currentTask.setOperator(loginId);
		String processer = "";
		String logContent1 = "";
		String logContent2 = "";
		
		// 复核流程操作
		if (RemissionConstants.REMISSION_OPERATE_TYPE_REVIEW
				.equals(operateType)) {
			// 复核通过
			if (RemissionConstants.REMISSION_RESULT_PASS.equals(bean
					.getCheckResult())) {
				
				logContent1 = "减免复核通过";
				logContent2 = bean.getCheckText();
				currentTask.setVariable("logContent1", logContent1);
				currentTask.setVariable("logContent2", logContent2);
				
				if (remissionAmount <= 1000.00) {
					processer = this.getRandProcesser(RemissionConstants.REMISSION_ROLE_NAME_APPROVAL, orgId);
					
					processService.goNext(currentTask,
									RemissionConstants.REMISSION_BPM_STATE_APPROVAL,processer);
					log.info("==============减免流程：[" + bean.getContractNo()
							+ "]复核提交到审批");

					bean.setState(RemissionConstants.REMISSION_STATE_APPROVAL);
				} else {
					processer = this.getRandProcesser(RemissionConstants.REMISSION_ROLE_NAME_H_APPROVAL, orgId);
					processService.goNext(currentTask,
									RemissionConstants.REMISSION_BPM_STATE_H_APPROVAL,processer);
					log.info("==============减免流程[：" + bean.getContractNo()
							+ "]复核提交到高级审批");

					bean.setState(RemissionConstants.REMISSION_STATE_H_APPROVAL);
				}
			} else
			// 复核否决
			if (RemissionConstants.REMISSION_RESULT_N_PASS.equals(bean
					.getCheckResult())) {
				
				logContent1 = "减免复核否决";
				logContent2 = bean.getCheckText();
				currentTask.setVariable("logContent1", logContent1);
				currentTask.setVariable("logContent2", logContent2);
				
				processService.goNext(currentTask,
						RemissionConstants.REMISSION_BPM_STATE_END, loginId);
				log.info("==============减免流程[：" + bean.getContractNo() + "]结束");
				
				bean.setState(RemissionConstants.REMISSION_STATE_REVIEW_N_PASS); // 复核否决
			}
			bean.setCheckTime(time);
			
			jsonMsg = new RemissionJsonMsg(true, "操作成功");
		}
		
		// 审批/高级审批先判断是否发生还款操作
		if (RemissionConstants.REMISSION_OPERATE_TYPE_APPROVAL
				.equals(operateType)
				|| RemissionConstants.REMISSION_OPERATE_TYPE_H_APPROVAL
						.equals(operateType)) {
			
			if (RemissionConstants.REMISSION_RESULT_PASS
					.equals(bean.getApprovalResult())) {
				
				double receivedRepayAmount = receivedRepayAmount(bean.getContractNo(), bean.getId());
				if (receivedRepayAmount > 0) {
					// 发生还款减免失效
					bean.setState(RemissionConstants.REMISSION_STATE_N_PASS);
					bean.setUpdateUid(loginId);
					bean.setApprovalTime(time);
					if (RemissionConstants.REMISSION_OPERATE_TYPE_APPROVAL
							.equals(operateType)) {
						bean.setRemark(RemissionConstants.REMISSION_REMARK_APPROVAL);
					} else if (RemissionConstants.REMISSION_OPERATE_TYPE_H_APPROVAL
							.equals(operateType)) {
						bean.setRemark(RemissionConstants.REMISSION_REMARK_H_APPROVAL);
					}
					logContent1 = "减免无效";
					logContent2 = "客户已还款";
					currentTask.setVariable("logContent1", logContent1);
					currentTask.setVariable("logContent2", logContent2);
					processService.goNext(currentTask,RemissionConstants.REMISSION_BPM_STATE_END,loginId);

					this.updateOnlyChanged(bean);

					return new RemissionJsonMsg(false, "本期客户已还款"
							+ receivedRepayAmount + "钱，如需减免，请手工操作。");
				}
			}
		}
		
		// 审批流程操作
		if (RemissionConstants.REMISSION_OPERATE_TYPE_APPROVAL
				.equals(operateType)) {
			String approvalResult = bean.getApprovalResult();
			logContent2 = bean.getApprovalText();
			bean.setRemark(RemissionConstants.REMISSION_REMARK_APPROVAL);
			// 审批通过
			if (RemissionConstants.REMISSION_RESULT_PASS
					.equals(approvalResult)) {
				
				bean.setState(RemissionConstants.REMISSION_STATE_PASS);
				logContent1 = "减免审批通过";
				
				// 更新还款计划、划扣、记账
				updateRepayPlanOperate(bean.getContractNo(),bean.getId(), remissionAmount,loginId, orgId);
				
				jsonMsg = new RemissionJsonMsg(true, "减免成功");
			}
			
			// 审批不通过
			if (RemissionConstants.REMISSION_RESULT_N_PASS
					.equals(approvalResult)) {
				bean.setState(RemissionConstants.REMISSION_STATE_APPROVAL_N_PASS); // 审批否决
				logContent1 = "减免审批否决";
				
				jsonMsg = new RemissionJsonMsg(true, "操作成功");
			}
			
			currentTask.setVariable("logContent1", logContent1);
			currentTask.setVariable("logContent2", logContent2);
			
			processService.goNext(currentTask,
					RemissionConstants.REMISSION_BPM_STATE_END, loginId);
			bean.setApprovalTime(time);
		}
		
		// 高级审批流程操作
		if (RemissionConstants.REMISSION_OPERATE_TYPE_H_APPROVAL
				.equals(operateType)) {
			String approvalResult = bean.getApprovalResult();
			logContent2 = bean.getApprovalText();
			bean.setRemark(RemissionConstants.REMISSION_REMARK_H_APPROVAL);
			// 高级审批通过
			if (RemissionConstants.REMISSION_RESULT_PASS
					.equals(approvalResult)) {

				bean.setState(RemissionConstants.REMISSION_STATE_PASS);
				logContent1 = "减免高级审批通过";
				
				// 更新还款计划、划扣、记账
				updateRepayPlanOperate(bean.getContractNo(), bean.getId(),remissionAmount,loginId, orgId);
				
				jsonMsg = new RemissionJsonMsg(true, "减免成功");
			}
			
			// 高级审批不通过
			if (RemissionConstants.REMISSION_RESULT_N_PASS
					.equals(approvalResult)) {
				bean.setState(RemissionConstants.REMISSION_STATE_APPROVAL_N_PASS);// 审批否决
				logContent1 = "减免高级审批否决";
				
				jsonMsg = new RemissionJsonMsg(true, "操作成功");
			}
			
			currentTask.setVariable("logContent1", logContent1);
			currentTask.setVariable("logContent2", logContent2);
			
			processService.goNext(currentTask,
					RemissionConstants.REMISSION_BPM_STATE_END, loginId);
			bean.setApprovalTime(time);
		}
		
		// 更新减免申请表
		bean.setUpdateUid(loginId);
		this.updateOnlyChanged(bean);
		
		return jsonMsg;
	}
	
	/**
	 * 计算实收金额
	 * 
	 * @author QYANZE
	 * 
	 * @param contractNo 合同编号
	 * @param periodCur 当前期数
	 * @param periodLateHis 历史逾期期数
	 * @return
	 * @throws Exception 
	 */
	private double receivedRepayAmount(String contractNo, int remissionId) throws Exception {
		double repayAmount = 0.0;
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("remissionId", remissionId);
		List<CollectionRemissionDetail> details = collectionRemissionDetailService
				.queryList(queryMap);
		
		List<Integer> latePlanIds = new ArrayList<Integer>(details.size());
		for (CollectionRemissionDetail detail : details) {
			latePlanIds.add(detail.getLoanRepayPlanId());
		}
		queryMap.clear();
		queryMap.put("latePlanIds", latePlanIds);
		
		List<LoanRepayPlan> latePlanList = this.queryLatePlanList(queryMap);
		
		if (latePlanList != null && latePlanList.size() > 0) {
			
			for (LoanRepayPlan latePlan : latePlanList) {
				// 如果逾期有还款操作，计算还款金额
				if ("2".equals(latePlan.getState())) {
					double sreviceFeeReceived = latePlan
							.getSreviceFeeReceived(); // 实收服务费
					double interestReceived = latePlan
							.getInterestReceived(); // 实收利息
					double principalReceived = latePlan
							.getPrincipalReceived(); // 实收本金
					double penaltyReceived = latePlan
							.getPenaltyReceived(); // 实收罚息
					double defaultReceived = latePlan
							.getDefaultReceived(); // 实收违约金
					double delayReceived = latePlan.getDelayReceived(); // 实收滞纳金
					double addAmount1 = MathUtils.add(principalReceived,
							interestReceived);// 实收本金加利息
					double addAmount2 = MathUtils.add(sreviceFeeReceived,
							defaultReceived); // 实收服务费加违约金
					double addAmount3 = MathUtils.add(penaltyReceived,
							delayReceived); // 实收罚息加滞纳金
					double tmpAmount = MathUtils.add(addAmount1, addAmount2);
					double endAmount = MathUtils.add(addAmount3, tmpAmount);
					
					repayAmount = MathUtils.add(repayAmount, endAmount);
				}
			}
		}
		return repayAmount;
	}

	/**
	 * 更新还款计划、划扣、记账
	 * 
	 * @author QYANZE
	 * 
	 * @param contractNo
	 * @param remissionId
	 * @param reduceAmount
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Transactional
	private void updateRepayPlanOperate(String contractNo,int remissionId, double reduceAmount,
			String loginId, String orgId) throws Exception {
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("remissionId", remissionId);
		List<CollectionRemissionDetail> details = collectionRemissionDetailService
				.queryList(queryMap);
		
		List<Integer> latePlanIds = new ArrayList<Integer>(details.size());
		for (CollectionRemissionDetail detail : details) {
			latePlanIds.add(detail.getLoanRepayPlanId());
		}
		queryMap.clear();
		queryMap.put("latePlanIds", latePlanIds);
		
		List<LoanRepayPlan> latePlanList = this.queryLatePlanList(queryMap);
		
		if (latePlanList !=null && latePlanList.size() > 0) {
			for (LoanRepayPlan loanRepayPlan : latePlanList) {
				// 滞纳金减免记账 
				Accountting account=new Accountting();
				account.setInOut("2");
				account.setContractNo(loanRepayPlan.getContractNo());
				account.setPeriodNum(loanRepayPlan.getPeriodNum());
				account.setState("1");
				account.setOperator(loginId);
				account.setOrgId(orgId);
				long time=System.currentTimeMillis();
				account.setCreateTime(new Timestamp(time));
				account.setUpdateTime(new Timestamp(time));
				
				double accountingAmount = 0.0; // 减免记账金额
				
				// 1、滞纳金减免
				double surplusDelayAmount = MathUtils.sub(
						loanRepayPlan.getDelayReceivable(),
						loanRepayPlan.getDelayReceived()); // 剩余应还滞纳金金额
				
				double maxReduceDelayAmount = MathUtils.sub(surplusDelayAmount,
						loanRepayPlan.getDelayReduce()); // 最大可减免的滞纳金金额
				
				if (maxReduceDelayAmount > 0 && reduceAmount > 0) {
					if(reduceAmount >= maxReduceDelayAmount){
						reduceAmount = MathUtils.sub(reduceAmount,maxReduceDelayAmount);
						accountingAmount = maxReduceDelayAmount;
					}else{
						accountingAmount = reduceAmount;
						reduceAmount = 0.0;
					}
					
					account.setAccount("其它收入");
					account.setSubject("业务往来-滞纳金减免");
					account.setPlanAmount(accountingAmount);
					account.setActualAmount(accountingAmount);
					accounttingService.add(account);
					
					loanRepayPlan.setDelayReduce(MathUtils.add(
							loanRepayPlan.getDelayReduce(), accountingAmount));
				}
				
				// 2、罚息减免
				double surplusPenaltyAmount = MathUtils.sub(
						loanRepayPlan.getPenaltyReceivable(),
						loanRepayPlan.getPenaltyReceived()); // 剩余应还罚息金额
				
				double maxReducePenaltyAmount = MathUtils.sub(surplusPenaltyAmount,
						loanRepayPlan.getPenaltyReduce()); // 最大可减免的罚息金额
				
				if (maxReducePenaltyAmount > 0 && reduceAmount > 0) {
					if(reduceAmount >= maxReducePenaltyAmount){
						reduceAmount = MathUtils.sub(reduceAmount,maxReducePenaltyAmount);
						accountingAmount = maxReducePenaltyAmount;
					}else{
						accountingAmount = reduceAmount;
						reduceAmount = 0.0;
					}
					
					account.setAccount("其它收入");
					account.setSubject("业务往来-罚息减免");
					account.setPlanAmount(accountingAmount);
					account.setActualAmount(accountingAmount);
					accounttingService.add(account);
					
					loanRepayPlan.setPenaltyReduce(MathUtils.add(
							loanRepayPlan.getPenaltyReduce(), accountingAmount));
				}
				
				// 更新还款计划
				loanRepayPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				loanRepayPlanService.update(loanRepayPlan);
				
				// 更新payMent数据
				queryMap.clear();
				queryMap.put("contractNo", loanRepayPlan.getContractNo());
				queryMap.put("periodNum", loanRepayPlan.getPeriodNum());
				queryMap.put("inOut", "1");
				queryMap.put("subject", "收本息");
				List<Payment>  listPayments=paymentService.queryList(queryMap);
				double repayAmount = loanRepayPlanService.countLoanRepayPlan(loanRepayPlan); // 还款计划应还金额
				Payment payment=listPayments.get(0);
				payment.setPlanAmount(repayAmount);
				payment.setActualAmount(repayAmount);
				payment.setOperator(loginId);
				payment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				paymentService.update(payment);
				
				// 如果减免金额为0，跳出
				if (reduceAmount == 0) {
					break;
				}
			}
			// 调用方法更新collectionBase
			collectionBatchService.syncCollectionBaseData(contractNo);
		}
	}
}
