package com.tera.cooperation.jmbox.model.form;

/**
 * 渠道查重接口实体类
 * @author QYANZE
 * @date 2015-04-24
 */
public class JMChannalDuplicateBean extends JMQueryObject {

	private String cid;		// 渠道id
	private String name;	// 姓名
	private String idCard;	// 身份证号
	
	public JMChannalDuplicateBean() {}

	public JMChannalDuplicateBean(String cid, String name, String idCard) {
		super();
		this.cid = cid;
		this.name = name;
		this.idCard = idCard;
	}
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
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
}
