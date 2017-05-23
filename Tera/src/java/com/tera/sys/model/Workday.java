package com.tera.sys.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>WorkdayDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-14 16:31:40<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Workday {

	//属性部分
	private int id; //ID
	private java.util.Date day; //日期
	private String dayStr; //日期
	private String work; //上班
	private String remark; //备注

	//getter部分
	public int getId () {
		return this.id;
	}
	public java.util.Date getDay () {
		return this.day;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getDayStr () {
		return DateUtils.formatDate(this.day);
	}
	public String getWork () {
		return this.work;
	}
	public String getRemark () {
		return this.remark;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setDay (java.util.Date day) {
		this.day=day;
	}
	public void setDayStr (String dayStr) {
		this.dayStr=dayStr;
	}
	public void setWork (String work) {
		this.work=work;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}

}

