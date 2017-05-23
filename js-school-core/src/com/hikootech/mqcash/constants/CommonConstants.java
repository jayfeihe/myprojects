package com.hikootech.mqcash.constants;

/**
 * @author yuwei
 * 2015年8月11日
 * 通用一些常量
 */
public class CommonConstants {
	
	public final static String idCardSymbol = "***********";
	
	public final static String VALIDATE_BIND_MOBILE_MSG_TEMPLATE = "您的手机号已被占用，请您根据验证码#进行验证。";
	
	/** 默认系统字符集 utf-8 */
	public final static String DEFAULT_CHARSET = "utf-8";
	
	/**默认操作人*/
	public final static String DEFAULT_OPERATOR = "system";
	
	/**银行卡类型，储蓄卡*/
	public final static Integer CARD_TYPE_SAVINGS_CARD = 0;
	/**银行卡类型，信用卡*/
	public final static Integer CARD_TYPE_CREDIT_CARD = 1;
	
	/**性别：女*/
	public final static Integer SEX_FEMALE = 0;
	/**性别：男*/
	public final static Integer SEX_MALE = 1;
	
	/** 支付类型：0被动支付（代收）    */
	public final static String PAYMENT_CHANNEL_ID_CPCN = "CPCN";
	/**第三方支付渠道：微信*/
 	public final static String PAYMENT_CHANNEL_ID_WX = "WX";
	
	/** 支付类型：0被动支付（代收）    */
	public final static Integer PAYMENT_TYPE_PASSIVE = 0;
	/** 支付类型：1主动支付（用户主动还款）  */
	public final static Integer PAYMENT_TYPE_ACTIVE = 1;
	/** 支付类型：2 微信扫码支付  */
	public final static Integer PAYMENT_TYPE_WX_NATIVE = 2;
	
	/** 支付方式：0网银支付    */
	public final static Integer PAYMENT_METHOD_BANK_CARD = 0;
	
	
	/**短信主要内容的分隔符*/
	public final static String SM_DATA_SEPARATOR="||";
	
	/**短信主要内容的分隔符*/
	public final static String SM_DATA_SPLIT_SEPARATOR="\\|\\|";
	
	/**征信信息配置表key*/
	public final static String CONFIG_KV_CREDIT_KEY = "CONFIG_KV_CREDIT_KEY";
	
	/**企信征信成功返回码*/
	public final static String QX_CREDIT_SUCCESS = "9999";
	
	/**合作伙伴渠道（0-网厅，1-掌厅）*/
	public final static String SOURCE = new String("0,1");
	/**用户所使用的平台（1：微信 2：APP 3：web）*/
	public final static String CHANNELID = new String("1,2,3");

	/**************************表主键配置开始******************************************/
	/**还款订单批次号*/
	public final static String PAYMENT_BATCH_NO_PREFIX = "PBN";
	
	/**告警表前缀*/
	public final static String SYS_ALARM_ID_PREFIX = "ALM";
			
	/**还款订单表主键id前缀*/
	public final static String PAYMENT_ORDER_ID_PREFIX = "CYM";
	
	/**发送给第三方支付流水号id前缀*/
	public final static String PAYMENT_SEQ_PREFIX = "CAY";
	
	/**还款订单子表记录项主键id*/
	public final static String PAYMENT_RECORD_ITEM_ID_PREFIX = "CRI";
	
	/**还款订单成功表主键id*/
	public final static String REPAYMENT_ORDER_ID_PREFIX = "ROR";
	
	/**逾期罚息计算日志表id*/
	public final static String OVERDUE_LOG_ID_PREFIX = "ODL";
	
	/**用户征信信息表id*/
	public final static String CREDIT_INFO_ID_PREFIX = "CIN";
	
	/**用户征信请求记录表id*/
	public final static String CREDIT_REQUEST_RECORD_ID_PREFIX = "CRR";
	
	/**用户手机信用信息表id*/
	public final static String MOBILE_CREDIT_INFO_ID_PREFIX = "MIN";
	
	/**用户手机信用明细信息表id*/
	public final static String MOBILE_CREDIT_DETAIL_INFO_ID_PREFIX = "MDIN";
	
