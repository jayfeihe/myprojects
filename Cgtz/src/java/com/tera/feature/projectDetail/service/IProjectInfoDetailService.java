package com.tera.feature.projectDetail.service;



import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.projectDetail.model.ProjectDetail;

/**
 * 
 * T_ProjectDetail服务类
 * <b>功能：</b>ProjectDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IProjectInfoDetailService {

	public void add(ProjectDetail... objs)  throws Exception ;
	
	public void update(ProjectDetail obj)  throws Exception ;
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<ProjectDetail> queryList(Map<String, Object> map) throws Exception;

	public ProjectDetail queryByKey(Object id) throws Exception ;

	public PageList<ProjectDetail> queryPageList(Map<String, Object> params);
}
