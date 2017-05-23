package com.tera.report.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.RoleDataRel;
import com.tera.sys.model.User;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.util.JsonUtil;
@Controller
@RequestMapping("/roleDataRelOrgs")
public class RoleDataController {
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(BillAcctFlowController.class);
	
	/**
	 * RoleDataRelService
	 */
	
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	
	/**
	 * 报表查询权限机构下拉
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/listOrgs.do")
	public void listOrgs(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);
		User loginUser=(User)request.getSession().getAttribute(SysConstants.LOGIN_USER);

		if(loginId!=null&&!loginId.equals("")){
			//非管理员显示登录id关联的机构
			if(loginUser.getIsAdmin()==0){
				List<RoleDataRel> list=new ArrayList<RoleDataRel>();
				Map<String,Object> queryMap=new HashMap<String, Object>();			
				queryMap.put("loginId",loginId);
				list = this.roleDataRelService.queryOrgListByLoginId(queryMap);
				writer.print(JsonUtil.object2json(list));
				writer.flush();
				writer.close();
			}
			//管理员显示所有的机构
			if(loginUser.getIsAdmin()==1){
				List<Org> allOrgs=this.orgService.queryList(null);
		    //总公司不下拉
			if(allOrgs!=null&&allOrgs.size()>0){
					for (int i = 0; i <allOrgs.size(); i++) {
						if(allOrgs.get(i).getOrgId().equals("86")){
							allOrgs.remove(i);
						}
					}
			}	
				writer.print(JsonUtil.object2json(allOrgs));
				writer.flush();
				writer.close();
			}
		}		
		log.info(thisMethodName+":end");
	}	
}
