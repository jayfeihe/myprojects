package com.hikootech.mqcash.constants;

/**
 * @author yuwei 2015年8月5日
 * 
 */
public class StatusConstants {

	
	/** 秒趣分期调用未通知状态 */
	public final static Integer MQ_NOTIFY_NO = 0;
	/** 秒趣分期调用已通知成功状态 */
	public final static Integer MQ_NOTIFY_SUCCESS = 1;
	/** 秒趣分期调用已通知失败状态 */
	public final static Integer MQ_NOTIFY_FAIL = 2;
	
	/** 秒趣分期合作伙伴调用失败状态 */
	public final static String PARTNER_CALL_FAIL = "0";
	/** 秒趣分期合作伙伴调用成功状态 */
	public final static String PARTNER_CALL_SUCCESS = "1";
	
	
	/** 用户不可用状态 */
	public final static Integer USER_UNEFFECTIVE = 0;
	/** 用户可用状态 */
	public final static Integer USER_EFFECTIVE = 1;

	/** 银行卡未绑定状态 */
	public final static Integer USER_BANK_CARD_UNBIND = 0;
	/** 银行卡绑定状态 */
	public final static Integer USER_BANK_CARD_BIND = 1;
	/** 银行卡解绑状态 */
	public final static Integer USER_BANK_CARD_RELIEVE_BIND = 2;
	/** 银行卡绑定中 */
	public final static Integer USER_BANK_CARD_BIND_DOING = 3;
	/** 银行卡绑定失败 */
	public final static Integer USER_BANK_CARD_BIND_FAIL = 4;

	/** 分期配置不可用状态 */
	public final static Integer CONFIG_INSTALMENT_UNEFFECTIVE = 0;
	/** 分期配置可用状态 */
	public final static Integer CONFIG_INSTALMENT_EFFECTIVE = 1;

	/** 分期配置计划不可用状态 */
	public final static Integer CONFIG_INSTALMENT_PLAN_UNEFFECTIVE = 0;
	/** 分期配置计划可用状态 */
	public final static Integer CONFIG_INSTALMENT_PLAN_EFFECTIVE = 1;

	
	
	
	
	/** 合同协议状态 10待审核    */
	public final static Integer PROTOCAL_STATUS_AWAIT_AUDIT = 10;
	/** 合同协议状态 20执行中    */
	public final static Integer PROTOCAL_STATUS_DOING_EFFECTIVE = 20;
	/** 合同协议状态 30结束    */
	public final static Integer PROTOCAL_STATUS_COMPLETED = 30;
	/** 合同协议状态 40取消订单意外中止    */
	public final static Integer PROTOCAL_STATUS_CANCEL_ORDER = 40;
	/** 合同协议状态 50退货意外中止    */
	public final static Integer PROTOCAL_STATUS_RETURN_GOODS = 50;
				
	/** 分期计划状态：10初始化 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_INIT = 10;
	/** 分期计划状态：20待还款 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY = 20;
	/** 分期计划状态：30还款中 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY = 30;
	/** 分期计划状态：40已还款 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED = 40;
	/** 分期计划状态：50已逾期 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE = 50;
	/** 分期计划状态：60已取消 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_CANCELED = 60;
	/** 分期计划状态：70已中止 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_SUSPENDED = 70;
	/** 分期计划状态：80待退款 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_REFUND = 80;

	/** 分期还款计划代收锁定：0 不锁定 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_REPAY_UNLOCK = 0;
	/** 分期还款计划代收锁定：1 锁定 */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_REPAY_LOCK = 1;

	/** 分期订单状态：10 初始化 */
	public final static Integer INSTALMENT_STATUS_INIT = 10;
	/** 分期订单状态：20待还款 */
	public final static Integer INSTALMENT_STATUS_AWAIT_PAY = 20;
	/** 分期订单状态：30已逾期 */
	public final static Integer INSTALMENT_STATUS_OVERDUE = 30;
	/** 分期订单状态：40已结清 */
	public final static Integer INSTALMENT_STATUS_COMPLETED = 40;
	/** 分期订单状态：50已取消 */
	public final static Integer INSTALMENT_STATUS_CANCELED = 50;
	/** 分期订单状态：60已中止 */
	public final static Integer INSTALMENT_STATUS_SUSPENDED = 60;
	/** 分期订单状态：70待退款 */
	public final static Integer INSTALMENT_STATUS_AWAIT_REFUND = 70;

	
	
	
	
	
	/** 征信中金查询成功状态 */
	public final static Integer CREDIT_QUERY_STATUS_SUCCESS = 0;
	/** 征信中金查询成功有数据状态 */
	public final static Integer CREDIT_QUERY_STATUS_SUCCESS_GET_DATA = 0;
	/** 征信中金查询成功无数据状态 */
	public final static Integer CREDIT_QUERY_STATUS_SUCCESS_NO_DATA = 1;

	
	
	
	
	
	// 用户主动和被动支付订单表常量
	/** 支付订单支付状态：10待支付    */
	public final static Integer PAYMENT_ORDER_STATUS_AWAIT_PAY = 10;
	/** 支付订单支付状态：20处理中    */
	public final static Integer PAYMENT_ORDER_STATUS_DEALING = 20;
	/** 支付订单支付状态：30支付成功    */
	public final static Integer PAYMENT_ORDER_STATUS_PAY_SUCCESS = 30;
	/** 支付订单支付状态：40支付失败    */
	public final static Integer PAYMENT_ORDER_STATUS_PAY_FAILED = 40;
	/** 支付订单支付状态：50重复还款    */
	public final static Integer PAYMENT_ORDER_STATUS_PAY_AGAIN = 50;

	/** 发送状态:10待请求    */
	public final static Integer REQUEST_STATUS_AWAIT = 10;
	/** 发送状态:20请求成功  */
	public final static Integer REQUEST_STATUS_SUCCESS = 20;
	/** 发送状态:30请求失败    */
	public final static Integer REQUEST_STATUS_FAILED = 30;
	
	/** 支付类型：0全部付款   */
	public final static Integer PAYMENT_TYPE_PAYALL = 0;
	
	/** 子表支付状态： 10未支付    */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_AWAIT_PAY = 10;
	/** 子表支付状态：   20支付成功    */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_PAY_SUCCESS = 20;
	/** 子表支付状态： 30重复还款     */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_PAY_AGAIN = 30;
	/** 子表支付状态： 40已退款    */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_REFUNDED = 40;

	
	
	
	
	
	/** 银行卡绑定操作记录类型 0 请求验证银行卡四要素 */
	public final static Integer BANK_CARD_OPERATION_VALIDATE = 0;
	/** 银行卡绑定操作记录类型 1绑定银行卡 */
	public final static Integer BANK_CARD_OPERATION_BIND = 1;

	/** 发送短信状态：待发送 */
	public final static Integer SEND_SMS_WAITTING = 0;
	/** 发送短信状态：成功 */
	public final static Integer SEND_SMS_SUCCESS = 1;
	/** 发送短信状态：失败 */
	public final static Integer SEND_SMS_FAILED = 2;

	/** 发送短信类型：延迟发送 */
	public final static Integer SEND_SMS_TYPE_LATER = 0;
	/** 发送短信类型：立即发送 */
	public final static Integer SEND_SMS_TYPE_NOW = 1;

}
