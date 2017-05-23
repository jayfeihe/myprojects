package com.tera.interfaces.model;

import java.util.List;

public class PushData {
	
	private PushDebt  Debt;
	private PushDebtPer DebtBorrower;//借款人信息
	private PushDebtPer DebtCreditor;//债权人信息
	private PushPledge DebtPledge;//抵押物信息
	private PushDebtDesc DebtDesc;
	private PushBankCard UserBankCard;
	private PushFileInfo debtAttachment;
	public PushDebt getDebt() {
		return Debt;
	}
	public void setDebt(PushDebt debt) {
		Debt = debt;
	}
	public PushDebtPer getDebtBorrower() {
		return DebtBorrower;
	}
	public void setDebtBorrower(PushDebtPer debtBorrower) {
		DebtBorrower = debtBorrower;
	}
	public PushDebtPer getDebtCreditor() {
		return DebtCreditor;
	}
	public void setDebtCreditor(PushDebtPer debtCreditor) {
		DebtCreditor = debtCreditor;
	}
	
	public PushPledge getDebtPledge() {
		return DebtPledge;
	}
	public void setDebtPledge(PushPledge debtPledge) {
		DebtPledge = debtPledge;
	}
	public PushDebtDesc getDebtDesc() {
		return DebtDesc;
	}
	public void setDebtDesc(PushDebtDesc debtDesc) {
		DebtDesc = debtDesc;
	}
	public PushBankCard getUserBankCard() {
		return UserBankCard;
	}
	public void setUserBankCard(PushBankCard userBankCard) {
		UserBankCard = userBankCard;
	}
	public PushFileInfo getDebtAttachment() {
		return debtAttachment;
	}
	public void setDebtAttachment(PushFileInfo debtAttachment) {
		this.debtAttachment = debtAttachment;
	}
	
	

}
