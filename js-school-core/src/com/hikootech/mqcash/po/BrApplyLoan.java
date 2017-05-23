package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName BrApplyLoan 
* @Description 百融PO
* @author 余巍 yuweiqwe@126.com 
* @date 2016年5月25日 下午5:05:49 
*  
*/
public class BrApplyLoan implements Serializable {

	private String id;
	private String brReqId;
	private String code;
	private String swiftNumber;
	private Integer m12IdBankAllnum;
	private Integer m12IdBankSelfnum;
	private Integer m12IdBankOrgnum;
	private Integer m12IdNotbankAllnum;
	private Integer m12IdNotbankSelfnum;
	private Integer m12IdNotbankOrgnum;
	
	private Integer m6IdBankAllnum;
	private Integer m6IdBankSelfnum;
	private Integer m6IdBankOrgnum;
	private Integer m6IdNotbankAllnum;
	private Integer m6IdNotbankSelfnum;
	private Integer m6IdNotbankOrgnum;
	
	private Integer m3IdBankAllnum;
	private Integer m3IdBankSelfnum;
	private Integer m3IdBankOrgnum;
	private Integer m3IdNotbankAllnum;
	private Integer m3IdNotbankSelfnum;
	private Integer m3IdNotbankOrgnum;
	
	private Date createTime;
	
	public BrApplyLoan() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrReqId() {
		return brReqId;
	}

	public void setBrReqId(String brReqId) {
		this.brReqId = brReqId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSwiftNumber() {
		return swiftNumber;
	}

	public void setSwiftNumber(String swiftNumber) {
		this.swiftNumber = swiftNumber;
	}

	public Integer getM12IdBankAllnum() {
		return m12IdBankAllnum;
	}

	public void setM12IdBankAllnum(Integer m12IdBankAllnum) {
		this.m12IdBankAllnum = m12IdBankAllnum;
	}

	public Integer getM12IdBankSelfnum() {
		return m12IdBankSelfnum;
	}

	public void setM12IdBankSelfnum(Integer m12IdBankSelfnum) {
		this.m12IdBankSelfnum = m12IdBankSelfnum;
	}

	public Integer getM12IdBankOrgnum() {
		return m12IdBankOrgnum;
	}

	public void setM12IdBankOrgnum(Integer m12IdBankOrgnum) {
		this.m12IdBankOrgnum = m12IdBankOrgnum;
	}

	public Integer getM12IdNotbankAllnum() {
		return m12IdNotbankAllnum;
	}

	public void setM12IdNotbankAllnum(Integer m12IdNotbankAllnum) {
		this.m12IdNotbankAllnum = m12IdNotbankAllnum;
	}

	public Integer getM12IdNotbankSelfnum() {
		return m12IdNotbankSelfnum;
	}

	public void setM12IdNotbankSelfnum(Integer m12IdNotbankSelfnum) {
		this.m12IdNotbankSelfnum = m12IdNotbankSelfnum;
	}

	public Integer getM12IdNotbankOrgnum() {
		return m12IdNotbankOrgnum;
	}

	public void setM12IdNotbankOrgnum(Integer m12IdNotbankOrgnum) {
		this.m12IdNotbankOrgnum = m12IdNotbankOrgnum;
	}

	public Integer getM6IdBankAllnum() {
		return m6IdBankAllnum;
	}

	public void setM6IdBankAllnum(Integer m6IdBankAllnum) {
		this.m6IdBankAllnum = m6IdBankAllnum;
	}

	public Integer getM6IdBankSelfnum() {
		return m6IdBankSelfnum;
	}

	public void setM6IdBankSelfnum(Integer m6IdBankSelfnum) {
		this.m6IdBankSelfnum = m6IdBankSelfnum;
	}

	public Integer getM6IdBankOrgnum() {
		return m6IdBankOrgnum;
	}

	public void setM6IdBankOrgnum(Integer m6IdBankOrgnum) {
		this.m6IdBankOrgnum = m6IdBankOrgnum;
	}

	public Integer getM6IdNotbankAllnum() {
		return m6IdNotbankAllnum;
	}

	public void setM6IdNotbankAllnum(Integer m6IdNotbankAllnum) {
		this.m6IdNotbankAllnum = m6IdNotbankAllnum;
	}

	public Integer getM6IdNotbankSelfnum() {
		return m6IdNotbankSelfnum;
	}

	public void setM6IdNotbankSelfnum(Integer m6IdNotbankSelfnum) {
		this.m6IdNotbankSelfnum = m6IdNotbankSelfnum;
	}

	public Integer getM6IdNotbankOrgnum() {
		return m6IdNotbankOrgnum;
	}

	public void setM6IdNotbankOrgnum(Integer m6IdNotbankOrgnum) {
		this.m6IdNotbankOrgnum = m6IdNotbankOrgnum;
	}

	public Integer getM3IdBankAllnum() {
		return m3IdBankAllnum;
	}

	public void setM3IdBankAllnum(Integer m3IdBankAllnum) {
		this.m3IdBankAllnum = m3IdBankAllnum;
	}

	public Integer getM3IdBankSelfnum() {
		return m3IdBankSelfnum;
	}

	public void setM3IdBankSelfnum(Integer m3IdBankSelfnum) {
		this.m3IdBankSelfnum = m3IdBankSelfnum;
	}

	public Integer getM3IdBankOrgnum() {
		return m3IdBankOrgnum;
	}

	public void setM3IdBankOrgnum(Integer m3IdBankOrgnum) {
		this.m3IdBankOrgnum = m3IdBankOrgnum;
	}

	public Integer getM3IdNotbankAllnum() {
		return m3IdNotbankAllnum;
	}

	public void setM3IdNotbankAllnum(Integer m3IdNotbankAllnum) {
		this.m3IdNotbankAllnum = m3IdNotbankAllnum;
	}

	public Integer getM3IdNotbankSelfnum() {
		return m3IdNotbankSelfnum;
	}

	public void setM3IdNotbankSelfnum(Integer m3IdNotbankSelfnum) {
		this.m3IdNotbankSelfnum = m3IdNotbankSelfnum;
	}

	public Integer getM3IdNotbankOrgnum() {
		return m3IdNotbankOrgnum;
	}

	public void setM3IdNotbankOrgnum(Integer m3IdNotbankOrgnum) {
		this.m3IdNotbankOrgnum = m3IdNotbankOrgnum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
