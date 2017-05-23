package com.hikootech.mqcash.exception;

/** 
* @ClassName MQExceptionConstants 
* @Description TODO(秒趣异常常量) 
* @author 余巍 yuweiqwe@126.com 
* @date 2016年1月21日 上午11:21:00 
* AA BBB CC DDDDD
* 10 001 01 00001 
* AA: 平台代码 10秒趣
* BBB: 系统代码 001核心 002分期 003门户 004企信 005后台
* CC: 错误类型：01业务异常 02系统运行时异常 03未知异常
* DDDDD: 具体错误码
*/
public class MQExceptionConstants {
	
	/** 
	* @Fields MQ_DATABASE_EXCEPTION : 数据库连接异常
	*/ 
	public final static String MQ_DATABASE_EXCEPTION = "10001020001"; 
	public final static String MQ_DATABASE_EXCEPTION_DESC = "数据库连接异常！";
	
	/** 
	* @Fields MQ_RUNTIME_EXCEPTION : 空指针异常
	*/ 
	public final static String MQ_RUNTIME_EXCEPTION = "10001020002"; 
	public final static String MQ_RUNTIME_EXCEPTION_DESC = "空指针异常！";
	
	/** 
	* @Fields MQ_RUNTIME_EXCEPTION : IO异常
	*/ 
	public final static String MQ_IO_EXCEPTION = "10001020003"; 
	public final static String MQ_IO_EXCEPTION_DESC = "IO异常！";
	
	/** 
	* @Fields MQ_UNKNOWN_EXCEPTION :未知异常
	*/ 
	public final static String MQ_UNKNOWN_EXCEPTION = "10001030001"; 
	public final static String MQ_UNKNOWN_EXCEPTION_DESC = "未知异常！";
	
	//------------------------------------生成支付订单------------------------------------------
	
	/** 
	* @Fields MQ_CREATE_PAYMENT_ORDER_OVERDUE_INCORRENT : 用户逾期罚息计算有误，逾期罚息校验失败
	*/ 
	public final static String MQ_CREATE_PAYMENT_ORDER_OVERDUE_INCORRENT = "10001010001"; 
	public final static String MQ_CREATE_PAYMENT_ORDER_OVERDUE_INCORRENT_DESC = "用户逾期罚息计算有误，逾期罚息校验失败，代收订单创建停止！";
	
	/** 
	* @Fields MQ_CREATE_PAYMENT_ORDER_AMOUNT_INCORRENT : 用户代收总金额计算有误，代收总金额校验失败
	*/ 
	public final static String MQ_CREATE_PAYMENT_ORDER_AMOUNT_INCORRENT = "10001010002"; 
	public final static String MQ_CREATE_PAYMENT_ORDER_AMOUNT_INCORRENT_DESC = "用户代收总金额计算有误，代收总金额校验失败，代收订单创建停止！";
	
