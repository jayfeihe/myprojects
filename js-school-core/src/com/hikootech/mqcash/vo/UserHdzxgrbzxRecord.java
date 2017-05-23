package com.hikootech.mqcash.vo;
//个人被执行公告查询

import java.math.BigDecimal;

public class UserHdzxgrbzxRecord {

	private String body;
	private String title;
	private Integer status;
	private String pname;
	private String proposer;
	private String idcardNo;
	private String zxggId;
	private String code;
	private String msg;
	private String court; // 执行法院名称
	private String caseNo;// 案号
	private String sortTime;// 立案时间
	private BigDecimal execMoney;// 执行标的(单位:万元)
	private String caseState;// 案件状态

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getSortTime() {
		return sortTime;
	}

	public void setSortTime(String sortTime) {
		this.sortTime = sortTime;
	}

	public BigDecimal getExecMoney() {
		return execMoney;
	}

	public void setExecMoney(BigDecimal execMoney) {
		this.execMoney = execMoney;
	}

	public String getCaseState() {
		return caseState;
	}

	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getZxggId() {
		return zxggId;
	}

	public void setZxggId(String zxggId) {
		this.zxggId = zxggId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
