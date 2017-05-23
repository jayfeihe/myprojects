package com.hikootech.mqcash.vo;

import java.io.Serializable;

/** 
* @ClassName UserHdbxRecordsStr 
* @Description TODO(被信贷机构查询次数) 
* @author HAI DA
* @date Dec 20, 2015 1:39:48 PM 
*  
*/
public class UserHdbxRecordsStr implements Serializable{

	private static final long serialVersionUID = 8675603053186240842L;
	private Integer lastTwelveMonth ;
	private Integer lastSixMonth ;
	private Integer lastThreemonth ;
	private Integer lastOneMonth ;
	public Integer getLastTwelveMonth() {
		return lastTwelveMonth;
	}
	public void setLastTwelveMonth(Integer lastTwelveMonth) {
		this.lastTwelveMonth = lastTwelveMonth;
	}
	public Integer getLastSixMonth() {
		return lastSixMonth;
	}
	public void setLastSixMonth(Integer lastSixMonth) {
		this.lastSixMonth = lastSixMonth;
	}
	public Integer getLastThreemonth() {
		return lastThreemonth;
	}
	public void setLastThreemonth(Integer lastThreemonth) {
		this.lastThreemonth = lastThreemonth;
	}
	public Integer getLastOneMonth() {
		return lastOneMonth;
	}
	public void setLastOneMonth(Integer lastOneMonth) {
		this.lastOneMonth = lastOneMonth;
	}
	
	
	
}
