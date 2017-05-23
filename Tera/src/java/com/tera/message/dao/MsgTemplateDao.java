package com.tera.message.dao;

import java.util.List;
import java.util.Map;

import com.tera.message.model.MsgTemplate;

/**
 * 
 * 模板表DAO
 * <b>功能：</b>TemplateDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 11:58:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface MsgTemplateDao {
		
	public void add(MsgTemplate obj);	
	
	public void update(MsgTemplate obj);
	
	public void updateOnlyChanged(MsgTemplate obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<MsgTemplate> queryList(Map<String, Object> map);

	public MsgTemplate queryByKey(Object obj);

	public MsgTemplate queryByType(Object type);

}
