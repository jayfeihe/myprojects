/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接xml批量代收demo
 */
package com.tera.payment.pay;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tera.payment.constant.AllinpayConstant;
import com.tera.payment.fuyou.constant.FuOuConstant;
import com.tera.payment.fuyou.model.IncomeReqBean;
import com.tera.payment.fuyou.model.PayReqBean;
import com.tera.payment.fuyou.model.ResultBean;
import com.tera.payment.fuyou.service.ReqService;
import com.tera.payment.model.Payment;
import com.tera.payment.model.ThirdPaymentLog;
import com.tera.util.DateUtils;

/**
 * 富友支付
 */
@Service("fuiouPayService")
public class FuiouPayService extends PayService{
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Payment daishou(Payment pmt) throws Exception {	
		if (pmt==null) {
			return pmt;
		}
		
		String url = FuOuConstant.URL;
		String merid = FuOuConstant.MERID;
		String key = FuOuConstant.KEY;
		
		String reqtype=FuOuConstant.SINCOME_REQ_TYPE;
		IncomeReqBean srb=new IncomeReqBean();
		
		//请求流水号
		String orderNo=pmt.getContractNo().substring(pmt.getContractNo().length()-12)+System.currentTimeMillis();
		srb.setOrderno(orderNo);
		
		srb.setVer("1.0");
		srb.setMerdt(DateUtils.formatDate(new Date(), "yyyyMMdd"));
		srb.setBankno(pmt.getBankNo());//银行代码
		srb.setAccntnm(pmt.getAccountName());
		srb.setAccntno(pmt.getAccounttNo());
		srb.setAmt(String.valueOf((int)(pmt.getActualAmount()*100)));//金额 到分
		srb.setEntseq(pmt.getContractNo());
		srb.setCerttp(ReqService.getIdType(pmt.getIdType()));
		srb.setCertno(pmt.getIdNo());
		srb.setMobile(pmt.getMobile());	//手机号 必填
//		srb.setMemo(pmt.getIdNo());
		
		//请求 富友支付
		ReqService<IncomeReqBean> reqService=new ReqService<IncomeReqBean>(merid, key, reqtype, srb);
		ResultBean jg=reqService.requestFuOu(url);
		
		Timestamp tmp=new Timestamp(System.currentTimeMillis());
		
		if("000000".equals(jg.getRet()) || "0000".equals(jg.getRet())){
			pmt.setState("5");
			pmt.setReason(jg.getMsg());
			pmt.setUpdateTime(tmp);
		}else if("error".equals(jg.getRet())){
			pmt.setState("3");
			pmt.setReason(jg.getMsg());
			pmt.setUpdateTime(tmp);
		}else{
			pmt.setState("6");
			pmt.setReason(jg.getMsg());
			pmt.setUpdateTime(tmp);
		}
		List<ThirdPaymentLog> logList=pmt.getThirdPaymentLogList();
		if(null != logList){	//如果日志对象不为空 更新信息 入库
			for (ThirdPaymentLog log : logList) {
				log.setState(pmt.getState());
				log.setReason(pmt.getReason());
				log.setOrderNo(orderNo);
				log.setUpdateTime(tmp);
				log.setReceiveTime(tmp);
				//支付完成 记录支付日志
				thirdPaymentService.add(log);
			}
		}else{ //如果没有 Log 对象就 创建一个 入库
			//添加log记录
			ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
			thirdPaymentLog.setPaymentId(pmt.getId());
			thirdPaymentLog.setOrderNo(orderNo);
			thirdPaymentLog.setContractNo(pmt.getContractNo());
			thirdPaymentLog.setPeriodNum(pmt.getPeriodNum());
			thirdPaymentLog.setSendTime(tmp);
			thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
			thirdPaymentLog.setTargetAccount(pmt.getAccounttNo());
			thirdPaymentLog.setAmount(pmt.getActualAmount());
			thirdPaymentLog.setSubject(pmt.getSubject());
			thirdPaymentLog.setState(pmt.getState());
			thirdPaymentLog.setReason(pmt.getReason());
			thirdPaymentLog.setOperator("sysauto");
			thirdPaymentLog.setOrgId(pmt.getOrgId());
			thirdPaymentLog.setReceiveTime(tmp);
			thirdPaymentLog.setCreateTime(tmp);
			thirdPaymentLog.setUpdateTime(tmp);
			thirdPaymentLog.setSn("0");
			pmt.addThirdPaymentLog(thirdPaymentLog);
			//支付完成 记录支付日志
			thirdPaymentService.add(thirdPaymentLog);
		}
		
		return pmt;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Payment daiFu(Payment pmt) throws Exception {	
		if (pmt==null) {
			return pmt;
		}
		
		String url = FuOuConstant.URL;
		String merid = FuOuConstant.MERID;
		String key = FuOuConstant.KEY;
		
		String reqtype=FuOuConstant.PAY_REQ_TYPE;
		PayReqBean prb=new PayReqBean();
		
		//请求流水号
		String orderNo=pmt.getContractNo().substring(pmt.getContractNo().length()-12)+System.currentTimeMillis();
		prb.setOrderno(orderNo);
		
		prb.setVer("1.0");
		prb.setMerdt(DateUtils.formatDate(new Date(), "yyyyMMdd"));
		prb.setBankno(pmt.getBankNo());//银行代码
		prb.setCityno(pmt.getCityNo());//城市代码
//		prb.setBranchnm(pmt.getbranchnm);//支行名称
		prb.setAccntno(pmt.getAccounttNo());
		prb.setAccntnm(pmt.getAccountName());
		prb.setAmt(String.valueOf((int)(pmt.getActualAmount()*100)));//金额 到分
		prb.setEntseq(pmt.getContractNo());
		
		prb.setMobile(pmt.getMobile());//手机号必填
//		prb.setMemo("备注");
		
		//请求 富友支付
		ReqService<PayReqBean> reqService=new ReqService<PayReqBean>(merid, key, reqtype, prb);
		ResultBean jg=reqService.requestFuOu(url);
		
		Timestamp tmp=new Timestamp(System.currentTimeMillis());
		
		if("000000".equals(jg.getRet()) || "0000".equals(jg.getRet())){
			pmt.setState("5");
			pmt.setReason(jg.getMsg());
			pmt.setUpdateTime(tmp);
		}else {
			pmt.setState("6");
			pmt.setReason(jg.getMsg());
			pmt.setUpdateTime(tmp);
		}
		List<ThirdPaymentLog> logList=pmt.getThirdPaymentLogList();
		if(null != logList){	//如果日志对象不为空 更新信息 入库
			for (ThirdPaymentLog log : logList) {
				log.setState(pmt.getState());
				log.setReason(pmt.getReason());
				log.setOrderNo(orderNo);
				log.setUpdateTime(tmp);
				log.setReceiveTime(tmp);
				//支付完成 记录支付日志
				thirdPaymentService.add(log);
			}
		}else{ //如果没有 Log 对象就 创建一个 入库
			//添加log记录
			ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
			thirdPaymentLog.setPaymentId(pmt.getId());
			thirdPaymentLog.setOrderNo(orderNo);
			thirdPaymentLog.setContractNo(pmt.getContractNo());
			thirdPaymentLog.setPeriodNum(pmt.getPeriodNum());
			thirdPaymentLog.setSendTime(tmp);
			thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
			thirdPaymentLog.setTargetAccount(pmt.getAccounttNo());
			thirdPaymentLog.setAmount(pmt.getActualAmount());
			thirdPaymentLog.setSubject(pmt.getSubject());
			thirdPaymentLog.setState(pmt.getState());
			thirdPaymentLog.setReason(pmt.getReason());
			thirdPaymentLog.setOperator("sysauto");
			thirdPaymentLog.setOrgId(pmt.getOrgId());
			thirdPaymentLog.setReceiveTime(tmp);
			thirdPaymentLog.setCreateTime(tmp);
			thirdPaymentLog.setUpdateTime(tmp);
			thirdPaymentLog.setSn("0");
			pmt.addThirdPaymentLog(thirdPaymentLog);
			//支付完成 记录支付日志
			thirdPaymentService.add(thirdPaymentLog);
		}
		return pmt;
	}
}
