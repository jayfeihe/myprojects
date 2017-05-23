/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Wallace Chu
 */
public class XMLHandler {

	/**
	 * xml 编码格式
	 */
	private static final String ENCODING = "GBK";

	/**
	 * SAXReader
	 */
	private SAXReader saxReader;

	/**
	 * Document
	 */
	private Document document;

	/**
	 * 根节点
	 */
	private Element root;

	/**
	 * 记录取null的标签列表
	 */
	private List<String> nullValues = new ArrayList<String>();

	/**
	 * 构造函数
	 * 直接使用saxReader.read(File file); 出现 The encoding "GBK" is not supported
	 * @param inputXml xml文件
	 */
	public XMLHandler(File inputXml) {
//		saxReader = new SAXReader();
//		try {
//			if(inputXml != null) {
//				document = saxReader.read(inputXml);
//				root = document.getRootElement();
//			}
//		} catch (Exception e) {
//			throw new XMLHandlerException("can't parse xml, please check the xml format", e);
//		}
		String xml = file2String(inputXml);
		try {
			if (inputXml != null) {
				document = DocumentHelper.parseText(xml);
				root = document.getRootElement();
			}
		} catch (Exception e) {
			throw new RuntimeException("can't parse xml, please check the xml format", e);
		}
	}

	/**
	 * 构造函数
	 * @param inputXml xml文件
	 * @param encoding xml 编码
	 */
	public XMLHandler(File inputXml, String encoding) {
		saxReader = new SAXReader();
		try {
			if (inputXml != null) {
				saxReader.setEncoding(encoding);
				document = saxReader.read(inputXml);
				root = document.getRootElement();
			}
		} catch (Exception e) {
			throw new RuntimeException("can't parse xml, please check the xml format", e);
		}

	}

	/**
	 * 构造函数
	 * 直接使用saxReader.read(String str);会出现no protocol:的异常
	 * 使用DocumentHelper.parseText(String str);解决这一问题
	 * @param inputXml xml字符串
	 */
	public XMLHandler(String inputXml) {
		saxReader = new SAXReader();
		try {
			if (inputXml != null) {
				document = DocumentHelper.parseText(inputXml);
				root = document.getRootElement();
			}
		} catch (Exception e) {
			throw new RuntimeException("can't parse xml, please check the xml format", e);
		}
	}

	/**
	 * 将xml的document对象转换成xml字符串
	 * @return xml字符串
	 */
	public String doc2Xml() {
		StringWriter writer = new StringWriter();
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(ENCODING);
			XMLWriter output = new XMLWriter(writer, format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * 将xml的document对象转换成xml字符串
	 * @param encoding 字符串编码
	 * @return xml字符串
	 */
	public String doc2Xml(String encoding) {
		StringWriter writer = new StringWriter();
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(encoding);
			XMLWriter output = new XMLWriter(writer, format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * 获取root节点
	 * @return root节点
	 */
	public Element getRootElement() {
		return root;
	}

	/**
	 * 获取单个xpath路径的Text值
	 * "//article/date"
	 * @param xpath xpath路径
	 * @return Text值
	 */
	@SuppressWarnings("unchecked")
	public String getElementText(String xpath) {
		List list = document.selectNodes(xpath);
		Iterator iter = list.iterator();
		String value = null;
		if (iter.hasNext()) {
			Element element = (Element) iter.next();
			value = element.getText().trim();
		}

		if (value == null) {
			if (!nullValues.contains(xpath)) {
				nullValues.add(xpath);
			}
			value = "";
		}
		return value;
	}

	/**
	 * 设置单个xpath路径的Text值
	 * "//article/date"
	 * "//article/date/book[n]" 取出第N个元素
	 * @param xpath xpath路径
	 * @param value Text值
	 */
	@SuppressWarnings("unchecked")
	public void setElementText(String xpath, String value) {
		List list = document.selectNodes(xpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			Element element = (Element) iter.next();
			element.setText(value);
		}
	}

	/**
	 * 获取单个xpath路径的Attribute值
	 * "//article/@date"
	 * @param xpath xpath路径
	 * @return Attribute值
	 */
	@SuppressWarnings("unchecked")
	public String getElementAttribute(String xpath) {
		List list = document.selectNodes(xpath);
		Iterator iter = list.iterator();
		String value = null;
		if (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			value = attribute.getValue().trim();
		}
		if (value == null) {
			if (!nullValues.contains(xpath)) {
				nullValues.add(xpath);
			}
			value = "";
		}
		return value;
	}

	/**
	 * 设置单个xpath路径的Attribute值
	 * "//article/@date"
	 * "//article/date/book[n]" 取出第N个元素
	 * @param xpath xpath路径
	 * @param value Attribute值
	 */
	@SuppressWarnings("unchecked")
	public void setElementAttribute(String xpath, String value) {
		List list = document.selectNodes(xpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			attribute.setValue(value);
		}
	}

	/**
	 * 获取xpath下的Element列表
	 * @param xpath xpath路径
	 * @return Element列表
	 */
	@SuppressWarnings("unchecked")
	public List getListElement(String xpath) {
		return document.selectNodes(xpath);
	}

	/**
	 * 获取元素Element的xpath下的Element列表
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @return Element列表
	 */
	@SuppressWarnings("unchecked")
	public List getListElement(Element element, String xpath) {
		return element.selectNodes(xpath);
	}

	/**
	 * 获取xpath下的Element的Attribute值列表
	 * @param xpath xpath路径
	 * @return Attribute值列表
	 */
	@SuppressWarnings("unchecked")
	public List<String> getListElementAttribute(String xpath) {
		List<String> value = new ArrayList<String>();
		List list = document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			value.add(attribute.getValue().trim());
		}
		return value;
	}

	/**
	 *  获取元素Element的xpath下的Element的Attribute值列表
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @return Attribute值列表
	 */
	@SuppressWarnings("unchecked")
	public List<String> getListElementAttribute(Element element, String xpath) {
		List<String> value = new ArrayList<String>();
		List list = element.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			value.add(attribute.getValue().trim());
		}
		return value;
	}

	/**
	 * 获取xpath下的Element的Text值列表
	 * @param xpath xpath路径
	 * @return Text值列表
	 */
	@SuppressWarnings("unchecked")
	public List<String> getListElementText(String xpath) {
		List<String> value = new ArrayList<String>();
		List list = document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element ele = (Element) iter.next();
			value.add(ele.getText().trim());
		}
		return value;
	}

	/**
	 *  获取元素Element的xpath下的Element的Text值列表
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @return Text值列表
	 */
	@SuppressWarnings("unchecked")
	public List<String> getListElementText(Element element, String xpath) {
		List<String> value = new ArrayList<String>();
		List list = element.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element ele = (Element) iter.next();
			value.add(ele.getText().trim());
		}
		return value;
	}

