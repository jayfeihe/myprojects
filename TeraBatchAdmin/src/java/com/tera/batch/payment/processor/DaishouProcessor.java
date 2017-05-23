package com.tera.batch.payment.processor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.batch.exception.BatchProcessException;
import com.tera.payment.dao.PaymentDao;
import com.tera.payment.model.Payment;
import com.tera.payment.pay.FuiouPayService;
import com.tera.payment.pay.PayService;
import com.tera.payment.service.AllinPayTranxService;



public class DaishouProcessor implements ItemProcessor<Integer, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(DaishouProcessor.class);
	


    private PaymentDao<Payment> daoPay;
	

	private AllinPayTranxService allinPayTranxService;
	
    private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;

	private PayService payService;
    
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
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("inOut", "1");//1 收, 2 付
    	map.put("states", new String[]{"1", "3", "6"});// 1,待支付 2,已发盘 3,发盘失败 4,发盘成功 5,支付成功 6,支付失败 9,未确认 10,人工确认问题 
    	map.put("repaymentDate", new Date());
    	List<Payment> listPayInfos=daoPay.queryPayList(map);
    	try {
    		//代收
    		payService.batchDaishou(listPayInfos);
    		
    		
    		
    		/*//通联代收 已废弃
			InfoReq info= allinPayTranxService.makeReq("100001");//代收代码    			
    		if (info!=null) {
    			// 添加支付日志 返回合并后的 支付列表
    			List<Payment> pmtList=allinPayTranxService.executeBeforeDaishou(listPayInfos, info.getREQ_SN());
    			//执行代扣操作  返回 结果信息
    			pmtList=allinPayTranxService.batchDaishou(pmtList,info,AllinpayConstant.URLhttps);
    			for (Payment payment : pmtList) {
    				for (Payment pmInfo : listPayInfos) {
    					if(payment.getContractNo().equals(pmInfo.getContractNo())){
    						pmInfo.setState(payment.getState());
    						pmInfo.setReason(payment.getReason());
    					}
					}
				}
    			//集合 状态更新
    			allinPayTranxService.executeAfterDaishou(listPayInfos, info);
    		}*/
    		
		} catch (Exception e) {
			
			BatchErrorLog errorLog=new BatchErrorLog();
			errorLog.setErrorMsg(e.getMessage());
			errorLog.setJobName("paymentJob");
			errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			batchErrorLogDao.add(errorLog);
			throw new BatchProcessException(e);
		}
    	
    	
    	return null;
    	}

	public PaymentDao<Payment> getDaoPay() {
		return daoPay;
	}

	public void setDaoPay(PaymentDao<Payment> daoPay) {
		this.daoPay = daoPay;
	}

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}

	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}

	public AllinPayTranxService getAllinPayTranxService() {
		return allinPayTranxService;
	}

	public void setAllinPayTranxService(AllinPayTranxService allinPayTranxService) {
		this.allinPayTranxService = allinPayTranxService;
	}

	public PayService getPayService() {
		return payService;
	}

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

}
