package com.greenkoo.record.model.form;

/**
 * 数据统计对象
 * 
 * @author QYANZE
 *
 */
public class DataCountBean {

	private int count; // 数量
	
	private String timeProp; // 不同时间之间的占比
	
	private int increase; // 增量
	
	private double countProp; // 各违法占总违法的占比

	public DataCountBean() {
		super();
	}

	public DataCountBean(int count, String timeProp, int increase) {
		super();
		this.count = count;
		this.timeProp = timeProp;
		this.increase = increase;
	}

	public DataCountBean(int count, String timeProp) {
		super();
		this.count = count;
		this.timeProp = timeProp;
	}

	public DataCountBean(String timeProp, double countProp) {
		super();
		this.timeProp = timeProp;
		this.countProp = countProp;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTimeProp() {
		return timeProp;
	}

	public void setTimeProp(String timeProp) {
		this.timeProp = timeProp;
	}

	public int getIncrease() {
		return increase;
	}

	public void setIncrease(int increase) {
		this.increase = increase;
	}

	public double getCountProp() {
		return countProp;
	}

	public void setCountProp(double countProp) {
		this.countProp = countProp;
	}
}
