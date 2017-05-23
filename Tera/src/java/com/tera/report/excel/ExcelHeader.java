package com.tera.report.excel;

/**
 * 表头定义类
 * @author QYANZE
 *
 */
public class ExcelHeader implements Comparable<ExcelHeader> {

	private String title;
	private int order;
	private String fieldName;
	
	public ExcelHeader() {
		super();
	}
	
	public ExcelHeader(String title, int order, String fieldName) {
		super();
		this.title = title;
		this.order = order;
		this.fieldName = fieldName;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public int compareTo(ExcelHeader o) {
		return order>o.order?1:(order<o.order?-1:0);
	}
}
