package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.form.CollateralJsonMsg;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.interfaces.model.AppCollateralBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;

/**
 * 抵押物相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/collateral")
public class InterCollateralController extends BaseController {

	private static final Logger log = Logger.getLogger(InterCollateralController.class);
	
	@Autowired
	private ICollateralService collateralService;
	
	
	@RequestMapping("/list.do")
	public void collList(String loanId,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：loanId<--------------->"+loanId);
		
		PrintWriter writer = null;
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			if (null == loanId) {
				loanId = "";
			}
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("loanId", loanId);
			List<Collateral> colls = this.collateralService.queryList(queryMap);
			
			String jsonStr = "";
			
			jsonStr = GsonUtils.getInstance().toJson(colls, List.class);
			log.info("== 响应报文："+ jsonStr +" ==");
			
			writer.print(jsonStr);
		} catch (Exception e) {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/update.do")
	public void collUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (id != null && id != 0) {
			PrintWriter writer = null;
			
			try {
				response.setContentType("application/json;charset=UTF-8");
				writer = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Collateral coll = null;
			try {
				coll = this.collateralService.queryByKey(id);
				
				String jsonStr = GsonUtils.getInstance().toJson(coll);
				log.info("== 响应报文："+ jsonStr +" ==");
				writer.print(jsonStr);
				
			} catch (Exception e) {
				writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
				e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			}
		}
		
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/save.do")
	public void collSave(@RequestBody String reqBody,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		try {
			reqBody = URLDecoder.decode(reqBody, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		log.info("== 请求报文:"+reqBody+" ==");
		
		PrintWriter writer = null;
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			AppCollateralBean bean = GsonUtils.getInstance().fromJson(reqBody, AppCollateralBean.class);
			
			CollateralJsonMsg jsonMsg = this.collateralService.saveOrUpdate(bean.getCollateral(), bean.getLoginId());
			
			String jsonStr = GsonUtils.getInstance().toJson(jsonMsg);
			log.info("== 响应报文："+ jsonStr +" ==");
			
			writer.print(jsonStr);
		} catch (Exception e) {
			writer.print(GsonUtils.getInstance().toJson(new JsonMsg(false,"接口服务调用失败")));
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		log.info(thisMethodName+":end");
	}
}
