package com.hikootech.mqcash.constants;

/**
 * 微信支付相关常量
 * 
 * @author QYANZE
 *
 */
public class WxPayConstants {

	/**交易类型-原生支付*/
	public static final String TRADE_TYPE_NATIVE = "NATIVE";
	
	/**返回状态码-成功*/
	public static final String RETURN_CODE_SUCCESS = "SUCCESS";
	/**返回状态码-失败*/
	public static final String RETURN_CODE_FAIL = "FAIL";
	/**返回业务码-成功*/
	public static final String RESULT_CODE_SUCCESS = "SUCCESS";
	/**返回业务码-失败*/
	public static final String RESULT_CODE_FAIL = "FAIL";
	
	/**交易状态-成功*/
	public static final String TRADE_STATE_SUCCESS = "SUCCESS";
	/**交易状态-转入退款*/
	public static final String TRADE_STATE_REFUND = "REFUND";
	/**交易状态-未支付*/
	public static final String TRADE_STATE_NOTPAY = "NOTPAY";
	/**交易状态-已关闭*/
	public static final String TRADE_STATE_CLOSED = "CLOSED";
	/**交易状态-已撤销*/
	public static final String TRADE_STATE_REVOKED = "REVOKED";
	/**交易状态-用户支付中*/
	public static final String TRADE_STATE_USERPAYING = "USERPAYING";
	/**交易状态-支付失败(其他原因，如银行返回失败)*/
	public static final String TRADE_STATE_PAYERROR = "PAYERROR";
}
