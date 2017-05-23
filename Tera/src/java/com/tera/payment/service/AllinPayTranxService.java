/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接xml批量代收demo
 */
package com.tera.payment.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.payreq.Body;
import com.aipg.payreq.Trans_Detail;
import com.aipg.payreq.Trans_Sum;
import com.aipg.rtreq.Trans;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.aipg.transquery.TransQueryReq;
import com.allinpay.XmlTools;
import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditAppService;
import com.tera.lend.model.LendApp;
import com.tera.lend.service.LendAppService;
import com.tera.match.model.Lend2match;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.MatchResultService;
import com.tera.payment.constant.AllinpayConstant;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.model.ThirdPaymentLog;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.dao.ParameterDao;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.Workday;
import com.tera.sys.service.WorkdayService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;


/**
 */
@Service("allinPayTranxService")
public class AllinPayTranxService {
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(AllinPayTranxService.class);
	
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private String strPfxPath="";
	private String strTltcerPath="";
	
	@Autowired
	private PaymentService<Payment> paymentService;
	
	@Autowired
	private ThirdPaymentService thirdPaymentService;
	
	@Autowired
	private ParameterDao<Parameter> parameterDao;
	
	@Autowired
	private MatchResultService<MatchResult> matchResultService;
	
	@Autowired
	private LoanRepayPlanService loanRepayPlanService;
	
	@Autowired
	private AccounttingService<Accountting> accounttingService;
	
	@Autowired(required=false)
	private ContractService contractService;
	
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;
	

