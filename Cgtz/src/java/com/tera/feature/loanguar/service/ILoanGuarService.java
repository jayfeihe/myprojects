package com.tera.feature.loanguar.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.loanguar.model.LoanGuar;

/**
 * 
 * T_LOAN_GUAR服务类
 * <b>功能：</b>LoanGuarDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-06 13:51:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface ILoanGuarService {

	
	public void add(LoanGuar... objs)  throws Exception ;
	
	
	public void update(LoanGuar obj)  throws Exception ;
	
	
	public void updateOnlyChanged(LoanGuar obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<LoanGuar> queryList(Map<String, Object> map) throws Exception ;

	public LoanGuar queryByKey(Object id) throws Exception;

	public PageList<LoanGuar> queryPageList(Map<String, Object> params);
	
}
