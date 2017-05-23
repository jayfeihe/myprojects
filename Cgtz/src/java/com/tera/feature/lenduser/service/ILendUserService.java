package com.tera.feature.lenduser.service;



import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.lenduser.model.LendUser;

/**
 * 
 * 出借人基本信息维护服务类
 * <b>功能：</b>LendUserDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface ILendUserService {

	public void add(LendUser... objs)  throws Exception ;
	
	
	public void update(LendUser obj)  throws Exception ;
	
	
	public void updateOnlyChanged(LendUser obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<LendUser> queryList(Map<String, Object> map) throws Exception ;
	public LendUser queryByKey(Object id) throws Exception ;

	public PageList<LendUser> queryPageList(Map<String, Object> params);
	
}
