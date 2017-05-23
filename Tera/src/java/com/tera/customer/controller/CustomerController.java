/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.customer.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.customer.model.Contact;
import com.tera.customer.model.Customer;
import com.tera.customer.model.CustomerExt;
import com.tera.customer.model.form.CustomerFBean;
import com.tera.customer.model.form.CustomerQBean;
import com.tera.customer.service.ContactService;
import com.tera.customer.service.CustomerExtService;
import com.tera.customer.service.CustomerService;
import com.tera.lend.constant.LendConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.ParameterService;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 *
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-27 17:54:48<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CustomerController.class);

	/**
	 * CustomerService
	 */
	@Autowired(required=false) //自动注入
	private CustomerService<Customer> customerService;

	@Autowired(required=false) //自动注入
	private ContactService<Contact> contactService;

	@Autowired(required=false) //自动注入
	private CustomerExtService<CustomerExt> customerExtService;

	@Autowired(required=false) //自动注入
	private RoleService roleService;
	
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;

	/**
	 * 跳转到财富客户表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String customerQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "customer/customerQuery";
	}

	/**
	 * 显示财富客户表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String customerList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		//系统登录用户
		String sessionLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		//登录机构
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//登录用户
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);

		Map<String, Object> beanMap = new HashMap<String, Object>();
		beanMap.put("loginId", sessionLoginId);
		beanMap.put("orgId", sessionOrg.getOrgId());
		//用户在登录机构下的角色
		List<Role> roles = this.roleService.getRoleByOrgLoginId(beanMap);
		Role role = null;
		if (roles.size() > 0) {
			role = roles.get(0);
		}
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(CustomerQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//财富客户经理|借款客户经理|风险专员  仅能浏览当前机构下个人的客户
		queryMap.put("customerManager", sessionLoginId);
		//营业部经理|营业部负责人  仅能浏览当前机构下的客户
		queryMap.put("orgId", sessionOrg.getOrgId());
		//用户当前机构下的最大角色
		//总部  营业部  个人
		String roleLever = "";
		//只有财富客户经理可以看用户
		if (user.getIsAdmin() != 1) {
			if (LendConstants.ROLE_CFKHJL.equals(role.getName())) {
				roleLever = role.getOrgRoleLever();
			}
		}
		queryMap.put("roleLever", roleLever);
		//只查询有效状态
		queryMap.put("states", new String[]{"1"});
		int rowsCount = this.customerService.queryCustomerCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Customer> customerList = this.customerService.queryCustomerList(queryMap);
		pm.setData(customerList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "customer/customerList";
	}

	/**
	 * 跳转到更新财富客户表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String customerUpdate(String id, String customerType, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		Customer bean = null;
		Contact contact = null;
		CustomerExt customerExt = null;
		Contact farenContact = null;
		Contact cfoContact = null;
		Contact kongzhiContact = null;

		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.customerService.queryByKey(id);
			Map<String , Object> qmap = new HashMap<String , Object>();
			qmap.put("customerId", id);
			List<Contact> contacts = contactService.queryList(qmap);
			for (Contact cont : contacts) {
				//机构客户
				if ("02".equals(bean.getCustomerType())) {
					//法人
					if (cont.getType().equals("02")) {
						farenContact = cont;
					} else if (cont.getType().equals("04")) { //财务负责人04
						cfoContact = cont;
					} else if (cont.getType().equals("07")) { //实际控制人07
						kongzhiContact = cont;
					}
				}
				if (cont.getType().equals("00")) { //紧急联系人00
					//根据客户ID查询出联系人信息 TODO 紧急联系人是否会多条?
					contact = cont;
				}
			}
			//根据客户ID查询出客户扩展信息信息
			customerExt = this.customerExtService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("contact", contact);
		map.put("customerExt", customerExt);
		map.put("farenContact", farenContact);
		map.put("cfoContact", cfoContact);
		map.put("kongzhiContact", kongzhiContact);
		//map.put("beanJson" , JsonUtil.object2json(bean));
		//log.info("客户信息:" + JsonUtil.object2json(bean));
		
		// 根据配置获取甲方公司
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		map.put("companyName", companyName);
		
		if ("01".equals(customerType)) {
			log.info(thisMethodName+":end");
			return "customer/customerUpdatePer";
		} else {
			log.info(thisMethodName+":end");
			return "customer/customerUpdateOrg";
		}
	}

	/**
	 * 保存财富客户表数据
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void customerSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org =  (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			CustomerFBean bean = (CustomerFBean) RequestUtils.getRequestBean(CustomerFBean.class, request);
			
			if(bean.getId()==0){
				 //如果不存在
				Map<String, Object> customeMap=new HashMap<String, Object>();
				customeMap.put("name", bean.getName());
				customeMap.put("idType", bean.getIdType());
				customeMap.put("idNo", bean.getIdNo());
				customeMap.put("customerType", bean.getCustomerType());
				int cusCount=customerService.queryCount(customeMap);
				if(cusCount!=0){
					writer.print(JsonUtil.object2json(new JsonMsg(false, "添加失败!客户已存在。")));
					writer.flush();
					writer.close();
					log.info(thisMethodName+":end");
					return;
				}
			}
			this.customerService.updateCustomer(bean, org, loginid);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
			writer.flush();
			writer.close();
			log.info(thisMethodName+":end");
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			log.info(e.toString());
			throw e;
		}
	}

	/**
	 * 删除财富客户表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void customerDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org =  (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			//TODO 删除客户信息 扩展信息
			for (String str : id) {
				Customer bean = null;
				//如果存在
				if (null != id && !"".equals(id)) {
					bean  = this.customerService.queryByKey(str);
				}
				bean.setState("0"); //失效
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				bean.setOperator(loginid);
				this.customerService.updateOnlyChanged(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 跳转到查看财富客户表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String customerRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		Customer bean = null;
		Contact contact = null;
		CustomerExt customerExt = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.customerService.queryByKey(id);
			//根据客户ID查询出联系人信息 TODO 紧急联系人是否会多条?
			contact = this.contactService.queryByKey(id);
			//根据客户ID查询出客户扩展信息信息
			customerExt = this.customerExtService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("contact", contact);
		map.put("customerExt", customerExt);
		log.info(thisMethodName+":end");
		return "customer/customerRead";
	}

	@RequestMapping("/readJson.do")
	public void readJson(String customerType,String zjNumber,String idType, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Customer bean = null;
		// 如果存在
		if (null != zjNumber && !"".equals(zjNumber)) {
			Map<String , Object> rcmap=new HashMap<String , Object>();
			rcmap.put("idNo", zjNumber);
			rcmap.put("idType", idType);
			rcmap.put("customerType", customerType);
			List<Customer> beanList = this.customerService.queryList(rcmap);
			if(beanList!=null&& beanList.size()>0){
				bean=beanList.get(0);
			}
		}
		map.put("bean", bean);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(map));
		writer.flush();
		writer.close();
	}
	
	
}
