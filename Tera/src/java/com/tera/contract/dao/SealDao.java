package com.tera.contract.dao;

import java.util.List;
import java.util.Map;

import com.tera.contract.model.Seal;

/**
 * 
 * DAO
 * <b>功能：</b>SealDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-29 13:26:30<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface SealDao {
		
	public void add(Seal obj);	
	
	public void update(Seal obj);
	
	public void updateOnlyChanged(Seal obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Seal> queryList(Map<String, Object> map);

	public Seal queryByKey(Object obj);
	/**
	 * 取得随机数
	 */
	public String getSealCode();

}
