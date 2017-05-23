package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信8004查询发送业务信息子项
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:13:20
	*/
	
public class BusiDataItemReqQhMSC8004ActiveReport {

	
	/*idNo	证件号码	String	64	必填	　
idType	证件类型	String	1	必填	详见附录二
name	主体名称	String	64	必填	　
money	金额	String	24	必填	0：无 整数：金额大小
cardNo	卡号	String	24	选填	可为空
qqNo	qq号	String	24	选填	可为空
phoneNo	手机号	String	16	必填	　
currency	币种	String	3	必填	采用根据GB/T 12406-1996编制的三位字母型代码，详见附录四
gradeReport	报送严重等级	String	2	必填	3 -逾期31-60天  5 -逾期61-90天 6 -逾期91-180天  7 -违约         
updatedDate	业务发生时间	String	24	必填	yyyy-MM-dd HH:mm:ss
seqNo	序列号	String	8	必填	子批次号，同一批次唯一*/
	
	private   String idNo;
	private   String idType;
	private   String name;
	private   String money;
	private   String cardNo;
	private   String qqNo;
	private   String phoneNo;
	private   String currency;
	private   String gradeReport;
	private   String updatedDate;
	private   String seqNo;
	/**  证件号码	String	64   */
	public String getIdNo() {
		return idNo;
	}
	/**  证件类型	String	1 : 
	 *  0-身份证；
	*	1-户口簿；
	*	2-护照；
	*	3-军官证；
	*	4-士兵证；
	*	5-港澳居民来往内地通行证；
	*	6-台湾同胞来往内地通行证；
	*	7-临时身份证；
	*	8-外国人居留证；
	*	9-警官证；
	*X-其他证件
	 *  
	 *  */
	public String getIdType() {
		return idType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getQqNo() {
		return qqNo;
	}
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getGradeReport() {
		return gradeReport;
	}
	public void setGradeReport(String gradeReport) {
		this.gradeReport = gradeReport;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}

}
