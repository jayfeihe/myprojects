package com.tera.audit.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * T_CONTACT实体类
 * <b>功能：</b>ContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 11:10:05<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Contact {

	//属性部分
	private int id; //ID
	private String loanId; //申请ID
	private String name; //姓名
	private String relation; //关系
	private String company; //单位
	private String tel; //联系方式
	private String source; //来源
	private String state; //状态

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getName () {
		return this.name;
	}
	public String getRelation () {
		return this.relation;
	}
	public String getCompany () {
		return this.company;
	}
	public String getTel () {
		return this.tel;
	}
	public String getSource () {
		return this.source;
	}
	public String getState () {
		return this.state;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setRelation (String relation) {
		this.relation=relation;
	}
	public void setCompany (String company) {
		this.company=company;
	}
	public void setTel (String tel) {
		this.tel=tel;
	}
	public void setSource (String source) {
		this.source=source;
	}
	public void setState (String state) {
		this.state=state;
	}

}

