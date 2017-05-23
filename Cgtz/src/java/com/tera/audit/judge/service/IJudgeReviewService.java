package com.tera.audit.judge.service;

import com.tera.audit.judge.model.form.JudgeFormBean;
import com.tera.sys.model.JsonMsg;

public interface IJudgeReviewService {

	/**
	 * 评审会复核流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(JudgeFormBean bean, String loginId) throws Exception;

}