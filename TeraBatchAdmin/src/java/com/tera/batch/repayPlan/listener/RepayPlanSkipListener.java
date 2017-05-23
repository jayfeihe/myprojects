package com.tera.batch.repayPlan.listener;

import java.sql.Timestamp;

import org.springframework.batch.core.annotation.OnSkipInProcess;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.batch.exception.BatchProcessException;
import com.tera.payment.model.LoanRepayPlan;




public class RepayPlanSkipListener {

	private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;
	
	public RepayPlanSkipListener(BatchErrorLogDao<BatchErrorLog> logDao) {
		this.batchErrorLogDao=logDao;
	}
	
	@OnSkipInProcess
	public void log(LoanRepayPlan loanRepayPlan,BatchProcessException t){
		
		BatchErrorLog errorLog=new BatchErrorLog();
		errorLog.setErrorMsg(t.getMessage());
		errorLog.setJobName("repayPlanJob");
		errorLog.setBizKey(String.valueOf(loanRepayPlan.getContractNo()));
		errorLog.setExt1("记录所在表名t_loan_repay_plan;记录Id为"+String.valueOf(loanRepayPlan.getId()));
		errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
		batchErrorLogDao.add(errorLog);
		
	}
}
