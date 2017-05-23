package com.tera.feature.check.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.model.CheckOverDue;
import com.tera.feature.check.model.OverdueAccept;

public interface ICheckOverDueService {
	
	public void add(CheckOverDue... objs)  throws Exception ;
	
	public void update(CheckOverDue obj)  throws Exception ;
	
	public void updateOnlyChanged(CheckOverDue obj)  throws Exception ;
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<CheckOverDue> queryList(Map<String, Object> map) throws Exception ;
	public CheckOverDue queryByKey(Object id) throws Exception ;
	
	public PageList<CheckOverDue> queryPageList(Map<String, Object> params) throws Exception ;
}
