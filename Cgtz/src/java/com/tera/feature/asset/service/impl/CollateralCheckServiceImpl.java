package com.tera.feature.asset.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.asset.dao.AssetDao;
import com.tera.feature.asset.dao.CollateralCheckDao;
import com.tera.feature.asset.model.Asset;
import com.tera.feature.asset.model.CollateralCheck;
import com.tera.feature.asset.service.ICollateralCheckService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 押品检查信息记录
服务类
 * <b>功能：</b>CollateralCheckDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 10:11:56<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collateralCheckService")
public class CollateralCheckServiceImpl extends MybatisBaseService<CollateralCheck> implements ICollateralCheckService {

	private final static Logger log = Logger.getLogger(CollateralCheckServiceImpl.class);

	@Autowired(required=false)
    private CollateralCheckDao dao;
	@Override
	@Transactional
	public void add(CollateralCheck... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollateralCheck obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(CollateralCheck obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(CollateralCheck obj)  throws Exception {
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
	public List<CollateralCheck> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public CollateralCheck queryByKey(Object id) throws Exception {
		return (CollateralCheck)dao.queryByKey(id);
	}
	@Override
	public PageList<CollateralCheck> queryPageList(Map<String, Object> params) {
		return this.selectPageList(CollateralCheckDao.class, "queryList", params);
	}
}
