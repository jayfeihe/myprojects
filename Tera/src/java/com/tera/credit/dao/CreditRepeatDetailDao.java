package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditRepeatDetail;

/**
 * 
 * 信用贷款申请查重信息详细表DAO
 * <b>功能：</b>CreditRepeatDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-29 16:03:00<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditRepeatDetailDao {
		
	public void add(CreditRepeatDetail obj);	
	
	public void update(CreditRepeatDetail obj);
	
	public void updateOnlyChanged(CreditRepeatDetail obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditRepeatDetail> queryList(Map<String, Object> map);
	
	public List<CreditRepeatDetail> queryListGroupBy(Map<String, Object> map);

	public CreditRepeatDetail queryByKey(Object obj);
	
	public List<CreditRepeatDetail> repeatQueryByNoId(Map<String, Object> map);

	public List<CreditRepeatDetail> repeatQueryByContact(Map<String, Object> map);
	
	public List<CreditRepeatDetail> repeatQueryByKeyValue(Map<String, Object> map);

	/**
	 * 根据 APP_ID 清空 当前申请的 重复记录
	 * @param appId
	 */
	public void deleteByAppId(String appId);
	
	
}
