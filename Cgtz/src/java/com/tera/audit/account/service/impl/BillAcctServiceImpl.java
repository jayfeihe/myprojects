package com.tera.audit.account.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.dao.BillAcctDao;
import com.tera.audit.account.model.BillAcct;
import com.tera.audit.account.service.IBillAcctService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 借款人交易记录表
服务类
 * <b>功能：</b>BillAcctDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:14:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("billAcctService")
public class BillAcctServiceImpl extends MybatisBaseService<BillAcct> implements IBillAcctService {

	private final static Logger log = Logger.getLogger(BillAcctServiceImpl.class);

	@Autowired(required=false)
    private BillAcctDao dao;

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillAcctService#add(com.tera.audit.account.model.BillAcct)
	 */
	@Override
	@Transactional
	public void add(BillAcct... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(BillAcct obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillAcctService#update(com.tera.audit.account.model.BillAcct)
	 */
	@Override
	@Transactional
	public void update(BillAcct obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillAcctService#updateOnlyChanged(com.tera.audit.account.model.BillAcct)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(BillAcct obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillAcctService#delete(java.lang.Object)
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
	 * @see com.tera.audit.account.service.impl.IBillAcctService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillAcctService#queryList(java.util.Map)
	 */
	@Override
	public List<BillAcct> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IBillAcctService#queryByKey(java.lang.Object)
	 */
	@Override
	public BillAcct queryByKey(Object id) throws Exception {
		return (BillAcct)dao.queryByKey(id);
	}

	@Override
	public PageList<BillAcct> queryPageList(Map<String, Object> params) {
		return this.selectPageList(BillAcctDao.class, "queryList", params);
	}
	
}
