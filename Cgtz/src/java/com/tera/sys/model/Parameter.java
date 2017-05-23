package com.tera.sys.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>ParameterDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 17:30:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Parameter {

	//属性部分
	private int id; //ID
	private String paramName; //参数名称
	private String paramValue; //参数值
	private String discription; //说明

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getParamName () {
		return this.paramName;
	}
	public String getParamValue () {
		return this.paramValue;
	}
	public String getDiscription () {
		return this.discription;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setParamName (String paramName) {
		this.paramName=paramName;
	}
	public void setParamValue (String paramValue) {
		this.paramValue=paramValue;
	}
	public void setDiscription (String discription) {
		this.discription=discription;
	}

}

