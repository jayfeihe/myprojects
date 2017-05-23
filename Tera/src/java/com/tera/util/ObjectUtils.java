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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Chu
 * 只对POJO进行处理，目前主要实现了两个功能：1，POJO类的初始化。2，POJO类的toString。
 * 循环嵌套的问题使用图的遍历算法实现。
 */
public class ObjectUtils {

	/**
	 * BOOLEAN_FIELD
	 */
	public static final boolean BOOLEAN_FIELD = false;

	/**
	 * CHAR_FIELD
	 */
	public static final char CHAR_FIELD = 'c';

	/**
	 * INT_FIELD
	 */
	public static final int INT_FIELD = 0;

	/**
	 * DOUBLE_FIELD
	 */
	public static final double DOUBLE_FIELD = 1.0;

	/**
	 * STRING_FIELD
	 */
	public static final String STRING_FIELD = "string";

	/**
	 * DATE_FIELD
	 */
	public static final Date DATE_FIELD = new Date();

	/**
	 * LIST_ARRAY_FIELD_SIZE
	 */
	public static final int LIST_ARRAY_FIELD_SIZE = 2;

	/**
	 * 供递归缓存使用
	 */
	private static StringBuffer buffer = null;

	/**
	 * 记录访问过的节点
	 */
	private static List<String> visited = new LinkedList<String>();

	//前导Tab格式
//	private static String format_Tab = "";

