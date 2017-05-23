package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.ReportDealDao;
import com.tera.report.model.ReportDeal;
import com.tera.report.service.IReportDealService;
import com.tera.sys.service.MybatisBaseService;
@Service("reportDealService")
public class ReportDealImpl extends MybatisBaseService<ReportDeal> implements IReportDealService {
	@Autowired(required=false)
    private ReportDealDao dao;
	
	@Override
	public int queryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<ReportDeal> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}
	@Override
	public PageList<ReportDeal> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(ReportDealDao.class, "queryList", params);
	}
}
