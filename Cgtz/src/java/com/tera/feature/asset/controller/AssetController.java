/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.asset.controller;

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

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.IContactService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.feature.asset.model.Asset;
import com.tera.feature.asset.service.IAssetService;
import com.tera.feature.warehouse.service.IWarehouseService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 资产管理控制器
 * <b>功能：</b>AssetController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/asset/manage")
public class AssetController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(AssetController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private IAssetService assetService;
	
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	
	@Autowired(required=false) //自动注入
	private IContactService contactService;
	
	@Autowired(required=false) //自动注入
	private ILoanAppService loanAppService;
	
	@Autowired(required=false) //自动注入
	private IWarehouseService warehouseService;
	
	/**
	 * 跳转到资产管理的查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String assetServiceQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "asset/manage/assetQuery";
	}

	/**
	 * 显示资产信息的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String assetList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Asset bean =(Asset) RequestUtils.getRequestBean(Asset.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);	
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Asset> assetList = this.assetService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "asset/manage/assetList";
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
	public String assetUpdate(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Asset bean = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean  = this.assetService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId", loanId);
		map.put("type", type);
		// 车
		if (Asset.TYPE_CAR.equals(type)) {
			return "asset/manage/carAssetUpdate";
		}
		//车商
		if (Asset.TYPE_CARDEALER.equals(type)) {
			return "asset/manage/cardealerAssetUpdate";
		}
		// 房
		if (Asset.TYPE_HOUSE.equals(type)) {
			return "asset/manage/houseAssetUpdate";
		}
		// 红木
		if (Asset.TYPE_ROSEWOOD.equals(type)) {
			return "asset/manage/rosewoodAssetUpdate";
		}
		// 海鲜
		if (Asset.TYPE_SEAFOOD.equals(type)) {
			return "asset/manage/seafoodAssetUpdate";
		}
		// 其他
		if (Asset.TYPE_OTHER.equals(type)) {
			return "asset/manage/otherAssetUpdate";
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
	public void assetSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		try {
			Asset bean = (Asset) RequestUtils.getRequestBean(Collateral.class, request);
			this.assetService.saveOrUpdate(bean,loginId,loginOrg.getOrgId());
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
	 * 删除质押、抵押物信息数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void assetDelete(String id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.assetService.delete(id);
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
	public String assetRead(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Asset bean = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean  = this.assetService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId", loanId);
		map.put("type", type);
		log.info(thisMethodName+":end");
		return "asset/manage/assetRead";
	}
	@RequestMapping("/allRead.do")
	public String loanBaseAllRead(String loanId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		List<LoanApp> loanApps = null;
		List<Contact> contacts = null;
		System.out.println(loanId);
		//如果存在
		if (loanId!=null&&!loanId.equals("")) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			// 非续贷
			if ("0".equals(loanBase.getIsRenew()) || "2".equals(loanBase.getIsRenew())) {
				queryMap.put("loanId", loanId);
				contacts = this.contactService.queryByLoanId(loanId);
			}
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				queryMap.put("loanId", loanBase.getOrigLoanId());
				contacts = this.contactService.queryByLoanId(loanBase.getOrigLoanId());
			}
			
			queryMap.put("mainFlag", "1"); // 主借款人
			loanApps = this.loanAppService.queryList(queryMap );
			
		}
		map.put("loanBase", loanBase);
		if (loanApps != null && loanApps.size() > 0) {
			// 个人
			if ("01".equals(loanBase.getLoanType()))
				map.put("appTypeLoan", loanApps.get(0));
			// 公司
			if ("02".equals(loanBase.getLoanType()))
				map.put("comTypeLoan", loanApps.get(0));
			// 主键id
			map.put("appId", loanApps.get(0).getId());
		}
		map.put("contacts", contacts);
		log.info(thisMethodName+":end");
		return "loan/loanBaseAllRead";
	}
}
