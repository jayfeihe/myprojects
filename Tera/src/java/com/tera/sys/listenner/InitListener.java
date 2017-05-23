package com.tera.sys.listenner;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tera.sys.model.DataDictionary;
import com.tera.sys.service.DataDictionaryService;

public class InitListener implements ServletContextListener {

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent sce) {
		// 从Spring的容器中获取对象
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		DataDictionaryService<DataDictionary> sysDictService = (DataDictionaryService<DataDictionary>) ac
				.getBean("dataDictionaryService");

		Map<String, List<DataDictionary>> dictMap = new HashMap<String, List<DataDictionary>>();
		
		// 把所有的词典信息放置到application作用域中
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			List<String> keys = sysDictService.queryKeyNamesAndParentKeys();
			for (String key : keys) {
				String keyName = key.split(",")[0];
				String parentKey = key.split(",")[1];
				if ("*".equals(parentKey)) {
					queryMap.put("keyName", keyName);
					List<DataDictionary> dicts = sysDictService.queryList(queryMap);
					dictMap.put(keyName.toLowerCase(), dicts);
				} else {
					queryMap.put("keyName", keyName);
					queryMap.put("parentKeyProp", parentKey);
					List<DataDictionary> dicts = sysDictService.queryList(queryMap);
					dictMap.put(key.toLowerCase(), dicts);
				}
			}
			sce.getServletContext().setAttribute("DATADICTS", dictMap);
			System.out.println("----------->> InitListener的初始化工作完毕 <<------------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("----------->> InitListener的初始化工作失败 <<------------");
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
