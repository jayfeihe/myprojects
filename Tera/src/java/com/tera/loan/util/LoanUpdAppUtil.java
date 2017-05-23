package com.tera.loan.util;

import java.util.List;


import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanAppContact;
import com.tera.loan.model.form.LoanUpdFBean;

/**
 * see:查询LoanApp表封装回显到页面Value
 * @data 2014-6-16->下午06:29:48
 * @throws Exception
 */
public class LoanUpdAppUtil {
	public static LoanUpdFBean rtsLoanApp(LoanUpdFBean bean,List<LoanApp> contactList){
		if( null!= contactList ){
			for (LoanApp app : contactList) {
				if("01".equals(app.getCustomerType())){ //1 表示个人
					bean.setAppChannel(app.getAppChannel());
					bean.setId(app.getId());
					bean.setAppId(app.getAppId());
					bean.setCustomerNo(app.getCustomerNo());
					bean.setMainFlag(app.getMainFlag());
					bean.setType(app.getType());
					bean.setConsultId(app.getConsultId());
					bean.setName(app.getName());
					bean.setMobile(app.getMobile());
					bean.setPhone(app.getPhone());
					bean.setIdType(app.getIdType());
					bean.setIdNo(app.getIdNo());
					bean.setMarriage(app.getMarriage());
					bean.setAddProvince(app.getAddProvince());
					bean.setAddCity(app.getAddCity());
					bean.setAddCounty(app.getAddCounty());
					bean.setAddress(app.getAddress());
					bean.setIndustry1(app.getIndustry1());
					bean.setIndustry2(app.getIndustry2());
					bean.setIndustry3(app.getIndustry3());
					bean.setAmount(app.getAmount());
					bean.setUseage(app.getUseage());
					bean.setDetailUseage(app.getDetailUseage());
					bean.setProduct(app.getProduct());
					bean.setPeriod(app.getPeriod());
					bean.setSreviceFeeRate(app.getSreviceFeeRate());
					bean.setInterestRate(app.getInterestRate());
					bean.setLendAccName(app.getLendAccName());
					bean.setLendAccBank(app.getLendAccBank());
					bean.setLendAccount(app.getLendAccount());
					bean.setRepayAccName(app.getRepayAccName());
					bean.setRepayAccBank(app.getRepayAccBank());
					bean.setRepayAccount(app.getRepayAccount());
					bean.setRegDate(app.getRegDate());
					bean.setRegAmount(app.getRegAmount());
					bean.setAcctualAmount(app.getAcctualAmount());
					bean.setOrgCodeNo(app.getOrgCodeNo());
					bean.setOrgCodeExpiryDate(app.getOrgCodeExpiryDate());
					bean.setTaxRegNo(app.getTaxRegNo());
					bean.setBaiscAccountBank(app.getBaiscAccountBank());
					bean.setBaiscAccount(app.getBaiscAccount());
					bean.setBizzScope(app.getBizzScope());
					bean.setCompanyType(app.getCompanyType());
					bean.setMainProduct(app.getMainProduct());
					bean.setLastYearTurnover(app.getLastYearTurnover());
					bean.setLast3mTurnover(app.getLast3mTurnover());
					bean.setCooperateBankCompany(app.getCooperateBankCompany());
					bean.setDailyClearBank(app.getDailyClearBank());
					bean.setLoanBalance(app.getLoanBalance());
					bean.setFinanceCompany(app.getFinanceCompany());
					bean.setFinanceBank(app.getFinanceBank());
					bean.setNearly3mEleBill1(app.getNearly3mEleBill1());
					bean.setNearly3mEleBill2(app.getNearly3mEleBill2());
					bean.setNearly3mEleBill3(app.getNearly3mEleBill3());
					bean.setMatchType(app.getMatchType());
					bean.setCustomerType(app.getCustomerType());
					bean.setAppType(app.getAppType());
					bean.setType(app.getType()); //
					bean.setOrgId(app.getOrgId());
					bean.setSales(app.getSales());
				}
				if("02".equals(app.getCustomerType())){ // 表示 机构
					bean.setOrgLoanAppId(app.getId());
					bean.setOrgName(app.getName());
					bean.setOrgRegDate(app.getRegDate());
					bean.setOrgRegDateStr(app.getRegDateStr());
					bean.setOrgRegAmount(app.getRegAmount());
					bean.setOrgIdType(app.getIdType());
					bean.setOrgIdNo(app.getIdNo());
					bean.setOrgAcctualAmount(app.getAcctualAmount());
					bean.setOrgOrgCodeNo(app.getOrgCodeNo());
					bean.setOrgOrgCodeExpiryDateStr(app.getOrgCodeExpiryDateStr());
					bean.setOrgTaxRegNo(app.getTaxRegNo());
					bean.setOrgBaiscAccountBank(app.getBaiscAccountBank());
					bean.setOrgBaiscAccount(app.getBaiscAccount());
					bean.setOrgUseage(app.getUseage());
					bean.setOrgIndustry1(app.getIndustry1());
					bean.setOrgIndustry2(app.getIndustry2());
					bean.setOrgIndustry3(app.getIndustry3());
					bean.setOrgBizzScope(app.getBizzScope());
					bean.setOrgCompanyType(app.getCompanyType());
					bean.setOrgMainProduct(app.getMainProduct());
					bean.setOrgLastYearTurnover(app.getLastYearTurnover());
					bean.setOrgLast3mTurnover(app.getLast3mTurnover());
					bean.setOrgCooperateBankCompany(app
							.getCooperateBankCompany());
					bean.setOrgDailyClearBank(app.getDailyClearBank());
					bean.setOrgLoanBalance(app.getLoanBalance());
					bean.setOrgFinanceCompany(app.getFinanceCompany());
					bean.setOrgFinanceBank(app.getFinanceBank());
					bean.setOrgNearly3mEleBill1(app.getNearly3mEleBill1());
					bean.setOrgNearly3mEleBill2(app.getNearly3mEleBill2());
					bean.setOrgNearly3mEleBill3(app.getNearly3mEleBill3());
					bean.setOrgLendAccName(app.getLendAccName());
					bean.setOrgLendAccBank(app.getLendAccBank());
					bean.setOrgLendAccount(app.getLendAccount());
					bean.setCustomerType(app.getCustomerType());
					bean.setOrgId(app.getOrgId());
					bean.setType(app.getType());
					bean.setAppType(app.getAppType());
					bean.setSales(app.getSales());
				}
			}
		}
		return bean;
	}
	public static LoanUpdFBean rtsContact(LoanUpdFBean bean,List<LoanAppContact> contactList){
		if( null!= contactList ){
			for (LoanAppContact per : contactList) {
				if(null != per.getContactType()){
				if("01".equals(per.getContactType())){ //配偶信息
					bean.setContactId(per.getId());
					bean.setContactName(per.getName());
					bean.setContactMobile(per.getMobile());
					bean.setContactIdType(per.getIdType());
					bean.setContactIdNo(per.getIdNo());
					bean.setContactAddProvice(per.getAddProvice());
					bean.setContactAddCity(per.getAddCity());
					bean.setContactAddCounty(per.getAddCounty());
					bean.setContactAddress(per.getAddress());
					bean.setContactOrgId(per.getOrgId());
					bean.setMainFlag(per.getMainFlag());
				}else if("02".equals(per.getContactType())){ //法定人员信息
					bean.setFdOrgId(per.getId());
					bean.setFdOrgName(per.getName());
					bean.setFdOrgMobile(per.getMobile());
					bean.setFdorgIdType(per.getIdType());
					bean.setFdOrgIdNo(per.getIdNo());
					bean.setFdOrgOrgId(per.getOrgId());
					bean.setFdOrgMainFlag(per.getMainFlag());
					
				}else if("03".equals(per.getContactType())){ //授权人员信息
					bean.setSqOrgId(per.getId());
					bean.setSqOrgName(per.getName());
					bean.setSqOrgMobile(per.getMobile());
					bean.setSqOrgIdType(per.getIdType());
					bean.setSqOrgIdNo(per.getIdNo());
					bean.setSqOrgOrgId(per.getOrgId());
					bean.setSqOrgMainFlag(per.getMainFlag());
				}else if("04".equals(per.getContactType())){ //财务主管信息
					bean.setCwOrgId(per.getId());
					bean.setCwOrgName(per.getName());
					bean.setCwOrgMobile(per.getMobile());
					bean.setCwOrgIdType(per.getIdType());
					bean.setCwOrgIdNo(per.getIdNo());
					bean.setCwOrgOrgId(per.getOrgId());
					bean.setCwOrgMainFlag(per.getMainFlag());
				}
			}
			}
		}
		return bean;
	}
}
