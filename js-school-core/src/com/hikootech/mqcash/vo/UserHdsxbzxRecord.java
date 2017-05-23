package com.hikootech.mqcash.vo;

import java.util.Date;

//个人失信被执行查询

public class UserHdsxbzxRecord {

	private long sxggId;// 失信公告ID (不为空命中)
	private String court;// 执行法院名称
	private String province;// 省份
	private String caseNo;// 案号(不为空命中)
	private Date sortTime;// 立案时间
	private String pname;// 被执行人姓名
	private String idcardNo;// 身份证/组织机构代码 (不为空命中)
	private String age;// 年龄
	private String sex;// 性别
	private String yjCode;// 执行依据文号
	private String yiwu;// 生效法律文书确定的义务
	private String lxqk;// 被执行人的履行情况
	private String jtqx;// 失信被执行人行为具体情形
	private String yjdw;// 做出执行依据单位
	private String postTime;// 发布时间
	private String body;// 概要信息


	public long getSxggId() {
		return sxggId;
	}

	public void setSxggId(long sxggId) {
		this.sxggId = sxggId;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public Date getSortTime() {
		return sortTime;
	}

	public void setSortTime(Date sortTime) {
		this.sortTime = sortTime;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getYjCode() {
		return yjCode;
	}

	public void setYjCode(String yjCode) {
		this.yjCode = yjCode;
	}

	public String getYiwu() {
		return yiwu;
	}

	public void setYiwu(String yiwu) {
		this.yiwu = yiwu;
	}

	public String getLxqk() {
		return lxqk;
	}

	public void setLxqk(String lxqk) {
		this.lxqk = lxqk;
	}

	public String getJtqx() {
		return jtqx;
	}

	public void setJtqx(String jtqx) {
		this.jtqx = jtqx;
	}

	public String getYjdw() {
		return yjdw;
	}

	

	public void setYjdw(String yjdw) {
		this.yjdw = yjdw;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
