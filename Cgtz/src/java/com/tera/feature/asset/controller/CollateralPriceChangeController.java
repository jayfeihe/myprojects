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
import java.util.HashMap;
import java.util.Iterator;
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
import com.tera.feature.asset.model.Asset;
import com.tera.feature.asset.model.CollateralPriceChange;
import com.tera.feature.asset.service.ICollateralPriceChangeService;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 价值变动表控制器
 * <b>功能：</b>CollateralPriceChangeController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 11:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/asset/change")
public class CollateralPriceChangeController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollateralPriceChangeController.class);
	
	/**
	 * CollateralPriceChangeService
	 */
	@Autowired(required=false) //自动注入
	private ICollateralPriceChangeService collateralPriceChangeService;
	
	@Autowired(required=false) //自动注入
	private ICollateralService collateralService;
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	/**
	 * 跳转到价值变动表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collateralPriceChangeQuery(String collateralId,String isValueChange,String state,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("collateralId", collateralId);
		map.put("isValueChange", isValueChange);
		map.put("state", state);
		log.info(thisMethodName+":end");
		return "asset/change/collateralPriceChangeQuery";
	}

	/**
	 * 显示价值变动表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collateralPriceChangeList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		CollateralPriceChange bean = (CollateralPriceChange)RequestUtils.getRequestBean(CollateralPriceChange.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<CollateralPriceChange> assetList = this.collateralPriceChangeService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("collateralId",bean.getCollateralId());
		log.info(thisMethodName+":end");
		return "asset/change/collateralPriceChangeList";
	}

	/**
	 * 跳转到更新价值变动表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String collateralPriceChangeUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollateralPriceChange bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.collateralPriceChangeService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "asset/change/collateralPriceChangeUpdate";
	}

	/**
	 * 保存价值变动表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void collateralPriceChangeSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			CollateralPriceChange bean = (CollateralPriceChange) RequestUtils.getRequestBean(CollateralPriceChange.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.collateralPriceChangeService.update(bean);
			} else { //如果不存在
				Timestamp nowTime=new Timestamp(System.currentTimeMillis());
				bean.setOperTime(nowTime);
				bean.setOperUid((request.getSession().getAttribute("loginid").toString()));
				//更改抵押物信息
				updateCollateral(bean);
				this.collateralPriceChangeService.add(bean);
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
	 * 删除价值变动表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collateralPriceChangeDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.collateralPriceChangeService.delete(id);
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
	 * 跳转到查看价值变动表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String collateralPriceChangeRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollateralPriceChange bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.collateralPriceChangeService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "asset/change/collateralPriceChangeRead";
	}
	
	public void updateCollateral(CollateralPriceChange bean){
		//价值变动，更改抵押物信息
        Collateral collateral=null;
		try {
			collateral = this.collateralService.queryByKey(bean.getCollateralId());
			collateral.setLatestPrice(bean.getNewAmt());
	        collateral.setChangeTime(bean.getOperTime());
	        collateral.setChangeUid(bean.getOperUid());
	        collateral.setChangeRemark(bean.getRemark());
	        //要计算续贷相关的押品价值
	     
	        collateral.setIsValueChange("1");
	        //修改人和修改时间
	        collateral.setUpdateTime(bean.getOperTime());
	        collateral.setUpdateUid(bean.getOperUid());
	        this.collateralService.update(collateral);
	        // 判断是否足额，对于整个的申请借贷，包含续贷的
	        Map<String, Object> map =new HashMap<String, Object>();
	        LoanBase loanBase=loanBaseService.queryByLoanId(collateral.getLoanId());
	        loanBase.setIsEnough("0");
	        if ("0".equals(loanBase.getIsRenew())) {
	        	map.put("loanId", collateral.getLoanId());
	        	map.put("state", "1");
				List<Collateral> listCollaterals=collateralService.queryList(map);
				double db=0;
				for (Collateral col : listCollaterals) {
					if ("1".equals(col.getIsValueChange())) {
						db=MathUtils.add(db, col.getLatestPrice());
					}else {
						db=MathUtils.add(db, col.getEvalPrice());
					}
				}
				if (MathUtils.sub(db,loanBase.getLoanAmt())>=0) {
					loanBase.setIsEnough("1");
				}
				
			}else {//续贷的
				map.clear();
				map.put("origLoanId", loanBase.getOrigLoanId());
				map.put("loanId", loanBase.getLoanId());
				map.put("state", "1");
				List<Collateral> listCollaterals=collateralService.queryListByLoanId(map);
				double db=0;
				for (Collateral col : listCollaterals) {
					if ("1".equals(col.getIsValueChange())) {
						db=MathUtils.add(db, col.getLatestPrice());
					}else {
						db=MathUtils.add(db, col.getEvalPrice());
					}
				}
				if (MathUtils.sub( db,loanBase.getLoanAmt())>=0) {
					loanBase.setIsEnough("1");
				}
			}
	        loanBaseService.update(loanBase);
	       
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}
}
