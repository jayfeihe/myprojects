package com.tera.report.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.model.BranchDealQBean;

public interface BranchDealDao {

	public List<BranchDealQBean> queryList(Map<String,Object> map);
}