	/**
	 * 将一个对象的属性描述为一个Map
	 * @param obj obj
	 * @return Map
	 */
	public static Map<String, Object> describe(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return map;
		}
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException("Can't describe [" + obj.getClass().getName() + "]", e);
		}
		return map;
	}

	/**
	 * 初始化POJO对象的属性，包括简单类型：char, int, double, String, Date, Object(自定义的)
	 * 数组类型：char[], int[], double[], String[], Date[], Object[](自定义的)。
	 * List类型：List<String>, List<Date>, List<Object>
	 * 注意：除了"数组类型"说明中列出的"char[], int[], double[], String[], Date[]"之外，其他的类型均视为自定义对象数组。
	 * @param obj obj
	 */
	@SuppressWarnings("unchecked")
	public static void initAttribute(Object obj) {
		if (obj == null) {
			return;
		}
		try {
			String objectName = obj.getClass().getName();
			Field[] fields = obj.getClass().getDeclaredFields();
			//System.out.println(fields.length);
			addVisited(objectName);
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String fieldType = field.getType().getName();
				//System.out.println(field.getName() + ",	" + field.getType() + ",	" + isVisited(field));
				//循环嵌套跳过
				if (fieldType.equals(objectName) || isVisited(field)) {
					continue;
				} else if (fieldType.equals("boolean")) {
					field.setBoolean(obj, BOOLEAN_FIELD);
				} else if (fieldType.equals("char")) {
					field.setChar(obj, CHAR_FIELD);
				} else if (fieldType.equals("int")) {
					field.setInt(obj, INT_FIELD);
				} else if (fieldType.equals("double")) {
					field.setDouble(obj, DOUBLE_FIELD);
				} else if (fieldType.equals("java.lang.String")) {
					field.set(obj, STRING_FIELD);
				} else if (fieldType.equals("java.util.Date")) {
					field.set(obj, DATE_FIELD);
				} else if (fieldType.startsWith("[")) {
					//数组处理
					// boolean 数组
					if (fieldType.startsWith("[Z")) {
						boolean[] booleanField = (boolean[]) field.get(obj);
						if (field.get(obj) == null || booleanField.length == 0) {
							booleanField =  new boolean[LIST_ARRAY_FIELD_SIZE];
						} else {
							booleanField = (boolean[]) field.get(obj);
						}
						for (int j = 0; j < booleanField.length; j++) {
							booleanField[j] = BOOLEAN_FIELD;
						}
						field.set(obj, booleanField);
					} else if (fieldType.startsWith("[I")) {
						//int 数组
						int[] intField = (int[]) field.get(obj);
						if (field.get(obj) == null || intField.length == 0) {
							intField =  new int[LIST_ARRAY_FIELD_SIZE];
						} else {
							intField = (int[]) field.get(obj);
						}
						for (int j = 0; j < intField.length; j++) {
							intField[j] = INT_FIELD;
						}
						field.set(obj, intField);
					} else if (fieldType.startsWith("[D")) {
						//double 数组
						double[] doubleField = (double[]) field.get(obj);
						if (field.get(obj) == null || doubleField.length == 0) {
							doubleField =  new double[LIST_ARRAY_FIELD_SIZE];
						} else {
							doubleField = (double[]) field.get(obj);
						}
						for (int j = 0; j < doubleField.length; j++) {
							doubleField[j] = DOUBLE_FIELD;
						}
						field.set(obj, doubleField);
					} else if (fieldType.startsWith("[C")) {
						//char 数组
						char[] charField = (char[]) field.get(obj);
						if (field.get(obj) == null || charField.length == 0) {
							charField =  new char[LIST_ARRAY_FIELD_SIZE];
						} else {
							charField = (char[]) field.get(obj);
						}
						for (int j = 0; j < charField.length; j++) {
							charField[j] = CHAR_FIELD;
						}
						field.set(obj, charField);
					} else if (fieldType.startsWith("[Ljava.lang.String")) {
						//string 数组
						String[] stringField = (String[]) field.get(obj);
						if (field.get(obj) == null || stringField.length == 0) {
							stringField =  new String[LIST_ARRAY_FIELD_SIZE];
						} else {
							stringField = (String[]) field.get(obj);
						}
						for (int j = 0; j < stringField.length; j++) {
							stringField[j] = STRING_FIELD;
						}
						field.set(obj, stringField);
					} else if (fieldType.startsWith("[Ljava.util.Date")) {
						//Date 数组
						Date[] dateField = (Date[]) field.get(obj);
						if (field.get(obj) == null || dateField.length == 0) {
							dateField =  new Date[LIST_ARRAY_FIELD_SIZE];
						} else {
							dateField = (Date[]) field.get(obj);
						}
						for (int j = 0; j < dateField.length; j++) {
							dateField[j] = DATE_FIELD;
						}
						field.set(obj, dateField);
					} else {
						//对象数组处理 TODO walalce
						Object[] objectField = (Object[]) field.get(obj);
						if (field.get(obj) == null || objectField.length == 0) {
							String className = fieldType.substring(2, fieldType.length() - 1);
							Object array = Array.newInstance(Class.forName(className), LIST_ARRAY_FIELD_SIZE);
							for (int j = 0; j < LIST_ARRAY_FIELD_SIZE; j++) {
								Object object = Class.forName(className).newInstance();
								initAttribute(object);
								Array.set(array, j, object);
							}
							field.set(obj, array);
						}
					}
				} else if (fieldType.equals("java.util.List")) {
					//List处理
					Type type = field.getGenericType();
					//System.out.println(type.toString());
					int index1 = type.toString().indexOf("<");
					int index2 = type.toString().indexOf(">");
					String typeName = type.toString().substring(index1 + 1, index2);
					//List String 处理
					if (typeName.contains("java.lang.String")) {
						List<String> stringListField;
						if (field.get(obj) == null) {
							stringListField = new ArrayList<String>();
						} else {
							stringListField = (List) field.get(obj);
						}
						for (int j = 0; j < LIST_ARRAY_FIELD_SIZE; j++) {
							stringListField.add(STRING_FIELD);
						}
						field.set(obj, stringListField);
					} else if (typeName.contains("java.util.Date")) {
						//List Date 处理
						List<Date> dateListField;
						if (field.get(obj) == null) {
							dateListField = new ArrayList<Date>();
						} else {
							dateListField = (List) field.get(obj);
						}
						for (int j = 0; j < LIST_ARRAY_FIELD_SIZE; j++) {
							dateListField.add(DATE_FIELD);
						}
						field.set(obj, dateListField);
					} else {
						//List 对象处理
						List objListField;
						if (field.get(obj) != null) {
							objListField = (List) field.get(obj);
						} else {
							objListField = new ArrayList();
						}
						for (int j = 0; j < LIST_ARRAY_FIELD_SIZE; j++) {
							Object object = Class.forName(typeName).newInstance();
							initAttribute(object);
							objListField.add(object);
						}
						field.set(obj, objListField);
					}
				} else {
					//自定义对象处理
					Object sub = field.get(obj);
					if (sub == null) {
						sub = Class.forName(fieldType).newInstance();
						//System.out.println(sub.getClass().getName());
					}
					initAttribute(sub);
					field.set(obj, sub);
				}
			}
		} catch (Exception e) {
			throw new ObjectUtilsException("Can't init class initAttribute ["
					+ obj.getClass().getName() + "]", e);
		}
	}

	/**
	 * getToString
	 * @param obj obj
	 * @return String
	 */
	public static String getToString(Object obj) {
		buffer = new StringBuffer();
		visited = new LinkedList<String>();
		buffer.append("\n----------" + obj.getClass().getName() + "----------\n");
		toString(obj);
		return buffer.toString();
	}

	/**
	 * 打印POJO对象的属性，包括简单类型：char, int, double, String, Object(自定义的)
	 * 数组类型：char[], int[], double[], String[], Date[], Object[](自定义的)。
	 * List类型：List<String>, List<Date>, List<Object>
	 * 注意：除了"数组类型"说明中列出的"char[], int[], double[], String[], Date[]"之外，其他的类型均视为自定义对象数组。
	 * @param obj obj
	 */
	@SuppressWarnings("unchecked")
	private static void toString(Object obj) {
		if (obj == null) {
			return;
		}
		try {
			String objectName = obj.getClass().getName();
			//System.out.println("=====" + objectName);
			addVisited(objectName);
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String fieldType = field.getType().getName();
				if (field.get(obj) == null) {
					buffer.append(field.getName() + ": ").append(field.get(obj)).append("\n");
					continue;
				}
				// 嵌套自身跳过
				if (fieldType.equals(objectName) || isVisited(field)) {
					continue;
				}
				//基本属性
				if (fieldType.equals("boolean")
						|| fieldType.equals("char")
						|| fieldType.equals("int")
						|| fieldType.equals("double")
						|| fieldType.equals("java.lang.String")) {
					buffer.append(field.getName() + ": ").append(field.get(obj)).append("\n");
				} else if (fieldType.equals("java.util.Date")) {
					buffer.append(field.getName() + ": ").append(
							formatDate((Date) field.get(obj))).append("\n");
				} else if (fieldType.startsWith("[")) {
					//数组处理
					//bool 数组
					if (fieldType.startsWith("[Z")) {
						boolean[] booleanField = (boolean[]) field.get(obj);
						buffer.append("\nboolean[] " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(booleanField.length + "\n");
							for (int j = 0; j < booleanField.length; j++) {
								buffer.append(field.getName() + "[" + j + "]:").append(booleanField[j]).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else if (fieldType.startsWith("[I")) {
						//int 数组
						int[] intField = (int[]) field.get(obj);
						buffer.append("\nint[] " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(intField.length + "\n");
							for (int j = 0; j < intField.length; j++) {
								buffer.append(field.getName() + "[" + j + "]:").append(intField[j]).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else if (fieldType.startsWith("[D")) {
						//double 数组
						double[] doubleField = (double[]) field.get(obj);
						buffer.append("\ndouble[] " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(doubleField.length + "\n");
							for (int j = 0; j < doubleField.length; j++) {
								buffer.append(field.getName() + "[" + j + "]:").append(doubleField[j]).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else if (fieldType.startsWith("[C")) {
						//char 数组
						char[] charField = (char[]) field.get(obj);
						buffer.append("\nchar[] " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(charField.length + "\n");
							for (int j = 0; j < charField.length; j++) {
								buffer.append(field.getName() + "[" + j + "]:").append(charField[j]).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else if (fieldType.startsWith("[Ljava.lang.String")) {
						//string 数组
						String[] stringField = (String[]) field.get(obj);
						buffer.append("\nString[] " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(stringField.length + "\n");
							for (int j = 0; j < stringField.length; j++) {
								buffer.append(field.getName() + "[" + j + "]:").append(stringField[j]).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else if (fieldType.startsWith("[Ljava.util.Date")) {
						//Date 数组
						Date[] dateField = (Date[]) field.get(obj);
						buffer.append("\nDate[] " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(dateField.length + "\n");
							for (int j = 0; j < dateField.length; j++) {
								buffer.append(field.getName() + "[" + j + "]:")
									.append(formatDate(dateField[j])).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else {
						//对象数组处理 TODO walalce
						String className = fieldType.substring(2, fieldType.length() - 1);
						Object[] objField = (Object[]) field.get(obj);
						buffer.append("\n").append(className + "[] " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(objField.length + "\n");
							visited.add(className);
							for (int j = 0; j < objField.length; j++) {
								buffer.append(field.getName() + "[" + j + "]:");
								toString(objField[j]);
								buffer.append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					}
				} else if (fieldType.equals("java.util.List")) {
					//List处理
					Type type = field.getGenericType();
					//System.out.println(type.toString());
					int index1 = type.toString().indexOf("<");
					int index2 = type.toString().indexOf(">");
					String typeName = type.toString().substring(index1 + 1, index2);
					//List String 处理
					if (typeName.contains("java.lang.String")) {
						List<String> stringListField = (List) field.get(obj);
						buffer.append("\nList<String> " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(stringListField.size() + "\n");
							for (int j = 0; j < stringListField.size(); j++) {
								buffer.append(field.getName() + "<" + j + ">:")
									.append(stringListField.get(j)).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else if (typeName.contains("java.util.Date")) {
						//List Date 处理
						List<Date> dateListField = (List) field.get(obj);
						buffer.append("\nList<Date> " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(dateListField.size() + "\n");
							for (int j = 0; j < dateListField.size(); j++) {
								buffer.append(field.getName() + "<" + j + ">:")
									.append(formatDate(dateListField.get(j))).append("\n");
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					} else {
						//List 对象处理
						List objListField = (List) field.get(obj);
						buffer.append("\nList<" + typeName + "> " + field.getName() + " Size:");
						if (field.get(obj) != null) {
							buffer.append(objListField.size() + "\n");
							for (int j = 0; j < objListField.size(); j++) {
								buffer.append(field.getName() + "<" + j + ">:").append("\n");
								toString(objListField.get(j));
							}
						} else {
							buffer.append(0).append("\n");
						}
						buffer.append("\n");
					}
				} else {
					//自定义对象处理
					buffer.append("\n").append(fieldType + " " + field.getName() + "\n");
					toString(field.get(obj));
				}
			}
		} catch (Exception e) {
			throw new ObjectUtilsException("Can't get attribute ["
					+ obj.getClass().getName() + "]", e);
		}
	}

	/**
	 * formatDate
	 * @param date date
	 * @return String
	 */
	private static String formatDate(Date date) {
		if (null == date) {
			return null;
		}
		try {
			DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datestr = simpleDateFormat.format(date);
			return datestr;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * addVisited
	 * @param className className
	 */
	private static void addVisited(String className) {
		if (className == null
				|| className.equals("boolean")
				|| className.equals("char")
				|| className.equals("int")
				|| className.equals("double")
				|| className.equals("java.lang.String")
				|| className.equals("java.util.Date")) {
			return;
		} else {
			visited.add(className);
			//System.out.println("Add:" + className);
		}
	}

	/**
	 * isVisited
	 * @param field field
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	private static boolean isVisited(Field field) {
		if (field == null) {
			return false;
		}
		field.setAccessible(true);
		String fieldType = field.getType().getName();
		boolean isVisited = false;
		String className = fieldType;
		if (fieldType.startsWith("[")) {
			className = fieldType.substring(2, fieldType.length() - 1);
		} else if (fieldType.equals("java.util.List")) {
			Type type = field.getGenericType();
			//System.out.println(type.toString());
			int index1 = type.toString().indexOf("<");
			int index2 = type.toString().indexOf(">");
			className = type.toString().substring(index1 + 1, index2);
		}
		for (Iterator iter = visited.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if (element.equals(className)) {
				isVisited = true;
			}
		}
		return isVisited;
	}

}

/**
 * @author Wallace chu
 *
 */
@SuppressWarnings("serial")
class ObjectUtilsException extends RuntimeException {

	/**
	 * @param message message
	 * @param e Exception
	 */
	ObjectUtilsException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * @param message message
	 */
	ObjectUtilsException(String message) {
		super(message);
	}

}
