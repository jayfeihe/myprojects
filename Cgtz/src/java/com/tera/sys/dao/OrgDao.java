package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.Org;

/**
 * 
 * <br>
 * <b>功能：</b>OrgDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 13:00:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface OrgDao {	
		
	public void add(Org t);	
	
	public void update(Org t);
	
	public void updateOnlyChanged(Org obj);
		
	public void delete(Object id);	

	public int queryCount(Map<String, Object> map);
	
	public List<Org> queryList(Map<String, Object> map);
	
	public Org queryByKey(Object id);

	public Org queryByOrgId(String OrgId);

	List<Org> getOrgByLoginId(String userId);
	
	/**
	 * 根据机构编码 查询 本身与 所有子机构 列表
	 * @param orgId
	 * @return
	 */
	List<Org> getSubOrg(Map<String, Object> map);
	
	//根据用户ID 删除 关联关系
	public void removeOrgUserById(int id);
	
	public void deleteSelfAndChlidren(String orgId);

	public List<Org> querySelectList(Map<String, Object> map);
}
