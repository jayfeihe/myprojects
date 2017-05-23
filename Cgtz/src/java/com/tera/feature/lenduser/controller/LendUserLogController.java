/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.lenduser.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.model.LendUserLog;
import com.tera.feature.lenduser.service.ILendUserLogService;
import com.tera.feature.lenduser.service.ILendUserService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 出借人资金变动记录控制器
 * <b>功能：</b>LendUserLogController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-10 22:42:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/lenduser/lenduserlog")
public class LendUserLogController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LendUserLogController.class);
	
	/**
	 * LendUserLogService
	 */
	@Autowired(required=false) //自动注入
	private ILendUserLogService lendUserLogService;
	
	@Autowired(required=false) //自动注入
	private ILendUserService lendUserService;
	
	/**
	 * 跳转到出借人资金变动记录的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String lendUserLogQuery(String lendUserId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("lendUserId", lendUserId);
		log.info(thisMethodName+":end");
		return "lenduser/lenduserlog/lendUserLogQuery";
	}

	/**
	 * 显示出借人资金变动记录的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String lendUserLogList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		LendUserLog bean =(LendUserLog) RequestUtils.getRequestBean(LendUserLog.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LendUserLog> lendUserList = this.lendUserLogService.queryPageList(queryMap);
		pm.setData(lendUserList);
		pm.initRowsCount(lendUserList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("lendUserId",bean.getLendUserId());
		map.put("lendUserName",this.lendUserService.queryByKey(bean.getLendUserId()).getName());
		log.info(thisMethodName+":end");
		return "lenduser/lenduserlog/lendUserLogList";
	}

	/**
	 * 跳转到更新出借人资金变动记录的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String lendUserLogUpdate(String id,String lendUserId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendUserLog bean = null;	
		//如果存在
		if (null !=id && !"".equals(id)) {
			bean  = this.lendUserLogService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("lendUserId",lendUserId);
		log.info(thisMethodName+":end");
		return "lenduser/lenduserlog/lendUserLogUpdate";
	}

	/**
	 * 保存出借人资金变动记录数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void lendUserLogSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			LendUserLog bean = (LendUserLog) RequestUtils.getRequestBean(LendUserLog.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.lendUserLogService.update(bean);
			} else { //如果不存在
				Timestamp nowTime=new Timestamp(System.currentTimeMillis());
				bean.setCreateTime(nowTime);
				bean.setCreateUid((request.getSession().getAttribute("loginid").toString()));
				//支出
				LendUser lenduser=this.lendUserService.queryByKey(bean.getLendUserId());
				if(bean.getType().equals("2")){
					lenduser.setAmt(lenduser.getAmt()-bean.getAmt());
				}else{
					lenduser.setAmt(lenduser.getAmt()+bean.getAmt());
				}
				this.lendUserService.update(lenduser);
				this.lendUserLogService.add(bean);
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
	 * 删除出借人资金变动记录数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void lendUserLogDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.lendUserLogService.delete(id);
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
	 * 跳转到查看出借人资金变动记录的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String lendUserLogRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendUserLog bean = null;
		// 如果存在
		if (null !=id  && !"".equals(id)) {
			bean = this.lendUserLogService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "lenduser/lenduserlog/lendUserLogRead";
	}

}
