package com.tera.report.model;

import java.sql.Timestamp;

import com.tera.util.DateUtils;

public class ReportDeal {
   private int id;
   private String orgId;
   private String orgName;
   private String type;
   private String mon;
   private int count;
   private double amt;
   private Timestamp createTime;
   private String createTimeStr;
   //getter部分
	public int getId() {
		return id;
	}
	public String getOrgId() {
		return orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getType() {
		return type;
	}
	public String getMon() {
		return mon;
	}
	public int getCount() {
		return count;
	}
	public double getAmt() {
		return amt;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public String getCreateTimeStr() {
		return DateUtils.formatTime(createTime);
	}
	//setter部分
	public void setId(int id) {
		this.id = id;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
   
}
