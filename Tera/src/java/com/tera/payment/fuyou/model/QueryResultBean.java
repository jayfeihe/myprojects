package com.tera.payment.fuyou.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.tera.payment.fuyou.constant.FuOuConstant;

/**
 * 富有支付 查询结果 XML 实体
 * 
 * @author XunXiake
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name="trans")
@XmlType(name="QueryResultBean", propOrder = {
	"merdt",
	"orderno",
	"accntno",
	"accntnm",
	"amt",
	"entseq",
	"memo",
	"state",
	"result",
	"reason"
})
public class QueryResultBean{

	private String merdt;  //原请求日期, 类型：c(8)， 备注：yyyyMMdd
	private String orderno;  //原请求流水, 类型：c(10,30) 备注：数字串，当天必须唯一
	private String accntno;  //账号, 类型：c(10,28)， 备注：用户账号ddddd
	private String accntnm;  //账户名称, 类型：c(30)， 备注：用户账户名称
	private String amt;  //交易金额, 类型：n(1,12)， 备注：单位：分
	private String entseq;  //企业流水号, 类型：c(1,32)， 备注：填写后，系统体现在交易查询和外部通知中
	private String memo;  //备注, 类型：c(1,64) 备注：填写后，系统体现在交易查询和外部通知中
	private String state;  //交易状态, 类型：c(2)   0：未发送  1：已发送且成功  2：已发送且失败  3：发送中  7：交易超时
	private String result;  //交易结果, 类型：c(1,8)
	private String reason;  //结果原因, 类型：c(1,1024)
	
	
	public String getMerdt() {
		return merdt;
	}
	public void setMerdt(String merdt) {
		this.merdt = merdt;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getAccntno() {
		return accntno;
	}
	public void setAccntno(String accntno) {
		this.accntno = accntno;
	}
	public String getAccntnm() {
		return accntnm;
	}
	public void setAccntnm(String accntnm) {
		this.accntnm = accntnm;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getEntseq() {
		return entseq;
	}
	public void setEntseq(String entseq) {
		this.entseq = entseq;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}


	
	
}

