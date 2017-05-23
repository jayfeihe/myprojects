package com.tera.audit.common.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;
import com.tera.util.StringUtils;

/** 基础服务
 * @author QYANZE
 *
 */
@Service("commonHandlerService")
public class CommonHandlerServiceImpl implements ICommonHandlerService {

	@Autowired
	private UserService userService;
	@Autowired
	private ProcessService processService;
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.ICommonHandlerService#getNextUser(java.lang.String, java.lang.String)
	 */
	@Override
	public String getNextUser(String roleName,String orgId) {
		String processer = null;
		if(orgId.length()>4){//权限只控制到分公司
			orgId=orgId.substring(0, 4);
		}
		List<User> users = userService.getUserByOrgAndRole(orgId,new String[]{roleName},null);
		if (users != null && users.size() > 0) {
			User user = users.get(new Random().nextInt(users.size()));
			processer = user.getLoginId();
		}
		return processer;
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.ICommonHandlerService#getNextUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getNextUser(String roleName,String orgId,String dataOrgId) {
		String processer = null;
		if(orgId.length()>4){//权限只控制到分公司
			orgId=orgId.substring(0, 4);
		}
		if(dataOrgId.length()>4){//权限只控制到分公司
			dataOrgId=dataOrgId.substring(0, 4);
		}
		List<User> users = userService.getUserByOrgAndRole(orgId,new String[]{roleName},new String[]{dataOrgId});
		if (users != null && users.size() > 0) {
			User user = users.get(new Random().nextInt(users.size()));
			processer = user.getLoginId();
		}
		return processer;
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.ICommonHandlerService#getPrevUser(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPrevUser(String bizKey,String bpmState) {
		String prevOperator = null;
		List<BpmLog> historyLog = this.processService.getProcessHistoryLog(bizKey, bpmState);
		if (historyLog != null && historyLog.size() > 0) {
			prevOperator = historyLog.get(0).getOperator();
			if (StringUtils.isNotNullAndEmpty(prevOperator)) {
				return prevOperator;
			} else {
				prevOperator = historyLog.get(0).getProcesser();
			}
		}
		return prevOperator;
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.ICommonHandlerService#workFlow(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void workFlow(String bizKey,String nextState,String nextUser,String operator,String decision,String remark) {
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(CommonConstant.AUDIT_PROCESS_NAME,bizKey);
		
		if (taskList != null && taskList.size() > 0) {
			BpmTask task = taskList.get(0);
			task.setOperator(operator);
			task.setVariable("decision", decision);
			task.setVariable("remark", remark);
			task = processService.goNext(task, nextState, nextUser);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.ICommonHandlerService#getEmailByLoginId(java.lang.String)
	 */
	@Override
	public String getEmailByLoginId(String loginId) {
		User user = this.userService.getUser(loginId);
		String email = user.getEmail();
		return email;
	}
	
	/**
	 * 获取申请编号
	 * @param nowTime
	 * @return
	 */
	@Override
	public synchronized String getLoanId(Timestamp nowTime) {
		String nowDateStr = DateUtils.format(nowTime, "yyyyMMddHHmmsss");
		return nowDateStr;
	}
}
