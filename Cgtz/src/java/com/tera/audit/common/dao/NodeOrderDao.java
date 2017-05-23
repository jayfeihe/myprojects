package com.tera.audit.common.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.common.model.NodeOrder;

/**
 * 
 * 流程环节表DAO
 * <b>功能：</b>NodeOrderDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-09 14:19:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface NodeOrderDao {
		
	public void add(NodeOrder obj);	
	
	public void update(NodeOrder obj);
	
	public void updateOnlyChanged(NodeOrder obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<NodeOrder> queryList(Map<String, Object> map);

	public NodeOrder queryByKey(Object obj);

}
