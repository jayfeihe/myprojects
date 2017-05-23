package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.Dept;

/**
 * 
 * 部门机构表DAO
 * <b>功能：</b>DeptDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 09:38:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface DeptDao {
		
	public void add(Dept obj);	
	
	public void update(Dept obj);
	
	public void updateOnlyChanged(Dept obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Dept> queryList(Map<String, Object> map);

	public Dept queryByKey(Object obj);

}
