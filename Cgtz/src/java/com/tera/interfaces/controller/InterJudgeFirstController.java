package com.tera.interfaces.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.tera.audit.judge.model.form.JudgeFormBean;
import com.tera.audit.judge.service.IJudgeFirstServcie;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.interfaces.model.AppAuditBean;
import com.tera.interfaces.model.AppJudgeUser;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.User;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.UserExtService;
import com.tera.sys.service.UserService;

/**
 * 评审会初审相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/judgeFirst")
public class InterJudgeFirstController extends BaseController {

	private static final Logger log = Logger.getLogger(InterJudgeFirstController.class);
	
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private IJudgeFirstServcie judgeFirstService;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/list.do")
	public void judgeFirstList(String loginId,HttpServletRequest request,HttpServletResponse response) {
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
			
			queryMap.put("bpmStates", new String[]{CommonConstant.PROCESS_D});
			queryMap.put("processer", loginId);
			queryMap.put("orgId", userExt.getOrgId());
			queryMap.put("isRenew", "0");
			queryMap.put("appProducts", new String[]{"01","03"}); // 车贷和房贷
			List<LoanBase> judgeFirstList = this.loanBaseService.queryBusinessList(queryMap);
			
			String jsonStr = "";
			if (judgeFirstList != null && judgeFirstList.size() > 0) {
				for (LoanBase loanBase : judgeFirstList) {
					loanBase.setAppState(loanBase.getAppState());
				}
			}
			
			jsonStr = GsonUtils.getInstance().toJson(judgeFirstList, List.class);
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
	public void judgeFirstUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
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
				
				// 获取评审会成员
				List<User> judgeUsers = this.userService.getUserByOrgAndRole(BusinessConstants.ORG_CODE,
						new String[] { CommonConstant.ROLE_JUDGE_DIR }, new String[] { loanBase.getOrg() });
				
				List<AppJudgeUser> jdUsers = new ArrayList<AppJudgeUser>();
				if (judgeUsers != null && judgeUsers.size() > 0) {
					for (User user : judgeUsers) {
						jdUsers.add(new AppJudgeUser(user.getLoginId(), user.getName()));
					}
				}
				
				bean.setJudgeUsers(jdUsers);
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
	public void judgeFirstSave(@RequestBody String reqBody, HttpServletRequest request,HttpServletResponse response) {
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
			
			List<String> judgeUids = bean.getJudgeUids();
			
			formBean.setJudgeUsers(judgeUids.toArray(new String[judgeUids.size()]));
			
			JsonMsg jsonMsg = this.judgeFirstService.operateProcess(formBean, bean.getLoginId());
			
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
