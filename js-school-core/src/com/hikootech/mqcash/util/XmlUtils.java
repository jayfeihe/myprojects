package com.hikootech.mqcash.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author yuwei
 * 2015年8月20日
 * XML相关工具类
 * 如果并发上去后效率低下，可以使用Map将需要使用Xstream（xStream.processAnnotations(obj.getClass());）各种类型注册到Map中，根据对象getClass获取
 */
public class XmlUtils {
	
	/**
	 * 转换时编码格式
	 */
	public final static String code = "utf-8";
	
	
	/**将Bean对象转换为xml格式字符串
	 * @param obj
	 * @return
	 */
	public static String Bean2Xml(Object obj){
		XStream xStream = new XStream();
		xStream.processAnnotations(obj.getClass());
		xStream.autodetectAnnotations(true);
		
		String xml = xStream.toXML(obj);
		
		return xml;
	}
	
	/**将xml字符串转换为type类型对象
	 * @param xml
	 * @param type
	 * @return
	 */
	public static Object xml2Bean(String xml, Class<?> type){
		XStream xStream = new XStream(new DomDriver("utf-8"));
//		xStream1.alias("request", BuildBindingRelationSendSMRequest.class);
		xStream.processAnnotations(type);
		xStream.autodetectAnnotations(true);
		
		return xStream.fromXML(xml);
	}
	
}
