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

import com.tera.feature.asset.model.Asset;
import com.tera.feature.asset.model.CollateralCheck;
import com.tera.feature.asset.service.IAssetService;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.service.ICollateralService;

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
 * 资产处置控制器
 * <b>功能：</b>CollateralController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/asset/deal")
public class AssetDealController {

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
	private ICollateralService collateralService;

	@RequestMapping("/update.do")
	public String assetUpdate(String collateralId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("collateralId",collateralId);
	    Collateral collateral=collateralService.queryByKey(Integer.valueOf(collateralId));
	    if(collateral!=null){
	    	map.put("loanId",collateral.getLoanId());
		    map.put("collateral",collateral);
	    }
		log.info(thisMethodName+":end");	
		return "asset/deal/collateralDeal";
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
		
		//String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		loginOrg.getOrgName();
		try {
			Collateral bean = (Collateral) RequestUtils.getRequestBean(Collateral.class, request);
			updateCollateral(bean,request);
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
	
	public void updateCollateral(Collateral bean,HttpServletRequest request){
		//处置时间
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		//更改抵押物信息
        Collateral collateral=null;
		try {
			collateral = this.collateralService.queryByKey(bean.getId());
			collateral.setSellAmt(bean.getSellAmt());
			collateral.setSellTime(nowTime);
			collateral.setUpdateTime(nowTime);
			collateral.setUpdateUid((String) request.getSession().getAttribute(SysConstants.LOGIN_ID));
			collateral.setSellInputUid((String) request.getSession().getAttribute(SysConstants.LOGIN_ID));
	        Org loginOrg=(Org) (request.getSession().getAttribute(SysConstants.LOGIN_ORG));
			collateral.setSellOrg(bean.getSellOrg());
			collateral.setSellRemark(bean.getSellRemark());
			collateral.setSellWay(bean.getSellWay());
			collateral.setState("3");
	        this.collateralService.update(collateral);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}
}

