package com.tera.payment.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.collection.phone.model.CollectionBaseInfo;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.service.CreditSignService;
import com.tera.message.constant.MessageConstant;
import com.tera.message.model.Msglog;
import com.tera.message.model.MsgTemplate;
import com.tera.message.service.MsglogService;
import com.tera.message.service.MsgTemplateService;
import com.tera.payment.dao.LoanRepayPlanDao;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

/**
 * 还款计划
 * <br>
 * <b>功能：</b>LoanRepayPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-04 13:56:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanRepayPlanService")
public class LoanRepayPlanService {

	private final static Logger log = Logger.getLogger(LoanRepayPlanService.class);

	@Autowired(required=false)
    private LoanRepayPlanDao<LoanRepayPlan> dao;
	
	@Autowired(required=false)
	private ContractService contractService;
	
	@Autowired(required=false)
	private PaymentService<Payment> paymentService;
	
	@Autowired(required=false)
	private ProductService<Product> productService;

	@Autowired(required=false) //自动注入
	CreditSignService creditSignService;

	@Autowired(required=false)
	private CollectionBaseService collectionBaseService;
	@Autowired(required=false)
	private MsgTemplateService msgTemplateService;
	@Autowired(required=false)
	private MsglogService msglogService;
	
	
	@Transactional
	public void add(LoanRepayPlan... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(LoanRepayPlan t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(LoanRepayPlan t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(LoanRepayPlan t)  throws Exception {
		dao.updateOnlyChanged(t);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<LoanRepayPlan> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public LoanRepayPlan queryByKey(Object id) throws Exception {
		return (LoanRepayPlan)dao.queryByKey(id);
	}
	
	
	/**
	 * 查询扩展
	 * @param map
	 * 		KEY
	 * 			states[]		数组 包含状态
	 * 			noStates[]		数组 不包含 状态
	 * 			existPenalty	是违约  为空 不验证
	 * @return
	 * @throws Exception
	 */
	public List<LoanRepayPlan> queryListExt(Map<String, Object> map) throws Exception {
		return dao.queryListExt(map);
	}
	
	
	
	/**
	 * 为一个合同添加还款计划
	 * @param contractNo
	 * @throws Exception 
	 */
	@Transactional
	public void addRepayPlan(String contractNo) throws Exception {
     
		Map<String, Object> mapCon=new HashMap<String, Object>();
		mapCon.put("contractNo", contractNo);
		
		List<Contract> listContracts=contractService.queryList(mapCon);
		
		if (listContracts.size()==0) {
			return;
		}
		
		Contract contract=listContracts.get(0);
		int sum=contract.getLoanPeriod()+1;
		Date dtFirst=DateUtils.addDay(DateUtils.getDateNow(), -1);
		Date dtTmp=dtFirst;
		//每期一条记录;
		for (int i = 1; i < sum; i++) {
			LoanRepayPlan loanRepayPlan=new LoanRepayPlan();
			loanRepayPlan.setContractNo(contractNo);
			loanRepayPlan.setRepayMethod(contract.getRepayMethod());
			loanRepayPlan.setRepaymentDate(nextMonth(dtTmp));
			dtTmp=loanRepayPlan.getRepaymentDate();
			loanRepayPlan.setStartDate(contract.getStartDate());
			loanRepayPlan.setEndDate(contract.getEndDate());
			loanRepayPlan.setPeriodNum(i);
			loanRepayPlan.setSreviceFeeReceivable(contract.getLoanAmount()*contract.getLoanServiceRate()/100);
			loanRepayPlan.setSreviceFeeReceived(0);
			loanRepayPlan.setInterestReceivable(contract.getLoanAmount()*contract.getLoanInterestRate()/100);
			loanRepayPlan.setInterestReceived(0);
			loanRepayPlan.setPrincipalReceivable(0);
			if (i==sum-1) {
				loanRepayPlan.setPrincipalReceivable(contract.getLoanAmount());
			}
			loanRepayPlan.setPrincipalReceived(0);
			loanRepayPlan.setPenaltyReceivable(0);
			loanRepayPlan.setPenaltyReceived(0);
			loanRepayPlan.setDefaultReceivable(0);
			loanRepayPlan.setDefaultReceived(0);
			loanRepayPlan.setDelayReceivable(0);
			loanRepayPlan.setDelayReceived(0);
			loanRepayPlan.setState("1");
			loanRepayPlan.setDefaultFlag("0");
			loanRepayPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			
			//插入到payment表
			Payment payment =new Payment();
			payment.setContractNo(contractNo);
			payment.setInOut("1");
			payment.setSubject("收利息");
			if (i==sum-1) {
				payment.setSubject("收本息");
			}
			double db=loanRepayPlan.getPrincipalReceivable()+loanRepayPlan.getSreviceFeeReceivable()+loanRepayPlan.getInterestReceivable();
			payment.setPlanAmount(db);
			payment.setActualAmount(db);
			payment.setPeriodNum(i);
			payment.setRepaymentDate(loanRepayPlan.getRepaymentDate());
			payment.setState("1");
			payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
			
			dao.add(loanRepayPlan);
			paymentService.add(payment);
		}
		
	}
	
	@Transactional
	public void handleRepayPlan() throws Exception{
		//获取为还款的记录
		Map<String, Object> mapRepay=new HashMap<String, Object>();
		mapRepay.put("state", "1");
		List<LoanRepayPlan> listLoanRepayPlans=dao.queryList(mapRepay);
		for (LoanRepayPlan loanRepayPlan : listLoanRepayPlans) {
			int com=DateUtils.compareDate(loanRepayPlan.getRepaymentDate(), DateUtils.getDateNow());
			if (com<0) {
				//超期
				Map<String, Object> mapCon=new HashMap<String, Object>();
				mapCon.put("contractNo", loanRepayPlan.getContractNo());
				List<Contract> listContracts=contractService.queryList(mapCon);
				if (listContracts.size()==0) {
					continue;
				}
				Contract contract=listContracts.get(0);
				Map<String, Object> mapPro=new HashMap<String, Object>();
				mapPro.put("name", contract.getLoanProduct());
				List<Product> listProducts=productService.queryList(mapPro);
				if (listProducts.size()==0) {
					continue;
				}
				Product product=listProducts.get(0);
				double db=contract.getLoanAmount()*product.getPenaltyRate()/100;
				int dayRange=DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), DateUtils.getDateNow());
				db=db*dayRange;
				loanRepayPlan.setPenaltyReceivable(db);
				loanRepayPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				Double dbAmount=loanRepayPlan.getPrincipalReceivable()+loanRepayPlan.getSreviceFeeReceivable()+loanRepayPlan.getInterestReceivable()+loanRepayPlan.getPenaltyReceivable();
				
				//更新payment表中数据
				Map<String, Object> mapPay=new HashMap<String, Object>();
				mapPay.put("contractNo", loanRepayPlan.getContractNo());
				mapPay.put("periodNum", loanRepayPlan.getPeriodNum());
				mapPay.put("subject", "还款");
				List<Payment>  listPayments=paymentService.queryList(mapPay);
				if (listPayments.size()==0) {
					continue;
				}
				Payment payment=listPayments.get(0);
				payment.setPlanAmount(dbAmount);
				payment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				paymentService.update(payment);
				dao.update(loanRepayPlan);
			}
		}
		
	}
	
	/**
	 * 完成一笔还款，还款计划
	 * @param loanRepayPlan
	 * @return
	 */
	@Transactional
	public LoanRepayPlan finishRepay(LoanRepayPlan loanRepayPlan){
		
		
		//本次实收服务费
		loanRepayPlan.setSreviceCurrent(MathUtils.sub(loanRepayPlan.getSreviceFeeReceivable(),loanRepayPlan.getSreviceFeeReceived()));
		loanRepayPlan.setSreviceFeeReceived(loanRepayPlan.getSreviceFeeReceivable());
		
		//本次实收利息
		loanRepayPlan.setInterestCurrent(MathUtils.sub(loanRepayPlan.getInterestReceivable(),loanRepayPlan.getInterestReceived()));
		loanRepayPlan.setInterestReceived(loanRepayPlan.getInterestReceivable());
		
		//本次实收本金
		loanRepayPlan.setPrincipalCurrent(MathUtils.sub(loanRepayPlan.getPrincipalReceivable(),loanRepayPlan.getPrincipalReceived()));
		loanRepayPlan.setPrincipalReceived(loanRepayPlan.getPrincipalReceivable());
		
		//本次实收违约金
		loanRepayPlan.setDefaultCurrent(MathUtils.sub(loanRepayPlan.getDefaultReceivable(),loanRepayPlan.getDefaultReceived()));
		loanRepayPlan.setDefaultReceived(loanRepayPlan.getDefaultReceivable());
				
		//当前实收罚息
		loanRepayPlan.setPenaltyCurrent(
				MathUtils.sub(MathUtils.sub(loanRepayPlan.getPenaltyReceivable(),
										loanRepayPlan.getPenaltyReceived())
						 ,loanRepayPlan.getPenaltyReduce()));
		loanRepayPlan.setPenaltyReceived(MathUtils.sub(loanRepayPlan.getPenaltyReceivable()
				,loanRepayPlan.getPenaltyReduce()));
		
		//当前实收滞纳金
		loanRepayPlan.setDelayCurrent(
				MathUtils.sub(MathUtils.sub(loanRepayPlan.getDelayReceivable(),
										loanRepayPlan.getDelayReceived())
						 ,loanRepayPlan.getDelayReduce()));
		
		loanRepayPlan.setDelayReceived(MathUtils.sub(loanRepayPlan.getDelayReceivable()
				,loanRepayPlan.getDelayReduce()));
		
		String state="2";

		loanRepayPlan.setState(state);
		loanRepayPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		loanRepayPlan.setPayDate(new Date());
		dao.update(loanRepayPlan);
		
		return loanRepayPlan;
	}
	
	/**
	 * 不足额还款 还款计划分析 
	 * @param loanRepayPlan  还款计划
	 * @param dbAmount		 还款金额
	 * @return
	 */
	@Transactional
	public LoanRepayPlan finishRepay(LoanRepayPlan loanRepayPlan,double dbAmount){
		
		
//		String state="2";
		
		// 剩余服务费金额
		double residueSrevice=MathUtils.sub(loanRepayPlan.getSreviceFeeReceivable(), 
										  loanRepayPlan.getSreviceFeeReceived());
		if(dbAmount>0&&residueSrevice>0){
			if(dbAmount>=residueSrevice){
				dbAmount=MathUtils.sub(dbAmount,residueSrevice);
			}else{
				residueSrevice=dbAmount;
				dbAmount=0.0;
			}
			loanRepayPlan.setSreviceFeeReceived(
					MathUtils.add(
							loanRepayPlan.getSreviceFeeReceived(),
							residueSrevice));
			//本次实收服务费
			loanRepayPlan.setSreviceCurrent(residueSrevice);
		}
		
		// 剩余滞纳金金额
		double residueDelay=MathUtils.sub(MathUtils.sub(loanRepayPlan.getDelayReceivable(),
														loanRepayPlan.getDelayReceived())
										  ,loanRepayPlan.getDelayReduce());
		
		if(dbAmount>0&&residueDelay>0){
			if(dbAmount>=residueDelay){
				dbAmount=MathUtils.sub(dbAmount,residueDelay);
			}else{
				residueDelay=dbAmount;
				dbAmount=0.0;
			}
			loanRepayPlan.setDelayReceived(
					MathUtils.add(
							loanRepayPlan.getDelayReceived(),
							residueDelay));
			//本次实收 滞纳金
			loanRepayPlan.setDelayCurrent(residueDelay);
		}
		
		// 剩余罚息金额
		double residuePenalty=MathUtils.sub(MathUtils.sub(loanRepayPlan.getPenaltyReceivable(),
														loanRepayPlan.getPenaltyReceived())
										  ,loanRepayPlan.getPenaltyReduce());
		if(dbAmount>0&&residuePenalty>0){
			if(dbAmount>=residuePenalty){
				dbAmount=MathUtils.sub(dbAmount,residuePenalty);
			}else{
				residuePenalty=dbAmount;
				dbAmount=0.0;
			}
			loanRepayPlan.setPenaltyReceived(
					MathUtils.add(
							loanRepayPlan.getPenaltyReceived(),
							residuePenalty));
			//本次实收 罚息
			loanRepayPlan.setPenaltyCurrent(residuePenalty);
		}
		
		// 剩余利息金额
		double residueInterest=MathUtils.sub(loanRepayPlan.getInterestReceivable(),loanRepayPlan.getInterestReceived());
		if(dbAmount>0&&residueInterest>0){
			if(dbAmount>=residueInterest){
				dbAmount=MathUtils.sub(dbAmount,residueInterest);
			}else{
				residueInterest=dbAmount;
				dbAmount=0.0;
			}
			loanRepayPlan.setInterestReceived(
					MathUtils.add(
							loanRepayPlan.getInterestReceived(),
							residueInterest));
			//本次实收利息
			loanRepayPlan.setInterestCurrent(residueInterest);
		}
		// 剩余本金金额
		double residuePrincipal=MathUtils.sub(loanRepayPlan.getPrincipalReceivable(),loanRepayPlan.getPrincipalReceived());
		if(dbAmount>0&&residuePrincipal>0){
			if(dbAmount>=residuePrincipal){
				dbAmount=MathUtils.sub(dbAmount,residuePrincipal);
			}else{
				residuePrincipal=dbAmount;
				dbAmount=0.0;
			}
			loanRepayPlan.setPrincipalReceived(
					MathUtils.add(
							loanRepayPlan.getPrincipalReceived(),
							residuePrincipal));
			//本次实收本金
			loanRepayPlan.setPrincipalCurrent(residuePrincipal);
		}
		//计算还款后 剩余多少
		double syje=this.countLoanRepayPlan(loanRepayPlan);
		if(syje>0){
			loanRepayPlan.setState("3");
		}else{
			loanRepayPlan.setState("2");
			loanRepayPlan.setPayDate(new Date());
		}
		loanRepayPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		dao.update(loanRepayPlan);
		return loanRepayPlan;
	}
	
	
	/**
	 * 还款计划中计算下一期的还款日，累加一个月，并对时间做处理
	 * @param dtStart，上一期的还款日
	 * @return
	 */
	public Date nextMonth(Date dtStart){
		Date dtNext=DateUtils.addMonth(dtStart, 1);
		int month=DateUtils.getMonth(dtNext);
		int day=DateUtils.getDay(dtNext);
		if (month==2 && day==29) {
			dtNext=DateUtils.addDay(dtNext, -1);
		}
		
		if (day==31) {
			dtNext=DateUtils.addDay(dtNext, -1);
		}
		return dtNext;
	}
	
	/**
	 * 还款计划中计算下一期的还款日，累加一个月，并对时间做处理
	 * @param dtStart，上一期的还款日
	 * @return
	 */
	public Date nextMonth(Date dtStart, int addMonthCount){
		Date dtNext=DateUtils.addMonth(dtStart, addMonthCount);
		int month=DateUtils.getMonth(dtNext);
		int day=DateUtils.getDay(dtNext);
		if (month==2 && day==29) {
			dtNext=DateUtils.addDay(dtNext, -1);
		}
		
		if (day==31) {
			dtNext=DateUtils.addDay(dtNext, -1);
		}
		return dtNext;
	}
	
	/**
	 * 查询为支付完成的还款记录，状态为1,3
	 * @return
	 */
