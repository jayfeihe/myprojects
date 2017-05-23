package com.greenkoo.echarts.model.form;

/**
 * 图标查询数据返回的实体
 * 
 * @author QYANZE
 *
 */
public class CountBean {

	private int count; // 数量
	private String confirmTime; // 时间
	private double prop; // 占比 
	
	public CountBean() {
		super();
	}
	public CountBean(int count, String confirmTime) {
		super();
		this.count = count;
		this.confirmTime = confirmTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public double getProp() {
		return prop;
	}
	public void setProp(double prop) {
		this.prop = prop;
	}
}
