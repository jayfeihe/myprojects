package com.tera.feature.overdue.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.overdue.model.SalesMenCllt;

public interface SalesMenClltDao {
public void add(SalesMenCllt obj);	

	public int queryCount(Map<String, Object> map);
	
	public List<SalesMenCllt> queryList(Map<String, Object> map);
}
