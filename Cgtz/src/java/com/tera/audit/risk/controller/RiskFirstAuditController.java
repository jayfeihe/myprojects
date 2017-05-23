package com.tera.audit.risk.controller;

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
import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.risk.service.IRiskFirstAuditService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/** 风控初审Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/risk/first")
public class RiskFirstAuditController {

	private final static Logger log = Logger.getLogger(RiskFirstAuditController.class);
	
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	@Autowired
	private IRiskFirstAuditService riskFirstAuditService;
	
	
	/**
	 * 跳转到风控初审的查询条件页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String riskAuditQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "risk/first/riskFirstAuditQuery";
	}
	
	/**
	 * 跳转到风控初审的列表页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String riskAuditList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanBase.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		queryMap.put("bpmStates", new String[]{CommonConstant.PROCESS_C});
		if (0 == loginUser.getIsAdmin()) {
			queryMap.put("processer", loginUser.getLoginId());
			queryMap.put("orgId", loginOrg.getOrgId());
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanBase> loanBaseList = this.loanBaseService.queryPageList(queryMap);
		pm.setData(loanBaseList);
		pm.initRowsCount(loanBaseList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "risk/first/riskFirstAuditList";
	}
	
	@RequestMapping("/update.do")
	public String riskAuditUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			loanBase  = this.loanBaseService.queryByKey(id);
			
			// 影像资料场景类型
			// 个人
			if ("01".equals(loanBase.getLoanType())) {
				map.put("sec","filesce1");
			}
			// 公司
			if ("02".equals(loanBase.getLoanType())) {
				map.put("sec","filesce2");
			}
		}
		map.put("loanBase", loanBase);
		
		// 续贷
		if ("1".equals(loanBase.getIsRenew())) {
			LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanBase.getOrigLoanId());
			map.put("origLoanBase", origLoanBase);
			map.put("isTgth", origLoanBase.getIsTgth());
		} else {
			map.put("isTgth", loanBase.getIsTgth());
		}
		
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
		return "risk/first/riskFirstAuditUpdate";
	}
	
	@RequestMapping("/save.do")
	public void riskAuditSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		try {
			AuditFormBean bean = (AuditFormBean) RequestUtils.getRequestBean(AuditFormBean.class, request);
			JsonMsg jsonMsg = this.riskFirstAuditService.operateProcess(bean,loginId);
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
}
