package com.greenkoo.echarts.model;

/**
 * 直角坐标系 grid 中的 x轴
 * 
 * @author QYANZE
 *
 */
public class XAxis {

	private String[] data;     // 数据
	
	public XAxis() {
		super();
	}
	public XAxis(String[] data) {
		super();
		this.data = data;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
}
