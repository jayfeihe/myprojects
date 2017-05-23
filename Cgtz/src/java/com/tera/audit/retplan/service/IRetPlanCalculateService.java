package com.tera.audit.retplan.service;

import java.util.List;

import com.tera.audit.retplan.model.RetPlan;

public interface IRetPlanCalculateService {

	/**
	 * 根据申请编号和合同开始日期计算还款计划列表
	 * 
	 * @param loanId 申请编号
	 * @param startDate 合同开始日期
	 * @throws Exception
	 */
	List<RetPlan> createRetPlan(String loanId, String startDate) throws Exception;

}