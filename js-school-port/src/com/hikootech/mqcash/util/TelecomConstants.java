package com.hikootech.mqcash.util;


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
	public static final String API_PARAMS_NULL_DESC = "参数为空";
	/*
	 * 参数合法性校验失败
	 */
	public static final String API_PARAMS_WRONG = "11";
	public static final String API_PARAMS_WRONG_DESC = "参数合法性校验失败";
	/*
	 * 签名验证失败
	 */
	public static final String API_PARAMS_SIGN_WRONG = "12";
	public static final String API_PARAMS_SIGN_WRONG_DESC = "签名验证失败";
	/*
	 * 系统异常
	 */
	public static final String API_SYSTEM_EXCEPTION = "13";
	public static final String API_SYSTEM_EXCEPTION_DESC = "系统异常";
	/*
	 * 用户信息不存在
	 */
	public static final String API_SYSTEM_NO_USEER = "14";
	public static final String API_SYSTEM_NO_USEER_DESC = "用户信息不存在";
	
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
	public static final String CRE_FAIL_DESC = "征信不通过";
	
	
	/*
	 * 征信未通过
	 */
	public static final String CRE_REPEAT_INST = "2";
	public static final String CRE_REPEAT_INST_DESC = "有未还款的订单";
	
	/*
	 * 前海/国政通/企信 报错的情况下提示
	 */
	public final static String CRE_ERROR_CODE="3";
	public final static String CRE_ERROR_MSG="系统繁忙，请稍后重试！";
	
	/** 调用征信接口返回错误代码结束  */
	
	/** 发送银行卡预留手机号验证码接口开始  BIM=bindMobile*/
	/*
	 * 发送银行卡预留手机号验证码发送短信成功
	 */
	public static final String BIM_SUCCESS = "0";
	public static final String BIM_SUCCESS_DESC = "发送成功";
	
	/*
	 * 发送银行卡预留手机号验证码发送短信发送失败
	 */
	public static final String BIM_FAIL = "1";
	public static final String BIM_FAIL_DESC = "发送失败";
	/*
	 * 发送银行卡预留手机号验证码发送短信不支持该银行
	 */
	public static final String BIM_BANK_NOT_SUPPORT = "2";
	public static final String BIM_BANK_NOT_SUPPORT_DESC = "不支持该银行";
	
	/*
	 * 发送银行卡预留手机号验证码发送短信征信不通过
	 */
	public static final String BIM_CRE_FAIL = "3";
	public static final String BIM_CRE_FAIL_DESC = "征信不通过";
	
	/*
	 * 发送银行卡预留手机号验证码发送短信征信超时
	 */
	public static final String BIM_CRE_TIME_OUT = "4";
	public static final String BIM_CRE_TIME_OUT_DESC = "征信超时";
	
	
	 /* 用户征信信息不存在
	 */
	public static final String BIM_CRE_NO_USER = "6";
	public static final String BIM_CRE_NO_USER_DESC = "无征信记录";
	
	
	/** 发送银行卡预留手机号验证码接口结束  */
	
	
	/** 绑定银行卡接口开始  BIC=binkCard*/
	/*
	 * 绑定银行卡成功
	 */
	public static final String BIC_SUCCESS = "0";
	public static final String BIC_SUCCESS_DESC = "绑定成功";
	
	/*
	 * 绑定银行卡失败
	 */
	public static final String BIC_FAIL = "1";
	public static final String BIC_FAIL_DESC = "绑定失败";
	
	/*
	 * 绑定银行卡验证码不存在
	 */
	public static final String BIC_VALID_CODE = "2";
	public static final String BIC_VALID_CODE_DESC = "验证码不存在";
	
	/*
	 * 验证码超时
	 */
	public static final String BIC_VALID_CODE_TIME_OUT = "3";
	public static final String BIC_VALID_CODE_TIME_OUT_DESC = "验证码超时";
	
	/*
	 * 验证码错误
	 */
	public static final String BIC_VALID_CODE_ERROR = "4";
	public static final String BIC_VALID_CODE_ERROR_DESC = "验证码错误";
	
	/*
	 * 绑定中
	 */
	public static final String BIC_VALID_CODE_BINDING = "5";
	public static final String BIC_VALID_CODE_BINDING_DESC = "绑定中";
	/*
	 * 绑卡卡号和发送验证码的卡号不一致
	 */
	public static final String BIC_BANK_CARD_ERROR = "6";
	public static final String BIC_BANK_CARD_ERROR_DESC = "绑卡卡号和发送验证码的卡号不一致";
	
	/*
	 * 绑卡预留手机号和发送验证码预留手机号不一致
	 */
	public static final String BIC_RESERVEMOBILE_ERROR = "7";
	public static final String BIC_RESERVEMOBILE_ERROR_DESC = "绑卡预留手机号和发送验证码预留手机号不一致";
	
	/*
	 * 发送次数超限
	 */
	public static final String BIC_VALID_CODE_FLOW = "8";
	public static final String BIC_VALID_CODE_FLOW_DESC = "绑定次数超限";
	
	/*
	 * 未发送验证码
	 */
	public static final String BIC_VALID_CODE_NOSEND = "9";
	public static final String BIC_VALID_CODE_NOSEND_DESC = "未发送验证码";
	/*
	 * 发送次数超限
	 */
	public static final String BIC_VALIDED_CODE = "10";
	public static final String BIC_VALIDED_CODE_DESC = "已绑定成功";
	
	/**绑定银行卡接口结束  */
	
	
	/** 确认分期接口开始 INS=instalment */
	/*
	 *分期确认成功
	 */
	public static final String INS_SUCCESS = "0";
	public static final String INS_SUCCESS_DESC = "成功";
	
	/*
	 * 分期确认失败
	 */
	public static final String INS_FAIL = "1";
	public static final String INS_FAIL_DESC = "提交信息失败";
	
	/*
	 *征信超时
	 */
	public static final String INS_CRE_TIME_OUT = "2";
	public static final String INS_CRE_TIME_OUT_DESC = "征信超时";
	
	/*
	 * 征信不通过
	 */
	public static final String INS_CRE_FAIL = "3";
	public static final String INS_CRE_FAIL_DESC = "征信不通过";
	
	/*
	 * 该银行卡未绑定
	 */
	public static final String INS_BANK_CARD_FAIL = "4";
	public static final String INS_BANK_CARD_FAIL_DESC = "该银行卡未绑定";
	
	 /* 用户征信信息不存在
	 */
	public static final String INS_CRE_NO_USER = "5";
	public static final String INS_CRE_NO_USER_DESC = "无征信记录";
	

	/*
	 * 分期数错误
	 */
	public static final String INS_NUMBER_ERROR = "8";
	public static final String INS_NUMBER_ERROR_DESC = "分期数错误";
	
	/**确认分期接口结束  */
	
	
	/** 订单同步接口开始 SYC=synchronous */
	/*
	 *订单同步成功
	 */
	public static final String SYC_SUCCESS = "0";
	public static final String SYC_SUCCESS_DESC = "成功";
	
	
	/*
	 * 同步订单不存在
	 */
	public static final String SYC_ORDER_NO_EXIST = "1";
	public static final String SYC_ORDER_NO_EXIST_DESC = "同步订单不存在"; 
	
	/*
	 * 同步订单失败，拒贷
	 */
	public static final String SYC_ORDER_REFUSE = "2";
	public static final String SYC_ORDER_REFUSE_DESC = "同步订单失败，拒贷"; 
	/*
	 * 同步订单失败，系统有误
	 */
	public static final String SYC_ORDER_FAIL = "3";
	public static final String SYC_ORDER_FAIL_DESC = "同步订单失败，系统有误"; 
	/*
	 * 同步订单已存在
	 */
	public static final String SYC_ORDER_EXIST = "4";
	public static final String SYC_ORDER_EXIST_DESC = "同步订单已存在";
	
	/*
	 * 同步订单金额有误
	 */
	public static final String SYC_ORDER_MONEY_ERROR = "5";
	public static final String SYC_ORDER_MONEY_ERROR_DESC = "同步订单金额有误"; 
	
	/*
	 * 收货人城市错误
	 */
	public static final String SYC_ORDER_RECE_PRO_ERROR = "6";
	public static final String SYC_ORDER_RECE_PRO_ERROR_DESC = "收货人省份错误"; 
	
	/*
	 * 家庭地址城市错误
	 */
	public static final String SYC_ORDER_HOME_PRO_ERROR = "7";
	public static final String SYC_ORDER_HOME_PRO_ERROR_DESC = "家庭地址省份错误"; 
	
	/*
	 * 单位地址城市错误
	 */
	public static final String SYC_ORDER_COM_PRO_ERROR = "8";
	public static final String SYC_ORDER_COM_PRO_ERROR_DESC = "单位地址省份错误"; 
	
	/*
	 * 业务唯一标识序列号不存在
	 */
	public static final String SYC_ORDER_NO_INFOID = "9";
	public static final String SYC_ORDER_NO_INFOID_DESC = "业务唯一标识序列号不存在"; 
	
	/*
	 * 身份证号不存在
	 */
	public static final String SYC_ORDER_NO_IDCARD = "10";
	public static final String SYC_ORDER_NO_IDCARD_DESC = "身份证号不存在"; 
	
	/*
	 * 同步订单手机号不存在
	 */
	public static final String SYC_ORDER_NO_TELNUM = "11";
	public static final String SYC_ORDER_NO_TELNUM_DESC = "手机号不存在"; 
	
	/*
	 * 同步订单订单来源不正确
	 */
	public static final String SYC_ORDER_SOURCE_ERROR = "12";
	public static final String SYC_ORDER_SOURCE_ERROR_DESC = "订单来源不正确"; 
	
	/*
	 * 有未还款的订单
	 */
	public static final String SYC_UNPAY_ORDER = "13";
	public static final String SYC_UNPAY_ORDER_DESC = "有未还款的订单";
	
	/*
	 * 商品金额不一致
	 */
	public static final String SYC_PRODUCT_MONEY_ERROR = "14";
	public static final String SYC_PRODUCT_MONEY_ERROR_DESC = "商品金额不一致";
	
	/*
	 * 套餐金额不一致
	 */
	public static final String SYC_PLAN_MONEY_ERROR = "15";
	public static final String SYC_PLAN_MONEY_ERROR_DESC = "套餐金额不一致";
	
	/*
	 * 申办人姓名和收货人姓名不一致！
	 */
	public static final String SYC_RECEIVER_ERROR = "16";
	public static final String SYC_RECEIVER_ERROR_DESC = "申办人姓名和收货人姓名不一致";
	
	
	/**订单同步接口结束  */
	
	/** 订单状态变更接口开始 REF=Refund */
	/*
	 *成功
	 */
	public static final String CHANGE_SUCCESS = "0";
	public static final String CHANGE_SUCCESS_DESC = "处理成功";
	
	
	/*
	 * 电信订单号不存在
	 */
	public static final String CHANGE_DX_ORDER_NO_EXIST = "1";
	public static final String CHANGE_DX_ORDER_NO_EXIST_DESC = "电信订单号不存在"; 
	/*
	 * 秒趣订单号不存在
	 */
	public static final String CHANGE_MQ_ORDER_NO_EXIST = "2";
	public static final String CHANGE_MQ_ORDER_NO_EXIST_DESC = "秒趣订单号不存在"; 
	/*
	 * 退单金额有误
	 */
	public static final String CHANGE_ORDER_AMOUNT_ERROR = "3";
	public static final String CHANGE_ORDER_AMOUNT_ERROR_DESC = "退单金额有误"; 
	/*
	 * 重复退货
	 */
	public static final String CHANGE_RETURN_REPEAT = "4";
	public static final String CHANGE_RETURN_REPEAT_DESC = "已成功退货"; 
	/*
	 * 不符合收货完成条件
	 */
	public static final String CHANGE_RECEIVE_FAIL = "5";
	public static final String CHANGE_RECEIVE_FAIL_DESC = "不符合收货完成条件"; 
	
	/*
	 * 订单已退
	 */
	public static final String CHANGE_REFUND_FINISH = "6";
	public static final String CHANGE_REFUND_FINISH_DESC = "已成功退单"; 
	
	/*
	 * 订单状态不能申请退单
	 */
	public static final String CHANGE_REFUND_BILL_STATUS_ERROR = "7";
	public static final String CHANGE_REFUND_BILL_STATUS_ERROR_DESC = "不符合退单条件"; 
	
	/*
	 * 订单状态已收货成功
	 */
	public static final String CHANGE_RECE_FINISH = "8";
	public static final String CHANGE_RECE_FINISH_DESC = "已收货成功"; 
	
	/*
	 * 不符合退款条件
	 */
	public static final String CHANGE_REFUND_STATUS_ERROR = "9";
	public static final String CHANGE_REFUND_STATUS_ERROR_DESC = "不符合退款条件"; 
	/*
	 * 退单金额大于下单金额
	 */
	public static final String CHANGE_REFUND_BILL_AMOUNT_ERROR = "10";
	public static final String CHANGE_REFUND_BILL_AMOUNT_ERROR_DESC = "退单金额大于下单金额"; 
	/*
	 * 退款金额大于下单金额
	 */
	public static final String CHANGE_REFUND_AMOUNT_ERROR = "11";
	public static final String CHANGE_REFUND_AMOUNT_ERROR_DESC = "退款金额大于下单金额"; 
	
	/*
	 * 资方状态为打款中，暂不支持退款操作
	 */
	public static final String CHANGE_REFUND_TRANSFERING = "12";
	public static final String CHANGE_REFUND_TRANSFERING_DESC = "打款中,暂不支持退款"; 
	
	/**订单状态变更接口结束  */
	
	

}
