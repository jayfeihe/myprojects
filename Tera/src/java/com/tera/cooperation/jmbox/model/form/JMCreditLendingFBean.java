package com.tera.cooperation.jmbox.model.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
public class JMCreditLendingFBean {

	private String 	Type; //产品类型;单位：50
	private int 	FinancingAmount; //意向融资金额（元）;单位：元
	private int 	Batch; //贷款期限（月）;单位：月
	private String 	DebtUsage; //贷款用途;单位：45
	private String 	RepaymentSource; //还款来源;单位：50；取值：固定值 ：工作所得
	private String 	ChineseName; //姓名;单位：20
	private int 	Age; //年龄;取值：根据身份证计算年龄
	private String 	Sex; //性别;取值：根据身份证分析性别；备注：男/女
	private String 	Phone; //手机号码;单位：20
	private String 	IdentityNumber; //身份证号;单位：18
	private String 	PostAddress; //通讯地址;单位：100；取值：居住地址
	private String 	AccountLocation; //户籍地址;单位：100
	private String 	CurrentAddressLiveTime; //现地址居住时间（年）;单位：20
	private String 	MaritalStatus; //婚姻状况;备注：婚姻状况
	private String 	SpouseName; //配偶姓名;单位：20
	private String 	SpouseID; //配偶身份证号;单位：18
	private String 	SpousePhone; //配偶手机号码;单位：20
	private String 	SpouseWorkUnit; //配偶工作单位名称;单位：50
	private String 	CompanyName; //工作单位;单位：100；取值：本人公司信息
	private String 	CompanyNature; //单位性质;
	private String 	CompanyTel; //工作单位座机;取值：单位电话
	private String 	CompanyAddress; //单位地址;单位：100
	private Date 	WorkStartDate; //本工作开始日期;单位：年-月-日；取值：入职时间
	private String 	Position; //职务;单位：50；备注：职务
	private int		WorkYears; //总工龄;单位：年；取值：根据入职时间计算
	private String 	HasBadRecord; //是否有诉讼记录;取值：取 重要情况说明；备注：有/无
	private String 	MainBusinessDescrib; //主营业务描述;单位：100；取值：业主贷 
	private int 	FoundYears; //成立年限;取值：进件时间 减去 本地经营时间 求年份
	private double 	ShareHolderRate; //融资人持股比例;取值：占股比例

	private String AccountNo; //开户银行账号;单位：50
	private String BankName; //开户银行名称;单位：50
	private String Provence; //开户银行省份;单位：50
	private String Area; //开户银行地区;单位：50
	private String SubbranchBank; //开户银行支行;单位：50
	private String ApplyCity; //贷款提交城市;单位：50
	private String UserName; //融资用户名;单位：50，备注：可以不填，由积木盒子生产
	private int LenderAmount; //融资人实收金额;单位：元
	private int RepaymentAmountByMonth; //月偿还本息数额;单位：元

	private String SPID;
	//2015-03-18新增字段
	private String ThirdPartyScore;//信用等级评分等级
	//2015-05-25新增字段
	private String HasCar;//有无车产，暂时默认无
	private String HasHouse;//有无房产，暂时默认无
	private int CheckedAmount;//经核实月收入


