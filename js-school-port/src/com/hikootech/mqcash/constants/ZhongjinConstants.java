package com.hikootech.mqcash.constants;

/**
 * @author yuwei
 * 2015年8月20日
 * 中金一些常量
 */
public class ZhongjinConstants {
	
	/** 账户类型 个人 */
	public final static int ACCOUNT_TYPE_PRIVATE = 11;
	/** 账户类型 企业 */
	public final static int ACCOUNT_TYPE_ENTERPRICE = 12;
	
	/** 中金请求报文版本号 */
	public final static String REQUEST_VERSION = "2.1";
	/** 中金请求报文版本号 */
	public final static String RESPONSE_VERSION = "2.0";
	
	/** 中金请求报文版本号 */
	public final static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	/** 中金请求报文 证件类型：身份证 */
	public final static String IDENTIFICATION_TYPE_ID_CARD = "0";
	/**银行卡类型，储蓄卡*/
	public final static String CARD_TYPE_SAVINGS_CARD = "10";
	/**银行卡类型，信用卡*/
	public final static String CARD_TYPE_CREDIT_CARD = "20";
	
	
}
