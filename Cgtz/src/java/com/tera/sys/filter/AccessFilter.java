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

import com.tera.core.Global;
import com.tera.core.WebConstants;
import com.tera.interfaces.model.SessionJson;
import com.tera.interfaces.util.GsonUtils;
import com.tera.sys.constant.SysConstants;
import com.tera.util.ServletUtils;


/**
 * @author Wallace chu
 *
 */
public class AccessFilter implements Filter {
	/**
	 * 日志
	 */
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
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		
		 boolean ajax = ServletUtils.isAjax(request);
	      if(!ajax){
	        request.setAttribute("g", new Global(ServletUtils.getDomain(request)));
	      }
	
	      /* 设置domain */
	      WebConstants.setDomain(ServletUtils.getDomain(request));
	      
		//请求的URI
		String uri = request.getRequestURI();
		//Referer从哪个页面链接过来的
		String referer = request.getHeader("Referer");
		log.info("------------------IN AccessFilter------------------");
		log.info("AccessFilter-getRequestURI:" + request.getRequestURI());
		log.info("AccessFilter-referer:" + referer);
		
		//用户已经登录
		if (request.getSession().getAttribute(SysConstants.LOGIN_ID) != null ) {
//			List<Menu> allMenus = (List<Menu>) request.getSession().getAttribute(SysConstants.LOGIN_MENUS);
//			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
//			String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			//非管理员越权过滤
//			if (user.getIsAdmin() != 1 && null != allMenus) {
//				log.info("AccessFilter：用户" + loginid + "已登录时，访问[" + uri + "]页面！");
//				//判读是否有权限
//				boolean hasRight = false;
//				for (Menu menu : allMenus) {
//					if (null != menu.getUrl() && uri.indexOf(menu.getUrl()) > -1) {
//						hasRight = true;
//					}
//				}
//				//登陆后无权限也可以操作的链接
//				List<String> exceptionURLs = new ArrayList<String>();
//				exceptionURLs.add("frame.do");
//				exceptionURLs.add("top.do");
//				exceptionURLs.add("left.do");
//				exceptionURLs.add("navigation.do");
//				exceptionURLs.add("main.do");
//				exceptionURLs.add("message.do");
//				exceptionURLs.add("logout.do");
//				exceptionURLs.add("sys/user/updatemyself.do");
//				for (String exceptionURL : exceptionURLs) {
//					if (uri.indexOf(exceptionURL) > -1) {
//						hasRight = true;
//					}
//				}
//				//无权限提示
//				if (!hasRight) {
//					String path = request.getContextPath();
//					response.sendRedirect(path + "/message.do?message=" + URLEncoder.encode("操作未授权，请联系系统管理员!", "UTF-8"));
//					return;
//				}
//			}
			
			if (uri.indexOf("index.do") == -1) {
				filterChain.doFilter(servletRequest, servletResponse);
			}else{
				String path = request.getContextPath();
				response.sendRedirect(path + "/frame.do");
			}
			return;
		} else {
			//用户未登录
			//用户未登录时，可以访问的页面URL，过滤掉
			List<String> exceptionURLs = new ArrayList<String>();
			exceptionURLs.add("index.do");
			exceptionURLs.add("login.do");
			exceptionURLs.add("randomImg.do");
			exceptionURLs.add("sys/org/userOrg.do");
			
			// 接口url
			exceptionURLs.add("inter/app/login.do");
			
			exceptionURLs.add("inter/loanBase/list.do");
			exceptionURLs.add("inter/loanBase/update.do");
			exceptionURLs.add("inter/loanBase/save.do");
			
			exceptionURLs.add("inter/collateral/list.do");
			exceptionURLs.add("inter/collateral/update.do");
			exceptionURLs.add("inter/collateral/save.do");
			
			exceptionURLs.add("inter/loanGuar/list.do");
			exceptionURLs.add("inter/loanGuar/update.do");
			exceptionURLs.add("inter/loanGuar/save.do");
			
			exceptionURLs.add("inter/loanLaw/list.do");
			exceptionURLs.add("inter/loanLaw/update.do");
			exceptionURLs.add("inter/loanLaw/save.do");
			
			exceptionURLs.add("inter/loanCommon/list.do");
			exceptionURLs.add("inter/loanCommon/update.do");
			exceptionURLs.add("inter/loanCommon/save.do");
			
			exceptionURLs.add("inter/branch/list.do");
			exceptionURLs.add("inter/branch/update.do");
			exceptionURLs.add("inter/branch/save.do");
			
			exceptionURLs.add("inter/riskFirst/list.do");
			exceptionURLs.add("inter/riskFirst/update.do");
			exceptionURLs.add("inter/riskFirst/save.do");
			
			exceptionURLs.add("inter/judgeFirst/list.do");
			exceptionURLs.add("inter/judgeFirst/update.do");
			exceptionURLs.add("inter/judgeFirst/save.do");
			
			exceptionURLs.add("inter/judgeReview/list.do");
			exceptionURLs.add("inter/judgeReview/update.do");
			exceptionURLs.add("inter/judgeReview/save.do");
			
			exceptionURLs.add("inter/judgeAdvice/list.do");
			exceptionURLs.add("inter/judgeAdvice/update.do");
			exceptionURLs.add("inter/judgeAdvice/save.do");
			
			exceptionURLs.add("inter/riskReview/list.do");
			exceptionURLs.add("inter/riskReview/update.do");
			exceptionURLs.add("inter/riskReview/save.do");
			
			exceptionURLs.add("inter/dict.do");
			exceptionURLs.add("inter/branchBank.do");
			exceptionURLs.add("inter/warehouse.do");
			
			exceptionURLs.add("inter/online/sendProjectInfo.do");
			exceptionURLs.add("inter/getBpmLog.do");
			
			exceptionURLs.add("files/imgupload.do");
			exceptionURLs.add("files/getCategories.do");
			exceptionURLs.add("files/getUrl.do");
			exceptionURLs.add("files/imgRead.do");
			exceptionURLs.add("files/getfile.do");
			
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
				// 手机端返回session失效信息（手机端请求时需要先addHeader("Client-Type","APP")）
				String ctype = request.getHeader("Client-Type");
				log.info("AccessFilter-Client-Type:" + ctype);
				if ("APP".equals(ctype)) {
					response.getWriter().print(GsonUtils.getInstance().toJson(new SessionJson("0", "session invalidate")));
					return;
				}
				
				//用户未登录时，访问其他页面时，强制登录
				log.info("AccessFilter：用户未登录访问[" + uri + "]强制跳转到登录页面！");
				String path = request.getContextPath();
				response.sendRedirect(path + "/index.do");
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
