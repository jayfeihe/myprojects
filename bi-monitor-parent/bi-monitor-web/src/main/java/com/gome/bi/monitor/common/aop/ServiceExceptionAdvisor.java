package com.gome.bi.monitor.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.bi.monitor.common.exception.ServiceException;

/**
 * Service异常切面
 * 
 * @author QYANZE
 *
 */
@Aspect
public class ServiceExceptionAdvisor {
	private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionAdvisor.class); 
	
	@Pointcut("@within(org.springframework.stereotype.Service)")
	public void servicePointCut(){}
	
	/**
	 * service 异常处理，直接抛到上层处理
	 * 
	 * @param joinPoint
	 * @param e 异常
	 */
	@AfterThrowing(pointcut="servicePointCut()", throwing="e")
	public void handException(JoinPoint joinPoint, Throwable e) {
		String classname = joinPoint.getTarget().getClass().getName();
		String methodname = joinPoint.getSignature().getName();
		logger.error("{}()方法发生异常*****************************", classname + "." + methodname);
		logger.error("异常信息：", e);
		throw new ServiceException(e);
	}
}
