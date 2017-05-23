package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class TopViewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String currentPayment;//30天还款总额
	private String allLastPayment; //所有欠款
	private String nowDate; //测试时发现更改日期时，js中的时间不能实时同步更新，与下方数据显示不一致，故此加该字段，用以前台显示
	private String eveMapStr="";
	private Map<String,EveryDayPaymentViewDTO> eveMap; //每日欠款集合 key
	public String getCurrentPayment() {
		return currentPayment;
	}

	public void setCurrentPayment(String currentPayment) {
		this.currentPayment = currentPayment;
	}

	public String getAllLastPayment() {
		return allLastPayment;
	}
	public void setAllLastPayment(String allLastPayment) {
		this.allLastPayment = allLastPayment;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public Map<String, EveryDayPaymentViewDTO> getEveMap() {
		return eveMap;
	}
	public void setEveMap(Map<String, EveryDayPaymentViewDTO> eveMap) {
		this.eveMap = eveMap;
	}
	public String getEveMapStr() {
		return JSONObject.fromObject(this.getEveMap()).toString();
	}
	public void setEveMapStr(String eveMapStr) {
		this.eveMapStr = eveMapStr;
	}
	 
}
