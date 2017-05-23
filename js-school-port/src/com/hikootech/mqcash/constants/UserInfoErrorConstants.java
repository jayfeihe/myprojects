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
public class UserInfoErrorConstants {
	
	public static final String ERROR_SESSION_USER_MISS = "MQ00101000";
	public static final String ERROR_SESSION_USER_MISS_DESC = "用户连接超时";
	
	public static final String ERROR_CODE_NAME = "MQ00101001";
	public static final String ERROR_CODE_NAME_DESC = "用户真实姓名校验失败";
	
	public static final String ERROR_CODE_IDCARD = "MQ00101002";
	public static final String ERROR_CODE_IDCARD_DESC = "用户身份证校验失败";
	
	public static final String ERROR_CODE_HOMEADDRESS = "MQ00101003";
	public static final String ERROR_CODE_HOMEADDRESS_DESC = "用户家庭地址校验失败";
	
	public static final String ERROR_CODE_COMPANYNAME = "MQ00101004";
	public static final String ERROR_CODE_COMPANYNAME_DESC = "用户公司名称校验失败";
	
	public static final String ERROR_CODE_COMPANYADDRESS = "MQ00101005";
	public static final String ERROR_CODE_COMPANYADDRESS_DESC = "用户公司地址校验失败";
	
}
