package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信8004查询发送业务信息子项
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:13:20
	*/
	
public class BusiDataItemRspQhMSC8005 {
	/*idNo	证件号码	String	64	R	　
	idType	证件类型	String	2	R	　
	name	主体名称	String	128	R	　
	mobileNo	手机号码	String	24	R	　
	seqNo	序列号	String	24	R	子批次号，本批次内唯一
	sourceId	来源代码	String	2	O	1 - 金融机构
	credooScore	可度分	String	16	O	 -1：权限不足，其他为评分值
	bseInfoScore	个人信息评分	String	16	O	 -1：权限不足，其他为评分值
	finRequireScore	金融需求评分	String	16	O	 -1：权限不足，其他为评分值
	payAbilityScore	还款能力评分	String	16	O	 -1：权限不足，其他为评分值
	performScore	信用履约评分	String	16	O	 -1：权限不足，其他为评分值
	actionScore	行为数据评分	String	16	O	 -1：权限不足，其他为评分值
	virAssetScore	虚拟资产评分	String	16	O	 -1：权限不足，其他为评分值
	trendScore	成长性评分	String	16	O	 -1：权限不足，其他为评分值
	dataBuildTime	查询时间	String	24	O	yyyy-MM-dd  99 - 权限不足
	erCode	错误代码	String	8	R	E+{六位数字} E000000:成功   其他失败
	erMsg	错误信息	String	256	O	　*/
	
	private   String idNo;
	private   String idType; //0:身份证1:户口簿  2:护照 3:军官证 4:士兵证 5:港澳居民来往内地通行证 6:台湾同胞来往内地通行证 7:临时身份证 8:外国人居留证 9:警官证 X:其他证件
	private   String name;
	private   String mobileNo;
	private   String seqNo;
	private   String sourceId;
	private   String credooScore;
	private   String bseInfoScore;
	private   String finRequireScore;
	private   String payAbilityScore;
	private   String performScore;
	private   String actionScore;
	private   String virAssetScore;
	private   String trendScore;
	private   String dataBuildTime; //yyyy-MM-dd
	private   String erCode;
	private   String erMsg;
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
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getCredooScore() {
		return credooScore;
	}
	public void setCredooScore(String credooScore) {
		this.credooScore = credooScore;
	}
	public String getBseInfoScore() {
		return bseInfoScore;
	}
	public void setBseInfoScore(String bseInfoScore) {
		this.bseInfoScore = bseInfoScore;
	}
	public String getFinRequireScore() {
		return finRequireScore;
	}
	public void setFinRequireScore(String finRequireScore) {
		this.finRequireScore = finRequireScore;
	}
	public String getPayAbilityScore() {
		return payAbilityScore;
	}
	public void setPayAbilityScore(String payAbilityScore) {
		this.payAbilityScore = payAbilityScore;
	}
	public String getPerformScore() {
		return performScore;
	}
	public void setPerformScore(String performScore) {
		this.performScore = performScore;
	}
	public String getActionScore() {
		return actionScore;
	}
	public void setActionScore(String actionScore) {
		this.actionScore = actionScore;
	}
	public String getVirAssetScore() {
		return virAssetScore;
	}
	public void setVirAssetScore(String virAssetScore) {
		this.virAssetScore = virAssetScore;
	}
	public String getTrendScore() {
		return trendScore;
	}
	public void setTrendScore(String trendScore) {
		this.trendScore = trendScore;
	}
	public String getDataBuildTime() {
		return dataBuildTime;
	}
	public void setDataBuildTime(String dataBuildTime) {
		this.dataBuildTime = dataBuildTime;
	}
	public String getErCode() {
		return erCode;
	}
	public void setErCode(String erCode) {
		this.erCode = erCode;
	}
	public String getErMsg() {
		return erMsg;
	}
	public void setErMsg(String erMsg) {
		this.erMsg = erMsg;
	}
	
}
