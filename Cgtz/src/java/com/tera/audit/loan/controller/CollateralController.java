/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.audit.loan.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.form.CollateralJsonMsg;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.feature.warehouse.service.IWarehouseService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 质押、抵押物信息控制器
 * <b>功能：</b>CollateralController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/collateral")
public class CollateralController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollateralController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private ICollateralService collateralService;
	@Autowired
	private IWarehouseService warehouseService;
	
	/**
	 * 跳转到质押、抵押物信息的查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collateralQuery(String loanId,String origLoanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId", loanId);
		map.put("origLoanId", origLoanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loan/collateral/collateralQuery";
	}

	/**
	 * 显示质押、抵押物信息的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collateralList(String loanId,String origLoanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Collateral.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.put("loanId", loanId);
		if (StringUtils.isNotNullAndEmpty(origLoanId)) {
			queryMap.put("origLoanId", origLoanId);
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Collateral> collateralList = this.collateralService.queryPageList(queryMap);
		pm.setData(collateralList);
		pm.initRowsCount(collateralList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("loanId", loanId);
		map.put("origLoanId", origLoanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loan/collateral/collateralList";
	}

	/**
	 * 跳转到更新质押、抵押物信息的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String collateralUpdate(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Collateral bean = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean  = this.collateralService.queryByKey(id);
			Warehouse warehouse = this.warehouseService.queryByKey(bean.getWarehouseId());
			if (warehouse != null) {
				bean.setWarehousePrvn(warehouse.getPrvn());
				bean.setWarehouseCity(warehouse.getCity());
			}
		}
		map.put("bean", bean);
		map.put("loanId", loanId);
		map.put("type", type);
		// 车
		if (Collateral.TYPE_CAR.equals(type)) {
			return "loan/collateral/carCollateralUpdate";
		}
		//车商
		if (Collateral.TYPE_CARDEALER.equals(type)) {
			return "loan/collateral/cardealerCollateralUpdate";
		}
		// 房
		if (Collateral.TYPE_HOUSE.equals(type)) {
			return "loan/collateral/houseCollateralUpdate";
		}
		// 红木
		if (Collateral.TYPE_ROSEWOOD.equals(type)) {
			return "loan/collateral/rosewoodCollateralUpdate";
		}
		// 海鲜
		if (Collateral.TYPE_SEAFOOD.equals(type)) {
			return "loan/collateral/seafoodCollateralUpdate";
		}
		// 其他
		if (Collateral.TYPE_OTHER.equals(type)) {
			return "loan/collateral/otherCollateralUpdate";
		}
		log.info(thisMethodName+":end");
		
		return "";
	}

	/**
	 * 保存质押、抵押物信息数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void collateralSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		try {
			Collateral bean = (Collateral) RequestUtils.getRequestBean(Collateral.class, request);
			CollateralJsonMsg jsonMsg = this.collateralService.saveOrUpdate(bean,loginId);
			writer.print(JsonUtil.object2json(jsonMsg));
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
	 * 删除质押、抵押物信息数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collateralDelete(String id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.collateralService.delete(id);
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
	 * 跳转到查看质押、抵押物信息的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String collateralRead(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Collateral bean = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean  = this.collateralService.queryByKey(id);
			Warehouse warehouse = this.warehouseService.queryByKey(bean.getWarehouseId());
			if (warehouse != null) {
				bean.setWarehousePrvn(warehouse.getPrvn());
				bean.setWarehousePrvn(warehouse.getCity());
			}
		}
		map.put("bean", bean);
		map.put("loanId", loanId);
		map.put("type", type);
		log.info(thisMethodName+":end");
		return "loan/collateral/collateralRead";
	}

}
