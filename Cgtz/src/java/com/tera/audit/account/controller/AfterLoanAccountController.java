package com.tera.audit.account.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.model.BillAcct;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.AccountJsonMsg;
import com.tera.audit.account.model.form.AfterLoanQBean;
import com.tera.audit.account.service.IAfterLoanAccountService;
import com.tera.audit.account.service.IBillAcctService;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/** 贷后核帐Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/account/afterloan")
public class AfterLoanAccountController extends BaseController {

	private final static Logger log = Logger.getLogger(AfterLoanAccountController.class);
	
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	@Autowired
	private IAfterLoanAccountService afterLoanAccountService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private IBillAcctService billAcctService;
	
	/**
	 * 跳转到贷后核帐的查询条件页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String afterLoanQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "account/afterloan/afterLoanQuery";
	}
	
	/**
	 * 跳转到贷后核帐的列表页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String afterLoanList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(AfterLoanQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		if (0 == loginUser.getIsAdmin()) {
			queryMap.put("orgId", loginOrg.getOrgId());
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<AfterLoanQBean> afterLoanList = this.afterLoanAccountService.queryPageList(queryMap);
		pm.setData(afterLoanList);
		pm.initRowsCount(afterLoanList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "account/afterloan/afterLoanList";
	}
	
	@RequestMapping("/update.do")
	public String afterLoanUpdate(Integer id,String contractId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		Contract contract = null;
		//如果存在
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
			contract = this.contractService.queryByContractId(contractId);
			
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanBase.getOrigLoanId());
				map.put("origLoanBase", origLoanBase);
				map.put("isTgth", origLoanBase.getIsTgth());
			} else {
				map.put("isTgth", loanBase.getIsTgth());
			}
		}
		map.put("loanBase", loanBase);
		map.put("contract", contract);
		
		// 影像资料场景类型
		// 个人
		if ("01".equals(loanBase.getLoanType())) {
			map.put("sec","filesce1");
		}
		// 公司
		if ("02".equals(loanBase.getLoanType())) {
			map.put("sec","filesce2");
		}
		log.info(thisMethodName+":end");
		return "account/afterloan/afterLoanUpdate";
	}
	
	@RequestMapping("/save.do")
	public void afterLoanSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
//		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		try {
			AccountFormBean bean = (AccountFormBean) RequestUtils.getRequestBean(AccountFormBean.class, request);
			AccountJsonMsg jsonMsg = this.afterLoanAccountService.collected(bean,loginId);
			writer.print(JsonUtil.object2json(jsonMsg));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
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
	 * 跳转到交易记录的查询条件页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/acctQuery.do")
	public String billAcctQuery(String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId", loanId);
		map.put("contractId", contractId);
		log.info(thisMethodName+":end");
		return "account/afterloan/billAcctQuery";
	}
	
	/**
	 * 跳转到交易记录的列表页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/acctList.do")
	public String billAcctList(String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(AccountFormBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<BillAcct> billAcctList = this.billAcctService.queryPageList(queryMap);
		pm.setData(billAcctList);
		pm.initRowsCount(billAcctList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "account/afterloan/billAcctList";
	}
}
