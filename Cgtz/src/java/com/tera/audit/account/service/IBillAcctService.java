package com.tera.audit.account.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.model.BillAcct;

public interface IBillAcctService {

	void add(BillAcct... objs) throws Exception;

	void update(BillAcct obj) throws Exception;

	void updateOnlyChanged(BillAcct obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<BillAcct> queryList(Map<String, Object> map) throws Exception;

	BillAcct queryByKey(Object id) throws Exception;

	PageList<BillAcct> queryPageList(Map<String, Object> queryMap);

}