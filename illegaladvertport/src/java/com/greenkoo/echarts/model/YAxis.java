package com.greenkoo.echarts.model;

/**
 * 直角坐标系 grid 中的 y 轴
 * 
 * @author QYANZE
 *
 */
public class YAxis {

	private String name; // 名称-与legend的值相对应
	private int min; // 最小值
	private int max; // 最大值
	private String formatter; // 格式化显示
	
	public YAxis() {
		super();
	}
	
	public YAxis(String name, int min, int max, String formatter) {
		super();
		this.name = name;
		this.min = min;
		this.max = max;
		this.formatter = formatter;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
}
