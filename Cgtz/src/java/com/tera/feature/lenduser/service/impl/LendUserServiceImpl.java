package com.tera.feature.lenduser.service.impl;



import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.lenduser.dao.LendUserDao;
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.service.ILendUserService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 出借人基本信息维护服务类
 * <b>功能：</b>LendUserDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("lendUserService")
public class LendUserServiceImpl extends MybatisBaseService<LendUser> implements ILendUserService {

	private final static Logger log = Logger.getLogger(LendUserServiceImpl.class);

	@Autowired(required=false)
    private LendUserDao dao;
	@Override
	@Transactional
	public void add(LendUser... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(LendUser obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(LendUser obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(LendUser obj)  throws Exception {
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
	public List<LendUser> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public LendUser queryByKey(Object id) throws Exception {
		return (LendUser)dao.queryByKey(id);
	}
	@Override
	public PageList<LendUser> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LendUserDao.class, "queryList", params);
	}
}
