package com.tera.payment.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.dao.CreditAppDao;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.service.CreditDecisionService;
import com.tera.customer.model.Customer;
import com.tera.customer.service.CustomerService;
import com.tera.lend.model.LendApp;
import com.tera.lend.service.LendAppService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.service.LoanAppService;
import com.tera.match.model.Lend2match;
import com.tera.match.service.Lend2matchService;
import com.tera.payment.dao.PaymentDao;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.model.ThirdPaymentLog;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.model.Workday;
import com.tera.sys.service.MybatisBaseService;
import com.tera.sys.service.WorkdayService;
import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>PaymentDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-23 13:08:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("paymentService")
public class PaymentService<T> extends MybatisBaseService<Payment> {

	private final static Logger log = Logger.getLogger(PaymentService.class);

	@Autowired(required=false)
    private PaymentDao<Payment> dao;

	@Autowired(required=false)
	private CustomerService<Customer> customerService;
	@Autowired(required=false)
	private LoanAppService<LoanApp> loanAppService;
	@Autowired(required=false)
    private CreditAppDao creditAppDao;
	@Autowired(required=false) //自动注入
	private ThirdPaymentService thirdPaymentService;
	@Autowired(required=false)
	ProcessService processService;
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;
	@Autowired(required=false)
	private ProductService<Product> productService;
	@Autowired(required=false)
	private Lend2matchService<Lend2match> lend2matchService;
	
