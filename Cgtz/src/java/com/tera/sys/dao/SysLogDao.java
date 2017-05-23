package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.SysLog;

/**
 * 
 * <br>
 * <b>功能：</b>SyslogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-23 13:09:42<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface SysLogDao{	
		
	/**
	 * 写日志
	 * @param sysLog sysLog
	 */
	void addSysLog(SysLog sysLog);
	
	/**
	 * 日志信息计数
	 * @param map map
	 * @return int
	 */
	int getSysLogCount(Map<String, Object> map);
	
	/**
	 * 日志信息
	 * @param map map
	 * @return list
	 */
	List<SysLog> querySysLogList(Map<String, Object> map);
	
		
	public void delete(SysLog t);	

	public SysLog queryByKey(SysLog t);

}
