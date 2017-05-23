package com.tera.audit.judge.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.judge.model.JudgeAdv;

/**
 * 
 * 评审会意见DAO
 * <b>功能：</b>JudgeAdvDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-11 15:43:53<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface JudgeAdvDao {
		
	public void add(JudgeAdv obj);	
	
	public void update(JudgeAdv obj);
	
	public void updateOnlyChanged(JudgeAdv obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<JudgeAdv> queryList(Map<String, Object> map);

	public JudgeAdv queryByKey(Object obj);

	public Integer getNum(String loanId);
}
