package com.tera.house.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.BpmTaskDao;
import com.tera.bpm.model.BpmTask;
import com.tera.house.constant.Constants;
import com.tera.rule.model.common.AssignUserInfoBean;
import com.tera.rule.service.AssignService;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.service.SysLogService;
import com.tera.sys.service.UserService;


@Service("houseAssignService")
public class HouseAssignService {
	
	private final static Logger log = Logger.getLogger(HouseAssignService.class);

	@Autowired(required=false)
	private AssignService assignService;
	@Autowired(required=false)
	private UserService userService;
	@Autowired(required=false)
	private BpmTaskDao bpmTaskDao;

	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 分单修改bpmTask部分（逻辑与执行部分），一次一单。
	 * @param bpmTask
	 */
	@Transactional
	public void setBpmTaskProcesser(BpmTask bpmTask)throws Exception{
		//取到当前单子处理人的信息
		List<AssignUserInfoBean> assignUserInfos = this.getAssignUserInfos(bpmTask);
		if(assignUserInfos!=null && !assignUserInfos.isEmpty()){
			//调用规则进行分单
			boolean ruleState=ruleAssign(bpmTask, assignUserInfos);
			//执行完分单规则后，进行保存
			if(ruleState){
				User sysUser=new User();
				sysUser.setLoginId("sysauto");
				sysUser.setName("系统批处理");
				sysLogService.addSysLog(
						new SysLog("",sysUser , "", 
						bpmTask.getState()+"规则分单", bpmTask.getBizKey(), "规则分单给" + bpmTask.getProcesser())
				    );
				bpmTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				bpmTaskDao.updateBpmTask(bpmTask);
			}else{
				log.info("分单失败，规则没有匹配到 待处理人,bizKey"+bpmTask.getBizKey()+",state:"+bpmTask.getState());
			}
		}
		
	}
	
	/**
	 * 调用规则 分单
	 * @param bpmTase      需要分单的 流程
	 * @param assignInfos  人员信息列表
	 * @return  bpmTase    处理后的 流程 
	 * @throws Exception 
	 */
	public boolean ruleAssign(BpmTask bpmTask,List<AssignUserInfoBean> assignUserInfos) throws Exception{
		
//		AutoAssignRule autoAssignRule = new AutoAssignRule();
//		autoAssignRule.setAppId(bpmTask.getBizKey());
//		autoAssignRule.setOperateUser(bpmTask.getProcesser());
//		String node = bpmTask.getState();
//		
//		if ("审核".equals(node)) {
//			autoAssignRule.setType(AutoAssignRule.VERIFY);
//		} else if ("审批".equals(node)) {
//			autoAssignRule.setType(AutoAssignRule.APPROVAL);
//		}else if("复核".equals(node)){
//			autoAssignRule.setType(AutoAssignRule.REVIEW);
//		}
//		
//		autoAssignRule.setAssignInfo(assignInfos);
		
		//调用分单方法
//		iDroolsJudgeService.ruleJudge(autoAssignRule);
		
		String userid = assignService.lowestAssign(bpmTask.getBizKey(), assignUserInfos, null);
		
		//规则结果
		if( userid!=null && !"".equals(userid) ){
			bpmTask.setProcesser(userid);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 取到当前单子处理人的信息
	 * 查询该bpmTask状态是审核还是审批，并查出所有审核（审批）人员的单子状态
	 * @param map 
	 * 		status		user的状态，自动分单为1
	 * 		state		bpmTask的state
	 * 		assignRole	处理角色，如信用审核专员,信用审批专员
	 * @return
	 */
	public List<AssignUserInfoBean> getAssignUserInfos(BpmTask bpmTask) {
		
		Map<String, String[]> roleNamesMap=new HashMap<String, String[]>();
		roleNamesMap.put(Constants.PROCESS_STATE_VERIFY, new String[]{Constants.ROLE_SHZY});
		roleNamesMap.put(Constants.PROCESS_STATE_APPROVAL, new String[]{Constants.ROLE_SPZY});
		roleNamesMap.put(Constants.PROCESS_STATE_REVIEW, new String[]{Constants.ROLE_FHZY});
		String[] roleNames=roleNamesMap.get(bpmTask.getState());;
		if(roleNames==null){
			log.info("分单查询处理人 当前流程节点不支持自动分单,bizKey"+bpmTask.getBizKey()+",state:"+bpmTask.getState());
			return null;
		}
		String[] states={"1"}; //状态只查询 在职  在岗
		return userService.queryAssignUserInfo(bpmTask.getState(),states, roleNames);
		
	}
}
