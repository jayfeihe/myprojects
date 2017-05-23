package com.tera.audit.account.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.account.dao.BillBaseDao;
import com.tera.audit.account.model.BillBase;
import com.tera.audit.account.service.IBillBaseService;

/**
 * 
 * 公司收支明细表服务类
 * <b>功能：</b>BillBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:15:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("billBaseService")
public class BillBaseServiceImpl implements IBillBaseService {

	private final static Logger log = Logger.getLogger(BillBaseServiceImpl.class);

	@Autowired(required=false)
    private BillBaseDao dao;

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillBaseService#add(com.tera.audit.account.model.BillBase)
	 */
	@Override
	@Transactional
	public void add(BillBase... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(BillBase obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillBaseService#update(com.tera.audit.account.model.BillBase)
	 */
	@Override
	@Transactional
	public void update(BillBase obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillBaseService#updateOnlyChanged(com.tera.audit.account.model.BillBase)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(BillBase obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillBaseService#delete(java.lang.Object)
	 */
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
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillBaseService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillBaseService#queryList(java.util.Map)
	 */
	@Override
	public List<BillBase> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillBaseService#queryByKey(java.lang.Object)
	 */
	@Override
	public BillBase queryByKey(Object id) throws Exception {
		return (BillBase)dao.queryByKey(id);
	}
	
}
