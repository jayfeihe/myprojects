package com.hikootech.mqcash.qhzx;


/**  
 *   
 * BusiDataItemReqQhMSC8037  
 *   
 * @function:(前海征信8037查询发送业务信息子项)  
 * @create time:2016年5月7日 下午1:38:54   
 * @version 1.0.0  
 * @author:张海达    
 */
public class BusiDataItemReqQhMSC8037 {

	
	/*	idNo	证件号码	String	64	R	
		idType	证件类型	String	1	R	见附件四：证件类型详细介绍
		busiDesc	业务描述	String	2	O	如：抢钱活动
		name	主体名称	String	128	R	
		entityAuthCode	信息主体授权码	String	32	R	被查询信息主体的授权代码
		entityAuthDate	信息主体授权时间	String	24	R	yyyy-MM-dd
		seqNo	序列号	String	24	R	子批次号，本批次内唯一*/
	
	/**  
	 * idNo:TODO（证件号码）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String idNo;
	/**  
	 * idType:TODO（证件类型）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String idType;
	/**  
	 * busiDesc:TODO（业务描述）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String busiDesc;
	/**  
	 * name:TODO（主体名称）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String name;
	/**  
	 * entityAuthCode:TODO（信息主体授权码）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String entityAuthCode;
	/**  
	 * entityAuthDate:TODO（信息主体授权时间）  
	 *  
	 * @since 1.0.0  
	 */  
	private   String entityAuthDate;
	/**  
	 * seqNo:TODO（序列号）  
	 *  
	 * @since 1.0.0  
	 */  
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
	public String getBusiDesc() {
		return busiDesc;
	}
	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}
	

}
