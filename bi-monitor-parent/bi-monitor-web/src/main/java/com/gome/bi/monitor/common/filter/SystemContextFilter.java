package com.gome.bi.monitor.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import com.gome.bi.monitor.common.model.SystemContext;


public class SystemContextFilter implements Filter {
	private Integer pageSize;

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			String curPage = req.getParameter("curPage");
			if (StringUtils.isEmpty(curPage)) {
				SystemContext.setCurPage(1);
			} else {
				SystemContext.setCurPage(Integer.valueOf(curPage));
			}
			
			String _pageSize = req.getParameter("pageSize");
			
			SystemContext.setPageSize(this.pageSize);
			
			if (StringUtils.isNotEmpty(_pageSize)) {
				SystemContext.setPageSize(Integer.valueOf(_pageSize));
			}
			chain.doFilter(req, resp);
		} finally {
			SystemContext.removePageSize();
			SystemContext.removeCurPage();
		}
	}

	public void init(FilterConfig cfg) throws ServletException {
		try {
			this.pageSize = Integer.valueOf(Integer.parseInt(cfg.getInitParameter("pageSize")));
		} catch (NumberFormatException e) {
			this.pageSize = Integer.valueOf(15);
		}
	}
}