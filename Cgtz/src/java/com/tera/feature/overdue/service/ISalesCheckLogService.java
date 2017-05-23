package com.tera.feature.overdue.service;

import java.util.List;
import java.util.Map;



import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.overdue.model.SalesCheckLog;

/**
 * 
 * 业务员，稽查人员催收跟进记录表服务类
 * <b>功能：</b>SalesCheckLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface ISalesCheckLogService {

	
	
	public void add(SalesCheckLog... objs)  throws Exception ;
	
	
	public void update(SalesCheckLog obj)  throws Exception;
	
	
	public void updateOnlyChanged(SalesCheckLog obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<SalesCheckLog> queryList(Map<String, Object> map) throws Exception ;
	public SalesCheckLog queryByKey(Object id) throws Exception ;


	public PageList<SalesCheckLog> queryPageList(Map<String, Object> params)throws Exception;
}
