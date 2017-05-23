package com.tera.match.dao;

import java.util.List;
import java.util.Map;

import com.tera.lend.model.form.MatchManageQBean;
import com.tera.match.model.Lend2match;

/**
 * 
 * <br>
 * <b>功能：</b>Lend2matchDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-11 09:13:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface Lend2matchDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);
	
	//独占锁查询方法
	public List<T> queryLockList(Map<String, Object> map);
	
	//杨长收添加
	public int queryLend2MatchCount(Map<String, Object> map);
	
	//杨长收添加
	public List<T> queryLend2MatchList(Map<String, Object> map);
	
//杨长收添加(人工撮合审批时添加，根据传过来的loan2matchId查询matchresult表中的出借申请号，同时state状态为“1”)
	public int queryManualMatchVerifyLend2MatchCount(Map<String, Object> map);
	
	//杨长收添加(人工撮合审批时添加，根据传过来的loan2matchId查询matchresult表中的出借申请号，同时state状态为“1”)
	public List<T> queryManualMatchVerifyLend2MatchList(Map<String, Object> map);
	
	public List<T> queryBasicLockList(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public void updateToLock();
	
	public void updateToUnLock();
	
	public void updateMatchTimes();
	
	public List<Lend2match> queryMatchManageList(Map<String, Object> map);
	public int queryMatchManageCount(Map<String, Object> map);
	
	//查询状态为1或者2的集合
	public List<T> queryUnFinishList();
	
}
