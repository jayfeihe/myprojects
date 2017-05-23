package com.tera.audit.account.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.dao.MarginInfoDao;
import com.tera.audit.account.model.MarginInfo;
import com.tera.audit.account.service.IMarginInfoService;
import com.tera.feature.check.dao.AuditReportDao;
import com.tera.feature.check.model.AuditReport;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 保证金信息表服务类 <b>功能：</b>MarginInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-24 18:23:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("marginInfoService")
public class MarginInfoServiceImpl extends MybatisBaseService<MarginInfo>
		implements IMarginInfoService {

	private final static Logger log = Logger
			.getLogger(MarginInfoServiceImpl.class);

	@Autowired(required = false)
	private MarginInfoDao dao;

	@Override
	@Transactional
	public void add(MarginInfo... objs) throws Exception {
		if (objs == null || objs.length < 1) {
			return;
		}
		for (MarginInfo obj : objs) {
			dao.add(obj);
		}
	}

	@Override
	@Transactional
	public void update(MarginInfo obj) throws Exception {
		dao.update(obj);
	}

	@Override
	@Transactional
	public void updateOnlyChanged(MarginInfo obj) throws Exception {
		dao.updateOnlyChanged(obj);
	}

	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if (ids == null || ids.length < 1) {
			return;
		}
		for (Object id : ids) {
			dao.delete(id);
		}
	}

	@Override
	public int queryCount(Map<String, Object> map) throws Exception {
		return dao.queryCount(map);
	}

	@Override
	public List<MarginInfo> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	@Override
	public MarginInfo queryByKey(Object id) throws Exception {
		return (MarginInfo) dao.queryByKey(id);
	}

	@Override
	public PageList<MarginInfo> queryPageList(Map<String, Object> params) {
		return this.selectPageList(MarginInfoDao.class, "queryList", params);
	}

}