	/**银行卡绑定操作记录表id前缀*/
	public final static String LOG_REQUEST_VALIDATE_BANK_ID_PREFIX = "LGB";
	
	/**前海征信交易流水号前缀*/
	public final static String QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX = "QHT";
	
	/**前海征信子批次号前缀*/
	public final static String QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX = "QHS";
	
	/**记录发送短信的相关信息id前缀*/
	public final static String SM_ORDER_ID_PREFIX = "SMS";
	
	/**各渠道来源下单成功的分期单统计记录id前缀*/
	public final static String RECORD_PARTNER_ORDER_STATISTIC_ID_PREFIX = "OSS";
	
	/**征信结果记录表记录id前缀*/
	public final static String CREDIT_RECORD_ID_PREFIX = "CRE";
	
	/**电信提交的所有的用户数据临时表id前缀*/
	public final static String TOTAL_INFO_ID_PREFIX = "MOR";
	
	/**用户订单状态变更日志表前缀*/
	public final static String USER_ORDER_STATUS_LOG_ID_PREFIX = "LGS";
	
	/**各征信模块征信结果表前缀*/
	public final static String USER_CREIDT_MODEL_RECORD_ID_PREFIX = "CMR";
	
	/**百融信贷请求参数表前缀*/
	public final static String USER_BR_REQ_ID_PREFIX = "BRD";
	/**百融信贷特殊名单核查表前缀*/
	public final static String USER_BR_REQ_SPECIALLIST_ID_PREFIX = "BRS";
	/**百融信贷多次申请核查前缀*/
	public final static String USER_BR_REQ_APPLYLOAD_ID_PREFIX = "BRA";
	
	
	/**学生评分信息表id*/
	public final static String EDUCATION_SCORE_ID_PREFIX = "EDC";
	
	/**学籍核查数据IDid*/
	public final static String CHECK_SCHOOL_ID_PREFIX = "CSH";
	
	/**银联数据报告表id*/
	public final static String UNIPAY_REPORT = "UR";
	/**银联数据分类表id*/
	public final static String UNIPAY_REPORT_CATEGORY = "URCG";
	/**银联数据城市表id*/
	public final static String UNIPAY_REPORT_CITY = "URCT";
	/**银联数据消费表id*/
	public final static String UNIPAY_REPORT_CONSUME = "URCS";
	/**银联数据信用表id*/
	public final static String UNIPAY_REPORT_CREDIT = "URCR";
	/**************************表主键配置结束******************************************/
	


	/**<b>配置表中中度逾期天数限制key</b> : 超过该限制天数的计划不再进行短信催收和代收         */
 	public final static String  OVERDUE_DAYS_MIDDLE="OVERDUE_DAYS_MIDDLE";
	
	/**配置逾期客户发送短信内容中存款时间*/
	public final static String  OVERDUE_DEPOSIT_TIME="OVERDUE_DEPOSIT_TIME";
	
	/**秒趣门户网址*/
	public final static String  MQ_CASH_PORT_URL="MQ_CASH_PORT_URL";
	
	/**客服电话*/
	public final static String  CUSTOMER_SERVICE_TEL="CUSTOMER_SERVICE_TEL";
	
	/**通知资方打款最大次数*/
	public final static String  PROVIDER_MAX_SEND_TIMES="PROVIDER_MAX_SEND_TIMES";
	
	/**百融贷登录tokenid--key*/
	public final static String BR_USER_LOGIN_TOKEN_KEY = "BR_USER_LOGIN_TOKEN_KEY";
	/**百融贷特殊名单核查*/
	public final static String BR_MEAL_SPECIALLIST_C = "SpecialList_c";
	/**百融贷多次申请核查*/
	public final static String BR_MEAL_APPLYLOAN = "ApplyLoan";
	
	/**江苏电信电渠对应 中金支付订单交易流水号前缀*/
	public static final String PARTNER_10000_PAYMENT_ORDER_ID_PREFIX = "10000";
	/**江苏电信校园对应 中金支付订单交易流水号前缀*/
	public static final String PARTNER_10001_PAYMENT_ORDER_ID_PREFIX = "10001";
 
	/**************************配置表key结束******************************************/

}
