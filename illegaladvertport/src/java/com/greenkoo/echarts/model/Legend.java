package com.greenkoo.echarts.model;

/**
 * 图表线条说明
 * 
 * @author QYANZE
 *
 */
public class Legend {

	private String[] data;

	public Legend() {
		super();
	}

	public Legend(String[] data) {
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
