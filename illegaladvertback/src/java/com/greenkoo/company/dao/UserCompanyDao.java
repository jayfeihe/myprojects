package com.greenkoo.company.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.greenkoo.company.model.UserCompany;
import com.greenkoo.company.model.form.UserCompanyVo;

public interface UserCompanyDao {

	void add(UserCompany company) throws Exception; 
	
	int update(UserCompany company) throws Exception;
	
	void updateStatus(UserCompany company) throws Exception;

	UserCompany queryByCompanyId(String companyId);
	
	UserCompany queryByCompanyUrlAndType(@Param("companyUrl") String companyUrl, @Param("type") String type);

	int queryCount(Map<String, Object> paramMap);
	
	List<UserCompanyVo> queryList(Map<String, Object> paramMap);
}
