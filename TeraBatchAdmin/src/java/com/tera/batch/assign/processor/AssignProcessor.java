package com.tera.batch.assign.processor;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.bpm.model.BpmTask;
import com.tera.contract.model.Contract;
import com.tera.cooperation.jmbox.service.JmboxService;
import com.tera.credit.service.CreditAssignService;



public class AssignProcessor implements ItemProcessor<BpmTask, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(AssignProcessor.class);

	private CreditAssignService creditAssignService;
	private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;
	
	/**
	 * 规则分单
	 */
    @Override
    public String process(BpmTask bpmTask) throws Exception {
    	try {
    		creditAssignService.setBpmTaskProcesser(bpmTask);
		} catch (Exception e) {
			BatchErrorLog errorLog=new BatchErrorLog();
			errorLog.setErrorMsg(e.getMessage());
			errorLog.setJobName("JMPaymentQueryJob");
			errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			batchErrorLogDao.add(errorLog);
			throw e;
		}
    	return "";
    }

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}
	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}

	public CreditAssignService getCreditAssignService() {
		return creditAssignService;
	}

	public void setCreditAssignService(CreditAssignService creditAssignService) {
		this.creditAssignService = creditAssignService;
	}

	public static Logger getLog() {
		return log;
	}
	
    
}
