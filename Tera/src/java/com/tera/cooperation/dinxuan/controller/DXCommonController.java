package com.tera.cooperation.dinxuan.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.cooperation.dinxuan.model.CeilingAmountQBean;
import com.tera.cooperation.dinxuan.service.CeilingAmountService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;

/**
 * 鼎轩Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/cooperation/dx/common")
public class DXCommonController {

	private final static Logger log = Logger.getLogger(DXCommonController.class);
	
	@Autowired(required=false)
	private ParameterService<Parameter> parameterService;
	@Autowired(required=false)
	private CeilingAmountService ceilingAmountService;

	@RequestMapping("/query.do")
	public String ceilingAmountQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "cooperation/dx/common/ceilingAmountQuery";
	}
	
	@RequestMapping("/list.do")
	public String ceilingAmountList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		CeilingAmountQBean bean = ceilingAmountService.countDxAmountOfWeek();
		map.put("bean", bean);
		
		log.info(thisMethodName+":end");
		return "cooperation/dx/common/ceilingAmountList";
	}
	
	@RequestMapping("/update.do")
	public String ceilingAmountUpdate(HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		log.info(thisMethodName + ":start");
		
		map.put("paramId", request.getParameter("paramId"));
		map.put("ceilingAmount", request.getParameter("ceilingAmount"));
		map.put("passAmountOfWeek", request.getParameter("passAmountOfWeek"));
		map.put("leaveAmountOfWeek", request.getParameter("leaveAmountOfWeek"));
		map.put("signAmountOfWeek", request.getParameter("signAmountOfWeek"));
		map.put("leaveSignAmountOfWeek", request.getParameter("leaveSignAmountOfWeek"));
		
		log.info(thisMethodName + ":end");
		return "cooperation/dx/common/ceilingAmountUpdate";
	}
	
	@RequestMapping("/save.do")
	public void ceilingAmountSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Parameter dxParam = (Parameter) RequestUtils.getRequestBeanList(
					"parameter", Parameter.class, request).get(0);
			parameterService.updateOnlyChanged(dxParam);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
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
