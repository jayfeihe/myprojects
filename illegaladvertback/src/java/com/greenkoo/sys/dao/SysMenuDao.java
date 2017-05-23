package com.greenkoo.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greenkoo.sys.model.SysMenu;

public interface SysMenuDao {

	void add(SysMenu menu) throws Exception;
	
	int update(SysMenu menu) throws Exception;

	List<SysMenu> queryList();
	
	List<SysMenu> queryMenuByLevel(@Param("level") int level, @Param("status") Integer status);
}
