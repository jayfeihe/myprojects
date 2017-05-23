package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;
/**  
 *   
 * UserValidateCardLimit  
 *   
 * @function:(用户验证银行卡限制表)  
 * @create time:Nov 3, 2015 3:32:33 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserValidateCardLimit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idCard = ""; 	//身份证号
	private Integer curTimes;		//当前验证次数
	private Integer maxTimes;			//大多验证次数
	private Integer totalTimes;     //验证总次数   持续加1，不清零
	private Date createTime;		//创建时间
	private Date updateTime;		//修改时间
	private String operator;		//操作人
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getCurTimes() {
		return curTimes;
	}
	public void setCurTimes(Integer curTimes) {
		this.curTimes = curTimes;
	}
	public Integer getMaxTimes() {
		return maxTimes;
	}
	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
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
	public Integer getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}
	

}
