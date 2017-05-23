package com.tera.collection.visit.model;

import java.util.List;

import com.tera.credit.model.CreditContact;

public class VisitRelations {
 	private List<CreditContact> commonContacts;		//联系人信息集合

	public List<CreditContact> getCommonContacts() {
		return commonContacts;
	}

	public void setCommonContacts(List<CreditContact> commonContacts) {
		this.commonContacts = commonContacts;
	}	
}
