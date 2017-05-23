package com.hikootech.mqcash.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.context.CommonKVContext;
import com.hikootech.mqcash.po.ConfigCreditKv;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.UnipayService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.MemCachedUtils;

/**
 * @author yuwei
 * 2015年10月31日
 * 初始化环境
 */
public class EnvironmentListener implements ServletContextListener {
	
	private static Logger log = LoggerFactory.getLogger(EnvironmentListener.class);
	
	private ConfigKvService configKvService;
	private ConfigCreditKvService configCreditKvService;
	private UnipayService unipayService;

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
			configCreditKvService = context.getBean(ConfigCreditKvService.class);
			unipayService = context.getBean(UnipayService.class);
			initKv();
			MemCachedUtils.init();
			initCreditMemCachedEnvironment();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("初始化环境出错", e);
		}
	}
	
	/**
	 * 初始化征信memcached环境参数
	 */
	private void initCreditMemCachedEnvironment() {
		// TODO Auto-generated method stub
		String creditMemKey = CommonConstants.CONFIG_KV_CREDIT_KEY;//征信配置Map集合在memcached存储的常量key
		Map<String, String> creditMap = MemCachedUtils.get(creditMemKey);
		if(creditMap != null){
			return;
		}
		synchronized (EnvironmentListener.class) {
			creditMap = MemCachedUtils.get(creditMemKey);
			if(creditMap != null){
				return;
			}
			
			creditMap = new HashMap<String, String>();
			
			List<ConfigCreditKv> list = configCreditKvService.queryAll();//从数据库中读取所有征信配置，然后放入Map中
			for(ConfigCreditKv kv : list){
				creditMap.put(kv.getKvKey(), kv.getKvValue());
			}
			
			int expiration = Integer.parseInt(ConfigUtils.getProperty("memcached_expiration"));//读取征信配置在memcache中缓存失效时间
			MemCachedUtils.set(creditMemKey, expiration, creditMap);
		}
	}
	
	public void initKv(){
		if(CommonKVContext.kvCache == null){
			synchronized (CommonKVContext.class) {
				if(CommonKVContext.kvCache != null){
					return;
				}
				
				CommonKVContext.init(configKvService.queryAll());
			}
		}
		
		if(CommonKVContext.unipayCityDic == null){
			synchronized (CommonKVContext.class) {
				if(CommonKVContext.unipayCityDic != null){
					return;
				}
				
				CommonKVContext.initUnipayCity(unipayService.listCityCode());
			}
		}
	}

}
