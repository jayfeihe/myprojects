package com.tera.lend.service;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.lend.model.LendApp;
import com.tera.loan.constant.LoanConstants;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchModel;
import com.tera.match.service.Loan2matchService;
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
@Service("manualMatchService")
public class ManualMatchService<T> {

	private final static Logger log = Logger.getLogger(LendAppService.class);

	@Autowired(required=false)
    private LendAppService lendAppService;

	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired(required=false) //自动注入
	private MatchResultService matchResultService;
	
	@Autowired(required=false) //自动注入
	private Loan2matchService loan2matchService;
	
	@Autowired(required=false) //自动注入
	private UserService userService;

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
		return lendAppService.queryBpmLendAppCount(map);
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
		return lendAppService.queryBpmLendAppList(map);
	}
	
	@Transactional
	public void bpmNext(MatchModel matchModel ,String loginId) throws Exception {
		
		//调用匹配算法
		matchResultService.addArtificialMatch(matchModel, loginId);
		
		for(int i=0;i<matchModel.getListLoan().size();i++){
			//根据matchModel中借款人列表中的借款申请Id重新取得该loan2match申请，看它的state是否为“2”，
			//同时看该借款申请在t_bpm_task表中是否处于"人工撮合"状态，如果state既为 “2”，同时又处于”人工撮合“状态
			//则将t_bpm_task表中的该申请的状态置位”人工撮合审批“状态，并随机提交给一个”人工撮合审核员“
			
			//1.重新取得loan2match申请
			Loan2match loan2match = (Loan2match) loan2matchService.queryByKey(matchModel.getListLoan().get(i).getId());
			
			//2.根据申请Id查找出t_bpm_task表中的该条记录				
			List<BpmTask> taskList = processService.getProcessInstanceByBizKey(loan2match.getLoanAppId());
			BpmTask task=taskList.get(0);
			
			//3.判断loan2match的State是否为”2“，同时task的State是否为”人工撮合“
			if("2".equals(loan2match.getState()) && "人工撮合".equals(task.getState())){
				//如果上面的两个条件均成立，则将该流程的状态置为”人工撮合审批“，同时随机提交给一个人工撮合审核员
				List<User> users = userService.getUserByOrgAndRoleAndDepart("86",new String[]{"人工撮合审批人"},null);
				if (users.size() > 0) {
					User user = users.get(new Random().nextInt(users.size()));
					String netxUser=user.getLoginId();
					
					//提交流程
					task = processService.goNext(task, LoanConstants.PROCESS_STATE_RGCHSP, netxUser);
					log.info(task);
				}
			}
		}
	}
}
