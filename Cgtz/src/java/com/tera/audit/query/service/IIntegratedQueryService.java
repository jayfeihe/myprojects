package com.tera.audit.query.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.query.model.IntegratedQueryBean;

public interface IIntegratedQueryService {

	public PageList<IntegratedQueryBean> queryPageList(Map<String, Object> params) throws Exception;
}