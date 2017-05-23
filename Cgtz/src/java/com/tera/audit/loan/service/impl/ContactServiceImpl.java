package com.tera.audit.loan.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.loan.dao.ContactDao;
import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.service.IContactService;

/**
 * 
 * T_CONTACT服务类
 * <b>功能：</b>ContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 11:10:05<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("contactService")
public class ContactServiceImpl implements IContactService {

	private final static Logger log = Logger.getLogger(ContactServiceImpl.class);

	@Autowired(required=false)
    private ContactDao dao;

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.IContactService#add(com.tera.audit.loan.model.Contact)
	 */
	@Override
	@Transactional
	public void add(Contact... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Contact obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.IContactService#update(com.tera.audit.loan.model.Contact)
	 */
	@Override
	@Transactional
	public void update(Contact obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.IContactService#updateOnlyChanged(com.tera.audit.loan.model.Contact)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(Contact obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.IContactService#delete(java.lang.Object)
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
	 * @see com.tera.audit.loan.service.IContactService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.IContactService#queryList(java.util.Map)
	 */
	@Override
	public List<Contact> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.IContactService#queryByKey(java.lang.Object)
	 */
	@Override
	public Contact queryByKey(Object id) throws Exception {
		return (Contact)dao.queryByKey(id);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.IContactService#queryByLoanId(java.lang.String)
	 */
	@Override
	public List<Contact> queryByLoanId(String loanId) {
		return dao.queryByLoanId(loanId);
	}
	
}
