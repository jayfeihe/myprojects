package com.tera.loan.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tera.customer.model.Customer;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanAppContact;
import com.tera.loan.model.form.LoanAppBean;
import com.tera.loan.model.form.LoanFBean;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
/**
 * @see 取出封装的FormBean的value,Set到LoanApp中 
 * @data 2014-6-17->下午06:15:14
 * @throws Exception
 */
public class LoanAppUtil {
	public static List<LoanAppBean> getLoanAppBeans(LoanFBean bean,HttpServletRequest request){
		List<LoanAppBean> lists = new ArrayList<LoanAppBean>();
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		for (int i = 0; i < bean.getName().length; i++) {
			LoanAppBean  app = new LoanAppBean();
			LoanApp loanapp = new LoanApp();
			loanapp.setId(getNullArray(bean.getId(),i));
			loanapp.setAppChannel(bean.getAppChannel());
			loanapp.setAppId(getNullArray(bean.getAppId(),0));
			loanapp.setCustomerNo(getNullArray(bean.getCustomerNo(),i));
			loanapp.setMainFlag(bean.getMainFlag()[i]);
			loanapp.setType(getNullArray(bean.getType(),i));
			loanapp.setConsultId(getNullArray(bean.getConsultId(),i));
			loanapp.setName(getNullArray(bean.getName(),i));
			loanapp.setMobile(getNullArray(bean.getMobile(),i));
			loanapp.setPhone(getNullArray(bean.getPhone(),i));
			loanapp.setIdType(getNullArray(bean.getIdType(),i));
			loanapp.setIdNo(getNullArray(bean.getIdNo(),i));
			loanapp.setMarriage(getNullArray(bean.getMarriage(),i));
			loanapp.setAddProvince(getNullArray(bean.getAddProvince(),i));
			loanapp.setAddCity(getNullArray(bean.getAddCity(),i));
			loanapp.setAddCounty(getNullArray(bean.getAddCounty(),i));
			loanapp.setAddress(getNullArray(bean.getAddress(),i));
			loanapp.setIndustry1(getNullArray(bean.getIndustry1(),i));
			loanapp.setIndustry2(getNullArray(bean.getIndustry2(),i));
			loanapp.setIndustry3(getNullArray(bean.getIndustry3(),i));
			loanapp.setAmount(getNullArray(bean.getAmount(),0));
			loanapp.setUseage(getNullArray(bean.getUseage(),0));
			loanapp.setDetailUseage(getNullArray(bean.getDetailUseage(),0));
			loanapp.setProduct(getNullArray(bean.getProduct(),0));
			loanapp.setPeriod(getNullArray(bean.getPeriod(),0));
			loanapp.setSreviceFeeRate(getNullArray(bean.getSreviceFeeRate(),0));
			loanapp.setInterestRate(getNullArray(bean.getInterestRate(),0));
			loanapp.setLendAccName(getNullArray(bean.getLendAccName(),0));
			loanapp.setLendAccBank(getNullArray(bean.getLendAccBank(),0));
			loanapp.setLendAccount(getNullArray(bean.getLendAccount(),0));
			loanapp.setRepayAccName(getNullArray(bean.getRepayAccName(),0));
			loanapp.setRepayAccBank(getNullArray(bean.getRepayAccBank(),0));
			loanapp.setRepayAccount(getNullArray(bean.getRepayAccount(),0));
			loanapp.setRegDate(getNullArray(bean.getRegDate(),i));
			loanapp.setRegAmount(getNullArray(bean.getRegAmount(),i));
			loanapp.setAcctualAmount(getNullArray(bean.getAcctualAmount(),i));
			loanapp.setOrgCodeNo(getNullArray(bean.getOrgCodeNo(),i));
			loanapp.setOrgCodeExpiryDate(getNullArray(bean.getOrgCodeExpiryDate(),i));
			loanapp.setTaxRegNo(getNullArray(bean.getTaxRegNo(),i));
			loanapp.setBaiscAccountBank(getNullArray(bean.getBaiscAccountBank(),i));
			loanapp.setBaiscAccount(getNullArray(bean.getBaiscAccount(),i));
			loanapp.setBizzScope(getNullArray(bean.getBizzScope(),i));
			loanapp.setCompanyType(getNullArray(bean.getCompanyType(),i));
			loanapp.setMainProduct(getNullArray(bean.getMainProduct(),i));
			loanapp.setLastYearTurnover(getNullArray(bean.getLastYearTurnover(),i));
			loanapp.setLast3mTurnover(getNullArray(bean.getLast3mTurnover(),i));
			loanapp.setCooperateBankCompany(getNullArray(bean.getCooperateBankCompany(),i));
			loanapp.setDailyClearBank(getNullArray(bean.getDailyClearBank(),i));
			loanapp.setLoanBalance(getNullArray(bean.getLoanBalance(),i));
			loanapp.setFinanceCompany(getNullArray(bean.getFinanceCompany(),i));
			loanapp.setFinanceBank(getNullArray(bean.getFinanceBank(),i));
			loanapp.setNearly3mEleBill1(getNullArray(bean.getNearly3mEleBill1(),i));
			loanapp.setNearly3mEleBill2(getNullArray(bean.getNearly3mEleBill2(),i));
			loanapp.setNearly3mEleBill3(getNullArray(bean.getNearly3mEleBill3(),i));
			loanapp.setMatchType(getNullArray(bean.getMatchType(),i));
			loanapp.setOperator(loginId);
			loanapp.setCustomerType("01");
			loanapp.setAppType(bean.getAppType()); //申请类型
			loanapp.setType("1"); //类型-抵押/信用
			loanapp.setOrgId(org.getOrgId());
			loanapp.setCustomerManager(loginId);
			loanapp.setSales(bean.getSales());
			app.setLoanapp(loanapp); 
			
			LoanApp orgLoanApp = new LoanApp();
			orgLoanApp.setId(getNullArray(bean.getOrgLoanAppId(),i));
			orgLoanApp.setAppChannel(bean.getAppChannel());          	//++
			
			orgLoanApp.setName(getNullArray(bean.getOrgName(),i));
			orgLoanApp.setMainFlag(bean.getMainFlag()[i]);
			orgLoanApp.setRegDate(getNullArray(bean.getOrgRegDate(),i));
			orgLoanApp.setRegAmount(getNullArray(bean.getOrgRegAmount(),i));
			orgLoanApp.setIdType(getNullArray(bean.getOrgIdType(),i));
			orgLoanApp.setIdNo(getNullArray(bean.getOrgIdNo(),i));
			orgLoanApp.setAcctualAmount(getNullArray(bean.getOrgAcctualAmount(),i));
			orgLoanApp.setOrgCodeNo(getNullArray(bean.getOrgOrgCodeNo(),i));
			orgLoanApp.setOrgCodeExpiryDate(getNullArray(bean.getOrgOrgCodeExpiryDate(),i));
			orgLoanApp.setTaxRegNo(getNullArray(bean.getOrgTaxRegNo(),i));
			orgLoanApp.setBaiscAccountBank(getNullArray(bean.getOrgBaiscAccountBank(),i));
			orgLoanApp.setBaiscAccount(getNullArray(bean.getOrgBaiscAccount(),i));
			orgLoanApp.setUseage(getNullArray(bean.getOrgUseage(),i));
			orgLoanApp.setIndustry1(getNullArray(bean.getOrgIndustry1(),i));
			orgLoanApp.setIndustry2(getNullArray(bean.getOrgIndustry2(),i));
			orgLoanApp.setIndustry3(getNullArray(bean.getOrgIndustry3(),i));
			orgLoanApp.setAmount(getNullArray(bean.getAmount(),0));
			orgLoanApp.setUseage(getNullArray(bean.getUseage(),0));
			orgLoanApp.setDetailUseage(getNullArray(bean.getDetailUseage(),0));
			orgLoanApp.setProduct(getNullArray(bean.getProduct(),0));
			orgLoanApp.setPeriod(getNullArray(bean.getPeriod(),0));
			orgLoanApp.setSreviceFeeRate(getNullArray(bean.getSreviceFeeRate(),0));
			orgLoanApp.setInterestRate(getNullArray(bean.getInterestRate(),0));
			orgLoanApp.setLendAccName(getNullArray(bean.getLendAccName(),0));
			orgLoanApp.setLendAccBank(getNullArray(bean.getLendAccBank(),0));
			orgLoanApp.setLendAccount(getNullArray(bean.getLendAccount(),0));
			orgLoanApp.setRepayAccName(getNullArray(bean.getRepayAccName(),0));
			orgLoanApp.setRepayAccBank(getNullArray(bean.getRepayAccBank(),0));
			orgLoanApp.setRepayAccount(getNullArray(bean.getRepayAccount(),0));
			orgLoanApp.setBizzScope(getNullArray(bean.getOrgBizzScope(),i));
			orgLoanApp.setCompanyType(getNullArray(bean.getOrgCompanyType(),i));
			orgLoanApp.setMainProduct(getNullArray(bean.getOrgMainProduct(),i));
			orgLoanApp.setLastYearTurnover(getNullArray(bean.getOrgLastYearTurnover(),i));
			orgLoanApp.setLast3mTurnover(getNullArray(bean.getOrgLast3mTurnover(),i));
			orgLoanApp.setCooperateBankCompany(getNullArray(bean.getOrgCooperateBankCompany(),i));
			orgLoanApp.setDailyClearBank(getNullArray(bean.getOrgDailyClearBank(),i));
			orgLoanApp.setLoanBalance(getNullArray(bean.getOrgLoanBalance(),i));
			orgLoanApp.setFinanceCompany(getNullArray(bean.getOrgFinanceCompany(),i));
			orgLoanApp.setFinanceBank(getNullArray(bean.getOrgFinanceBank(),i));
			orgLoanApp.setNearly3mEleBill1(getNullArray(bean.getOrgNearly3mEleBill1(),i));
			orgLoanApp.setNearly3mEleBill2(getNullArray(bean.getOrgNearly3mEleBill2(),i));
			orgLoanApp.setNearly3mEleBill3(getNullArray(bean.getOrgNearly3mEleBill3(),i));
			orgLoanApp.setCustomerType("02");
			orgLoanApp.setOperator(loginId);
			orgLoanApp.setOrgId(org.getOrgId());
			orgLoanApp.setType("1");
			orgLoanApp.setAppType(bean.getAppType());
			orgLoanApp.setCustomerManager(loginId);
			orgLoanApp.setSales(bean.getSales());
			app.setOrgLoanApp(orgLoanApp);
			
			LoanAppContact fdOrgContact = new LoanAppContact();
			fdOrgContact.setId(getNullArray(bean.getFdOrgId(),i));
			fdOrgContact.setName(getNullArray(bean.getFdOrgName(),i));
			fdOrgContact.setMobile(getNullArray(bean.getFdOrgMobile(),i));
			fdOrgContact.setIdType(getNullArray(bean.getFdorgIdType(),i));
			fdOrgContact.setIdNo(getNullArray(bean.getFdOrgIdNo(),i));
			fdOrgContact.setMainFlag(bean.getMainFlag()[i]);
			fdOrgContact.setOperator(loginId);
			fdOrgContact.setOrgId(org.getOrgId());
			app.setFdOrgContact(fdOrgContact);
			
			LoanAppContact poContact= new LoanAppContact();
			poContact.setId(getNullArray(bean.getContactId(),i));
			poContact.setName(getNullArray(bean.getContactName(),i));
			poContact.setMobile(getNullArray(bean.getContactMobile(),i));
			poContact.setIdType(getNullArray(bean.getContactIdType(),i));
			poContact.setIdNo(getNullArray(bean.getContactIdNo(),i));
			poContact.setAddProvice(getNullArray(bean.getContactAddProvice(),i));
			poContact.setAddCity(getNullArray(bean.getContactAddCity(),i));
			poContact.setAddCounty(getNullArray(bean.getContactAddCounty(),i));
			poContact.setAddress(getNullArray(bean.getContactAddress(),i));
			poContact.setOperator(loginId);
			poContact.setOrgId(org.getOrgId());
			poContact.setMainFlag(bean.getMainFlag()[i]);
			app.setPoContact(poContact);
			
			LoanAppContact sqOrgContact =new LoanAppContact();
			sqOrgContact.setId(getNullArray(bean.getSqOrgId(),i));
			sqOrgContact.setName(getNullArray(bean.getSqOrgName(),i));
			sqOrgContact.setMobile(getNullArray(bean.getSqOrgMobile(),i));
			sqOrgContact.setIdType(getNullArray(bean.getSqOrgIdType(),i));
			sqOrgContact.setIdNo(getNullArray(bean.getSqOrgIdNo(),i));
			sqOrgContact.setMainFlag(bean.getMainFlag()[i]);
			sqOrgContact.setOperator(loginId);
			sqOrgContact.setOrgId(org.getOrgId());
			app.setSqOrgContact(sqOrgContact);
			
			LoanAppContact cwOrgContact =new LoanAppContact();
			cwOrgContact.setId(getNullArray(bean.getCwOrgId(),i));
			cwOrgContact.setName(getNullArray(bean.getCwOrgName(),i));
			cwOrgContact.setMobile(getNullArray(bean.getCwOrgMobile(),i));
			cwOrgContact.setIdType(getNullArray(bean.getCwOrgIdType(),i));
			cwOrgContact.setIdNo(getNullArray(bean.getCwOrgIdNo(),i));
			cwOrgContact.setOperator(loginId);
			cwOrgContact.setOrgId(org.getOrgId());
			cwOrgContact.setMainFlag(bean.getMainFlag()[i]);
			app.setCwOrgContact(cwOrgContact);
			
			Customer customer= new Customer();
			customer.setName(getNullArray(bean.getName(),i));
			customer.setMarriage(getNullArray(bean.getMarriage(),i));
			customer.setIdType(getNullArray(bean.getIdType(),i));
			customer.setIdNo(getNullArray(bean.getIdNo(),i));
			customer.setIndustry1(getNullArray(bean.getIndustry1(),i));
			customer.setIndustry2(getNullArray(bean.getIndustry2(),i));
			customer.setIndustry3(getNullArray(bean.getIndustry3(),i));
			customer.setMobile(getNullArray(bean.getMobile(),i));
			customer.setAddProvince(getNullArray(bean.getAddProvince(),i));
			customer.setAddCity(getNullArray(bean.getAddCity(),i));
			customer.setAddCounty(getNullArray(bean.getAddCounty(),i));
			customer.setAddress(getNullArray(bean.getAddress(),i));
			customer.setOperator(loginId);
			customer.setOrgId(org.getOrgId());
			customer.setCustomerType("01");
			app.setCustomer(customer);
			
			
			Customer orgCustomer = new Customer();
			orgCustomer.setName(getNullArray(bean.getOrgName(),i));
			orgCustomer.setIdType(getNullArray(bean.getOrgIdType(),i));
			orgCustomer.setIdNo(getNullArray(bean.getOrgIdNo(),i));
			orgCustomer.setIndustry1(getNullArray(bean.getOrgIndustry1(),i));
			orgCustomer.setIndustry2(getNullArray(bean.getOrgIndustry2(),i));
			orgCustomer.setIndustry3(getNullArray(bean.getOrgIndustry3(),i));
			orgCustomer.setBizzScope(getNullArray(bean.getOrgBizzScope(),i));
			orgCustomer.setCompanyType(getNullArray(bean.getOrgCompanyType(),i));
			orgCustomer.setCustomerType("02");
			orgCustomer.setOperator(loginId);
			orgCustomer.setOrgId(org.getOrgId());
			app.setOrgCustomer(orgCustomer);
			
			lists.add(app);
		}
		return lists;
	}
	public static String getNullArray(String[] str,int i) {
		String array = "";
		if(str != null && !"".equals(str[i])){
			array = str[i];
		}
		return array;
	}
	public static int getNullArray(int[] str,int i) {
		int array=0;
		if(str != null){
			array = str[i];
		}
		return array;
	}
	public static double getNullArray(double[] str,int i) {
		double array = 0.0;
		if(str != null){
			array = str[i];
		}
		return array;
	}
	public static java.util.Date getNullArray(java.util.Date str[] ,int i) {
		java.util.Date array = null ;
		if(str != null){
			array = str[i];
		}
		return array;
	}

}
