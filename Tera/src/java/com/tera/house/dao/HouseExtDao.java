package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseExt;

/**
 * 
 * 信用贷款申请信息扩展表DAO
 * <b>功能：</b>HouseExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:25:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseExtDao {
		
	public void add(HouseExt obj);	
	
	public void update(HouseExt obj);
	
	public void updateOnlyChanged(HouseExt obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseExt> queryList(Map<String, Object> map);

	public HouseExt queryByKey(Object obj);

}
