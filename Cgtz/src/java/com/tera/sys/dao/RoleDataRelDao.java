package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.RoleDataRel;

/**
 * 
 * 机构的数据权限，和T_USER_EXT中的内容不一样DAO
 * <b>功能：</b>RoleDataRelDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 19:16:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RoleDataRelDao {
		
	public void add(RoleDataRel obj);	
	
	public void update(RoleDataRel obj);
	
	public void updateOnlyChanged(RoleDataRel obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RoleDataRel> queryList(Map<String, Object> map);

	public RoleDataRel queryByKey(Object obj);
	
	//根据登录id获取所有机构,用于下拉显示
	public List<RoleDataRel> queryOrgListByLoginId(Map<String, Object> map);

}
