package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.Depart;
import com.tera.sys.model.form.DepartTreeBean;

/**
 * 
 * 组织管理DAO
 * <b>功能：</b>DepartDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-22 18:05:02<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface DepartDao {
		
	public void add(Depart obj);
	
	public void addUserDepart(Map<String, Object> map);
	
	public void deleteUserDepart(Object obj);
	
	public void update(Depart obj);
	
	public void updateOnlyChanged(Depart obj);
		
	public void delete(Object obj);
	
	public void deleteOneselfAndChildren(Map<String, Object> map);
	
	public void updateDepartCode(Map<String, Object> map);

	public int queryCount(Map<String, Object> map);
	
	public List<Depart> queryList(Map<String, Object> map);

	public Depart queryByKey(Object obj);
	
	public List<DepartTreeBean> queryListByLevel(Map<String, Object> map);

	public List<DepartTreeBean> queryListByOrg(Map<String, Object> map);

	public List<String> queryUserDepartByLoginId(String loginId);

}
