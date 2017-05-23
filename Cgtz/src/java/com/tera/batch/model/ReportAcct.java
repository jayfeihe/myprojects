package com.tera.batch.model;

import com.tera.util.DateUtils;

/**
 * 
 * 报表数据，每天汇总一次。各个公司的存量，转贷情况等统计实体类
 * <b>功能：</b>ReportAcctDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-15 10:21:02<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ReportAcct {

	//属性部分
	private int id; //id
	private String orgId; //分公司
	private String product; //产品
	private int norNum; //未到期未收回笔数
	private double norAmt; //未到期未收回金额
	private int overNum; //已到期未收回笔数
	private double overAmt; //已到期未收回金额
	private int allNum; //债权总笔数
	private double allAmt; //债权总金额
	private int tranNum; //转贷笔数
	private double tranAmt; //转贷金额
	private double tranRate; //转贷金额转贷比(转贷金额/存量)
	private double tranFewAmt; //转贷次数<=3项目金额
	private double tranMoreAmt; //转贷次数>3项目金额
	private java.util.Date createDate; //创建日期
	private String createDateStr; //创建日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getProduct () {
		return this.product;
	}
	public int getNorNum () {
		return this.norNum;
	}
	public double getNorAmt () {
		return this.norAmt;
	}
	public int getOverNum () {
		return this.overNum;
	}
	public double getOverAmt () {
		return this.overAmt;
	}
	public int getAllNum () {
		return this.allNum;
	}
	public double getAllAmt () {
		return this.allAmt;
	}
	public int getTranNum () {
		return this.tranNum;
	}
	public double getTranAmt () {
		return this.tranAmt;
	}
	public double getTranRate () {
		return this.tranRate;
	}
	public double getTranFewAmt () {
		return this.tranFewAmt;
	}
	public double getTranMoreAmt () {
		return this.tranMoreAmt;
	}
	public java.util.Date getCreateDate () {
		return this.createDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getCreateDateStr () {
		return DateUtils.formatDate(this.createDate);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setProduct (String product) {
		this.product=product;
	}
	public void setNorNum (int norNum) {
		this.norNum=norNum;
	}
	public void setNorAmt (double norAmt) {
		this.norAmt=norAmt;
	}
	public void setOverNum (int overNum) {
		this.overNum=overNum;
	}
	public void setOverAmt (double overAmt) {
		this.overAmt=overAmt;
	}
	public void setAllNum (int allNum) {
		this.allNum=allNum;
	}
	public void setAllAmt (double allAmt) {
		this.allAmt=allAmt;
	}
	public void setTranNum (int tranNum) {
		this.tranNum=tranNum;
	}
	public void setTranAmt (double tranAmt) {
		this.tranAmt=tranAmt;
	}
	public void setTranRate (double tranRate) {
		this.tranRate=tranRate;
	}
	public void setTranFewAmt (double tranFewAmt) {
		this.tranFewAmt=tranFewAmt;
	}
	public void setTranMoreAmt (double tranMoreAmt) {
		this.tranMoreAmt=tranMoreAmt;
	}
	public void setCreateDate (java.util.Date createDate) {
		this.createDate=createDate;
	}
	public void setCreateDateStr (String createDateStr) {
		this.createDateStr=createDateStr;
	}

}

