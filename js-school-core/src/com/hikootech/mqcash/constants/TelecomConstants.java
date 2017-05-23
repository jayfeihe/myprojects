package com.hikootech.mqcash.constants;


/**  
 *   
 * ResultCodeConstants  
 *   
 * @function:(秒趣返回API平台的错误代码)  
 * @Description: 操作结果码定义
 * 第1位表示系统类型，1-秒趣分期系统；2-核心系统；
 * 第2-3位表示功能模块：如,21征信判断接口，22发送银行卡预留手机号验证码接口,23-绑定银行卡接口,24-确认分期信息接口，25-订单同步接口,26-退单接口，27-打款接口
 * 第4-6位表示序号，递增，最大999：如,001,002,003,...。
 * @create time:Nov 19, 2015 11:05:12 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class TelecomConstants {
	
	
	/*
	 * 参数为空
	 */
	public static final String API_PARAMS_NULL = "10";
	public static final String API_PARAMS_NULL_DESC = "系统繁忙，请稍后重试或联系客服";
	public static final String API_PARAMS_NULL_DETAIL = "参数为空";
	/*
	 * 参数合法性校验失败
	 */
	public static final String API_PARAMS_WRONG = "11";
	public static final String API_PARAMS_WRONG_DESC = "系统繁忙，请稍后重试或联系客服";
	public static final String API_PARAMS_WRONG_DETAIL = "参数合法性校验失败";
	/*
	 * 签名验证失败
	 */
	public static final String API_PARAMS_SIGN_WRONG = "12";
	public static final String API_PARAMS_SIGN_WRONG_DESC = "系统繁忙，请稍后重试或联系客服";
	public static final String API_PARAMS_SIGN_WRONG_DETAIL = "签名验证失败";
	/*
	 * 系统异常
	 */
	public static final String API_SYSTEM_EXCEPTION = "13";
	public static final String API_SYSTEM_EXCEPTION_DESC = "系统繁忙，请稍后重试，抱歉~";
	public static final String API_SYSTEM_EXCEPTION_DETAIL = "系统异常";
	/*
	 * 用户信息不存在
	 */
	public static final String API_SYSTEM_NO_USEER = "14";
	public static final String API_SYSTEM_NO_USEER_DESC = "系统繁忙，请稍后重试或联系客服";
	public static final String API_SYSTEM_NO_USEER_DETAIL = "用户信息不存在";
	
	
	/** 请求学籍核查接口返回代码 **/
	public static final String CHECK_SCHOOL_SUCCESS = "0";
	public static final String CHECK_SCHOOL_SUCCESS_DESC = "学籍核查通过";
	
	public static final String CHECK_SCHOOL_DEFAULT = "1";
	public static final String CHECK_SCHOOL_DEFAULT_DESC = "学籍核查未通过";
	
	/** 调用征信接口返回错误代码开始  CRE=credit */
	
	/*
	 * 征信通过
	 */
	public static final String CRE_SUCCESS = "0";
	public static final String CRE_SUCCESS_DESC = "该用户征信通过";
	
	
	/*
	 * 征信未通过
	 */
	public static final String CRE_FAIL = "1";
	public static final String CRE_FAIL_DESC = "本次征信暂未通过!";
	public static final String CRE_FAIL_DETAIL = "征信不通过";
	
	/*
	 * 有未还款的订单
	 */
	public static final String CRE_REPEAT_INST = "2";
	public static final String CRE_REPEAT_INST_DESC = "您已参与过秒趣分期活动，账单结清后可再次参与，谢谢！";
	public static final String CRE_REPEAT_INST_DETAIL = "有未还款的订单";

	
	/*
	 *用户姓名合法性校验
	 */
	public final static String CRE_NAME_CODE="3";
	public final static String CRE_NAME_CODE_DESC="您需要填写正确的姓名，谢谢~";
	
	/*
	 *好信一鉴通校验
	 */
	public final static String CRE_QH_YJT_CODE="4";
	public final static String CRE_QH_YJT_CODE_DESC="您需要填写正确的身份信息，谢谢~";
	
	
	/** 调用征信接口返回错误代码结束  */
	

}
