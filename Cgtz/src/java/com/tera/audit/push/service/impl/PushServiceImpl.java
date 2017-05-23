package com.tera.audit.push.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.push.service.IPushService;
import com.tera.interfaces.service.InterfaceService;
import com.tera.sys.model.JsonMsg;

/** 
 * 推送线上Service
 * @author QYANZE
 *
 */
@Service("pushService")
public class PushServiceImpl implements IPushService {

//	@Autowired
//	private IContractService contractService;
	
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private ILoanBaseService loanBaseService;
	
	@Override
	@Transactional
	public JsonMsg operateProcess(AccountFormBean bean, String loginId) throws Exception {
		
//		String contractId = bean.getContractId();
		String loanId = bean.getLoanId();
		String decision = "推送成功";
		String remark = bean.getRemark();
		
//		Contract contract = this.contractService.queryByContractId(contractId);
		// 流程结束
		this.commonHandlerService.workFlow(loanId, CommonConstant.PROCESS_END, CommonConstant.SYS_USER, loginId, decision,remark);
		
		// 推送线上接口调用
		JsonMsg jsonMsg = this.interfaceService.pushOnline(loanId);
		
		// 申请状态为推送成功
		LoanBase loanBase = loanBaseService.queryByLoanId(loanId);
		loanBase.setState1st("N");
		loanBase.setState2nd("2");
		loanBase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		loanBase.setUpdateUid(loginId);
		this.loanBaseService.update(loanBase);
		
		return jsonMsg;
	}
}
