package com.tera.feature.olstatus.service.impl;



import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.olstatus.dao.OnLineStatesDao;
import com.tera.feature.olstatus.model.OnLineStates;
import com.tera.feature.olstatus.service.IOnLineStatesService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * T_OnLineStates服务类
 * <b>功能：</b>OnLineStatesDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("onLineStatesService")
public class OnLineStatesServiceImpl extends MybatisBaseService<OnLineStates> implements IOnLineStatesService{

	private final static Logger log = Logger.getLogger(OnLineStatesServiceImpl.class);

	@Autowired(required=false)
    private OnLineStatesDao dao;
	@Override
	@Transactional
	public void add(OnLineStates... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(OnLineStates obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(OnLineStates obj)  throws Exception {
		dao.update(obj);
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
	public List<OnLineStates> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public OnLineStates queryByKey(Object id) throws Exception {
		return (OnLineStates)dao.queryByKey(id);
	}
	@Override
	public PageList<OnLineStates> queryPageList(Map<String, Object> params)throws Exception{
		return this.selectPageList(OnLineStatesDao.class, "queryList", params);
	}
	
}
