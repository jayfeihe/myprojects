package com.tera.match.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.dao.ContractDao;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditAppService;
import com.tera.lend.dao.LendAppDao;
import com.tera.lend.model.LendApp;
import com.tera.match.dao.MatchResultDao;
import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchInfo;
import com.tera.match.model.MatchModel;
import com.tera.match.model.MatchResult;
import com.tera.payment.dao.PaymentDao;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.Workday;
import com.tera.sys.service.ParameterService;
import com.tera.sys.service.WorkdayService;
import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>MatchResultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-10 10:19:27<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("matchResultService")
public class MatchResultService<T> {

	private final static Logger log = Logger.getLogger(MatchResultService.class);

	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired(required=false)
    private MatchResultDao<MatchResult> dao;
	@Autowired(required=false)
	private ContractService contractService;
	//注入相关的service
	@Autowired(required=false)
	private Loan2matchService<Loan2match> loan2matchService;
	@Autowired(required=false)
	private Lend2matchService<Lend2match> lend2matchService;
	@Autowired(required=false)
	private WorkdayService<Workday> workdayService;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	
	/**
	 * 处理两个list中所有的时间为空的，预设一个开始和结束时间
	 * @param matchModel loan和lend的两个list处理
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public MatchModel handleDate(MatchModel matchModel) throws Exception{
		
		//设置listlend和listloan的开始时间和结束时间
		for (int i = 0; i < matchModel.getListLoan().size(); i++) {
			if (MatchUtil.testLoan2Match(matchModel.getListLoan().get(i))==1) {
				matchModel.getListLoan().get(i).setStartDate(DateUtils.getDateNow());
				Date dt=DateUtils.addMonth(DateUtils.getDateNow(),matchModel.getListLoan().get(i).getLoanPeriod());
				matchModel.getListLoan().get(i).setEndDate(DateUtils.addDay(dt, -1));
			}
		}
		//lend的结束时间在记录插入之前已经写好了。
		for (int i = 0; i < matchModel.getListLend().size(); i++) {
			Lend2match lendTmp=matchModel.getListLend().get(i);
			if (MatchUtil.testLend2Match(lendTmp)==1) {
				lendTmp.setStartDate(LendGetStartDate(lendTmp));
//				Date dt=workdayService.afterWorkDay(lendTmp.getAppTime(), 3);
//				dt=DateUtils.addMonth(dt, lendTmp.getLendPeriod());
//				dt=DateUtils.addDay(dt, -1);
//				lendTmp.setEndDate(dt);
				matchModel.getListLend().set(i, lendTmp);
			}
		}
		
		
		return matchModel;
		
	}

	/**
	 * 获取lend的开始时间
	 * @param lend2match
	 * @return
	 * @throws Exception
	 */
	public Date LendGetStartDate(Lend2match lend2match) throws Exception {
		
		if (MatchUtil.testLend2Match(lend2match)==1) {
			Date dt1=workdayService.afterWorkDay(lend2match.getAppTime(), 4);
			Date dt2=DateUtils.getDateNow();
			if (DateUtils.compareDate(dt1, dt2)>0) {
				return dt2;
			}else {
				return dt1;
			}
		}else if (MatchUtil.testLend2Match(lend2match)==2) {
			
			return lend2match.getStartDate();
		}else if (MatchUtil.testLend2Match(lend2match)==3) {
			
			return lend2match.getStartDate();
		}
		
		return DateUtils.getDateNow();
	}
	
