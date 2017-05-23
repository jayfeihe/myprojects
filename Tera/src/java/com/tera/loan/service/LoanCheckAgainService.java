package com.tera.loan.service;

import java.sql.Timestamp;
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
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.loan.dao.LoanApprovalDao;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanApproval;
import com.tera.match.model.Loan2match;
import com.tera.match.service.Loan2matchService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>LoanApprovalDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-09 15:36:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanCheckAgainService")
public class LoanCheckAgainService {

	private final static Logger log = Logger.getLogger(LoanCheckAgainService.class);

	@Autowired(required=false)
    private LoanApprovalDao dao;

	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired(required=false) //自动注入
	private UserService userService;
	
	@Autowired(required=false) //自动注入
	private LoanAppService<LoanApp> loanAppService;
	@Autowired(required=false) //自动注入
	private Loan2matchService<Loan2match> loan2matchService;
	
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;
	
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	
	@Autowired(required=false) //自动注入
	private AccounttingService<Accountting> accounttingService;
	
	
	
	
	@Transactional
	public void add(LoanApproval... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(LoanApproval t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(LoanApproval t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(LoanApproval t)  throws Exception {
		dao.updateOnlyChanged(t);
	}
	
	@Transactional
	public void delete(String... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(String id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<LoanApproval> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public LoanApproval queryByKey(String appId) throws Exception {
		return dao.queryByKey(appId);
	}
	@Transactional
	//public void bpmNext(String loginid,String auditText,String denyToRole,String auditResult,LoanApproval bean,Org org,String approvalAmount) throws Exception {
	public void bpmNext(String loginid,String auditText,String denyToRole,String auditResult,LoanApproval bean,Org org) throws Exception {
	
		
		//调试时使用，无意义所以注释掉
		/*String xx = auditResult;
		if("1".equals(auditResult)){
			System.out.println("yes");
		}*/
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(bean.getAppId());
		BpmTask task=taskList.get(0);
		String zhlx=null,netxUser=null;
		//不通过 退回 风险专员或者是营业部经理
		if("0".equals(auditResult)){
			if("0".equals(denyToRole)){
				zhlx="风险专员初核";
			}else{
				zhlx = "营业部经理审核";
			}
			
			List<BpmLog> bpmLogs=processService.getProcessHistoryLog(bean.getAppId(), zhlx);
			BpmLog bm= bpmLogs.get(0);
			netxUser=bm.getOperator();
			task.setVariable("logContent1", "不通过");
			task.setVariable("logContent2", auditText);
			task.setOperator(loginid);
			task = processService.goNext(task, zhlx, netxUser);
			
			//退回的时候将t_loan_app中的对应的appId的state的状态改成"3"
			List<LoanApp> loanAppList = loanAppService.getAppByAppId(task.getBizKey());
			LoanApp loanApp = loanAppList.get(0);
			loanApp.setState("3");
			//loanAppService.updateAppByAppId(loanApp);
			loanAppService.updateOnlyChanged(loanApp);
			
			//将loan_2match表中的对应的appId的state变成2
			Loan2match newLoan2match = loan2matchService.queryBasicByKey(bean.getAppId());
			newLoan2match.setState("2");
			loan2matchService.updateOnlyChanged(newLoan2match);
			
			
			
		}else if("1".equals(auditResult)){
			List<User> users = userService.getUserByOrgAndRoleAndDepart("86",new String[]{"放款专员"},null);
			if (users.size() > 0) {
				User user = users.get(new Random().nextInt(users.size()));
				netxUser=user.getLoginId();
			}else{
				netxUser=BpmConstants.SYSTEM_SYS; 
			}
			task.setOperator(loginid);
			task = processService.goNext(task, "放款", netxUser);
			
			/*注释开始
			 *注释说明：注释掉这部分代码是因为原先定下的在复核时插入t_payment表和t_accountting表改为在放款操作时执行）
			 * 
			//以下两步是为了向t_payment表中添加一条数据
			//第一步根据申请号查出Contract，目的是为了获得合同号
			Contract contract = contractService.queryByAppId(bean.getAppId());
			
			//向t_payment表中插入的计划金额和实际金额的值
			Double paymentAmount = Double.valueOf(approvalAmount) * 0.98;
			
			//向t_account表中插入的计划金额和实际金额的值
			Double accountAmount = Double.valueOf(approvalAmount) * 0.02;
			
			//第二步取得需要的各个数据
			Payment payment = new Payment();
			payment.setContractNo(contract.getContractNo());
			payment.setInOut("2");//2表示“付”
			payment.setSubject("放款");
			payment.setPlanAmount(Double.valueOf(approvalAmount));
			payment.setActualAmount(paymentAmount);
			payment.setSource("0");
			//期数原先定的是0，后来改为Contract表中的期限是多少就是多少
			//payment.setPeriodNum(0)
			payment.setPeriodNum(contract.getLoanPeriod());
			payment.setSendFlag("0");//0表示未发盘
			//payment.setState("1");//1表示准备放款
			payment.setState("9");//9表示未确认
			payment.setOperator(loginid);
			payment.setOrgId(org.getOrgId());
			
			Timestamp ts = new Timestamp(System.currentTimeMillis()); 
			payment.setCreateTime(ts);
			payment.setUpdateTime(ts);
			
			//第三步，想t_payment表中插入数据
			paymentService.add(payment);
			
			
			//第四步，向t_account表中插入一条数据
			Accountting accountting = new Accountting();
			accountting.setInOut("1"); //1 收
			accountting.setAccount1("资金账户");
			accountting.setAccount2(contract.getContractNo());
			accountting.setSubject("风险金");
			accountting.setActualAmount(accountAmount);
			accountting.setPlanAmount(accountAmount);
			accountting.setSource("0");
			//期数原先定的是0，后来改为Contract表中的期限是多少就是多少
			//accountting.setPeriodNum(0);
			accountting.setPeriodNum(contract.getLoanPeriod());
			accountting.setState("1");
			accountting.setOperator(loginid);
			accountting.setOrgId(org.getOrgId());
			accountting.setCreateTime(ts);
			accountting.setUpdateTime(ts);
			
			accounttingService.add(accountting);
			
			*注释结束
			*/
			
			
		}
		
		
		log.info(task);
	}
}
