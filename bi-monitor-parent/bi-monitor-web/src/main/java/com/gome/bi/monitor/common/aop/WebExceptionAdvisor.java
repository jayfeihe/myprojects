package com.gome.bi.monitor.common.aop;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.gome.bi.monitor.common.constant.CommonConstants;
import com.gome.bi.monitor.common.exception.ServiceException;

/**
 * web层异常切面
 * 
 * @author QYANZE
 *
 */
@Aspect
public class WebExceptionAdvisor {

	private static final Logger logger = LoggerFactory.getLogger(WebExceptionAdvisor.class);

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ||"
			+ "@annotation(org.springframework.web.bind.annotation.GetMapping) ||"
			+ "@annotation(org.springframework.web.bind.annotation.PostMapping) ||"
			+ "@annotation(org.springframework.web.bind.annotation.PutMapping) ||"
			+ "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||"
			+ "@annotation(org.springframework.web.bind.annotation.PatchMapping)")
	public void webPointCut() {}

	@Before("webPointCut()")
	public void handBefore(JoinPoint joinPoint) {
		String classname = joinPoint.getTarget().getClass().getName();
		String methodname = joinPoint.getSignature().getName();
		logger.debug("{}()方法执行开始*****************************", classname + "." + methodname);
		Object[] args = joinPoint.getArgs();
		String ds = StringUtils.collectionToDelimitedString(Arrays.asList(args), "|");
		try {
			logger.debug("请求报文：{}", URLDecoder.decode(ds, "UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@After("webPointCut()")
	public void handAfter(JoinPoint joinPoint) {
		String classname = joinPoint.getTarget().getClass().getName();
		String methodname = joinPoint.getSignature().getName();
		logger.debug("{}()方法执行结束*****************************", classname + "." + methodname);
	}

	/**
	 * Controller 返回页面处理
	 * 
	 * @param obj 返回数据
	 */
	@AfterReturning(pointcut = "webPointCut()", returning = "obj")
	public void handAfterReturn(Object obj) {
		writeData(CommonConstants.SUCCESS_CODE, "", obj);
	}

	/**
	 * controller 异常处理
	 * 
	 * @param joinPoint 切入点
	 * @param e 异常
	 */
	@AfterThrowing(pointcut = "webPointCut()", throwing = "e")
	public void handException(JoinPoint joinPoint, Throwable e) {

		if (!(e instanceof ServiceException)) {
			String classname = joinPoint.getTarget().getClass().getName();
			String methodname = joinPoint.getSignature().getName();
			logger.error("{}()方法发生异常*****************************", classname + "." + methodname);
			logger.error("异常信息：", e);
		}

		writeData(CommonConstants.EXCEPTION_CODE, e.getMessage(), "");
	}

	/**
	 * 回写数据到页面
	 * 
	 * @param code 状态码
	 * @param errMsg 错误信息
	 * @param obj 数据
	 */
	private void writeData(int code, String errMsg, Object obj) {

		JSONObject json = new JSONObject(true);
		json.put("code", code);
		json.put("errmsg", errMsg);
		json.put("data", obj);
		String jstr = json.toJSONString();
		
		logger.debug("响应报文：{}", jstr);
		
		HttpServletResponse response =
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getResponse();
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/json;charset=UTF-8");
		response.setHeader("icop-content-type", "exception");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(jstr);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
	}
}
