package com.tera.batch.jimu.processor;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.batch.exception.BatchProcessException;
import com.tera.cooperation.jmbox.model.form.DefaultCustomersInfo;
import com.tera.cooperation.jmbox.model.form.DefaultInfoResponseBean;
import com.tera.cooperation.jmbox.service.JmboxLogService;
import com.tera.cooperation.jmbox.service.JmboxService;



public class JMSendDefaultInfoProcessor implements ItemProcessor<Integer, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(JMSendDefaultInfoProcessor.class);
	

	private JmboxService jmboxService;
	private JmboxLogService jmboxLogService;
	
    private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;

	/**
	 * 推送积木盒子 违约客户信息
	 */
    @Override
    public String process(Integer pm) throws Exception {
    	
    	int size=100;
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("rowE",size);
    	int count=jmboxLogService.getDefaultCustomersCount(map);
    	int len=count/size;
    	len=count%size>0?len+1:len;
    	for (int i = 0; i < len; i++) {
    		map.put("rowS",i*size);
    		List<DefaultCustomersInfo> infoList=jmboxLogService.getDefaultCustomersList(map);
    		try {
    			DefaultInfoResponseBean repBean=jmboxService.sendDefaultInfo(infoList);
    			if(repBean!=null){
    				log.info("推送积木违约客户信息，总数量："+count+",一次100条，当前地"+(i+1)+"次发送。" +
    						"返回结果："+repBean.getState());
    			}else{
    				log.info("推送积木违约客户信息，总数量："+count+",一次100条，当前地"+(i+1)+"次发送。" +
    						"返回结果：NULL");
    			}
    		} catch (Exception e) {
    			
    			log.info("Error：推送积木违约客户信息异常!总数量："+count+",一次100条，当前"+i+1+"次发送。\n"+e);
    			
    			BatchErrorLog errorLog=new BatchErrorLog();
    			errorLog.setErrorMsg(e.getMessage());
    			errorLog.setJobName("daifuJob");
    			errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
    			batchErrorLogDao.add(errorLog);
    			throw new BatchProcessException(e);
    		}
		}
    	log.info("END：推送积木违约客户信息 执行结束!总数量："+count);
    	return null;
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

	public JmboxLogService getJmboxLogService() {
		return jmboxLogService;
	}
	public void setJmboxLogService(JmboxLogService jmboxLogService) {
		this.jmboxLogService = jmboxLogService;
	}

}
