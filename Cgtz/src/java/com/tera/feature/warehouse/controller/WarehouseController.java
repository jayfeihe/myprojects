package com.tera.feature.warehouse.controller;

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
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.feature.warehouse.service.IWarehouseService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * T_WAREHOUSE控制器
 * <b>功能：</b>WarehouseController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(WarehouseController.class);
	
	/**
	 * WarehouseService
	 */
	@Autowired(required=false) //自动注入
	private IWarehouseService warehouseService;
	
	/**
	 * 跳转到T_WAREHOUSE的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String warehouseQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "warehouse/warehouseQuery";
	}

	/**
	 * 显示T_WAREHOUSE的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String warehouseList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Warehouse.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Warehouse> warehouseList = this.warehouseService.queryPageList(queryMap);
		pm.setData(warehouseList);
		pm.initRowsCount(warehouseList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "warehouse/warehouseList";
	}

	/**
	 * 跳转到更新T_WAREHOUSE的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String warehouseUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Warehouse bean = null;
		//如果存在
		if (null !=id  && !"".equals(id)) {
			bean  = this.warehouseService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "warehouse/warehouseUpdate";
	}
	//假删除，修改仓库状态为0
	@RequestMapping("/updateState.do")
	public void warehouseUpdateState(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Warehouse bean = null;
		//如果存在
		if (null !=id  && !"".equals(id)) {
			bean  = this.warehouseService.queryByKey(id);
		}
		bean.setState("0");
		//修改仓库信息的修改时间和修改人id
		Timestamp nowTime=new Timestamp(System.currentTimeMillis());
		bean.setUpdateTime(nowTime);
		bean.setUpdateUid(((request.getSession().getAttribute("loginid").toString())));
		map.put("bean", bean);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {		
			this.warehouseService.update(bean);
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
	 * 保存T_WAREHOUSE数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void warehouseSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Warehouse bean = (Warehouse) RequestUtils.getRequestBean(Warehouse.class, request);
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			String loginid=request.getSession().getAttribute("loginid").toString();
			//如果存在
			if (bean.getId() != 0) {
				Warehouse warehouse=warehouseService.queryByKey(bean.getId());
				warehouse.setRemark(bean.getRemark());
				warehouse.setAddr(bean.getAddr());
				warehouse.setCity(bean.getCity());
				warehouse.setCtry(bean.getCtry());
				warehouse.setName(bean.getName());
				warehouse.setOrg(bean.getOrg());
				warehouse.setOwner(bean.getOwner());
				warehouse.setPrvn(bean.getPrvn());
				warehouse.setTel(bean.getTel());
				warehouse.setUpdateTime(nowTime);
				warehouse.setUpdateUid(loginid);
				//修改仓库信息的修改时间和修改人id
				warehouseService.update(warehouse);
			} else { //如果不存在
				//添加创建时间和创建人id
				bean.setCreateTime(nowTime);
				bean.setCreateUid(loginid);
				bean.setState("1");
				this.warehouseService.add(bean);
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
	 * 删除T_WAREHOUSE数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void warehouseDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.warehouseService.delete(id);
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
	 * 跳转到查看T_WAREHOUSE的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String warehouseRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Warehouse bean = null;
		// 如果存在
		if (null !=id  && !"".equals(id)) {
			bean = this.warehouseService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "warehouse/warehouseRead";
	}

	@RequestMapping("/listWarehouse.do")
	public void listWarehouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Warehouse> warehouses = this.warehouseService.queryList(null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(warehouses));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
