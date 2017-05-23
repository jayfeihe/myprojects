package com.tera.interfaces.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.interfaces.dao.InterfaceLogDao;
import com.tera.interfaces.model.InterfaceLog;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.User;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 接口对接,日志服务类
 * <b>功能：</b>InterfaceLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-17 10:07:47<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("interfaceLogService")
public class InterfaceLogService extends MybatisBaseService<InterfaceLog> {

	private final static Logger log = Logger.getLogger(InterfaceLogService.class);

	@Autowired(required=false)
    private InterfaceLogDao dao;

	@Transactional
	public void add(InterfaceLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(InterfaceLog obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(InterfaceLog obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(InterfaceLog obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<InterfaceLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public InterfaceLog queryByKey(Object id) throws Exception {
		return (InterfaceLog)dao.queryByKey(id);
	}
	
	public PageList<InterfaceLog> queryPageList(Map<String, Object> params) {
		return this.selectPageList(InterfaceLogDao.class, "queryList", params);
	}
	
}
