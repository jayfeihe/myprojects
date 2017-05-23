package com.tera.audit.retplan.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanCalculateService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

/**
 * 计算还款计划Service (放款前计算)
 * @author QYANZE
 *
 */
@Service("retPlanCalculateService")
public class RetPlanCalculateServiceImpl implements IRetPlanCalculateService {

	@Autowired(required=false)
    private ILoanBaseService loanBaseService;
	
	/** (non-Javadoc)
	 * @see com.tera.audit.retplan.service.impl.IRetPlanCalculateService#createRetPlan(java.lang.String, java.lang.String)
	 */
	@Override
	public List<RetPlan> createRetPlan(String loanId,String startDate) throws Exception {
		List<RetPlan> retList = new ArrayList<RetPlan>();
		if (startDate==null||"".equals(startDate)) {
			return retList;
		}
		//周期方案 01月付 02季付03首付04末付
		//retway 01利息等额  02利息先部分后等额
		Map<String, Object> mapCon=new HashMap<String, Object>();
		mapCon.put("loanId", loanId);
		List<LoanBase> listBases=loanBaseService.queryList(mapCon);
		if (listBases==null||listBases.size()==0) {
			return retList;
		}
		
		LoanBase loanBase=listBases.get(0);
		if ("".equals(loanBase.getRetLoanSol())) {
			return retList;
		}
		//标识是否利息等额
		boolean isEqu=true;
		if ("02".equals(loanBase.getRetWay())) {
			isEqu=false;//先部分后等额
		}
		Date now = DateUtils.getDate(startDate);//合同开始时间
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
			
			//第一期 计息天数
			int dayRange=0;
			//和结束日期做对比，是否超过
			if ( DateUtils.compareDate(loanBase.getEndDate(), secRetDate)>0) {//区间内
				dayRange=DateUtils.getDayRange(now, secRetDate)+1;
			}else {
				dayRange=DateUtils.getDayRange(now, loanBase.getEndDate())+1;
			}
			double firstInt=0;
			double dayInt=0;//算出每天的利息
			if (!isEqu) {
				//先计算所有的差额利息一次性收取
				firstInt=(DateUtils.getDayRange(now, loanBase.getEndDate())+1)*loanBase.getLoanAmt()*(loanBase.getRate()-loanBase.getDeRate())/(loanBase.getInteDays()*100);
				//算出每天的利息===*****************=========
				dayInt=loanBase.getLoanAmt()*loanBase.getDeRate()/(loanBase.getInteDays()*100);
			}else {
				dayInt=loanBase.getLoanAmt()*loanBase.getRate()/(loanBase.getInteDays()*100);
			}
			RetPlan firstPlan=new RetPlan();
			firstPlan.setLoanId(loanBase.getLoanId());
//			firstPlan.setContractId(contractId);
			firstPlan.setRetDate(now);
			firstPlan.setNum(1);
			firstPlan.setType("1");//利息
			firstPlan.setPlanInterest(dayInt*dayRange+firstInt);
			//对于拆分的情况，不需要记录除利息外的费用
			firstPlan.setPlanMemFee(loanBase.getMemFee());
			firstPlan.setPlanLawFee(loanBase.getLawFee());
			firstPlan.setPlanTranFee(loanBase.getTranFee());
			firstPlan.setPlanMargin(loanBase.getMagin());
			firstPlan.setPlanOtherFee(loanBase.getOtherFee());
			
			firstPlan.setPlanAmt(MathUtils.add(firstPlan.getPlanInterest(), MathUtils.add(firstPlan.getPlanMemFee(), MathUtils.add(firstPlan.getPlanLawFee(),
								MathUtils.add(firstPlan.getPlanMargin(), MathUtils.add(firstPlan.getPlanOtherFee(),firstPlan.getPlanTranFee()))))));
			
			firstPlan.setState("1");
			firstPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			retList.add(firstPlan); //添加
			
			
			//计算中间的还息表
			int num =1;
			while (DateUtils.compareDate(loanBase.getEndDate(), secRetDate)>0) {
				num++;//序号累加
				Date next=DateUtils.addMonth(secRetDate, mon);
				if (DateUtils.compareDate(loanBase.getEndDate(), next)>0) {
					dayRange=DateUtils.getDayRange(secRetDate, next);
				}else {
					dayRange=DateUtils.getDayRange(secRetDate, loanBase.getEndDate());//最后一期
				}
				RetPlan midPlan=new RetPlan();
				midPlan.setLoanId(loanBase.getLoanId());
//				midPlan.setContractId(contractId);
				midPlan.setRetDate(secRetDate);
				midPlan.setNum(num);
				midPlan.setType("1");//利息
				midPlan.setPlanInterest(dayInt*dayRange);
				midPlan.setPlanAmt(midPlan.getPlanInterest());
				midPlan.setState("1");
				midPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
				retList.add(midPlan);
				secRetDate=next;//增加一个周期
			}
			
			//还本金
			RetPlan cashPlan=new RetPlan();
			
			cashPlan.setLoanId(loanBase.getLoanId());
//			cashPlan.setContractId(contractId);
			cashPlan.setRetDate(loanBase.getEndDate());//合同结束那天
			cashPlan.setNum(999);//本金期
			cashPlan.setType("2");//本金
			cashPlan.setPlanCapital(loanBase.getLoanAmt());
			cashPlan.setPlanAmt(loanBase.getLoanAmt());
			cashPlan.setState("1");
			cashPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			retList.add(cashPlan);
			
		}
		
