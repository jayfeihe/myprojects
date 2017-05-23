package com.tera.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.dao.OrgDao;
import com.tera.sys.model.Org;
import com.tera.util.StringUtils;

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
	public void updateOnlyChanged(Org obj)  throws Exception {
		dao.updateOnlyChanged(obj);
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
	
	/**
	 * 保存或者更新
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void saveOrUpdate(Org bean) throws Exception {
		List<Org> orgs = this.queryList(null);
		
		if (orgs == null || orgs.isEmpty()) {
			bean.setLevel("0");
			bean.setOrgId(BusinessConstants.ORG_CODE);
			this.add(bean);
		} else {
			//如果存在
			if (bean.getId() != 0) {
				this.updateOnlyChanged(bean);
			} else { //如果不存在
				
				String parentOrgId = bean.getParentOrgId(); // 所属机构
				if (StringUtils.isNotNullAndEmpty(parentOrgId)) {
					Org parentOrg = this.queryByOrgId(parentOrgId);
					String parentLevel = parentOrg.getLevel();
					bean.setLevel(String.valueOf(Integer.parseInt(parentLevel) + 1));
					
					// 获取所在机构下的所有
					List<Org> subOrgs = this.getSubOrg(parentOrgId,bean.getLevel());
					if (subOrgs != null && subOrgs.size() > 0) {
						Org lastSubOrg = subOrgs.get(subOrgs.size() - 1);
						bean.setOrgId(String.valueOf(Integer.parseInt(lastSubOrg.getOrgId())+1));
					} else {
						bean.setOrgId(parentOrgId + "01");
					}
					
				}
				
				this.add(bean);
			}
		}
	}
	
	/**
	 * 得到机构代码
	 * @return
	 */
	/*
	private String getOrgId() {
		List<Org> subOrgs = this.getSubOrg(BusinessConstants.ORG_CODE);
		if (subOrgs == null || subOrgs.isEmpty()) {
			return BusinessConstants.ORG_CODE + "01";
		} else {
			int orgNum = Integer.parseInt(subOrgs.get(subOrgs.size()-1).getOrgId());
			return String.valueOf(++orgNum);
		}
	}*/

	public void deleteSelfAndChlidren(String orgId) throws Exception {
		dao.deleteSelfAndChlidren(orgId);
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<Org> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	public Org queryByKey(Object id) throws Exception {
		return dao.queryByKey(id);
	}
	public Org queryByOrgId(String id) throws Exception {
		return dao.queryByOrgId(id);
	}
	
	public List<Org> getOrgByLoginId(String userId) throws Exception {
		return dao.getOrgByLoginId(userId);
	}
	
	public List<Org> getSubOrg(String orgId,String level){
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("orgId", orgId);
		queryMap.put("level", level);
		return dao.getSubOrg(queryMap);
	}

	public List<Org> querySelectList(Map<String, Object> map) throws Exception {
		return dao.querySelectList(map);
	}
	
	/**
	 * 查询数据权限下拉集合
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	public List<Org> queryListTree(List<Org> orgList) throws Exception {
		if(orgList.size() == 1) return orgList;
		List<Org> orgListSum1 = new ArrayList<Org>();
		for (Org org : orgList) {
 			if(BusinessConstants.ORG_CODE.equals(org.getOrgId()))
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
//		level = 4;
		for (Org org2 : orgListChildrens) {
			getOrgSelectList(org2, orgList, level);
		}
	}
}
