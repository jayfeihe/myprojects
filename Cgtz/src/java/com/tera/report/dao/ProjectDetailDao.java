package com.tera.report.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.model.ProjectDetailQBean;

public interface ProjectDetailDao {

	List<ProjectDetailQBean> queryList(Map<String, Object> queryMap);

}
