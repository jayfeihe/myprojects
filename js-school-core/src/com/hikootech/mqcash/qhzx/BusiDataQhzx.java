package com.hikootech.mqcash.qhzx;

import java.util.List;


/**
	* 此类描述的是：前海征信业务主体信息（请求与响应主体结构都为 batchNo和records）
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:13:20
	*/
	
public class BusiDataQhzx {

	
	private   String batchNo;
	private String subProductInc;
	private   List   records;
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public List getRecords() {
		return records;
	}
	public void setRecords(List  records) {
		this.records = records;
	}
	public void setSubProductInc(String subProductInc) {
		this.subProductInc = subProductInc;
	}
	public String getSubProductInc() {
		return subProductInc;
	}

}
