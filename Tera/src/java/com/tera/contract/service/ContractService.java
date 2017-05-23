package com.tera.contract.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.contract.dao.ContractDao;
import com.tera.contract.model.Contract;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.service.LoanRepayPlanService;

/**
 * 
 * 合同表服务类
 * <b>功能：</b>ContractDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-12-09 16:22:06<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("contractService")
public class ContractService {

	private final static Logger log = Logger.getLogger(ContractService.class);

	@Autowired(required=false)
    private ContractDao dao;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;

	@Transactional
	public void add(Contract... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Contract obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(Contract obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(Contract obj)  throws Exception {
		dao.updateOnlyChanged(obj);
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
	
	public List<Contract> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public Contract queryByKey(Object id) throws Exception {
		return dao.queryByKey(id);
	}

	//杨长收添加
	public Contract queryByAppId(Object appId) throws Exception {
		return dao.queryByAppId(appId);
	}
	
	//合同相关
	@Transactional
	public List<Contract> getContractByAppId(String loanAppId,String contractClass,String ext1 ){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loanAppId", loanAppId);
		map.put("contractClass", contractClass);
		map.put("ext1", ext1);
		return dao.getContractByAppId(map);
	}
	
	/**
	 * 根据申请号查询历史借款情况
	 * @param appId
	 * @return
	 */
	public List<Contract> queryHistoryList(String appId){
		return dao.queryHistoryList(appId);
	}
	
	/**
	 * 根据 还款计划查询  列表
	 * @param map
	 * 		mapkey
	 * 			contractNo		合同编码
	 * 			processer 		流程待处理人
	 * 			orgId			机构编码
	 * 			states			包含状态 字符串 数组 
	 * 			nonStates		不包含状态 字符串 数组 
	 * 			name 			姓名
	 * 			idType 			证件号码
	 * 			idNo 			证件号码
	 * @return
	 */
	public List<Contract> queryContractRepayPlanList(Map<String, Object> map)throws Exception{
		//TODO  只有这两个属性有效，其他的暂时无效 [contractNo,orgId]
		return dao.queryContractRepayPlanList(map);
	}
	public int queryContractRepayPlanCount(Map<String, Object> map)throws Exception{
		
		return dao.queryContractRepayPlanCount(map);
	}

	/**
	 * 判断 合同 是否终止 
	 * 根据还款计划 结果判断合同终止
	 * @param contractNo
	 * @throws Exception
	 */
	@Transactional
	public void ifContractEnd(String contractNo) throws Exception{
		Map<String, Object> mapCon=new HashMap<String, Object>();
		mapCon.put("contractNo", contractNo);
		List<Contract>listContracts=dao.queryList(mapCon);
		Contract contract=listContracts.get(0);
		//查询当期还款计划
		List<LoanRepayPlan> listLoanRepayPlans=loanRepayPlanService.queryList(mapCon);
		for (LoanRepayPlan loanRepayPlan : listLoanRepayPlans) {
			double dbAmount=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
			if(dbAmount>0){
				contract.setState("2");
				break;
			}else{
				contract.setState("4");
			}
		}
		dao.updateOnlyChanged(contract);
	}
	
	public String getContractCode(){
		return dao.getContractCode();
	}
	
	/**
	 *  查询拒贷列表(功能区模块的)
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			inputTimeMin	进件时间开始
	 * 			inputTimeMax	进件时间结束
	 *			product 		产品
	 *			orgId  			营业部
	 *			platformName	平台名称(如：积木盒子)
	 * @return
	 */
	public List<Contract> queryCreditRepelLoanList(Map<String, Object> map){
		return dao.queryCreditRepelLoanList(map);
	}
	
	public int queryCreditRepelLoanCount(Map<String, Object> map){
		return dao.queryCreditRepelLoanCount(map);
	}
	
	
	
	/**
	 *  查询拒贷列表(功能区模块的)
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 *			orgId  			营业部
	 * 			contractNo		合同编号
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 *			appStates  		申请状态 数组
	 *			contractStates  合同状态数组
	 * @return
	 */
	public List<Contract> queryManageList(Map<String, Object> map){
		return dao.queryManageList(map);
	}
	
	public int queryManageCount(Map<String, Object> map){
		return dao.queryManageCount(map);
	}
	
	
	
}
