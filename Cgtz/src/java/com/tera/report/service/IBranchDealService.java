package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.BranchDealQBean;

public interface IBranchDealService {

	/**
	 * 列表查询
	 * @param queryMap
	 * @return
	 */
	List<BranchDealQBean> queryList(Map<String, Object> queryMap);

	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	PageList<BranchDealQBean> queryPageList(Map<String, Object> params);

}