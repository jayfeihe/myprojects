package com.tera.audit.account.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.account.dao.BillOnlineDao;
import com.tera.audit.account.model.BillOnline;
import com.tera.audit.account.service.IBillOnlineService;

/**
 * 
 * 线上放款表服务类
 * <b>功能：</b>BillOnlineDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:17:54<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("billOnlineService")
public class BillOnlineServiceImpl implements IBillOnlineService {

	private final static Logger log = Logger.getLogger(BillOnlineServiceImpl.class);

	@Autowired(required=false)
    private BillOnlineDao dao;

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillOnlineService#add(com.tera.audit.account.model.BillOnline)
	 */
	@Override
	@Transactional
	public void add(BillOnline... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(BillOnline obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillOnlineService#update(com.tera.audit.account.model.BillOnline)
	 */
	@Override
	@Transactional
	public void update(BillOnline obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillOnlineService#updateOnlyChanged(com.tera.audit.account.model.BillOnline)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(BillOnline obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillOnlineService#delete(java.lang.Object)
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
	 * @see com.tera.audit.account.service.impl.IBillOnlineService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillOnlineService#queryList(java.util.Map)
	 */
	@Override
	public List<BillOnline> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillOnlineService#queryByKey(java.lang.Object)
	 */
	@Override
	public BillOnline queryByKey(Object id) throws Exception {
		return (BillOnline)dao.queryByKey(id);
	}
	
}
