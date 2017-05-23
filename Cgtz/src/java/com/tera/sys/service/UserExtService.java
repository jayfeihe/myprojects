package com.tera.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.dao.UserExtDao;
import com.tera.sys.model.UserExt;

/**
 * 
 * 用户信息扩展表服务类
 * <b>功能：</b>UserExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 16:47:09<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("userExtService")
public class UserExtService {

	private final static Logger log = Logger.getLogger(UserExtService.class);

	@Autowired(required=false)
    private UserExtDao dao;

	@Transactional
	public void add(UserExt... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(UserExt obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(UserExt obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(UserExt obj)  throws Exception {
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
	
	public List<UserExt> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public UserExt queryByKey(Object id) throws Exception {
		return (UserExt)dao.queryByKey(id).get(0);
	}
}
