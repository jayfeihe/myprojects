/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.lend.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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

import com.tera.lend.model.LendApp;
import com.tera.lend.service.LendAppService;
import com.tera.lend.service.LendDivestService;
import com.tera.match.model.Lend2match;
import com.tera.match.service.Lend2matchService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;


@Controller
@RequestMapping("/lend/divest") 
public class LendDivestController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LendDivestController.class);
	
	/**
	 * LendAppService
	 */
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;
	
	@Autowired(required=false) //自动注入
	private LendDivestService lendDivestService;
	
	@Autowired(required=false) //自动注入
	Lend2matchService<Lend2match> lend2matchService;
	
	@Autowired(required=false) //自动注入
	ProductService<Product> productService;
	
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;

	/**
	 * 跳转到撤资的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String lendDivestQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "lend/divest/lendDivestQuery";
	}
	
	/**
	 * 显示撤资的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 撤资; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String lendDivestList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm=new PageModel();
		LendApp bean=(LendApp)RequestUtils.getRequestBean(LendApp.class, request);
		Map<String, Object> queryMap=ObjectUtils.describe(bean);
		//-----------其他查询条件-------------------
		//1.Lend2Match表中的state={1,2}
		//2.delete计息日的三个月之后
		//3.delete结束日期要在今天日期+5天之后
		
		//long dqDate=System.currentTimeMillis();
		
		queryMap.put("state", new String[]{"1","2"});
		
		//queryMap.put("valueDate", DateUtils.formatDate(DateUtils.addMonth(new java.sql.Timestamp(dqDate), -3)));
	
		//queryMap.put("endDate",DateUtils.formatDate(DateUtils.addDay(new java.sql.Timestamp(dqDate), 5)));
		//获取当前登陆用户的机构
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String orgId=org.getOrgId();
		queryMap.put("orgId", orgId);
		//获取当前登陆用户Id
		String currentLoginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);
		queryMap.put("userLoginId", currentLoginId);
		int rowsCount=this.lend2matchService.queryDivestCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<LendApp> divestList=this.lend2matchService.queryDivestList(queryMap);
		pm.setData(divestList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "lend/divest/lendDivestList";
	}
	/**
	 * 显示撤资操作页面
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showDivest.do")
	public String showDivest(int id, HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//根据申请获得该申请的产品，从而获得所有撤资违约金比例
		LendApp lendApp=this.lendAppService.queryByKey(id);
		Double rate1=productService.queryByName(lendApp.getProduct()).getDefaultRate1();
		Double rate2=productService.queryByName(lendApp.getProduct()).getDefaultRate2();
		Double rate3=productService.queryByName(lendApp.getProduct()).getDefaultRate3();
		Double rate4=productService.queryByName(lendApp.getProduct()).getDefaultRate4();
		List<String> rateList=new ArrayList<String>();
		rateList.add(rate1.toString());
		rateList.add(rate2.toString());
		rateList.add(rate3.toString());
		rateList.add(rate4.toString());
		for (int i = 0; i < rateList.size(); i++) {  
	         
	        if ("0.0".equals(rateList.get(i))) {  
	        	rateList.set(i, "不可撤资");  
	        }  
	    }  
		map.put("rateList", rateList);
		map.put("lendId", id);
		return "lend/divest/lendDivest";
	}
	
	@RequestMapping("/divest.do")
	public void divest(int lendId,String defaultRate,String divestDays,HttpServletRequest request,
			HttpServletResponse response, ModelMap map)throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		System.out.println("defaultRate====="+defaultRate);
		System.out.println("lendId====="+lendId);
		System.out.println("divestDays====="+divestDays);
		try {
			//判断条件
			//1.Lend_2Match里计息日VALUE_DATE 要在三个月之后Today>VALUE_DATE+3月 
			LendApp lendApp=lendAppService.queryByKey(lendId);
			String lendAppId=lendApp.getAppId();
			long dqDate=System.currentTimeMillis();
			Map<String, Object> map2=new HashMap<String, Object>();
			map2.put("lendAppId", lendAppId);
			List<Lend2match> lend2matchList=lend2matchService.queryList(map2);
			
			if ("不可撤资".equals(defaultRate)) {
				writer.print(JsonUtil.object2json(new JsonMsg(true, "该产品不可撤资")));
				return;
			}
			
			if (lend2matchList.size()>0) {
				if (DateUtils.addMonth(lend2matchList.get(0).getValueDate(), 3).after(new Date(dqDate))) {
					writer.print(JsonUtil.object2json(new JsonMsg(true, "要在还息日三个月后才能撤资")));
					return;
				}
				//2.结束的日期要大于选择的天数
				if (DateUtils.getDayRange(new Date(dqDate),lend2matchList.get(0).getEndDate())<=Integer.parseInt(divestDays)) {
					writer.print(JsonUtil.object2json(new JsonMsg(true, "到期日要大于所选择的天数")));
					return;
				}
			}
			//撤资操作
			lendDivestService.Divest(lendId,defaultRate,divestDays);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
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
