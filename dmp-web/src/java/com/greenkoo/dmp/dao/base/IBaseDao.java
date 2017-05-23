package com.greenkoo.dmp.dao.base;

import java.util.List;

import com.greenkoo.dmp.sys.model.Pager;

public interface IBaseDao<T> {

	public List<T> findAll(Class<T> clz);
	
	public Pager<T> pageList(Class<T> clz);
}
