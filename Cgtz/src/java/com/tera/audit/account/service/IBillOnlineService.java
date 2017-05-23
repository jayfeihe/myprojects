package com.tera.audit.account.service;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.BillOnline;

public interface IBillOnlineService {

	void add(BillOnline... objs) throws Exception;

	void update(BillOnline obj) throws Exception;

	void updateOnlyChanged(BillOnline obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<BillOnline> queryList(Map<String, Object> map) throws Exception;

	BillOnline queryByKey(Object id) throws Exception;

}