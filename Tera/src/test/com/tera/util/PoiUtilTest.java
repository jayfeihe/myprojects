package com.tera.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;


public class PoiUtilTest {
	
	private static Log log = LogFactory.getLog(PoiUtilTest.class);

	@Test
	public void getTemplateInstTest() {
		log.info(11);
		try {
					List<List<String>> list = new ArrayList<List<String>>();
					List<String> list1 = new ArrayList <String>();
					list1.add("张三");
					list1.add("010221");
					list1.add("21212");
					list1.add("北京");
					list.add(list1);
					
					
					List<String> list2 = new ArrayList <String>();
					list2.add("李四");
					list2.add("010221");
					list2.add("21212");
					list2.add("北京");
					list.add(list2);
					
					List<String> list3 = new ArrayList <String>();
					list3.add("王5");
					list3.add("010221");
					list3.add("21212");
					list3.add("澳门");
					list.add(list3);
					
					List<String> list4 = new ArrayList <String>();
					list4.add("王5");
					list4.add("010221");
					list4.add("21212");
					list4.add("澳门");
					list.add(list4);
					PoiUtil test = new PoiUtil();
					test.setClassPathDocument("config/contract/贷款合同_v20140701.docx");
					test.setTablesValue(list, 0);

					test.setTablesValue(list, 1);
					//替换文字
					Map<String, String> mapValue = new HashMap<String, String>();
					mapValue.put("lendAmount", "2123123");
					test.replaceTextToText(mapValue);
					IOUtils.write(new File("xml/合同样例.docx"), test.getDocx());
			log.debug("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void getTemplateInstTest1() {
		log.info(11);
		try {
			PoiUtil test = new PoiUtil();
			test.setClassPathDocument("config/contract/房屋抵押担保合同_v20140701.docx");
			// 替换文字
			Map<String, String> mapValue = new HashMap<String, String>();
			mapValue.put("contractno", "测试");
			test.replaceTextToText(mapValue);
			IOUtils.write(new File("xml/合同样例1.docx"), test.getDocx());
			log.debug("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}