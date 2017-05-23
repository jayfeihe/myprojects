package com.tera.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.dao.OrgDao;
import com.tera.sys.model.Org;
import com.tera.sys.model.form.DepartTreeBean;

/**
 * 
 * <br>
 * <b>功能：</b>OrgDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 13:00:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("orgService")
public class OrgService{

	private final static Logger log = Logger.getLogger(OrgService.class);

	@Autowired
    private OrgDao dao;

	@Transactional
	public void add(Org t)  throws Exception {
		dao.add(t);
	}
	
	@Transactional
	public void update(Org t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void delete(Object ... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public void deleteSelfAndChlidren(String orgId) throws Exception {
		dao.deleteSelfAndChlidren(orgId);
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<Org> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	public List<Org> queryDepartList(Map<String, Object> map) throws Exception {
		return dao.queryDepartList(map);
	}

	public Org queryByKey(Object id) throws Exception {
		return dao.queryByKey(id);
	}
	public Org queryByOrgId(String id) throws Exception {
		return dao.queryByOrgId(id);
	}
	
	public List<Org> getOrgByUserId(int userId) throws Exception {
		return dao.getOrgByUserId(userId);
	}
	
	public List<Org> getSubOrg(String orgId){
		return dao.getSubOrg(orgId);
	}
	@Transactional
	public void updateOrgByUserId(int id,String loginId, String[] orgIds){
		
		this.dao.removeOrgUserById(id);
		if (null == orgIds || orgIds.length == 0) {
			return;
		}
		for (String string : orgIds) {
			if(!string.equals("")){
				String[] key=string.split("_");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orgId", key[0]);
				map.put("roleId", key[1]);
				map.put("id", id);
				map.put("loginId", loginId);
				this.dao.addOrgUser(map);
			}
		}
		
	}

	/**
	 * 查询数据权限下拉集合
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	public List<Org> queryListByQuery(List<Org> orgList) throws Exception {
		if(orgList.size() == 1) return orgList;
		List<Org> orgListSum1 = new ArrayList<Org>();
		for (Org org : orgList) {
 			if(org.getOrgId().length() == 2)
				orgListSum1.add(org);
		}
		for (Org org : orgListSum1) {
			getOrgSelectList(org, orgList, 2);
		}
		return orgListSum1;
	}
	
	/**
	 * 递归查询二级以下的数据权限
	 * @param org
	 * @param orgList
	 * @param level
	 */
	public void getOrgSelectList(Org org, List<Org> orgList, int level){
		List<Org> orgListChildrens = new ArrayList<Org>();
		for (Org org1 : orgList) {
			if((Integer.parseInt(org.getLevel()) + 1) == Integer.parseInt(org1.getLevel()) && org.getOrgId().equals(org1.getOrgId().substring(0, org1.getOrgId().length() - level))){
				orgListChildrens.add(org1);
			}
		}
		org.setChildren(orgListChildrens);
		level = 4;
		for (Org org2 : orgListChildrens) {
			getOrgSelectList(org2, orgList, level);
		}
	}

	public List<DepartTreeBean> queryTree(Map<String, Object> map) throws Exception {
		List<DepartTreeBean> list = this.queryListByOrg(map);
		this.getDepartListByLevel(list,map);
		return list;
	}

	/**
	 * 递归组织树
	 * @param treeList
	 * @param map
	 * @throws Exception
	 */
	private void getDepartListByLevel(List<DepartTreeBean> treeList,Map<String, Object> map) throws Exception {
		for (int i=0;i<treeList.size();i++) {
			int j=0;
			Map<String,Object> queryMap=new HashMap<String,Object>();
			DepartTreeBean depart=treeList.get(i);
			queryMap.put("parentId", depart.getId());
			j=Integer.parseInt(depart.getLevel())+1;
			queryMap.put("level",j);
			List<DepartTreeBean> tmpTreeList = this.queryListByLevel(queryMap);
			depart.setParentId(Integer.parseInt(depart.getUId()));
			depart.setChildren(tmpTreeList);
			getDepartListByLevel(tmpTreeList, map);
		}
	}
		public List<DepartTreeBean> queryListByLevel(Map<String, Object> map) {
		return dao.queryListByLevel(map);
	}

	public List<DepartTreeBean> queryListByOrg(Map<String, Object> map) {
		return dao.queryListByOrg(map);
	}
	 
	 
}
