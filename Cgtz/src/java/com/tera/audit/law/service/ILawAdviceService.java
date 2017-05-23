package com.tera.audit.law.service;

import com.tera.audit.law.model.form.LawAdviceFormBean;
import com.tera.sys.model.JsonMsg;

public interface ILawAdviceService {

	/**
	 * 法律意见操作
	 * @param formBean
	 * @param loginId
	 * @return
	 * @throws Exception 
	 */
	JsonMsg operateProcess(LawAdviceFormBean formBean, String loginId) throws Exception;

}