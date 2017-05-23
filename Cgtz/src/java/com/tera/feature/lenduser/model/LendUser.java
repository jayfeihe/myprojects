package com.tera.feature.lenduser.model;



import com.tera.util.DateUtils;

/**
 * 
 * 出借人基本信息维护实体类
 * <b>功能：</b>LendUserDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LendUser {

	//属性部分
	private int id; //ID
	private String name; //姓名
	private String gender; //性别
	private String idNo; //身份证号
	private String email; //EMAIL
	private String phone; //固定电话
	private String mobile; //手机
	private String homePrvn; //户籍所在省
	private String homeCity; //户籍所在市
	private String homeAddr; //户籍地址
	private String nowPrvn; //现居地所在省
	private String nowCity; //现居地所在市
	private String nowCtry; //现居地所在县
	private String nowAddr; //现局地地址
	private String homeCtry; //户籍所在县
	private String acctPrvn;//开户行所在省
	private String acctCity;//开户行所在市
	private String acctCtry;//开户行所在区/县
	private String acctBranch;//分行
	private String acctName; //开户名
	private String acctBank; //开户行
	private String acctCode; //收款账号
	private String remark; //说明
	private double amt;//出借人资金余额
	private String state;//出借状态
	private String ext1; //
	private String ext2; //
	private String realName;

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getName () {
		return this.name;
	}
	public String getGender () {
		return this.gender;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getEmail () {
		return this.email;
	}
	public String getPhone () {
		return this.phone;
	}
	public String getMobile () {
		return this.mobile;
	}
	public String getHomePrvn () {
		return this.homePrvn;
	}
	public String getHomeCity () {
		return this.homeCity;
	}
	public String getHomeAddr () {
		return this.homeAddr;
	}
	public String getNowPrvn () {
		return this.nowPrvn;
	}
	public String getNowCity () {
		return this.nowCity;
	}
	public String getNowCtry () {
		return this.nowCtry;
	}
	public String getNowAddr () {
		return this.nowAddr;
	}
	public String getHomeCtry () {
		return this.homeCtry;
	}
	public String getAcctName () {
		return this.acctName;
	}
	public String getAcctBank () {
		return this.acctBank;
	}
	public String getAcctCode () {
		return this.acctCode;
	}
	public String getRemark () {
		return this.remark;
	}
	public Double getAmt() {
		return amt;
	}
	public String getState() {
		return state;
	}
	public String getExt1 () {
		return this.ext1;
	}
	public String getExt2 () {
		return this.ext2;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public String getAcctPrvn() {
		return acctPrvn;
	}
	public String getAcctCity() {
		return acctCity;
	}
	public String getAcctCtry() {
		return acctCtry;
	}
	public String getAcctBranch() {
		return acctBranch;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setGender (String gender) {
		this.gender=gender;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setEmail (String email) {
		this.email=email;
	}
	public void setPhone (String phone) {
		this.phone=phone;
	}
	public void setMobile (String mobile) {
		this.mobile=mobile;
	}
	public void setHomePrvn (String homePrvn) {
		this.homePrvn=homePrvn;
	}
	public void setHomeCity (String homeCity) {
		this.homeCity=homeCity;
	}
	public void setHomeAddr (String homeAddr) {
		this.homeAddr=homeAddr;
	}
	public void setNowPrvn (String nowPrvn) {
		this.nowPrvn=nowPrvn;
	}
	public void setNowCity (String nowCity) {
		this.nowCity=nowCity;
	}
	public void setNowCtry (String nowCtry) {
		this.nowCtry=nowCtry;
	}
	public void setNowAddr (String nowAddr) {
		this.nowAddr=nowAddr;
	}
	public void setHomeCtry (String homeCtry) {
		this.homeCtry=homeCtry;
	}
	public void setAcctName (String acctName) {
		this.acctName=acctName;
	}
	public void setAcctBank (String acctBank) {
		this.acctBank=acctBank;
	}
	public void setAcctCode (String acctCode) {
		this.acctCode=acctCode;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setExt1 (String ext1) {
		this.ext1=ext1;
	}
	public void setExt2 (String ext2) {
		this.ext2=ext2;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setAcctPrvn(String acctPrvn) {
		this.acctPrvn = acctPrvn;
	}
	public void setAcctCity(String acctCity) {
		this.acctCity = acctCity;
	}
	public void setAcctCtry(String acctCtry) {
		this.acctCtry = acctCtry;
	}
	public void setAcctBranch(String acctBranch) {
		this.acctBranch = acctBranch;
	}

}