	/**
	 * 获取xpath下的单个Element
	 * @param xpath xpath路径
	 * @return 单个Element
	 */
	@SuppressWarnings("unchecked")
	public Element getElement(String xpath) {
		List list = document.selectNodes(xpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			return (Element) iter.next();
		}
		return null;
	}

	/**
	 * 获取元素Element的xpath下的单个Element
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @return 单个Element
	 */
	@SuppressWarnings("unchecked")
	public Element getElement(Element element, String xpath) {
		List list = element.selectNodes(xpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			return (Element) iter.next();
		}
		return null;
	}

	/**
	 *  获取元素Element的xpath下的Element的Attribute值
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @return Attribute值
	 */
	@SuppressWarnings("unchecked")
	public String getElementAttribute(Element element, String xpath) {
		List list = element.selectNodes(xpath);
		Iterator iter = list.iterator();
		String value = null;
		if (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			value = attribute.getValue().trim();
		}
		if (value == null) {
			String path = element.getUniquePath() + "/" + xpath;
			if (!nullValues.contains(path)) {
				nullValues.add(path);
			}
			value = "";
		}
		return value;
	}

	/**
	 * 设置元素Element的xpath下的Element的Attribute值
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @param value Attribute值
	 */
	@SuppressWarnings("unchecked")
	public void setElementAttribute(Element element, String xpath, String value) {
		List list = element.selectNodes(xpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			attribute.setValue(value);
		}
	}

	/**
	 *  获取元素Element的xpath下的Element的Text值
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @return Text值
	 */
	@SuppressWarnings("unchecked")
	public String getElementText(Element element, String xpath) {
		if (element == null) {
			return "";
		}
		List list = element.selectNodes(xpath);
		Iterator iter = list.iterator();
		String value = null;
		if (iter.hasNext()) {
			Element ele = (Element) iter.next();
			value = ele.getText().trim();
		}
		if (value == null) {
			String path = element.getUniquePath() + "/" + xpath;
			if (!nullValues.contains(path)) {
				nullValues.add(path);
			}
			value = "";
		}
		return value;
	}


	/**
	 * 设置元素Element的xpath下的Element的Text值
	 * @param element 当前元素Element
	 * @param xpath xpath路径
	 * @param value Text值
	 */
	@SuppressWarnings("unchecked")
	public void setElementText(Element element, String xpath, String value) {
		List list = element.selectNodes(xpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			Element ele = (Element) iter.next();
			ele.setText(value);
		}
	}

	/**
	 * 读取File的内容
	 * @param file 文件
	 * @return 内容
	 */
	public static String file2String(File file) {
		try {
			InputStream fs = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			fs.read(b);
			fs.close();
			return new String(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取xml字符串，获取Document对象
	 * @param xml xml字符串
	 * @return Document对象
	 */
	public static Document xml2Doc(String xml) {
		try {
			return DocumentHelper.parseText(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return List
	 */
	public List<String> getNullValues() {
		return nullValues;
	}

}

