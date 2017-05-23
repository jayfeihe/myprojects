package com.hikootech.mqcash.vo;

import java.util.Date;
import java.util.List;

import com.hikootech.mqcash.po.UserHdBxdjgcxjl;
import com.hikootech.mqcash.po.UserHdDkcfsq;
import com.hikootech.mqcash.po.UserHdGrbzxggcx;
import com.hikootech.mqcash.po.UserHdShanyinSyhmd;
import com.hikootech.mqcash.po.UserHdSxbzxrcx;

public class HaoDaiVo {

	private String recordId;// 记录id
	private String queryType;// 查询类型
	private String name;// 姓名
	private String idCard;// 身份证号
	private String mobile;// 手机号
	private String date;// 数据
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private String operator;// 操作者
	
	/** 
	* 个人失信被执行查询
	*/ 
	private List<UserHdSxbzxrcx>  userHdSxbzxrcxList ;
	/** 
	* 个人被执行公告查询
	*/ 
	private List<UserHdGrbzxggcx>  userHdGrbzxggcxList ;
	/** 
	*借款重复查询次数
	*/ 
	private List<UserHdDkcfsq>  userHdDkcfsqList ;
	/** 
	* 黑名单查询
	*/ 
	private List<UserHdShanyinSyhmd>  userHdShanyinSyhmdList ;
	/** 
	* 被信贷机构查询次数
	*/ 
	private List<UserHdBxdjgcxjl>  userHdBxdjgcxjlList ;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public List<UserHdSxbzxrcx> getUserHdSxbzxrcxList() {
		return userHdSxbzxrcxList;
	}

	public void setUserHdSxbzxrcxList(List<UserHdSxbzxrcx> userHdSxbzxrcxList) {
		this.userHdSxbzxrcxList = userHdSxbzxrcxList;
	}

	public List<UserHdGrbzxggcx> getUserHdGrbzxggcxList() {
		return userHdGrbzxggcxList;
	}

	public void setUserHdGrbzxggcxList(List<UserHdGrbzxggcx> userHdGrbzxggcxList) {
		this.userHdGrbzxggcxList = userHdGrbzxggcxList;
	}

	public List<UserHdDkcfsq> getUserHdDkcfsqList() {
		return userHdDkcfsqList;
	}

	public void setUserHdDkcfsqList(List<UserHdDkcfsq> userHdDkcfsqList) {
		this.userHdDkcfsqList = userHdDkcfsqList;
	}

	public List<UserHdShanyinSyhmd> getUserHdShanyinSyhmdList() {
		return userHdShanyinSyhmdList;
	}

	public void setUserHdShanyinSyhmdList(
			List<UserHdShanyinSyhmd> userHdShanyinSyhmdList) {
		this.userHdShanyinSyhmdList = userHdShanyinSyhmdList;
	}

	public List<UserHdBxdjgcxjl> getUserHdBxdjgcxjlList() {
		return userHdBxdjgcxjlList;
	}

	public void setUserHdBxdjgcxjlList(List<UserHdBxdjgcxjl> userHdBxdjgcxjlList) {
		this.userHdBxdjgcxjlList = userHdBxdjgcxjlList;
	}


	
	

}
