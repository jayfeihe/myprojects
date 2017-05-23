package com.tera.sys.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.dao.DepartDao;
import com.tera.sys.model.Depart;
import com.tera.sys.model.Org;
import com.tera.sys.model.form.DepartTreeBean;

/**
 * 
 * 组织管理服务类
 * <b>功能：</b>DepartDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-22 18:05:02<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("departService")
public class DepartService {

	private final static Logger log = Logger.getLogger(DepartService.class);

	@Autowired(required=false)
    private DepartDao dao;
	
	@Autowired(required=false) //自动注入
	private OrgService orgService;

	@Transactional
	public void add(Depart... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Depart obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(Depart obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(Depart obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	@Transactional
	public void addUserDepart(Map<String, Object> map) throws Exception {
		dao.addUserDepart(map);
	}
	
	@Transactional
	public void deleteUserDepart(String loginId) throws Exception {
		dao.deleteUserDepart(loginId);
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<Depart> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	public List<DepartTreeBean> queryListByLevel(Map<String, Object> map) {
		return dao.queryListByLevel(map);
	}
	public List<DepartTreeBean> queryListByOrg(Map<String, Object> map) {
		return dao.queryListByOrg(map);
	}
	
	/**
	 * 查询组织树根据Level
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DepartTreeBean> queryTreeByLevel(Map<String, Object> map) throws Exception {
		List<DepartTreeBean> list = this.queryListByLevel(map);
		if (list.size() <= 0 ) {
			return list;
		}
		
		List<DepartTreeBean> treeList = new ArrayList<DepartTreeBean>();
		treeList.add(list.get(0));
		this.getDepartListByLevel(treeList,map);
		return treeList;
	}
	

	
	public List<DepartTreeBean> queryTreeByLevelAndOrgId(Map<String, Object> map) throws Exception {
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
		for (DepartTreeBean depart : treeList) {
			map.put("parentId", depart.getUId());
			List<DepartTreeBean> tmpTreeList = this.queryListByLevel(map);
			depart.setChildren(tmpTreeList);
			getDepartListByLevel(tmpTreeList, map);
		}
	}
	
	public List<Depart> queryListByQuery(Map<String, Object> map, String id) throws Exception {
		List<Depart> departList = dao.queryList(map);
		// 如果有查询条件的话直接返回
		if(-1 != (Integer)map.get("parentId")) return departList;
		
		int noId = null != id && !"".equals(id) ? Integer.parseInt(id) : 0;
		this.getDepartList(departList, noId);
		return departList;
	}
	
	/**
	 * 根据销售人员所在部门查询其下组织(用于销售人员管理)
	 * @param map
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Depart> queryListByOrgId(Map<String, Object> map, String id) throws Exception {
		List<Depart> departList = dao.queryList(map);
		if(departList.size() <= 0){
			return departList;
		}
		List<Depart> departList1 = new ArrayList<Depart>();
		departList1.add(departList.get(0));
		int noId = null != id && !"".equals(id) ? Integer.parseInt(id) : 0;
		this.getDepartList(departList1, noId);
		return departList1;
	}
	
	private void getDepartList(List<Depart> departList, int id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("noId", id);
		map.put("state", "1");
		for (Depart depart : departList) {
			map.put("parentId", depart.getId());
			List<Depart> departList1 = this.queryList(map);
			depart.setChildren(departList1);
			getDepartList(departList1, id);
		}
	}

	public Depart queryByKey(Object id) throws Exception {
		return (Depart)dao.queryByKey(id);
	}
	
	/**
	 * 删除自身节点及以下的子孩子
	 * @param map
	 */
	public void deleteOneselfAndChildren(Map<String, Object> map){
		dao.deleteOneselfAndChildren(map);
	}
	
	/**
	 * 修改本身节点及以下的子孩子的组织代码
	 * @param map
	 */
	public void updateDepartCode(Map<String, Object> map){
		dao.updateDepartCode(map);
	}
	
	/**
	 * 保存组织
	 * @param bean
	 * @param loginId
	 * @throws Exception
	 */
	@Transactional
	public void save(Depart bean, String loginId) throws Exception{
		Timestamp newTime = new Timestamp(System.currentTimeMillis());
		Depart parentBean = this.queryByKey(bean.getParentId());
		bean.setState("1");
		bean.setLevel(Integer.parseInt(parentBean.getLevel()) + 1 + "");
		bean.setOperator(loginId);
		Org org = orgService.queryByKey(bean.getOrgId());
		bean.setOrgId(org.getOrgId());
		//如果存在
		if (bean.getId() != 0) {
			Depart bean1 = this.queryByKey(bean.getId());
			String oldCode = bean1.getDepartCode();
			bean.setUpdateTime(newTime);
			bean.setDepartCode(parentBean.getDepartCode()+bean.getId());
			this.updateOnlyChanged(bean);
			Map<String, Object> fMap = new HashMap<String, Object>();
			fMap.put("oldCode", oldCode);
			fMap.put("departCode", bean.getDepartCode());
			fMap.put("orgId", org.getOrgId());
			this.updateDepartCode(fMap);
		} else { //如果不存在
			bean.setCreateTime(newTime);
			bean.setUpdateTime(newTime);
			bean.setDepartCode(parentBean.getDepartCode());
			this.add(bean);
		}
	}

	/**
	 * 根据loginId修改用户所属组织机构
	 * @param loginId
	 * @param depart_ids
	 * @throws Exception 
	 */
	@Transactional
	public void updateUserDepart(String loginId, String[] departIds) throws Exception {
		// 先根据loginId删除
		this.deleteUserDepart(loginId);
		
		// 插入
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", loginId);
		for (int i = 0; i < departIds.length; i++) {
			map.put("departId", departIds[i]);
			this.addUserDepart(map);
		}
		
	}

	/**
	 * 根据用户loginId查找所属的组织机构id
	 * @param loginId
	 */
	public List<String> queryUserDepartByLoginId(String loginId) {
		return dao.queryUserDepartByLoginId(loginId);
	}
	
}
