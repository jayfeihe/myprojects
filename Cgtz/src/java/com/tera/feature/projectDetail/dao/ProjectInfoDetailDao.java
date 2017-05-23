package com.tera.feature.projectDetail.dao;


import java.util.List;
import java.util.Map;

import com.tera.feature.projectDetail.model.ProjectDetail;

/**
 * 
 * ProjectDetail
 * <b>功能：</b>ProjectDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ProjectInfoDetailDao {
		
	public void add(ProjectDetail obj);	
	
	public void update(ProjectDetail obj);
		
	public void delete(Object obj);
	
	public int queryCount(Map<String, Object> map);
	
	public List<ProjectDetail> queryList(Map<String, Object> map);

	public ProjectDetail queryByKey(Object obj);

}
