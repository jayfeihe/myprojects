package com.tera.feature.projectDetail.service.impl;



import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.projectDetail.dao.ProjectInfoDetailDao;
import com.tera.feature.projectDetail.model.ProjectDetail;
import com.tera.feature.projectDetail.service.IProjectInfoDetailService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * T_ProjectDetail服务类
 * <b>功能：</b>ProjectDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("projectInfoService")
public class ProjectInfoDetailServiceImpl extends MybatisBaseService<ProjectDetail> implements IProjectInfoDetailService{

	private final static Logger log = Logger.getLogger(ProjectInfoDetailServiceImpl.class);

	@Autowired(required=false)
    private ProjectInfoDetailDao dao;
	@Override
	@Transactional
	public void add(ProjectDetail... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(ProjectDetail obj : objs ){
			//is_end默认为0
			if(obj.getRealEndDate()!=null){
				obj.setIsEnd(ProjectDetail.IS_END_Y);
			}else{
				obj.setIsEnd(ProjectDetail.IS_END_N);
			}	
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(ProjectDetail obj)  throws Exception {
		if(obj.getRealEndDate()!=null&&obj.getIsEnd().equals(ProjectDetail.IS_END_N)){
			//添加了实际结束时间
			obj.setIsEnd(ProjectDetail.IS_END_Y);
		}
		dao.update(obj);
	}
	
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<ProjectDetail> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public ProjectDetail queryByKey(Object id) throws Exception {
		return (ProjectDetail)dao.queryByKey(id);
	}
	@Override
	public PageList<ProjectDetail> queryPageList(Map<String, Object> params) {
		return this.selectPageList(ProjectInfoDetailDao.class, "queryList", params);
	}
	
}
