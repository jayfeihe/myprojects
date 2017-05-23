package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.dao.CollateralDao;
import com.tera.audit.loan.model.Collateral;
import com.tera.report.dao.CollateralAccountBeanDao;
import com.tera.report.model.CollateralAccountBean;
import com.tera.report.service.ICollateralAccountBeanService;
import com.tera.sys.service.MybatisBaseService;
@Service("collateralAccountBeanService")
public class CollateralAccountBeanServiceImpl extends MybatisBaseService<CollateralAccountBean> implements ICollateralAccountBeanService{

	@Autowired(required=false)
    private CollateralAccountBeanDao dao;
	
	@Override
	public int queryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<CollateralAccountBean> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}
	@Override
	public PageList<CollateralAccountBean> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(CollateralAccountBeanDao.class, "queryList", params);
	}

}
