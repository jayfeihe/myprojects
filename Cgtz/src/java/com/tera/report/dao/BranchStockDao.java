package com.tera.report.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.model.BranchStockQBean;

public interface BranchStockDao {

	public List<BranchStockQBean> queryList(Map<String, Object> map);
}
