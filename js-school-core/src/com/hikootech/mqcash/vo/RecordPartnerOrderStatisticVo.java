package com.hikootech.mqcash.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 *   
 * RecordPartnerOrderStatistic  
 *   
 * @function:(各渠道来源下单成功的分期单统计记录)  
 * @create time:Oct 14, 2015 6:31:58 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class RecordPartnerOrderStatisticVo implements Serializable{

	
	private static final long serialVersionUID = 1560992225223350639L;
	
	private String recordId = "" ;   			//统计记录id
	private String configPartnerId = "" ;   	//合作伙伴id
	private BigDecimal successOrderNumber  ;   //当日下单成功分期单数
	private Date statisticTime ;   			//统计日期
	private Date createTime ;   				//创建时间
	private Date updateTime  ;   				//更新时间
	private BigDecimal maxTime  ;   				//当日下单成功最大次数
	private Integer isLimit  ;   				//是否限制：0不限制 1限制
	private String partnerId  ;   				//合作伙伴id
	private String operator = "" ;   			//操作人
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getConfigPartnerId() {
		return configPartnerId;
	}
	public void setConfigPartnerId(String configPartnerId) {
		this.configPartnerId = configPartnerId;
	}
	public BigDecimal getSuccessOrderNumber() {
		return successOrderNumber;
	}
	public void setSuccessOrderNumber(BigDecimal successOrderNumber) {
		this.successOrderNumber = successOrderNumber;
	}
	public Date getStatisticTime() {
		return statisticTime;
	}
	public void setStatisticTime(Date statisticTime) {
		this.statisticTime = statisticTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public BigDecimal getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(BigDecimal maxTime) {
		this.maxTime = maxTime;
	}
	public Integer getIsLimit() {
		return isLimit;
	}
	public void setIsLimit(Integer isLimit) {
		this.isLimit = isLimit;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	
}
