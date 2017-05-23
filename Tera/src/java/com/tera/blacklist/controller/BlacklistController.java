/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.blacklist.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.blacklist.model.Blacklist;
import com.tera.blacklist.model.BlacklistQBean;
import com.tera.blacklist.service.BlacklistService;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 黑名单表控制器
 * <b>功能：</b>BlacklistController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-09-29 12:15:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/blacklist")
public class BlacklistController extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(BlacklistController.class);
	
	/**
	 * BlacklistService
	 */
	@Autowired(required=false) //自动注入
	private BlacklistService blacklistService;
	
	/**
	 * 跳转到黑名单表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String blacklistQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "blacklist/blacklistQuery";
	}

	/**
	 * 显示黑名单表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String blacklistList(BlacklistQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		pm.init(request,null, qBean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Blacklist> blacklistList = this.blacklistService.queryList(queryMap);
		pm.setData(blacklistList);
		pm.initRowsCount(blacklistList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "blacklist/blacklistList";
	}

	/**
	 * 跳转到更新黑名单表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String blacklistUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Blacklist bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.blacklistService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "blacklist/blacklistUpdate";
	}

	/**
	 * 保存黑名单表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void blacklistSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Blacklist bean = (Blacklist) RequestUtils.getRequestBean(Blacklist.class, request);
			//如果存在
			if (bean.getId() != 0) {
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.blacklistService.updateOnlyChanged(bean);
			} else { //如果不存在
				bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.blacklistService.add(bean);
			}
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
	 * 删除黑名单表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/deleteOrActive.do")
	public void blacklistDeleteOrActive(String id,String state, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Blacklist bean = null;
			if (StringUtils.isNotNullAndEmpty(id)) {
				bean  = this.blacklistService.queryByKey(id);
				bean.setState(state);
				this.blacklistService.updateOnlyChanged(bean);
				writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
			} else {
				writer.print(JsonUtil.object2json(new JsonMsg(false, "数据错误，不存在此黑名单")));
			}
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
	 * 跳转到查看黑名单表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String blacklistRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Blacklist bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.blacklistService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "blacklist/blacklistRead";
	}

}
