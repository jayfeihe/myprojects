package com.hikootech.mqcash.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hikootech.mqcash.context.CommonKVContext;
import com.hikootech.mqcash.service.ConfigKvService;

/**
 * @author yuwei
 * 2015年10月31日
 * 初始化环境
 */
public class EnvironmentListener implements ServletContextListener {
	
	private static Logger log = LoggerFactory.getLogger(EnvironmentListener.class);
	
	private ConfigKvService configKvService;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		configKvService = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		log.debug("初始化环境");
		try {
			
			ServletContext servletContext = arg0.getServletContext();
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			configKvService = context.getBean(ConfigKvService.class);
			
			initKv();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("初始化环境出错", e);
		}
	}
	
	public void initKv(){
		if(CommonKVContext.kvCache != null){
			return;
		}
		
		synchronized (CommonKVContext.class) {
			if(CommonKVContext.kvCache != null){
				return;
			}
			
			CommonKVContext.init(configKvService.queryAll());
		}
	}

}
