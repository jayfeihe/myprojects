package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信8017查询发送业务信息子项
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:13:20
	*/
	
public class BusiDataItemReqQhMSC8017 {

	
	/*idNo	证件号码	String	64	R	　
idType	证件类型	String	2	R	
name	主体名称	String	64	R	　
mobileNo	手机号码	String	24	R	　
cardNo 	卡号	String	64	R	　
reasonNo	查询原因	String	2	R	01--贷款审批 02--贷中管理 03--贷后管理 04--本人查询 05--异议查询 99--其他
email   	邮箱	String	128	O	　
weiboNo  	微博号	String	128	O	　
weixinNo	微信号	String	64	O	　
qqNo    	qq号	String	64	O	　
taobaoNo	淘宝帐号	String	64	O	　
jdNo	京东帐号	String	64	O	　
amazonNo	亚马逊帐号	String	128	O	　
yhdNo	1号店	String	128	O	　
entityAuthCode	信息主体授权码	String	32	R	被查询信息主体的授权代码
entityAuthDate	信息主体授权时间	String	24	R	yyyy-MM-dd HH:mm:ss
seqNo	序列号	String	24	R	子批次号，本批次内唯一*/
	
	private   String idNo;
	private   String idType; //0:身份证1:户口簿  2:护照 3:军官证 4:士兵证 5:港澳居民来往内地通行证 6:台湾同胞来往内地通行证 7:临时身份证 8:外国人居留证 9:警官证 X:其他证件
	private   String name;
	private   String mobileNo;
		/**
		* reasonNo: 注意与8004的查询原因字段名不一样
		*/
	private   String reasonCode;
 
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
}
