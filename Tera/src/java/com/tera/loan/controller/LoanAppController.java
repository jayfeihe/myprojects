/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.loan.controller;

import java.io.PrintWriter;
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

import com.tera.img.service.ImgService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanAppCollateral;
import com.tera.loan.model.LoanAppContact;
import com.tera.loan.model.LoanJsonMsg;
import com.tera.loan.model.form.LoanAppBean;
import com.tera.loan.model.form.LoanFBean;
import com.tera.loan.model.form.LoanQBean;
import com.tera.loan.model.form.LoanUpdFBean;
import com.tera.loan.service.LoanAppCollateralService;
import com.tera.loan.service.LoanAppContactService;
import com.tera.loan.service.LoanAppService;
import com.tera.loan.util.LoanAppUtil;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 18:42:18<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/app")
public class LoanAppController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanAppController.class);
	
	/**
	 * LoanAppService
	 */
	@Autowired(required=false) //自动注入
	private LoanAppService<LoanApp> loanAppService;
	
	@Autowired(required=false) //自动注入
	private LoanAppCollateralService<LoanAppCollateral> loanAppCollateralService;
	
	@Autowired(required=false) //自动注入
	private LoanAppContactService<LoanAppContact> loanAppContactService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	/**
	 * 跳转到借款端申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String loanAppQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loan/app/loanQuery";
	}

	/**
	 * 显示借款端申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String loanAppList(String stateTask,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("loginId", loginId);
		roleMap.put("orgId", sessionOrg.getOrgId());
		//查询登录用户在当前机构下的角色等级
		List<Role> roles = this.roleService.getRoleByOrgLoginId(roleMap);
		Role role = null;
		String roleLever = ""; //用户当前登录机构的最大角色等级
		if (roles.size() > 0) {
			role = roles.get(0);
			roleLever = role.getOrgRoleLever();
		}
		
		if(queryMap.get("orgId")==null || "".equals(queryMap.get("orgId"))){
			queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		}
		queryMap.put("nonStates", new String[]{"0"}); //状态
		queryMap.put("customerManager",loginId); //客户经理，当前登录人 查看属于他的 单子
		queryMap.put("roleLever", roleLever); //角色等级
		if("waitTask".equals(stateTask)){
			queryMap.put("operator", loginId); //Session 操作员
			queryMap.put("bpmStates", new String[]{"录入申请"});//查询录入流程定义为录入申请的
		}else if("inTask".equals(stateTask)){
			// 进行中
			queryMap.put("nonBpmStates", new String[]{"录入申请","结束"});
		}else if("offTask".equals(stateTask)){
			queryMap.put("bpmStates", new String[]{"结束"});
		}
		
		int rowsCount =loanAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<LoanApp> loanAppList=loanAppService.queryBpmLoanAppList(queryMap);
		pm.setData(loanAppList);
		map.put("pm", pm);
		map.put("stateType", stateTask);
		log.info(thisMethodName+":end");
		return "loan/app/loanList";
	}
	/**
	 * 跳转到更新借款端申请表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanAppUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp bean = null;
		if (null != id && !"".equals(id)) { //点击更新
			bean  = this.loanAppService.queryByKey(id);
			if(bean!=null){
				List<String> mainFlagList=this.loanAppService.getMainFlagListByAppId(bean.getAppId());//查询主借款人有多少个共同借款人
				map.put("appId", bean.getAppId()); //申请ID
				map.put("mainFlagList", mainFlagList);//主借款人表示  ex:0为主借款人 非0为共同借款人
				if(mainFlagList!=null||mainFlagList.size()>0){
					map.put("mainFlag", mainFlagList.get(mainFlagList.size()-1));//取得第一个不要0
				}
			}
			map.put("bean", bean);
			map.put("paramType",bean.getAppType().equals("01")? "per":"org");//区分类型 01为个人显示加载顺序
			map.put("appType",bean.getAppType());//区分类型 01为
		}else{
			map.put("paramType", request.getParameter("paramVal"));//获取需要加载的顺序
			map.put("mainFlag", "0");//首次加载录入申请页面为主借款人人
			map.put("appType", request.getParameter("paramVal").equals("per")? "01":"02");//获取需要加载的顺序
		}
		log.info(thisMethodName+":end");
		return "loan/app/loanUpdate";
	}
	/**
	 * @see 如果有多个共同借款人循环加载多个借款人
	 * @data 2014-6-17
	 * @param appId
	 * @param mainFlag
	 * @param paramType
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception 
	 * @return String
	 */
	@RequestMapping("/loanUpdateBorr.do")
	public String loanUpdateBorr(String appId,String mainFlag,String paramType, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanUpdFBean appList = null;
		if (null != appId && !"".equals(appId)) {
			appList= this.loanAppService.getUpdLoanBean(appId, mainFlag);
		}
		map.put("paramType",paramType); //区分个人机构类型
		map.put("mainFlag",mainFlag);// //新添加选项卡时候回显索引 ==共同借款人
		map.put("loanBean", appList);
		log.info(thisMethodName+":end");
		return "loan/app/loanUpdateBorrower";
	}

	/**
	 * 保存借款端申请表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanAppSave(String isStart,HttpServletRequest request, HttpServletResponse response, ModelMap map,String id) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//得到抵押物
			List<LoanAppCollateral> collList=RequestUtils.getRequestBeanList("guarantyList",LoanAppCollateral.class,request);
			//得到抵押物 个人
			List<LoanAppContact> perConList=RequestUtils.getRequestBeanList("perContactsList",LoanAppContact.class,request);
			//得到抵押物 机构
			List<LoanAppContact> orgConList=RequestUtils.getRequestBeanList("orgContactsList",LoanAppContact.class,request);
			
			LoanFBean bean = (LoanFBean) RequestUtils.getRequestBean(LoanFBean.class, request);
			Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String loginId= (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			//取得页面上所有的参数封装到一个对象LoanFBean中。然后通过Name的循环获取页面有多少个共同信息并且Set到表对象中
			List<LoanAppBean> list = LoanAppUtil.getLoanAppBeans(bean,request);
			this.loanAppService.updateLoanApp(list,org,loginId,collList,perConList,orgConList);
			
			if("start".equals(isStart)){
				String yz=imgService.imgVerify(list.get(0).getLoanapp().getAppId(), new String[]{"A01","B01"});
				if("".equals(yz)){
					//保存完成后提交流程list.get(0).getLoanapp().getAppId()->申请Id, loginId：登录用户 + 用户 + 提交表示
					this.loanAppService.submitLoanApp(list.get(0).getLoanapp().getAppId(), loginId,org.getOrgId(),isStart);
					writer.print(JsonUtil.object2json(new JsonMsg(true, "提交成功。")));
				}else if(yz==null){ //未找到图片的情况
					writer.print(JsonUtil.object2json(new JsonMsg(false, "提交流程失败，请先上传必须资料。")));
				}else{
					//必传图片缺少的情况
					writer.print(JsonUtil.object2json(new JsonMsg(false, "提交流程失败，请先上传缺少资料：（"+yz+"）。")));
				}
			}else{
				writer.print(JsonUtil.object2json(new LoanJsonMsg(true, "保存成功。",list.get(0).getLoanapp().getAppId(),list.get(0).getLoanapp().getId())));
			}
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
	 * 删除借款端申请表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void loanAppDelete(String appId, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		try {
			this.loanAppService.deleteLoanApp(appId,loginId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * @data 2014-6-18
	 * @param response
	 * @param request 
	 * @return void
	 * @throws Exception 
	 */
	@RequestMapping("/delPerAndOrg.do")
	public void loanDelPer(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			String appId = request.getParameter("appId");
			String mainFlg  = request.getParameter("mainFlag");
			//将人员机构主信息拼装数组根据主键ID循环删除
			String personInfo = request.getParameter("id")+","+ request.getParameter("orgId");
			//将附加人员信息拼装数组根据主键ID循环删除
			String contactId = request.getParameter("fdOrgId")+","+request.getParameter("cwOrgId")+","+request.getParameter("contactId")+","+request.getParameter("sqOrgId");
			this.loanAppService.deleteLoanPer(appId,mainFlg,personInfo,contactId);
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
	 * 跳转到查看借款端申请表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanAppRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp bean = null;
		if (null != id && !"".equals(id)) { //点击更新
			bean  = this.loanAppService.queryByKey(id);
			if(bean!=null){
				List<String> mainFlagList=this.loanAppService.getMainFlagListByAppId(bean.getAppId());//查询主借款人有多少个共同借款人
				map.put("appId", bean.getAppId()); //申请ID
				map.put("mainFlagList", mainFlagList);//主借款人表示  ex:0为主借款人 非0为共同借款人
				if(mainFlagList!=null||mainFlagList.size()>0){
					map.put("mainFlag", mainFlagList.get(mainFlagList.size()-1));//取得第一个不要0
				}
			}
			map.put("bean", bean);
			map.put("paramType",bean.getAppType().equals("01")? "per":"org");//区分类型 01为个人显示加载顺序
			map.put("appType",bean.getAppType());//区分类型 01为
		}else{
			map.put("paramType", request.getParameter("paramVal"));//获取需要加载的顺序
			map.put("mainFlag", "0");//首次加载录入申请页面为主借款人人
			map.put("appType", request.getParameter("paramVal").equals("per")? "01":"02");//获取需要加载的顺序
		}
		log.info(thisMethodName+":end");
		return "loan/app/loanRead";
	}
	
