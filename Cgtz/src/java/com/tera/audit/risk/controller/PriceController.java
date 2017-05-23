/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.audit.risk.controller;

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
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.risk.model.CollateralPriceAudit;
import com.tera.audit.risk.service.ICollateralPriceAuditService;
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
 * 核价Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/risk/price")
public class PriceController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PriceController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private ICollateralService collateralService;
	@Autowired
	private ICollateralPriceAuditService collateralPriceAuditService;
	@Autowired
	private IWarehouseService warehouseService;
	
	/**
	 * 跳转到核价条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String priceQuery(String loanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId", loanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "risk/price/priceQuery";
	}

	/**
	 * 显示核价的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String priceList(String loanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Collateral.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.put("loanId", loanId);

		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Collateral> priceList = this.collateralService.queryPageList(queryMap);
		pm.setData(priceList);
		pm.initRowsCount(priceList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("loanId", loanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "risk/price/priceList";
	}

	/**
	 * 跳转到更新核价信息的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String priceUpdate(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Collateral bean = null;
		List<CollateralPriceAudit> priceAuditList = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean  = this.collateralService.queryByKey(id);
			Warehouse warehouse = this.warehouseService.queryByKey(bean.getWarehouseId());
			if (warehouse != null) {
				bean.setWarehousePrvn(warehouse.getPrvn());
				bean.setWarehouseCity(warehouse.getCity());
			}
			priceAuditList = this.collateralPriceAuditService.queryByCollateralId(id);
		}
		map.put("bean", bean);
		map.put("loanId", loanId);
		map.put("type", type);
		map.put("priceAuditList", priceAuditList);
		log.info(thisMethodName+":end");
		
		return "risk/price/priceUpdate";
	}
	
	@RequestMapping("/save.do")
	public void priceSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
//		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		try {
			Collateral coll = (Collateral) RequestUtils.getRequestBean(Collateral.class, request);
			this.collateralPriceAuditService.saveOrUpdate(coll,loginId);
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
}
