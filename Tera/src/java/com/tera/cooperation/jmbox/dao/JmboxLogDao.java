package com.tera.cooperation.jmbox.dao;

import java.util.List;
import java.util.Map;

import com.tera.cooperation.jmbox.model.JmboxLog;
import com.tera.cooperation.jmbox.model.form.DefaultCustomersInfo;

/**
 * 
 * 积木盒子交互日志表DAO
 * <b>功能：</b>JmboxLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-11-13 15:53:59<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface JmboxLogDao {
		
	public void add(JmboxLog obj);	
	
	public void update(JmboxLog obj);
	
	public void updateOnlyChanged(JmboxLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<JmboxLog> queryList(Map<String, Object> map);

	public JmboxLog queryByKey(Object obj);

	/**
	 * 客户违约信息  JM违约 信息推送接口专用
	 * @param map
	 * @return
	 */
	public List<DefaultCustomersInfo> getDefaultCustomersList(Map<String, Object> map);
	public int getDefaultCustomersCount(Map<String, Object> map);
	
}
