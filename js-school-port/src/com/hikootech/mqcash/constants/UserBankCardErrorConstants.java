package com.hikootech.mqcash.constants;

/**
 * @author yuwei
 * 2015年8月5日
 * 用户信息错误常量类
 * AA BBB CC DDD
   MQ 001 01 001 
   AA: 平台代码
   BBB: 系统代码 
   CC: 错误类型
   DDD: 具体错误码
 */
public class UserBankCardErrorConstants {
	
	public static final String ERROR_CODE_BANKID = "MQ00102001";
	public static final String ERROR_CODE_BANKID_DESC = "银行ID校验失败";
	
	public static final String ERROR_CODE_OWNERNAME = "MQ00102002";
	public static final String ERROR_CODE_OWNERNAME_DESC = "持卡人姓名校验失败";
	
	public static final String ERROR_CODE_IDCARD = "MQ00102003";
	public static final String ERROR_CODE_IDCARD_DESC = "持卡人身份证号校验失败";
	
	public static final String ERROR_CODE_CARDNUMBER = "MQ00102004";
	public static final String ERROR_CODE_CARDNUMBER_DESC = "卡号校验校验失败";
	
	public static final String ERROR_CODE_RESERVEMOBILE = "MQ00102005";
	public static final String ERROR_CODE_RESERVEMOBILE_DESC = "预留手机号校验失败";
	
	public static final String ERROR_CODE_SMCODE = "MQ00102006";
	public static final String ERROR_CODE_SMCODE_DESC = "绑定银行校验验证码为空";
	
}