	/**
	 * 放弃一个申请合同
	 * @param AppId
	 * @throws Exception
	 */
	 @Transactional
	public void giveupApp(String loanAppId) throws Exception{
		List<MatchResult> listMatch= queryByLoanAppId(loanAppId);
		delMatchResult(listMatch);
		
	}
	/**
	 * 合同放弃，匹配记录的批量删除，回退处理
	 * @param matchModel
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void delMatchResult(List<MatchResult> listMatch) throws Exception{
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		
		for (int i = 0; i <listMatch.size(); i++) {
			Double dbAmount=listMatch.get(i).getLendAmount();
			Lend2match lend2match=lend2matchService.queryBasicByKey(listMatch.get(i).getLendAppId());
			//加独占锁
			//Lend2match lend2match=lend2matchService.queryLockBasicByKey(listMatch.get(i).getLendAppId());
			
			
			lend2match.setLendAmount(lend2match.getLendAmount()+dbAmount);
			if (lend2match.getLendAmount()!=0) {
				lend2match.setState("1");
			}
			lend2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			//根据appid获取Matchlist
			List<MatchResult> listLendMatchResults=queryByLendAppId(listMatch.get(i).getLendAppId());
			if (listLendMatchResults.size()==1) { //只有这一条
				lend2match.setStartDate(null);
				lend2match.setContractStartDate(null);
				lend2match.setContractEndDate(null);
			}else {//有多条
				if (MatchUtil.testLend2Match(lend2match)!=3) {//未签订过合同
					if (DateUtils.compareDate(listMatch.get(i).getStartDate(), lend2match.getStartDate())==0) {
						//是同一天。从剩余的记录中找一个日期最早的，并和计息日多对比
						listLendMatchResults.remove(listMatch.get(i));
						Date dt=listLendMatchResults.get(0).getStartDate();
						Date dt1=workdayService.afterWorkDay(lend2match.getAppTime(), 4);
						//取日期的最小值
						for (int j = 1; j < listLendMatchResults.size(); j++) {
							int x=DateUtils.compareDate(dt, listLendMatchResults.get(j).getStartDate());
							if (x>0) {
								dt=listLendMatchResults.get(j).getStartDate();
							}
						}
						int y=DateUtils.compareDate(dt, dt1);
						if (y>0) {
							lend2match.setStartDate(dt1);
						}else {
							lend2match.setStartDate(dt);
						}
					}
				}
			}
			
			Loan2match loan2match=loan2matchService.queryLockBasicByKey(listMatch.get(i).getLoanAppId());
			loan2match.setLoanAmount(loan2match.getLoanAmount()+dbAmount);
			if (loan2match.getLoanAmount()!=0) {
				loan2match.setState("1");
			}
			loan2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			List<MatchResult> listLoanMatchResults=queryByLoanAppId(listMatch.get(i).getLoanAppId());
			if (listLoanMatchResults.size()==1) { //只有这一条
				loan2match.setStartDate(null);
				loan2match.setEndDate(null);
				loan2match.setContractStartDate(null);
				loan2match.setContractEndDate(null);
			}else {//有多条
				if (DateUtils.compareDate(listMatch.get(i).getStartDate(), loan2match.getStartDate())==0) {
					//是同一天。从剩余的记录中找一个日期最早的
					listLoanMatchResults.remove(listMatch.get(i));
					Date dt=listLoanMatchResults.get(0).getStartDate();
					
					//取日期的最小值
					for (int j = 1; j < listLoanMatchResults.size(); j++) {
						int x=DateUtils.compareDate(dt, listLoanMatchResults.get(j).getStartDate());
						if (x>0) {
							dt=listLoanMatchResults.get(j).getStartDate();
						}
					}
					loan2match.setStartDate(dt);
					Date dtend1=DateUtils.addMonth(loan2match.getStartDate(), loan2match.getLoanPeriod());
					loan2match.setEndDate(dtend1);
				}
			}
			
			//更改matchResult的状态
			listMatch.get(i).setState("0");
			//listMatch.get(i).setOperator("");
			listMatch.get(i).setUpdateTime(new Timestamp(System.currentTimeMillis()));
			//执行更新操作
			lend2matchService.update(lend2match);
			loan2matchService.update(loan2match);
			update(listMatch.get(i));	
		}
	}
	/**
	 * 人工撮合添加一条记录 
	 * @param matchModel参数：lend2Match,List<Loan2match>，封装到matchModel中，用户名
	 * @throws Exception
	 */
	@Transactional
	public void addArtificialMatch (MatchModel matchModel,String userName)  throws Exception {
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		
		if (MatchUtil.testLend2Match(matchModel.getLend2match())==1) {
			matchModel.getLend2match().setStartDate(LendGetStartDate(matchModel.getLend2match()));
			Date dt=workdayService.afterWorkDay(matchModel.getLend2match().getAppTime(), 4);
			dt=DateUtils.addMonth(dt, matchModel.getLend2match().getLendPeriod());
			dt=DateUtils.addDay(dt, -1);
			matchModel.getLend2match().setEndDate(dt);
		}
		
		for (int k = 0; k < matchModel.getListLoan().size(); k++) {
			if (matchModel.getLend2match().getLendAmount()<=0) {
				break;
			}
			//处理时间
			if (MatchUtil.testLoan2Match(matchModel.getListLoan().get(k))==1) {
				matchModel.getListLoan().get(k).setStartDate(DateUtils.getDateNow());
				Date dt=DateUtils.addMonth(DateUtils.getDateNow(),matchModel.getListLoan().get(k).getLoanPeriod());
				matchModel.getListLoan().get(k).setEndDate(DateUtils.addDay(dt, -1));
			}
			Loan2match loan2match=loan2matchService.queryByKey(matchModel.getListLoan().get(k).getId());
			Loan2match loanTmp=matchModel.getListLoan().get(k);
			if (loan2match.getLoanAmount()<loanTmp.getLoanAmount()) {
				continue;
			}
			if (loanTmp.getLoanAmount()==0) {
				continue;
			}
			Lend2match lend2match=matchModel.getLend2match();
			
			MatchResult matchResult=new MatchResult();
			
			int comp =DateUtils.compareDate(MatchUtil.LendGetEndDate(lend2match), MatchUtil.LoanGetEndDate(loanTmp));
			if (comp>0) {//前者比后者大
				matchResult.setEndDate( MatchUtil.LoanGetEndDate(loanTmp));
			}else {
				matchResult.setEndDate(MatchUtil.LendGetEndDate(lend2match));
			}
			matchResult.setLendAmount(loanTmp.getLoanAmount());
			matchResult.setLoanAmount(loanTmp.getLoanAmount());
			matchResult.setStartDate(DateUtils.getDateNow());
			matchResult.setCreateTime(new Timestamp(System.currentTimeMillis()));
			matchResult.setLendAppId(lend2match.getLendAppId());
			matchResult.setLendInterestRate(lend2match.getLendInterestRate());
			matchResult.setLendPeriod(lend2match.getLendPeriod());
			matchResult.setLendProduct(lend2match.getLendProduct());
			matchResult.setLendType(lend2match.getType());
			matchResult.setLoanAppId(loanTmp.getLoanAppId());
			matchResult.setLoanInterestRate(loanTmp.getLoanInterestRate());
			matchResult.setLoanPeriod(loanTmp.getLoanPeriod());
			matchResult.setLoanProduct(loanTmp.getLoanProduct());
			matchResult.setLoanType(loanTmp.getType());
			matchResult.setMatchTime(new Timestamp(System.currentTimeMillis()));
			matchResult.setMatchType(loanTmp.getMatchType());
			matchResult.setOperator(userName);
			matchResult.setUseage(loanTmp.getUseage());
			matchResult.setFlag("1");
			matchResult.setState("4");
			
			loan2match.setLoanAmount(loan2match.getLoanAmount()-loanTmp.getLoanAmount());
			lend2match.setLendAmount(lend2match.getLendAmount()-loanTmp.getLoanAmount());
			//每次添加的中间状态为4，最后一次匹配完余额为0之后，更改所有的记录状态为1
			
			if (loan2match.getLoanAmount()==0) {
				loan2match.setState("2");
				
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("state", "4");
				map.put("loanAppId", loanTmp.getLoanAppId());
				List<MatchResult> listMatch=queryList(map);
				if (listMatch.size()!=0) {
					for (int i = 0; i <listMatch.size(); i++) {
						listMatch.get(i).setState("1");
						update(listMatch.get(i));
					}
				}
				matchResult.setState("1");
			}
			//添加记录
			add(matchResult);
			//更新loan记录
			loan2match.setStartDate(matchModel.getListLoan().get(k).getStartDate());
			loan2match.setEndDate(matchModel.getListLoan().get(k).getEndDate());
			loan2matchService.update(loan2match);
			
			matchModel.setLend2match(lend2match);
			
		}
		if (matchModel.getLend2match().getLendAmount()==0) {
			matchModel.getLend2match().setState("2");
		}
		lend2matchService.update(matchModel.getLend2match());
	}
	
	
	@Transactional
	public void add(MatchResult... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(MatchResult t : ts ){
			dao.add(t);
		}
	}
	
