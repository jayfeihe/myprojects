package com.tera;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.batch.service.ClltBatchService;
import com.tera.util.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class ClltTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private IRetPlanService retPlanService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private ClltBatchService clltBatchService;
	
	@Test
	@Rollback(false)
	public void test01() throws Exception{
		// 查找还款计划，更改时间   减2月
		String contractId = "2016cg0229";
		
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("contractId", contractId);
		List<RetPlan> plans = this.retPlanService.queryList(retMap );
		
		for (RetPlan retPlan : plans) {
			retPlan.setRetDate(DateUtils.addDay(retPlan.getRetDate(), -1));
			this.retPlanService.update(retPlan);
		}
		
		// 更改合同日期   减2月
		Contract contract = this.contractService.queryByContractId(contractId);
		contract.setStartDate(DateUtils.addDay(contract.getStartDate(), -1));
		contract.setEndDate(DateUtils.addDay(contract.getEndDate(), -1));
		this.contractService.update(contract);
		
		// 更改申请到期日期    减2月
		LoanBase loanBase = this.loanBaseService.queryByLoanId(contract.getLoanId());
		loanBase.setEndDate(DateUtils.addDay(loanBase.getEndDate(), -1));
		this.loanBaseService.update(loanBase);
		
		System.out.println("======== 执行完毕 ==========");
	}
	
	@Test
	@Rollback(false)
	public void testBatch() throws Exception {
		// 调用批处理
		this.clltBatchService.autoDisCllt();
		
		//this.clltBatchService.autoCreateCheckTask();

		System.out.println("======== 执行完毕 ==========");
	}
	
	@Test
	@Rollback(false)
	public void test02() throws Exception{
		// 查找还款计划，更改时间   减2月
		String contractId = "A00010";
		
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("contractId", contractId);
		List<RetPlan> plans = this.retPlanService.queryList(retMap );
		
		for (RetPlan retPlan : plans) {
			retPlan.setRetDate(DateUtils.addMonth(retPlan.getRetDate(), 2));
			this.retPlanService.update(retPlan);
		}
		
		// 更改合同日期   减2月
		Contract contract = this.contractService.queryByContractId(contractId);
		contract.setStartDate(DateUtils.addMonth(contract.getStartDate(), 2));
		contract.setEndDate(DateUtils.addMonth(contract.getEndDate(), 2));
		this.contractService.update(contract);
		
		// 更改申请到期日期    减2月
		LoanBase loanBase = this.loanBaseService.queryByLoanId(contract.getLoanId());
		loanBase.setEndDate(DateUtils.addMonth(loanBase.getEndDate(), 2));
		this.loanBaseService.update(loanBase);
		
		System.out.println("======== 执行完毕 ==========");
	}
}
