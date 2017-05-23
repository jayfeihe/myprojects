package com.hikootech.mqcash.po;

/** 
* @ClassName MqcashException 
* @Description 秒趣自定义异常 service层抛出该异常 
* @author 余巍 yuweiqwe@126.com 
* @date 2015年12月14日 下午6:45:44 
*  
*/
public class MqcashException extends RuntimeException {

	/** 
	* @Fields serialVersionUID : 序列化版本号 
	*/ 
	private static final long serialVersionUID = -8658319460477004864L;
	
	/** 
	* @Fields errorCode : 错误码，用来区分异常种类
	*/ 
	private String errorCode;
	/** 
	* @Fields errorMsg : 错误消息
	*/ 
	private String errorMsg;

	public MqcashException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public MqcashException(String errorCode, String errorMsg, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		// TODO Auto-generated constructor stub
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
