package com.hikootech.mqcash.constants;

/**
 * @author yuwei
 * 2015年8月5日
 * 返回信息常量
 */
public class ResponseConstants {
	
	/** 返回成功码 */
	public static final String SUCCESS = "0";
	/** 返回失败码 */
	public static final String FAIL = "1";
	
	/** 立即付款画面，前后台金额不对返回失败码 */
	public static final String FAIL_PAYNOW = "0xF1";
	/** 返回超时请求码 */
	public static final String UNVALID = "-1";
	
	/** 返回码 */
	public static final String RETURN_CODE = "code";
	/** 返回信息描述 */
	public static final String RETURN_DESC = "desc";
	
}
