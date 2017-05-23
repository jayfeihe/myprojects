package com.tera.audit.account.service.impl;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.dao.AfterLoanDao;
import com.tera.audit.account.model.BillBase;
import com.tera.audit.account.model.BillInfo;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.AccountJsonMsg;
import com.tera.audit.account.model.form.AfterLoanQBean;
import com.tera.audit.account.service.IAfterLoanAccountService;
import com.tera.audit.account.service.IBillBaseService;
import com.tera.audit.law.model.Contract;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.model.LendUserLog;
import com.tera.feature.lenduser.service.ILendUserLogService;
import com.tera.feature.lenduser.service.ILendUserService;
import com.tera.sys.service.MybatisBaseService;
import com.tera.util.MathUtils;


/** 贷后核帐Service
 * @author QYANZE
 *
 */
@Service("afterLoanAccountService")
public class AfterLoanAccountServiceImpl extends MybatisBaseService<AfterLoanQBean> implements IAfterLoanAccountService {

//	@Autowired
//	private IBillAcctService billAcctService;
	@Autowired
	private IBillBaseService billBaseService;
	@Autowired
	private ILendUserService lendUserService;
	@Autowired
	private ILendUserLogService lendUserLogService;
	@Autowired
	private IRetPlanService retPlanService;
//	@Autowired
//	private IContractService contractService;
	
	
	@Override
	public PageList<AfterLoanQBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(AfterLoanDao.class, "queryList", params);
	}
	
	@Override
	public AccountJsonMsg collected(AccountFormBean bean, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		String loanId = bean.getLoanId();
		String contractId = bean.getContractId();
		String getLoanWay = bean.getGetLoanWay(); // 融资方式
		double amt = bean.getAmt();
		String remark = bean.getRemark();
		String subject = bean.getSubject();
		String proof = bean.getProof();
		int curPeriod = bean.getCurPeriod();
//		String totalPeriod = bean.getTotalPeriod();
		String lendUserId = bean.getLendUserId();
		
		String payDate = bean.getPayDate(); // 收款日期
		BillInfo info=new BillInfo();
		info.setContractId(contractId);
		info.setIntAmt(amt);
		info.setRemarks(remark);
		info.setLoginId(loginId);
		info.setProof(bean.getProof());
		info.setRetnum(curPeriod);
		info.setPayTime(payDate);
		info.setDefaultFee(bean.getDefaultFee());
		info.setPenaltyFee(bean.getPenaltyFee());
		info.setOtherFee(bean.getOtherFee());
		info.setStorFee(bean.getStorFee());
		
	
		// 收利息-更新还款计划
		
		if ("1".equals(subject)) {
			this.retPlanService.repaymentInt(info);
		} 
		
		// 收本金-更新还款计划
		if ("2".equals(subject)) {
			this.retPlanService.repaymentPrincipal(info);
		}
//		Contract contract=contractService.queryByContractId(contractId);
//		// 交易表记账
//		BillAcct acct = new BillAcct();
//		acct.setLoanId(loanId);
//		acct.setContractId(contractId);
//		acct.setAmt(amt);
//		acct.setRemark(remark);
//		acct.setType("1"); // 收
//		acct.setSubject(subject);
//		acct.setProof(proof);
//		acct.setNum(curPeriod+"/"+contract.getNum());
//		acct.setOperTime(nowTime);
//		acct.setOperUid(loginId);
//		this.billAcctService.add(acct);
		
		// 公司帐户表记账
		BillBase base = new BillBase();
		base.setLoanId(loanId);
		base.setContractId(contractId);
		base.setAmt(amt);
		base.setProof(proof);
		base.setRemark(remark);
		base.setSubject(subject);
		base.setState("1");
		base.setType("1"); // 收
		base.setCreateTime(nowTime);
		base.setCreateUid(loginId);
		this.billBaseService.add(base);
	
		// 收本金--债权转让和线下方式--给出借人回款记账
		if ("2".equals(subject)) {
			if (Contract.LOAN_WAY_OFF_LINE.equals(getLoanWay) 
					|| Contract.LOAN_WAY_ZQ.equals(getLoanWay)) {
				
				base.setLendUsreId(lendUserId);
				base.setSubject("3"); // 付出借人回款
				base.setType("2"); // 付
				this.billBaseService.add(base);
				
				// 出借人资金变动记账
				LendUserLog log = new LendUserLog();
				log.setBizKey(loanId);
				log.setAmt(amt);
				log.setLendUserId(lendUserId);
				log.setProof(proof);
				log.setRemark(remark);
				log.setType("1"); // 付
				log.setCreateTime(nowTime);
				log.setCreateUid(loginId);
				this.lendUserLogService.add(log);
				
				// 更新出借人账户余额
				LendUser lUser = this.lendUserService.queryByKey(lendUserId);
				LendUser lendUser = new LendUser();
				lendUser.setId(lUser.getId());
				lendUser.setAmt(MathUtils.add(lUser.getAmt(), amt));
				this.lendUserService.updateOnlyChanged(lendUser);
			}
		}
		
		return new AccountJsonMsg(bean.getId(), loanId, contractId, true, "收款成功");
	}
}
