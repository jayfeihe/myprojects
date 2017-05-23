package com.tera.audit.account.service;

import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.AccountJsonMsg;
import com.tera.audit.branch.model.AuditFormBean;
import com.tera.sys.model.JsonMsg;

public interface IPreLoanAccountService {

	/**
	 * 贷前核帐申请放款流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(AuditFormBean formBean, String loginId, String orgId) throws Exception;

	/**
	 * 贷前收息记账
	 * @param base
	 * @param loginId
	 * @return
	 * @throws Exception 
	 */
	AccountJsonMsg collectInterest(AccountFormBean bean, String loginId) throws Exception;

}