package com.tera.report.echarts;

import java.util.ArrayList;
import java.util.List;

/** echarts 返回结果
 * @author QYANZE
 *
 */
public class EchartsResult {

	private List<String> legend = new ArrayList<String>(); 
	private List<String> category = new ArrayList<String>(); // 横坐标
	private List<Series> series = new ArrayList<Series>();//纵坐标
	
	private String seriesDataJson; // 通过构建json传递
	
	
	public EchartsResult() {
		super();
	}

	/**
	 * 无坐标轴-饼图和漏斗图
	 * @param legend
	 * @param series
	 */
	public EchartsResult(List<String> legend, String seriesDataJson) {
		super();
		this.legend = legend;
		this.setSeriesDataJson(seriesDataJson);
	}
	
	/**
	 * 有坐标轴-柱状和线图
	 * @param legend
	 * @param category
	 * @param series
	 */
	public EchartsResult(List<String> legend, List<String> category, List<Series> series) {
		super();
		this.legend = legend;
		this.category = category;
		this.series = series;
	}

	public List<String> getLegend() {
		return legend;
	}

	public void setLegend(List<String> legend) {
		this.legend = legend;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	public String getSeriesDataJson() {
		return seriesDataJson;
	}

	public void setSeriesDataJson(String seriesDataJson) {
		this.seriesDataJson = seriesDataJson;
	}
}
