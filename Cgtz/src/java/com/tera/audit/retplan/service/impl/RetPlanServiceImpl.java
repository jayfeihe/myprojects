package com.tera.audit.retplan.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.net.aso.p;

import org.apache.commons.collections.set.MapBackedSet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.tera.audit.account.model.BillAcct;
import com.tera.audit.account.model.BillInfo;
import com.tera.audit.account.model.MarginInfo;
import com.tera.audit.account.service.IBillAcctService;
import com.tera.audit.account.service.IMarginInfoService;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.retplan.dao.RetPlanDao;
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.feature.cllt.model.ClltDistr;
import com.tera.feature.cllt.service.IClltDistrService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

/**
 * 
 * 还款计划表服务类
 * <b>功能：</b>RetPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 12:02:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("retPlanService")
public class RetPlanServiceImpl implements IRetPlanService{

	private final static Logger log = Logger.getLogger(RetPlanServiceImpl.class);

	@Autowired(required=false)
    private RetPlanDao dao;
	@Autowired(required=false)
    private IContractService contractService;
	@Autowired(required=false)
    private ILoanBaseService loanBaseService;
	
	@Autowired(required=false)
    private IClltDistrService clltDistrService;
	
	@Autowired(required=false)
    private IBillAcctService billAcctService;
	
	@Autowired(required=false)
    private  IMarginInfoService marginInfoService;
	
	
	@Override
	@Transactional
	public void add(RetPlan... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RetPlan obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(RetPlan obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(RetPlan obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<RetPlan> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public RetPlan queryByKey(Object id) throws Exception {
		return (RetPlan)dao.queryByKey(id);
	}
	
	/**
	 * 根据合同号生成还款计划
	 * 调用之前，保证合同信息完整，合同号，开始和结束时间
	 * 适用于纯线下，债权转让两种，一个申请对应一个合同
	 * @param contractId
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public void createRetPlan(String contractId) throws Exception {
		//
		//周期方案 01月付 02季付03首付04末付
		//retway 01利息等额  02利息先部分后等额
		Map<String, Object> mapCon=new HashMap<String, Object>();
		mapCon.put("contractId", contractId);
		List<Contract> listContracts=contractService.queryList(mapCon);
		if (listContracts==null||listContracts.size()==0) {
			return;
		}
		Map<String, Object> mapPlan=new HashMap<String, Object>();
		mapPlan.put("contractId", contractId);
		List<RetPlan> listPlans=dao.queryList(mapPlan);
		if (listPlans.size()>0) {//有记录
			return;
		}
		
		Contract contract=listContracts.get(0);
		mapCon.clear();
		mapCon.put("loanId", contract.getLoanId());
		List<LoanBase> listBases=loanBaseService.queryList(mapCon);
		if (listBases==null||listBases.size()==0) {
			return;
		}
		
		LoanBase loanBase=listBases.get(0);
		if ("".equals(loanBase.getRetLoanSol())) {
			return;
		}
		//标识是否利息等额
		boolean isEqu=true;
		if ("02".equals(loanBase.getRetWay())) {
			isEqu=false;//先部分后等额
		}
		Date now =contract.getStartDate();//合同开始时间
		/**
		 * 月付或者季付    
		 */
		if ("01".equals(loanBase.getRetLoanSol())||"02".equals(loanBase.getRetLoanSol())) {//月付
			int mon=1;  //默认周期为1  ，每月收息
			if ("02".equals(loanBase.getRetLoanSol())) {
				mon=3;//季付   三个月为一周期
			}
			//第一个月  确定还款日 
			
			int day=DateUtils.getDay(now);
			int month=DateUtils.getMonth(now);
			int year=DateUtils.getYear(now);
			Date secRetDate=DateUtils.getDate(year, month, 20);//本月20日
			if (day>15) { //当月十五号之后下月付息不包含15号
				secRetDate=DateUtils.addMonth(secRetDate, mon);//增加一个周期
			}else {
				secRetDate=DateUtils.addMonth(secRetDate, mon-1);//增加一个周期少一月
			}
			
//			//确定最后一个还息日
//			 day=DateUtils.getDay(contract.getEndDate());
//			 month=DateUtils.getMonth(contract.getEndDate());
//			 year=DateUtils.getYear(contract.getEndDate());
//			 Date lastRetDate=DateUtils.getDate(year, month, 20);
//			 if (day<=20) {
//				 lastRetDate=DateUtils.addMonth(lastRetDate,0-mon);
//			}
			
//			 //第二次还款日大于截止日期，一共只有一期
//			 if (DateUtils.compareDate(secRetDate, contract.getEndDate())>=0) {
//				//计息天数为合同天数，还息一期  开始时间为放款日
//				 //还本金一条
//				 
//			}
			 
			//第一期 计息天数
			int dayRange=0;
			//和结束日期做对比，是否超过
			if ( DateUtils.compareDate(contract.getEndDate(), secRetDate)>0) {//区间内
				dayRange=DateUtils.getDayRange(now, secRetDate)+1;
			}else {
				dayRange=DateUtils.getDayRange(now, contract.getEndDate())+1;
			}
			double firstInt=0;
			double dayInt=0;//算出每天的利息
			if (!isEqu) {
				//先计算所有的差额利息一次性收取
				firstInt=(DateUtils.getDayRange(now, contract.getEndDate())+1)*loanBase.getLoanAmt()*(loanBase.getRate()-loanBase.getDeRate())/(loanBase.getInteDays()*100);
				//算出每天的利息===*****************=========
				dayInt=contract.getLoanAmt()*loanBase.getDeRate()/(loanBase.getInteDays()*100);
			}else {
				dayInt=contract.getLoanAmt()*loanBase.getRate()/(loanBase.getInteDays()*100);
			}
			RetPlan firstPlan=new RetPlan();
			firstPlan.setLoanId(loanBase.getLoanId());
			firstPlan.setContractId(contractId);
			firstPlan.setRetDate(now);
			firstPlan.setNum(1);
			firstPlan.setType("1");//利息
			firstPlan.setPlanInterest(dayInt*dayRange+firstInt);
			if (contract.getConIndex()<2) {//对于拆分的情况，不需要记录除利息外的费用
				firstPlan.setPlanMemFee(loanBase.getMemFee());
				firstPlan.setPlanLawFee(loanBase.getLawFee());
				firstPlan.setPlanTranFee(loanBase.getTranFee());
				firstPlan.setPlanMargin(loanBase.getMagin());
				firstPlan.setPlanOtherFee(loanBase.getOtherFee());
				firstPlan.setPlanMgrFee(loanBase.getMgrFee());
				firstPlan.setPlanAgentFee(loanBase.getAgentFee());
				firstPlan.setPlanCertFee(loanBase.getCertFee());
				firstPlan.setPlanEvalFee(loanBase.getEvalFee());
			}
			
			
			firstPlan.setState("1");
			firstPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(firstPlan); //添加
			
			
			//计算中间的还息表
			int num =1;
			RetPlan midPlan=new RetPlan();
			while (DateUtils.compareDate(contract.getEndDate(), secRetDate)>0) {
				num++;//序号累加
				Date next=DateUtils.addMonth(secRetDate, mon);
				if (DateUtils.compareDate(contract.getEndDate(), next)>0) {
					dayRange=DateUtils.getDayRange(secRetDate, next);
				}else {
					dayRange=DateUtils.getDayRange(secRetDate, contract.getEndDate());//最后一期
				}
				
				midPlan.setLoanId(loanBase.getLoanId());
				midPlan.setContractId(contractId);
				midPlan.setRetDate(secRetDate);
				midPlan.setNum(num);
				midPlan.setType("1");//利息
				midPlan.setPlanInterest(dayInt*dayRange);
				midPlan.setState("1");
				midPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
				dao.add(midPlan);
				secRetDate=next;//增加一个周期
			}
			//记录合同中多少期
			contract.setNum(num);
			contractService.update(contract);
			//还本金
			RetPlan cashPlan=new RetPlan();
			
			cashPlan.setLoanId(loanBase.getLoanId());
			cashPlan.setContractId(contractId);
			cashPlan.setRetDate(contract.getEndDate());//合同结束那天
			cashPlan.setNum(999);//本金期
			cashPlan.setType("2");//本金
			cashPlan.setPlanCapital(contract.getLoanAmt());
			cashPlan.setState("1");
			cashPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(cashPlan);
			
		}
		
		/**
		 * ===============首付===============
		 * **/
		if ("03".equals(loanBase.getRetLoanSol())) {//首付
			double dbinte=(DateUtils.getDayRange(now, contract.getEndDate())+1)*contract.getLoanAmt()*loanBase.getRate()/(loanBase.getInteDays()*100);
			
			RetPlan firstPlan=new RetPlan();
			firstPlan.setLoanId(loanBase.getLoanId());
			firstPlan.setContractId(contractId);
			firstPlan.setRetDate(now);
			firstPlan.setNum(1);
			firstPlan.setType("1");//利息
			firstPlan.setPlanInterest(dbinte);
			if (contract.getConIndex()<2) {//对于拆分的情况，不需要记录除利息外的费用
				firstPlan.setPlanMemFee(loanBase.getMemFee());
				firstPlan.setPlanLawFee(loanBase.getLawFee());
				firstPlan.setPlanTranFee(loanBase.getTranFee());
				firstPlan.setPlanMargin(loanBase.getMagin());
				firstPlan.setPlanOtherFee(loanBase.getOtherFee());
				firstPlan.setPlanMgrFee(loanBase.getMgrFee());
				firstPlan.setPlanAgentFee(loanBase.getAgentFee());
				firstPlan.setPlanCertFee(loanBase.getCertFee());
				firstPlan.setPlanEvalFee(loanBase.getEvalFee());
			}
			
			firstPlan.setState("1");
			firstPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(firstPlan); //添加
			

			//还本金
			RetPlan cashPlan=new RetPlan();
			
			cashPlan.setLoanId(loanBase.getLoanId());
			cashPlan.setContractId(contractId);
			cashPlan.setRetDate(contract.getEndDate());//合同结束那天
			cashPlan.setNum(999);
			cashPlan.setType("2");//本金
			cashPlan.setPlanCapital(loanBase.getLoanAmt());
			cashPlan.setState("1");
			cashPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(cashPlan);
			
		}
		/**
		 * ===============末付===============
		 * **/
		if ("04".equals(loanBase.getRetLoanSol())) {//末付
			double dbinte=(DateUtils.getDayRange(now, contract.getEndDate())+1)*contract.getLoanAmt()*loanBase.getRate()/(loanBase.getInteDays()*100);
			
			RetPlan firstPlan=new RetPlan();
			firstPlan.setLoanId(loanBase.getLoanId());
			firstPlan.setContractId(contractId);
			firstPlan.setRetDate(contract.getEndDate());
			firstPlan.setNum(1);
			firstPlan.setType("1");//利息
			firstPlan.setPlanInterest(dbinte);
			if (contract.getConIndex()<2) {//对于拆分的情况，不需要记录除利息外的费用
				firstPlan.setPlanMemFee(loanBase.getMemFee());
				firstPlan.setPlanLawFee(loanBase.getLawFee());
				firstPlan.setPlanTranFee(loanBase.getTranFee());
				firstPlan.setPlanMargin(loanBase.getMagin());
				firstPlan.setPlanOtherFee(loanBase.getOtherFee());
				firstPlan.setPlanMgrFee(loanBase.getMgrFee());
				firstPlan.setPlanAgentFee(loanBase.getAgentFee());
				firstPlan.setPlanCertFee(loanBase.getCertFee());
				firstPlan.setPlanEvalFee(loanBase.getEvalFee());
			}
			
			firstPlan.setState("1");
			firstPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(firstPlan); //添加
			

			//还本金
			RetPlan cashPlan=new RetPlan();
			
			cashPlan.setLoanId(loanBase.getLoanId());
			cashPlan.setContractId(contractId);
			cashPlan.setRetDate(contract.getEndDate());//合同结束那天
			cashPlan.setNum(999);
			cashPlan.setType("2");//本金
			cashPlan.setPlanCapital(loanBase.getLoanAmt());
			cashPlan.setState("1");
			cashPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(cashPlan);
		}

	}
