package com.greenkoo.echarts.model;

/**
 * 系列列表。每个系列通过 type 决定自己的图表类型
 * 
 * @author QYANZE
 *
 */
public class Series {

	private String name;    // 名称-与legend中对应
	private String[] data;  // 数据
	
	public Series() {
		super();
	}
	public Series(String name, String[] data) {
		super();
		this.name = name;
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
}
