package com.tera.feature.check.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.check.model.CheckOverDue;

public interface CheckOverDueDao {
public void add(CheckOverDue obj);	
	
	public void update(CheckOverDue obj);
	
	public void updateOnlyChanged(CheckOverDue obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CheckOverDue> queryList(Map<String, Object> map);

	public CheckOverDue queryByKey(Object obj);
}
