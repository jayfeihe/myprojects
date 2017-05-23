package com.tera.report.credit.dao;

import java.util.List;
import java.util.Map;
import com.tera.report.credit.model.ViolateReportBean;

public interface ViolateReportDao {

	public List<ViolateReportBean> queryList(Map<String, Object> map);
	
	public ViolateReportBean queryAmount(Map<String,Object> map);
}
