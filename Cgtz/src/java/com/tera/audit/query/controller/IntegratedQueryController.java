package com.tera.audit.query.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.query.model.IntegratedQueryBean;
import com.tera.audit.query.service.IIntegratedQueryService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

@Controller
@RequestMapping("/integrated")
public class IntegratedQueryController {

	private final static Logger log = Logger.getLogger(IntegratedQueryController.class);
	
	@Autowired
	private IIntegratedQueryService integratedQueryService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private IContractService contractService;
	
	@RequestMapping("/query.do")
	public String integratedQuery(ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "integrated/integratedQuery";
	}
	
	@RequestMapping("/list.do")
	public String integratedList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		Object bean = RequestUtils.getRequestBean(IntegratedQueryBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		if (0 == loginUser.getIsAdmin()) {
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("loginId", loginUser.getLoginId());
			roleMap.put("orgId", loginOrg.getOrgId());
			List<Role> loginRoles = this.roleService.getRoleByOrgLoginId(roleMap );
			if (loginRoles != null && loginRoles.size() > 0) {
				for (Role role : loginRoles) {
					// 是业务员自己看自己的
					if (CommonConstant.ROLE_SALESMAN.equals(role.getName()) && "1".equals(role.getFlag())) {
						queryMap.put("salesman", loginUser.getLoginId());
					}
				}
			}
			queryMap.put("orgId", loginOrg.getOrgId());
		}
		
		
		queryMap.put("branchBpmState", CommonConstant.PROCESS_B); // 分公司审批
		queryMap.put("riskFirstBpmState", CommonConstant.PROCESS_C); // 风控初审
		queryMap.put("riskCheckBpmState", CommonConstant.PROCESS_F); // 风控复核
		queryMap.put("meetFirstBpmState", CommonConstant.PROCESS_D); // 评审会初审
		queryMap.put("meetCheckBpmState", CommonConstant.PROCESS_E); // 评审会复核
		queryMap.put("lawFirstBpmState", CommonConstant.PROCESS_G1); // 法务初审
		queryMap.put("lawInsideBpmState", CommonConstant.PROCESS_G2); // 法务内勤
		queryMap.put("lawReviewBpmState", CommonConstant.PROCESS_G3); // 法务复核
		queryMap.put("cashBpmState", CommonConstant.PROCESS_H); // 出纳
		queryMap.put("acctBpmState", CommonConstant.PROCESS_I1); // 财务
		queryMap.put("loanBpmState", CommonConstant.PROCESS_J); // 放款
		
		queryMap.put("processName", CommonConstant.AUDIT_PROCESS_NAME); // 流程名称
		
		PageModel pm = new PageModel();
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<IntegratedQueryBean> interList = this.integratedQueryService.queryPageList(queryMap);
		pm.setData(interList);
		pm.initRowsCount(interList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "integrated/integratedList";
	}
	
	@RequestMapping("/read.do")
	public String integratedRead(Integer id,String contractId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
		}
		
		map.put("loanBase", loanBase);
		
		// 影像资料场景类型
		// 个人
		if ("01".equals(loanBase.getLoanType())) {
			map.put("sec","filesce1");
		}
		// 公司
		if ("02".equals(loanBase.getLoanType())) {
			map.put("sec","filesce2");
		}
		
		map.put("contractId", contractId);
		
		Contract contract = this.contractService.queryByContractId(contractId);
		map.put("contract", contract);
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if (0 == loginUser.getIsAdmin()) {
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("loginId", loginUser.getLoginId());
			roleMap.put("orgId", loginOrg.getOrgId());
			List<Role> loginRoles = this.roleService.getRoleByOrgLoginId(roleMap );
			if (loginRoles != null && loginRoles.size() > 0) {
				for (Role role : loginRoles) {
					// 是业务员自己看自己的
					if (CommonConstant.ROLE_SALESMAN.equals(role.getName()) && "1".equals(role.getFlag())) {
						map.put("isSale", "true");
					} 
					if (CommonConstant.ROLE_BRAN_MGR.equals(role.getName()) && "1".equals(role.getFlag())) {
						map.put("isBranch", "true");
					}
				}
			}
		}
		
		log.info(thisMethodName+":end");
		return "integrated/integratedRead";
	}
}
