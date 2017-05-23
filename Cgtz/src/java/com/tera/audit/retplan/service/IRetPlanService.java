package com.tera.audit.retplan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.account.model.BillInfo;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.retplan.dao.RetPlanDao;
import com.tera.audit.retplan.model.RetPlan;

/**
 * 
 * 还款计划表服务类
 * <b>功能：</b>RetPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 12:02:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IRetPlanService {

	
	public void add(RetPlan... objs)  throws Exception;
	

	public void update(RetPlan obj)  throws Exception ;

	public void updateOnlyChanged(RetPlan obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<RetPlan> queryList(Map<String, Object> map) throws Exception ;

	public RetPlan queryByKey(Object id) throws Exception ;
	/**
	 * 根据合同号生成还款计划
	 * 调用之前，保证合同信息完整，合同号，开始和结束时间
	 * @param contractId
	 * @throws Exception 
	 */
	public void createRetPlan(String contractId) throws Exception;
	/**
	 * 收利息操作
	 * @param contractId
	 * @param amt 利息以及第一期的其他费用金额
	 * @param retnum    还款期数
	 * @param defaultfee  罚息
	 * @param penaltyfee  滞纳金
	 * @param storfee   仓储费
	 * @param otherfee  其他费用
	 * @param payDate  还款日期
	 * @throws Exception
	 */
	public void repaymentInt(BillInfo info)throws Exception;
	/**
	 * 收取本金计算
	 * @param contractId 线下合同id
	 * @param amt  还款本金金额
	 * @param defaultfee 罚息金额
	 * @param penaltyfee滞纳金违约金金额
	 * @param otherfee  其他费用
	 * @param payDate  还款日期
	 * @throws Exception
	 */
	
	public void repaymentPrincipal(BillInfo info) throws Exception ;
	


}
