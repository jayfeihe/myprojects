package com.tera.sys.listenner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tera.file.config.Configurations;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.ParameterService;

public class InitListener implements ServletContextListener {

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("----------->> InitListener的初始化工作开始 <<------------");
		long l = System.currentTimeMillis();
		long ll = 0;
		// 从Spring的容器中获取对象
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		DataDictionaryService<DataDictionary> sysDictService = (DataDictionaryService<DataDictionary>) ac
				.getBean("dataDictionaryService");

		Map<String, List<DataDictionary>> dictMap = new HashMap<String, List<DataDictionary>>();
		
		// 把所有的词典信息放置到application作用域中
		try {
			System.out.println("----------->> InitListener的数据字典加载   开始<<------------");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			List<String> keys = sysDictService.queryKeyNamesAndParentKeys();
			for (String key : keys) {
				String keyName = key.split(",")[0];
				String parentKey = key.split(",")[1];
				queryMap.clear();
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
			ll = System.currentTimeMillis();
			System.out.println("----------->> InitListener的数据字典加载   完毕 <<------------"+(ll-l));
		} catch (Exception e) {
			e.printStackTrace();
			ll = System.currentTimeMillis();
			System.out.println("----------->> InitListener的数据字典加载   失败 <<------------"+(ll-l));
		}
		
		try {
			ParameterService  ps = (ParameterService) ac.getBean("parameterService");
			Map<String, String> paraMap=new HashMap<String, String>();
			Map<String, Object> map=new HashMap<String, Object>();
			List<Parameter> lsitPara=ps.queryList(map);
			for (Parameter parameter : lsitPara) {
				paraMap.put(parameter.getParamName(), parameter.getParamValue());
			}
			sce.getServletContext().setAttribute("PARAS", paraMap);
			if (paraMap.get("filepath")!=null&&!paraMap.get("filepath").isEmpty()) {
				Configurations.USER_FILE_PATH=paraMap.get("filepath");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			ll = System.currentTimeMillis();
			System.out.println("----------->> InitListener的参数加载   失败 <<------------"+(ll-l));
		}

//		ll = System.currentTimeMillis();
//		try {
//			System.out.println("----------->> InitListener的决策引擎加载   开始<<------------");
//			DroolsJudgeServiceImpl ds = (DroolsJudgeServiceImpl) ac.getBean("droolsJudgeServiceImpl");
//			ds.refreshEnginRule("1");
//			long lll = System.currentTimeMillis();
//			System.out.println("----------->> InitListener的决策引擎加载   完毕 <<------------"+(lll-ll));
//		} catch (Exception e) {
//			e.printStackTrace();
//			long lll = System.currentTimeMillis();
//			System.out.println("----------->> InitListener的决策引擎加载   失败 <<------------"+(lll-ll));
//		}
		ll = System.currentTimeMillis();
		System.out.println("----------->> InitListener的初始化工作完毕 <<------------"+(ll-l));
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
