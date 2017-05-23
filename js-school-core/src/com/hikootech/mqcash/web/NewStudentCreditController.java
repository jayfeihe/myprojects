package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.service.NewStudentCreditService;
import com.hikootech.mqcash.util.EntityUtils;

@RequestMapping("/newstudent")
@Controller
public class NewStudentCreditController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(NewStudentCreditController.class);
	
	@Autowired
	private NewStudentCreditService newStudentCreditService;
	
	@RequestMapping("/credit.do")
	@ResponseBody
	public void credit(HttpServletResponse reponse){
		logger.info("江苏校园新生征信接口--开始");
		
		Map<String, String> retBusMap = new HashMap<String, String>();
		//拦截器中返回给调用接口处理
		getRequest().setAttribute("retBusMap", retBusMap);
		//获取解密后的业务参数
		Map<String, String> busMap = getRequest().getAttribute("busMap") == null ? null : (Map<String, String>) getRequest().getAttribute("busMap");
		
		if(busMap == null || busMap.size() == 0){
			logger.error("内部接口调用业务参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "内部接口调用业务参数为空！");
			return ;
		}
		
		Map<String, String> validateMap = null;
		try {
			validateMap = newStudentCreditService.validateCreditParameters(busMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("校验校园学生征信参数出错", e);
		}
		
		if(EntityUtils.isEmpty(validateMap)){
			logger.error("校验校园学生征信参数异常");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.EXCEPTION);
			retBusMap.put(ResponseConstants.RETURN_DESC, "校验校园学生征信参数异常");
			return;
		}else if(!StatusConstants.PARTNER_CALL_SUCCESS.equals(validateMap.get(ResponseConstants.RETURN_CODE))){
			logger.info("校验校园学生征信参数失败");
			retBusMap.putAll(validateMap);
			return;
		}else{
			logger.info("校验校园学生征信参数通过");
		}
		
		Map<String, String> resMap = null;
		try {
			resMap = newStudentCreditService.credit(busMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("校园学生征信出错", e);
		}
		
		if(EntityUtils.isEmpty(resMap)){
			logger.error("校园学生征信异常");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.EXCEPTION);
			retBusMap.put(ResponseConstants.RETURN_DESC, "校园学生征信异常");
			return;
		}else if(!StatusConstants.PARTNER_CALL_SUCCESS.equals(validateMap.get(ResponseConstants.RETURN_CODE))){
			logger.error("校验校园学生征信参数失败");
			retBusMap.putAll(resMap);
		}else{
			logger.info("校验校园学生征信参数通过");
			retBusMap.putAll(resMap);
		}
		
	}
	
	
}
