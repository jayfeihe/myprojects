package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信8004查询发送业务信息子项
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:13:20
	*/
	
public class BusiDataItemRspQhMSC8017 {
	
	private   String idNo;
	private   String idType; //0:身份证1:户口簿  2:护照 3:军官证 4:士兵证 5:港澳居民来往内地通行证 6:台湾同胞来往内地通行证 7:临时身份证 8:外国人居留证 9:警官证 X:其他证件
	private   String name;
	private   String mobileNo;
	private   String seqNo;
	
	
	private   String isRealIdentity;
	private   String isValidAddress;
	private   String addressType;
	private   String isRealCompany;
	private   String appear1ThDate;
	
	
	private   String isOwnerMobile;
	private   String useTimeScore;
	private   String isExistRel; //yyyy-MM-dd
	private   String photoCmpResult;
	private   String photoSimDeg;
	private   String carChkResult;
	private   String houseChkResult;
	private   String relLevel;
	private   String errorInfo;
	
	
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getIsRealIdentity() {
		return isRealIdentity;
	}
	public void setIsRealIdentity(String isRealIdentity) {
		this.isRealIdentity = isRealIdentity;
	}
	public String getIsValidAddress() {
		return isValidAddress;
	}
	public void setIsValidAddress(String isValidAddress) {
		this.isValidAddress = isValidAddress;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getIsRealCompany() {
		return isRealCompany;
	}
	public void setIsRealCompany(String isRealCompany) {
		this.isRealCompany = isRealCompany;
	}
	public String getAppear1ThDate() {
		return appear1ThDate;
	}
	public void setAppear1ThDate(String appear1ThDate) {
		this.appear1ThDate = appear1ThDate;
	}
	public String getIsOwnerMobile() {
		return isOwnerMobile;
	}
	public void setIsOwnerMobile(String isOwnerMobile) {
		this.isOwnerMobile = isOwnerMobile;
	}
	public String getUseTimeScore() {
		return useTimeScore;
	}
	public void setUseTimeScore(String useTimeScore) {
		this.useTimeScore = useTimeScore;
	}
	public String getIsExistRel() {
		return isExistRel;
	}
	public void setIsExistRel(String isExistRel) {
		this.isExistRel = isExistRel;
	}
	public String getPhotoCmpResult() {
		return photoCmpResult;
	}
	public void setPhotoCmpResult(String photoCmpResult) {
		this.photoCmpResult = photoCmpResult;
	}
	public String getPhotoSimDeg() {
		return photoSimDeg;
	}
	public void setPhotoSimDeg(String photoSimDeg) {
		this.photoSimDeg = photoSimDeg;
	}
	public String getCarChkResult() {
		return carChkResult;
	}
	public void setCarChkResult(String carChkResult) {
		this.carChkResult = carChkResult;
	}
	public String getHouseChkResult() {
		return houseChkResult;
	}
	public void setHouseChkResult(String houseChkResult) {
		this.houseChkResult = houseChkResult;
	}
	public String getRelLevel() {
		return relLevel;
	}
	public void setRelLevel(String relLevel) {
		this.relLevel = relLevel;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
