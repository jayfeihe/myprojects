package com.tera.losecredit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tera.sys.service.MybatisBaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.tera.losecredit.dao.LoseCreditDao;
import com.tera.losecredit.model.LoseCredit;

/**
 * 
 * 服务类
 * <b>功能：</b>LoseCreditDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-06 11:43:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loseCreditService")
public class LoseCreditService extends MybatisBaseService<LoseCredit> {

	private final static Logger log = Logger.getLogger(LoseCreditService.class);

	@Autowired(required=false)
    private LoseCreditDao dao;

	@Transactional
	public void add(LoseCredit... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(LoseCredit obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(LoseCredit obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(LoseCredit obj)  throws Exception {
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
	public PageList<LoseCredit> queryList(Map<String, Object> params) throws Exception {
		return this.selectPageList(LoseCreditDao.class, "queryList", params);
	}

	public LoseCredit queryByKey(Object id) throws Exception {
		return (LoseCredit)dao.queryByKey(id);
	}
	
}