	/**
	 * 得到 当前类的所有的属性的名字 并且 升序排序
	 * @return
	 */
	public List<String> getPropertyList(){
		List<String> nameList=new ArrayList<String>();
		Class cls =this.getClass();  //com.geocompass.model.STSTBPRPModel
		Field[] fieldlist = cls.getDeclaredFields();
		for (int i = 0; i < fieldlist.length; i++) {
			Field fld = fieldlist[i];
			nameList.add(fld.getName());
		}
		Collections.sort(nameList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				o1=o1.toUpperCase();
				o2=o2.toUpperCase();
				return o1.compareTo(o2);
			}
		});
		return nameList;
	}


	public String getSPID() {
		return SPID;
	}
	public void setSPID(String sPID) {
		SPID = sPID;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public int getFinancingAmount() {
		return FinancingAmount;
	}
	public void setFinancingAmount(int financingAmount) {
		FinancingAmount = financingAmount;
	}
	public int getBatch() {
		return Batch;
	}
	public void setBatch(int batch) {
		Batch = batch;
	}
	public String getDebtUsage() {
		return DebtUsage;
	}
	public void setDebtUsage(String debtUsage) {
		DebtUsage = debtUsage;
	}
	public String getRepaymentSource() {
		return RepaymentSource;
	}
	public void setRepaymentSource(String repaymentSource) {
		RepaymentSource = repaymentSource;
	}
	public String getChineseName() {
		return ChineseName;
	}
	public void setChineseName(String chineseName) {
		ChineseName = chineseName;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getIdentityNumber() {
		return IdentityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		IdentityNumber = identityNumber;
	}
	public String getPostAddress() {
		return PostAddress;
	}
	public void setPostAddress(String postAddress) {
		PostAddress = postAddress;
	}
	public String getAccountLocation() {
		return AccountLocation;
	}
	public void setAccountLocation(String accountLocation) {
		AccountLocation = accountLocation;
	}
	public String getCurrentAddressLiveTime() {
		return CurrentAddressLiveTime;
	}
	public void setCurrentAddressLiveTime(String currentAddressLiveTime) {
		CurrentAddressLiveTime = currentAddressLiveTime;
	}
	public String getMaritalStatus() {
		return MaritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		MaritalStatus = maritalStatus;
	}
	public String getSpouseName() {
		return SpouseName;
	}
	public void setSpouseName(String spouseName) {
		SpouseName = spouseName;
	}
	public String getSpouseID() {
		return SpouseID;
	}
	public void setSpouseID(String spouseID) {
		SpouseID = spouseID;
	}
	public String getSpousePhone() {
		return SpousePhone;
	}
	public void setSpousePhone(String spousePhone) {
		SpousePhone = spousePhone;
	}
	public String getSpouseWorkUnit() {
		return SpouseWorkUnit;
	}
	public void setSpouseWorkUnit(String spouseWorkUnit) {
		SpouseWorkUnit = spouseWorkUnit;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getCompanyNature() {
		return CompanyNature;
	}
	public void setCompanyNature(String companyNature) {
		CompanyNature = companyNature;
	}
	public String getCompanyTel() {
		return CompanyTel;
	}
	public void setCompanyTel(String companyTel) {
		CompanyTel = companyTel;
	}
	public String getCompanyAddress() {
		return CompanyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		CompanyAddress = companyAddress;
	}
	public Date getWorkStartDate() {
		return WorkStartDate;
	}
	public void setWorkStartDate(Date workStartDate) {
		WorkStartDate = workStartDate;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public int getWorkYears() {
		return WorkYears;
	}
	public void setWorkYears(int workYears) {
		WorkYears = workYears;
	}
	public String getHasBadRecord() {
		return HasBadRecord;
	}
	public void setHasBadRecord(String hasBadRecord) {
		HasBadRecord = hasBadRecord;
	}
	public String getMainBusinessDescrib() {
		return MainBusinessDescrib;
	}
	public void setMainBusinessDescrib(String mainBusinessDescrib) {
		MainBusinessDescrib = mainBusinessDescrib;
	}
	public int getFoundYears() {
		return FoundYears;
	}
	public void setFoundYears(int foundYears) {
		FoundYears = foundYears;
	}
	public double getShareHolderRate() {
		return ShareHolderRate;
	}
	public void setShareHolderRate(double shareHolderRate) {
		ShareHolderRate = shareHolderRate;
	}
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getProvence() {
		return Provence;
	}
	public void setProvence(String provence) {
		Provence = provence;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getSubbranchBank() {
		return SubbranchBank;
	}
	public void setSubbranchBank(String subbranchBank) {
		SubbranchBank = subbranchBank;
	}
	public String getApplyCity() {
		return ApplyCity;
	}
	public void setApplyCity(String applyCity) {
		ApplyCity = applyCity;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public int getLenderAmount() {
		return LenderAmount;
	}
	public void setLenderAmount(int lenderAmount) {
		LenderAmount = lenderAmount;
	}
	public int getRepaymentAmountByMonth() {
		return RepaymentAmountByMonth;
	}
	public void setRepaymentAmountByMonth(int repaymentAmountByMonth) {
		RepaymentAmountByMonth = repaymentAmountByMonth;
	}
	public String getThirdPartyScore() {
		return ThirdPartyScore;
	}
	public void setThirdPartyScore(String thirdPartyScore) {
		ThirdPartyScore = thirdPartyScore;
	}
	public String getHasCar() {
		return HasCar;
	}
	public void setHasCar(String hasCar) {
		HasCar = hasCar;
	}
	public String getHasHouse() {
		return HasHouse;
	}
	public void setHasHouse(String hasHouse) {
		HasHouse = hasHouse;
	}
	public int getCheckedAmount() {
		return CheckedAmount;
	}
	public void setCheckedAmount(int checkedAmount) {
		CheckedAmount = checkedAmount;
	}
}
