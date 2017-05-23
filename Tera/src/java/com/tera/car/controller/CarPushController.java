package com.tera.car.controller;

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
import com.tera.car.constant.Constants;
import com.tera.car.model.CarApp;
import com.tera.car.model.CarDecision;
import com.tera.car.model.form.CarQBean;
import com.tera.car.service.CarAppService;
import com.tera.car.service.CarDecisionService;
import com.tera.car.service.CarPushService;
import com.tera.car.service.CarSignService;
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
@RequestMapping("/car/push")
public class CarPushController {

	private static final Logger log = Logger.getLogger(CarPushController.class);
	@Autowired(required=false)
	private CarPushService carPushService;
	@Autowired(required=false)
	private CarAppService carAppService;
	@Autowired(required=false)
	private CarDecisionService carDecisionService;
	@Autowired(required=false) //自动注入
	private CarSignService carSignService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	
	@RequestMapping("/query.do")
	public String carPushQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "car/push/carPushQuery";
	}
	
	@RequestMapping("/list.do")
	public String carPushList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		CarQBean formBean = (CarQBean) RequestUtils.getRequestBean(CarQBean.class, request);
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
		
		PageList<CarApp> pushList = this.carPushService.queryPageList(beanMap);
		pm.setData(pushList);
		pm.initRowsCount(pushList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("stateType",formBean.getStateTask());
		
		log.info(thisMethodName+":end");
		return "car/push/carPushList";
	}
	
	@RequestMapping("/update.do")
	public String carPushUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		CarApp appBean = carAppService.queryByKey(id);
		map.put("bean", appBean);
		
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("appId", appBean.getAppId());
		queryMap.put("state", "1");
		queryMap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(queryMap);
		
		if (carDecisionList != null && carDecisionList.size() > 0) {
			Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
			double loanAmount = carSignService.getHtJkje(carDecisionList.get(0), pro);
			
			map.put("decision", carDecisionList.get(0));
			map.put("actualAmount", MathUtils.round(loanAmount, 2));
		}
		
		log.info(thisMethodName+":end");
		return "car/push/carPushUpdate";
	}
	
	@RequestMapping("/save.do")
	public void carPushSave(Contract contract, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			JsonMsg jsonMsg = this.carPushService.pushManage(contract,loginId,org);
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
