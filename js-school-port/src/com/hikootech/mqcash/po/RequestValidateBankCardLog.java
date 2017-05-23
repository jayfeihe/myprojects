package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**  
 *   
 * RequestValidateBankCardLog  
 *   
 * @function:(银行卡绑定操作记录表PO)  
 * @create time:Nov 3, 2015 1:46:27 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class RequestValidateBankCardLog implements Serializable{

	
	private static final long serialVersionUID = 2539307992865015753L;
	
	private String bankCardLogId ; 			//日志id
	private Integer operationType ; 			//操作类型：0 请求验证银行卡四要素 1绑定银行卡
	private String bankCardId ; 			//银行卡绑定操作记录表id
	
	private String custIp;    //用户ip
	private int custIpInt;   //用户ipInt
	private String thirdParyBindingId ;		//第三方绑定流水号
	private String configBankId ;			//银行列表对应关系表id银行代码
	private String userId ;					//用户id
	private String ownerIdCard ;				//持卡人身份证号码
	private String ownerName ;				//持卡人姓名
	private Integer cardType ;				//卡类型
	private String cardNumber ="";			//卡号
	private String reserveMobile="" ;		//预留手机号
	private Integer bindStatus ;			//绑定状态：0未绑定 1绑定 2解绑
	private String code ;			//响应码
	private String msg ="";			//响应消息
	private String responseCode ;			//响应码
	private String responseMsg ="";			//响应消息
	
	private Integer verifyStatus;			//短信验证状态
	private Integer status ;				//交易状态
	private Date bankTxTime ;				//银行处理时间
	private String issInsCode ;				//发卡机构代码
	private Integer payCardType ;			//支付卡类型
	private String descp ="";				//描述
	
	
	private Date createTime ;				//创建时间
	private Date updateTime ;				//修改时间
	public String getBankCardId() {
		return bankCardId;
	}
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}
	public String getThirdParyBindingId() {
		return thirdParyBindingId;
	}
	public void setThirdParyBindingId(String thirdParyBindingId) {
		this.thirdParyBindingId = thirdParyBindingId;
	}
	public String getConfigBankId() {
		return configBankId;
	}
	public void setConfigBankId(String configBankId) {
		this.configBankId = configBankId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOwnerIdCard() {
		return ownerIdCard;
	}
	public void setOwnerIdCard(String ownerIdCard) {
		this.ownerIdCard = ownerIdCard;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getReserveMobile() {
		return reserveMobile;
	}
	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}
	public Integer getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getBankCardLogId() {
		return bankCardLogId;
	}
	public void setBankCardLogId(String bankCardLogId) {
		this.bankCardLogId = bankCardLogId;
	}
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getBankTxTime() {
		return bankTxTime;
	}
	public void setBankTxTime(Date bankTxTime) {
		this.bankTxTime = bankTxTime;
	}
	public String getIssInsCode() {
		return issInsCode;
	}
	public void setIssInsCode(String issInsCode) {
		this.issInsCode = issInsCode;
	}
	public Integer getPayCardType() {
		return payCardType;
	}
	public void setPayCardType(Integer payCardType) {
		this.payCardType = payCardType;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCustIp() {
		return custIp;
	}
	public void setCustIp(String custIp) {
		this.custIp = custIp;
	}
	public int getCustIpInt() {
		return custIpInt;
	}
	public void setCustIpInt(int custIpInt) {
		this.custIpInt = custIpInt;
	}
	 
	
}
