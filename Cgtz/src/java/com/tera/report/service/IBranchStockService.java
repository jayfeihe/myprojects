package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.BranchStockQBean;

public interface IBranchStockService {

	/**
	 * 列表查询
	 * @param queryMap
	 * @return
	 */
	List<BranchStockQBean> queryList(Map<String, Object> queryMap);

	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	PageList<BranchStockQBean> queryPageList(Map<String, Object> params);

}