package com.tera.interfaces.model;

/**
 * App接口字典实体
 * @author QYANZE
 *
 */
public class AppDictBean {

	private String value; // 值
	private String text; // 文本
	
	public AppDictBean() {
		super();
	}
	public AppDictBean(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
