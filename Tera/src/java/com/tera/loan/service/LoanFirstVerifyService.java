package com.tera.loan.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.loan.constant.LoanConstants;
import com.tera.loan.dao.LoanAppDao;
import com.tera.loan.model.LoanApp;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 17:38:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanFirstVerifyService")
public class LoanFirstVerifyService<T> {

	private final static Logger log = Logger.getLogger(LoanFirstVerifyService.class);

	@Autowired(required=false)
    //private LoanFirstVerifyDao<T> dao;
	private LoanAppDao<T> dao;
	
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired(required=false) //自动注入
	private LoanAppService loanAppService;
	
	@Autowired(required=false) //自动注入
	private UserService userService;

	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(T t)  throws Exception {
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
	
	public List<T> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}
	
	//风险初审放弃
	@Transactional
	public void bpmNext(BpmTask task,String processState,String nextOperator,LoanApp bean,String sessionLoginId,Org org) throws Exception {
		
		try{
			
			
			//走到放弃节点
			BpmTask task1 = processService.goNext(task, processState, nextOperator);
			
			//走到结束 节点
			BpmTask task2 = processService.goNext(task, "结束", BpmConstants.SYSTEM_SYS);
			log.info(task1);
			log.info(task2);
			
			//调用老毕的放弃方法
			loanAppService.giveupLoanApp(bean.getAppId(), sessionLoginId, org.getOrgId());
			
		}catch(Exception e){
			throw e;
		}
		
	}
	//复议下一步
	public void bpmNextAgain(BpmTask task,String talkAgainReason,String currentLoginUserID, LoanApp loan) {
		//根据流程实例获取日志的该实例的最后一条记录
		BpmLog lastBpmLog = null;
		List<BpmLog> bpmLogList = processService.getProcessHistoryLog(task,LoanConstants.PROCESS_STATE_YYBJLSH);
		if(bpmLogList.size()>0){
			lastBpmLog = bpmLogList.get(0);
		}
		//本次事件的实际操作人，即session里面的用户id
		task.setOperator(currentLoginUserID);
		//哪个营业部经理退回来的还提交给谁
		//取得日志表中的OPERATOR
		User user = userService.getUser(lastBpmLog.getOperator());
		//走到复议申请
		task = processService.goNext(task, "申请复议", currentLoginUserID);
		task.setVariable("logContent1", LoanConstants.PROCESS_STATE_FXZYCH_FY);
		task.setVariable("logContent2", talkAgainReason);
		//走到营业部审批
		task = processService.goNext(task, LoanConstants.PROCESS_STATE_YYBJLSH, user.getLoginId());
		log.info(task);
	}
}
