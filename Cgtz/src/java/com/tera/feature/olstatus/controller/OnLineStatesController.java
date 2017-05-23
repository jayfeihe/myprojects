package com.tera.feature.olstatus.controller;

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
import java.sql.Timestamp;
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
import com.tera.feature.olstatus.model.OnLineStates;
import com.tera.feature.olstatus.service.IOnLineStatesService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * OnLineStatesController控制器
 * <b>功能：</b>OnLineStatesController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/onLineStates")
public class OnLineStatesController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(OnLineStatesController.class);
	
	/**
	 * onLineStatesService
	 */
	@Autowired(required=false) //自动注入
	private IOnLineStatesService onLineStatesService;
	
	/**
	 * 跳转到T_ONLINE_STATES的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String onLineStatesQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "olstates/onLineStatesQuery";
	}

	/**
	 * 显示T_ONLINE_STATES的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String onLineStatesList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(OnLineStates.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);	
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<OnLineStates> onLineStatesList = this.onLineStatesService.queryPageList(queryMap);
		pm.setData(onLineStatesList);
		pm.initRowsCount(onLineStatesList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "olstates/onLineStatesList";
	}

	/**
	 * 跳转到更新T_ONLINE_STATES的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String OnLineStatesUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OnLineStates bean = null;
		//如果存在
		if (null !=id  && !"".equals(id)) {
			bean  = this.onLineStatesService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "onLineStates/onLineStatesUpdate";
	}
	

	/**
	 * 保存T_ONLINE_STATES数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void OnLineStatesSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			OnLineStates bean = (OnLineStates) RequestUtils.getRequestBean(OnLineStates.class, request);
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			//如果存在
			if (bean.getId() != 0) {
				OnLineStates onLineStates=onLineStatesService.queryByKey(bean.getId());
				onLineStates.setContractId(bean.getContractId());
				onLineStates.setLoanId(bean.getLoanId());
				onLineStates.setIsCur(bean.getIsCur());
				onLineStates.setState(bean.getState());
				onLineStates.setLogTime(bean.getLogTime());
				onLineStates.setRemarks(bean.getRemarks());
				//修改仓库信息的修改时间和修改人id
				onLineStatesService.update(onLineStates);
			} else { //如果不存在
				//添加创建时间和创建人id
				bean.setCreateTime(nowTime);
				bean.setLogTime(nowTime);
				this.onLineStatesService.add(bean);
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
	 * 删除T_ONLINE_STATES数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void OnLineStatesDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.onLineStatesService.delete(id);
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
	 * 跳转到查看T_OnLineStates的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String OnLineStatesRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OnLineStates bean = null;
		// 如果存在
		if (null !=id  && !"".equals(id)) {
			bean = this.onLineStatesService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "onLineStates/onLineStatesRead";
	}
	
	@RequestMapping("/logList.do")
	public String logList(String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(OnLineStates.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);	
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		queryMap.put("contractId",contractId);
		PageList<OnLineStates> onLineStatesList = this.onLineStatesService.queryPageList(queryMap);
		pm.setData(onLineStatesList);
		pm.initRowsCount(onLineStatesList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "olstates/onLineStatesLogList";
	}
}
