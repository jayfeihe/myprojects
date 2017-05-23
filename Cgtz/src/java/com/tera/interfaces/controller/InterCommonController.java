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

import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.util.MathUtils;

/**
 * 共同借款人相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/loanCommon")
public class InterCommonController extends BaseController {

	private static final Logger log = Logger.getLogger(InterCommonController.class);
	
	@Autowired
	private ILoanAppService loanAppService;
	
	
	@RequestMapping("/list.do")
	public void commonList(String loanId,HttpServletRequest request,HttpServletResponse response) {
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
			queryMap.put("loanId",loanId);
			queryMap.put("mainFlag","0");
			List<LoanApp> commons = this.loanAppService.queryList(queryMap);
			
			String jsonStr = "";
			
			jsonStr = GsonUtils.getInstance().toJson(commons, List.class);
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
	public void commonUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
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
			LoanApp common = null;
			try {
				common = this.loanAppService.queryByKey(id);
				common.setYearIncome(MathUtils.div(common.getYearIncome(), 10000)); // 年收入单位转换
				String jsonStr = GsonUtils.getInstance().toJson(common);
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
	public void commonSave(@RequestBody String reqBody,HttpServletRequest request,HttpServletResponse response) {
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
			
			LoanApp commonApp = GsonUtils.getInstance().fromJson(reqBody, LoanApp.class);
			
			AppFormBean formBean = new AppFormBean();
			formBean.setAppTypeLoan(commonApp);
			
			LoanBaseJsonMsg jsonMsg = this.loanAppService.saveCommonLoan(formBean,null,null);
			
			String jsonStr = GsonUtils.getInstance().toJson(jsonMsg);
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
}
