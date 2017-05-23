package com.tera.loan.service;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.loan.constant.LoanConstants;
import com.tera.loan.dao.LoanAppDao;
import com.tera.loan.model.LoanApp;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>DepartVerifyDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 18:02:06<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("departVerifyService")
public class LoanDepartVerifyService<T> {

	private final static Logger log = Logger.getLogger(LoanDepartVerifyService.class);

	@Autowired(required=false)
	private LoanAppDao<LoanApp> dao;
	@Autowired(required=false)
	private UserService userService;
	
	@Autowired(required=false)
	private ProcessService processService;

	//根据ID查询借款端申请表
	public LoanApp queryByKey(String id) throws Exception {
		return (LoanApp)dao.queryByKey(id);
	}
	//下一步流程
	public void bpmNext(String currentLoginUserID, String auditText,
			String auditResult, LoanApp bean) {
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(bean.getAppId());
		BpmTask task=taskList.get(0);
		
		String zhlx=null,lastOrNextUser=null;

		//不通过 退回 风险专员或直接结束
		if("0".equals(auditResult)){
			
			//普通不通过，打回风险专员初核
			zhlx="风险专员初核";
			List<BpmLog> bpmLogs=processService.getProcessHistoryLog(bean.getAppId(), zhlx);
			BpmLog bm= bpmLogs.get(0);
			lastOrNextUser=bm.getOperator();
			task.setVariable("logContent1", "不通过");
			task.setVariable("logContent2", auditText);	
			task.setOperator(currentLoginUserID);
			task = processService.goNext(task, zhlx, lastOrNextUser);
			
		}else if("1".equals(auditResult)){
			//通过提交总部审核
			zhlx="总部审核";
			List<User> users = userService.getUserByOrgAndRoleAndDepart("86", new String[]{LoanConstants.ROLE_ZBSPR,LoanConstants.ROLE_ZBFZR},null); 
			if (users.size() > 0) {
					User user = users.get(new Random().nextInt(users.size()));
					lastOrNextUser=user.getLoginId();
				}
			task.setOperator(currentLoginUserID);
			task = processService.goNext(task, zhlx, lastOrNextUser);
//			//修改loanApp表里的state=1
//			bean.setState("1");
//		dao.updateOnlyChanged(bean);
		}else if ("2".equals(auditResult)) {
			//复议不通过，直接结束
				zhlx="结束";
				lastOrNextUser=BpmConstants.SYSTEM_SYS;
				task.setVariable("logContent1", "复议不通过");
				task.setVariable("logContent2", auditText);	
				task.setOperator(currentLoginUserID);
				task = processService.goNext(task, zhlx, lastOrNextUser);
				
		}
			
		log.info(task);
	}
	
}
	

	

