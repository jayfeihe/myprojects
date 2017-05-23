package com.tera.batch.initseq.model;


/**
 * 
 */
public class Sequence {

	//属性部分
	private String name; //序列名称
	private int currentValue; //当前值
	private int increment; //每次增加数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
	public int getIncrement() {
		return increment;
	}
	public void setIncrement(int increment) {
		this.increment = increment;
	}

}

