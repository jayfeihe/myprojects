package com.tera.audit.common.service;

import java.util.List;
import java.util.Map;

import com.tera.audit.common.model.NodeOrder;

public interface INodeOrderService {

	void add(NodeOrder... objs) throws Exception;

	void update(NodeOrder obj) throws Exception;

	void updateOnlyChanged(NodeOrder obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<NodeOrder> queryList(Map<String, Object> map) throws Exception;

	NodeOrder queryByKey(Object id) throws Exception;

}