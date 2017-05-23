package com.hikootech.mqcash.po.unipay;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName UnipayPersonalMonthConsume
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author 余巍 yuweiqwe@126.com
 * @date 2016年7月18日 下午4:57:44
 * 
 */
public class UnipayPersonalMonthConsume implements java.io.Serializable {
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	// field
	/**  **/
	private String id;
	/** 报告id **/
	private String reportId;
	/** 月份 **/
	private String month;
	/** 总消费金额 **/
	private BigDecimal amount;
	/** 总消费笔数 **/
	private Integer count;
	/** 消费金额在本市排名 **/
	private Integer amountRanking;
	/** 消费笔数在本市排名 **/
	private Integer countRanking;
	/**  **/
	private Date createTime;

	public UnipayPersonalMonthConsume(){}
	public UnipayPersonalMonthConsume(JSONObject consume){
		this.month=consume.getString("Month");
		this.amount=consume.getBigDecimal("Amount");
		this.count=consume.getIntValue("Count");
		this.amountRanking=consume.getIntValue("AmountRanking");
		this.countRanking=consume.getIntValue("CountRanking");
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getAmountRanking() {
		return amountRanking;
	}

	public void setAmountRanking(Integer amountRanking) {
		this.amountRanking = amountRanking;
	}

	public Integer getCountRanking() {
		return countRanking;
	}

	public void setCountRanking(Integer countRanking) {
		this.countRanking = countRanking;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// override toString Method
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("'id':'" + this.getId() + "',");
		sb.append("'reportId':'" + this.getReportId() + "',");
		sb.append("'month':'" + this.getMonth() + "',");
		sb.append("'amount':'" + this.getAmount() + "',");
		sb.append("'count':'" + this.getCount() + "',");
		sb.append("'amountRanking':'" + this.getAmountRanking() + "',");
		sb.append("'countRanking':'" + this.getCountRanking() + "',");
		sb.append("'createTime':'" + this.getCreateTime() + "'");
		sb.append("}");
		return sb.toString();
	}
}