/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.payment.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Menu;
import com.tera.sys.model.User;


/**
 * @author Wallace chu
 *
 */
public class PaymentFilter implements Filter {
	/**
	 * 日志
	 */
	@SuppressWarnings("unchecked")
	private static Log log = LogFactory.getLog(PaymentFilter.class);

	/** (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/** (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 * @param servletRequest servletRequest
	 * @param servletResponse servletResponse
	 * @param filterChain filterChain
	 * @throws IOException IOException
	 * @throws ServletException ServletException
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		//请求的URI
		String uri = request.getRequestURI();
		//Referer从哪个页面链接过来的
		String referer = request.getHeader("Referer");
		log.info("------------------IN AccessFilter------------------");
		log.info("AccessFilter-getRequestURI:" + request.getRequestURI());
		log.info("AccessFilter-referer:" + referer);
		//用户已经登录
		if (request.getSession().getAttribute("loginCustomer") != null) {
			
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} else {
			//用户未登录
			//用户未登录时，可以访问的页面URL，过滤掉
			List<String> exceptionURLs = new ArrayList<String>();
			exceptionURLs.add("login.htm");
			exceptionURLs.add("paydisplay.htm");
			exceptionURLs.add("payment/read.htm");
			//是否包含未登录也可访问的URL
			boolean hasRight = false;
			for (String exceptionURL : exceptionURLs) {
				if (uri.indexOf(exceptionURL) > -1) {
					hasRight = true;
				}
			}
			if (hasRight) {
				log.info("AccessFilter：用户未登录时，访问[" + uri + "]页面！");
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			} else {
				//用户未登录时，访问其他页面时，强制登录
				log.info("AccessFilter：用户未登录访问[" + uri + "]强制跳转到登录页面！");
				String path = request.getContextPath();
				response.sendRedirect(path + "/paycenter/login.htm");
			}
		}
	}

	/** (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 * @param arg0 arg0
	 * @throws ServletException ServletException
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
