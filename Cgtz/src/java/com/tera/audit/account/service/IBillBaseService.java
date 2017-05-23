package com.tera.audit.account.service;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.BillBase;

public interface IBillBaseService {

	void add(BillBase... objs) throws Exception;

	void update(BillBase obj) throws Exception;

	void updateOnlyChanged(BillBase obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<BillBase> queryList(Map<String, Object> map) throws Exception;

	BillBase queryByKey(Object id) throws Exception;

}