package com.tera.payment.fuyou.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.tera.payment.fuyou.constant.FuOuConstant;

/**
 * 富有支付 查询 XML 实体
 * 
 * @author XunXiake
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name="qrytransreq")
@XmlType(name="QueryReqBean", propOrder = {
	"ver", 
	"busicd", 
	"orderno",
	"startdt", 
	"enddt", 
	"transst" 
})
public class QueryReqBean{

	private String ver;  //版本号, 类型：c(4)
	private BusicdType busicd;  //业务代码, 类型：c(4)  AC01：代扣;AP01：付款;YZ01：实名验证 ;TP01:退票 
	private String orderno;  //原请求流水, 类型：c(10,30)
	private String startdt;  //开始日期, 类型：c(8)
	private String enddt;  //结束日期, 类型：c(8)
	private String transst;  //交易状态, 类型：c(1) 1：成功，2：失败，7：超时
	
	/**
	 * AC01：代扣;
	 * AP01：付款;
	 * YZ01：实名验证 ;
	 * TP01:退票 
	 * @author XunXiake
	 */
	public enum BusicdType{
		AC01,AP01,YZ01,TP01
	}
	
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public BusicdType getBusicd() {
		return busicd;
	}
	public void setBusicd(BusicdType busicd) {
		this.busicd = busicd;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getStartdt() {
		return startdt;
	}
	public void setStartdt(String startdt) {
		this.startdt = startdt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getTransst() {
		return transst;
	}
	public void setTransst(String transst) {
		this.transst = transst;
	}
}

