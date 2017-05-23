package com.hikootech.mqcash.qhzx;


/**  
 *   
 * BusiDataItemRspQhMSC8037  
 *   
 * @function:(前海征信8004查询发送业务信息子项)  
 * @create time:2016年5月7日 下午1:43:35   
 * @version 1.0.0  
 * @author:张海达    
 */
public class BusiDataItemRspQhMSC8037 {

	
	/*idNo	证件号码	String	64	R	
	idType	证件类型	String	1	R	0：身份证
	name	主体名称	String	128	R	
	seqNo	序列号	String	24	R	子批次号，本批次内唯一
	reasonCode	查询原因	String	2	O	见附件五：查询原因详细介绍
	industry	机构所属行业	String	8	O	附件六：机构所属行业详细介绍
	amount	命中机构数目	String	16	O	-1 无权限 其他为命中机构数
	busiDate	业务发生时间日期	String	24	O	0-无权限 其他为:yyyy-MM-dd
	erCode	错误代码	String	8	R	E+{六位数字} E000000:成功    其他失败
	erMsg	错误信息	String	64	O	　*/
	
	/**  
	 * name:TODO（主体名称）  
	 *  
	 * @since 1.0.0  
	 */  
	private String name;
	/**  
	 * idNo:TODO（证件号码）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String idNo;
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
	private   String idType;
	/**  
	 * seqNo:TODO（序列号）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String seqNo;
	/**  
	 * gradeQuery:TODO（查询原因）  
	 *  01--贷款审批；
		02--贷中管理；
		03—贷后管理；
		04--本人查询；
		05--异议查询；
		99--其他;
		0--无权限。
	 * @since 1.0.0  
	 */  
	private String    reasonCode    ;
	/**  
	 * industry:TODO（机构所属行业）  
	 *  BAK--银行；
		MCL--小贷；
		P2P--P2P；
		ASM--资产管理；
		TRU--信托；
		LEA--租赁；
		CRF--众筹；
		INV--投资；
		CNS--消费金融；
		INS--保险；
		THR--第三方；
		OTH--其他；
		0--无权限。
	 * @since 1.0.0  
	 */  
	private String    industry    ;
	/**  
	 * amount:TODO（命中机构数目）  
	 *  
	 * @since 1.0.0  
	 */  
	private String    amount    ;
	/**  
	 * busiDate:TODO（业务发生时间日期）  
	 *  
	 * @since 1.0.0  
	 */  
	private String    busiDate    ;
	/**  
	 * erCode:TODO（错误代码）  
	 *  
	 * @since 1.0.0  
	 */  
	private String     erCode   ;
	/**  
	 * erMsg:TODO（错误信息）  
	 *  
	 * @since 1.0.0  
	 */  
	private String     erMsg   ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBusiDate() {
		return busiDate;
	}
	public void setBusiDate(String busiDate) {
		this.busiDate = busiDate;
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
