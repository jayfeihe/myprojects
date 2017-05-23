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
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.feature.asset.model.Asset;
import com.tera.feature.asset.model.CollateralCheck;
import com.tera.feature.asset.service.ICollateralCheckService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 押品检查信息记录
控制器
 * <b>功能：</b>CollateralCheckController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 10:11:56<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/asset/check")
public class CollateralCheckController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollateralCheckController.class);
	
	/**
	 * CollateralCheckService
	 */
	@Autowired(required=false) //自动注入
	private ICollateralCheckService collateralCheckService;
	
	@Autowired(required=false) //自动注入
	private ICollateralService collateralService;
	
	/**
	 * 跳转到押品检查信息记录
的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collateralCheckQuery(String collateralId,String isValueChange,String state,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("collateralId",collateralId);
		map.put("isValueChange", isValueChange);
		map.put("state", state);
		log.info(thisMethodName+":end");
		return "asset/check/collateralCheckQuery";
	}

	/**
	 * 显示押品检查信息记录
的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collateralCheckList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		CollateralCheck bean = (CollateralCheck)RequestUtils.getRequestBean(CollateralCheck.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<CollateralCheck> assetList = this.collateralCheckService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("collateralId",bean.getCollateralId());
		log.info(thisMethodName+":end");
		return "asset/check/collateralCheckList";
	}

	/**
	 * 跳转到更新押品检查信息记录
的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String collateralCheckUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollateralCheck bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.collateralCheckService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "asset/check/collateralCheckUpdate";
	}

	/**
	 * 保存押品检查信息记录
数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void collateralCheckSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			CollateralCheck bean = (CollateralCheck) RequestUtils.getRequestBean(CollateralCheck.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.collateralCheckService.update(bean);
			} else { //如果不存在
				Timestamp nowTime=new Timestamp(System.currentTimeMillis());
				bean.setCheckTime(nowTime);
				bean.setCheckUid((request.getSession().getAttribute("loginid").toString()));
				updateCollateral(bean,request);
				this.collateralCheckService.add(bean);
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
	 * 删除押品检查信息记录
数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collateralCheckDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.collateralCheckService.delete(id);
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
	 * 跳转到查看押品检查信息记录
的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String collateralCheckRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollateralCheck bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.collateralCheckService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "asset/check/collateralCheckRead";
	}

	public void updateCollateral(CollateralCheck bean, HttpServletRequest request){
		//更改抵押物信息
        Collateral collateral=null;
		try {
			collateral = this.collateralService.queryByKey(bean.getCollateralId());
			collateral.setCheckUid(bean.getCheckUid());;
	        collateral.setCheckTime(bean.getCheckTime());
	        collateral.setCheckRemark(bean.getRemark());
	        collateral.setLatestCheck(bean.getState());	
	        collateral.setUpdateTime(bean.getCheckTime());
			collateral.setUpdateUid((String) request.getSession().getAttribute(SysConstants.LOGIN_ID));
	        this.collateralService.update(collateral);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}
	
}
