package com.tera.audit.account.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.model.MarginInfo;
import com.tera.audit.account.model.form.OnLineLoanQBean;

/**
 * 
 * 保证金信息表DAO
 * <b>功能：</b>MarginInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-24 18:23:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface MarginInfoDao {
		
	public void add(MarginInfo obj);	
	
	public void update(MarginInfo obj);
	
	public void updateOnlyChanged(MarginInfo obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<MarginInfo> queryList(Map<String, Object> map);

	public MarginInfo queryByKey(Object obj);

}
