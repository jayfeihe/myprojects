package com.tera.accounting.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.accounting.dao.AccounttingDao;
import com.tera.accounting.model.Accountting;
import com.tera.bpm.constant.BpmConstants;
import com.tera.match.model.Lend2match;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * <br>
 * <b>功能：</b>AccounttingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-21 14:52:43<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("accounttingService")
public class AccounttingService<T> extends MybatisBaseService<Accountting> {

	private final static Logger log = Logger.getLogger(AccounttingService.class);

	@Autowired(required=false)
    private AccounttingDao<T> dao;
	
	@Autowired(required=false)
	LoanRepayPlanService loanRepayPlanService;
	
	@Autowired(required=false)
	private PaymentService<Payment> paymentService;

	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(T t)  throws Exception {
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
	
	public List<T> queryList(Map<String, Object> map) throws Exception {
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
	public PageList<Accountting> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(AccounttingDao.class, "queryList", params);
	}
	
	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}

	/**
	 * 
	 * @param payment		支付信息
	 * @throws Exception
	 */
	@Transactional
	public void accountting(Payment payment) throws Exception {
		//出借方,
		//收：出借资金
		//付：付本息
		//付：付利息

		//借款方
		//付：放款
		//收：收本息； 收：收利息。   这里不处理 存在不足额还款情况
		
		if("1".equals(payment.getInOut())){
			Accountting account=new Accountting();
			account.setInOut("1");
			account.setContractNo(payment.getContractNo());
			account.setSource(payment.getSource());
			account.setPeriodNum(payment.getPeriodNum());
			account.setState("1");
			account.setOperator(BpmConstants.SYSTEM_SYS);
			account.setOrgId(payment.getOrgId());
			long time=System.currentTimeMillis();
			account.setCreateTime(new Timestamp(time));
			account.setUpdateTime(new Timestamp(time));
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-出借本金");
			account.setPlanAmount(payment.getPlanAmount());
			account.setActualAmount(payment.getActualAmount());
			dao.add((T)account);
			return;
		}
		
		Accountting account=new Accountting();
		account.setInOut(payment.getInOut());
		account.setContractNo(payment.getContractNo());
		account.setSource(payment.getSource());
		account.setPeriodNum(payment.getPeriodNum());
		account.setState("1");
		account.setOperator(BpmConstants.SYSTEM_SYS);
		account.setOrgId(payment.getOrgId());
		long time=System.currentTimeMillis();
		account.setCreateTime(new Timestamp(time));
		account.setUpdateTime(new Timestamp(time));
		
		if("放款".equals(payment.getSubject())){//如果是出借 付款
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-放款本金");
			account.setPlanAmount(payment.getPlanAmount());
			account.setActualAmount(payment.getActualAmount());
			dao.add((T)account);
		}else if("付利息".equals(payment.getSubject()) ){
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-赎回利息");
			account.setPlanAmount(payment.getPlanAmount());
			account.setActualAmount(payment.getActualAmount());
			dao.add((T)account);
		}else if ("付本息".equals(payment.getSubject())) {
			//分别记录本金和利息
			//获取本金
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("contractNo", payment.getContractNo());
			map.put("subject", "出借资金");
			map.put("state", "5");
			List<Payment> listPayments=paymentService.queryList(map);
			Double dbAmount=listPayments.get(0).getActualAmount();
			
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-赎回利息");
			account.setPlanAmount(payment.getPlanAmount()-dbAmount);
			account.setActualAmount(payment.getActualAmount()-dbAmount);
			dao.add((T)account);
			
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-赎回本金");
			account.setPlanAmount(dbAmount);
			account.setActualAmount(dbAmount);
			dao.add((T)account);
		}else if("赎回资金".equals(payment.getSubject()) ){
			//需要分别记录本金，利息，违约金的业务记账
			String strMoney=payment.getDetail();
			String[] strArray=strMoney.split(",");
			
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-赎回本金");
			account.setPlanAmount(Double.parseDouble(strArray[0]));
			account.setActualAmount(Double.parseDouble(strArray[0]));
			dao.add((T)account);
			
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-赎回利息");
			account.setPlanAmount(Double.parseDouble(strArray[1]));
			account.setActualAmount(Double.parseDouble(strArray[1]));
			dao.add((T)account);
			
			account.setId(0);
			account.setInOut("1");
			account.setAccount("其它收入");
			account.setSubject("业务往来-赎回违约金");
			account.setPlanAmount(Double.parseDouble(strArray[2]));
			account.setActualAmount(Double.parseDouble(strArray[2]));
			dao.add((T)account);
		}
		
	}
	/**
	 * 收 还款 记账
	 * 收：收本息； 收：收利息    记账处理
	 * @param loanRepayPlan
	 * @throws Exception
	 */
	@Transactional
	public void repayPlanAccountting(LoanRepayPlan loanRepayPlan) throws Exception{
		
		Accountting account=new Accountting();
		account.setInOut("1");
		account.setContractNo(loanRepayPlan.getContractNo());
//		account.setSource(payment.getSource());
		account.setPeriodNum(loanRepayPlan.getPeriodNum());
		account.setState("1");
		account.setOperator(BpmConstants.SYSTEM_SYS);
		account.setOrgId(loanRepayPlan.getOrgId());
		long time=System.currentTimeMillis();
		account.setCreateTime(new Timestamp(time));
		account.setUpdateTime(new Timestamp(time));
		
		if(loanRepayPlan.getPrincipalCurrent()>0){ 
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-每期实收本金");
			account.setPlanAmount(loanRepayPlan.getPrincipalReceivable());
			account.setActualAmount(loanRepayPlan.getPrincipalCurrent());
			dao.add((T)account);
			//风险金  
			if(loanRepayPlan.getDelayReceivable()>0){
				account.setId(0);
				account.setAccount("风险金");
				account.setSubject("业务往来-每期实收本金补风险金");
				account.setPlanAmount(loanRepayPlan.getPrincipalReceivable());
				account.setActualAmount(loanRepayPlan.getPrincipalCurrent());
				dao.add((T)account);
			}
		}
		//当月实收利息
		if(loanRepayPlan.getInterestCurrent()>0){ 
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-每期实收利息");
			account.setPlanAmount(loanRepayPlan.getInterestReceivable());
			account.setActualAmount(loanRepayPlan.getInterestCurrent());
			dao.add((T)account);
			//风险金
			if(loanRepayPlan.getDelayReceivable()>0){
				account.setId(0);
				account.setAccount("风险金");
				account.setSubject("业务往来-每期实收利息补风险金");
				account.setPlanAmount(loanRepayPlan.getInterestReceivable());
				account.setActualAmount(loanRepayPlan.getInterestCurrent());
				dao.add((T)account);
			}
		}
		//当月实收服务费
		if(loanRepayPlan.getSreviceCurrent()>0){
			account.setId(0);
			account.setAccount("服务费");
			account.setSubject("业务往来-每期实收服务费");
			account.setPlanAmount(loanRepayPlan.getSreviceFeeReceivable());
			account.setActualAmount(loanRepayPlan.getSreviceCurrent());
			dao.add((T)account);
		}
		//当月实收罚息
		if(loanRepayPlan.getPenaltyCurrent()>0){
			account.setId(0);
			account.setAccount("其它收入");
			account.setSubject("业务往来-每期实收罚息");
			account.setPlanAmount(loanRepayPlan.getPenaltyReceivable());
			account.setActualAmount(loanRepayPlan.getPenaltyCurrent());
			dao.add((T)account);
		}
		//实收滞纳金
		if(loanRepayPlan.getDelayCurrent()>0){
			account.setId(0);
			account.setAccount("其它收入");
			account.setSubject("业务往来-每期实收滞纳金");
			account.setPlanAmount(loanRepayPlan.getDelayReceivable());
			account.setActualAmount(loanRepayPlan.getDelayCurrent());
			dao.add((T)account);
		}
		//实收违约金
		if(loanRepayPlan.getDefaultCurrent()>0){
			account.setId(0);
			account.setAccount("其它收入");
			account.setSubject("业务往来-每期实收违约金");
			account.setPlanAmount(loanRepayPlan.getDefaultReceivable());
			account.setActualAmount(loanRepayPlan.getDefaultCurrent());
			dao.add((T)account);
		}
	
	}
	
	
}