/*	public List<LoanRepayPlan> queryListPay(){
		return dao.queryListPay();
	}*/ 
	
	/**
	 * 为一个信用借贷合同添加还款计划
	 * @param contractNo
	 * @throws Exception 
	 */
	@Transactional
	public void addCreditRepayPlan(String contractNo) throws Exception {
     
		Map<String, Object> mapCon=new HashMap<String, Object>();
		mapCon.put("contractNo", contractNo);
		
		List<Contract> listContracts=contractService.queryList(mapCon);
		
		if (listContracts.size()==0) {
			return;
		}
		Contract contract=listContracts.get(0);
		//清空冗余 还款计划
		this.deleteByContractNo(contractNo);
		Product pro = productService.queryByName(contract.getLoanProduct());
		List<LoanRepayPlan> rpList=averageCapitalPlusInterest(contract.getLoanAmount(), pro);
		
		//每期一条记录;
		for (int i = 0; i < rpList.size(); i++) {
			// 还款 计划还款日
			Date dtTmp=DateUtils.getDateNow();
			LoanRepayPlan loanRepayPlan=rpList.get(i);
			loanRepayPlan.setContractNo(contractNo);
			loanRepayPlan.setRepayMethod(contract.getRepayMethod());
			dtTmp = nextMonth(dtTmp, i + 1);//计算 还款日
			loanRepayPlan.setRepaymentDate(dtTmp);
			loanRepayPlan.setStartDate(contract.getStartDate());
			loanRepayPlan.setEndDate(contract.getEndDate());
//			loanRepayPlan.setState("1");
			loanRepayPlan.setState("0");	//放款之前状态 都是不可用
			loanRepayPlan.setDefaultFlag("0");
			loanRepayPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.add(loanRepayPlan);
		}
		
	}
	
	/**
	 * 对于重签客户或复核退回的合同更新还款计划中的日期
	 * 
	 * @param contract
	 * @throws Exception
	 */
	public void updateRepayPlanDate(Contract contract) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contract.getContractNo());
		List<LoanRepayPlan> list = this.queryList(map);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Date dtTmp=DateUtils.getDateNow();
				LoanRepayPlan loanRepayPlan=list.get(i);
				dtTmp = nextMonth(dtTmp, i + 1);
				loanRepayPlan.setRepaymentDate(dtTmp);
				loanRepayPlan.setStartDate(contract.getStartDate());
				loanRepayPlan.setEndDate(contract.getEndDate());
				this.updateOnlyChanged(loanRepayPlan);
			} 
		}
	}
	
	
	/**
	 * 计算 等额本息 还款计划
	 * @param contractAmount	合同金额
	 * @param product			借款产品
	 * @return					还款计划 列表
	 * @throws Exception
	 */
	public List<LoanRepayPlan> averageCapitalPlusInterest(double contractAmount,Product product) throws Exception {
		List<LoanRepayPlan> repayPlanList=new ArrayList<LoanRepayPlan>();
		double 	sff=product.getSreviceFeeRate(),  	//服务费率
				lilu=product.getInterestRate(),		//月利率
				qx=product.getPeriod();				//借款期限
		// 月还款额  保留两位 
		double MAmount=creditSignService.getYhkje(contractAmount, product);
		MAmount = MathUtils.roundUp(MAmount, 2);
		//还款总额
		double repaySum=MathUtils.mul(MAmount,qx);
		//趸交服务费总额
		//double serviceSum=MathUtils.sub(contractAmount,contractAmount);
	
		//每期 剩余本金
		double sybj=contractAmount;
		for (int i = 1; i <= qx; i++) {
			LoanRepayPlan rp=new LoanRepayPlan();
			rp.setPeriodNum(i);
			
			double m_li=0.0,m_bj=0.0;
			//月利息
			m_li=MathUtils.round(MathUtils.mul(sybj,MathUtils.div(lilu, 100.0)), 2);
			//月本金
			m_bj=MathUtils.sub(MAmount,m_li);
			//剩余本金
			sybj=MathUtils.sub(sybj,m_bj);
			
			rp.setInterestReceivable(m_li);
			rp.setPrincipalReceivable(m_bj);
			rp.setRestPrincipal(sybj);
			
			rp.setMonthAmount(MAmount);
			rp.setRepaySum(repaySum);
//			rp.setServiceSum(serviceSum);
			repayPlanList.add(rp);
		}
		return repayPlanList;
	}

		
	/**
	 * 根据合同号删除 还款计划
	 * @param contractNo
	 */
	@Transactional
	public void deleteByContractNo(String contractNo){
		dao.deleteByContractNo(contractNo);
	}
	
	/**
	 * 计算 还款计划 应收金额
	 * @param loanRepayPlan
	 * @return
	 */
	public double countLoanRepayPlan(LoanRepayPlan loanRepayPlan){
		
		//应收
		double ysAmount[]={loanRepayPlan.getPrincipalReceivable()//本金
				,loanRepayPlan.getInterestReceivable()//利息
				,loanRepayPlan.getPenaltyReceivable()//罚息
				,loanRepayPlan.getDelayReceivable()//滞纳金
				,loanRepayPlan.getSreviceFeeReceivable()//服务费
				,loanRepayPlan.getDefaultReceivable()};//违约金
		//实收
		double ssAmount[]={loanRepayPlan.getPrincipalReceived()//本金
				,loanRepayPlan.getInterestReceived()//利息
				,loanRepayPlan.getPenaltyReceived()//罚息
				,loanRepayPlan.getDelayReceived()//滞纳金
				,loanRepayPlan.getSreviceFeeReceived()//服务费
				,loanRepayPlan.getDefaultReceived()};//违约金
		//减免
		double jmAmount[]={loanRepayPlan.getDelayReduce()////滞纳金减免
				,loanRepayPlan.getPenaltyReduce()};//罚息减免

		//应收总额
		double dbAmount=0.0;
		for (double ys : ysAmount) {
			dbAmount=MathUtils.add(dbAmount, ys);
		}
		//减去 已收金额
		for (double ss : ssAmount) {
			dbAmount=MathUtils.sub(dbAmount, ss);
		}
		//减去减免金额
		for (double jm : jmAmount) {
			dbAmount=MathUtils.sub(dbAmount, jm);
		}
		
		return dbAmount;
	}
	
	/**
	 * 计算 还款计划 最高减免金额
	 * @param loanRepayPlan
	 * @return
	 */
	public double countLoanRepayPlanMax(LoanRepayPlan loanRepayPlan){
		
		//应收
		double ysAmount[]={
			loanRepayPlan.getPenaltyReceivable(),//罚息
			loanRepayPlan.getDelayReceivable()//滞纳金
		};
		
		//实收
		double ssAmount[]={
			loanRepayPlan.getPenaltyReceived(),//罚息
			loanRepayPlan.getDelayReceived()//滞纳金
		};
		//减免
		double jmAmount[]={
			loanRepayPlan.getDelayReduce(),//滞纳金减免
			loanRepayPlan.getPenaltyReduce()//罚息减免
		};

		//应收总额
		double maxAmount = 0.0;
		for (double ys : ysAmount) {
			maxAmount = MathUtils.add(maxAmount, ys);
		}
		//减去 已收金额
		for (double ss : ssAmount) {
			maxAmount = MathUtils.sub(maxAmount, ss);
		}
		//减去减免金额
		for (double jm : jmAmount) {
			maxAmount  =MathUtils.sub(maxAmount, jm);
		}
		return maxAmount;
	}
	
	public List<LoanRepayPlan> queryFyLoanStateList(Map<String, Object> map) throws Exception {
		return dao.queryFyLoanStateList(map);
	}
	public List<LoanRepayPlan> queryLateDaysCallList(Map<String, Object> map) throws Exception {
		return dao.queryLateDaysCallList(map);
	}
	/**
	 * 查询逾期的统计记录（催收-毕桃杨）
	 * @return
	 */
	
	public List<LoanRepayPlan> queryListLateStatistics(Map<String, Object> map) {
		
		return dao.queryListLateStatistics(map);
	}
	/**
	 * 排序查询某合同的所有逾期记录（Jesse）
	 * @param map
	 * @return
	 */
	public List<LoanRepayPlan> queryListLateByNo(Map<String, Object> map) {
		
		return dao.queryListLateByNo(map);
	}

	/**
	 * 根据合同号查询当前所在期的信息（Jesse）
	 * @param map
	 * @return
	 */
	public List<LoanRepayPlan> queryCurInfo(Map<String, Object> map) {
		
		return dao.queryCurInfo(map);
	}
	/**
	 * 根据合同号查询历史逾期期数
	 * @param map
	 * @return
	 */
	public int queryLateHisNum(Map<String, Object> map){
		return dao.queryLateHisNum(map);
	}
	
	/**
	 * 根据合同号查询最后一期的信息
	 * @param map
	 * @return
	 */
	public LoanRepayPlan queryLastInfo(Map<String, Object> map){
		return dao.queryLastInfo(map).get(0);
	}

	/**
	 * 还款计划数据生成提醒短信信息 
	 * @throws Exception
	 */
	@Transactional
	public void loanRepayPlanRemindMessage() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// ================   提醒短信     ====================
		String type = "remind";
		// 查询模板
		MsgTemplate template = msgTemplateService.queryByType(MessageConstant.REMIND_TEMPLATE_TYPE);
		if(template!=null){
			List<LoanRepayPlan> loanRepayPlanList = null;
			String[] states = {"1","3"}; // 状态
			for (int i = 1; i <= MessageConstant.REMIND_DAYS; i++) {
				for (String state : states) {
					map.clear();
					map.put("repaymentDate", DateUtils.formatDate(DateUtils.addDay(new Date(), i)));
					map.put("state", state);
					loanRepayPlanList = queryListExt(map);
					if (loanRepayPlanList != null && !loanRepayPlanList.isEmpty()) {
						for (LoanRepayPlan loanRepayPlan : loanRepayPlanList) {
							// 查询 短信日志中的短信条数
							map.clear();
							map.put("contractNo", loanRepayPlan.getContractNo());
							map.put("repaymentDate", DateUtils.formatDate(loanRepayPlan.getRepaymentDate()));
							map.put("days", MessageConstant.REMIND_DAYS);
							int count = msglogService.queryCount(map);
							// 短信条数少于设置条数  则创建新的短信信息
							if(count<MessageConstant.REMIND_COUNT){
								createMsglogByLoanRepayPlan(loanRepayPlan, template, type, MessageConstant.REMIND_DAYS);
							}
						}
					}
				}
			}
		} else {
			log.info("还款计划数据生成提醒短信信息:没有查询到有效的模板");
		}
		
		// ================   逾期短信     ====================
		type = "overdue";
		// 查询模板
		template = msgTemplateService.queryByType(MessageConstant.OVERDUE_TEMPLATE_TYPE);
		if(template!=null){
			List<LoanRepayPlan> loanRepayPlanList = null;
			String[] states = {"1","3"}; // 状态
			for (int i = 1; i <= MessageConstant.OVERDUE_DAYS; i++) {
				for (String state : states) {
					map.clear();
					map.put("repaymentDate", DateUtils.formatDate(DateUtils.addDay(new Date(), 0-i)));
					map.put("state", state);
					loanRepayPlanList = queryList(map);
					if (loanRepayPlanList != null && !loanRepayPlanList.isEmpty()) {
						for (LoanRepayPlan loanRepayPlan : loanRepayPlanList) {
							// 查询 短信日志中的短信条数
							map.clear();
							map.put("contractNo", loanRepayPlan.getContractNo());
							map.put("repaymentDate", DateUtils.formatDate(loanRepayPlan.getRepaymentDate()));
							map.put("days", i);
							int count = msglogService.queryCount(map);
							// 短信条数少于设置条数  则创建新的短信信息
							if(count<MessageConstant.OVERDUE_COUNT){
								createMsglogByLoanRepayPlan(loanRepayPlan, template, type, i);
							}
						}
					}
				}
			}
		} else {
			log.info("还款计划数据生成提醒短信信息:没有查询到有效的模板");
		}
		
	}
	@Transactional
	private void createMsglogByLoanRepayPlan(LoanRepayPlan loanRepayPlan, MsgTemplate template, String type, int days) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Contract> contractList = null;
		Contract contract = null;
		String bankAccount = "";
		double amountAll = 0; // 应还总额
		Msglog msglog = null;

		// 查询合同信息
		map.put("contractNo", loanRepayPlan.getContractNo());
		contractList = contractService.queryList(map);
		if(contractList!=null&&!contractList.isEmpty()){
			contract = contractList.get(0);
			bankAccount = contract.getBankAccount();
			if("remind".equals(type)){
				// 计算当月 应还金额
				amountAll = loanRepayPlan.getSreviceFeeReceivable() // 当月应收服务费
						- loanRepayPlan.getSreviceFeeReceived() // 当月实收服务费
						+ loanRepayPlan.getInterestReceivable() // 当月应收利息
						- loanRepayPlan.getInterestReceived()	// 当月实收利息
						+ loanRepayPlan.getPrincipalReceivable()// 当月应收本金
						- loanRepayPlan.getPrincipalReceived()	// 当月实收本金
						+ loanRepayPlan.getPenaltyReceivable() 	// 当月应收罚息
						- loanRepayPlan.getPenaltyReceived()	// 当月实收罚息
						+ loanRepayPlan.getDefaultReceivable() 	// 当月应收违约金
						- loanRepayPlan.getDefaultReceived()	// 当月实收违约金
						+ loanRepayPlan.getDelayReceivable() 	// 应收滞纳金
						- loanRepayPlan.getDelayReceived()	// 实收滞纳金
						- loanRepayPlan.getPenaltyReduce()	// 罚息减免
						- loanRepayPlan.getDelayReduce();	// 滞纳金减免
			}
			
			// 查询 催收基础表  取逾期 应还总额
			map.clear();
			map.put("contractNo", loanRepayPlan.getContractNo());
			CollectionBaseInfo collectionBaseInfo = collectionBaseService.queryInfo(map);
			if (collectionBaseInfo != null) {
				amountAll = amountAll + collectionBaseInfo.getAmountAll();
			}
			
			// 组装短信对象
			msglog = new Msglog();
			msglog.setContractNo(loanRepayPlan.getContractNo());
			msglog.setCustomerName(contract.getLoanName());
			msglog.setIdType(contract.getLoanIdType());
			msglog.setIdNo(contract.getLoanIdNo());
			if ("remind".equals(type)) {
				msglog.setType(MessageConstant.MESSAGE_TYPE_REMIND);
			} else if ("overdue".equals(type)) {
				msglog.setType(MessageConstant.MESSAGE_TYPE_OVERDUE);
			}
			msglog.setTemplateId(template.getId());
			msglog.setMobileTel(contract.getBankMobile());
			msglog.setSendState(MessageConstant.SENDSTATE_WAIT);
			msglog.setRepaymentDate(loanRepayPlan.getRepaymentDate());
			msglog.setDays(days);
			// 短信内容
			if ("remind".equals(type)) {
				msglog.setMsgContent(msgTemplateService.getMsgContent(template,
						new Object[] { contract.getLoanName(), amountAll,
								loanRepayPlan.getRepaymentDate().getMonth() + 1,
								loanRepayPlan.getRepaymentDate().getDate(),
								bankAccount.substring(bankAccount.length() - 4) }));
			} else if ("overdue".equals(type)) {
				msglog.setMsgContent(msgTemplateService.getMsgContent(template,
						new Object[] { contract.getLoanName(), amountAll,
								bankAccount.substring(bankAccount.length() - 4) }));
			}
			msglog.setCreateUid("sysauto");
			msglog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			msglogService.add(msglog);
		} else {
			log.info("还款计划数据生成提醒短信信息:没有查询到合同信息");
		}
	}
}
