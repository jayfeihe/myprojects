package com.greenkoo.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.greenkoo.sys.model.SysUser;

public interface SysUserDao {

	void add(SysUser user) throws Exception;
	
	int updatePwd(SysUser user);

	SysUser queryByUserId(String userId);

	int queryCount(Map<String, Object> paramMap);

	List<SysUser> queryList(Map<String, Object> paramMap);
	
	SysUser queryByAccountAndPwd(@Param("account") String account, @Param("pwd") String pwd);
}
