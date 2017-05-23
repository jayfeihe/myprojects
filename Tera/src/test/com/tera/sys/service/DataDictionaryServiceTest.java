package com.tera.sys.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

import com.tera.sys.model.DataDictionary;
import com.tera.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class DataDictionaryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * UserDao
	 */
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;

	@Test
	public void dataDictionaryListJson() throws Exception {
		String keyName = "customertype";
		String parentKeyProp = "";
		List<DataDictionary> list = new ArrayList<DataDictionary>();
		//字典名称
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", keyName);
		queryMap.put("parentKeyProp", parentKeyProp);
		list = this.dataDictionaryService.queryList(queryMap);
		List<DataDirt> listD = new ArrayList<DataDirt>();
		for (DataDictionary dataDictionary : list) {
			listD.add(new DataDirt(dataDictionary.getKeyProp(), dataDictionary.getKeyValue()));
		}
		System.out.println(JsonUtil.object2json(listD));
	}

}
