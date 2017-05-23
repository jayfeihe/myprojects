package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.BillAcctFlowDao;
import com.tera.report.model.BillAcctFlow;
import com.tera.report.service.IBillAcctFlowService;
import com.tera.sys.service.MybatisBaseService;
@Service("billAcctFlowService")
public class BillAcctFlowServiceImpl extends MybatisBaseService<BillAcctFlow> implements IBillAcctFlowService{

	@Autowired(required=false)
    private BillAcctFlowDao dao;
	
	@Override
	public int queryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<BillAcctFlow> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}
	@Override
	public PageList<BillAcctFlow> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(BillAcctFlowDao.class, "queryList", params);
	}

}
