package com.tera.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 通过此service，实现从Spring容器中，获取bean
 * 
 * @author wallace
 *
 */
@Service
public class SpringServiceUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringServiceUtils.applicationContext = context;
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

}
