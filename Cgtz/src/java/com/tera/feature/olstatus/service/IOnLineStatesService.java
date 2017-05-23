package com.tera.feature.olstatus.service;



import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.olstatus.model.OnLineStates;
import com.tera.util.DateUtils;

/**
 * 
 * IOnLineStatesService服务
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface IOnLineStatesService {
    
	public void add(OnLineStates... objs)throws Exception;	
	
	public void update(OnLineStates obj)throws Exception;
		
	public void delete(Object... objs)throws Exception;
	
	public List<OnLineStates> queryList(Map<String, Object> map)throws Exception;

	public OnLineStates queryByKey(Object obj) throws Exception;
    
	public PageList<OnLineStates> queryPageList(Map<String, Object> params)throws Exception;
}