/*	@RequestMapping("/colltest.do")
	public String collateraTest(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		List<LoanAppCollateral> collList=RequestUtils.getRequestBeanList("guarantyList",LoanAppCollateral.class,request);
		List<LoanAppContact> perConList=RequestUtils.getRequestBeanList("perContactsList",LoanAppContact.class,request);
		List<LoanAppContact> orgConList=RequestUtils.getRequestBeanList("orgContactsList",LoanAppContact.class,request);

		this.loanAppService.CollteralAdd(collList, perConList, orgConList, "");
		return null;
	}
*/	
	/**
	 * 查询展示 出借申请的 抵押物 
	 * @param appId		申请 ID
	 * @param paramType	申请类型
	 * @param request	
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/collateraList.do")
	public String collateraList(String appId,String paramType, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<LoanAppCollateral> collList=loanAppCollateralService.queryListByAppId(appId,"");
		if(collList!=null&& collList.size()>0){
			map.put("collList", collList);
			map.put("collNextIndex",collList.get(collList.size()-1).getSeqFlag()+1);
			List<LoanAppContact> collPerConList=loanAppContactService.queryCollList(appId,"01",null);
			map.put("collPerConList",collPerConList);
			List<LoanAppContact> collOrgConList=loanAppContactService.queryCollList(appId,"02",null);
			map.put("collOrgConList",collOrgConList);
		}else{
			map.put("collNextIndex",1);
		}
		map.put("appId", appId);
		if("per".equals(paramType)||"org".equals(paramType)){
			map.put("appType", paramType.equals("per")?"01":"02");
		}
		log.info(thisMethodName+":end");
		return "loan/app/loanAppCollateralUpdateIn";
	}
	/**
	 * 删除 申请的 抵押物
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/delAppColl.do")
	public void delectColl(String collId,HttpServletResponse response,HttpServletRequest request) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		System.out.println(collId);
		//删除 抵押物
		try {
			
			LoanAppCollateral lac=loanAppCollateralService.queryByKey(collId);
			String appId=lac.getAppId();
			String collateralSeqFlag=lac.getSeqFlag()+"";
			//删除抵押物，同时 删除 产权人
			loanAppCollateralService.deleteAndContactS(collId,appId, collateralSeqFlag);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "抵押物删除成功!")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			log.info(e);
		}
		
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * 删除抵押物 人员信息
	 * @param contId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/delAppCollCont.do")
	public void delectAppCollCont(String contId,HttpServletResponse response,HttpServletRequest request) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		System.out.println(contId);
		//删除 抵押物
		try {
			loanAppContactService.delete(contId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "产权人删除成功!")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			log.info(e);
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	

}
