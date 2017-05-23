package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信8004查询发送业务信息子项
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:13:20
	*/
	
public class BusiDataItemRspQhMSC8004 {

	
	/*idNo	证件号码	String	64	必填	　
idType	证件类型	String	1	必填	详见附录二
name	主体名称	String	128	必填	　
seqNo	序列号	String	24	必填	子批次号，本批次内唯一
sourceId	来源代码	String	2	必填	1 - 金融机构
gradeQuery	查询严重等级	String	2	必填	99 - 权限不足  1 - 被执行人    3 -逾期31-60天  5 -逾期61-90天  6 -逾期91-180天  7 -违约       9 - 失信被执行人
moneyBound	金额范围	String	2	必填	99 ：权限不足  0 ：无 金额不明   1：0-1000  2：1000-5000  3：5000-20000  4：2W-10W  5：10W以上
dataBuildTime	业务发生时间	String	24	必填	yyyy-MM-dd HH:mm:ss 99 - 权限不足
dataStatus	数据状态	String	2	必填	99 - 权限不足 1 - 已验证 2 - 未验证
reservedFiled1	预留字段1	String	LLV…	选填	空 - 未启用
reservedFiled2	预留字段2	String	LLV…	选填	空 - 未启用
reservedFiled3	预留字段3	String	LLV…	选填	空 - 未启用
reservedFiled4	预留字段4	String	LLV…	选填	空 - 未启用
reservedFiled5	预留字段5	String	LLV…	选填	空 - 未启用
state 	查询状态	String	1	选填	　
erCode	错误代码	String	8	必填	E+{六位数字} E000000:成功   其他失败
erMsg	错误信息	String	64	选填	　*/
	
	private String name;
	private   String idNo;
	private   String idType;
	private   String seqNo;
	private String sourceId;
	private String    gradeQuery    ;
	private String    moneyBound    ;
	private String    dataBuildTime    ;
	private String    dataStatus    ;
	private String    reservedFiled1    ;
	private String    reservedFiled2    ;
	private String    reservedFiled3    ;
	private String    reservedFiled4    ;
	private String    reservedFiled5    ;
	private String     state   ;
	private String     erCode   ;
	private String     erMsg   ;
	
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
	
	/**  证件号码   */
	public String getSeqNo() {
		return seqNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setIdType(String idType) {
		this.idType = idType;
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
	public String getGradeQuery() {
		return gradeQuery;
	}
	public void setGradeQuery(String gradeQuery) {
		this.gradeQuery = gradeQuery;
	}
	public String getMoneyBound() {
		return moneyBound;
	}
	public void setMoneyBound(String moneyBound) {
		this.moneyBound = moneyBound;
	}
	public String getDataBuildTime() {
		return dataBuildTime;
	}
	public void setDataBuildTime(String dataBuildTime) {
		this.dataBuildTime = dataBuildTime;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getReservedFiled1() {
		return reservedFiled1;
	}
	public void setReservedFiled1(String reservedFiled1) {
		this.reservedFiled1 = reservedFiled1;
	}
	public String getReservedFiled2() {
		return reservedFiled2;
	}
	public void setReservedFiled2(String reservedFiled2) {
		this.reservedFiled2 = reservedFiled2;
	}
	public String getReservedFiled3() {
		return reservedFiled3;
	}
	public void setReservedFiled3(String reservedFiled3) {
		this.reservedFiled3 = reservedFiled3;
	}
	public String getReservedFiled4() {
		return reservedFiled4;
	}
	public void setReservedFiled4(String reservedFiled4) {
		this.reservedFiled4 = reservedFiled4;
	}
	public String getReservedFiled5() {
		return reservedFiled5;
	}
	public void setReservedFiled5(String reservedFiled5) {
		this.reservedFiled5 = reservedFiled5;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
