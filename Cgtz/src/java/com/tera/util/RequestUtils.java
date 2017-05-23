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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wallace chu
 *
 */
public class RequestUtils {

	/**
	 * 从request中创建Bean
	 * @param clazz Class
	 * @param request httpserleterequest
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static Object getRequestBean(Class clazz, HttpServletRequest request) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			Map paraMap = request.getParameterMap();
			Set<String> key = paraMap.keySet();
			System.out.println("--------页面传递参数---------");
			for (Iterator it = key.iterator(); it.hasNext();) {
				String k = (String) it.next();
				System.out.print(k + ":");
				Object valueObj = paraMap.get(k);
				if (valueObj.getClass().getName().startsWith("[Ljava.lang.String")) {
					String[] values = (String[]) valueObj;
					for (int i = 0; i < values.length; i++) {
						//统一将参数解码
						String value = URLDecoder.decode(values[i], "UTF-8");
						//过滤到html页面的空白字符
						if (value.equals("&nbsp;")) {
							value = "";
						}
						values[i] = value;
						System.out.print(value + ",");
					}
				}
				System.out.println();
			}
			System.out.println("--------页面传递参数---------");
			for (Field field : fields) {
				field.setAccessible(true);
				String  fieldName = field.getName();
				String[] paraValues = (String[]) paraMap.get(fieldName);
				if (null == paraValues || paraValues.length <= 0) {
					continue;
				}
				if (int.class.equals(field.getType())
						|| Integer.class.equals(field.getType())) {
					String param = paraValues[0];
					if (param != null && param.matches("-?\\d+")) {
						field.setInt(obj, Integer.parseInt(param));
					}
				} else if (long.class.equals(field.getType())
						|| Long.class.equals(field.getType())) {
					String param = paraValues[0];
					if (param != null && param.matches("-?\\d+")) {
						field.setLong(obj, Long.parseLong(param));
					}
				} else if (float.class.equals(field.getType())
						|| Float.class.equals(field.getType())) {
					String param = paraValues[0];
					if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
						field.setFloat(obj, Float.parseFloat(param));
					}
				} else if (double.class.equals(field.getType())
						|| Double.class.equals(field.getType())) {
					String param = paraValues[0];
					if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
						field.setDouble(obj, Double.parseDouble(param));
					}
				} else if (String.class.equals(field.getType())) {
					String param = paraValues[0];
//					if (param.matches("(%..)+")) {
//						param = URLDecoder.decode(param, "UTF-8");
//					}
					field.set(obj, param);
				} else if (Date.class.equals(field.getType())) {
					String param = paraValues[0];
					Date value = DateUtils.getDate(param);
					field.set(obj, value);
				} else if (Timestamp.class.equals(field.getType())) {
					String param = paraValues[0];
					Date value = DateUtils.getTime(param);
					if (null != value) {
						field.set(obj, new Timestamp(value.getTime()));
					}
				} else if (boolean.class.equals(field.getType())
						|| Boolean.class.equals(field.getType())) {
					String param = paraValues[0];
					if (!"0".equals(param) || "true".equalsIgnoreCase(param)) {
						field.set(obj, true);
					}
				} else if (int[].class.equals(field.getType())
							|| Integer[].class.equals(field.getType())) {
					Object array = Array.newInstance(int.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i];
						int value = 0;
						if (param != null && param.matches("-?\\d+")) {
							value = Integer.parseInt(param);
						}
						Array.set(array, i, value);
					}
					field.set(obj, array);
				} else if (long[].class.equals(field.getType())
							|| Long[].class.equals(field.getType())) {
					Object array = Array.newInstance(long.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i];
						long value = 0;
						if (param != null && param.matches("-?\\d+")) {
							value = Long.parseLong(param);
						}
						Array.set(array, i, value);
					}
					field.set(obj, array);
				} else if (float[].class.equals(field.getType())
							|| Float[].class.equals(field.getType())) {
					Object array = Array.newInstance(float.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i];
						float value = 0;
						if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
							value = Float.parseFloat(param);
						}
						Array.set(array, i, value);
					}
					field.set(obj, array);
				} else if (double[].class.equals(field.getType())
							|| Double[].class.equals(field.getType())) {
					Object array = Array.newInstance(double.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i];
						double value = 0;
						if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
							value = Double.parseDouble(param);
						}
						Array.set(array, i, value);
					}
					field.set(obj, array);
				} else if (String[].class.equals(field.getType())) {
					Object array = Array.newInstance(String.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i];
//						if (param.matches("(%..)+")) {
//							param = URLDecoder.decode(param, "UTF-8");
//						}
						Array.set(array, i, param == null ? "" : param);
					}
					field.set(obj, array);
				} else if (Date[].class.equals(field.getType())) {
					Object array = Array.newInstance(Date.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i];
						Array.set(array, i, DateUtils.getDate(param));
					}
					field.set(obj, array);
				} else if (Timestamp[].class.equals(field.getType())) {
					Object array = Array.newInstance(Timestamp.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i];
						Date value = DateUtils.getTime(param);
						if (null != value) {
							Array.set(array, i, new Timestamp(value.getTime()));
						}
					}
					field.set(obj, array);
				} else if (boolean[].class.equals(field.getType())
							|| Boolean[].class.equals(field.getType())) {
					Object array = Array.newInstance(boolean.class, paraValues.length);
					for (int i = 0; i < paraValues.length; i++) {
						String param = paraValues[i] == null ? "" : paraValues[i];
						boolean value = false;
						if (!"0".equals(param) || "true".equalsIgnoreCase(param)) {
							value = true;
						}
						Array.set(array, i, value);
					}
					field.set(obj, array);
				} else if ("java.util.List".equals(field.getType().getName())
						|| "java.util.ArrayList".equals(field.getType().getName())
						|| "java.util.LinkedList".equals(field.getType().getName())) {
					Type type = field.getGenericType();
					int index1 = type.toString().indexOf("<");
					int index2 = type.toString().indexOf(">");
					String typeName = type.toString().substring(index1 + 1, index2);
					if (typeName.contains("java.lang.Integer")) {
						List<Integer> list = new ArrayList<Integer>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i];
							int value = 0;
							if (param != null && param.matches("-?\\d+")) {
								value = Integer.parseInt(param);
							}
							list.add(value);
						}
						field.set(obj, list);
					} else if (typeName.contains("java.lang.Long")) {
						List<Long> list = new ArrayList<Long>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i];
							long value = 0;
							if (param != null && param.matches("-?\\d+")) {
								value = Long.parseLong(param);
							}
							list.add(value);
						}
						field.set(obj, list);
					} else if (typeName.contains("java.lang.Float")) {
						List<Float> list = new ArrayList<Float>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i];
							float value = 0;
							if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
								value = Float.parseFloat(param);
							}
							list.add(value);
						}
						field.set(obj, list);
					} else if (typeName.contains("java.lang.Double")) {
						List<Double> list = new ArrayList<Double>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i];
							double value = 0;
							if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
								value = Double.parseDouble(param);
							}
							list.add(value);
						}
						field.set(obj, list);
					} else if (typeName.contains("java.lang.String")) {
						List<String> list = new ArrayList<String>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i];
//							if (param.matches("(%..)+")) {
//								param = URLDecoder.decode(param, "UTF-8");
//							}
							list.add(param == null ? "" : param);
						}
						field.set(obj, list);
					} else if (typeName.contains("java.util.Date")) {
						List<Date> list = new ArrayList<Date>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i];
							list.add(DateUtils.getDate(param));
						}
						field.set(obj, list);
					} else if (typeName.contains("java.sql.Timestamp")) {
						List<Timestamp> list = new ArrayList<Timestamp>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i];
							Date value = DateUtils.getTime(param);
							if (null != value) {
								list.add(new Timestamp(value.getTime()));
							}
						}
						field.set(obj, list);
					} else if (typeName.contains("java.lang.Boolean")) {
						List<Boolean> list = new ArrayList<Boolean>();
						for (int i = 0; i < paraValues.length; i++) {
							String param = paraValues[i] == null ? "" : paraValues[i];
							boolean value = false;
							if (!"0".equals(param) || "true".equalsIgnoreCase(param)) {
								value = true;
							}
							list.add(value);
						}
						field.set(obj, list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static String getURLPart(String url) {
		if (url == null) {
			return url;
		}
		int index = url.lastIndexOf("/");
		if (index != -1) {
			url = url.substring(index + 1);
		}
		index = url.indexOf("?");
		if (index != -1) {
			url = url.substring(0, index);
		}
		//对于"referer:index.do;jsessionid=6CDD0B0F8D340D8268E92810CFD7A499"的情况,IE7
		index = url.indexOf(";");
		if (index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List getRequestBeanList(String qz,Class clazz, HttpServletRequest request) {
		
		List<Object> rtList=new ArrayList<Object>();
		
		try {
			
			int objsize=0;
			Map paraMap = request.getParameterMap();
			Set<String> key = paraMap.keySet();
			System.out.println("--------页面传递参数---------");
			for (Iterator it = key.iterator(); it.hasNext();) {
				String k = (String) it.next();
				if(k.indexOf(qz)==0){
					System.out.print(k + ":");
					Object valueObj = paraMap.get(k);
					if (valueObj.getClass().getName().startsWith("[Ljava.lang.String")) {
						String[] values = (String[]) valueObj;
						for (int i = 0; i < values.length; i++) {
							//统一将参数解码
							String value = URLDecoder.decode(values[i], "UTF-8");
							//过滤到html页面的空白字符
							if (value.equals("&nbsp;")) {
								value = "";
							}
							values[i] = value;
							System.out.print(value + ",");
						}
						objsize=values.length>objsize?values.length:objsize;
						System.out.println(qz+":"+objsize);
					}
				}
				System.out.println();
			}
			System.out.println("--------页面传递参数---------");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < objsize; i++) {
				Object obj = clazz.newInstance();
				for (Field field : fields) {
					field.setAccessible(true);
					String  fieldName = qz+"."+field.getName();
					
					String[] paraValues = (String[]) paraMap.get(fieldName);
					if (null == paraValues || paraValues.length <= 0) {
						continue;
					}
					if (int.class.equals(field.getType())
							|| Integer.class.equals(field.getType())) {
						String param = paraValues[i];
						if (param != null && param.matches("-?\\d+")) {
							field.setInt(obj, Integer.parseInt(param));
						}
					} else if (long.class.equals(field.getType())
							|| Long.class.equals(field.getType())) {
						String param = paraValues[i];
						if (param != null && param.matches("-?\\d+")) {
							field.setLong(obj, Long.parseLong(param));
						}
					} else if (float.class.equals(field.getType())
							|| Float.class.equals(field.getType())) {
						String param = paraValues[i];
						if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
							field.setFloat(obj, Float.parseFloat(param));
						}
					} else if (double.class.equals(field.getType())
							|| Double.class.equals(field.getType())) {
						String param = paraValues[i];
						if (param != null && param.matches("(-?\\d+)(\\.\\d*)?")) {
							field.setDouble(obj, Double.parseDouble(param));
						}
					} else if (String.class.equals(field.getType())) {
						String param = paraValues[i];
						field.set(obj, param);
					} else if (Date.class.equals(field.getType())) {
						String param = paraValues[i];
						Date value = DateUtils.getDate(param);
						field.set(obj, value);
					} else if (Timestamp.class.equals(field.getType())) {
						String param = paraValues[i];
						Date value = DateUtils.getTime(param);
						if (null != value) {
							field.set(obj, new Timestamp(value.getTime()));
						}
					} else if (boolean.class.equals(field.getType())
							|| Boolean.class.equals(field.getType())) {
						String param = paraValues[i];
						if (!"0".equals(param) || "true".equalsIgnoreCase(param)) {
							field.set(obj, true);
						}
					} 
				}
				rtList.add(obj);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtList;
	}

	
}
