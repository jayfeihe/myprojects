package com.tera.loan.service;

import java.util.HashMap;
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
import com.tera.lend.model.LendApp;
import com.tera.lend.model.form.Lend2MatchQBean;
import com.tera.lend.service.LendAppService;
import com.tera.loan.constant.LoanConstants;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.MatchResultService;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>LendAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 11:08:58<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("manualMatchVerifyService")
public class ManualMatchVerifyService<T> {

	private final static Logger log = Logger.getLogger(LendAppService.class);

	@Autowired(required=false)
    private LendAppService lendAppService;
	
	@Autowired(required=false)
    private UserDao userdao;
	
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired(required=false) //自动注入
	private UserService userService;
	
	@Autowired(required=false) //自动注入
	private Lend2matchService lend2matchService;
	
	@Autowired(required=false) //自动注入
	private MatchResultService matchResultService;


	public int queryBpmManualMatchCount(Map<String, Object> map)throws Exception {
		String loginId=(String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
		}
		//return dao.queryBpmLendAppCount(map);
		return lendAppService.queryBpmLendAppCount(map);
	}
	
	public List<LendApp> queryBpmManualMatchList(Map<String, Object> map) throws Exception {
		String loginId=(String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
		}
		//return dao.queryBpmLendAppList(map);
		return lendAppService.queryBpmLendAppList(map);
	}
	
	/**
	 * 人工撮合审批通过时执行该方法
	 * @param loan2match
	 * @throws Exception
	 */
	@Transactional
	public void bpmGoNext(Loan2match loan2match) throws Exception {
		
		//取得MatchResult中相应loan_app_id，lend_app_id,state="1"的记录数
		Map<String, Object> queryMap = new HashMap();
		queryMap.put("loan2matchId", loan2match.getLoanAppId());
		queryMap.put("matchresultstate", "1");
		List<Lend2MatchQBean> lend2matchList = this.lend2matchService.queryManualMatchVerifyLend2MatchList(queryMap);
		int matchResultNo = lend2matchList.size();
		
		//循环遍历每条记录，将其状态更改为“2”
		for(int i=0;i<matchResultNo;i++){
			//以下是为了取得MatchResult中的相应记录，实际上根据条件只有一条
			Map<String, Object> queryMap1 = new HashMap();
			queryMap1.put("loanAppId", loan2match.getLoanAppId());
			queryMap1.put("lendAppId", lend2matchList.get(i).getAppId());
			queryMap1.put("state", "1");
			List<MatchResult> matchResultList = this.matchResultService.queryMatchResultByLoanAppId(queryMap1);
			
			MatchResult matchResult = matchResultList.get(0);
			matchResult.setState("2");
			matchResultService.update(matchResult);
			
			List<BpmTask> lendTaskList = processService.getProcessInstanceByBizKey(lend2matchList.get(i).getAppId());
			BpmTask lendTask=lendTaskList.get(0);
			if(lendTask.getState().equals("人工撮合")){
				lendTask = processService.goNext(lendTask,"人工撮合审批", BpmConstants.SYSTEM_SYS);
				lendTask = processService.goNext(lendTask,"交割", BpmConstants.SYSTEM_SYS);
				lendTask = processService.goNext(lendTask,"结束", BpmConstants.SYSTEM_SYS);
			}
			log.info(lendTask);
			
		}
		
		
		//根据申请Id查找出t_bpm_task表中的该条记录				
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(loan2match.getLoanAppId());
		BpmTask task=taskList.get(0);
		
		//如果上面的两个条件均成立，则将该流程的状态置为”人工撮合审批“，同时随机提交给一个人工撮合审核员
		List<User> users = userService.getUserByOrgAndRoleAndDepart("86",new String[]{"人工撮合收费专员 "},null);
		String netxUser="";
		if (users.size() > 0) {
			User user = users.get(new Random().nextInt(users.size()));
		    netxUser=user.getLoginId();
		}else{
			netxUser = BpmConstants.SYSTEM_SYS;
		}
		//提交流程
		task = processService.goNext(task, LoanConstants.PROCESS_STATE_RGCHSF, netxUser);
		log.info(task);
	}
	
	/**
	 * 人工撮合审批不通过时执行该方法
	 * @param loan2match
	 * @throws Exception
	 */
	@Transactional
	public void denyManualMatchVerify(String loan2matchId,String denyReason) throws Exception {
		try{
			//根据申请Id查找出t_bpm_task表中的该条记录				
			List<BpmTask> taskList = processService.getProcessInstanceByBizKey(loan2matchId);
			BpmTask task=taskList.get(0);
			
			//根据流程实例获取日志的该实例的最后一条记录
			BpmLog lastBpmLog = null;
			
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(task, "人工撮合");
			if(bpmLogList.size()>0){
				lastBpmLog = bpmLogList.get(0);
				//lastBpmLog = bpmLogList.get(1);
			}
			
			task.setVariable("logContent1", LoanConstants.PROCESS_STATE_FXZYCH_BTG);
			task.setVariable("logContent2", denyReason);
			
			//谁提交过来的退给谁
			//取得日志表中的OPERATOR
			User user = userService.getUser(lastBpmLog.getOperator());
			task = processService.goNext(task, LoanConstants.PROCESS_STATE_RGCH, user.getLoginId());
			
			//执行回退方法(调用)
			matchResultService.giveupApp(loan2matchId);
			log.info(task);
		}catch (Exception e) {
			throw(e);
		}
		
	}
}
