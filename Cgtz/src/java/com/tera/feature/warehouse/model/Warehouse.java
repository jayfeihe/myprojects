package com.tera.feature.warehouse.model;



import com.tera.util.DateUtils;

/**
 * 
 * T_WAREHOUSE实体类
 * <b>功能：</b>WarehouseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Warehouse {
	//属性部分
	private int id; //ID
	private String name; //仓库名称
	private String prvn; //所在省
	private String city; //所在市
	private String ctry; //所在县
	private String addr; //所在地址
	private String owner; //负责人
	private String tel; //联系方式
	private String remark; //说明
	private String state; //状态
	private String org; //所属机构
	private String orgName;
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getName () {
		return this.name;
	}
	public String getPrvn () {
		return this.prvn;
	}
	public String getCity () {
		return this.city;
	}
	public String getCtry () {
		return this.ctry;
	}
	public String getAddr () {
		return this.addr;
	}
	public String getOwner () {
		return this.owner;
	}
	public String getTel () {
		return this.tel;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getState () {
		return this.state;
	}
	public String getOrg () {
		return this.org;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateUid () {
		return this.updateUid;
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
	public void setName (String name) {
		this.name=name;
	}
	public void setPrvn (String prvn) {
		this.prvn=prvn;
	}
	public void setCity (String city) {
		this.city=city;
	}
	public void setCtry (String ctry) {
		this.ctry=ctry;
	}
	public void setAddr (String addr) {
		this.addr=addr;
	}
	public void setOwner (String owner) {
		this.owner=owner;
	}
	public void setTel (String tel) {
		this.tel=tel;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setOrg (String org) {
		this.org=org;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

