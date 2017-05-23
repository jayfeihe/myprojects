package com.tera.batch.initseq.processor;


import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.batch.initseq.model.Sequence;

public class InitSeqProcessor implements ItemProcessor<Sequence, Sequence> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(InitSeqProcessor.class);
    
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
    public Sequence process(Sequence seq) throws Exception {    	
    	if(seq.getCurrentValue()>0) {
			seq.setCurrentValue(0);
		}
		return seq;
    }
    
	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}


	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}
	
	
}
