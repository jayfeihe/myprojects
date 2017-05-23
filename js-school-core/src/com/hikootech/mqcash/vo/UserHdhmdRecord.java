package com.hikootech.mqcash.vo;


//好贷网 黑名单查询
public class UserHdhmdRecord {

	private String[] match;// 命中类型(不为空命中)
	private int reason;// 原因类型
	private int create_date_type;// 不良行为时间
	private int amount_type;// 违约金额
	private int over_due_type;// 违约持续时间
	private int legal_status;// 法院记录状态码
	private String outOrderNo;
	private String errormesage;
	private String jnlNo;
	private String sign;
	private String retCode;
	private String retMsg;
	
	
	
	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getErrormesage() {
		return errormesage;
	}

	public void setErrormesage(String errormesage) {
		this.errormesage = errormesage;
	}

	public String getJnlNo() {
		return jnlNo;
	}

	public void setJnlNo(String jnlNo) {
		this.jnlNo = jnlNo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public int getReason() {
		return reason;
	}

	public void setReason(int reason) {
		this.reason = reason;
	}

	public String[] getMatch() {
		return match;
	}

	public void setMatch(String[] match) {
		this.match = match;
	}

	public int getCreate_date_type() {
		return create_date_type;
	}

	public void setCreate_date_type(int create_date_type) {
		this.create_date_type = create_date_type;
	}

	public int getAmount_type() {
		return amount_type;
	}

	public void setAmount_type(int amount_type) {
		this.amount_type = amount_type;
	}

	public int getOver_due_type() {
		return over_due_type;
	}

	public void setOver_due_type(int over_due_type) {
		this.over_due_type = over_due_type;
	}

	public int getLegal_status() {
		return legal_status;
	}

	public void setLegal_status(int legal_status) {
		this.legal_status = legal_status;
	}

}
