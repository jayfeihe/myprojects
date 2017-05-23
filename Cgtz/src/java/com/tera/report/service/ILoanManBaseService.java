package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.LoanManBaseQBean;

public interface ILoanManBaseService {

	/**
	 * 列表查询
	 * @param queryMap
	 * @return
	 */
	List<LoanManBaseQBean> queryList(Map<String, Object> queryMap);

	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	PageList<LoanManBaseQBean> queryPageList(Map<String, Object> params);

}