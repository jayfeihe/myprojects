package com.tera.audit.law.model.form;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.law.model.Contract;

/** 审核表单bean
 * @author QYANZE
 *
 */
public class LawAuditFormBean {

	private Contract contract; // 合同
	
	private AuditFormBean formBean; // 决策表单bean
	
	private String buttonType;

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public AuditFormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(AuditFormBean formBean) {
		this.formBean = formBean;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
}
