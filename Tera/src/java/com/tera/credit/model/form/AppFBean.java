package com.tera.credit.model.form;

import java.util.List;

import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditContact;
import com.tera.credit.model.CreditExt;


public class AppFBean {

	private CreditApp creditApp;						// 申请表信息
	private List<CreditExt> creditExts;					// 重要情况说明   扩展信息
	private List<CreditContact> commonContacts;			// 常用联系人
	private List<CreditContact> dealingsContacts;		// 经营往来联系人
	
	private String buttonType;		//按钮事件类型   save 保存,submit 提交,waive 放弃
	
	public CreditApp getCreditApp() {
		return creditApp;
	}
	public void setCreditApp(CreditApp creditApp) {
		this.creditApp = creditApp;
	}
	public List<CreditExt> getCreditExts() {
		return creditExts;
	}
	public void setCreditExts(List<CreditExt> creditExts) {
		this.creditExts = creditExts;
	}
	public List<CreditContact> getCommonContacts() {
		return commonContacts;
	}
	public void setCommonContacts(List<CreditContact> commonContacts) {
		this.commonContacts = commonContacts;
	}
	public List<CreditContact> getDealingsContacts() {
		return dealingsContacts;
	}
	public void setDealingsContacts(List<CreditContact> dealingsContacts) {
		this.dealingsContacts = dealingsContacts;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	
	
}
