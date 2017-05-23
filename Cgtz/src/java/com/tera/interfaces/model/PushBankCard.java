package com.tera.interfaces.model;
/**
 * 银行卡信息
 * @author Jesse
 *
 */
public class PushBankCard {
	

	private String  card_type; // 银行卡类型  0个人，１企业
	private String  name;//需与借款人姓名一直 
	private String  card_number; //银行卡号
	private String  bank_id;// 银行数据  开户银行
	private String  bank_province;//银行省
	private String  bank_city;//银行市
	private String  sub_branch_name;//分支行
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String cardType) {
		card_type = cardType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String cardNumber) {
		card_number = cardNumber;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bankId) {
		bank_id = bankId;
	}
	public String getBank_province() {
		return bank_province;
	}
	public void setBank_province(String bankProvince) {
		bank_province = bankProvince;
	}
	public String getBank_city() {
		return bank_city;
	}
	public void setBank_city(String bankCity) {
		bank_city = bankCity;
	}
	public String getSub_branch_name() {
		return sub_branch_name;
	}
	public void setSub_branch_name(String subBranchName) {
		sub_branch_name = subBranchName;
	}

}
