package com.tera.loan.service;

import java.sql.Timestamp;
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
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.lend.dao.LendAppDao;
import com.tera.lend.model.LendApp;
import com.tera.lend.model.form.Lend2MatchQBean;
import com.tera.lend.service.LendAppService;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.MatchResultService;
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
@Service("manualFeeService")
public class ManualMatchFeeService<T> {

	private final static Logger log = Logger.getLogger(LendAppService.class);

	@Autowired(required=false)
    private LendAppService dao;
	
	@Autowired(required=false)
    private UserService userService;
	
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private Lend2matchService lend2matchService;
	
	@Autowired(required=false) //自动注入
	private MatchResultService matchResultService;
	
	@Autowired(required=false) //自动注入
	private AccounttingService accounttingService;
	
	public int queryBpmManualMatchCount(Map<String, Object> map)throws Exception {
		String loginId=(String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userService.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
		}
		//return dao.queryBpmLendAppCount(map);
		return dao.queryBpmLendAppCount(map);
	}
	
	public List<LendApp> queryBpmManualMatchList(Map<String, Object> map) throws Exception {
		String loginId=(String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userService.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
		}
		//return dao.queryBpmLendAppList(map);
		return dao.queryBpmLendAppList(map);
	}
	
	/**
	 * 人工撮合收费时执行该方法
	 * @param loan2match
	 * @param servicefee
	 * @param loginId
	 * @param orgId 
	 * @throws Exception
	 */
	@Transactional
	public void bpmGoNext(Loan2match loan2match,String servicefee,String loginId,String orgId) throws Exception {
		
		//取得MatchResult中相应loan_app_id，lend_app_id,state="1"的记录数
		Map<String, Object> queryMap = new HashMap();
		queryMap.put("loan2matchId", loan2match.getLoanAppId());
		queryMap.put("matchresultstate", "2");
		List<Lend2MatchQBean> loan2matchList = this.lend2matchService.queryManualMatchVerifyLend2MatchList(queryMap);
		int matchResultNo = loan2matchList.size();
		
		 
		//循环遍历每条记录，将其状态更改为“3”
		for(int i=0;i<matchResultNo;i++){
			//以下是为了取得MatchResult中的相应记录,实际上根据条件只有一条
			Map<String, Object> queryMap1 = new HashMap();
			queryMap1.put("loanAppId", loan2match.getLoanAppId());
			queryMap1.put("lendAppId", loan2matchList.get(i).getAppId());
			queryMap1.put("state", "2");
			List<MatchResult> matchResultList = this.matchResultService.queryMatchResultByLoanAppId(queryMap1);
			
			MatchResult matchResult = matchResultList.get(0);
			matchResult.setState("3");
			matchResultService.update(matchResult);

			if (i == 0) {
				//向Accounting表中添加记录注意只对第一条的计划金额为输入的金额
				Accountting accounting = new Accountting();
				accounting.setInOut("1"); //1 收
				accounting.setAccount("服务费");
				accounting.setContractNo(loan2match.getLoanAppId());
				accounting.setSubject("业务往来-居间服务费");
				accounting.setPlanAmount(Double.valueOf(servicefee));
				accounting.setActualAmount(matchResult.getLoanAmount());
				accounting.setPeriodNum(0);
				accounting.setState("1");
				accounting.setOperator(loginId);
				accounting.setOrgId(orgId);
				Timestamp ts = new Timestamp(System.currentTimeMillis()); 
				accounting.setCreateTime(ts);				
				accounttingService.add(accounting);
			}
		}
		
		//根据申请Id查找出t_bpm_task表中的该条记录				
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(loan2match.getLoanAppId());
		BpmTask task=taskList.get(0);
		
		String netxUser=BpmConstants.SYSTEM_SYS;
		
		//提交流程
		task = processService.goNext(task, "结束", netxUser);
		log.info(task);
	}
	
}