	/** 
	* @Fields MQ_CREATE_PAYMENT_ORDER_UPDATE_STATUS_FAIL : 用户代收总金额计算有误，代收总金额校验失败
	*/ 
	public final static String MQ_CREATE_PAYMENT_ORDER_UPDATE_STATUS_FAIL = "10001010003"; 
	public final static String MQ_CREATE_PAYMENT_ORDER_UPDATE_STATUS_FAIL_DESC = "更新还款计划状态为支付中失败，找不到对应还款计划或者还款计划状态有误，代收订单创建回滚！";
	
	
	//------------------------------------请求中金代收支付订单------------------------------------------
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1361_PROCESS_REQUEST_EXCEPTION : 组装中金tx1361报文：异常！
	*/ 
	public final static String MQ_REQUEST_CPCN_1361_PROCESS_REQUEST_EXCEPTION = "10001010101"; 
	public final static String MQ_REQUEST_CPCN_1361_PROCESS_REQUEST_EXCEPTION_DESC = "组装中金tx1361报文：异常！";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1361_REQUEST_EXCEPTION : 请求中金市场订单代收交易异常!
	*/ 
	public final static String MQ_REQUEST_CPCN_1361_REQUEST_EXCEPTION = "10001010102"; 
	public final static String MQ_REQUEST_CPCN_1361_REQUEST_EXCEPTION_DESC = "请求中金市场订单代收交易异常!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION : 中金市场订单代收同步返回信息解析失败!
	*/ 
	public final static String MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION = "10001010103"; 
	public final static String MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION_DESC = "中金市场订单代收同步返回信息解析失败!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1361_RETURN_TXSN_EXCEPTION : 代收请求通知错误，第三方通知中返回交易流水号为空!
	*/ 
	public final static String MQ_REQUEST_CPCN_1361_RETURN_TXSN_EXCEPTION = "10001010104"; 
	public final static String MQ_REQUEST_CPCN_1361_RETURN_TXSN_EXCEPTION_DESC = "代收请求通知错误，第三方通知中返回交易流水号为空!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1361_RETURN_STATUS_EXCEPTION : 代收请求通知错误，第三方通知中返回返回支付状态未知!
	*/ 
	public final static String MQ_REQUEST_CPCN_1361_RETURN_STATUS_EXCEPTION = "10001010105"; 
	public final static String MQ_REQUEST_CPCN_1361_RETURN_STATUS_EXCEPTION_DESC = "代收请求通知错误，第三方通知中返回返回支付状态未知!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION : 代收请求返回支付流水号对应的支付订单支付状态有误!
	*/ 
	public final static String MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION = "10001010106"; 
	public final static String MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC = "代收请求返回支付流水号对应的支付订单支付状态有误!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_DEALING_EXCEPTION : 根据支付订单id修改支付订单为处理中失败!
	*/ 
	public final static String MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_DEALING_EXCEPTION = "10001010107"; 
	public final static String MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_DEALING_EXCEPTION_DESC = "根据支付订单id修改支付订单为处理中失败!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION : 根据支付订单id修改支付订单为'处理失败'失败!
	*/ 
	public final static String MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION = "10001010108"; 
	public final static String MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION_DESC = "根据支付订单id修改支付订单为'处理失败'失败!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_MQ_NOT_FOUND_PLANS_EXCEPTION : 根据支付订单id找不到对应还款计划!
	*/ 
	public final static String MQ_REQUEST_CPCN_MQ_NOT_FOUND_PLANS_EXCEPTION = "10001010109"; 
	public final static String MQ_REQUEST_CPCN_MQ_NOT_FOUND_PLANS_EXCEPTION_DESC = "根据支付订单id找不到对应还款计划!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_MQ_DEAL_PAYFAILED_FAILED_EXCEPTION : 处理支付失败订单失败异常!
	*/ 
	public final static String MQ_REQUEST_CPCN_MQ_DEAL_PAYFAILED_FAILED_EXCEPTION = "10001010110"; 
	public final static String MQ_REQUEST_CPCN_MQ_DEAL_PAYFAILED_FAILED_EXCEPTION_DESC = "处理支付失败订单失败异常!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_MQ_UPDATE_INSTALMENT_EXCEPTION : 更新分期订单为分期完成有误，更新条数不正确!
	*/ 
	public final static String MQ_REQUEST_CPCN_MQ_UPDATE_INSTALMENT_EXCEPTION = "10001010111"; 
	public final static String MQ_REQUEST_CPCN_MQ_UPDATE_INSTALMENT_EXCEPTION_DESC = "更新分期订单为分期完成有误，更新条数不正确!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION : 处理支付成功订单失败异常!
	*/ 
	public final static String MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION = "10001010112"; 
	public final static String MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION_DESC = "处理支付成功订单失败异常!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1361_MQ_UPDATE_STATUS_PAYFAILED_EXCEPTION : 主动付款异步通知错误， 返回支付状态未知!
	*/ 
	public final static String MQ_REQUEST_CPCN_1318_RETURN_STATUS_EXCEPTION = "10001010113"; 
	public final static String MQ_REQUEST_CPCN_1318_RETURN_STATUS_EXCEPTION_DESC = "主动付款异步通知错误， 返回支付状态未知!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1362_REQUEST_EXCEPTION : 请求中金市场订单代收交易查询异常!
	*/ 
	public final static String MQ_REQUEST_CPCN_1362_REQUEST_EXCEPTION = "10001010114"; 
	public final static String MQ_REQUEST_CPCN_1362_REQUEST_EXCEPTION_DESC = "请求中金市场订单代收交易查询异常!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_INSERT_REPAYMENT_ORDER_EXCEPTION : 插入还款成功订单聚合表异常!
	*/ 
	public final static String MQ_REQUEST_CPCN_INSERT_REPAYMENT_ORDER_EXCEPTION = "10001010115"; 
	public final static String MQ_REQUEST_CPCN_INSERT_REPAYMENT_ORDER_EXCEPTION_DESC = "插入还款成功订单聚合表异常!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_INSERT_REPAYMENT_ORDER_EXCEPTION : 还款金额异常!
	*/ 
	public final static String MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION = "10001010116"; 
	public final static String MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION_DESC = "还款金额异常!";
	
