package com.tera.cooperation.jmbox.model;

import com.tera.util.DateUtils;

/**
 * 
 * 积木盒子交互日志表实体类
 * <b>功能：</b>JmboxLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-11-13 15:53:59<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class JmboxLog {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private String contractNo; //合同号
	private String type; //类型	1,把产品发送到JM  2查询产品状态 3 推送违约信息
	private String jmProjectId; //融资项目ID
	private String jmChineseName; //融资客户名
	private String jmIdentityNumber; //融资客户身份证号码
	private String jmStatus; //结果状态
	private String jmMessage; //结果信息
	private String operator; //操作员
	private String orgId; //所属机构
	private String state; //状态   1成功  2失败
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getType () {
		return this.type;
	}
	public String getJmProjectId () {
		return this.jmProjectId;
	}
	public String getJmChineseName () {
		return this.jmChineseName;
	}
	public String getJmIdentityNumber () {
		return this.jmIdentityNumber;
	}
	public String getJmStatus () {
		return this.jmStatus;
	}
	public String getJmMessage () {
		return this.jmMessage;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getState () {
		return this.state;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setJmProjectId (String jmProjectId) {
		this.jmProjectId=jmProjectId;
	}
	public void setJmChineseName (String jmChineseName) {
		this.jmChineseName=jmChineseName;
	}
	public void setJmIdentityNumber (String jmIdentityNumber) {
		this.jmIdentityNumber=jmIdentityNumber;
	}
	public void setJmStatus (String jmStatus) {
		this.jmStatus=jmStatus;
	}
	public void setJmMessage (String jmMessage) {
		this.jmMessage=jmMessage;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

