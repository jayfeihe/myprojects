package com.hikootech.mqcash.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.hikootech.mqcash.constants.ResponseConstants;

public class MQExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("MQExceptionResolver");
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			
			return new ModelAndView("error");
		} else {// JSON格式返回
			try {
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("application/json; charset=utf-8");
			    Map<String, Object> retMap=new HashMap<String, Object>();
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.UNVALID);
				retMap.put(ResponseConstants.RETURN_DESC, ex.getMessage());
				System.out.println(JSONObject.fromObject(retMap).toString());
				response.getWriter().write(JSONObject.fromObject(retMap).toString());
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

}
