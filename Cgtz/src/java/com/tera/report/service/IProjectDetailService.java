package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.ProjectDetailQBean;

public interface IProjectDetailService {

	/**
	 * 列表查询
	 * @param queryMap
	 * @return
	 */
	List<ProjectDetailQBean> queryList(Map<String, Object> queryMap);

	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	PageList<ProjectDetailQBean> queryPageList(Map<String, Object> params);

}