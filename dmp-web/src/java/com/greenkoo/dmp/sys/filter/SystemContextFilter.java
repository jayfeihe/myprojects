package com.greenkoo.dmp.sys.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.greenkoo.dmp.sys.model.SystemContext;
import com.greenkoo.dmp.utils.StringUtil;


public class SystemContextFilter implements Filter {
	private Integer pageSize;

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			String pageOffset = req.getParameter("pager.offset");
			if (StringUtil.isEmpty(pageOffset)) {
				SystemContext.setPageOffset(0);
			} else {
				SystemContext.setPageOffset(Integer.valueOf(pageOffset));
			}
			
			SystemContext.setPageSize(this.pageSize);
			String _pageSize = req.getParameter("pageSize");
			if (StringUtil.isNotEmpty(_pageSize)) {
				SystemContext.setPageSize(Integer.valueOf(_pageSize));
			}
			
			SystemContext.setRealPath(((HttpServletRequest) req).getSession().getServletContext().getRealPath("/"));
			chain.doFilter(req, resp);
		} finally {
			SystemContext.removePageSize();
			SystemContext.removeRealPath();
			SystemContext.removePageOffset();
		}
	}

	public void init(FilterConfig cfg) throws ServletException {
		try {
			this.pageSize = Integer.valueOf(Integer.parseInt(cfg.getInitParameter("pageSize")));
		} catch (NumberFormatException e) {
			this.pageSize = Integer.valueOf(1);
		}
	}
}