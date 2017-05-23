package com.tera.customer.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.customer.constant.CustomerConstants;
import com.tera.customer.dao.ContactDao;
import com.tera.customer.dao.CustomerDao;
import com.tera.customer.dao.CustomerExtDao;
import com.tera.customer.model.Contact;
import com.tera.customer.model.Customer;
import com.tera.customer.model.CustomerExt;
import com.tera.customer.model.form.CustomerFBean;
import com.tera.customer.util.CustomerUtil;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.model.Org;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 16:51:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("customerService")
public class CustomerService<T> {

	private final static Logger log = Logger.getLogger(CustomerService.class);

	@Autowired
    private CustomerDao dao;
	
	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private CustomerExtDao customerExtDao;

	/**
	 * 更新客户信息
	 * @param bean
	 * @param org
	 * @param loginid
	 */
	@Transactional
	public void updateCustomer(CustomerFBean bean, Org org, String loginid) {
		// TODO 处理更新保存操作
		// 1. bean 转换成customerExt = null;  Customer bean = null; Contact contact = null; CustomerExt
		Customer customer = CustomerUtil.getCustomer(bean);
		// 2. Customer.ID存在则更新操作Customer 否者插入
		if (bean.getId() != 0) {
			customer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			customer.setOperator(loginid);
			dao.updateOnlyChanged(customer);
		} else {
			customer.setState("1"); //第一次插入的状态是有效的
			customer.setCreateTime(new Timestamp(System.currentTimeMillis()));
			customer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			customer.setOperator(loginid);
			customer.setCustomerManager(loginid); //客户经理
			customer.setOrgId(org.getOrgId());
			dao.add(customer);
		}
		// 3. 联系查询Contact.CUSTOMER_ID,存在则更新,否者插入
		Contact contact = CustomerUtil.getContact(bean);
		if (contact.getId() != 0) {
			contact.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			contact.setOperator(loginid);
			contactDao.updateOnlyChanged(contact);
		} else {
			contact.setCustomerId(customer.getId());

			contact.setCreateTime(new Timestamp(System.currentTimeMillis()));
			contact.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			contact.setOperator(loginid);
			contact.setOrgId(org.getOrgId());
			contactDao.add(contact);
		}
		
		//客户类型是企业的才进行保存法人、财务负责人、实际控制人
		if (CustomerConstants.CUSTOMER_TYPE_ORG.equals(bean.getCustomerType())) {
			//法人
			Contact farenContact = CustomerUtil.getFarenContact(bean);
			if (farenContact.getId() != 0) {
				farenContact.setUpdateTime(new Timestamp(System
						.currentTimeMillis()));
				farenContact.setOperator(loginid);
				contactDao.updateOnlyChanged(farenContact);
			} else {
				farenContact.setCustomerId(customer.getId());

				farenContact.setCreateTime(new Timestamp(System
						.currentTimeMillis()));
				farenContact.setUpdateTime(new Timestamp(System
						.currentTimeMillis()));
				farenContact.setOperator(loginid);
				farenContact.setOrgId(org.getOrgId());
				contactDao.add(farenContact);
			}
			//财务负责人
			Contact cfoContact = CustomerUtil.getCfoContact(bean);
			if (cfoContact.getId() != 0) {
				cfoContact.setUpdateTime(new Timestamp(System
						.currentTimeMillis()));
				cfoContact.setOperator(loginid);
				contactDao.updateOnlyChanged(cfoContact);
			} else {
				cfoContact.setCustomerId(customer.getId());

				cfoContact.setCreateTime(new Timestamp(System
						.currentTimeMillis()));
				cfoContact.setUpdateTime(new Timestamp(System
						.currentTimeMillis()));
				cfoContact.setOperator(loginid);
				cfoContact.setOrgId(org.getOrgId());
				contactDao.add(cfoContact);
			}
			//实际控制人
			Contact kongzhiContact = CustomerUtil.getKongzhiContact(bean);
			if (kongzhiContact.getId() != 0) {
				kongzhiContact.setUpdateTime(new Timestamp(System
						.currentTimeMillis()));
				kongzhiContact.setOperator(loginid);
				contactDao.updateOnlyChanged(kongzhiContact);
			} else {
				kongzhiContact.setCustomerId(customer.getId());

				kongzhiContact.setCreateTime(new Timestamp(System
						.currentTimeMillis()));
				kongzhiContact.setUpdateTime(new Timestamp(System
						.currentTimeMillis()));
				kongzhiContact.setOperator(loginid);
				kongzhiContact.setOrgId(org.getOrgId());
				contactDao.add(kongzhiContact);
			}
		}
		// 4. 根据Customer.ID查询CustomerExt.CUSTOMER_ID,存在则更新,否者插入
		CustomerExt customerExt = CustomerUtil.getCustomerExt(bean);;
		if (customerExt.getCustomerId() != 0) {
			customerExt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			customerExt.setOperator(loginid);
			customerExtDao.updateOnlyChanged(customerExt);
		} else {
			customerExt.setCustomerId(customer.getId());

			customerExt.setCreateTime(new Timestamp(System.currentTimeMillis()));
			customerExt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			customerExt.setOperator(loginid);
			customerExt.setOrgId(org.getOrgId());
			customerExtDao.add(customerExt);
		}
		// 5. 操作完成
	}

	/**
	 * 客户信息置为无效
	 * @param id
	 */
	@Transactional
	public void deleteCustomer(String id) {
		Customer customer = (Customer) dao.queryByKey(id);
		customer.setState(BusinessConstants.STATE_FALSE);
		dao.update(customer);
	}

	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
	}

	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(T t)  throws Exception {
		dao.updateOnlyChanged(t);
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

	public List<T> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}

	public int queryCustomerCount(Map<String, Object> map)throws Exception {
		return dao.queryCustomerCount(map);
	}

	public List<T> queryCustomerList(Map<String, Object> map)throws Exception {
		return dao.queryCustomerList(map);
	}

}
