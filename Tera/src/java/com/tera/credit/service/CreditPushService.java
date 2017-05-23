package com.tera.credit.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.constant.Constants;
import com.tera.credit.dao.CreditAppDao;
import com.tera.credit.model.CreditApp;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.service.MybatisBaseService;

/**
 * 信审推送Service
 * @author QYANZE
 *
 */
@Service("creditPushService")
public class CreditPushService extends MybatisBaseService<CreditApp>{

	@Autowired(required=false)
	private ProcessService processService;
	@Autowired(required=false)
	private ContractService contractService;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	
	/**
	 * 分页查询
	 * @param params
	 * @return PageList
	 */
	public PageList<CreditApp> queryPageList(Map<String, Object> params) {	
		return this.selectPageList(CreditAppDao.class, "queryBpmAppAndContractList", params);
	}
	
	@Transactional
	public JsonMsg pushManage(Contract contract, String loginId, Org org) throws Exception {
		
		JsonMsg json = new JsonMsg(true, "成功");
		
		if ("1".equals(contract.getOperateResult())) {
			// 先默认调用成功
			contract.setChannelState("1");
			contract.setState("1");
			
			// 推送接口调用
			this.callPushInterface();
			
			// TODO 根据接口返回信息进行更新状态
			
		}
		
		// 放弃
		if ("0".equals(contract.getOperateResult())) {
			contract.setChannelState("0");
			contract.setState("0");
		}
		
		// 保存更新数据
		this.saveOrUpdate(contract, loginId, org);
		
		// 流程处理
		this.workFlowProcess(contract, loginId, org);
		
		return json;
	}
	
	/**
	 * 推送接口调用
	 */
	private void callPushInterface() {
		// TODO 接口调用
		
	}

	/**
	 * 保存数据
	 * @param contract
	 * @param loginId
	 * @param org
	 * @throws Exception 
	 */
	@Transactional
	private void saveOrUpdate(Contract contract, String loginId, Org org) throws Exception {
		double loanAmount = contract.getLoanAmount(); 
		// 取整百
		if (loanAmount % 100 > 0) {
			int tmpAmount = ((int) ((loanAmount + 100) / 100)) * 100;
			contract.setLoanAmount(tmpAmount);
		}
		contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		contract.setOrgId(org.getOrgId());
		contract.setOperator(loginId);
		if (contract.getId() == 0) {
			contract.setCreateTime(new Timestamp(System.currentTimeMillis()));
			this.contractService.add(contract);
		} else {
			this.contractService.updateOnlyChanged(contract);
		}
	}
	
	/**
	 * 流程处理
	 * @param contract
	 * @param loginId
	 * @param org
	 * @throws Exception 
	 */
	@Transactional
	private void workFlowProcess(Contract contract, String loginId, Org org) throws Exception {
		
		List<BpmTask> bpmTasks = processService.getProcessInstanceByBizKey(contract.getLoanAppId());
		
		if (bpmTasks != null && bpmTasks.size() > 0) {
			BpmTask currentTask = bpmTasks.get(0);
			currentTask.setOperator(loginId);
			// 推送
			if ("1".equals(contract.getOperateResult())) {
				currentTask.setVariable("logContent1", "推送至"+contract.getAppChannelName());
				processService.goNext(currentTask, Constants.PROCESS_END_APP, BpmConstants.SYSTEM_SYS);
				
				// 更新creditApp
				this.updateCreditApp(contract.getLoanAppId(),"23",loginId);
			}
			
			// 放弃
			if ("0".equals(contract.getOperateResult())) {
				processService.goNext(currentTask, Constants.PROCESS_STATE_GIVEUP, BpmConstants.SYSTEM_SYS);
				currentTask.setVariable("logContent1", contract.getChannelRemark());
				processService.goNext(currentTask, Constants.PROCESS_END_APP, BpmConstants.SYSTEM_SYS);
				
				// 更新creditApp
				this.updateCreditApp(contract.getLoanAppId(),"0",loginId);
			}
		}
	}
	
	/**
	 * 更新creditApp
	 * @param loanAppId
	 * @param state
	 * @param loginId
	 * @throws Exception
	 */
	private void updateCreditApp(String loanAppId,String state,String loginId) throws Exception {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("appId", loanAppId);
		List<CreditApp> apps = this.creditAppService.queryList(queryMap);
		CreditApp creditApp = null;
		if (apps != null && apps.size() > 0) {
			creditApp = new CreditApp();
			creditApp.setId(apps.get(0).getId());
			creditApp.setState(state);
			creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			this.creditAppService.updateOnlyChanged(creditApp);
		}
	}
}
