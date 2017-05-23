/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.product.controller;

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

import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
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
 * <br>
 * <b>功能：</b>ProductController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-08 08:57:47<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/product/hedao")
public class ProductController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ProductController.class);
	
	/**
	 * ProductService
	 */
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	
	/**
	 * 跳转到产品表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String productQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "product/hedao/productQuery";
	}

	/**
	 * 显示产品表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String productList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Product.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.productService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Product> productList = this.productService.queryList(queryMap);
		pm.setData(productList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "product/hedao/productList";
	}

	/**
	 * 跳转到更新产品表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String productUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Product bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.productService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "product/hedao/productUpdate";
	}

	/**
	 * 保存产品表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void productSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Product bean = (Product) RequestUtils.getRequestBean(Product.class, request);
			String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			Org org =  (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			//如果存在
			if (bean.getId() != 0) {
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				bean.setOperator(loginid);
				bean.setOrgId(org.getOrgId());
				this.productService.updateOnlyChanged(bean);
			} else { //如果不存在
				bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				bean.setOperator(loginid);
				bean.setOrgId(org.getOrgId());
				this.productService.add(bean);
			}
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

	/**
	 * 删除产品表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void productDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.productService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
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
	 * 跳转到查看产品表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String productRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Product bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.productService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "product/hedao/productRead";
	}
	@RequestMapping("/listjason.do")
	public void dataDictionaryListJson(String flag,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Object bean = RequestUtils.getRequestBean(Product.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		// 审核、审批产品下拉框排序
		if ("1".equals(flag)) {
			queryMap.put("flag", flag);
		}
		List<Product> productList = this.productService.queryList(queryMap);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(productList));
		writer.flush();
		writer.close();
	}
	
	/**
	 * 只查找合作方产品
	 * 
	 * @param flag
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/listJsonByCooperation.do")
	public void productListJsonByCooperation(String cooperation,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Object bean = RequestUtils.getRequestBean(Product.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		if (StringUtils.isNotNullAndEmpty(cooperation)) {
			queryMap.put("productName", "\'%"+cooperation+"%\'");
		}
		List<Product> productList = this.productService.queryListByCooperation(queryMap);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(productList));
		writer.flush();
		writer.close();
	}
	
	/**
	 * up or down挂起与解除
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/upordown.do")
	public String productUpordown(int id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Product product = productService.queryByKey(id);
				if (product.getId() != 0) {
				if("1".equals(product.getState())){
					product.setState("2");
				}else if("2".equals(product.getState())){
					product.setState("1");
				}
				this.productService.update(product);
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			throw e;
		}
		log.info(thisMethodName+":end");
		return "product/hedao/productQuery";
	}
}
