package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
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
import com.tera.audit.loan.model.form.CollateralJsonMsg;
import com.tera.audit.loan.model.form.LoanLawQBean;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanLawService;
import com.tera.feature.loanguar.model.LoanGuar;
import com.tera.feature.loanguar.service.ILoanGuarService;
import com.tera.interfaces.model.AppLoanLawBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;

/**
 * 抵押物相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/loanLaw")
public class InterLoanLawController extends BaseController {

	private static final Logger log = Logger.getLogger(InterLoanLawController.class);
	
	@Autowired
	private ILoanLawService loanLawService;
	@Autowired
	private ILoanAppService loanAppService;
	@Autowired
	private ILoanGuarService loanGuarService;
	
	@RequestMapping("/list.do")
	public void loanLawList(String loanId,HttpServletRequest request,HttpServletResponse response) {
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
			List<LoanLawQBean> loanLaws = this.loanLawService.queryListByLoanId(queryMap);
			
			String jsonStr = "";
			
			jsonStr = GsonUtils.getInstance().toJson(loanLaws, List.class);
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
	public void loanLawUpdate(Integer id,String targetType,HttpServletRequest request,HttpServletResponse response) {
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
			
			try {
				
				AppLoanLawBean bean = new AppLoanLawBean();
				bean.setTargetType(targetType);
				if ("1".equals(targetType)) {
					LoanApp app = this.loanAppService.queryByKey(id);
					bean.setId(app.getId());
					bean.setLawState(app.getLawState());
					bean.setLawRemark(app.getLawRemark());
				}
				// 担保
				if ("2".equals(targetType)) {
					LoanGuar guar = this.loanGuarService.queryByKey(id);
					bean.setId(guar.getId());
					bean.setLawState(guar.getLawState());
					bean.setLawRemark(guar.getLawRemark());
				}
				
				String jsonStr = GsonUtils.getInstance().toJson(bean);
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
	public void loanLawSave(@RequestBody String reqBody,HttpServletRequest request,HttpServletResponse response) {
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
			
			AppLoanLawBean bean = GsonUtils.getInstance().fromJson(reqBody, AppLoanLawBean.class);
			
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			
			CollateralJsonMsg jsonMsg = null;
			
			// 申请
			if ("1".equals(bean.getTargetType())) {
				LoanApp loanApp = this.loanAppService.queryByKey(bean.getId());
				loanApp.setLawState(bean.getLawState());
				loanApp.setLawRemark(bean.getLawRemark());
				loanApp.setUpdateTime(nowTime);
				this.loanAppService.update(loanApp);
				jsonMsg = new CollateralJsonMsg(bean.getId(), bean.getLoanId(), bean.getTargetType(), true, "成功");
			}
			// 担保
			if ("2".equals(bean.getTargetType())) {
				LoanGuar loanGuar = this.loanGuarService.queryByKey(bean.getId());
				loanGuar.setLawState(bean.getLawState());
				loanGuar.setLawRemark(bean.getLawRemark());
				loanGuar.setUpdateTime(nowTime);
				this.loanGuarService.update(loanGuar);
				jsonMsg = new CollateralJsonMsg(bean.getId(), bean.getLoanId(), bean.getTargetType(), true, "成功");
			}
			
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
