package com.hikootech.mqcash.po.unipay;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/** 
* @ClassName UnipayPersonalConsumeCateory 
* @Description TODO(这里用一句话描述这个类的作用) 
* @author 余巍 yuweiqwe@126.com 
* @date 2016年7月18日 下午4:51:53 
*  
*/
public class UnipayPersonalConsumeCategory implements java.io.Serializable {
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	//field
	/**  **/
	private String id;
	/** 报告id **/
	private String reportId;
	/** 类别 **/
	private String name;
	/** 总消费金额 **/
	private BigDecimal amount;
	/** 总消费笔数 **/
	private int count;
	/**  **/
	private Date createTime;

	public UnipayPersonalConsumeCategory(){}
	public UnipayPersonalConsumeCategory(JSONObject category){
		this.name=category.getString("Name");
		this.amount=category.getBigDecimal("Amount");
		this.count=category.getIntValue("Count");
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//override toString Method 
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("'id':'"+this.getId()+"',");
		sb.append("'reportId':'"+this.getReportId()+"',");
		sb.append("'name':'"+this.getName()+"',");
		sb.append("'amount':'"+this.getAmount()+"',");
		sb.append("'count':'"+this.getCount()+"',");
		sb.append("'createTime':'"+this.getCreateTime()+"'");
		sb.append("}");
		return sb.toString();
	}
}