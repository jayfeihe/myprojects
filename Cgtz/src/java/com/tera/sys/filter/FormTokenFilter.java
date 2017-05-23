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
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tera.sys.model.JsonMsg;
import com.tera.util.JsonUtil;
import com.tera.util.StringUtils;


/**
 * @author wy
 *
 */
public class FormTokenFilter implements Filter {

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

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//String uri = RequestUtils.getURLPart(request.getRequestURI());
		String formToken = request.getParameter("formToken");
		String newFormToken = request.getParameter("newFormToken");
		/*
		 *防止重复提交，目前只是实现了系统内单个的控制，整个系统只有一个令牌
		 *扩展：可以map创建一个令牌池放在session中对每一个表单的token进行管理
		 */

		if (null != newFormToken && "".equals(newFormToken)) {
			request.getSession().setAttribute("formToken", newFormToken);

			PrintWriter writer = response.getWriter();
			writer.print("newFormTokenOk");
			writer.flush();
			writer.close();
			return;

		}

		if (null == formToken || "".equals(formToken)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		String sessionFormToken = request.getSession().getAttribute("formToken").toString();

		if (formToken.equals(sessionFormToken)) {
			//新令牌
			String token = StringUtils.createToken();
			request.getSession().setAttribute("formToken", token);
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} else {
			PrintWriter writer = response.getWriter();
			JsonMsg jm=new JsonMsg(false,"repeatSubmit");
			writer.print(JsonUtil.object2json(jm));
			writer.flush();
			writer.close();
			return;

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
