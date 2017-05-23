package com.tera.feature.cllt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.dao.CheckOverDueDao;
import com.tera.feature.check.model.CheckOverDue;
import com.tera.feature.cllt.dao.ClltDao;
import com.tera.feature.cllt.model.Cllt;
import com.tera.feature.cllt.service.IClltService;
import com.tera.sys.service.MybatisBaseService;

/**
 *功能:ClltServiceImpl  贷后催收服务
 *时间:2016年3月7日上午10:52:09
 *@author Ldh
 */
@Service("clltService")
public class ClltServiceImpl extends MybatisBaseService<Cllt> implements IClltService {
	private final static Logger log = Logger.getLogger(ClltServiceImpl.class);

	@Autowired(required=false)
    private ClltDao dao;
	
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<Cllt> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	@Override
	public PageList<Cllt> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(ClltDao.class, "queryList", params);
	}
}
