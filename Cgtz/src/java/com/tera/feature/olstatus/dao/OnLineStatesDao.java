package com.tera.feature.olstatus.dao;


import java.util.List;
import java.util.Map;

import com.tera.feature.olstatus.model.OnLineStates;

/**
 * 
 * T_OnLineStatesDAO
 * <b>功能：</b>OnLineStatesDao<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface OnLineStatesDao {
		
	public void add(OnLineStates obj);	
	
	public void update(OnLineStates obj);
		
	public void delete(Object obj);
	
	public List<OnLineStates> queryList(Map<String, Object> map);

	public OnLineStates queryByKey(Object obj);

}
