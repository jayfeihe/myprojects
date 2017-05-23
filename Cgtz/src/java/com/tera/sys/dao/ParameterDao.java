package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.Parameter;

/**
 * 
 * <br>
 * <b>功能：</b>ParameterDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 17:27:37<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ParameterDao{	
		
	public void add(Parameter t);	
	
	public void update(Parameter t);
	
	public void updateOnlyChanged(Parameter t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<Parameter> queryList(Map<String, Object> map);

	public Parameter queryByKey(Object t);
	
	public Parameter queryByParamName(String paramName);

}
