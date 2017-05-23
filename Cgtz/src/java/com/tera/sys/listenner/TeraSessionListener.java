/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.listenner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.dao.SysLogDao;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
/**
 * session listener
 * @author wy
 *
 */
public class TeraSessionListener implements HttpSessionListener {

	/***/
	//@Autowired
	//private SysLogService sysLogService;
	/**
	 * @param event event
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {

	}
	/**
	 * session 超时记录用户退出到数据库
	 * @param event event
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        User user = (User) session.getAttribute(SysConstants.LOGIN_USER);
        if(user!=null){
        	String ipAddress = (String) session.getAttribute(SysConstants.LOGIN_IP);
        	WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(application);
        	SysLogDao sysLogDao = (SysLogDao) webApplicationContext.getBean("sysLogDao");
        	System.out.println(user.getName() + user.getLoginId() + ipAddress + "===========");
        	sysLogDao.addSysLog(new SysLog(ipAddress, user, "退出"));
        }
	}

}
