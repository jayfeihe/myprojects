package com.tera.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.dao.RoleDataRelDao;
import com.tera.sys.model.Org;
import com.tera.sys.model.RoleDataRel;
import com.tera.sys.model.User;

/**
 * 
 * 机构的数据权限，和T_USER_EXT中的内容不一样服务类
 * <b>功能：</b>RoleDataRelDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 19:16:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("roleDataRelService")
public class RoleDataRelService {

	private final static Logger log = Logger.getLogger(RoleDataRelService.class);

	@Autowired(required=false)
    private RoleDataRelDao dao;
	@Autowired(required=false)
    private OrgService orgService;

	@Transactional
	public void add(RoleDataRel... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RoleDataRel obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(RoleDataRel obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(RoleDataRel obj)  throws Exception {
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
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<RoleDataRel> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public RoleDataRel queryByKey(Object id) throws Exception {
		return (RoleDataRel)dao.queryByKey(id);
	}
	
	//非管理员用户,根据登录id获取所有机构,用于下拉显示
    public List<RoleDataRel> queryOrgListByLoginId(Map<String, Object> map){
    	return dao.queryOrgListByLoginId(map);
    };
    
  //根据用户loginId，获得权限内的机构集合,用于限制查询权限
  	public List<String> queryList(HttpServletRequest request) throws Exception{
  		User loginUser=(User)request.getSession().getAttribute(SysConstants.LOGIN_USER);
		//登录Id所对应的机构id集合
		List<String> orgs=new ArrayList<String>();	
		//管理员权限	
		if(loginUser.getIsAdmin()==1){
			//所有的机构
			List<Org> allOrgs=this.orgService.queryList(null);
			for (Org org : allOrgs) {
				if(!org.getOrgId().equals("86")){
					orgs.add(org.getOrgId());	
				}
			}
		}
		//非管理员
		if(loginUser.getIsAdmin()==0){
			Map<String,Object> rdMap=new HashMap<String, Object>();
			rdMap.put("loginId",loginUser.getLoginId());
			List<RoleDataRel> rdOrgs=this.queryOrgListByLoginId(rdMap);
			for(RoleDataRel roleDataRel:rdOrgs){
				orgs.add(roleDataRel.getOrgId());
			}
		}
  		return orgs;
  	};
	
}
