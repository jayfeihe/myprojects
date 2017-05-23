package com.tera.batch.collection.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import  com.tera.collection.phone.service.CollectionBatchService;
import com.tera.message.service.MsglogService;
import com.tera.payment.service.LoanRepayPlanService;

public class CollectionProcessor  implements ItemProcessor<Integer, String> {

	private CollectionBatchService collectionBatchService;
	
	private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;
	
	
	@Override
    public String process(Integer lm) throws Exception {
		
		try{
			collectionBatchService.collectionBasicIni();
			collectionBatchService.handleCollectionWay();
			collectionBatchService.autoDistribution();
			
			return null;
		}catch (Exception e) {
			
			e.printStackTrace();  
            StringWriter sw = new StringWriter();  
            e.printStackTrace(new PrintWriter(sw, true));  
            String str = sw.toString();  
    
			BatchErrorLog errorLog=new BatchErrorLog();
			errorLog.setErrorMsg(str);
			errorLog.setJobName("collectionJob");
			errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			batchErrorLogDao.add(errorLog);
			throw e;
		}
		
	}

	public CollectionBatchService getCollectionBatchService() {
		return collectionBatchService;
	}

	public void setCollectionBatchService(
			CollectionBatchService collectionBatchService) {
		this.collectionBatchService = collectionBatchService;
	}

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}

	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}

	
	
}
