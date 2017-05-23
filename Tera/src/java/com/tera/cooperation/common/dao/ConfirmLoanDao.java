package com.tera.cooperation.common.dao;

import java.util.List;
import java.util.Map;

import com.tera.cooperation.common.model.ConfirmLoanQBean;

public interface ConfirmLoanDao {

public int queryConfirmLoanCount(Map<String, Object> map);
	
	public List<ConfirmLoanQBean> queryConfirmLoanList(Map<String, Object> map);
}
