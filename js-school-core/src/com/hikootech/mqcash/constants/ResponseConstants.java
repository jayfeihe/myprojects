package com.hikootech.mqcash.constants;

 

	/**
	* 此类描述的是：
	* @author: zhaohefei
	* @version: 2016年3月11日 下午3:03:02
	*/
	
public class ResponseConstants {
	
	/** 返回成功码 */
	public static final String SUCCESS = "0";
	/** 返回失败码 */
	public static final String FAIL = "1";
	/** 返回非法请求码 */
	public static final String UNVALID = "-1";
	/** 返回异常请求码 */
	public static final String EXCEPTION = "2";
	
	/** 返回码 */
	public static final String RETURN_CODE = "resultCode";
	/** 返回信息描述 */
	public static final String RETURN_DESC = "desc";
	
	/** 内部接口错误码：参数为空 */
	public static final String INNER_INTERFACE_PARAM_NULL = "10";
	/** 内部接口错误码：参数合法性校验失败 */
	public static final String INNER_INTERFACE_VALIDATION = "11";
	/** 内部接口错误码：签名验证失败 */
	public static final String INNER_INTERFACE_SIGN_FAILED = "12";
	/** 内部接口错误码：系统异常 */
	public static final String INNER_INTERFACE_SYSTEM_EXCEPTION = "13";
	
	
	/** 返回码 */
	public static final String RESULT_CODE = "resultCode";
	
	/** 返回信息描述 */
	public static final String RESULT_DESC = "desc";
	
	/**接口内部发生错误时返回的错误码*/
	public static final String ERROR_CODE = "errorCode";
	
	/**接口返回的对象的json字符串*/
	public static final String DATA = "data";
	
	/**调用第三方至解析结果时全部正常*/
	public static final String QHZX_RESULT_CODE_SUCCESS="0";
	
	/**调用第三方至解析结果时发生异常，导致失败*/
	public static final String QHZX_RESULT_CODE_FAIL="20";
	
	/**参数为空错误码*/
	public static final String MQ_EX_PARAM_NULL="10";
	
	/**错误码:验证失败*/
	public static final String MQ_EX_SIGN_WRONG = "12";
	
	/**错误信息：非E000000成功状态*/
	public static final String MQ_EX_ERCODE_FAILED = "13";
	
	
	/**错误码:与第三方交互发生错误*/
	public static final String MQ_EX_INTERACTION_THIRD="100";
	
	/**错误码:组装第三方报文发生错误*/
	public static final String MQ_EX_MAKE_UP_THIRD_MSG="101";
	
	/**错误码:解析第三方报文发生错误*/
	public static final String MQ_EX_RESOLUTION_THIRD_MSG="102";
	
	/**错误码:系统异常 数据异常*/
	public static final String MQ_EX_DATABASE="200";
	 
	
}
