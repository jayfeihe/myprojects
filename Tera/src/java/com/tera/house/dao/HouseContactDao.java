package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseContact;
import com.tera.house.model.HouseContactHistory;

/**
 * 
 * 信用借款申请联系人表DAO
 * <b>功能：</b>HouseContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:22:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseContactDao {
		
	public void add(HouseContact obj);	
	
	public void update(HouseContact obj);
	
	public void updateOnlyChanged(HouseContact obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseContact> queryList(Map<String, Object> map);

	public List<HouseContact> queryListOrderByRelation(Map<String, Object> map);
	
	public List<HouseContactHistory> getRelationList(Object map); 

	public HouseContact queryByKey(Object obj);
}
