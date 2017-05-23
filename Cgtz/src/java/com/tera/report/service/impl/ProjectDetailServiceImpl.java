package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.ProjectDetailDao;
import com.tera.report.model.ProjectDetailQBean;
import com.tera.report.service.IProjectDetailService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 项目明细报表Service
 * @author QYANZE
 *
 */
@Service("projectDetailService")
public class ProjectDetailServiceImpl extends MybatisBaseService<ProjectDetailQBean> implements IProjectDetailService {

	@Autowired
	private ProjectDetailDao dao;
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.impl.IProjectDetailService#queryList(java.util.Map)
	 */
	@Override
	public List<ProjectDetailQBean> queryList(Map<String,Object> queryMap) {
		return dao.queryList(queryMap);
	}
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.impl.IProjectDetailService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<ProjectDetailQBean> queryPageList(Map<String,Object> params) {
		return this.selectPageList(ProjectDetailDao.class, "queryList", params);
	}
}
