package com.tera.feature.asset.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.asset.dao.CollateralCheckDao;
import com.tera.feature.asset.dao.CollateralPriceChangeDao;
import com.tera.feature.asset.model.CollateralCheck;
import com.tera.feature.asset.model.CollateralPriceChange;
import com.tera.feature.asset.service.ICollateralPriceChangeService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 价值变动表服务类
 * <b>功能：</b>CollateralPriceChangeDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 11:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collateralPriceChangeService")
public class CollateralPriceChangeServiceImpl extends MybatisBaseService<CollateralPriceChange> implements ICollateralPriceChangeService{

	private final static Logger log = Logger.getLogger(CollateralPriceChangeServiceImpl.class);

	@Autowired(required=false)
    private CollateralPriceChangeDao dao;
	@Override
	@Transactional
	public void add(CollateralPriceChange... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollateralPriceChange obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(CollateralPriceChange obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(CollateralPriceChange obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<CollateralPriceChange> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public CollateralPriceChange queryByKey(Object id) throws Exception {
		return (CollateralPriceChange)dao.queryByKey(id);
	}	
	@Override
	public PageList<CollateralPriceChange> queryPageList(Map<String, Object> params) {
		return this.selectPageList(CollateralPriceChangeDao.class, "queryList", params);
	}
}
