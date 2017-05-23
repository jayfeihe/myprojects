/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;


/**
 * @author Administrator create 2012-7-10
 */
public class Log4jInitServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * （非 Javadoc）
	 * @see javax.servlet.GenericServlet#init()
	 *      在Weblogic上部署war中org.springframework.web.util.Log4jConfigListener出现的问题
	 *      Error: weblogic.management.DeploymentException: Cannot set web app
	 *      root system property when WAR file is not expanded - with nested
	 *      exception: [java.lang.IllegalStateException: Cannot set web app root
	 *      system property when WAR file is not expanded]
	 */
	@Override
	public void init() throws ServletException {
		InputStream instream = getServletContext().getResourceAsStream("/WEB-INF/classes/log4j.properties");
		Properties props = new Properties();
		try {
			props.load(instream);
		} catch (IOException e) {
			System.err.println("Load log4j configuration failed");
		}
		PropertyConfigurator.configure(props);
	}

}
