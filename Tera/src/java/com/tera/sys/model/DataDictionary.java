package com.tera.sys.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>DataDictionaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 16:19:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class DataDictionary {

	//属性部分
	private int id; //ID
	private String keyName; //字典名称
	private String keyProp; //字典属性
	private String keyValue; //字典值
	private String description; //描述
	private String parentKeyProp; //字典父属性

	//getter部分
	public int getId () {
		return this.id;
	}
	//getter部分,Date类型的修改获取String的方法
	//getter部分,Timestamp类型的修改获取String的方法
	public String getKeyName () {
		return this.keyName;
	}
	//getter部分,Date类型的修改获取String的方法
	//getter部分,Timestamp类型的修改获取String的方法
	public String getKeyProp () {
		return this.keyProp;
	}
	//getter部分,Date类型的修改获取String的方法
	//getter部分,Timestamp类型的修改获取String的方法
	public String getKeyValue () {
		return this.keyValue;
	}
	//getter部分,Date类型的修改获取String的方法
	//getter部分,Timestamp类型的修改获取String的方法
	public String getDescription () {
		return this.description;
	}
	//getter部分,Date类型的修改获取String的方法
	//getter部分,Timestamp类型的修改获取String的方法
	public String getParentKeyProp () {
		return this.parentKeyProp;
	}
	//getter部分,Date类型的修改获取String的方法
	//getter部分,Timestamp类型的修改获取String的方法

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setKeyName (String keyName) {
		this.keyName=keyName;
	}
	public void setKeyProp (String keyProp) {
		this.keyProp=keyProp;
	}
	public void setKeyValue (String keyValue) {
		this.keyValue=keyValue;
	}
	public void setDescription (String description) {
		this.description=description;
	}
	public void setParentKeyProp (String parentKeyProp) {
		this.parentKeyProp=parentKeyProp;
	}

}