	/**
	 * 清理撮合中的超时记录，释放债权
	 * @throws Exception
	 */
	@Transactional
	public void clearMatchResult() throws Exception{
		
		//从result中查出状态为1的记录		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("matchType", "0");
		map.put("state", "1"); //1	待签合同
		map.put("flag", "1");
		
		List<MatchResult> listResults= dao.queryList(map);
		//抵押贷清理
		//判断开始时间是否到今天已经过了一个工作日
		//过了一个工作日，执行清理
		List<MatchResult> loanListClear=new ArrayList<MatchResult>();
		for (int i = 0; i < listResults.size(); i++) {
			//抵押贷
			if(listResults.get(i).getLoanAppId().startsWith("B")) {
				Date dtClearDate=workdayService.afterWorkDay(listResults.get(i).getStartDate(), 1);
				int com=DateUtils.compareDate(dtClearDate, DateUtils.getDateNow());
				if (com>=0) {
					//可以清理,记录
					loanListClear.add(listResults.get(i));
				}
			}
		}
		//调用回退方法，清理
		delMatchResult(loanListClear);
		
		//信用贷清理
		//在签约节点，没有过复核节点的单子，过了今天要清理
		//	1)t_contract	       当前合同状态 置为 0
		//  2)t_loan_repay_plan 还款计划删除
		//  3)t_credit_app   当前申请状态 置为 13 //签约
		
		delContract2();//清理二期撮合
		
	}
	
	/**
	 * 清理二期撮合
	 * 1)t_contract	       当前合同状态 置为 0
	 * 2)t_loan_repay_plan 还款计划删除
	 * 3)t_credit_app   当前申请状态 置为 13 //签约
	 * @throws Exception 
	 */
	@Transactional
	public void delContract2() throws Exception{
		//获取状态为已撮合的(14),流程节点为签约的所有申请进行清理
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
//		map.put("state", "14");
//		map.put("bpmStates", Constants.PROCESS_STATE_SIGN);
		map.put("states", new String[]{"14"});
		map.put("bpmStates",new String[]{Constants.PROCESS_STATE_SIGN});
		map1.put("state", "1");
		List<CreditApp> creditAppList= creditAppService.queryBpmLoanAppList(map);
		List<MatchResult> matchResultList = new ArrayList<MatchResult>();
		for (CreditApp creditApp : creditAppList) {
			creditApp.setState("13");
			creditAppService.update(creditApp);//当前申请状态 置为 13 //签约
			map1.put("loanAppId", creditApp.getAppId());
			List<Contract> contractList = contractService.queryList(map1);
			if(contractList.size() > 0){
				Contract contract = contractList.get(0);
				loanRepayPlanService.deleteByContractNo(contract.getContractNo());//还款计划删除
				contract.setState("0");
				contractService.update(contract);//当前合同状态 置为 0
			}
			List<MatchResult> listMatch = queryByLoanAppId(creditApp.getAppId());
			matchResultList.addAll(listMatch);
		}
		this.delMatchResult(matchResultList);//放弃 签约 撮合以及金额回退
	}
	
