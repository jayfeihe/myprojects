package com.tera.audit.branch.service;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.sys.model.JsonMsg;

public interface IBranchAuditService {

	/**
	 * 分公司初审流程操作
	 * @param bean
	 * @param loginId 登录id
	 * @param orgId 登录机构
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(AuditFormBean bean, String loginId,String orgId) throws Exception;

}