package com.tera.cooperation.jmbox.model.form;

import java.util.Date;

/**
 * 项目查询 返回 结果对象
 * @author XunXiake
 *
 */
public class JMProjecResponseBean {

	private String projectid;			//项目ID
	private String status;				// 状态  0：已创建、1：准备中、2：准备完毕、3：投标中、4：投标结束、5：还款中、6：还款完成、7：中⽌止、8：结束
	private String amount;				//上线标实际融资金额
	private String guaranteefee;		//保障机构服务费
	private String riskguaranteefee;	//风险准备金
	private String servicefee;			//融资服务费
	private Date biddealline;			//满标时间
	
	
	

	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getGuaranteefee() {
		return guaranteefee;
	}
	public void setGuaranteefee(String guaranteefee) {
		this.guaranteefee = guaranteefee;
	}
	public String getRiskguaranteefee() {
		return riskguaranteefee;
	}
	public void setRiskguaranteefee(String riskguaranteefee) {
		this.riskguaranteefee = riskguaranteefee;
	}
	public String getServicefee() {
		return servicefee;
	}
	public void setServicefee(String servicefee) {
		this.servicefee = servicefee;
	}
	public Date getBiddealline() {
		return biddealline;
	}
	public void setBiddealline(Date biddealline) {
		this.biddealline = biddealline;
	}
	

	

}
