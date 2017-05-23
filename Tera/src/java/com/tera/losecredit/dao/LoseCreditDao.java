package com.tera.losecredit.dao;

import java.util.List;
import java.util.Map;

import com.tera.losecredit.model.LoseCredit;

/**
 * 
 * DAO
 * <b>功能：</b>LoseCreditDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-06 11:43:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoseCreditDao {
		
	public void add(LoseCredit obj);	
	
	public void update(LoseCredit obj);
	
	public void updateOnlyChanged(LoseCredit obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<LoseCredit> queryList(Map<String, Object> map);

	public LoseCredit queryByKey(Object obj);

}
