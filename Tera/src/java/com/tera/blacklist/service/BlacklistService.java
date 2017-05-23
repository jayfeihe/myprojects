package com.tera.blacklist.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.service.MybatisBaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.blacklist.dao.BlacklistDao;
import com.tera.blacklist.model.Blacklist;

/**
 * 
 * 黑名单表服务类
 * <b>功能：</b>BlacklistDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-09-29 12:15:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("blacklistService")
public class BlacklistService extends MybatisBaseService<Blacklist> {

	private final static Logger log = Logger.getLogger(BlacklistService.class);

	@Autowired(required=false)
    private BlacklistDao dao;

	@Transactional
	public void add(Blacklist... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Blacklist obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(Blacklist obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(Blacklist obj)  throws Exception {
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
	
	/**
	 * 分页查询
	 * @param params
	 * 				key
	 * 					curPage   当前页数
	 * 					pageSize  每页条数
	 * @return
	 */
	public PageList<Blacklist> queryList(Map<String, Object> params) throws Exception {
		return this.selectPageList(BlacklistDao.class, "queryList", params);
	}

	public Blacklist queryByKey(Object id) throws Exception {
		return (Blacklist)dao.queryByKey(id);
	}
	
}
