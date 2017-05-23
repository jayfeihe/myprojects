package com.tera.channeltotal.dao;

import java.util.List;
import java.util.Map;

import com.tera.channeltotal.model.ChannelTotal;

/**
 * 
 * 渠道汇总管理表DAO
 * <b>功能：</b>ChannelTotalDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 15:35:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ChannelTotalDao {
		
	public void add(ChannelTotal obj);	
	
	public void update(ChannelTotal obj);
	
	public void updateOnlyChanged(ChannelTotal obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ChannelTotal> queryList(Map<String, Object> map);

	public ChannelTotal queryByKey(Object obj);

}
