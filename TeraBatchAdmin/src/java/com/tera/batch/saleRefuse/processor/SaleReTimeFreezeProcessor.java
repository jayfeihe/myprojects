package com.tera.batch.saleRefuse.processor;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.batch.exception.BatchProcessException;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditDecisionService;
import com.tera.util.DateUtils;

public class SaleReTimeFreezeProcessor implements ItemProcessor<CreditApp, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(SaleReTimeFreezeProcessor.class);
	
	private CreditDecisionService creditDecisionService;
	
	private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;
	
	/**
     * 对取到的超时单子进行简单的处理。
     * 
     * @param student 处理前的数据。
     * @return 处理后的数据。
     * @exception Exception 处理时发生的任何异常。
     */
    @Override
    public String process(CreditApp creditApp) throws Exception {
    	try {
    		String loginId = "";
    		String appState = creditApp.getState();
    		int retentionDay = DateUtils.getDayRange(creditApp.getUpdateTime(), new Date());
    		//批处理当天24点之前跑，为8天与31天。过了24点为新的一天，为9和32。
    		if("26".equals(appState)||"13".equals(appState)){
	    		if("26".equals(appState) && retentionDay>=7){
	    			creditApp.setSaleRefuseCode1("X02");
	    			creditApp.setSaleRefuseCode2("X0207");
	    			creditApp.setSaleRefuseDescribe("此单超时，系统自动冻结");
	    			creditDecisionService.saleRefuse(creditApp, loginId);
	    		}else if("13".equals(appState) && retentionDay>=30){
    				creditApp.setSaleRefuseCode1("X02");
        			creditApp.setSaleRefuseCode2("X0206");
        			creditApp.setSaleRefuseDescribe("此单超时，系统自动冻结");
        			creditDecisionService.saleRefuse(creditApp, loginId);
	    		}
    		}
    		
    		
    	} catch (Exception e) {
    		throw new BatchProcessException(e);
		}
    	return null;
    }

	public CreditDecisionService getCreditDecisionService() {
		return creditDecisionService;
	}

	public void setCreditDecisionService(CreditDecisionService creditDecisionService) {
		this.creditDecisionService = creditDecisionService;
	}

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}
	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}

}
