package com.tera.feature.lenduser.service;

import java.util.List;
import java.util.Map;



import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.lenduser.model.LendUserLog;

/**
 * 
 * 出借人资金变动记录服务类
 * <b>功能：</b>LendUserLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-10 22:42:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface ILendUserLogService {

	
	public void add(LendUserLog... objs)  throws Exception ;
	
	
	public void update(LendUserLog obj)  throws Exception ;

	public void updateOnlyChanged(LendUserLog obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<LendUserLog> queryList(Map<String, Object> map) throws Exception ;
	public LendUserLog queryByKey(Object id) throws Exception ;


	public PageList<LendUserLog> queryPageList(Map<String, Object> params);
	
}
