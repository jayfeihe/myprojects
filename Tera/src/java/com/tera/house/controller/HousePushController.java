package com.tera.house.controller;

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
import com.tera.house.constant.Constants;
import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseDecision;
import com.tera.house.model.form.HouseQBean;
import com.tera.house.service.HouseAppService;
import com.tera.house.service.HouseDecisionService;
import com.tera.house.service.HousePushService;
import com.tera.house.service.HouseSignService;
import com.tera.contract.model.Contract;
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
@RequestMapping("/house/push")
public class HousePushController {

	private static final Logger log = Logger.getLogger(HousePushController.class);
	@Autowired(required=false)
	private HousePushService housePushService;
	@Autowired(required=false)
	private HouseAppService houseAppService;
	@Autowired(required=false)
	private HouseDecisionService houseDecisionService;
	@Autowired(required=false) //自动注入
	private HouseSignService houseSignService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	
	@RequestMapping("/query.do")
	public String housePushQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "house/push/housePushQuery";
	}
	
	@RequestMapping("/list.do")
	public String housePushList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		HouseQBean formBean = (HouseQBean) RequestUtils.getRequestBean(HouseQBean.class, request);
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
		
		PageList<HouseApp> pushList = this.housePushService.queryPageList(beanMap);
		pm.setData(pushList);
		pm.initRowsCount(pushList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("stateType",formBean.getStateTask());
		
		log.info(thisMethodName+":end");
		return "house/push/housePushList";
	}
	
	@RequestMapping("/update.do")
	public String housePushUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		HouseApp appBean = houseAppService.queryByKey(id);
		map.put("bean", appBean);
		
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("appId", appBean.getAppId());
		queryMap.put("state", "1");
		queryMap.put("type", "0");
		List<HouseDecision> houseDecisionList = houseDecisionService.queryList(queryMap);
		
		if (houseDecisionList != null && houseDecisionList.size() > 0) {
			Product pro = productService.queryByName(houseDecisionList.get(0).getProduct());
			double loanAmount = houseSignService.getHtJkje(houseDecisionList.get(0), pro);
			
			map.put("decision", houseDecisionList.get(0));
			map.put("actualAmount", MathUtils.round(loanAmount, 2));
		}
		
		log.info(thisMethodName+":end");
		return "house/push/housePushUpdate";
	}
	
	@RequestMapping("/save.do")
	public void housePushSave(Contract contract, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			JsonMsg jsonMsg = this.housePushService.pushManage(contract,loginId,org);
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
