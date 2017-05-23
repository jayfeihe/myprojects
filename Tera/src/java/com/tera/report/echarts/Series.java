package com.tera.report.echarts;

import java.util.List;

/** 通用; 驱动图表生成数据
 * @author QYANZE
 *
 */
public class Series {

	private String name;
	private String type;
	private List<Object> data;
	
	public Series(String name, String type, List<Object> seriexData) {
		super();
		this.name = name;
		this.type = type;
		this.data = seriexData;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	
	/**
	 * 折线图 
	 */
	public final static String TYPE_LINE = "line"; 
	/**
	 * 柱状图
	 */
	public final static String TYPE_BAR = "bar";  
	/**
	 * 散点图 
	 */
	public final static String TYPE_SCATTER = "scatter"; 
	/**
	 * K线图 
	 */
	public final static String TYPE_K = "k"; 
	/**
	 * 饼图
	 */
	public final static String TYPE_PIE= "pie"; 
	/**
	 * 雷达图
	 */
	public final static String TYPE_RADAR = "radar"; 
	/**
	 * 和弦图
	 */
	public final static String TYPE_CHORD = "chord"; 
	/**
	 * 力导向布局图
	 */
	public final static String TYPE_FORCE = "force"; 
	/**
	 * 地图
	 */
	public final static String TYPE_MAP = "map";
}
