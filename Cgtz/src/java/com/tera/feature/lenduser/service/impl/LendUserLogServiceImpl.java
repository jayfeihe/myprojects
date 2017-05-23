package com.tera.feature.lenduser.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.lenduser.dao.LendUserDao;
import com.tera.feature.lenduser.dao.LendUserLogDao;
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.model.LendUserLog;
import com.tera.feature.lenduser.service.ILendUserLogService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 出借人资金变动记录服务类
 * <b>功能：</b>LendUserLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-10 22:42:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("lendUserLogService")
public class LendUserLogServiceImpl extends MybatisBaseService<LendUserLog> implements ILendUserLogService{

	private final static Logger log = Logger.getLogger(LendUserLogServiceImpl.class);

	@Autowired(required=false)
    private LendUserLogDao dao;
	@Override
	@Transactional
	public void add(LendUserLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(LendUserLog obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(LendUserLog obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(LendUserLog obj)  throws Exception {
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
	public List<LendUserLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public LendUserLog queryByKey(Object id) throws Exception {
		return (LendUserLog)dao.queryByKey(id);
	}
	@Override
	public PageList<LendUserLog> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LendUserLogDao.class, "queryList", params);
	}
}
