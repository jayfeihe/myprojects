/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sales.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;

import com.tera.sales.model.Sales;
import com.tera.sales.service.SalesService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * <br>
 * <b>功能：</b>SalesController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-08-19 14:55:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/sales")
public class SalesController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(SalesController.class);
	
	/**
	 * SalesService
	 */
	@Autowired(required=false) //自动注入
	private SalesService salesService;
	
	/**
	 * 跳转到营销人员表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String salesQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sales/salesQuery";
	}
	
	/**
	 * 跳转到部门设置页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/skipDepartSet.do")
	public String skipDepartSet(String id, HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Sales bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.salesService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sales/departSet";
	}

	/**
	 * 显示营销人员表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String salesList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Sales.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.salesService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Sales> salesList = this.salesService.queryList(queryMap);
		pm.setData(salesList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "sales/salesList";
	}

	/**
	 * 跳转到更新营销人员表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String salesUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Sales bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.salesService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sales/salesUpdate";
	}

	/**
	 * 保存营销人员表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void salesSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Sales bean = (Sales) RequestUtils.getRequestBean(Sales.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.salesService.updateOnlyChanged(bean);
			} else { //如果不存在
				//onJob(bean);//判断离职时间是否为空来置在职状态
				this.salesService.add(bean);
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
	
	/*public boolean onJob(Sales tSales){
		boolean onJobFlag = false;
		if("".equals(tSales.getLeaveDate())||null==tSales.getLeaveDate()){
			onJobFlag = true;
			tSales.setState("1");
		}else{
			tSales.setState("0");
		}
		return onJobFlag;
	}*/

	/**
	 * 删除营销人员表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void salesDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.salesService.delete(id);
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
	 * 跳转到查看营销人员表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String salesRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Sales bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.salesService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sales/salesRead";
	}

	@RequestMapping("/listjason.do")
	public void dataDictionaryListJson(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Object bean = RequestUtils.getRequestBean(Sales.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		List<Sales> salesList = this.salesService.queryList(queryMap);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(salesList));
		writer.flush();
		writer.close();
	}
	
	/**
	 * 部门设置功能
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/departSet.do")
	public void departSet(String id, int departId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Sales bean = null;
			// 如果存在
			if (null != id && !"".equals(id)) {
				bean = this.salesService.queryByKey(id);
				bean.setDepartId(departId);
				this.salesService.update(bean);
				writer.print(JsonUtil.object2json(new JsonMsg(true, "部门配置成功！")));
			}else{
				writer.print(JsonUtil.object2json(new JsonMsg(false, "部门配置失败！")));
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 出借申请和借款申请选择销售人员时调用
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/listSelect.do")
	public void listSelect(String orgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("orgId", orgId);
		queryMap.put("state", "1");
		List<Sales> salesList = this.salesService.queryListSelect(queryMap);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(salesList));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 判断团队组织内是否存在团队经理（排除自己）
	 * 
	 * @param orgId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/isExistTeamManagerByOrgId.do", method = RequestMethod.GET)
	public void isExistTeamManagerByOrgId(String staffNo, String orgId, String departId,
			HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("staffNo", staffNo);
			queryMap.put("orgId", orgId);
			queryMap.put("departId", departId);
			queryMap.put("level", "3");
			queryMap.put("state", "1");
			List<Sales> salesList = this.salesService.queryExcludeSelf(queryMap);
			if (salesList != null && salesList.size() > 0) {
				writer.print(JsonUtil
						.object2json(new JsonMsg(true, "")));
			} else {
				writer.print(JsonUtil
						.object2json(new JsonMsg(false, "")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName + ":end");
	}
	
}
