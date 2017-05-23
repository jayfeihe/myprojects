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
import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.service.IBankBranchInfoService;
import com.tera.audit.loan.service.IContactService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.interfaces.model.AppBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.UserExtService;
import com.tera.util.MathUtils;

/**
 * 申请相关接口
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/inter/loanBase")
public class InterLoanBaseController extends BaseController {

	private static final Logger log = Logger.getLogger(InterLoanBaseController.class);
	
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ILoanAppService loanAppService;
	@Autowired
	private IContactService contactService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private IBankBranchInfoService bankBranchInfoService;
	
	
	@RequestMapping("/list.do")
	public void baseList(String loginId,HttpServletRequest request,HttpServletResponse response) {
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
			
			queryMap.put("bpmStates", new String[]{CommonConstant.PROCESS_A});
			queryMap.put("orgId", userExt.getOrgId());
			queryMap.put("processer", loginId);
			queryMap.put("salesman", loginId);
			queryMap.put("isRenew", "0");
			queryMap.put("appProducts", new String[]{"01","03"}); // 车贷和房贷
			List<LoanBase> baseList = this.loanBaseService.queryBusinessList(queryMap);
			
			String jsonStr = "";
			if (baseList != null && baseList.size() > 0) {
				for (LoanBase loanBase : baseList) {
					loanBase.setAppState(loanBase.getAppState());
				}
			}
			
			jsonStr = GsonUtils.getInstance().toJson(baseList, List.class);
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
	public void baseUpdate(Integer id,HttpServletRequest request,HttpServletResponse response) {
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
			LoanApp loanApp = null;
			List<LoanApp> loanApps = null;
			List<Contact> contacts = null;
			try {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				// 申请信息
				loanBase = this.loanBaseService.queryByKey(id);
				String loanId = loanBase.getLoanId();
				
				queryMap.put("loanId", loanId);
				queryMap.put("mainFlag", "1"); // 主借款人
				loanApps = this.loanAppService.queryList(queryMap );
				contacts = this.contactService.queryByLoanId(loanId);
				
				// 客户基本信息
				loanApp = loanApps.get(0);
				
				loanApp.setYearIncome(MathUtils.div(loanApp.getYearIncome(), 10000)); // 年收入单位转换
				loanApp.setOrgRegAmt(MathUtils.div(loanApp.getOrgRegAmt(), 10000.00)); // 注册资本单位转换
				loanApp.setOrgSalesAmt(MathUtils.div(loanApp.getOrgSalesAmt(), 10000.00)); // 年销售额单位转换
				
				AppBean bean = new AppBean();
				bean.setContacts(contacts);
				bean.setLoanApp(loanApp);
				bean.setLoanBase(loanBase);
				
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
	public void baseSave(@RequestBody String reqBody, HttpServletRequest request,HttpServletResponse response) {
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
			AppBean bean = GsonUtils.getInstance().fromJson(reqBody, AppBean.class);
			LoanBase loanBase = bean.getLoanBase();
			LoanApp loanApp = bean.getLoanApp();
			List<Contact> contacts = bean.getContacts();
			
			AppFormBean formBean = new AppFormBean();
			
			formBean.setLoanBase(loanBase);
			formBean.setContacts(contacts);
			formBean.setSaleRemark(loanApp.getSaleRemark());
			formBean.setButtonType(bean.getButtonType());
			formBean.setLoanWay(LoanBase.LOAN_WAY_APP); // APP端
			
			// 个人申请
			if ("01".equals(loanBase.getLoanType())) {
				loanApp.setYearIncome(MathUtils.mul(loanApp.getYearIncome(), 10000.00)); // 年收入单位转换
				formBean.setAppTypeLoan(loanApp);
				formBean.setSaleRemark(loanApp.getSaleRemark());
			}
			// 公司申请
			if ("02".equals(loanBase.getLoanType())) {
				loanApp.setOrgRegAmt(MathUtils.mul(loanApp.getOrgRegAmt(), 10000.00)); // 注册资本单位转换
				loanApp.setOrgSalesAmt(MathUtils.mul(loanApp.getOrgSalesAmt(), 10000.00)); // 年销售额单位转换
				formBean.setComTypeLoan(loanApp);
			}
			
			UserExt userExt = this.userExtService.queryByKey(bean.getLoginId());
			
			//银行信息维护
			bankBranchInfoService.verifyBank(formBean.getLoanBase().getAcctBank(), request);
			LoanBaseJsonMsg jsonMsg = this.loanBaseService.loanAppProcess(formBean, bean.getLoginId(), userExt.getOrgId());
			
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
