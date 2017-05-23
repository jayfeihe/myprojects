package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.report.dao.ReportDealDao;
import com.tera.report.dao.ReportOverdueDao;
import com.tera.report.model.ReportDeal;
import com.tera.report.model.ReportOverdue;
import com.tera.report.service.IReportOverdueService;
import com.tera.sys.service.MybatisBaseService;
@Service("reportOverdueService")
public class ReportOverdueImpl extends MybatisBaseService<ReportOverdue> implements IReportOverdueService {
	@Autowired(required=false)
    private ReportOverdueDao dao;
	
	@Override
	public void add(ReportOverdue obj) throws Exception {
			dao.add(obj);
	}

	@Override
	public List<ReportOverdue> queryOverdueStatics(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryOverdueStatics(map);
	}
	
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<ReportOverdue> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}
	@Override
	public PageList<ReportOverdue> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(ReportOverdueDao.class, "queryList", params);
	}

}
