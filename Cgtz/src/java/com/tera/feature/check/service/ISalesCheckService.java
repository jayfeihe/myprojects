package com.tera.feature.check.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.model.SalesCheck;

/**
 * 
 * 业务员，稽查人员催收跟进记录表服务类
 * <b>功能：</b>SalesCheckLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface ISalesCheckService {

	
	
	public void add(SalesCheck... objs)  throws Exception ;
	
	
	public void update(SalesCheck obj)  throws Exception;
	
	
	public void updateOnlyChanged(SalesCheck obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<SalesCheck> queryList(Map<String, Object> map) throws Exception ;
	public SalesCheck queryByKey(Object id) throws Exception ;


	public PageList<SalesCheck> queryPageList(Map<String, Object> params);
}