	/**
	 * 处理到期的合同和匹配的记录
	 * @throws Exception 
	 */
	@Transactional
	public void handleContract() throws Exception{
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		//先处理合同
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("states", new String[]{"2","3"});
		List<Contract> listContracts=contractService.queryList(map);
		for (Contract contract : listContracts) {
			int com=DateUtils.compareDate(contract.getEndDate(), DateUtils.getDateNow());
			if (com<0 || "3".equals(contract.getState())) { //合同到期
				if("2".equals(contract.getState())){
					contract.setState("4");
				}
				contractService.update(contract);
				
				Map<String, Object> mapResult=new HashMap<String, Object>();
				mapResult.put("loanAppId", contract.getLoanAppId());
				mapResult.put("state", "3");
				List<MatchResult> listResults=dao.queryList(mapResult);
				for (MatchResult matchResult : listResults) {
					matchResult.setState("5");
					dao.update(matchResult);
					
					Map<String, Object> mapLend=new HashMap<String, Object>();
					mapLend.put("lendAppId",matchResult.getLendAppId());
					List<Lend2match> listLend2matchs=lend2matchService.queryList(mapLend);
					for (Lend2match lend2match : listLend2matchs) {
						int comlend=DateUtils.compareDate(matchResult.getEndDate(), lend2match.getContractEndDate());
						if (comlend>=0) {
							if (!"3".equals(lend2match.getState())) {
								lend2match.setState("3");
								lend2matchService.update(lend2match);
							}
							
						}else {
							lend2match.setLendAmount(lend2match.getLendAmount()+matchResult.getLendAmount());
							if ("1".equals(lend2match.getType())) {
								lend2match.setType("2");
							}
							if ("2".equals(lend2match.getState())) {
								lend2match.setState("1");
							}
							
							lend2matchService.update(lend2match);
						}
						
					}
					Map<String, Object> mapLoan=new HashMap<String, Object>();
					mapLoan.put("loanAppId", contract.getLoanAppId());
					List<Loan2match> listLoan2matchs=loan2matchService.queryList(mapLoan);
					for (Loan2match loan2match : listLoan2matchs) {
						loan2match.setState("5");
						loan2matchService.update(loan2match);
					}
				}
			}
		
			//处理不是合同到期的，出借期限中间到期的记录
			Map<String, Object> mapResult=new HashMap<String, Object>();
			mapResult.put("state", "3");
			List<MatchResult> listResults=dao.queryList(mapResult);
			
			Map<String, Object> mapResult1=new HashMap<String, Object>();
			mapResult1.put("state", "2");
			List<MatchResult> listResults1=dao.queryList(mapResult1);
			//合并到一个list中
			for (int i = 0; i < listResults1.size(); i++) {
				listResults.add(listResults1.get(i));
			}
			for (MatchResult matchResult : listResults) {
				int comTmp=DateUtils.compareDate(matchResult.getEndDate(), DateUtils.getDateNow());
				if (comTmp<0) {
					
					Map<String, Object> mapLoan=new HashMap<String, Object>();
					mapLoan.put("loanAppId",matchResult.getLoanAppId());
					List<Loan2match> listLoan2matchs=loan2matchService.queryList(mapLoan);
					for (Loan2match loan2match : listLoan2matchs) {
						loan2match.setLoanAmount(loan2match.getLoanAmount()+matchResult.getLoanAmount());
						if ("1".equals(loan2match.getType())&&"3".equals(matchResult.getState())) {
							loan2match.setType("2");
						}
						if (loan2match.getLoanAmount()>0) {
							loan2match.setState("1");
						}
						loan2matchService.update(loan2match);
					}
					matchResult.setState("5");
					dao.update(matchResult);
					
					Map<String, Object> mapLend=new HashMap<String, Object>();
					mapLend.put("lendAppId",matchResult.getLendAppId());
					List<Lend2match> listLend2matchs=lend2matchService.queryList(mapLend);
					for (Lend2match lend2match : listLend2matchs) {
						int comlend=DateUtils.compareDate(matchResult.getEndDate(), MatchUtil.LendGetEndDate(lend2match));
						if (comlend>=0) {
							
							if (!"3".equals(lend2match.getState())) {
								lend2match.setState("3");
								lend2matchService.update(lend2match);
							}
							
						}else {
							lend2match.setLendAmount(lend2match.getLendAmount()+matchResult.getLendAmount());
							if ("1".equals(lend2match.getType())) {
								lend2match.setType("2");
							}
							if (lend2match.getLendAmount()!=0) {
								lend2match.setState("1");
							}
							lend2matchService.update(lend2match);
						}
					}
				}
			}
		}
	}
	/**
	 * （一期）放款完成后的处理，包括合同的开始和结束日期，matchresult中的日期，以及lendMatch和loanmatch的有效期
	 * @param ContractNo 合同号
	 * @param dtStart	开始日期
	 * @throws Exception 
	 */
	@Transactional
	public void handleAfterPayment(String ContractNo,Date dtStart) throws Exception{
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		
		//获取loanAppId
		Map<String, Object> mapCon=new HashMap<String, Object>();
		mapCon.put("contractNo", ContractNo);
		mapCon.put("state", "1");
		List<Contract>listContracts=contractService.queryList(mapCon);
		if (listContracts.size()==0) {
			return;
		}
		Contract contract=listContracts.get(0);
		String loanAppId=contract.getLoanAppId().trim();
		//判断流程状态是否是放款，是就继续
		BpmTask task = null;
		 
		//实际上里面只有一个BpmTask对象
		List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(loanAppId);
		for (int m = 0;m<bpmTasks.size();m++){
			task = bpmTasks.get(0);
		}
		if (task==null||!"放款".equals(task.getState())) {
			return;
		}
		
		Map<String, Object> mapLoan=new HashMap<String, Object>();
		mapLoan.put("loanAppId", loanAppId);
		mapLoan.put("state", "3");
		List<Loan2match>listLoan2matchs=loan2matchService.queryList(mapLoan);
		Loan2match loan2match=listLoan2matchs.get(0);
		
		loan2match.setContractStartDate(dtStart);
		Date dtEnd=DateUtils.addMonth(dtStart, loan2match.getLoanPeriod());
		dtEnd=DateUtils.addDay(dtEnd, -1);
		loan2match.setContractEndDate(dtEnd);
		loan2match.setContractAmount(0);
		loan2match.setState("4");
		loan2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		loan2matchService.update(loan2match);
		
		contract.setStartDate(dtStart);
		contract.setEndDate(dtEnd);
		contract.setState("2");
		contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		contractService.update(contract);
		
		Map<String, Object> mapResult=new HashMap<String, Object>();
		mapResult.put("loanAppId", loanAppId);
		mapResult.put("state", "2");
		List<MatchResult> listResults=queryList(mapResult);
		for (MatchResult matchResult : listResults) {
			matchResult.setState("3");
			//设置实际的有效时间
			matchResult.setActualStartDate(dtStart);
			matchResult.setActualEndDate(matchResult.getEndDate());
			
			Map<String, Object> maplend=new HashMap<String, Object>();
			maplend.put("lendAppId", matchResult.getLendAppId());
			List<Lend2match> listLend2matchs=lend2matchService.queryList(maplend);
			if (listLend2matchs.size()==0) {
				return;
			}
			Lend2match lend2match=listLend2matchs.get(0);
			
			if (lend2match.getContractStartDate()==null||lend2match.getContractEndDate()==null) {
				//未签订过合同
				Date dtTmp=workdayService.afterWorkDay(lend2match.getAppTime(), 4);
				
				lend2match.setContractStartDate(dtTmp);
				Date dtlendEnd=DateUtils.addMonth(lend2match.getContractStartDate(), lend2match.getLendPeriod());
				dtlendEnd=DateUtils.addDay(dtlendEnd, -1);
				lend2match.setContractEndDate(dtlendEnd);
				//lend2match.setEndDate(dtlendEnd);
				lend2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				Map<String, Object> mapResults=new HashMap<String, Object>();
				mapResults.put("lendAppId", lend2match.getLendAppId());
				List<MatchResult> listMatchResults=queryList(mapResults);
				//找出最早的有效开始时间
				Date dtValueStart=lend2match.getContractStartDate(); 
				int comm=DateUtils.compareDate(dtValueStart,DateUtils.getDateNow());
				if (comm>0) {
					dtValueStart=DateUtils.getDateNow();
				}
				
				for (MatchResult matchResult2 : listMatchResults) {
					if (matchResult2.getActualStartDate()==null||matchResult2.getActualEndDate()==null) {
						int com2=DateUtils.compareDate(dtValueStart, matchResult2.getStartDate());
						if (com2>0) {//取较大的
							matchResult2.setStartDate(dtValueStart);
							matchResult2.setUpdateTime(new Timestamp(System.currentTimeMillis()));
							update(matchResult2);
						}
					}	
				}
				for (MatchResult matchResult0 : listMatchResults) {
					if (matchResult0.getActualStartDate()!=null||matchResult0.getActualEndDate()!=null) {
							int com0=DateUtils.compareDate(dtValueStart, matchResult0.getStartDate());
							if (com0>0) {
								dtValueStart=matchResult0.getStartDate();
							}
					}
				}
				//记录真实的开始有收益的时间
				lend2match.setStartDate(dtValueStart);
				lend2matchService.update(lend2match);
			}
			matchResult.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			update(matchResult);
			
			
		}
		//结束流程
		//根据传过来的申请号获取流程实例
		
		
		task.setOperator(BpmConstants.SYSTEM_SYS);
		task = processService.goNext(task, "结束", BpmConstants.SYSTEM_SYS);
		log.info(task);
		
	
	}			
	
