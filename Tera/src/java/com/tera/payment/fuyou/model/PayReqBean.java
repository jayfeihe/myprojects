package com.tera.payment.fuyou.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.tera.payment.fuyou.constant.FuOuConstant;


/**
 * 富有支付 付款请求 XML 实体
 * @author XunXiake
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name = "payforreq")
@XmlType(name = "PayReqBean", propOrder = {
    "ver",
    "merdt",
    "orderno",
    "bankno",
    "cityno",
    "branchnm",
    "accntno",
    "accntnm",
    "amt",
    "entseq",
    "memo",
    "mobile"
})
public class PayReqBean {

	private String ver;  //版本号, 类型：c(4)，必填：是，备注：1
	private String merdt;  //请求日期, 类型：c(8)，必填：是，备注：yyyyMMdd
	private String orderno;  //请求流水, 类型：c(10,30)，必填：是，备注：数字串，当天必须唯一
	private String bankno;  //总行代码, 类型：c(4)，必填：是，备注：参见总行代码表
	private String cityno;  //城市代码, 类型：c(4)，必填：是，备注：参见地市代码表
	private String branchnm;  //支行名称, 类型：c(100)，必填：否，备注：支行名称，中行、建行、广发必填
	private String accntno;  //账号, 类型：c(10,28)，必填：是，备注：用户账号
	private String accntnm;  //账户名称, 类型：c(30)，必填：是，备注：用户账户名称
	private String amt;  //金额, 类型：n(1,12)，必填：是，备注：单位：分
	private String entseq;  //企业流水号, 类型：c(1,32)，必填：否，备注：填写后，系统体现在交易查询和外部通知中
	private String memo;  //备注, 类型：c(1,64)，必填：否，备注：填写后，系统体现在交易查询和外部通知中
	private String mobile;  //手机号, 类型：c(11)，必填：否，备注：为将来短信通知时使用
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
	public String getCityno() {
		return cityno;
	}
	public void setCityno(String cityno) {
		this.cityno = cityno;
	}
	public String getBranchnm() {
		return branchnm;
	}
	public void setBranchnm(String branchnm) {
		this.branchnm = branchnm;
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

	
}