		/**
		 * ===============首付===============
		 * **/
		if ("03".equals(loanBase.getRetLoanSol())) {//首付
			double dbinte=(DateUtils.getDayRange(now, loanBase.getEndDate())+1)*loanBase.getLoanAmt()*loanBase.getRate()/(loanBase.getInteDays()*100);
			
			RetPlan firstPlan=new RetPlan();
			firstPlan.setLoanId(loanBase.getLoanId());
//			firstPlan.setContractId(contractId);
			firstPlan.setRetDate(now);
			firstPlan.setNum(1);
			firstPlan.setType("1");//利息
			firstPlan.setPlanInterest(dbinte);
			firstPlan.setPlanMemFee(loanBase.getMemFee());
			firstPlan.setPlanLawFee(loanBase.getLawFee());
			firstPlan.setPlanTranFee(loanBase.getTranFee());
			firstPlan.setPlanMargin(loanBase.getMagin());
			firstPlan.setPlanOtherFee(loanBase.getOtherFee());
			firstPlan.setState("1");
			firstPlan.setPlanAmt(MathUtils.add(firstPlan.getPlanInterest(), MathUtils.add(firstPlan.getPlanMemFee(), MathUtils.add(firstPlan.getPlanLawFee(),
					MathUtils.add(firstPlan.getPlanMargin(), MathUtils.add(firstPlan.getPlanOtherFee(),firstPlan.getPlanTranFee()))))));

			firstPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			retList.add(firstPlan); //添加
			

			//还本金
			RetPlan cashPlan=new RetPlan();
			
			cashPlan.setLoanId(loanBase.getLoanId());
//			cashPlan.setContractId(contractId);
			cashPlan.setRetDate(loanBase.getEndDate());//合同结束那天
			cashPlan.setNum(999);
			cashPlan.setType("2");//本金
			cashPlan.setPlanCapital(loanBase.getLoanAmt());
			cashPlan.setPlanAmt(loanBase.getLoanAmt());
			cashPlan.setState("1");
			cashPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			retList.add(cashPlan);
			
		}
		/**
		 * ===============末付===============
		 * **/
		if ("04".equals(loanBase.getRetLoanSol())) {//末付
			double dbinte=(DateUtils.getDayRange(now, loanBase.getEndDate())+1)*loanBase.getLoanAmt()*loanBase.getRate()/(loanBase.getInteDays()*100);
			
			RetPlan firstPlan=new RetPlan();
			firstPlan.setLoanId(loanBase.getLoanId());
//			firstPlan.setContractId(contractId);
			firstPlan.setRetDate(loanBase.getEndDate());
			firstPlan.setNum(1);
			firstPlan.setType("1");//利息
			firstPlan.setPlanInterest(dbinte);
			firstPlan.setPlanMemFee(loanBase.getMemFee());
			firstPlan.setPlanLawFee(loanBase.getLawFee());
			firstPlan.setPlanTranFee(loanBase.getTranFee());
			firstPlan.setPlanMargin(loanBase.getMagin());
			firstPlan.setPlanOtherFee(loanBase.getOtherFee());
			firstPlan.setState("1");
			firstPlan.setPlanAmt(MathUtils.add(firstPlan.getPlanInterest(), MathUtils.add(firstPlan.getPlanMemFee(), MathUtils.add(firstPlan.getPlanLawFee(),
					MathUtils.add(firstPlan.getPlanMargin(), MathUtils.add(firstPlan.getPlanOtherFee(),firstPlan.getPlanTranFee()))))));

			firstPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			retList.add(firstPlan); //添加
			

			//还本金
			RetPlan cashPlan=new RetPlan();
			
			cashPlan.setLoanId(loanBase.getLoanId());
//			cashPlan.setContractId(contractId);
			cashPlan.setRetDate(loanBase.getEndDate());//合同结束那天
			cashPlan.setNum(999);
			cashPlan.setType("2");//本金
			cashPlan.setPlanCapital(loanBase.getLoanAmt());
			cashPlan.setPlanAmt(loanBase.getLoanAmt());
			cashPlan.setState("1");
			cashPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			retList.add(cashPlan);
		}
		
		return retList;
	}
}