	/**
	 * （二期）实时撮合完成，签订合同后的处理，lend2Match和loan2match的合同日期和状态
	 * @param ContractNo 合同号
	 *
	 * @throws Exception 
	 */
	@Transactional
	public void handleAfterRealTimeMatch(String ContractNo) throws Exception{
		
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		
		Date dtStart=DateUtils.getDateNow();
		//获取loanAppId
		Map<String, Object> mapCon=new HashMap<String, Object>();
		mapCon.put("contractNo", ContractNo);
		//mapCon.put("state", "1");
		List<Contract>listContracts=contractService.queryList(mapCon);
		if (listContracts.size()==0) {
			return;
		}
		Contract contract=listContracts.get(0);
		String loanAppId=contract.getLoanAppId().trim();
		
		
		Map<String, Object> mapLoan=new HashMap<String, Object>();
		mapLoan.put("loanAppId", loanAppId);
		//mapLoan.put("state", "3");
		List<Loan2match>listLoan2matchs=loan2matchService.queryList(mapLoan);
		Loan2match loan2match=listLoan2matchs.get(0);
		
		loan2match.setContractStartDate(dtStart);
		Date dtEnd=DateUtils.addMonth(dtStart, loan2match.getLoanPeriod());
		dtEnd=DateUtils.addDay(dtEnd, -1);
		loan2match.setContractEndDate(dtEnd);
		loan2match.setContractAmount(0);
		loan2match.setState("4");		//撮合表，改为合同中
		loan2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		loan2matchService.update(loan2match);
		
//		contract.setStartDate(dtStart);
//		contract.setEndDate(dtEnd);
//		contract.setState("2");
//		contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//		contractService.update(contract);
		
		Map<String, Object> mapResult=new HashMap<String, Object>();
		mapResult.put("loanAppId", loanAppId);
		mapResult.put("state", "1");
		List<MatchResult> listResults=queryList(mapResult);
		for (MatchResult matchResult : listResults) {
			matchResult.setState("3");	//撮合成功 直接到达 合同中 状态
			//设置实际的有效时间
			matchResult.setActualStartDate(dtStart);
			matchResult.setActualEndDate(matchResult.getEndDate());
			
			Map<String, Object> maplend=new HashMap<String, Object>();
			maplend.put("lendAppId", matchResult.getLendAppId());
			List<Lend2match> listLend2matchs=lend2matchService.queryList(maplend);
			if (listLend2matchs.size()==0) {
				return;
			}
			Lend2match lend2match=listLend2matchs.get(0);
			
			if (lend2match.getContractStartDate()==null||lend2match.getContractEndDate()==null) {
				//未签订过合同
				Date dtTmp=workdayService.afterWorkDay(lend2match.getAppTime(), 4);
				
				lend2match.setContractStartDate(dtTmp);
				Date dtlendEnd=DateUtils.addMonth(lend2match.getContractStartDate(), lend2match.getLendPeriod());
				dtlendEnd=DateUtils.addDay(dtlendEnd, -1);
				lend2match.setContractEndDate(dtlendEnd);
				//lend2match.setEndDate(dtlendEnd);
				lend2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				Map<String, Object> mapResults=new HashMap<String, Object>();
				mapResults.put("lendAppId", lend2match.getLendAppId());
				List<MatchResult> listMatchResults=queryList(mapResults);
				//找出最早的有效开始时间
				Date dtValueStart=lend2match.getContractStartDate(); 
				int comm=DateUtils.compareDate(dtValueStart,DateUtils.getDateNow());
				if (comm>0) {
					dtValueStart=DateUtils.getDateNow();
				}
				
				for (MatchResult matchResult2 : listMatchResults) {
					if (matchResult2.getActualStartDate()==null||matchResult2.getActualEndDate()==null) {
						int com2=DateUtils.compareDate(dtValueStart, matchResult2.getStartDate());
						if (com2>0) {//取较大的
							matchResult2.setStartDate(dtValueStart);
							matchResult2.setUpdateTime(new Timestamp(System.currentTimeMillis()));
							update(matchResult2);
						}
					}	
				}
				for (MatchResult matchResult0 : listMatchResults) {
					if (matchResult0.getActualStartDate()!=null||matchResult0.getActualEndDate()!=null) {
							int com0=DateUtils.compareDate(dtValueStart, matchResult0.getStartDate());
							if (com0>0) {
								dtValueStart=matchResult0.getStartDate();
							}
					}
				}
				//记录真实的开始有收益的时间
				lend2match.setStartDate(dtValueStart);
				lend2matchService.update(lend2match);
			}
			matchResult.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			update(matchResult);
			
		}
	}	
	
	
	/**
	 * 处理lend出借方的合同状态，到期后及时退还本金
	 * 调用此方法要在handleContract方法之后
	 * @throws Exception 		
	 */
	
