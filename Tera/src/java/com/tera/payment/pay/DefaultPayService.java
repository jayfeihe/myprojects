/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接xml批量代收demo
 */
package com.tera.payment.pay;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tera.payment.constant.AllinpayConstant;
import com.tera.payment.model.Payment;
import com.tera.payment.model.ThirdPaymentLog;


@Service("defaultPayService")
public class DefaultPayService extends PayService{
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(DefaultPayService.class);
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Payment daishou(Payment pmt) throws Exception {	
		if (pmt==null) {
			return pmt;
		}
		
		// TODO 接口调用
		
		// 默认成功
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		pmt.setState("5");
		pmt.setReason("代收成功");
		pmt.setUpdateTime(nowTime);
		
		List<ThirdPaymentLog> logList=pmt.getThirdPaymentLogList();
		if(null != logList){	//如果日志对象不为空 更新信息 入库
			for (ThirdPaymentLog log : logList) {
				log.setState(pmt.getState());
				log.setReason(pmt.getReason());
				log.setOrderNo("");
				log.setUpdateTime(nowTime);
				log.setReceiveTime(nowTime);
				//支付完成 记录支付日志
				thirdPaymentService.add(log);
			}
		}else{ //如果没有 Log 对象就 创建一个 入库
			//添加log记录
			ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
			thirdPaymentLog.setPaymentId(pmt.getId());
			thirdPaymentLog.setOrderNo("");
			thirdPaymentLog.setContractNo(pmt.getContractNo());
			thirdPaymentLog.setPeriodNum(pmt.getPeriodNum());
			thirdPaymentLog.setSendTime(nowTime);
			thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
			thirdPaymentLog.setTargetAccount(pmt.getAccounttNo());
			thirdPaymentLog.setAmount(pmt.getActualAmount());
			thirdPaymentLog.setSubject(pmt.getSubject());
			thirdPaymentLog.setState(pmt.getState());
			thirdPaymentLog.setReason(pmt.getReason());
			thirdPaymentLog.setOperator("sysauto");
			thirdPaymentLog.setOrgId(pmt.getOrgId());
			thirdPaymentLog.setReceiveTime(nowTime);
			thirdPaymentLog.setCreateTime(nowTime);
			thirdPaymentLog.setUpdateTime(nowTime);
			thirdPaymentLog.setSn("0");
			pmt.addThirdPaymentLog(thirdPaymentLog);
			//支付完成 记录支付日志
			thirdPaymentService.add(thirdPaymentLog);
		}
		log.info("=========================代收成功=======================");
		return pmt;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Payment daiFu(Payment pmt) throws Exception {	
		if (pmt==null) {
			return pmt;
		}
		
		// TODO 接口调用
		
		// 默认成功
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		pmt.setState("5");
		pmt.setReason("代付成功");
		pmt.setUpdateTime(nowTime);
		
		List<ThirdPaymentLog> logList=pmt.getThirdPaymentLogList();
		if(null != logList){	//如果日志对象不为空 更新信息 入库
			for (ThirdPaymentLog log : logList) {
				log.setState(pmt.getState());
				log.setReason(pmt.getReason());
				log.setOrderNo("");
				log.setUpdateTime(nowTime);
				log.setReceiveTime(nowTime);
				//支付完成 记录支付日志
				thirdPaymentService.add(log);
			}
		}else{ //如果没有 Log 对象就 创建一个 入库
			//添加log记录
			ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
			thirdPaymentLog.setPaymentId(pmt.getId());
			thirdPaymentLog.setOrderNo("");
			thirdPaymentLog.setContractNo(pmt.getContractNo());
			thirdPaymentLog.setPeriodNum(pmt.getPeriodNum());
			thirdPaymentLog.setSendTime(nowTime);
			thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
			thirdPaymentLog.setTargetAccount(pmt.getAccounttNo());
			thirdPaymentLog.setAmount(pmt.getActualAmount());
			thirdPaymentLog.setSubject(pmt.getSubject());
			thirdPaymentLog.setState(pmt.getState());
			thirdPaymentLog.setReason(pmt.getReason());
			thirdPaymentLog.setOperator("sysauto");
			thirdPaymentLog.setOrgId(pmt.getOrgId());
			thirdPaymentLog.setReceiveTime(nowTime);
			thirdPaymentLog.setCreateTime(nowTime);
			thirdPaymentLog.setUpdateTime(nowTime);
			thirdPaymentLog.setSn("0");
			pmt.addThirdPaymentLog(thirdPaymentLog);
			//支付完成 记录支付日志
			thirdPaymentService.add(thirdPaymentLog);
		}
		return pmt;
	}
}
