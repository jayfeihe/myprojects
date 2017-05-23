/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.search.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.search.model.AfterLoanSearchBean;
import com.tera.feature.search.service.IAfterLoanSearchService;

import com.tera.sys.model.PageModel;

import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 *功能:RecordController  稽查逾期记录controller
 *时间:2016年3月7日上午10:42:11
 *@author Ldh
 */

@Controller
@RequestMapping("/search/record")
public class RecordController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(RecordController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private IAfterLoanSearchService afterLoanSearchService;
		
	/**
	 * 显示逾期记录列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String recordList(String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		
		AfterLoanSearchBean bean =(AfterLoanSearchBean) RequestUtils.getRequestBean(AfterLoanSearchBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
	
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		queryMap.put("contractId",contractId);		
		PageList<AfterLoanSearchBean> recordlList = this.afterLoanSearchService.queryRecordPageList(queryMap);
		pm.setData(recordlList);
		pm.initRowsCount(recordlList.getPaginator().getTotalCount());
		map.put("pm",pm);
		log.info(thisMethodName+":end");
		return "search/recordList";
	}
	
}
