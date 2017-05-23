package com.tera.loan.service;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.loan.dao.LoanApprovalDao;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanApproval;
import com.tera.match.model.Loan2match;
import com.tera.match.service.Loan2matchService;
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
@Service("loanSpecialService")
public class LoanSpecialService {

	private final static Logger log = Logger.getLogger(LoanSpecialService.class);

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
	public void bpmNext(String loginid,String auditText,String denyToRole,String auditResult,LoanApproval bean,Org org) throws Exception {
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
			
		}else if("1".equals(auditResult)){
			zhlx="0".equals(bean.getMatchType())?"自动撮合":"人工撮合";
			if(!"0".equals(bean.getMatchType())){
				List<User> users = userService.getUserByOrgAndRoleAndDepart("86",new String[]{"人工撮合专员"},null);
				if (users.size() > 0) {
					User user = users.get(new Random().nextInt(users.size()));
					netxUser=user.getLoginId();
				}
			}else{
				netxUser=BpmConstants.SYSTEM_SYS;
			}
			task.setOperator(loginid);
			//提交流程
			task = processService.goNext(task, zhlx, netxUser);
			
			List<LoanApp> sqList=loanAppService.getAppByAppId(bean.getAppId());
			LoanApp sq=sqList.get(0);
			Loan2match l2=new Loan2match();
			l2.setLoanAppId(bean.getAppId());
			l2.setType("1"); // 1 新增  2差额
			l2.setMatchType(bean.getMatchType());
			l2.setAppTime(sq.getCreateTime());
			l2.setLoanAmount(bean.getApprovalAmount());
			l2.setLoanProduct(sq.getProduct());
			l2.setLoanPeriod(bean.getApprovalPeriod());
			l2.setLoanInterestRate(sq.getInterestRate());
			l2.setLoanServiceRate(bean.getApprovalServiceRate());
			l2.setOrgId(sq.getOrgId());
			l2.setUseage(sq.getUseage());
			l2.setState("1");//初始状态 带撮合
			l2.setOrgId2(org.getOrgId());
			loan2matchService.add(l2);
			log.info(l2);
			
		}else if("2".equals(auditResult)){
			zhlx="拒件";
			netxUser=BpmConstants.SYSTEM_SYS;
			
			task.setOperator(loginid);
			// 跳到 拒件
			task = processService.goNext(task, zhlx, netxUser);
			task.setVariable("logContent1", "拒件");
			task.setVariable("logContent2", auditText);
			zhlx="风险专员初核";
			List<BpmLog> bpmLogs=processService.getProcessHistoryLog(bean.getAppId(), zhlx);
			BpmLog bm= bpmLogs.get(0);
			netxUser=bm.getOperator();
			task = processService.goNext(task,netxUser);
			// 更新申请状态
			LoanApp loanapp=new LoanApp();
			loanapp.setAppId(bean.getAppId());
			loanapp.setState("2");
			loanAppService.updateAppByAppId(loanapp);
		}
		log.info(task);
	}
}
