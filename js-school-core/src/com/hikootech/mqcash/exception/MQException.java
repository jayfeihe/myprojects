package com.hikootech.mqcash.exception;

/** 
* @ClassName MQException 
* @Description 秒趣自定义业务异常
* @author 余巍 yuweiqwe@126.com 
* @date 2016年1月21日 上午11:15:41 
*  
*/
public class MQException extends RuntimeException {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	/** 
	* @Fields retCode : TODO(异常对应的返回码) 
	*/ 
	private String errorCode;
	/** 
	* @Fields retMsg : TODO(异常对应的描述信息) 
	*/ 
	private String errorMsg;
	
	public MQException() {
		// TODO Auto-generated constructor stub
		super();
	}

	public MQException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public MQException(String errorCode, String errorMsg, Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
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