//	/**
//	 * 直投模式，生成还款计划，涉及到合同的拆分
//	 * @throws Exception 
//	 * 
//	 */
//	public void createRetPlanSplit(String contractId) throws Exception {
//		Map<String, Object> mapCon=new HashMap<String, Object>();
//		mapCon.put("contractId", contractId);
//		List<Contract> listContracts=contractService.queryList(mapCon);
//		if (listContracts==null||listContracts.size()==0) {
//			return;
//		}
//		Contract contract=listContracts.get(0);
//		if (!"2".equals(contract.getState())) {//不是合同中的状态
//		    return;	
//		}
//		
//		if (contract.getConIndex()<2) {
//			createRetPlan(contractId);
//		}else {//属于拆分，但不是第一个合同
//			
//		}
//		
//	}
	
	/**
	 * 收利息操作
	 * @param contractId
	 * @param amt 利息以及第一期的其他费用金额
	 * @param retnum    还款期数
	 * @param defaultfee  罚息
	 * @param penaltyfee  滞纳金
	 * @param storfee   仓储费
	 * @param otherfee  其他费用
	 * @param payDate  还款日期
	 * @throws Exception
	 */

	@Override
	@Transactional
	public void repaymentInt(BillInfo info) throws Exception{
		String contractId=info.getContractId();
		double amt=info.getIntAmt();
		int retnum =info.getRetnum();
		double defaultfee=info.getDefaultFee();
		double penaltyfee=info.getPenaltyFee();
		double storfee=info.getStorFee();
		double otherfee=info.getOtherFee();
		String payTime=info.getPayTime();
		
		
		if (contractId==null ||"".equals(contractId)) {
			return;
		}
		Date dtPay=DateUtils.getDateNow();
		Date dtTime=DateUtils.getTime(payTime);
		if (payTime!=null &&!"".equals(payTime)) {  //日期不能晚于当前日期
			Date dtTime1=DateUtils.getTime(payTime);
			if (DateUtils.compareDate(DateUtils.getDateNow(), DateUtils.getDate(DateUtils.toDateString(dtTime1)))>0) {//晚于还款日，逾期
				
				dtPay=DateUtils.getDate(DateUtils.toDateString(dtTime1));
				
			}
			
		}
		Map<String, Object> mapPlan=new HashMap<String, Object>();
		mapPlan.put("contractId", contractId);
		mapPlan.put("state", "1");
		mapPlan.put("type", "1");
		mapPlan.put("orderNum", "1");
		//获取未结清的记录
		List<RetPlan> listPlans=dao.queryList(mapPlan);
		if (listPlans==null||listPlans.size()==0) {//数据有问题
			log.info("调用方法repaymentInt,合同号"+contractId+"数据有问题！");
			return;
		}
		//记账信息
		BillAcct acct = new BillAcct();
		acct.setContractId(contractId);
		acct.setRemark(info.getRemarks());
		acct.setType("1"); // 收
		acct.setProof(info.getProof());
		acct.setOperTime(new Timestamp(dtTime.getTime()));
		acct.setOperUid(info.getLoginId());
		//逐条的冲抵未还清的
		for (RetPlan retPlan : listPlans) {
			acct.setLoanId(retPlan.getLoanId());//记录申请号
			if (amt<=0) {
				break;//跳出
			}
			if (retPlan.getNum()==1) {//第一期
				//应还金额
				double dbtmp=MathUtils.add(retPlan.getPlanInterest(),MathUtils.add(retPlan.getPlanLawFee(),MathUtils.add(retPlan.getPlanMargin(),MathUtils.add(retPlan.getPlanMemFee(), MathUtils.add(retPlan.getPlanOtherFee(), MathUtils.add(retPlan.getPlanTranFee(), MathUtils.add(retPlan.getPlanMgrFee(), MathUtils.add(retPlan.getPlanCertFee(), MathUtils.add(retPlan.getPlanEvalFee(), retPlan.getPlanAgentFee())))))))) );
				//实还金额
				double dbReal=MathUtils.add(retPlan.getRealInterest(),MathUtils.add(retPlan.getRealLawFee(),MathUtils.add(retPlan.getRealMargin(),MathUtils.add(retPlan.getRealMemFee(), MathUtils.add(retPlan.getRealOtherFee(), MathUtils.add(retPlan.getRealTranFee(), MathUtils.add(retPlan.getRealMgrFee(), MathUtils.add(retPlan.getRealCertFee(), MathUtils.add(retPlan.getRealEvalFee(), retPlan.getRealAgentFee())))))))) );
				dbtmp=MathUtils.sub(dbtmp, dbReal);
				if (MathUtils.sub(amt, dbtmp)>=0) {//可以结清
					amt=MathUtils.sub(amt, dbtmp);
					if (MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest()));
						acct.setSubject("收利息"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealInterest(retPlan.getPlanInterest());
					if (MathUtils.sub(retPlan.getPlanLawFee(), retPlan.getRealLawFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanLawFee(), retPlan.getRealLawFee()));
						acct.setSubject("收法律服务费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealLawFee(retPlan.getPlanLawFee());
					if (MathUtils.sub(retPlan.getPlanMargin(), retPlan.getRealMargin())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanMargin(), retPlan.getRealMargin()));
						acct.setSubject("收保证金"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
						
						//保证金信息表中记录收取的保证金,全部收齐的时候才记录
						MarginInfo marginInfo=new MarginInfo();
						
						marginInfo.setContractId(contractId);
						marginInfo.setAmt(retPlan.getPlanMargin());
						marginInfo.setGetTime(new Timestamp(dtTime.getTime()));
						marginInfo.setState("1");//未处理
						marginInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
						marginInfoService.add(marginInfo);
					}
					retPlan.setRealMargin(retPlan.getPlanMargin());
					if (MathUtils.sub(retPlan.getPlanMemFee(), retPlan.getRealMemFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanMemFee(), retPlan.getRealMemFee()));
						acct.setSubject("收会员服务费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealMemFee(retPlan.getPlanMemFee());
					if (MathUtils.sub(retPlan.getPlanAgentFee(), retPlan.getRealAgentFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanAgentFee(), retPlan.getRealAgentFee()));
						acct.setSubject("收中介费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealAgentFee(retPlan.getPlanAgentFee());
					if (MathUtils.sub(retPlan.getPlanEvalFee(), retPlan.getRealEvalFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanEvalFee(), retPlan.getRealEvalFee()));
						acct.setSubject("收评估费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealEvalFee(retPlan.getPlanEvalFee());
					if (MathUtils.sub(retPlan.getPlanMgrFee(), retPlan.getRealMgrFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanMgrFee(), retPlan.getRealMgrFee()));
						acct.setSubject("收管理服务费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealMgrFee(retPlan.getPlanMgrFee());
					if (MathUtils.sub(retPlan.getPlanCertFee(), retPlan.getRealCertFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanCertFee(), retPlan.getRealCertFee()));
						acct.setSubject("收他项权证费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealCertFee(retPlan.getPlanCertFee());
					if (MathUtils.sub(retPlan.getPlanOtherFee(), retPlan.getRealOtherFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanOtherFee(), retPlan.getRealOtherFee()));
						acct.setSubject("收贷前其他费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealOtherFee(retPlan.getPlanOtherFee());
					if (MathUtils.sub(retPlan.getPlanTranFee(), retPlan.getRealTranFee())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanTranFee(), retPlan.getRealTranFee()));
						acct.setSubject("收转贷费"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealTranFee(retPlan.getPlanTranFee());
					retPlan.setLastDate(dtPay);
					//判断是否逾期还清
					if (DateUtils.compareDate(retPlan.getRetDate(), dtPay)<0) {//晚于还款日，逾期
						retPlan.setState("5");//逾期结清
					}else {
						retPlan.setState("2");//结清
					}
					
					retPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					dao.update(retPlan);
				}else {
					if (MathUtils.sub(amt,MathUtils.sub(retPlan.getPlanLawFee(), retPlan.getRealLawFee()) )>0) {
						amt=MathUtils.sub(amt,MathUtils.sub(retPlan.getPlanLawFee(), retPlan.getRealLawFee()) );//法律服务费
						if (MathUtils.sub(retPlan.getPlanLawFee(), retPlan.getRealLawFee())>0) {
							acct.setAmt(MathUtils.sub(retPlan.getPlanLawFee(), retPlan.getRealLawFee()));
							acct.setSubject("收法律服务费"); 
							acct.setNum("1"); // 第一期
							billAcctService.add(acct);
						}
						retPlan.setRealLawFee( retPlan.getPlanLawFee());
						if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanMargin(), retPlan.getRealMargin()))>0) {
							amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanMargin(), retPlan.getRealMargin()));//保证金
							if (MathUtils.sub(retPlan.getPlanMargin(), retPlan.getRealMargin())>0) {
								acct.setAmt(MathUtils.sub(retPlan.getPlanMargin(), retPlan.getRealMargin()));
								acct.setSubject("收保证金"); 
								acct.setNum("1"); // 第一期
								billAcctService.add(acct);
								
								//保证金信息表中记录收取的保证金
								MarginInfo marginInfo=new MarginInfo();
								
								marginInfo.setContractId(contractId);
								marginInfo.setAmt(retPlan.getPlanMargin());
								marginInfo.setGetTime(new Timestamp(dtTime.getTime()));
								marginInfo.setState("1");//未处理
								marginInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
								marginInfoService.add(marginInfo);
								
							}
							retPlan.setRealMargin(retPlan.getPlanMargin());
							if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanMemFee(), retPlan.getRealMemFee()))>0) {
								amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanMemFee(), retPlan.getRealMemFee()));//会员服务费
								if (MathUtils.sub(retPlan.getPlanMemFee(), retPlan.getRealMemFee())>0) {
									acct.setAmt(MathUtils.sub(retPlan.getPlanMemFee(), retPlan.getRealMemFee()));
									acct.setSubject("收会员服务费"); 
									acct.setNum("1"); // 第一期
									billAcctService.add(acct);
								}
								retPlan.setRealMemFee(retPlan.getPlanMemFee());
								if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanOtherFee(), retPlan.getRealOtherFee()))>0) {
									amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanOtherFee(), retPlan.getRealOtherFee()));//其他费用服务费
									if (MathUtils.sub(retPlan.getPlanOtherFee(), retPlan.getRealOtherFee())>0) {
										acct.setAmt(MathUtils.sub(retPlan.getPlanOtherFee(), retPlan.getRealOtherFee()));
										acct.setSubject("收贷前其他费"); 
										acct.setNum("1"); // 第一期
										billAcctService.add(acct);
									}
									retPlan.setRealOtherFee(retPlan.getPlanOtherFee());
									if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanTranFee(), retPlan.getRealTranFee()))>0) {
										amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanTranFee(), retPlan.getRealTranFee()));//转贷费
										if (MathUtils.sub(retPlan.getPlanTranFee(), retPlan.getRealTranFee())>0) {
											acct.setAmt(MathUtils.sub(retPlan.getPlanTranFee(), retPlan.getRealTranFee()));
											acct.setSubject("收转贷费"); 
											acct.setNum("1"); // 第一期
											billAcctService.add(acct);
										}
										retPlan.setRealTranFee(retPlan.getPlanTranFee());
										if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanMgrFee(), retPlan.getRealMgrFee()))>0) {
											amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanMgrFee(), retPlan.getRealMgrFee()));//管理服务费
											if (MathUtils.sub(retPlan.getPlanMgrFee(), retPlan.getRealMgrFee())>0) {
												acct.setAmt(MathUtils.sub(retPlan.getPlanMgrFee(), retPlan.getRealMgrFee()));
												acct.setSubject("收管理服务费"); 
												acct.setNum("1"); // 第一期
												billAcctService.add(acct);
											}
											retPlan.setRealMgrFee(retPlan.getPlanMgrFee());
											if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanCertFee(), retPlan.getRealCertFee()))>0) {
												amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanCertFee(), retPlan.getRealCertFee()));//他项权证费
												if (MathUtils.sub(retPlan.getPlanCertFee(), retPlan.getRealCertFee())>0) {
													acct.setAmt(MathUtils.sub(retPlan.getPlanCertFee(), retPlan.getRealCertFee()));
													acct.setSubject("收他项权证费"); 
													acct.setNum("1"); // 第一期
													billAcctService.add(acct);
												}
												retPlan.setRealCertFee(retPlan.getPlanCertFee());
												if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanEvalFee(), retPlan.getRealEvalFee()))>0) {
													amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanEvalFee(), retPlan.getRealEvalFee()));//评估费
													if (MathUtils.sub(retPlan.getPlanEvalFee(), retPlan.getRealEvalFee())>0) {
														acct.setAmt(MathUtils.sub(retPlan.getPlanEvalFee(), retPlan.getRealEvalFee()));
														acct.setSubject("收评估费"); 
														acct.setNum("1"); // 第一期
														billAcctService.add(acct);
													}
													retPlan.setRealEvalFee(retPlan.getPlanEvalFee());
													if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanAgentFee(), retPlan.getRealAgentFee()))>0) {
														amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanAgentFee(), retPlan.getRealAgentFee()));//中介费
														if (MathUtils.sub(retPlan.getPlanAgentFee(), retPlan.getRealAgentFee())>0) {
															acct.setAmt(MathUtils.sub(retPlan.getPlanAgentFee(), retPlan.getRealAgentFee()));
															acct.setSubject("收中介费"); 
															acct.setNum("1"); // 第一期
															billAcctService.add(acct);
														}
														retPlan.setRealAgentFee(retPlan.getPlanAgentFee());
														if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest()))>0) {
															amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest()));
															if (MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest())>0) {
																acct.setAmt(MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest()));
																acct.setSubject("收利息"); 
																acct.setNum("1"); // 第一期
																billAcctService.add(acct);
															}
															retPlan.setRealInterest(retPlan.getPlanInterest());//还利息
														}else {
															if (amt>0) {
																acct.setAmt(amt);
																acct.setSubject("收利息"); 
																acct.setNum("1"); // 第一期
																billAcctService.add(acct);
															}
															retPlan.setRealInterest(MathUtils.add(retPlan.getRealInterest(), amt));
														}
													}else {
														if (amt>0) {
															acct.setAmt(amt);
															acct.setSubject("收中介费"); 
															acct.setNum("1"); // 第一期
															billAcctService.add(acct);
														}
														retPlan.setRealAgentFee(MathUtils.add(retPlan.getRealAgentFee(), amt));
													}
												}else {
													if (amt>0) {
														acct.setAmt(amt);
														acct.setSubject("收评估费"); 
														acct.setNum("1"); // 第一期
														billAcctService.add(acct);
													}
													retPlan.setRealEvalFee(MathUtils.add(retPlan.getRealEvalFee(), amt));
												}
											}else {
												if (amt>0) {
													acct.setAmt(amt);
													acct.setSubject("收他项权证费"); 
													acct.setNum("1"); // 第一期
													billAcctService.add(acct);
												}
												retPlan.setRealCertFee(MathUtils.add(retPlan.getRealCertFee(), amt));
											}
										}else {
											if (amt>0) {
												acct.setAmt(amt);
												acct.setSubject("收管理服务费"); 
												acct.setNum("1"); // 第一期
												billAcctService.add(acct);
											}
											retPlan.setRealMgrFee(MathUtils.add(retPlan.getRealMgrFee(), amt));
										}
									}else {
										if (amt>0) {
											acct.setAmt(amt);
											acct.setSubject("收转贷费"); 
											acct.setNum("1"); // 第一期
											billAcctService.add(acct);
										}
										retPlan.setRealTranFee(MathUtils.add(retPlan.getRealTranFee(), amt));
									}
								}else {
									if (amt>0) {
										acct.setAmt(amt);
										acct.setSubject("收贷前其他费"); 
										acct.setNum("1"); // 第一期
										billAcctService.add(acct);
									}
									retPlan.setRealOtherFee(MathUtils.add(retPlan.getRealOtherFee(), amt));
								}
							}else {
								if (amt>0) {
									acct.setAmt(amt);
									acct.setSubject("收会员服务费"); 
									acct.setNum("1"); // 第一期
									billAcctService.add(acct);
								}
								retPlan.setRealMemFee(MathUtils.add(retPlan.getRealMemFee(), amt));
							}
						}else {
							if (amt>0) {
								acct.setAmt(amt);
								acct.setSubject("收保证金"); 
								acct.setNum("1"); // 第一期
								billAcctService.add(acct);
							}
							retPlan.setRealMargin(MathUtils.add(retPlan.getRealMargin(), amt));
						}
					}else {
						if (amt>0) {
							acct.setAmt(amt);
							acct.setSubject("收法律服务费"); 
							acct.setNum("1"); // 第一期
							billAcctService.add(acct);
						}
						retPlan.setRealLawFee(MathUtils.add(retPlan.getRealLawFee(), amt));
					}
					
					amt=0;
					retPlan.setLastDate(dtPay);
					retPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					dao.update(retPlan);//更新数据
				}
			}else {//不是第一期，只需要冲抵利息
				if (MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest()))>0) {
					amt=MathUtils.sub(amt, MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest()));
					if (MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest())>0) {
						acct.setAmt(MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest()));
						acct.setSubject("收利息"); 
						acct.setNum(String.valueOf(retPlan.getNum())); 
						billAcctService.add(acct);
					}
					retPlan.setRealInterest(retPlan.getPlanInterest());//还利息
					//判断是否逾期还清
					if (DateUtils.compareDate(retPlan.getRetDate(), dtPay)<0) {//晚于还款日，逾期
						retPlan.setState("5");//逾期结清
					}else {
						retPlan.setState("2");//结清
					}
					
				}else {
					if (MathUtils.sub(retPlan.getPlanInterest(), retPlan.getRealInterest())>0) {
						acct.setAmt(amt);
						acct.setSubject("收利息"); 
						acct.setNum("1"); // 第一期
						billAcctService.add(acct);
					}
					retPlan.setRealInterest(MathUtils.add(retPlan.getRealInterest(), amt));
					amt=0;
				}
				retPlan.setLastDate(dtPay);
				retPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				dao.update(retPlan);//更新数据
			}
		}
		if (amt>0) {
			//如果还有剩余，冲抵本金！！
			mapPlan.clear();
			mapPlan.put("contractId", contractId);
			mapPlan.put("type", "2");// 本金
			
			//获取未结清的记录
			List<RetPlan> listPlans2=dao.queryList(mapPlan);
			if (listPlans2==null||listPlans2.size()==0) {
				return;
			}
			
			RetPlan retCash=listPlans2.get(0);
			if ("1".equals(retCash.getState())) {//未还清
				if (MathUtils.sub(amt, MathUtils.sub(retCash.getPlanCapital(), retCash.getRealCapital()))>0) {
					if (amt>0) {
						acct.setAmt(amt);
						acct.setSubject("收本金"); 
						acct.setNum(""); // 
						billAcctService.add(acct);
					}
					
					retCash.setRealCapital(MathUtils.add(amt, retCash.getRealCapital()));//记录实际收取的金额
					//判断是否逾期还清
					if (DateUtils.compareDate(retCash.getRetDate(),dtPay)<0) {//晚于还款日，逾期
						retCash.setState("5");//逾期结清
					}else {
						retCash.setState("2");//结清
					}
					retCash.setLastDate(dtPay);
					retCash.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					dao.update(retCash);//更新数据
					//合同结清
					mapPlan.clear();
					mapPlan.put("contractId", contractId);
					List<Contract> listContracts=contractService.queryList(mapPlan);
					if (listContracts!=null&&listContracts.size()>0) {
						Contract contract =listContracts.get(0);
						contract.setRealEndDate(dtPay);
						contract.setState("3");//合同结清
						contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						contractService.update(contract);
					}
					
				}else {
					if (amt>0) {
						acct.setAmt(amt);
						acct.setSubject("收本金"); 
						acct.setNum(""); // 
						billAcctService.add(acct);
					}
					retCash.setRealCapital(MathUtils.add(amt, retCash.getRealCapital()));//记录实际收取的金
					retCash.setLastDate(dtPay);
					retCash.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					dao.update(retCash);//更新数据
				}
			}
			
		}	
		//记录其他费用
	
		mapPlan.clear();
		mapPlan.put("contractId", contractId);
		mapPlan.put("num", retnum);
		List<RetPlan> listPlan3=dao.queryList(mapPlan);
		if (listPlan3!=null&&listPlan3.size()>0) {
			
			RetPlan retPlan=listPlan3.get(0);
			retPlan.setDefaultFee(MathUtils.add(retPlan.getDefaultFee(), defaultfee));
			retPlan.setPenaltyFee(MathUtils.add(retPlan.getPenaltyFee(), penaltyfee));
			//double storfee,double otherfee
			retPlan.setStorFee(MathUtils.add(retPlan.getStorFee(), storfee));
			retPlan.setLoanOtherFee(MathUtils.add(retPlan.getLoanOtherFee(), otherfee));
			retPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			dao.update(retPlan);//更新数据
			
			//记账
			if (defaultfee>0) {
				acct.setAmt(defaultfee);
				acct.setSubject("收罚息"); 
				acct.setNum(String.valueOf(retnum)); 
				billAcctService.add(acct);
			}
			if (penaltyfee>0) {
				acct.setAmt(penaltyfee);
				acct.setSubject("收滞纳金"); 
				acct.setNum(String.valueOf(retnum)); 
				billAcctService.add(acct);
			}
			if (storfee>0) {
				acct.setAmt(storfee);
				acct.setSubject("收仓储费"); 
				acct.setNum(String.valueOf(retnum)); 
				billAcctService.add(acct);
			}
			if (otherfee>0) {
				acct.setAmt(otherfee);
				acct.setSubject("收贷后其他费"); 
				acct.setNum(String.valueOf(retnum)); 
				billAcctService.add(acct);
			}
			
		}
		
		
		//逾期处理
		IsLateHandle(contractId);
	}
	
	/**
	 * 收取本金计算
	 * @param contractId 线下合同id
	 * @param amt  还款本金金额
	 * @param defaultfee 罚息金额
	 * @param penaltyfee滞纳金违约金金额
	 * @param otherfee  其他费用
	 * @param payDate  还款日期
	 * @throws Exception
	 */
	
	public void repaymentPrincipal(BillInfo info) throws Exception {
		
		String contractId=info.getContractId();
		double amt=info.getIntAmt();
		int retnum =info.getRetnum();
		double defaultfee=info.getDefaultFee();
		double penaltyfee=info.getPenaltyFee();
		double otherfee=info.getOtherFee();
		String payTime=info.getPayTime();
		
		
		if (contractId==null ||"".equals(contractId)) {
			return;
		}
		Date dtPay=DateUtils.getDateNow();
		Date dtTime=DateUtils.getTime(payTime);
		if (payTime!=null &&!"".equals(payTime)) {  //日期不能晚于当前日期
			Date dtTime1=DateUtils.getTime(payTime);
			if (DateUtils.compareDate(DateUtils.getDateNow(), DateUtils.getDate(DateUtils.toDateString(dtTime1)))>0) {//晚于还款日，逾期
				
				dtPay=DateUtils.getDate(DateUtils.toDateString(dtTime1));
				
			}
			
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.clear();
		map.put("contractId", contractId);
		map.put("type", "2");// 本金
		
		//获取未结清的记录
		List<RetPlan> listPlans2=dao.queryList(map);
		if (listPlans2==null||listPlans2.size()==0) {
			return;
		}
		
		RetPlan retCash=listPlans2.get(0);
		if ("1".equals(retCash.getState())) {//未还清
			if (MathUtils.sub(amt, MathUtils.sub(retCash.getPlanCapital(), retCash.getRealCapital()))>0) {
				retCash.setRealCapital(MathUtils.add(amt, retCash.getRealCapital()));//记录实际收取的金额
				retCash.setDefaultFee(MathUtils.add(retCash.getDefaultFee(), defaultfee));
				retCash.setPenaltyFee(MathUtils.add(retCash.getPenaltyFee(), penaltyfee));
				retCash.setLoanOtherFee(MathUtils.add(retCash.getLoanOtherFee(), otherfee));
				if (DateUtils.compareDate(retCash.getRetDate(),dtPay)<0) {//晚于还款日，逾期
					retCash.setState("5");//逾期结清
				}else {
					retCash.setState("2");//结清
				}
				
				retCash.setRetDate(dtPay);
				retCash.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				dao.update(retCash);//更新数据
				//合同结清
				//先判断是否还有未还的还款计划
				map.clear();
				map.put("contractId", contractId);
				map.put("state", "1");
				List<RetPlan> listPlans=dao.queryList(map);
				if (listPlans==null||listPlans.size()==0) {//无记录
					map.clear();
					map.put("contractId", contractId);
					List<Contract> listContracts=contractService.queryList(map);
					if (listContracts!=null&&listContracts.size()>0) {
						Contract contract =listContracts.get(0);
						contract.setRealEndDate(dtPay);
						contract.setState("3");//合同结清
						contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						contractService.update(contract);
					}
				}
			}else {
				retCash.setRealCapital(MathUtils.add(amt, retCash.getRealCapital()));//记录实际收取的金
				retCash.setDefaultFee(MathUtils.add(retCash.getDefaultFee(), defaultfee));
				retCash.setPenaltyFee(MathUtils.add(retCash.getPenaltyFee(), penaltyfee));
				retCash.setLoanOtherFee(MathUtils.add(retCash.getLoanOtherFee(), otherfee));
				retCash.setRetDate(dtPay);
				retCash.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				dao.update(retCash);//更新数据
			}
		}
		//记账信息
		BillAcct acct = new BillAcct();
		acct.setContractId(contractId);
		acct.setRemark(info.getRemarks());
		acct.setLoanId(retCash.getLoanId());
		acct.setType("1"); // 收
		acct.setProof(info.getProof());
		acct.setOperTime(new Timestamp(dtTime.getTime()));
		acct.setOperUid(info.getLoginId());
		if (amt>0) {
			acct.setAmt(amt);
			acct.setSubject("收本金"); 
			acct.setNum(""); // 
			billAcctService.add(acct);
		}
		
		//记账
		if (defaultfee>0) {
			acct.setAmt(defaultfee);
			acct.setSubject("收罚息"); 
			acct.setNum(String.valueOf(retnum)); 
			billAcctService.add(acct);
		}
		if (penaltyfee>0) {
			acct.setAmt(penaltyfee);
			acct.setSubject("收滞纳金"); 
			acct.setNum(String.valueOf(retnum)); 
			billAcctService.add(acct);
		}
		
		if (otherfee>0) {
			acct.setAmt(otherfee);
			acct.setSubject("收贷后其他费"); 
			acct.setNum(String.valueOf(retnum)); 
			billAcctService.add(acct);
		}
		
		//逾期处理
		IsLateHandle(contractId);
	}
	
	/**
	 * 还款后逾期处理
	 * @param contractid
	 * @throws Exception
	 */
	public void IsLateHandle(String contractid) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractId", contractid);
		map.put("date1", DateUtils.getDateNow());
		List<RetPlan> listPlans=dao.queryLateByCon(map);
		if (listPlans==null||listPlans.size()==0) {//没有逾期数据
			//更改合同中的逾期状态
			map.remove("date1");
			List<Contract> listCons=contractService.queryList(map);
			if (listCons!=null&&listCons.size()>0) {
				Contract contract=listCons.get(0);
				if ("1".equals(contract.getIsLate())) {
					contract.setIsLate("0");
					contract.setIsLateDis("0");
					contract.setIsCheck("0");
					contract.setCheckState("1");
					contract.setIsAccept("0");
					contract.setCheckReportState("0");
					contractService.update(contract);
					
					
					//分配表
					
					map.put("isCur", "1");
					List<ClltDistr> listDistrs=clltDistrService.queryList(map);
					for (ClltDistr clltDistr : listDistrs) {
						clltDistr.setIsCur("0");
						clltDistr.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					
						clltDistrService.update(clltDistr);
					}
				}	
			}	
		}
		
	}
	
	
	
}
