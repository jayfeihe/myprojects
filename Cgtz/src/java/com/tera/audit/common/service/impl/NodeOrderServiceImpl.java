package com.tera.audit.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.common.dao.NodeOrderDao;
import com.tera.audit.common.model.NodeOrder;
import com.tera.audit.common.service.INodeOrderService;

/**
 * 
 * 流程环节表服务类
 * <b>功能：</b>NodeOrderDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-09 14:19:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("nodeOrderService")
public class NodeOrderServiceImpl implements INodeOrderService {

	private final static Logger log = Logger.getLogger(NodeOrderServiceImpl.class);

	@Autowired(required=false)
    private NodeOrderDao dao;

	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.INodeOrderService#add(com.tera.audit.common.model.NodeOrder)
	 */
	@Override
	@Transactional
	public void add(NodeOrder... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(NodeOrder obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.INodeOrderService#update(com.tera.audit.common.model.NodeOrder)
	 */
	@Override
	@Transactional
	public void update(NodeOrder obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.INodeOrderService#updateOnlyChanged(com.tera.audit.common.model.NodeOrder)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(NodeOrder obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.INodeOrderService#delete(java.lang.Object)
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
	 * @see com.tera.audit.common.service.INodeOrderService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.INodeOrderService#queryList(java.util.Map)
	 */
	@Override
	public List<NodeOrder> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.common.service.INodeOrderService#queryByKey(java.lang.Object)
	 */
	@Override
	public NodeOrder queryByKey(Object id) throws Exception {
		return (NodeOrder)dao.queryByKey(id);
	}
	
}
