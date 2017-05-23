package com.hikootech.mqcash.constants;

/**
 * @author yuwei
 * 2015年8月11日
 * 通用一些常量
 */
public class CommonConstants {
	
	/**默认操作人*/
	public final static String DEFAULT_OPERATOR = "user";
	
	/**银行卡类型，储蓄卡*/
 	public final static Integer CARD_TYPE_SAVINGS_CARD = 0;
	/**银行卡类型，信用卡*/
 	public final static Integer CARD_TYPE_CREDIT_CARD = 1;
	
	/**登陆方式：动态短信*/
	public final static Integer LOGIN_METHOD_SM = 0;
	/**登陆方式：账号密码*/
	public final static Integer LOGIN_METHOD_PWD = 1;
	
	/**登陆结果：成功*/
	public final static Integer LOGIN_RESULT_SUCCESS = 1;
	/**登陆结果：失败*/
	public final static Integer LOGIN_RESULT_FAIL = 0;
	
	
	/** 默认支付公司：   中金  需改成第三方配置表中的id  */
 	public final static String PAYMENT_CHANNEL_ID_CPCN = "CPCN";
 	/**第三方支付渠道：微信*/
 	public final static String PAYMENT_CHANNEL_ID_WX = "WX";
	
	/** 支付类型：0被动支付（代收）    */
	public final static Integer PAYMENT_TYPE_PASSIVE = 0;
	/** 支付类型：1主动支付（用户主动还款）  */
	public final static Integer PAYMENT_TYPE_ACTIVE = 1;
	/** 支付类型：2 微信扫码支付  */
	public final static Integer PAYMENT_TYPE_WX_NATIVE = 2;
	
	/**"轻松分期，畅想生活","账单中心","合同中心","个人中心","用户中心"*/
	public final static String[] LOGO_MSG={"轻松分期，畅想生活","账单中心","合同中心","个人中心","用户中心","收银台"};
	


	/**还款订单表主键id前缀*/
	public final static String PAYMENT_ORDER_ID_PREFIX = "PYM";
	
	/**发送给第三方支付订单号前缀*/
	public final static String PAYMENT_ORDER_NO_PREFIX = "PYN";
	
	
	/**还款订单子表记录项主键id*/
	public final static String PAYMENT_RECORD_ITEM_ID_PREFIX = "PRI";
	
	/**还款订单成功表主键id*/
	public final static String REPAYMENT_ORDER_ID_PREFIX = "ROR";
	
	
	/**用户登录记录表id*/
	public final static String LOGIN_RECORD_ID_PREFIX = "LGN";
	
	/**用户银行卡绑定表id前缀*/
	public final static String BANK_CARD_ID_PREFIX = "CRD";
	
	/**银行卡绑定操作记录表id前缀*/
	public final static String LOG_REQUEST_VALIDATE_BANK_ID_PREFIX = "LGB";
	
	public final static String AREA_PARENT_CODE="000000";
	/**短信主要内容的分隔符*/
	public final static String SM_DATA_SEPARATOR="||";
	
	/**记录发送短信的相关信息id前缀*/
	public final static String SM_ORDER_ID_PREFIX = "SMS";
	
	/**<b>配置表中中度逾期天数限制key</b> : 超过该限制天数的计划不再进行短信催收和代收         */
 	public final static String  OVERDUE_DAYS_MIDDLE="OVERDUE_DAYS_MIDDLE";
	
	/**记录用户操作记录id前缀*/
	public final static String LOG_USER_INFO_PREFIX = "LUS";
	/**1001用户修改登陆手机号**/
	public final static String LOG_USER_INFO_UP_MOB="1001"; 
	/**1002用户修改登陆密码**/
	public final static String LOG_USER_INFO_UP_PSW="1002";
	/**1003用户修改用户信息**/
	public final static String LOG_USER_INFO_UP_BASE="1003"; 
	/**用户操作记录的结果：成功**/
	public final static int LOG_USER_INFO_UP_SUCCESS=1;
	/**用户操作记录的结果：失败**/
	public final static int LOG_USER_INFO_UP_FAIL=0;
	
	/**秒趣门户短信登录密码长度**/
	public final static int MQCASH_MOBILE_LOGIN_PWD_LENGTH=6;
	
	/**配置表中秒趣客服电话key*/
	public final static String CUSTOMER_SERVICE_TEL="CUSTOMER_SERVICE_TEL";
	
	/**配置表中秒趣门户地址key（不带http）*/
	public final static String  MQ_CASH_PORT_URL="MQ_CASH_PORT_URL";
	
	/**合作伙伴渠道（0-网厅，1-掌厅）*/
	public final static String SOURCE = new String("0,1");
	/**0：退货（打款前退货）、1：退款（打款后退货）、2：收货成功（收货）*/
	public final static String CHANGETYPE = new String("0,1,2");
	/**0：身份证审核不通过、1：用户主动退货*/
	public final static String REFUNDREASON = new String("0,1");
	
	/**账单画面现在最上面的账单序号*/
	public final static int MQ_CASH_INSTALMENT_SORT_ONE=1;
	public final static int MQ_CASH_INSTALMENT_SORT_TWO=2;
	public final static int MQ_CASH_INSTALMENT_SORT_THREEE=3;
	public final static int MQ_CASH_INSTALMENT_SORT_FOUR=4;
	
	/**用户是否将初始密码修改 0-否*/
	public final static Integer USER_PWD_UNMODIFY_STS = 0;
	/**用户是否将初始密码修改1-是*/
	public final static Integer USER_PWD_MODIFIED_STS = 1;
	
	/**绑卡发送验证码超限代码*/
	public final static String SEND_VALID_CODE_LIMIT = "VC001";
	
	/**绑定银行卡最大次数配置key*/
	public final static String  BIND_CARD_MAX_TIMES="BIND_CARD_MAX_TIMES";
}
