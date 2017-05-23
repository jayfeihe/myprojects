package com.tera.batch.jimu.processor;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.contract.model.Contract;
import com.tera.cooperation.jmbox.service.JmboxService;



public class JMProjectQueryProcessor implements ItemProcessor<Contract, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(JMProjectQueryProcessor.class);
		
	private JmboxService jmboxService;

	private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;

	/**
	 * 積木盒子 查詢 
	 */
    @Override
    public String process(Contract contract) throws Exception {
    	try {
    		try {
    			jmboxService.reqQueryJmBox(contract);
			} catch (Exception e) {
				log.error("积木盒子项目查询批处理异常:contract_NO:"+contract.getContractNo(), e);
			}
			return null;
		} catch (Exception e) {
			BatchErrorLog errorLog=new BatchErrorLog();
			errorLog.setErrorMsg(e.getMessage());
			errorLog.setJobName("JMPaymentQueryJob");
			errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			batchErrorLogDao.add(errorLog);
			throw e;
		}
    	
    }

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}
	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}
	public JmboxService getJmboxService() {
		return jmboxService;
	}
	public void setJmboxService(JmboxService jmboxService) {
		this.jmboxService = jmboxService;
	}
    
}
