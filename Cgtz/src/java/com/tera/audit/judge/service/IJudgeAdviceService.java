package com.tera.audit.judge.service;

import java.util.List;
import java.util.Map;

import com.tera.audit.judge.model.JudgeAdv;
import com.tera.sys.model.JsonMsg;

public interface IJudgeAdviceService {

	void add(JudgeAdv... objs) throws Exception;

	void update(JudgeAdv obj) throws Exception;

	void updateOnlyChanged(JudgeAdv obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<JudgeAdv> queryList(Map<String, Object> map) throws Exception;

	JudgeAdv queryByKey(Object id) throws Exception;

	/**
	 * 保存评审会意见
	 * @param jAdv
	 * @throws Exception 
	 */
	void saveJudgeAdv(JudgeAdv jAdv) throws Exception;

	int getNextNum(String loanId);

	/**
	 * 评审会意见操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(JudgeAdv bean, String loginId) throws Exception;

}