	@Transactional
	public void handleLend2Match() throws Exception{
		
		List<Lend2match> listLend2matchs=lend2matchService.queryUnFinishList();
		for (Lend2match lend2match : listLend2matchs) {
			
//			if (MatchUtil.testLend2Match(lend2match)==1) {
//				lend2match.setStartDate(LendGetStartDate(lend2match));
//				Date dt=DateUtils.addMonth(lend2match.getStartDate(),lend2match.getLendPeriod());
//				lend2match.setEndDate(DateUtils.addDay(dt, -1));
//				
//			}
			int com=DateUtils.compareDate(MatchUtil.LendGetEndDate(lend2match), DateUtils.getDateNow());
			if (com<=0) {
				if (!"3".equals(lend2match.getState())) {
					lend2match.setState("3");
					lend2matchService.update(lend2match);
		
					//TODO 判断续投的情况，做处理
					
				}
			}
			
		}
		
		
	}
	
	/**
	 * 提前撤资做的处理
	 * @param lendAppId
	 * @throws Exception 
	 */
	@Transactional
	public void lendDivest(String lendAppId) throws Exception {
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		
		//根据lendappId获取matchResult中的记录，做结束处理
		Map<String, Object> mapResult=new HashMap<String, Object>();
		mapResult.put("lendAppId", lendAppId);
		mapResult.put("state", "3");
		List<MatchResult> listResults=dao.queryList(mapResult);
		
		Map<String, Object> mapResult1=new HashMap<String, Object>();
		mapResult1.put("lendAppId", lendAppId);
		mapResult1.put("state", "2");
		List<MatchResult> listResults1=dao.queryList(mapResult1);
		//合并到一个list中
		for (int i = 0; i < listResults1.size(); i++) {
			listResults.add(listResults1.get(i));
		}
		for (MatchResult matchResult : listResults) {
			matchResult.setEndDate(DateUtils.getDateNow());
			matchResult.setActualEndDate(DateUtils.getDateNow());
			matchResult.setState("5");
			matchResult.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			dao.update(matchResult);
			
			Map<String, Object> mapLoan=new HashMap<String, Object>();
			mapLoan.put("loanAppId",matchResult.getLoanAppId());
			List<Loan2match> listLoan2matchs=loan2matchService.queryList(mapLoan);
			for (Loan2match loan2match : listLoan2matchs) {
				//更新loan2match
				loan2match.setLoanAmount(loan2match.getLoanAmount()+matchResult.getLoanAmount());
				if ("1".equals(loan2match.getType())&&"3".equals(matchResult.getState())) {
					loan2match.setType("2");
				}
				if (loan2match.getLoanAmount()>0) {
					loan2match.setState("1");
				}
				loan2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				loan2matchService.update(loan2match);
			}
		}
		
		//完成lend2match
		
		Map<String, Object> mapLend=new HashMap<String, Object>();
		mapLend.put("lendAppId",lendAppId);
		List<Lend2match> listLend2matchs=lend2matchService.queryList(mapLend);
		for (int i = 0; i < listLend2matchs.size(); i++) {
			listLend2matchs.get(i).setState("3");
			listLend2matchs.get(i).setEndDate(DateUtils.getDateNow());
			listLend2matchs.get(i).setUpdateTime(new Timestamp(System.currentTimeMillis()));
			lend2matchService.update(listLend2matchs.get(i));
		}
		
		//判断流程状态是否是自动撮合，是就继续
		BpmTask task = null;
		 
		//实际上里面只有一个BpmTask对象
		List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(lendAppId);
		for (int m = 0;m<bpmTasks.size();m++){
			task = bpmTasks.get(0);
		}
		if (task!=null&&"自动撮合".equals(task.getState())) {
			task.setOperator(BpmConstants.SYSTEM_SYS);
			task = processService.goNext(task, "交割", BpmConstants.SYSTEM_SYS);		
			task = processService.goNext(task, "结束", BpmConstants.SYSTEM_SYS);
		}		
	}
			
