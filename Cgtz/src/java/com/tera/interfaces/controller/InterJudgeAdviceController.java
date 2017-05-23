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

import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.judge.model.JudgeAdv;
import com.tera.audit.judge.service.IJudgeAdviceService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.interfaces.model.AppAuditBean;
import com.tera.interfaces.model.AppJudgeAdviceBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.UserExtService;

/**
 * 评审会意见相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/judgeAdvice")
public class InterJudgeAdviceController extends BaseController {

	private static final Logger log = Logger.getLogger(InterJudgeAdviceController.class);
	
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private IJudgeAdviceService judgeAdviceService;
	
	
	@RequestMapping("/list.do")
	public void judgeAdviceList(String loginId,HttpServletRequest request,HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		log.info("请求参数：loginId<--------------->"+loginId);
		
		PrintWriter writer = null;

		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			UserExt userExt = this.userExtService.queryByKey(loginId);
			Map<String, Object> queryMap = new HashMap<String,Object>();
			
			queryMap.put("bpmStates", new String[]{CommonConstant.PROCESS_E});
			queryMap.put("judgeUser", loginId);
			queryMap.put("appState", "E1"); // 评审会意见状态
			queryMap.put("orgId", userExt.getOrgId());
			queryMap.put("isRenew", "0");
			queryMap.put("appProducts", new String[]{"01","03"}); // 车贷和房贷
			List<LoanBase> judgeAdviceList = this.loanBaseService.queryBusinessList(queryMap);
			
			String jsonStr = "";
			if (judgeAdviceList != null && judgeAdviceList.size() > 0) {
				for (LoanBase loanBase : judgeAdviceList) {
					loanBase.setAppState(loanBase.getAppState());
				}
			}
			
			jsonStr = GsonUtils.getInstance().toJson(judgeAdviceList, List.class);
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
	public void judgeAdviceUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
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
			
			LoanBase loanBase = null;
			try {
				// 申请信息
				loanBase = this.loanBaseService.queryByKey(id);
				String loanId = loanBase.getLoanId();
				
				AppAuditBean bean = new AppAuditBean();
				bean.setLoanId(loanId);
				bean.setIsTgth(loanBase.getIsTgth());
				
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
	public void judgeAdviceSave(@RequestBody String reqBody, HttpServletRequest request,HttpServletResponse response) {
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
			AppJudgeAdviceBean bean = GsonUtils.getInstance().fromJson(reqBody, AppJudgeAdviceBean.class);
			
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("loanId", bean.getLoanId());
			queryMap.put("num", this.judgeAdviceService.getNextNum(bean.getLoanId())-1);
			queryMap.put("auditUid", bean.getLoginId());
			List<JudgeAdv> advs = this.judgeAdviceService.queryList(queryMap );
			
			JudgeAdv adv = new JudgeAdv();
			adv.setId(advs.get(0).getId());
			adv.setAuditAdv(bean.getAuditAdv());
			adv.setLoanId(bean.getLoanId());
			
			JsonMsg jsonMsg = this.judgeAdviceService.operateProcess(adv, bean.getLoginId());
			
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
