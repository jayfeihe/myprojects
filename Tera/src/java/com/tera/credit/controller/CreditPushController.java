package com.tera.credit.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.contract.model.Contract;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditPushService;
import com.tera.credit.service.CreditSignService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 信审推送Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/credit/push")
public class CreditPushController {

	private static final Logger log = Logger.getLogger(CreditPushController.class);
	@Autowired(required=false)
	private CreditPushService creditPushService;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	@Autowired(required=false)
	private CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	private CreditSignService creditSignService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	
	@RequestMapping("/query.do")
	public String creditPushQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "credit/push/creditPushQuery";
	}
	
	@RequestMapping("/list.do")
	public String creditPushList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		CreditQBean formBean = (CreditQBean) RequestUtils.getRequestBean(CreditQBean.class, request);
		Map<String, Object> beanMap = ObjectUtils.describe(formBean);
		
//		beanMap.put("nonStates", new String[]{"0"}); 
		
		// 管理员查看全部
		if (user.getIsAdmin() == 0) 
			beanMap.put("processer", user.getLoginId());
		
		// 待处理
		if("0".equals(formBean.getStateTask()))
			beanMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_PUSH});
		
		// 已完成
		if("1".equals(formBean.getStateTask()))
			beanMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});
		
		PageModel pm = new PageModel();
		pm.init(request, null, formBean);
		beanMap.put("curPage", pm.getCurPage());
		beanMap.put("pageSize", pm.getPageSize());
		
		PageList<CreditApp> pushList = this.creditPushService.queryPageList(beanMap);
		pm.setData(pushList);
		pm.initRowsCount(pushList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("stateType",formBean.getStateTask());
		
		log.info(thisMethodName+":end");
		return "credit/push/creditPushList";
	}
	
	@RequestMapping("/update.do")
	public String creditPushUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		CreditApp appBean = creditAppService.queryByKey(id);
		map.put("bean", appBean);
		
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("appId", appBean.getAppId());
		queryMap.put("state", "1");
		queryMap.put("type", "0");
		List<CreditDecision> creditDecisionList = creditDecisionService.queryList(queryMap);
		
		if (creditDecisionList != null && creditDecisionList.size() > 0) {
			Product pro = productService.queryByName(creditDecisionList.get(0).getProduct());
			double loanAmount = creditSignService.getHtJkje(creditDecisionList.get(0), pro);
			
			map.put("decision", creditDecisionList.get(0));
			map.put("actualAmount", MathUtils.round(loanAmount, 2));
		}
		
		log.info(thisMethodName+":end");
		return "credit/push/creditPushUpdate";
	}
	
	@RequestMapping("/save.do")
	public void creditPushSave(Contract contract, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			JsonMsg jsonMsg = this.creditPushService.pushManage(contract,loginId,org);
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