	@Transactional
	public void update(MatchResult t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(MatchResult t)  throws Exception {
		dao.updateOnlyChanged(t);
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
	
	public List<MatchResult> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public MatchResult queryByKey(Object id) throws Exception {
		return (MatchResult)dao.queryByKey(id);
	}
	
	public List<MatchResult> queryByLendAppId(String lendAppId) throws Exception {
		
		return dao.queryByLendAppId(lendAppId);
	}
	
	public List<MatchResult> queryByLoanAppId(String loanAppId) throws Exception {
		return dao.queryByLoanAppId(loanAppId);
	}
	
	//杨长收添加（人工撮合审批）
	public List<MatchResult> queryMatchResultByLoanAppId(Map<String, Object> map) throws Exception {

		return dao.queryList(map);
	}
	
	@Transactional
	public List<MatchResult> getResultByAppId(String loanAppId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loanAppId", loanAppId);
		return dao.getResultByAppId(map);
	}
	
	public List<MatchResult> queryListInSign(Map<String, Object> beanMap) {
		return dao.queryListInSign(beanMap);
	}
	
	public List<MatchResult> queryListByLoanAppId(String creditAppId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("creditAppId", creditAppId);
		map.put("noState", "0");
		return dao.queryListByLoanAppId(map);
	}
	
	
	/**
	 * 实时撮合服务
	 * @param appId
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public synchronized MatchInfo realTimeMatch(String loanAppId) throws Exception{
		/*
		 * 先撮合
		 *   撮合成功后
		 *   t_contract	       当前合同状态 置为 1
		 *   t_credit_app   当前申请状态 置为 14（撮合已完成）
		 *   t_loan_repay_plan 生成还款计划
		 */
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		
		//获取要处理的两个队列
    	MatchInfo matchInfo=new MatchInfo();
    	Map<String, Object> map=new HashMap<String, Object>();
    	List<Lend2match> listLend=lend2matchService.queryLendBasicValueAutoList();
    	map.put("loanAppId", loanAppId);
    	List<Loan2match> listLoan=loan2matchService.queryList(map);
    	if (listLoan==null ||listLoan.size()==0) {
			matchInfo.setFlag(false);
			matchInfo.setRemark("数据问题，待匹配表中无此记录！");
			return matchInfo;
		}
    	//预留资金
//    	Map<String, List<Lend2match>>listMap=new HashMap<String, List<Lend2match>>();
//    	listMap=saveLimitLendAmount(listLend);
//    	List<Lend2match> listSave=listMap.get("save");
//    	if (listSave==null || listSave.size()==0) {
//    		matchInfo.setFlag(false);
//			matchInfo.setRemark("可用债权不足预留资金，匹配不成功");
//			return matchInfo;
//		}
//    	List<Lend2match> listMatch=listMap.get("match");
    	
    	listLend=saveLimitLendAmount(listLend);
    	//判断出去预留资金是否有余额可以进行匹配
    	Double dbTmp=0.00;
    	for (Lend2match lend2match : listLend) {
    		dbTmp+=lend2match.getLendAmount();
		}
    	if (dbTmp<=0) {
    		matchInfo.setFlag(false);
			matchInfo.setRemark("可用债权不足预留资金，匹配不成功");
			return matchInfo;
		}
    	MatchModel matchModel=new MatchModel();
    	matchModel.setListLend(listLend);
    	matchModel.setListLoan(listLoan);
    	//进行匹配
    	matchModel=handleDate(matchModel);
    	matchModel=MatchUtil.matchByAmountAndPeriod(matchModel);
       // matchModel=MatchUtil.matchByPeriod(matchModel);
    	matchModel=MatchUtil.matchByPeriodAndSplitAmount(matchModel);
     	matchModel=MatchUtil.matchByAmountAndSplitPeriod(matchModel);
  	    matchModel=MatchUtil.matchBySplitPeriodAndSplitAmount(matchModel);
  	    if (matchModel.getListMatchModels()==null||matchModel.getListMatchModels().size()==0) {
  	    	matchInfo.setFlag(false);
			matchInfo.setRemark("可用债权金额不足，匹配不成功！");
			return matchInfo;
		}
  	    //补充上预留的资金
//  	    for (int i = 0; i < listSave.size(); i++) {
//			if (listSave.get(i).getLendAppId()==matchModel.getListLend().get(i).getLendAppId()) {
//				matchModel.getListLend().get(i).setLendAmount(listSave.get(i).getLendAmount()+matchModel.getListLend().get(i).getLendAmount());
//			}
//		}
  	    
    	for (int i = 0; i < matchModel.getListMatchModels().size(); i++) {
    		Lend2match lendTmp=matchModel.getListMatchModels().get(i).getLend2match();
    		if (lendTmp.getTmpAmount()>0) {
				lendTmp.setLendAmount(lendTmp.getLendAmount()+lendTmp.getTmpAmount());
				if (lendTmp.getState()=="2") {
					lendTmp.setState("1");
				}
			}
    		lend2matchService.update(lendTmp);
    		loan2matchService.update(matchModel.getListMatchModels().get(i).getLoan2match());
    		//设置匹配表中的实际开始时间，实时撮合的当天
    		MatchResult matchResultTmp=new MatchResult();
    		matchResultTmp=matchModel.getListMatchModels().get(i).getMatchResult();
    		matchResultTmp.setActualStartDate(DateUtils.getDateNow());
    		matchResultTmp.setActualEndDate(matchResultTmp.getEndDate());
    		dao.add(matchModel.getListMatchModels().get(i).getMatchResult());
    	}  	
    	matchInfo.setFlag(true);	
    	return matchInfo;
	}
	/**放弃 签约 撮合以及金额回退
	 * 实时撮合服务
	 * @param appId
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public synchronized boolean giveUpRealTimeMatch(String loanAppId) throws Exception{
		/*
		 * t_loan_2match  当前借款撮合信息 置为无效
		 * t_match_result 撮合结果清单 置为无效
		 * t_lend_2match  根据撮合结果 信息 把金额回退
		 
		 */
		
		List<MatchResult> listMatch= queryByLoanAppId(loanAppId);
	
		this.delMatchResult(listMatch);
		
		return true;
	}
	
