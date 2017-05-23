package com.tera.batch.sendmsg.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.message.service.MsglogService;
import com.tera.payment.service.LoanRepayPlanService;

public class SendMsgProcessor implements ItemProcessor<Integer, String> {

	private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;
	
	private LoanRepayPlanService loanRepayPlanService;
	
	private MsglogService msglogService;
	
	@Override
    public String process(Integer lm) throws Exception {
		try{
			
			//短信通知处理
			loanRepayPlanService.loanRepayPlanRemindMessage();
			Map<String, Object> map=new HashMap<String, Object>();
			msglogService.queryAndSendMessage(map);
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

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}

	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}

	public LoanRepayPlanService getLoanRepayPlanService() {
		return loanRepayPlanService;
	}

	public void setLoanRepayPlanService(LoanRepayPlanService loanRepayPlanService) {
		this.loanRepayPlanService = loanRepayPlanService;
	}

	public MsglogService getMsglogService() {
		return msglogService;
	}

	public void setMsglogService(MsglogService msglogService) {
		this.msglogService = msglogService;
	}
	
	
	
	
}
