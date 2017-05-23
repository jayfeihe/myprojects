package com.hikootech.mqcash.util;

public class SeqCreateVo {

	private long  num = 1;//订单序号
	private String curDate;//当前日期
	private String serviceType;//业务类型
	
	
	public SeqCreateVo(long num,String curDate,String serviceType)
	{
		this.num =num;
		this.curDate = curDate;
		this.serviceType = serviceType;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getCurDate() {
		return curDate;
	}
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