	@Autowired(required=false)
	private WorkdayService<Workday> workdayService;
	@Autowired(required=false)
	LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false)
	AccounttingService accounttingService;

	@Autowired(required=false)
	ContractService contractService;
	@Autowired(required=false)
	CreditDecisionService creditDecisionService;
	
	@Transactional
	public void add(Payment... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(Payment t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(Payment t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(Payment t)  throws Exception {
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
	
	/**
	 * 生成公司对出借方的付息计划。
	 * @param lendAppId
	 * @param dtStart
	 * @throws Exception 
	 */
	@Transactional
	public void addInterest2LendPlan(String lendAppId) throws Exception {
		//获取
		Product product=productService.queryProductByLendAppId(lendAppId);
		Map<String, Object> maLend=new HashMap<String, Object>();
		maLend.put("lendAppId", lendAppId);
		
		List<Lend2match> listLend2matchs=lend2matchService.queryList(maLend);
		if (listLend2matchs.size()==0) {
			return;
		}
		Lend2match lend2match=listLend2matchs.get(0);
		
		if ("11".equals(product.getRepayMethod())) {
			//每三个月还息，最后还本息
			Date dtStart=workdayService.afterWorkDay(lend2match.getAppTime(), 4);
			int a=lend2match.getLendPeriod() % 3; //求余
			int b=lend2match.getLendPeriod() / 3;
			
			for (int i = 1; i < b; i++) {
				//插入到payment表
				Date dtPayDate=DateUtils.addMonth(dtStart, i*3);
				dtPayDate=DateUtils.addDay(dtPayDate, -1);
				Payment payment =new Payment();
				payment.setContractNo(lendAppId);
				payment.setInOut("2");
				payment.setSubject("付利息");
				double db=lend2match.getLendAmount()*lend2match.getLendInterestRate()*3/12/100;
				payment.setPlanAmount(db);
				payment.setActualAmount(db);
				payment.setPeriodNum(i);
				payment.setRepaymentDate(dtPayDate);
				payment.setState("9");
				payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
				this.add(payment);
			}
			
			if (a==0) {
				Date dtPayDate=DateUtils.addMonth(dtStart, b*3);
				dtPayDate=DateUtils.addDay(dtPayDate, -1);
				Payment payment =new Payment();
				payment.setContractNo(lendAppId);
				payment.setInOut("2");
				payment.setSubject("付本息");
				double db=lend2match.getLendAmount()+lend2match.getLendAmount()*lend2match.getLendInterestRate()*3/12/100;
				payment.setPlanAmount(db);
				payment.setActualAmount(db);
				payment.setPeriodNum(b);
				payment.setRepaymentDate(dtPayDate);
				payment.setState("9");
				payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
				this.add(payment);
			}else {
				//插入到payment表
				Date dtPayDate=DateUtils.addMonth(dtStart, b*3);
				dtPayDate=DateUtils.addDay(dtPayDate, -1);
				Payment payment =new Payment();
				payment.setContractNo(lendAppId);
				payment.setInOut("2");
				payment.setSubject("付利息");
				double db=lend2match.getLendAmount()*lend2match.getLendInterestRate()*3/12/100;
				payment.setPlanAmount(db);
				payment.setActualAmount(db);
				payment.setPeriodNum(b);
				payment.setRepaymentDate(dtPayDate);
				payment.setState("9");
				payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
				this.add(payment);
				
				//最后一期 ，时间不是3个月
				
				Date dtPayDate2=DateUtils.addMonth(dtStart, lend2match.getLendPeriod());
				dtPayDate2=DateUtils.addDay(dtPayDate2, -1);
				Payment payment2 =new Payment();
				payment2.setContractNo(lendAppId);
				payment2.setInOut("2");
				payment2.setSubject("付本息");
				double db2=lend2match.getLendAmount()+lend2match.getLendAmount()*lend2match.getLendInterestRate()*a/12/100;
				payment2.setPlanAmount(db2);
				payment2.setActualAmount(db2);
				payment2.setPeriodNum(b+1);
				payment2.setRepaymentDate(dtPayDate2);
				payment2.setState("9");
				payment2.setCreateTime(new Timestamp(System.currentTimeMillis()));
				this.add(payment2);
			}
		}
		
		
		
	}
	
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<Payment> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * 				key
	 * 					curPage   当前页数
	 * 					pageSize  每页条数
	 * @return
	 */
	public PageList<Payment> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(PaymentDao.class, "queryList", params);
	}

	public Payment queryByKey(Object id) throws Exception {
		return dao.queryByKey(id);
	}

	/**
	 * 收款
	 * 发送支付数据包到第三方支付接口
	 * @throws Exception 异常
	 */
	public void moneyIn() throws Exception {
		
	}
	
	/**
	 * 付款
	 * 发送支付数据包到第三方支付接口
	 */
	public void moneyOut(){
		
	}
	
	/**
	 * 实时回盘
	 * 收到第三方支付数据包
	 */
	public void receive(){
		
	}
	


	/**
	 * 根据用户信息，得到 用户带支付的 明细列表   全是必传参数
	 * @param customer	登录客户
	 * @param states 	明细状态
	 * @param rowS				
	 * @param rowE
	 * @return
	 */
	public List<Payment> getWaitingPayment(Map<String, Object> map){
		return dao.getWaitingPayment(map);
	}
	
	public int getWaitingPaymentCount(Map<String, Object> map){
		return dao.getWaitingPaymentCount(map);
	}
	
	
	/**
	 * 检查客户是否存在
	 * @param userType
	 * @param name
	 * @param idType
	 * @param idNo
	 * @return
	 * @throws Exception 
	 */
	public Customer checkIsCustomer(String userType,String name, String idType, String idNo) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("idType", idType);
		map.put("idNo", idNo);
		map.put("customerType", userType);
		List<Customer> customers=customerService.queryList(map);
		return customers.size()>0 ? customers.get(0): null;
	}


	public LoanApp queryLoanAppByContractNo(String appOrContractNo) {
		List<LoanApp> lfbList=loanAppService.getLoanListByContractNo(appOrContractNo);
		return  lfbList.size() >0 ?lfbList.get(0):null;
	}

//	public LendApp queryLendAppByAppId(String appOrContractNo) {
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("appId", appOrContractNo);
//		map.put("mainFlag", "0");
//		//出借人信息
//		return  lendAppDao.queryList(map).size()>0 ?lendAppDao.queryList(map).get(0):null ;
//	}
	
	
	@Transactional
	public void payUpdate(String orderNo,boolean paySuccess,String errorCode) throws Exception{
		if(paySuccess){
			ThirdPaymentLog tlog=thirdPaymentService.queryByOrderNo(orderNo);
			if(tlog!=null&& !"5".equals(tlog.getState())){
				tlog.setState("5");
				tlog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				thirdPaymentService.update(tlog);
				Payment pmt=this.queryByKey(tlog.getPaymentId());
				if(pmt!=null){
					// 判断金额是否相等，如果不等 可能批处理 已经改变了状态，需要进行人工处理。
					pmt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					if(tlog.getAmount()==pmt.getActualAmount()){
						pmt.setState("5");
						this.update(pmt);	//更新支付状态
					}else{
						pmt.setState("10");
						this.update(pmt);
					}
					//判断 是否是 出借方  资金入账 入账成功后进入自动撮合流程
					String appId=pmt.getContractNo();
					if(appId.startsWith("L")){//出借申请 借款
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
						this.addInterest2LendPlan(lendApp.getAppId());
						log.info(l2);
						//记录 记账明细
						accounttingService.accountting(pmt);
					}else{//还款操作 
						
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("contractNo", pmt.getContractNo());
						map.put("periodNum", pmt.getPeriodNum());
						
						//查询当期还款计划
						List<LoanRepayPlan> listLoanRepayPlans=loanRepayPlanService.queryList(map);
						LoanRepayPlan loanRepayPlan=listLoanRepayPlans.get(0);
						
						//分析还款计划  
						loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan);
						accounttingService.repayPlanAccountting(loanRepayPlan);
						
						//更新合同状态
						contractService.ifContractEnd(pmt.getContractNo());
					}
				}
			}
		}else{
			ThirdPaymentLog tlog=thirdPaymentService.queryByOrderNo(orderNo);
			if(tlog!=null&&!"6".equals(tlog.getState())){
				tlog.setState("6");
				thirdPaymentService.update(tlog);
				Payment pmt=this.queryByKey(tlog.getPaymentId());
				if(pmt!=null){
					pmt.setState("6");
					pmt.setReason(errorCode);
					this.update(pmt);
				}
			}
		}
	}

	public LendApp getLendListByAppId(String appOrContractNo) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appOrContractNo);
		List<LendApp> lendApps=lendAppService.getLendListByAppId(map);
		return lendApps.size()>0?lendApps.get(0):null;
	}
	
	public CreditApp getCreditListByContractNo(String appOrContractNo) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", appOrContractNo);
		List<CreditApp> creditApps = creditAppDao.queryBpmAppAndContractList(map);
		return creditApps.size()>0?creditApps.get(0):null;
	}
	
	@Transactional
	public void updateByLendAppId(String lendAppId,String reason) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lendAppId", lendAppId);
		map.put("reason", reason);
		dao.updateByLendAppId(map);
	}
	
	
	
	public int queryLendPaymentCount(Map<String, Object> map)throws Exception {
		return dao.queryLendPaymentCount(map);
	}
	
	public List<Payment> queryLendPaymentList(Map<String, Object> map) throws Exception {
		return dao.queryLendPaymentList(map);
	}
	
	/**
	 * 信用贷款 放款成功后，更新同步 状态。
	 * 
	 * 更新还款计划， 还款支付信息 
	 * 
	 * @param contractNo	 合同编号
	 * @param repaymentDate	真实 的放款时间，用于更新 实际还款日期  ，为 null 时 不更新时间
	 * @throws Exception 
	 */
	@Transactional
	public void paymentSuccessUpdate(String contractNo,Date repaymentDate) throws Exception{
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("contractNo",contractNo);
		//合同
		Contract contract=contractService.queryList(fmap).get(0);
		fmap.put("appId", contract.getLoanAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		//最终决策
		CreditDecision creditDecision = creditDecisionService.queryList(fmap).get(0);
		//得到产品
//		Product product=productService.queryByName(creditDecision.getProduct());
//		String proName=creditDecision.getProduct();
		String channel = creditDecision.getBelongChannel();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		//查询当期还款计划
		List<LoanRepayPlan> listLoanRepayPlans=loanRepayPlanService.queryList(map);

		//更细还款计划，与支付表
		Timestamp newTmp=new Timestamp(System.currentTimeMillis());
		//循环更新还款计划，与支付信息
		map.put("inOut", "1");
		for (int i = 0; i < listLoanRepayPlans.size(); i++) {
			LoanRepayPlan loanRepayPlan = listLoanRepayPlans.get(i);
			map.put("periodNum", loanRepayPlan.getPeriodNum());
			Payment payment=dao.queryList(map).get(0);

			loanRepayPlan.setState("1");
			loanRepayPlan.setUpdateTime(newTmp);
			
			payment.setState("1");
			payment.setUpdateTime(newTmp);
			
			/** 如果 业务扩展 只需要修改这里 **/
			if(repaymentDate!=null){
				Date lsDate=loanRepayPlanService.nextMonth(repaymentDate, i + 1);//计算 还款日;
//				if(proName.indexOf("JM")!=-1){//积木产品处理
				if("JM".equals(channel)){//积木产品处理
					loanRepayPlan.setRepaymentDate(lsDate);
					payment.setRepaymentDate(lsDate);
//				}else if(proName.indexOf("DX")!=-1){//鼎轩产品处理
				}else if("DX".equals(channel)){//鼎轩产品处理
					loanRepayPlan.setRepaymentDate(lsDate);
					payment.setRepaymentDate(lsDate);
//				}else if(proName.indexOf("MD")!=-1){//鼎轩产品处理
				}else if("MD".equals(channel)){//
					loanRepayPlan.setRepaymentDate(lsDate);
					payment.setRepaymentDate(lsDate);
				}else if("RY".equals(channel)){//中海软银
					loanRepayPlan.setRepaymentDate(lsDate);
					payment.setRepaymentDate(lsDate);
				}else{/**其他处理 暂时 不更新时间**/ }
			}
			loanRepayPlanService.update(loanRepayPlan);
			dao.update(payment);
		}
	}
	
	/**
	 * 根据合同号删除 支付列表
	 * @param contractNo
	 */
	public void deleteByContractNo(String contractNo){
		dao.deleteByContractNo(contractNo);
	}
	
}
