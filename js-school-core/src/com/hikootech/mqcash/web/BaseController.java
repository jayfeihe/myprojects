package com.hikootech.mqcash.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 2014年1月16日
 * com.jiexun.pos.webBaseController.java
 * @author yuwei
 * 基控制器
 */
public class BaseController {
	
	@Autowired
	private HttpServletRequest request;
	
	public BaseController() {
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
}
