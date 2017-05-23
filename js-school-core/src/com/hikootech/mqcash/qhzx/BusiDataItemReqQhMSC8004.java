package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信8004查询发送业务信息子项
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:13:20
	*/
	
public class BusiDataItemReqQhMSC8004 {

	
	/*idNo	证件号码	String	64
	idType	证件类型	String	1
	reasonCode	查询原因	String	2
	name	主体名称	String	128
	entityAuthCode	信息主体授权码	String	32
	entityAuthDate	信息主体授权时间	String	24
	seqNo	序列号	String	24*/
	
	private   String idNo;
	private   String idType;
	private   String reasonCode;
	private   String name;
	private   String entityAuthCode;
	private   String entityAuthDate;
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
	/**  查询原因	String	2  ：
	 *  01--贷款审批；
		02--贷中管理；
		03—贷后管理；
		04--本人查询；
		05--异议查询；
		99--其他。 */
	public String getReasonCode() {
		return reasonCode;
	}
	/**  主体名称	String	128   */
	public String getName() {
		return name;
	}
	/**  信息主体授权码	String	32  */
	public String getEntityAuthCode() {
		return entityAuthCode;
	}
	/**  信息主体授权时间	String	24  yyyy-MM-dd HH:mm:ss   */
	public String getEntityAuthDate() {
		return entityAuthDate;
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
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEntityAuthCode(String entityAuthCode) {
		this.entityAuthCode = entityAuthCode;
	}
	public void setEntityAuthDate(String entityAuthDate) {
		this.entityAuthDate = entityAuthDate;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	

}
