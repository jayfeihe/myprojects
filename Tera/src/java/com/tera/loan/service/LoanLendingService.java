package com.tera.loan.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.tera.accounting.dao.AccounttingDao;
import com.tera.accounting.model.Accountting;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.dao.ContractDao;
import com.tera.contract.model.Contract;
import com.tera.loan.dao.LoanAppDao;
import com.tera.loan.model.Lending;
import com.tera.loan.model.LoanApp;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchResult;
import com.tera.payment.dao.PaymentDao;
import com.tera.payment.model.Payment;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;

/**
 * 
 * <br>
 * <b>功能：</b>AccountDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 11:27:41<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanLendingService")
public class LoanLendingService {

	private final static Logger log = Logger.getLogger(LoanLendingService.class);
	@Autowired(required=false) //自动注入，流程服务
	private ProcessService processService;
	
	@Autowired(required=false)
    private UserDao userdao;
	
	@Autowired(required=false)
    private LoanAppDao loanAppDao;
	
	@Autowired(required=false)
	private ContractDao contractDao;
	
	@Autowired(required=false)
	private PaymentDao paymentDao;
	
	@Autowired(required=false)
	private AccounttingDao accounttingDao;
	
	public int queryLendingCount(Map<String, Object> map)throws Exception {
		String loginId=(String) map.get("processer");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("processer");
			}
		}
		return loanAppDao.queryLendingCount(map);
	}
	
	public List<Lending> queryLendingList(Map<String, Object> map) throws Exception {
		String loginId=(String) map.get("processer");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("processer");
			}
		}
		return loanAppDao.queryLendingList(map);
	}

	public void ConfirmLending(ModelMap map) {
		//以下两步是为了向t_payment表中添加一条数据
		//第一步根据合同号，获得合同信息
		String contractNo=map.get("contractNo").toString();
		String loginid=map.get("loginid").toString();
		String orgId=map.get("orgId").toString();
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("contractNo",contractNo);
		Contract contract = (Contract) contractDao.queryList(map2).get(0);
		Double approvalAmount=contract.getLoanAmount();
		
		//向t_payment表中插入的计划金额和实际金额的值
		Double paymentAmount = approvalAmount * 0.98;
		
		//向t_account表中插入的计划金额和实际金额的值
		Double accountAmount = approvalAmount * 0.02;
		
		//第二步取得需要的各个数据
		Payment payment = new Payment();
		payment.setContractNo(contractNo);
		payment.setInOut("2");//2表示“付”
		payment.setSubject("放款");
		payment.setPlanAmount(approvalAmount);
		payment.setActualAmount(paymentAmount);
		payment.setSource("0");
		//期数原先定的是0，后来改为Contract表中的期限是多少就是多少
		//payment.setPeriodNum(0)
		payment.setPeriodNum(contract.getLoanPeriod());
		payment.setSendFlag("0");//0表示未发盘
		payment.setState("1");//1表示准备放款
		//payment.setState("9");//9表示未确认
		payment.setOperator(loginid);
		payment.setOrgId(orgId);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		payment.setCreateTime(ts);
		payment.setUpdateTime(ts);
		
		//第三步，想t_payment表中插入数据
		paymentDao.add(payment);
		
		
		//第四步，向t_account表中插入一条数据
		Accountting accountting = new Accountting();
		accountting.setInOut("1"); //1 收
		accountting.setAccount("风险金");
		accountting.setContractNo(contract.getContractNo());
		accountting.setSubject("业务往来-放款风险金");
		accountting.setActualAmount(accountAmount);
		accountting.setPlanAmount(accountAmount);
		accountting.setSource("0");
		//期数原先定的是0，后来改为Contract表中的期限是多少就是多少
		//accountting.setPeriodNum(0);
		accountting.setPeriodNum(contract.getLoanPeriod());
		accountting.setState("1");
		accountting.setOperator(loginid);
		accountting.setOrgId(orgId);
		accountting.setCreateTime(ts);
		accountting.setUpdateTime(ts);
		
		accounttingDao.add(accountting);
		
	}

	public void bpmNext(String loginid, LoanApp bean) {

		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(bean.getAppId());
		BpmTask task=taskList.get(0);
		
		String zhlx=null,netxUser=null;
		//不通过，打回复核
		zhlx="复核";
		List<BpmLog> bpmLogs=processService.getProcessHistoryLog(bean.getAppId(), zhlx);
		BpmLog bm= bpmLogs.get(0);
		netxUser=bm.getOperator();
		task.setVariable("logContent1", "放款退回");
		task.setVariable("logContent2", "放款退回");	
		task.setOperator(loginid);
		task = processService.goNext(task, zhlx, netxUser);
		log.info(task);
		
	}

}
