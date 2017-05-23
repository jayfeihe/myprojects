package com.tera.batch.payment.processor;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.match.model.Lend2match;
import com.tera.payment.constant.AllinpayConstant;
import com.tera.payment.service.AllinPayTranxService;



public class PaymentQueryProcessor implements ItemProcessor<Integer, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PaymentQueryProcessor.class);
		
	private AllinPayTranxService allinPayTranxService;
	
	 private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;

	/**
     * 对取到的数据进行简单的处理。
     * 
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    @Override
    public String process(Integer pm) throws Exception {
    	//String URLhttps="https://113.108.182.3/aipg/ProcessServlet"; //有效
    	try {
    		allinPayTranxService.batchQuery(AllinpayConstant.URLhttps);
        	
        	return null;
		} catch (Exception e) {
			BatchErrorLog errorLog=new BatchErrorLog();
			errorLog.setErrorMsg(e.getMessage());
			errorLog.setJobName("paymentQueryJob");
			errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			batchErrorLogDao.add(errorLog);
			throw e;
		}
    	
    }

	public AllinPayTranxService getAllinPayTranxService() {
		return allinPayTranxService;
	}

	public void setAllinPayTranxService(AllinPayTranxService allinPayTranxService) {
		this.allinPayTranxService = allinPayTranxService;
	}

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}

	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}
    
    
    	

}