	//------------------------------------还款计划------------------------------------------
	/** 
	* @Fields MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION : 更新还款计划状态失败！
	*/ 
	public final static String MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION = "10001010201"; 
	public final static String MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION_DESC = "更新还款计划状态失败！";
	
	/** 
	* @Fields MQ_REPAYMENT_PLANS_OVERDUE_TIME_EXCEPTION : 还款计划逾期时间参数有误，检查开始时间不能大于结束时间，并且结束时间不能大于最大逾期时间（当前时间-1天）！
	*/ 
	public final static String MQ_REPAYMENT_PLANS_OVERDUE_TIME_EXCEPTION = "10001010202"; 
	public final static String MQ_REPAYMENT_PLANS_OVERDUE_TIME_EXCEPTION_DESC = "还款计划逾期时间参数有误，检查开始时间不能大于结束时间，并且结束时间不能大于最大逾期时间（当前时间-1天）！";
	
	/** 
	* @Fields MQ_REPAYMENT_PLANS_CAL_OVERDUE_EXCEPTION : 计算逾期服务费出错！
	*/ 
	public final static String MQ_REPAYMENT_PLANS_CAL_OVERDUE_EXCEPTION = "10001010203"; 
	public final static String MQ_REPAYMENT_PLANS_CAL_OVERDUE_EXCEPTION_DESC = "计算逾期服务费出错！";
	
	/** 
	* @Fields MQ_REPAYMENT_PLANS_UPDATE_OVERDUE_ROW_EXCEPTION : 更新还款计划应收逾期罚息、应收总金额有误，更新条数不正确
	*/ 
	public final static String MQ_REPAYMENT_PLANS_UPDATE_OVERDUE_ROW_EXCEPTION = "10001010204"; 
	public final static String MQ_REPAYMENT_PLANS_UPDATE_OVERDUE_ROW_EXCEPTION_DESC = "计算逾期服务费出错！";
	
	/** 
	* @Fields MQ_REPAYMENT_PLANS_LOCK_OVERDUE_PLANS_EXCEPTION : 锁定分期订单对应所有的还款计划出错
	*/ 
	public final static String MQ_REPAYMENT_PLANS_LOCK_OVERDUE_PLANS_EXCEPTION = "10001010205"; 
	public final static String MQ_REPAYMENT_PLANS_LOCK_OVERDUE_PLANS_EXCEPTION_DESC = "锁定分期订单对应所有的还款计划出错！";
	
	
	//------------------------------------主动支付------------------------------------------
	/** 
	* @Fields MQ_REQUEST_CPCN_1311_PROCESS_REQUEST_EXCEPTION : tx1311组装报文出错！
	*/ 
	public final static String MQ_REQUEST_CPCN_1311_PROCESS_REQUEST_EXCEPTION = "10001010301"; 
	public final static String MQ_REQUEST_CPCN_1311_PROCESS_REQUEST_EXCEPTION_DESC = "tx1311组装报文出错！";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1320_PROCESS_REQUEST_EXCEPTION : tx1320组装报文出错！
	*/ 
	public final static String MQ_REQUEST_CPCN_1320_PROCESS_REQUEST_EXCEPTION = "10001010302"; 
	public final static String MQ_REQUEST_CPCN_1320_PROCESS_REQUEST_EXCEPTION_DESC = "tx1311组装报文出错！";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1320_REQUEST_EXCEPTION : 请求市场订单支付查询异常!
	*/ 
	public final static String MQ_REQUEST_CPCN_1320_REQUEST_EXCEPTION = "10001010303"; 
	public final static String MQ_REQUEST_CPCN_1320_REQUEST_EXCEPTION_DESC = "请求市场订单支付查询异常";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1320_REQUEST_DECODE_EXCEPTION : 中金市场订单支付查询返回信息Base64解码失败!
	*/ 
	public final static String MQ_REQUEST_CPCN_1320_REQUEST_DECODE_EXCEPTION = "10001010304"; 
	public final static String MQ_REQUEST_CPCN_1320_REQUEST_DECODE_EXCEPTION_DESC = "中金市场订单支付查询返回信息Base64解码失败!";
	
	/** 
	* @Fields MQ_REQUEST_CPCN_1320_RETURN_PAYMENTNO_EXCEPTION : 市场订单支付查询错误，第三方通知中交易流水号为空!
	*/ 
	public final static String MQ_REQUEST_CPCN_1320_RETURN_PAYMENTNO_EXCEPTION = "10001010105"; 
	public final static String MQ_REQUEST_CPCN_1320_RETURN_PAYMENTNO_EXCEPTION_DESC = "市场订单支付查询错误，第三方通知中交易流水号为空!";
	
}
