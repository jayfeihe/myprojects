package com.tera.feature.lenduser.controller;


/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */


import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.service.IBankBranchInfoService;
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.service.ILendUserService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;


/**
 * 
 * 出借人基本信息维护控制器
 * <b>功能：</b>LendUserController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/lenduser")
public class LendUserController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LendUserController.class);
	
	/**
	 * LendUserService
	 */
	@Autowired(required=false) //自动注入
	private ILendUserService lendUserService;
	
	@Autowired(required=false) //自动注入
	private IBankBranchInfoService bankBranchInfoService;
	
	/**
	 * 跳转到出借人基本信息维护的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String lendUserQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "lenduser/lendUserQuery";
	}

	/**
	 * 显示出借人基本信息维护的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String lendUserList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LendUser.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);	
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LendUser> lendUserList = this.lendUserService.queryPageList(queryMap);
		pm.setData(lendUserList);
		pm.initRowsCount(lendUserList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "lenduser/lendUserList";
	}

	/**
	 * 跳转到更新出借人基本信息维护的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String lendUserUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendUser bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.lendUserService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "lenduser/lendUserUpdate";
	}
	//更改状态
	@RequestMapping("/updateState.do")
	public void lendUserUpdateState(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendUser bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.lendUserService.queryByKey(id);
		}
		bean.setState("0");
		map.put("bean",bean);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			
			this.lendUserService.update(bean);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
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
	 * 保存出借人基本信息维护数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void lendUserSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			LendUser bean = (LendUser) RequestUtils.getRequestBean(LendUser.class, request);
			//如果存在
			if (bean.getId() != 0) {
				LendUser lend=lendUserService.queryByKey(bean.getId());
				lend.setAcctBank(bean.getAcctBank());
				lend.setAcctCode(bean.getAcctCode());
				lend.setAcctName(bean.getAcctName());
				lend.setAcctPrvn(bean.getAcctPrvn());
				lend.setAcctCity(bean.getAcctCity());
				lend.setAcctCtry(bean.getAcctCtry());
				lend.setAcctBranch(bean.getAcctBranch());
				lend.setEmail(bean.getEmail());
				lend.setExt1(bean.getExt1());
				lend.setExt2(bean.getExt2());
				lend.setHomeAddr(bean.getHomeAddr());
				lend.setHomeCity(bean.getHomeCity());
				lend.setHomeCtry(bean.getHomeCtry());
				lend.setHomePrvn(bean.getHomePrvn());
				lend.setIdNo(bean.getIdNo());
				lend.setMobile(bean.getMobile());
				lend.setName(bean.getName());
				lend.setNowAddr(bean.getNowAddr());
				lend.setNowCity(bean.getNowCity());
				lend.setNowCtry(bean.getNowCtry());
				lend.setNowPrvn(bean.getNowPrvn());
				lend.setPhone(bean.getPhone());
				lend.setRealName(bean.getRealName());
				lend.setRemark(bean.getRemark());
				this.lendUserService.update(lend);
			} else { //如果不存在
				this.lendUserService.add(bean);
			}
			bankBranchInfoService.verifyBank( bean.getAcctBank(), request);
			bankBranchInfoService.verifyBranch(bean.getAcctCity(), bean.getAcctBank(), bean.getAcctBranch());
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
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
	 * 删除出借人基本信息维护数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void lendUserDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.lendUserService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
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
	 * 跳转到查看出借人基本信息维护的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String lendUserRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendUser bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.lendUserService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "lenduser/lendUserRead";
	}

	@RequestMapping("/listLendUser.do")
	public void listLendUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<LendUser> lendUsers = this.lendUserService.queryList(null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(lendUsers));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
