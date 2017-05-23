package com.tera.bpm.dao;

import java.util.List;
import java.util.Map;

import com.tera.bpm.model.BpmParameter;

/**
 * 
 * 流程参数DAO
 * <b>功能：</b>BpmParameterDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-10-30 15:12:24<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BpmParameterDao {
		
	public void add(BpmParameter parameter);	
	
	public void update(BpmParameter parameter);
			
	public void delete(Object obj);
	
	public List<BpmParameter> getBpmParameter(Map<String, Object> map);

}
