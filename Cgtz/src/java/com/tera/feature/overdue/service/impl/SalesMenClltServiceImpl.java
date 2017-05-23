package com.tera.feature.overdue.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.cllt.model.Cllt;
import com.tera.feature.overdue.dao.SalesMenClltDao;
import com.tera.feature.overdue.model.SalesMenCllt;
import com.tera.feature.overdue.service.ISalesMenClltService;
import com.tera.sys.service.MybatisBaseService;
@Service("salesMenClltService")
public class SalesMenClltServiceImpl extends MybatisBaseService<SalesMenCllt> implements ISalesMenClltService {
	private final static Logger log = Logger.getLogger(OverdueReportServiceImpl.class);

	@Autowired(required=false)
    private SalesMenClltDao dao;

	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<SalesMenCllt> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public PageList<SalesMenCllt> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(SalesMenClltDao.class, "queryList", params);
	}
}
