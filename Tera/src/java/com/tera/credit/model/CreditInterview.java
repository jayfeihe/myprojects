package com.tera.credit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款面审调查表实体类
 * <b>功能：</b>CreditInterviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:54:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CreditInterview {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private int isAdd; //是否新增
	private String type; //类型   本人手机:01;家庭固话:02;单位电话:03;常用联系人:04;
	private String relation; //关系
	private String source; //来源
	private String name; //名字
	private String phone; //号码
	private String surveyState; //调查状态   未调查:01,调查中:02,调查成功:03,调查失败:04
	private String surveyFlag;  //调查标志      正常:01,异常:02
	private String comName; //单位名称
	private int comNameFlag; //单位名称是否一致
	private String comAddress; //单位地址
	private int comAddressFlag; //单位地址是否一致
	private int isJob; //客户是否在职
	private String address; //居住地址
	private int addressFlag; //居住地址是否一致
	private String tel; //家庭固话
	private int telFlag; //家庭固话是否一致
	private int period; //期限
	private double monthlyPayments; //月还款额
	private double amount; //借款金额
	private String useage1; //借款用途1
	private String useage2; //借款用途2
	private String industry1; //行业代码1
	private String industry2; //行业代码2
	private String industry3; //行业代码3
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//电话拨打记录
	private List<CreditCallLog> callLogList=new ArrayList<CreditCallLog>();
	
	private String pateTitle;
	
	
	public String getPateTitle() {
		pateTitle="";
		Map<String,String> typeMap=new HashMap<String, String>();
		typeMap.put("01","本人手机");
		typeMap.put("02","家庭固话");
		typeMap.put("03","单位电话");
		typeMap.put("04","常用联系人");
		typeMap.put("05","配偶手机");
		Map<String,String> relationMap=new HashMap<String, String>();
		relationMap.put("1", "父母");
		relationMap.put("2", "配偶");
		relationMap.put("3", "子女");
		relationMap.put("4", "亲属");
		relationMap.put("5", "朋友");
		relationMap.put("6", "同事");
		relationMap.put("7", "同学");
		relationMap.put("99", "其他");
		
		Map<String,String> surveyStateMap=new HashMap<String, String>();
		surveyStateMap.put("01","未调查");
		surveyStateMap.put("02","调查中");
		surveyStateMap.put("03","调查成功");
		surveyStateMap.put("04","调查失败");
		Map<String,String> surveyFlagMap=new HashMap<String, String>();
		surveyFlagMap.put("01","正常");
		surveyFlagMap.put("02","异常");

		pateTitle+=typeMap.get(this.getType());			//类型
		pateTitle+=" "+this.name+":"+this.getPhone();	//名字 与号码
		if("04".equals(this.getType())&&this.getRelation()!=null&&!"".equals(this.getRelation())){
			pateTitle+="("+relationMap.get(this.getRelation())+")";		//如果是常用联系人 应该有关系
		}
		pateTitle+="——"+surveyStateMap.get(this.getSurveyState());	//调查状态
		pateTitle+="("+surveyFlagMap.get(this.getSurveyFlag())+")";		//结果状态
		if(isAdd==1){
			pateTitle+="（添）";
		}
		return pateTitle;
	}
	public void setPateTitle(String pateTitle) {
		this.pateTitle = pateTitle;
	}

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public int getIsAdd () {
		return this.isAdd;
	}
	public String getType () {
		return this.type;
	}
	public String getRelation () {
		return this.relation;
	}
	public String getSource () {
		return this.source;
	}
	public String getName () {
		return this.name;
	}
	public String getPhone () {
		return this.phone;
	}
	public String getSurveyState () {
		return this.surveyState;
	}
	public String getSurveyFlag () {
		return this.surveyFlag;
	}
	public String getComName () {
		return this.comName;
	}
	public int getComNameFlag () {
		return this.comNameFlag;
	}
	public String getComAddress () {
		return this.comAddress;
	}
	public int getComAddressFlag () {
		return this.comAddressFlag;
	}
	public int getIsJob () {
		return this.isJob;
	}
	public String getAddress () {
		return this.address;
	}
	public int getAddressFlag () {
		return this.addressFlag;
	}
	public String getTel () {
		return this.tel;
	}
	public int getTelFlag () {
		return this.telFlag;
	}
	public int getPeriod () {
		return this.period;
	}
	public double getMonthlyPayments () {
		return this.monthlyPayments;
	}
	public double getAmount () {
		return this.amount;
	}
	public String getUseage1 () {
		return this.useage1;
	}
	public String getUseage2 () {
		return this.useage2;
	}
	public String getIndustry1 () {
		return this.industry1;
	}
	public String getIndustry2 () {
		return this.industry2;
	}
	public String getIndustry3 () {
		return this.industry3;
	}
	public String getState () {
		return this.state;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
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
	public void setIsAdd (int isAdd) {
		this.isAdd=isAdd;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setRelation (String relation) {
		this.relation=relation;
	}
	public void setSource (String source) {
		this.source=source;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setPhone (String phone) {
		this.phone=phone;
	}
	public void setSurveyState (String surveyState) {
		this.surveyState=surveyState;
	}
	public void setSurveyFlag (String surveyFlag) {
		this.surveyFlag=surveyFlag;
	}
	public void setComName (String comName) {
		this.comName=comName;
	}
	public void setComNameFlag (int comNameFlag) {
		this.comNameFlag=comNameFlag;
	}
	public void setComAddress (String comAddress) {
		this.comAddress=comAddress;
	}
	public void setComAddressFlag (int comAddressFlag) {
		this.comAddressFlag=comAddressFlag;
	}
	public void setIsJob (int isJob) {
		this.isJob=isJob;
	}
	public void setAddress (String address) {
		this.address=address;
	}
	public void setAddressFlag (int addressFlag) {
		this.addressFlag=addressFlag;
	}
	public void setTel (String tel) {
		this.tel=tel;
	}
	public void setTelFlag (int telFlag) {
		this.telFlag=telFlag;
	}
	public void setPeriod (int period) {
		this.period=period;
	}
	public void setMonthlyPayments (double monthlyPayments) {
		this.monthlyPayments=monthlyPayments;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setUseage1 (String useage1) {
		this.useage1=useage1;
	}
	public void setUseage2 (String useage2) {
		this.useage2=useage2;
	}
	public void setIndustry1 (String industry1) {
		this.industry1=industry1;
	}
	public void setIndustry2 (String industry2) {
		this.industry2=industry2;
	}
	public void setIndustry3 (String industry3) {
		this.industry3=industry3;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
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
	public List<CreditCallLog> getCallLogList() {
		return callLogList;
	}
	public void setCallLogList(List<CreditCallLog> callLogList) {
		this.callLogList = callLogList;
	}
	
	
}

