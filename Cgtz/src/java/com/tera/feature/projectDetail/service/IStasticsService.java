package com.tera.feature.projectDetail.service;



import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.projectDetail.dao.StasticsDao;
import com.tera.feature.projectDetail.model.ProjectDetail;
import com.tera.feature.projectDetail.model.StasticsBean;

/**
 * 
 * T_ProjectDetail服务类
 * <b>功能：</b>ProjectDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IStasticsService {

    public List<StasticsBean> queryMonDealsList(Map<String, Object> map)throws Exception;
	
	public List<StasticsBean> queryDealsList(Map<String, Object> map)throws Exception;
	
	public List<StasticsBean> querySaveDealsList(Map<String, Object> map)throws Exception;

	public PageList<StasticsBean> queryMonDealsPageList(Map<String, Object> params)throws Exception;
	
	public PageList<StasticsBean> queryDealsPageList(Map<String, Object> params)throws Exception;

	
	public PageList<StasticsBean> querySaveDealsPageList(Map<String, Object> params)throws Exception;

}