	/**
	 * 一次性还清处理
	 * @throws Exception 
	 */
	@Transactional
	public void onetimePayOff(String loanAppId) throws Exception{
		//独占锁两个表
		List<Loan2match>listLockLoan2matchs= loan2matchService.queryLoanBasicValueAutoList();
		List<Lend2match>listLockLend2matchs= lend2matchService.queryLendBasicValueAutoList();
		
		
		Map<String, Object> mapResult=new HashMap<String, Object>();
		mapResult.put("loanAppId", loanAppId);
		mapResult.put("state", "3");
		Date dateNow=DateUtils.getDateNow();
		List<MatchResult> listResults=dao.queryList(mapResult);
		for (MatchResult matchResult : listResults) {
			matchResult.setState("5");
			matchResult.setEndDate(dateNow);
			matchResult.setActualEndDate(dateNow);
			dao.update(matchResult);
			
			Map<String, Object> mapLend=new HashMap<String, Object>();
			mapLend.put("lendAppId",matchResult.getLendAppId());
			List<Lend2match> listLend2matchs=lend2matchService.queryList(mapLend);
			for (Lend2match lend2match : listLend2matchs) {
				int comlend=DateUtils.compareDate(dateNow, lend2match.getContractEndDate());
				if (comlend>=0) {
					if (!"3".equals(lend2match.getState())) {
						lend2match.setState("3");
						lend2matchService.update(lend2match);
					}
					
				}else {
					lend2match.setLendAmount(lend2match.getLendAmount()+matchResult.getLendAmount());
					if ("1".equals(lend2match.getType())) {
						lend2match.setType("2");
					}
					if ("2".equals(lend2match.getState())) {
						lend2match.setState("1");
					}
					
					lend2matchService.update(lend2match);
				}
				
			}
			Map<String, Object> mapLoan=new HashMap<String, Object>();
			mapLoan.put("loanAppId", loanAppId);
			List<Loan2match> listLoan2matchs=loan2matchService.queryList(mapLoan);
			for (Loan2match loan2match : listLoan2matchs) {
				loan2match.setState("5");
				loan2matchService.update(loan2match);
			}
		}
	}
	
	/**
	 * 根据设定投资人每月最少留存的金额，处理可以参与匹配的
	 * 
	 * @param listLend
	 * @return  从返回的map中，判定save的list，如果为空，不进行匹配。
	 */
//	@Transactional
//	public Map<String, List<Lend2match>> saveLimitLendAmount(List<Lend2match> listLend) {
//		//构造返回的map
//		Map<String,List<Lend2match>> listMap=new HashMap<String, List<Lend2match>>();
//		//从数据字典表中获取限定数值
//		Parameter parameter=parameterService.queryByParamName("lendLockAmount");
//		Double dbLimit=Double.valueOf(parameter.getParamValue());
//		//保存的限制lend信息
//		List<Lend2match> listSave=new ArrayList<Lend2match>();
//		//判断总额是否大于限定值
//		Double dbtmp=0.00;
//		for (Lend2match lend2match : listLend) {
//			dbtmp+=lend2match.getLendAmount();
//		}
//		if (dbtmp<=dbLimit) {
//			List<Lend2match> listTmp=new ArrayList<Lend2match>();
//			listMap.put("save", listSave);//为空
//			listMap.put("match", listLend);
//			return listMap;
//		}
//		for (Lend2match lend2match : listLend) {
//			if (dbLimit<lend2match.getLendAmount()) {
//				lend2match.setLendAmount(lend2match.getLendAmount()-dbLimit);
//				Lend2match lendTmp=new Lend2match();
//				lendTmp=lend2match;
//				lendTmp.setLendAmount(dbLimit);
//				listSave.add(lendTmp);
//				break;
//			}
//			dbLimit-=lend2match.getLendAmount();
//			Lend2match lendTmp=new Lend2match();
//			lendTmp=lend2match;
//			listSave.add(lendTmp);
//			lend2match.setLendAmount(0);
//		}
//		listMap.put("save", listSave);
//		listMap.put("match", listLend);
//		return listMap;
//	}
	
	@Transactional
	public List<Lend2match> saveLimitLendAmount(List<Lend2match> listLend) {
		
		//从数据字典表中获取限定数值
		Parameter parameter=parameterService.queryByParamName("lendLockAmount");
		Double dbLimit=Double.valueOf(parameter.getParamValue());
		
		for (Lend2match lend2match : listLend) {
			if (dbLimit<=lend2match.getLendAmount()) {
				lend2match.setLendAmount(lend2match.getLendAmount()-dbLimit);
				lend2match.setTmpAmount(dbLimit);
				break;
			}
			dbLimit-=lend2match.getLendAmount();
			lend2match.setTmpAmount(lend2match.getLendAmount());
			lend2match.setLendAmount(0);
		}
		
		return listLend;
	}
		

	
}
