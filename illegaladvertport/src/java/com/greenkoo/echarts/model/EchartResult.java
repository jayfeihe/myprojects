package com.greenkoo.echarts.model;

/**
 * 给前端传递数据结果
 * 
 * @author QYANZE
 *
 */
public class EchartResult {

	private Legend legend;
	private XAxis[] xAxis;
	private YAxis[] yAxis;
	private Series[] series;
	
	public EchartResult() {
		super();
	}
	public EchartResult(Legend legend, XAxis[] xAxis, YAxis[] yAxis, Series[] series) {
		super();
		this.legend = legend;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.series = series;
	}
	public Legend getLegend() {
		return legend;
	}
	public void setLegend(Legend legend) {
		this.legend = legend;
	}
	public XAxis[] getxAxis() {
		return xAxis;
	}
	public void setxAxis(XAxis[] xAxis) {
		this.xAxis = xAxis;
	}
	public YAxis[] getyAxis() {
		return yAxis;
	}
	public void setyAxis(YAxis[] yAxis) {
		this.yAxis = yAxis;
	}
	public Series[] getSeries() {
		return series;
	}
	public void setSeries(Series[] series) {
		this.series = series;
	}
}
