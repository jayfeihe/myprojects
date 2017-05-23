package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.NetFundsDao;
import com.tera.report.model.NetFunds;
import com.tera.report.service.ICollateralAccountBeanService;
import com.tera.report.service.INetFundsService;
import com.tera.sys.service.MybatisBaseService;
@Service("netFundsService")
public class NetFundsServiceImpl extends MybatisBaseService<NetFunds> implements INetFundsService{

	@Autowired(required=false)
    private NetFundsDao dao;
	
	@Override
	public int queryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<NetFunds> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}
	@Override
	public PageList<NetFunds> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(NetFundsDao.class, "queryList", params);
	}

}
