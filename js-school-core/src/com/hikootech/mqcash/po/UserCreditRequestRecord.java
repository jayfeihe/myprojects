package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**  
 *   
 * UserCreditRequestRecord  
 *   
 * @function:(用户征信信息表：通过第三方征信获取的用户征信数据，以身份证做为主键)  
 * @create time:Oct 9, 2015 4:54:47 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserCreditRequestRecord implements Serializable{

	
	private static final long serialVersionUID = -1878092090986888846L;
	
	/**  
	 * requestId:TODO（用户征信请求流水号）  
	 */  
	private String requestId;
	/**  
	 * name:TODO（用户姓名）  
	 */  
	private String name;
	/**  
	 * idCard:TODO（身份证）  
	 */  
	private String idCard;
	/**  
	 * uniqueCode:TODO（请求国政通唯一标识(电信征信使用)）  
	 */  
	private String uniqueCode;
	
	/**  
	 * mobileNumber:TODO（手机号）  
	 */  
	private String mobileNumber;
	/**  
	 * source:TODO（请求源:10国政通）  
	 */  
	private String source;
	/**  
	 * requestType:TODO（请求类型：10学历 20电话）  
	 */  
	private Integer requestType;
	/**  
	 * requestType:TODO（征信请求时间）  
	 */  
	private Date requestTime;
	/**  
	 * requestResult:TODO（征信请求结果：0成功 1其他失败 ）  
	 */  
	private Integer requestResult;
	/**  
	 * descp（返回结果描述）  
	 */  
	private String descp;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getRequestType() {
		return requestType;
	}
	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Integer getRequestResult() {
		return requestResult;
	}
	public void setRequestResult(Integer requestResult) {
		this.requestResult = requestResult;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
	
}
