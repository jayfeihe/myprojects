package com.tera.message.constant;

public class MessageConstant {

	/** 短信接口类型 发送短信 */
	public static final String SEND_TYPE_SEND = "send";
	/** 短信接口类型 查询发送记录 */
	public static final String SEND_TYPE_QUERY = "query";
	/** 短信接口类型 查询状态报告 */
	public static final String SEND_TYPE_RECV = "recv";
	/** 短信接口 apikey */
	public static final String APIKEY="c3759299033a4e58bfab813329df0c80";
	/** 短信接口类型 用户名 */
	public static final String USERNAME = "hdrt";
	/** 短信接口类型 密码 */
	public static final String PASSWORD = "mlrt123";
	
	/** 发送状态  待发送 */
	public static final String SENDSTATE_WAIT = "0";
	/** 发送状态  已发送 */
	public static final String SENDSTATE_COMPLETE = "1";
	
	/** 接收状态  成功 */
	public static final String RECEIVESTATE_DELIVRD = "DELIVRD";
	/** 接收状态  失败 */
	public static final String RECEIVESTATE_UNDELIV = "UNDELIV";

	/** 短信接口类型 查询状态报告结果  没有记录 */
	public static final String SEND_TYPE_RECV_RESULT = "no record";

	/** 短信接口类型 查询状态报告结果  最大值 */
	public static final int RECV_COUNT_MAX = 1000;

	/** 逾期短信模板类型  */
	public static final int OVERDUE_TEMPLATE_TYPE = 1;

	/** 提醒短信模板类型  */
	public static final int REMIND_TEMPLATE_TYPE = 2;

	/** 逾期短信  逾期天数 */
	public static final int OVERDUE_DAYS = 3;

	/** 提醒短信  提醒天数 */
	public static final int REMIND_DAYS = 3;

	/** 提醒短信  逾期条数 */
	public static final int OVERDUE_COUNT = 1;

	/** 提醒短信  提醒条数 */
	public static final int REMIND_COUNT = 1;

	/** 模板有效 */
	public static final int TEMPLATE_VALID = 1;

	/** 短信类型  逾期 */
	public static final String MESSAGE_TYPE_OVERDUE = "1";

	/** 短信类型  提醒 */
	public static final String MESSAGE_TYPE_REMIND = "2";
	
	
	
	

}
