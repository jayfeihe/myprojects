package com.hikootech.mqcash.po;

import java.util.Date;

public class UserSchoolRoll{
	
	private String id;   	       //学籍查询id
	private String requestNo;     //分期是业务申请表ID
	private String documentNo;    //身份证号
	private String name; 		   //用户真实姓名
	private String collegeLevel;  //学历层次
	private String college;        //学校名称
	private String startTime;     //入学年份 格式：YYYY
	private String documentNoCheckResult; //身份证是否一致
	private String nameCheckResult;           //姓名是否一致
	private String collegeLevelCheckResult;  //学历层次是否一致
	private String collegeCheckResult; 		//学校名称是否一致
	private String startTimeCheckResult;	 //入学年份是否一致
	private String status;  				//tinyint(3)状态：10查询成功 20查询失败
	private Date createTime;  				//datetime
	private String descp;   				//(响应结果码
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCollegeLevel() {
		return collegeLevel;
	}
	public void setCollegeLevel(String collegeLevel) {
		this.collegeLevel = collegeLevel;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getDocumentNoCheckResult() {
		return documentNoCheckResult;
	}
	public void setDocumentNoCheckResult(String documentNoCheckResult) {
		this.documentNoCheckResult = documentNoCheckResult;
	}
	public String getNameCheckResult() {
		return nameCheckResult;
	}
	public void setNameCheckResult(String nameCheckResult) {
		this.nameCheckResult = nameCheckResult;
	}
	public String getCollegeLevelCheckResult() {
		return collegeLevelCheckResult;
	}
	public void setCollegeLevelCheckResult(String collegeLevelCheckResult) {
		this.collegeLevelCheckResult = collegeLevelCheckResult;
	}
	public String getCollegeCheckResult() {
		return collegeCheckResult;
	}
	public void setCollegeCheckResult(String collegeCheckResult) {
		this.collegeCheckResult = collegeCheckResult;
	}
	public String getStartTimeCheckResult() {
		return startTimeCheckResult;
	}
	public void setStartTimeCheckResult(String startTimeCheckResult) {
		this.startTimeCheckResult = startTimeCheckResult;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	
	
	
}
