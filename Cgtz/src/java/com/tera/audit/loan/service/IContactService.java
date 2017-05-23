package com.tera.audit.loan.service;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.Contact;

public interface IContactService {

	void add(Contact... objs) throws Exception;

	void update(Contact obj) throws Exception;

	void updateOnlyChanged(Contact obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<Contact> queryList(Map<String, Object> map) throws Exception;

	Contact queryByKey(Object id) throws Exception;

	List<Contact> queryByLoanId(String loanId);

}