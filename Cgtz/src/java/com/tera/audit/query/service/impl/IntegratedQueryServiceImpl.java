package com.tera.audit.query.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.query.dao.IntegratedQueryDao;
import com.tera.audit.query.model.IntegratedQueryBean;
import com.tera.audit.query.service.IIntegratedQueryService;
import com.tera.sys.service.MybatisBaseService;

@Service("integratedQueryService")
public class IntegratedQueryServiceImpl extends MybatisBaseService<IntegratedQueryBean> implements IIntegratedQueryService {

	@Override
	public PageList<IntegratedQueryBean> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(IntegratedQueryDao.class, "integratedQueryList", params);
	}
}
