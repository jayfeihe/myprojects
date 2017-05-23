package com.tera.loan.service;

import java.sql.Timestamp;
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
import com.tera.loan.constant.LoanConstants;
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
@Service("loanApprovalService")
public class LoanApprovalService {

	private final static Logger log = Logger.getLogger(LoanApprovalService.class);

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
	public void bpmNext(String loginid,String auditText,String auditResult,LoanApproval bean,Org org) throws Exception {
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(bean.getAppId());
		BpmTask task=taskList.get(0);
		//task.setOperator(loginid);
		String zhlx=null,netxUser=null;
		//不通过 退回 风险专员
		if("0".equals(auditResult)){
			zhlx="风险专员初核";
			List<BpmLog> bpmLogs=processService.getProcessHistoryLog(bean.getAppId(), zhlx);
			BpmLog bm= bpmLogs.get(0);
			netxUser=bm.getOperator();
			task.setOperator(loginid);
			task.setVariable("logContent1", "不通过");
			task.setVariable("logContent2", auditText);
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
			
			//提交流程
			task = processService.goNext(task, zhlx, netxUser);
			
			List<LoanApp> sqList=loanAppService.getAppByAppId(bean.getAppId());
			LoanApp sq=sqList.get(0);
			Loan2match loan2Match=new Loan2match();
			loan2Match.setLoanAppId(bean.getAppId());
			loan2Match.setType("1"); // 1 新增  2差额
			loan2Match.setMatchType(bean.getMatchType());
			loan2Match.setAppTime(new Timestamp(System.currentTimeMillis()));
			loan2Match.setLoanAmount(bean.getApprovalAmount());
			loan2Match.setLoanProduct(sq.getProduct());
			loan2Match.setLoanPeriod(bean.getApprovalPeriod());
			loan2Match.setLoanInterestRate(sq.getInterestRate());
			loan2Match.setLoanServiceRate(bean.getApprovalServiceRate());
			loan2Match.setOrgId(sq.getOrgId());
			loan2Match.setUseage(sq.getUseage());
			loan2Match.setState("1");//初始状态 带撮合
			loan2Match.setOrgId2(org.getOrgId());
			loan2matchService.add(loan2Match);
			log.info(loan2Match);
			
		}else if("2".equals(auditResult)){
			zhlx="拒件";
			netxUser=BpmConstants.SYSTEM_SYS;
			task.setOperator(loginid);
			// 跳到 拒件
			task = processService.goNext(task, zhlx, netxUser);
			zhlx="风险专员初核";
			List<BpmLog> bpmLogs=processService.getProcessHistoryLog(bean.getAppId(), zhlx);
			BpmLog bm= bpmLogs.get(0);
			netxUser=bm.getOperator();
			task.setOperator(loginid);
			task.setVariable("logContent1", "拒件");
			task.setVariable("logContent2", auditText);
			task = processService.goNext(task,netxUser);
			// 更新申请状态
			LoanApp loanapp=new LoanApp();
			loanapp.setAppId(bean.getAppId());
			loanapp.setState("2");
			loanAppService.updateAppByAppId(loanapp);
		}else if("3".equals(auditResult)){
			zhlx="特殊件审核";
			String[] rolenames={LoanConstants.ROLE_ZBGJSPR,LoanConstants.ROLE_ZBGJFZR}; 
			List<User> users = userService.getUserByOrgAndRoleAndDepart("86", rolenames,null);
			if (users.size() > 0) {
				User user = users.get(new Random().nextInt(users.size()));
				netxUser=user.getLoginId();
			}
			//跳到 特殊审核。
			task = processService.goNext(task, zhlx, netxUser);
		}
		log.info(task);
	}
	
	/**
	 * 保存审核信息，在一个事务里面 同步更新  请求 类型
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void TransApprovalUp(LoanApproval bean)  throws Exception {
		LoanApproval gbean =this.queryByKey(bean.getAppId());
		if(gbean!=null){
			this.updateOnlyChanged(bean);
		}else{
			this.add(bean);
		}
		if(bean.getMatchType()!=null&&!"".equals(bean.getMatchType())){
			LoanApp loanapp=new LoanApp();
			loanapp.setAppId(bean.getAppId());
			loanapp.setMatchType(bean.getMatchType());
			this.loanAppService.updateAppByAppId(loanapp);
		}
	}

	
}
