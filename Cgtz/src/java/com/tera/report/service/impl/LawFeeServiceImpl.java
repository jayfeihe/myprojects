package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.LawFeeDao;
import com.tera.report.dao.NetFundsDao;
import com.tera.report.model.LawFee;
import com.tera.report.model.NetFunds;
import com.tera.report.service.ILawFeeService;
import com.tera.sys.service.MybatisBaseService;
@Service("lawFeeService")
public class LawFeeServiceImpl extends MybatisBaseService<LawFee> implements ILawFeeService{

	@Autowired(required=false)
    private LawFeeDao dao;
	
	@Override
	public int queryCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<LawFee> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}
	@Override
	public PageList<LawFee> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(LawFeeDao.class, "queryList", params);
	}

}