	@Autowired(required=false)
	private ProductService<Product> productService;
	@Autowired(required=false)
	private Lend2matchService<Lend2match> lend2matchService;
	@Autowired(required=false)
	private WorkdayService<Workday> workdayService;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	
	/**
	 * 100002
	 * 批量代付。执行此方法之前先调用executeBeforeDaifu
	 * @param unPaymentList
	 * @param info
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Payment> batchDaifu(List<Payment> unPaymentList,InfoReq info,String url) throws Exception {
		if (unPaymentList.size()==0) {
			return unPaymentList;
		}
		String xml="";
		boolean isTLTFront=false;
		AipgReq aipg=new AipgReq();
		aipg.setINFO(info);
		Body body = new Body() ;//报文体
		
		//记录的概要汇总信息
		Trans_Sum trans_sum = new Trans_Sum() ;
		
		List<Trans_Detail> transList = new ArrayList<Trans_Detail>() ;
		Double dbSum=0.0; //记录总金额
		for (int i = 0; i < unPaymentList.size(); i++) {
			
			//报文信息
			dbSum=dbSum+unPaymentList.get(i).getActualAmount();
			
			Trans_Detail trans_detail = new Trans_Detail() ;
			trans_detail.setSN(String.valueOf(i)) ;
			
	    	trans_detail.setACCOUNT_NAME(unPaymentList.get(i).getAccountName().trim()) ;
	 		
			trans_detail.setACCOUNT_NO(unPaymentList.get(i).getAccounttNo().trim()) ;
			int tmp=(int)(unPaymentList.get(i).getActualAmount()*100);
			String strTmp=String.valueOf(tmp);
			trans_detail.setAMOUNT(strTmp) ;//交易金额，单位为分
			//测试商户号
			if(AllinpayConstant.merchantIdPay.equals("200604000000445")) {
				trans_detail.setBANK_CODE("0105"); //银行代码，生产可以不填
			}
			transList.add(trans_detail) ;
		}
		
		trans_sum.setBUSINESS_CODE(AllinpayConstant.daifuBussinessCode) ;//交易业务代码
		trans_sum.setMERCHANT_ID(AllinpayConstant.merchantIdPay) ;//商户号
		trans_sum.setTOTAL_ITEM(String.valueOf(unPaymentList.size())) ;//总记录数
		int tmp=(int)(dbSum*100);
		String strTmp=String.valueOf(tmp);
		trans_sum.setTOTAL_SUM(strTmp) ;//总金额
		Date dt=DateUtils.getDateTimeNow();
		trans_sum.setSUBMIT_TIME(DateUtils.toString(dt, "yyyyMMddHHmmss"));
		body.setTRANS_SUM(trans_sum) ;//加入到报文体中
		
		body.setDetails(transList) ;
	    aipg.addTrx(body) ;
		//做交易记录
	    //1更改payment的状态为
	    
	    xml=XmlTools.buildXml(aipg,true);//.replaceAll("</INFO>", "</INFO><BODY>").replaceAll("</AIPG>", "</BODY></AIPG>");
	    
	    xml=this.signMsg(xml);
	    log.info("批量代付发送报文：\n" + xml);
	    //发送报文
	    String resp=XmlTools.send(url,new String(xml.getBytes("UTF-8"),"UTF-8"));
	    log.info("批量代付返回报文：\n" + resp);
	    // 验证返回的报文
	    boolean flag=XmlTools.verifySign(resp, strTltcerPath, false,isTLTFront);
	    
	    if (flag) { //验签通过
	    	AipgRsp aipgRsp=new AipgRsp();
	    	aipgRsp=(AipgRsp)XmlTools.parseXml(resp, false);
	    	if ("0000".equals(aipgRsp.getINFO().getRET_CODE())) {	    		
				//成功
	    		//修改记录的状态，发盘成功
		    	for (int j = 0; j < unPaymentList.size(); j++) {
					Payment payment = unPaymentList.get(j);
		    		payment.setState("4");
		    		payment.setReason("");
		    		payment.setUpdateTime(new Timestamp(System.currentTimeMillis()));		    		
				}	    		
			}else {
				//失败
				String strReason=aipgRsp.getINFO().getERR_MSG();
				for (int j = 0; j < unPaymentList.size(); j++) {
					Payment payment=unPaymentList.get(j);
		    		payment.setState("3");
		    		payment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    		payment.setReason(strReason);		    		
				}
			}
		}
		return unPaymentList;
	}
	
	/**
	 * 100001
	 * 批量代收。执行此方法之前先调用
	 * @param unPaymentList 
	 * @param info
	 * @param url
	 * @return		返回 结果信息
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Payment> batchDaishou(List<Payment> unPaymentList,InfoReq info,String url) throws Exception {
		if (unPaymentList.size()==0) {
			return unPaymentList;
		}
		String xml="";
		boolean isTLTFront=false;
		AipgReq aipg=new AipgReq();
		aipg.setINFO(info);
		Body body = new Body() ;//报文体
		
		//记录的概要汇总信息
		Trans_Sum trans_sum = new Trans_Sum() ;
		
		List<Trans_Detail> transList = new ArrayList<Trans_Detail>() ;
		Double dbSum=0.0; //记录总金额
		for (int i = 0; i < unPaymentList.size(); i++) {
			
			//报文信息
			dbSum=MathUtils.add(dbSum, unPaymentList.get(i).getActualAmount());
			
			Trans_Detail trans_detail = new Trans_Detail() ;
			
			trans_detail.setSN(unPaymentList.get(i).getThirdPaymentLogList().get(0).getSn()) ;
			
	    	trans_detail.setACCOUNT_NAME(unPaymentList.get(i).getAccountName().trim()) ;
	 		
			trans_detail.setACCOUNT_NO(unPaymentList.get(i).getAccounttNo().trim()) ;
			long tmp=(long)MathUtils.mul(unPaymentList.get(i).getActualAmount(),100);
			String strTmp=String.valueOf(tmp);
			trans_detail.setAMOUNT(strTmp) ;//交易金额，单位为分
			//测试商户号
			if(AllinpayConstant.merchantIdPay.equals("200604000000445")) {
				trans_detail.setBANK_CODE("0105"); //银行代码，生产可以不填
			}
			transList.add(trans_detail) ;
		}
		
		trans_sum.setBUSINESS_CODE(AllinpayConstant.daishouBussinessCode) ;//交易业务代码
		trans_sum.setMERCHANT_ID(AllinpayConstant.merchantIdPay) ;//商户号
		trans_sum.setTOTAL_ITEM(String.valueOf(unPaymentList.size())) ;//总记录数
		long tmp=(long)MathUtils.mul(dbSum,100);
		String strTmp=String.valueOf(tmp);
		trans_sum.setTOTAL_SUM(strTmp) ;//总金额
		Date dt=DateUtils.getDateTimeNow();
		trans_sum.setSUBMIT_TIME(DateUtils.toString(dt, "yyyyMMddHHmmss"));
		body.setTRANS_SUM(trans_sum) ;//加入到报文体中
		
		body.setDetails(transList) ;
	    aipg.addTrx(body) ;
		//做交易记录
	    //1更改payment的状态为
	    
	    xml=XmlTools.buildXml(aipg,true);//.replaceAll("</INFO>", "</INFO><BODY>").replaceAll("</AIPG>", "</BODY></AIPG>");
	    xml=this.signMsg(xml);
	    log.info("批量代收发送报文：\n" + xml);
	    //发送报文
	    String resp=XmlTools.send(url,new String(xml.getBytes("UTF-8"),"UTF-8"));
	    log.info("批量代收返回报文：\n" + resp);
	    // 验证返回的报文
	    boolean flag=XmlTools.verifySign(resp, strTltcerPath, false,isTLTFront);
	    
	    AipgRsp aipgRsp=(AipgRsp)XmlTools.parseXml(resp, false);
	    if (flag) { //验签通过
	    	if ("0000".equals(aipgRsp.getINFO().getRET_CODE())) {	    		
				//成功
	    		
	    		for (Payment payment : unPaymentList) {
					payment.setState("4");
					payment.setReason("");	
				}
	    		
	    		/*List rspList=aipgRsp.getTrxData();
	    		com.aipg.payreq.Body pyBody=(com.aipg.payreq.Body)rspList.get(0);
	    		List<QTDetail> detailList= pyBody.getDetails();
	    		for (int i = 0; i < detailList.size(); i++) {
	    			QTDetail dail=detailList.get(i);
	    			Payment hxpmt=unPaymentList.get(Integer.valueOf(dail.getSN()));
	    			if("0000".equals(dail.getRET_CODE())){
	    				hxpmt.setState("4");
	    				hxpmt.setReason("");
	    			}else{
	    				hxpmt.setState("3");
	    				hxpmt.setReason(dail.getERR_MSG());
	    			}
	    		}*/
			}else {
				//失败
				String strReason=aipgRsp.getINFO().getERR_MSG();
				for (Payment payment : unPaymentList) {
					payment.setState("3");
					payment.setReason(strReason);	
				}
			}
		}else{
			//失败
			String strReason=aipgRsp.getINFO().getERR_MSG();
			for (Payment payment : unPaymentList) {
				payment.setState("3");
				payment.setReason(strReason);	
			}	    		
		}
		return unPaymentList;
	}

	/**
	 * 实时代收
	 * @param unPayment
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Payment daishouRealTime(Payment unPayment, String url) throws Exception {
		if (unPayment==null) {
			return null;
		}

		InfoReq info= makeReq("100011");//实时代收代码    		
		String xml="";
		boolean isTLTFront=false;
		AipgReq aipg=new AipgReq();
		aipg.setINFO(info);
		
		Trans trans=new Trans();
		trans.setBUSINESS_CODE(AllinpayConstant.daishouBussinessCode) ;//交易业务代码
		trans.setMERCHANT_ID(AllinpayConstant.merchantIdPay);
		trans.setSUBMIT_TIME(df.format(new Date()));
		trans.setACCOUNT_NAME(unPayment.getAccountName());
		trans.setACCOUNT_NO(unPayment.getAccounttNo());
		trans.setACCOUNT_PROP("0");//0私人，1公司
		trans.setAMOUNT(""+unPayment.getActualAmount()*100);
		//测试商户号
		if(AllinpayConstant.merchantIdPay.equals("200604000000445")) {
			trans.setBANK_CODE("0105"); //银行代码，生产可以不填
		}
		trans.setCURRENCY("CNY");
		aipg.addTrx(trans);
		ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
		thirdPaymentLog.setPaymentId(unPayment.getId());
		thirdPaymentLog.setOrderNo(info.getREQ_SN());
		thirdPaymentLog.setContractNo(unPayment.getContractNo());
		thirdPaymentLog.setSendTime(new Timestamp(System.currentTimeMillis()));
		thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
		thirdPaymentLog.setTargetAccount(unPayment.getAccounttNo().trim());
		thirdPaymentLog.setAmount(unPayment.getActualAmount());
		thirdPaymentLog.setSubject(unPayment.getSubject());
		thirdPaymentLog.setOperator("sysauto");
		thirdPaymentLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
		thirdPaymentLog.setSn(String.valueOf(0));
		unPayment.addThirdPaymentLog(thirdPaymentLog);
		//做交易记录
	    //1更改payment的状态为
	    
	    xml=XmlTools.buildXml(aipg,true);//.replaceAll("</INFO>", "</INFO><BODY>").replaceAll("</AIPG>", "</BODY></AIPG>");
	    
	    xml=this.signMsg(xml);
	    log.info("实时代收发送报文：\n" + xml);
	    //发送报文
	    String resp=XmlTools.send(url,new String(xml.getBytes("UTF-8"),"UTF-8"));
	    log.info("实时代收返回报文：\n" + resp);
	    // 验证返回的报文
	    boolean flag=XmlTools.verifySign(resp, strTltcerPath, false,isTLTFront);
	    
	    AipgRsp aipgRsp=(AipgRsp)XmlTools.parseXml(resp, false);
	    if (flag) { //验签通过
	    	if ("0000".equals(aipgRsp.getINFO().getRET_CODE())) {	    		
				//成功
	    		unPayment.setState("5");
	    		unPayment.setReason("");
	    		unPayment.getThirdPaymentLogList().get(0).setState("5");
			}else {
				//失败
				String strReason=aipgRsp.getINFO().getERR_MSG();
				unPayment.setState("6");
				unPayment.setReason(strReason);	
				unPayment.getThirdPaymentLogList().get(0).setState("6");
			}
		}else{
			//失败
			String strReason=aipgRsp.getINFO().getERR_MSG();
			unPayment.setState("6");
			unPayment.setReason(strReason);
			unPayment.getThirdPaymentLogList().get(0).setState("6");
		}
		return unPayment;
	}
	
	/**
	 * 代付批处理执行之前的操作
	 * @param listPayInfo   必须是根据合同号排序 的List
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Payment> executeBeforeDaishou(List<Payment> listPayInfo, String orderNo) throws Exception {	
		if (listPayInfo.size()==0) {
			return listPayInfo;
		}
		int SN=-1;
		String htNO="";
		Map<String, Payment> jgMap=new HashMap<String, Payment>();
		try {
			for (int i = 0; i < listPayInfo.size(); i++) {
				//更改payment的记录状态
				Payment paymentTmp=listPayInfo.get(i);
				paymentTmp.setState("2");//已发盘
				paymentTmp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				paymentService.update(paymentTmp);
				//处理 序号
				if(!htNO.equals(paymentTmp.getContractNo())){
					htNO=paymentTmp.getContractNo();
					SN++;
				}
				Payment zsPmt=jgMap.get(htNO);
				if(zsPmt==null){
					zsPmt= paymentTmp.clone();
					jgMap.put(htNO,zsPmt);
				}else{
					zsPmt.setActualAmount(MathUtils.add(zsPmt.getActualAmount(),paymentTmp.getActualAmount()));
				}
				//添加log记录
				ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
				thirdPaymentLog.setPaymentId(paymentTmp.getId());
				thirdPaymentLog.setOrderNo(orderNo);
				thirdPaymentLog.setContractNo(paymentTmp.getContractNo());
				thirdPaymentLog.setPeriodNum(paymentTmp.getPeriodNum());
				thirdPaymentLog.setSendTime(new Timestamp(System.currentTimeMillis()));
				thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
				thirdPaymentLog.setTargetAccount(paymentTmp.getAccounttNo());
				thirdPaymentLog.setAmount(paymentTmp.getActualAmount());
				thirdPaymentLog.setSubject(paymentTmp.getSubject());
				thirdPaymentLog.setState("2");
				thirdPaymentLog.setOperator("sysauto");
				thirdPaymentLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
				thirdPaymentLog.setSn(String.valueOf(SN));
				thirdPaymentService.add(thirdPaymentLog);
				paymentTmp.addThirdPaymentLog(thirdPaymentLog);
				zsPmt.addThirdPaymentLog(thirdPaymentLog);
			}
		}catch(Exception e) {
			throw e;
		}
		List<Payment> jgList=new ArrayList<Payment>();
		jgList.addAll(jgMap.values());
		return jgList;
	}

	
	/**
	 * 更新 代收处理结果
	 * @param listPayInfo
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void executeAfterDaishou(List<Payment> listPayInfo,InfoReq info) throws Exception{
		Timestamp tamp=new Timestamp(System.currentTimeMillis());
		for (Payment payment : listPayInfo) {
			payment.setUpdateTime(tamp);	
			paymentService.update(payment);
			List<ThirdPaymentLog> logList=payment.getThirdPaymentLogList();
			for (ThirdPaymentLog thirdPaymentLog : logList) {
				thirdPaymentLog.setState(payment.getState());
				thirdPaymentLog.setReceiveTime(tamp);
				thirdPaymentLog.setUpdateTime(tamp);
				thirdPaymentService.update(thirdPaymentLog);
			}
		}
	}
	
	
	/**
	 * 100002
	 * 批量 代付。执行此方法之前先调用executeBeforeDaifu
	 * @param url
	 * @param listPayInfo
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void batchDaifu(String url,List<Payment> listPayInfo,InfoReq info) throws Exception {
		
		if (listPayInfo.size()==0) {
			return;
		}
		String xml="";
		boolean isTLTFront=false;
		AipgReq aipg=new AipgReq();
		aipg.setINFO(info);
		Body body = new Body() ;//报文体
		
		//记录的概要汇总信息
		Trans_Sum trans_sum = new Trans_Sum() ;
		
		List<Trans_Detail> transList = new ArrayList<Trans_Detail>() ;
		Double dbSum=0.0; //记录总金额
		for (int i = 0; i < listPayInfo.size(); i++) {
			
			//报文信息
			dbSum=dbSum+listPayInfo.get(i).getActualAmount();
			
			Trans_Detail trans_detail = new Trans_Detail() ;
			trans_detail.setSN(String.valueOf(i)) ;
			
	    	trans_detail.setACCOUNT_NAME(listPayInfo.get(i).getAccountName().trim()) ;
	 		
			trans_detail.setACCOUNT_NO(listPayInfo.get(i).getAccounttNo().trim()) ;
			int tmp=(int)(listPayInfo.get(i).getActualAmount()*100);
			String strTmp=String.valueOf(tmp);
			trans_detail.setAMOUNT(strTmp) ;//交易金额，单位为分
			//测试商户号
			if(AllinpayConstant.merchantIdPay.equals("200604000000445")) {
				trans_detail.setBANK_CODE("0105"); //银行代码，生产可以不填
			}
			transList.add(trans_detail) ;
		}
		
		trans_sum.setBUSINESS_CODE(AllinpayConstant.daifuBussinessCode) ;//交易业务代码
		trans_sum.setMERCHANT_ID(AllinpayConstant.merchantIdPay) ;//商户号
		trans_sum.setTOTAL_ITEM(String.valueOf(listPayInfo.size())) ;//总记录数
		int tmp=(int)(dbSum*100);
		String strTmp=String.valueOf(tmp);
		trans_sum.setTOTAL_SUM(strTmp) ;//总金额
		Date dt=DateUtils.getDateTimeNow();
		trans_sum.setSUBMIT_TIME(DateUtils.toString(dt, "yyyyMMddHHmmss"));
		body.setTRANS_SUM(trans_sum) ;//加入到报文体中
		
		body.setDetails(transList) ;
	    aipg.addTrx(body) ;
		//做交易记录
	    //1更改payment的状态为
	    
	    xml=XmlTools.buildXml(aipg,true);//.replaceAll("</INFO>", "</INFO><BODY>").replaceAll("</AIPG>", "</BODY></AIPG>");
	    
	    xml=this.signMsg(xml);
	    log.info("批量代付发送报文：\n" + xml);
	    //发送报文
	    String resp=XmlTools.send(url,new String(xml.getBytes("UTF-8"),"UTF-8"));
	    log.info("批量代付返回报文：\n" + resp);
	    // 验证返回的报文
	    boolean flag=XmlTools.verifySign(resp, strTltcerPath, false,isTLTFront);
	    AipgRsp aipgRsp=(AipgRsp)XmlTools.parseXml(resp, false);
	    if (flag) { //验签通过
	    	if ("0000".equals(aipgRsp.getINFO().getRET_CODE())) {
				//成功
	    		//修改记录的状态，发盘成功
		    	for (int j = 0; j < listPayInfo.size(); j++) {
					Payment payments=new Payment();
		    		payments=paymentService.queryByKey(listPayInfo.get(j).getId());
		    		payments.setState("4");
		    		payments.setReason("");
		    		payments.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    		paymentService.update(payments);
    				
		    		//更新 信用借款 申请状态
		    		Map<String, Object> mapCon=new HashMap<String, Object>();
		    		mapCon.put("contractNo", payments.getContractNo());
		    		mapCon.put("state", "1");
		    		List<Contract>listContracts=contractService.queryList(mapCon);
		    		if (listContracts.size()==0) {
		    			continue;
		    		}
		    		Contract contract=listContracts.get(0);
		    		String loanAppId=contract.getLoanAppId().trim();
		    		//信用贷 更改申请ID
		    		if(loanAppId.startsWith("C")) {
		    			Map<String, Object> appMap = new HashMap<String, Object>();
			    		appMap.put("appId", loanAppId);
			    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
			    		creditApp.setState("21");
			    		creditApp.setOperator(BpmConstants.SYSTEM_SYS);
			    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			    		creditAppService.update(creditApp);
		    		}
		    		
		    		
				}
		    	//更改该交易订单下的记录的状态
		    	Map<String, Object> map=new HashMap<String, Object>();
		    	map.put("orderNo", info.getREQ_SN());
		    	
		    	List<ThirdPaymentLog> listLogs=thirdPaymentService.queryList(map);
		    	for (int k = 0; k < listLogs.size(); k++) {
		    		listLogs.get(k).setState("4");
		    		listLogs.get(k).setReceiveTime(new Timestamp(System.currentTimeMillis()));
		    		listLogs.get(k).setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    		
		    		thirdPaymentService.update(listLogs.get(k));
		    	}
	    		
			}else {
				//失败
				String strReason=aipgRsp.getINFO().getERR_MSG();
				for (int j = 0; j < listPayInfo.size(); j++) {
					Payment payments=new Payment();
		    		payments=paymentService.queryByKey(listPayInfo.get(j).getId());
		    		payments.setState("3");
		    		payments.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    		payments.setReason(strReason);
		    		paymentService.update(payments);
		    		
		    		//更新 信用借款 申请状态
		    		Map<String, Object> mapCon=new HashMap<String, Object>();
		    		mapCon.put("contractNo", payments.getContractNo());
		    		mapCon.put("state", "1");
		    		List<Contract>listContracts=contractService.queryList(mapCon);
		    		if (listContracts.size()==0) {
		    			continue;
		    		}
		    		Contract contract=listContracts.get(0);
		    		String loanAppId=contract.getLoanAppId().trim();
		    		//信用贷 更改申请
		    		if(loanAppId.startsWith("C")) {
		    			Map<String, Object> appMap = new HashMap<String, Object>();
			    		appMap.put("appId", loanAppId);
			    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
			    		creditApp.setState("22");
			    		creditApp.setOperator(BpmConstants.SYSTEM_SYS);
			    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			    		creditAppService.update(creditApp);
		    		}
		    		
				}
				//更改该交易订单下的记录的状态
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("orderNo", info.getREQ_SN().trim());
				List<ThirdPaymentLog> listLogs=thirdPaymentService.queryList(map);
				for (int k = 0; k < listLogs.size(); k++) {
					listLogs.get(k).setState("3");
					listLogs.get(k).setReceiveTime(new Timestamp(System.currentTimeMillis()));
					listLogs.get(k).setUpdateTime(new Timestamp(System.currentTimeMillis()));
					listLogs.get(k).setReason(strReason);
					thirdPaymentService.update(listLogs.get(k));
				}
			}
		}else{
			//失败
			String strReason=aipgRsp.getINFO().getERR_MSG();
			for (int j = 0; j < listPayInfo.size(); j++) {
				Payment payments=new Payment();
	    		payments=paymentService.queryByKey(listPayInfo.get(j).getId());
	    		payments.setState("3");
	    		payments.setUpdateTime(new Timestamp(System.currentTimeMillis()));
	    		payments.setReason(strReason);
	    		paymentService.update(payments);
	    		//更新 信用借款 申请状态
	    		Map<String, Object> mapCon=new HashMap<String, Object>();
	    		mapCon.put("contractNo", payments.getContractNo());
	    		mapCon.put("state", "1");
	    		List<Contract>listContracts=contractService.queryList(mapCon);
	    		if (listContracts.size()==0) {
	    			continue;
	    		}
	    		Contract contract=listContracts.get(0);
	    		String loanAppId=contract.getLoanAppId().trim();
	    		//信用贷 更改申请
	    		if(loanAppId.startsWith("C")) {
	    			Map<String, Object> appMap = new HashMap<String, Object>();
		    		appMap.put("appId", loanAppId);
		    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
		    		creditApp.setState("22");
		    		creditApp.setOperator(BpmConstants.SYSTEM_SYS);
		    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    		creditAppService.update(creditApp);
	    		}
			}
			//更改该交易订单下的记录的状态
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("orderNo", info.getREQ_SN().trim());
			List<ThirdPaymentLog> listLogs=thirdPaymentService.queryList(map);
			for (int k = 0; k < listLogs.size(); k++) {
				listLogs.get(k).setState("3");
				listLogs.get(k).setReceiveTime(new Timestamp(System.currentTimeMillis()));
				listLogs.get(k).setUpdateTime(new Timestamp(System.currentTimeMillis()));
				listLogs.get(k).setReason(strReason);
				thirdPaymentService.update(listLogs.get(k));
			}
		}
	      
	}
	/**
	 * 代付批处理执行之前的操作
	 * @param listPayInfo
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void executeBeforeDaifu(List<Payment> listPayInfo, String orderNo) throws Exception {	
		
		try {
			for (int i = 0; i < listPayInfo.size(); i++) {
				//更改payment的记录状态
				Payment paymentTmp=new Payment();
				paymentTmp=paymentService.queryByKey(listPayInfo.get(i).getId());
				paymentTmp.setState("2");//已发盘
				paymentTmp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				paymentService.update(paymentTmp);
				//添加log记录
				
				ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
				thirdPaymentLog.setPaymentId(paymentTmp.getId());
				thirdPaymentLog.setOrderNo(orderNo);
				thirdPaymentLog.setContractNo(paymentTmp.getContractNo());
				thirdPaymentLog.setPeriodNum(paymentTmp.getPeriodNum());
				thirdPaymentLog.setSendTime(new Timestamp(System.currentTimeMillis()));
				thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
				thirdPaymentLog.setTargetAccount(listPayInfo.get(i).getAccounttNo());
				thirdPaymentLog.setAmount(paymentTmp.getActualAmount());
				thirdPaymentLog.setSubject(paymentTmp.getSubject());
				thirdPaymentLog.setState("2");
				thirdPaymentLog.setOperator("sysauto");
				thirdPaymentLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
				thirdPaymentLog.setSn(String.valueOf(i));
				
				thirdPaymentService.add(thirdPaymentLog);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 交易处理结果批量查询
	 * @param url 支付的URL
	 * @param unfinishedList 未完成的第三方支付日志的列表
	 * @return 支付结果
	 * @throws Exception 异常
	 */
	@SuppressWarnings("unchecked")
	public List<ThirdPaymentLog> batchQuery(String url, List<ThirdPaymentLog> unfinishedList) throws Exception {		
		//保存处理结果的支付日志
		List<ThirdPaymentLog> resultLogs = new ArrayList<ThirdPaymentLog> ();
		
		String xml="";
		boolean isTLTFront=false;
		AipgReq aipg=new AipgReq();
		InfoReq info=makeReq("200004");//查询代码
		aipg.setINFO(info);
		
		for (int i = 0; i < unfinishedList.size(); i++) {
			TransQueryReq dr=new TransQueryReq();
			dr.setMERCHANT_ID(AllinpayConstant.merchantIdPay) ;
			dr.setQUERY_SN(unfinishedList.get(i).getOrderNo().trim());
			dr.setSTATUS(2);
			dr.setTYPE(1) ;
			dr.setSTART_DAY("");
			dr.setEND_DAY("");
			aipg.addTrx(dr);	
		}
		xml=XmlTools.buildXml(aipg,true);//.replaceAll("</INFO>", "</INFO><BODY>").replaceAll("</AIPG>", "</BODY></AIPG>");
		xml=this.signMsg(xml);
		log.info("批量查询发送报文：\n" + xml);
	    //发送报文
	    String resp=XmlTools.send(url,new String(xml.getBytes("UTF-8"),"UTF-8"));
	    log.info("批量查询返回报文：\n" + resp);
	    // 验证返回的报文
	    boolean flag=XmlTools.verifySign(resp, strTltcerPath, false,isTLTFront);
	    if (flag) { //验签通过
	    	AipgRsp aipgRsp=new AipgRsp();
	    	aipgRsp=(AipgRsp)XmlTools.parseXml(resp, false);	    	
	    	if ("0000".equals(aipgRsp.getINFO().getRET_CODE().trim())) {
	    		List list=aipgRsp.getTrxData();
	    		for (int i = 0; i < list.size(); i++) {	    			
	    			QTransRsp qtransRsp=(QTransRsp)list.get(i);	    			
	    			for (int j = 0; j < qtransRsp.getDetails().size(); j++) {	    				
			    		QTDetail qtDetail=(QTDetail)qtransRsp.getDetails().get(j);
			    		String strId=qtDetail.getBATCHID().trim();
			    		String strSn=qtDetail.getSN().trim();
			    		
			    		//循环遍历，将成功的记录放入返回结果
			    		ThirdPaymentLog logTmp = new ThirdPaymentLog();
		    			for (ThirdPaymentLog paymentLogTmp : unfinishedList) {
							if(paymentLogTmp.getOrderNo().equals(strId) 
									&& paymentLogTmp.getSn().equals(strSn)) {
								logTmp = paymentLogTmp;
							}
						}
			    		if ("0000".equals(qtDetail.getRET_CODE().trim())) {
			    			//交易成功
			    			if ("2".equals(logTmp.getState())||"4".equals(logTmp.getState())) {								
				    			logTmp.setState("5");
				    			logTmp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				    			logTmp.setReason("");
			    			}
						}else {
							//交易失败							
			    			logTmp.setState("6");
			    			logTmp.setReason(qtDetail.getERR_MSG());
			    			logTmp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					    }
			    		resultLogs.add(logTmp);
	    			}
	    		}
	    	}
	    }
		return resultLogs;
	}

	/**
	 * 交易批量查询
	 * @param url
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void batchQuery(String url) throws Exception {
		
		List<ThirdPaymentLog> listLogs=thirdPaymentService.queryUnfinishedList();
		if (listLogs.size()==0) {
			return;
		}
		
		String xml="";
		boolean isTLTFront=false;
		AipgReq aipg=new AipgReq();
		InfoReq info=makeReq("200004");//查询代码
		aipg.setINFO(info);
		
		for (int i = 0; i < listLogs.size(); i++) {
			TransQueryReq dr=new TransQueryReq();
			dr.setMERCHANT_ID(AllinpayConstant.merchantIdPay) ;
			dr.setQUERY_SN(listLogs.get(i).getOrderNo().trim());
			dr.setSTATUS(2);
			dr.setTYPE(1) ;
			dr.setSTART_DAY("");
			dr.setEND_DAY("");
			aipg.addTrx(dr);	
		}
	    xml=XmlTools.buildXml(aipg,true);//.replaceAll("</INFO>", "</INFO><BODY>").replaceAll("</AIPG>", "</BODY></AIPG>");
	    
	    xml=this.signMsg(xml);
	    log.info("批量查询发送报文：\n" + xml);
	    //发送报文
	    String resp=XmlTools.send(url,new String(xml.getBytes("UTF-8"),"UTF-8"));
	    log.info("批量查询返回报文：\n" + resp);
	    // 验证返回的报文
	    boolean flag=XmlTools.verifySign(resp, strTltcerPath, false,isTLTFront);
	    if (flag) { //验签通过
	    	AipgRsp aipgRsp=new AipgRsp();
	    	aipgRsp=(AipgRsp)XmlTools.parseXml(resp, false);
	    	
	    	if ("0000".equals(aipgRsp.getINFO().getRET_CODE().trim())) {
				//成功
	    		
	    		List list=aipgRsp.getTrxData();
	    		for (int i = 0; i < list.size(); i++) {
	    			
	    			QTransRsp qtransRsp=(QTransRsp)list.get(i);
	    			
	    			for (int j = 0; j < qtransRsp.getDetails().size(); j++) {
	    				
			    		QTDetail qtDetail=(QTDetail)qtransRsp.getDetails().get(j);
			    		//TRXDIR	交易方向	C(0)	0 付 1收
			    		String strId=qtDetail.getBATCHID().trim();			    		
			    		String strSn=qtDetail.getSN().trim();
			    		String inoutFlag = qtDetail.getTRXDIR().trim().equals("1") ? "1":"2"; //1 收 , 2付
			    		Map<String, Object> map=new HashMap<String, Object>();
			    		map.put("orderNo", strId);
			    		map.put("sn", strSn);
			    		List<ThirdPaymentLog> listTmp=thirdPaymentService.queryList(map);
			    		if (listTmp.size()==0) {
							continue;
						}
				    	try {
				    		for (int k = 0; k < listTmp.size(); k++) {
					    		ThirdPaymentLog logTmp=listTmp.get(k);
					    		if ("0000".equals(qtDetail.getRET_CODE().trim())) {
					    			//交易成功
					    			if ("2".equals(logTmp.getState())||"4".equals(logTmp.getState())) {
										
						    			logTmp.setState("5");
						    			logTmp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						    			logTmp.setReason("");
						    			thirdPaymentService.update(logTmp);
						    			//更新payment
						    			Payment payments=new Payment();
							    		payments=paymentService.queryByKey(logTmp.getPaymentId());
							    		//处理未成功的代付
							    		if (!"5".equals(payments.getState())&& inoutFlag.equals("2")) {
							    			payments.setState("5");
								    		payments.setReason("");
								    		payments.setUpdateTime(new Timestamp(System.currentTimeMillis()));
								    		
								    		paymentService.update(payments);
								    		//根据合同号获取借款申请号loanAppId
								    		Map<String, Object> mapCon=new HashMap<String, Object>();
								    		mapCon.put("contractNo", payments.getContractNo());
								    		mapCon.put("state", "1");
								    		List<Contract>listContracts=contractService.queryList(mapCon);
								    		if (listContracts.size()==0) {
								    			continue;
								    		}
								    		Contract contract=listContracts.get(0);
								    		String loanAppId=contract.getLoanAppId().trim();
								    		//抵押贷
								    		if(loanAppId.startsWith("B")) {
									    		//完成业务记账
									    		accounttingService.accountting(payments);								    		
									    		if ("放款".equals(payments.getSubject())) {
									    			//放款完成后的操作：合同处理，生成还款计划
										    		matchResultService.handleAfterPayment(logTmp.getContractNo(), payments.getUpdateTime());
										    		loanRepayPlanService.addRepayPlan(logTmp.getContractNo());
												}
								    		} else if(loanAppId.startsWith("C")) {
								    			//完成业务记账
									    		accounttingService.accountting(payments);
									    		//判断流程状态是否是放款，是就继续
									    		//实际上里面只有一个BpmTask对象
									    		List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(loanAppId);
									    		BpmTask task = bpmTasks.get(0);
									    		if (task!=null && "放款".equals(task.getState())) {
									    			task.setOperator(BpmConstants.SYSTEM_SYS);
										    		task = processService.goNext(task, "结束", task.getProcesser());
									    		}
									    		Map<String, Object> appMap = new HashMap<String, Object>();
									    		appMap.put("appId", loanAppId);
									    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
									    		creditApp.setState("23");
									    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
									    		creditAppService.update(creditApp);
									    		contract.setState("2");
									    		contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
									    		contractService.update(contract);
									    		
									    		//付款成功 调用，更新 还款计划，支付信息 状态
									    		paymentService.paymentSuccessUpdate(contract.getContractNo(), null);
									    		
									    		
								    		}
								    		
								    		if ("赎回资金".equals(payments.getSubject())) {
								    			//科目是赎回资金，提前撤资，需要做一些特殊处理
								    			matchResultService.lendDivest(payments.getContractNo());
								    		}							    		
										
							    		} else if(!"5".equals(payments.getState())&& inoutFlag.equals("1")) {
											//处理未成功的代收
											payments.setState("5");
								    		payments.setReason("");
								    		payments.setUpdateTime(new Timestamp(System.currentTimeMillis()));							    		
								    		paymentService.update(payments);
								    		//出借资金的情况
								    		if(payments.getContractNo().startsWith("L86")) {
						    					// 判断金额是否相等，如果不等 可能批处理 已经改变了状态，需要进行人工处理。
						    					if(logTmp.getAmount()!=payments.getActualAmount()){
						    						payments.setState("10");
						    						paymentService.update(payments);
						    					}
						    					//判断 是否是 出借方  资金入账 入账成功后进入自动撮合流程
						    					String appId=payments.getContractNo();
					    						Map<String, Object> appMap=new HashMap<String, Object>();
					    						appMap.put("appId", appId);
					    						List<LendApp> lendAppList=lendAppService.queryList(appMap);
					    						LendApp lendApp=lendAppList.get(0);
					    						List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
					    						BpmTask bpmTask=taskList.get(0);
					    						bpmTask=processService.goNext(bpmTask,BpmConstants.SYSTEM_SYS);
					    						Product pt=productService.queryByName(lendApp.getProduct());
					    						long timeLong=System.currentTimeMillis();
					    						//放入撮合队列    》》自动撮合
					    						Lend2match l2=new Lend2match();
					    						l2.setLendAppId(appId);
					    						l2.setType("1"); // 1 新增  2差额
					    						l2.setMatchType("0");	//撮合类型 为0 自动
					    						l2.setAppTime(new Timestamp(timeLong));
					    						l2.setLendAmount(lendApp.getAmount());
					    						l2.setLendProduct(lendApp.getProduct());
					    						l2.setLendPeriod(pt.getPeriod());
					    						l2.setLendInterestRate(pt.getInterestRate());
					    						l2.setLendServiceRate(pt.getSreviceFeeRate());
					    						l2.setOrgId(lendApp.getOrgId());
					    						l2.setState("1");//初始状态 带撮合
					    						l2.setOrgId2("");
					    						l2.setCreateTime(new Timestamp(timeLong));
					    						l2.setUpdateTime(new Timestamp(timeLong));
					    						//计算Lend2match的结束时间，一旦支付成功，3个工作日开始计息，出借申请的结束日期不变
					    						Date dt = workdayService.afterWorkDay(l2.getCreateTime(), 4);
					    						l2.setValueDate(dt);
					    						dt = DateUtils.addMonth(dt, l2.getLendPeriod());
					    						dt = DateUtils.addDay(dt, -1);
					    						l2.setEndDate(dt); //供提前赎回使用
					    						lend2matchService.add(l2);
					    						// 计算还款  支付  添加还款 数据表
					    						paymentService.addInterest2LendPlan(lendApp.getAppId());
					    						log.info(l2);
						    					//记录 记账明细
						    					accounttingService.accountting(payments);
								    		} else {//还款代收的情况
								    			
												Map<String, Object> map1=new HashMap<String, Object>();
												map1.put("contractNo", payments.getContractNo());
												map1.put("periodNum", payments.getPeriodNum());
												
												//查询当期还款计划
												List<LoanRepayPlan> listLoanRepayPlans=loanRepayPlanService.queryList(map1);
												LoanRepayPlan loanRepayPlan=listLoanRepayPlans.get(0);
												
												//分析还款计划  
												loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan);
												//记账
												accounttingService.repayPlanAccountting(loanRepayPlan);
												
												//更新合同状态
												contractService.ifContractEnd(payments.getContractNo());
								    		}							    		
								    		
										}
					    			}
								}else {
									//交易失败
					    			logTmp.setState("6");
					    			logTmp.setReason(qtDetail.getERR_MSG());
					    			logTmp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					    			thirdPaymentService.update(logTmp);
					    			//更新payment
					    			Payment payments=new Payment();
						    		payments=paymentService.queryByKey(logTmp.getPaymentId());
						    		payments.setState("6");
						    		payments.setReason(qtDetail.getERR_MSG());
						    		payments.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						    		
						    		paymentService.update(payments);
						    		//信用 借贷 处理申请 state
					    			if (inoutFlag.equals("2")) {
					    				Map<String, Object> mapCon=new HashMap<String, Object>();
							    		mapCon.put("contractNo", payments.getContractNo());
							    		mapCon.put("state", "1");
							    		List<Contract>listContracts=contractService.queryList(mapCon);
							    		if (listContracts.size()==0) {
							    			continue;
							    		}
							    		Contract contract=listContracts.get(0);
							    		String loanAppId=contract.getLoanAppId().trim();
							    		//信用贷 更改申请ID
							    		if(loanAppId.startsWith("C")) {
							    			Map<String, Object> appMap = new HashMap<String, Object>();
								    		appMap.put("appId", loanAppId);
								    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
								    		creditApp.setState("22");
								    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
								    		creditAppService.update(creditApp);
							    		}
					    			}
							    }	
		    				
							}
				    		
				    		
				    	} catch (Exception e) {
//	    					BatchErrorLog errorLog=new BatchErrorLog();
//	    					errorLog.setErrorMsg(e.getMessage());
//	    					errorLog.setJobName("paymentQueryJob");
//	    					errorLog.setBizKey("记录所在表为t_third_payment_log；orderNo为"+strId+"SN为"+strSn);
//	    					errorLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
//	    					batchErrorLogDao.add(errorLog);
//	    					throw e;
				    		e.printStackTrace();
						}
				    }	
			    }	
		    }
		}
	}				
	
	
	

	/**
	 * 组装报文头部
	 * @param trxcod
	 * @return
	 *日期：Sep 9, 2012
	 */
	public InfoReq makeReq(String trxcod)
	{
		
		Parameter parameter=parameterDao.queryByParamName("certpath");
		strPfxPath=parameter.getParamValue().trim()+AllinpayConstant.pfxPath;
		strTltcerPath=parameter.getParamValue().trim()+AllinpayConstant.tltcerPath;
		
		InfoReq info=new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(AllinpayConstant.merchantIdPay+"-"+String.valueOf(System.currentTimeMillis()));
		
		//info.setREQ_SN("200604000000445-1404835325302");
		info.setUSER_NAME(AllinpayConstant.userName);
		info.setUSER_PASS(AllinpayConstant.password);
		info.setMERCHANT_ID(AllinpayConstant.merchantIdPay);
		info.setDATA_TYPE("2");
		info.setVERSION("03");
		return info;
	}
	
	/**
	 * 报文签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public String signMsg(String xml) throws Exception{
		xml=XmlTools.signMsg(xml, strPfxPath,AllinpayConstant.pfxPassword, false);
		return xml;
	}
	
	/**
	 * 验证签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
		 boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
		//System.out.println("验签结果["+flag+"]") ;
		log.info("验签结果["+flag+"]") ;
		return flag;
	}

}
