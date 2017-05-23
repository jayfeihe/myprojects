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
import com.tera.audit.judge.model.form.JudgeFormBean;
import com.tera.audit.judge.service.IJudgeAdviceService;
import com.tera.audit.judge.service.IJudgeReviewService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.interfaces.model.AppAuditBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.UserExtService;

/**
 * 评审会复核相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/judgeReview")
public class InterJudgeReviewController extends BaseController {

	private static final Logger log = Logger.getLogger(InterJudgeReviewController.class);
	
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private IJudgeReviewService judgeReviewService;
	@Autowired
	private IJudgeAdviceService judgeAdviceService;
	
	
	@RequestMapping("/list.do")
	public void judgeReviewList(String loginId,HttpServletRequest request,HttpServletResponse response) {
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
			queryMap.put("processer", loginId);
			queryMap.put("orgId", userExt.getOrgId());
			queryMap.put("isRenew", "0");
			queryMap.put("appProducts", new String[]{"01","03"}); // 车贷和房贷
			List<LoanBase> judgeReviewList = this.loanBaseService.queryBusinessList(queryMap);
			
			String jsonStr = "";
			if (judgeReviewList != null && judgeReviewList.size() > 0) {
				for (LoanBase loanBase : judgeReviewList) {
					loanBase.setAppState(loanBase.getAppState());
				}
			}
			
			jsonStr = GsonUtils.getInstance().toJson(judgeReviewList, List.class);
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
	public void judgeReviewUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
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
				bean.setNodeType("1");
				
				// 评审会意见
				Map<String, Object> queryMap = new HashMap<String,Object>();
				queryMap.put("loanId", loanBase.getLoanId());
				queryMap.put("num", this.judgeAdviceService.getNextNum(loanBase.getLoanId())-1);
				List<JudgeAdv> judgeAdvs = this.judgeAdviceService.queryList(queryMap );
				
				bean.setJudgeAdvs(judgeAdvs);
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
	public void judgeReviewSave(@RequestBody String reqBody, HttpServletRequest request,HttpServletResponse response) {
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
			AppAuditBean bean = GsonUtils.getInstance().fromJson(reqBody, AppAuditBean.class);
			
			JudgeFormBean formBean = new JudgeFormBean();
			formBean.setDecision(bean.getDecision());
			formBean.setLoanId(bean.getLoanId());
			formBean.setNode(bean.getNode());
			formBean.setRemark(bean.getRemark());
			
			JsonMsg jsonMsg = this.judgeReviewService.operateProcess(formBean, bean.getLoginId());
			
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
