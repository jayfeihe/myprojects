package com.hikootech.mqcash.qhzx;


	/**
	* 此类描述的是：前海征信响应报文数据头
	* @author: zhaohefei
	* @version: 2015年11月5日 下午3:40:34
	*/
	
public class HeadersRspQhzx {
	/*
	chnlId	渠道、系统ID	String	16	必填	　
	transNo	交易流水号	String	24	必填	下游系统交易标识号，该流水号只能使用一次
	transDate	交易时间	String	24	必填	yyyy-MM-dd HH:mm:ss
	authCode	授权代码	String	32	必填	{机构授权码}_${16位信息主体授权码}
	authDate	授权时间	String	24	必填	yyyy-MM-dd HH:mm:ss
	*
	*/
	
	private String orgCode;
	private String chnlId;
	private String transNo;
	private String transDate;
	private String authCode;
	private String authDate;
	
	private String rtCode;//E+{六位数字} E000000:成功   其他失败
	private String rtMsg;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getChnlId() {
		return chnlId;
	}
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getRtCode() {
		return rtCode;
	}
	public void setRtCode(String rtCode) {
		this.rtCode = rtCode;
	}
	public String getRtMsg() {
		return rtMsg;
	}
	public void setRtMsg(String rtMsg) {
		this.rtMsg = rtMsg;
	}
	
	
}
