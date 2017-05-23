package com.tera.cooperation.jmbox.model.form;


/**
 * 项目 状态查询  项目还款计划查询  实体类
 * @author XunXiake
 *
 */
public class JMProjecQueryBean extends JMQueryObject {


	private String cid;		//对接 用户 ID
	private String financingProjectID;


	public JMProjecQueryBean(String cid,String financingProjectID){
		this.cid=cid;
		this.financingProjectID=financingProjectID;
	}

	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getFinancingProjectID() {
		return financingProjectID;
	}
	public void setFinancingProjectID(String financingProjectID) {
		this.financingProjectID = financingProjectID;
	}

}
