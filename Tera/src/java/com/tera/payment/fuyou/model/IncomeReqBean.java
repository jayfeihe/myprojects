package com.tera.payment.fuyou.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.tera.payment.fuyou.constant.FuOuConstant;


/**
 * 富有支付  代收 同步请求 XML 实体
 * @author XunXiake
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name="incomeforreq") 
@XmlType(name="IncomeReqBean", propOrder = {
    "ver",
    "merdt",
    "orderno",
    "bankno",
    "accntno",
    "accntnm",
    "amt",
    "entseq",
    "memo",
    "mobile",
    "certtp",
    "certno"
})
public class IncomeReqBean {

	private String ver;  //版本号类型：c(4)，必填：是，备注：1
	private String merdt;  //请求日期类型：c(8)，必填：是，备注：yyyyMMdd
	private String orderno;  //请求流水类型：c(10,30)，必填：是，备注：数字串，当天必须唯一
	private String bankno;  //总行代码类型：c(4)，必填：是，备注：参见总行代码表
	private String accntno;  //账号  类型：c(10,28)，必填：是，备注：用户账号
	private String accntnm;  //账户名称  类型：c(30)，必填：是，备注：用户账户名称
	private String amt;  //金额类型：n(1,12)，必填：是，备注：单位：分
	private String entseq;  //企业流水号类型：c(1,32)，必填：否，备注：填写后，系统体现在交易查询和外部通知中
	private String memo;  //备注类型：c(1,64)，必填：否，备注：填写后，系统体现在交易查询和外部通知中
	private String mobile;  //手机号类型：c(11)，必填：否，备注：为将来短信通知时使用
	private String certtp;  //证件类型类型：c(1)，必填：否，备注：发送交易到银行时用来做校验    [0:身份证,0:身份证,1:护照,2:军官证,3:士兵证,5:户口本,7:其他]
	private String certno;  //证件号：c(20)，必填：否，备注：发送交易到银行时用来做校验
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
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
	public String getBankno() {
		return bankno;
	}
	public void setBankno(String bankno) {
		this.bankno = bankno;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCerttp() {
		return certtp;
	}
	public void setCerttp(String certtp) {
		this.certtp = certtp;
	}
	public String getCertno() {
		return certno;
	}
	public void setCertno(String certno) {
		this.certno = certno;
	}

	
	
	
}

