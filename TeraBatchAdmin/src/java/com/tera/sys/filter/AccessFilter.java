/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.filter;

import java.io.IOException;

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


/**
 * @author Wallace chu
 *
 */
public class AccessFilter implements Filter {
	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AccessFilter.class);

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
		if (request.getSession().getAttribute("loginId") != null ) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} else {
			//用户未登录
			//用户未登录时，可以访问的页面URL，过滤掉
				String path = request.getContextPath();
				response.sendRedirect(path + "/index.do");
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
