package com.greenkoo.dmp.sys.model;

/**
 * 系统上下文
 * 
 * @author QYANZE
 *
 */
public class SystemContext {
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();

	private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();

	private static ThreadLocal<String> realPath = new ThreadLocal<String>();

	public static Integer getPageSize() {
		return (Integer) pageSize.get();
	}

	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}

	public static Integer getPageOffset() {
		return (Integer) pageOffset.get();
	}

	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);
	}

	public static void setRealPath(String _realPath) {
		realPath.set(_realPath);
	}

	public static String getRealPath() {
		return (String) realPath.get();
	}

	public static void removePageSize() {
		pageSize.remove();
	}

	public static void removeRealPath() {
		realPath.remove();
	}
	
	public static void removePageOffset() {
		pageOffset.remove();
	}
}