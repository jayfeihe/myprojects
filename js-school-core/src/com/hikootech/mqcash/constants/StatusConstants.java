package com.hikootech.mqcash.constants;

/**
 * @author yuwei
 * 2015年8月5日
 * 
 */
public class StatusConstants {
	
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
	
	
	/** 分期订单状态：10 初始化   */
	public final static Integer INSTALMENT_STATUS_INIT = 10;
	/** 分期订单状态：20待还款   */
	public final static Integer INSTALMENT_STATUS_AWAIT_PAY = 20;
	/** 分期订单状态：30已逾期   */
	public final static Integer INSTALMENT_STATUS_OVERDUE = 30;
	/** 分期订单状态：40已结清   */
	public final static Integer INSTALMENT_STATUS_COMPLETED = 40;
	/** 分期订单状态：50已取消   */
	public final static Integer INSTALMENT_STATUS_CANCELED = 50;
	/** 分期订单状态：60已中止   */
	public final static Integer INSTALMENT_STATUS_SUSPENDED = 60;
	/** 分期订单状态：70待退款   */
	public final static Integer INSTALMENT_STATUS_AWAIT_REFUND = 70;
	
	/** 电渠订单状态：10初始化状态    */
	public final static Integer INSTALMENT_ORDER_STATUS_INIT = 10;
	/** 电渠订单状态：20下单成功    */
	public final static Integer INSTALMENT_ORDER_STATUS_SUCCESS = 20;
	/** 电渠订单状态：30订单取消    */
	public final static Integer INSTALMENT_ORDER_STATUS_CANCELED = 30;
	/** 电渠订单状态：40订单收货    */
	public final static Integer INSTALMENT_ORDER_STATUS_DELIVERED = 40;
	/** 电渠订单状态：50订单退货    */
	public final static Integer INSTALMENT_ORDER_STATUS_REFUNDED = 50;
	
	/** 资方状态：10初始化状态    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_INIT = 10;
	/** 资方状态：20待打款    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_AWAIT_PAY = 20;
	/** 资方状态：24融资准备     */
	public final static Integer INSTALMENT_PROVIDER_STATUS_APPLY_BATCH = 24;
	/** 资方状态：25融资申请    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_SUBMIT_BATCH = 25;
	/** 资方状态：26融资完成     */
	public final static Integer INSTALMENT_PROVIDER_STATUS_COMPLETE_BATCH = 26;
	/** 资方状态：27融资取消     */
	public final static Integer INSTALMENT_PROVIDER_STATUS_CANCEL_BATCH = 27;
	/** 资方状态：30打款中    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_DOING_PAY = 30;
	/** 资方状态：40已打款    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_PAY_SUCCESS = 40;
	/** 资方状态：50已取消    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_CANCELED = 50;
	/** 资方状态：60待退款    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_AWAIT_REFUND = 60;
	/** 资方状态：70已退款    */
	public final static Integer INSTALMENT_PROVIDER_STATUS_REFUNDED = 70;
	
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
	
	/** 分期计划状态：10初始化    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_INIT = 10;
	/** 分期计划状态：20待还款    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY = 20;
	/** 分期计划状态：30还款中    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY = 30;
	/** 分期计划状态：40已还款    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED = 40;
	/** 分期计划状态：50已逾期    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE = 50;
	/** 分期计划状态：60已取消    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_CANCELED = 60;
	/** 分期计划状态：70已中止    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_SUSPENDED = 70;
	/** 分期计划状态：80待退款    */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_REFUND = 80;
	
	/** 分期还款计划代收锁定：0 不锁定     */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_REPAY_UNLOCK = 0;
	/** 分期还款计划代收锁定：1 锁定   */
	public final static Integer INSTALMENT_REPAYMENT_PLANS_REPAY_LOCK = 1;
	
	/** 发送状态:10待请求    */
	public final static Integer REQUEST_STATUS_AWAIT = 10;
	/** 发送状态:20请求成功  */
	public final static Integer REQUEST_STATUS_SUCCESS = 20;
	/** 发送状态:30请求失败    */
	public final static Integer REQUEST_STATUS_FAILED = 30;
	
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
	
	/** 支付类型：0全部付款   */
	public final static Integer PAYMENT_TYPE_PAYALL = 0;
	
	/** 子表支付状态： 10未支付    */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_AWAIT_PAY = 10;
	/** 子表支付状态： 20支付成功    */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_PAY_SUCCESS = 20;
	/** 子表支付状态： 30重复还款    */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_PAY_AGAIN = 30;
	/** 子表支付状态： 40已退款    */
	public final static Integer PAYMENT_ORDER_ITEM_PAYSTATUS_REFUNDED = 40;
	
	/** 用户状态：0无效（逻辑删除）      */
	public final static Integer USER_STATUS_UNEFFECTIVE = 0;
	/** 用户状态：1可用    */
	public final static Integer USER_STATUS_EFFECTIVE = 1;
	
	/** 国政通成功状态 */
	public final static Integer CREDIT_QUERY_STATUS_SUCCESS = 0;
	/** 国政通查询成功有数据状态 */
	public final static Integer CREDIT_QUERY_STATUS_SUCCESS_GET_DATA = 0;
	/** 国政通查询成功无数据状态 */
	public final static Integer CREDIT_QUERY_STATUS_SUCCESS_NO_DATA = 1;
	
	/** 国政通    */
	public final static Integer SERVICE_SOURCE_NAME_GZT = 10;
	/**  学历   */
	public final static Integer SERVICE_REQUEST_TYPE_EDU = 10;
	/**  电话   */
	public final static Integer SERVICE_REQUEST_TYPE_PHONE = 20;
	
	/** 调用秒趣征信接口：0处理成功     */
	public final static Integer QUERY_CREDIT_RESULT_SUCCESS = 0;
	/** 调用秒趣征信接口：1处理失败    */
	public final static Integer QUERY_CREDIT_RESULT_FAIL = 1;
	
	/** 调用秒趣征信接口：0可以使用秒趣分期     */
	public final static Integer USER_MIAOQU_YES = 0;
	/** 调用秒趣征信接口：1不可以使用秒趣分期    */
	public final static Integer USER_MIAOQU_NO = 1;
	
	/** 发送短信状态：待发送  */
	public final static Integer SEND_SMS_WAITTING = 0;
	/** 发送短信状态：成功   */
	public final static Integer SEND_SMS_SUCCESS = 1;
	/** 发送短信状态：失败    */
	public final static Integer SEND_SMS_FAILED = 2;
	
	/** 发送短信类型：延迟发送   */
	public final static Integer SEND_SMS_TYPE_LATER = 0;
	/** 发送短信类型：立即发送    */
	public final static Integer SEND_SMS_TYPE_NOW = 1;
	/**A类用户*/
	public final static Integer USER_SM_TYPE_A=1;
	/**B类用户*/
	public final static Integer USER_SM_TYPE_B=2;

	/** 合作伙伴调用分期接口返回处理结果：成功   */
	public final static Integer PARTNER_RESULT_CODE_SUCCESS = 0;
	/** 合作伙伴调用分期接口返回处理结果：失败    */
	public final static Integer PARTNER_RESULT_CODE_FAIL = 1;
	
	/**是否征信通过：0通过*/
	public final static Integer CREDIT_IS_PASS=0;
	/**是否征信通过：1不通过*/
	public final static Integer CREDIT_IS_NOT_PASS=1;
	
	/**性别：女*/
	public final static Integer SEX_FEMALE = 0;
	/**性别：男*/
	public final static Integer SEX_MALE = 1;
	
	/**打款状态：0未打款*/
	public final static Integer PAYMENT_STATUS_UNPAY = 0;
	/**打款状态：10打款中*/
	public final static Integer PAYMENT_STATUS_PAYING = 10;
	/**打款状态：20已打款*/
	public final static Integer PAYMENT_STATUS_PAYED = 20;
	/**打款状态：30打款失败*/
	public final static Integer PAYMENT_STATUS_PAYFAIL = 30;
	
	/**业务表第三方征信结果  0初始化状态  */
	public final static Integer THIRD_PARTY_CREDIT_STATUS_INIT=0;
	/**业务表第三方征信结果  10通过 */
	public final static Integer THIRD_PARTY_CREDIT_STATUS_PASS=10;
	/**业务表第三方征信结果    20不通过*/
	public final static Integer THIRD_PARTY_CREDIT_STATUS_NOT_PASS=20;

	 
	/** 征信模块判断结果：10 征信通过 */
	public final static Integer  CREDIT_CHECKING_RESULT_SUCCESS= 10;
	/** 征信模块判断结果：20征信不通过   */
	public final static Integer CREDIT_CHECKING_RESULT_FAILED = 20;
	
	/** 调用91发送状态:10请求成功  */
	public final static Integer REQUEST_STATUS_JYZX_SUCCESS = 10;
	/** 调用91发送状态:20请求失败    */
	public final static Integer REQUEST_STATUS_JYZX_FAILED = 20;
	
	/** 91征信返回的报文数据状态:0未回应  */
	public final static Integer RESPONSE_STATUS_JYZX_WAITTING = 0;
	/** 91征信返回的报文数据状态:10 回应有数据  */
	public final static Integer RESPONSE_STATUS_JYZX_DATA = 10;
	/** 91征信返回的报文数据状态:20 回应无数据  */
	public final static Integer RESPONSE_STATUS_JYZX_NODATA = 20;
	
	/**状态变化动作类型：10 征信  */
	public final static Integer STEP_CREDIT_ACTION_TYPE=10;
	/**状态变化动作类型： 20绑卡验证  */
	public final static Integer STEP_VALIDCODE_ACTION_TYPE=20;
	/**状态变化动作类型： 30绑卡  */
	public final static Integer STEP_BINDCARD_ACTION_TYPE=30;
	/**状态变化动作类型： 40订单确认  */
	public final static Integer STEP_CONFIRM_ACTION_TYPE=40;
	/**状态变化动作类型： 50 订单同步    */
	public final static Integer STEP_SYNC_ACTION_TYPE=50;
	/**状态变化动作类型：60退单 */
	public final static Integer STEP_RETURN_ACTION_TYPE=60;
	/**状态变化动作类型：70收货  */
	public final static Integer STEP_RECEIVE_ACTION_TYPE=70;
	/**状态变化动作类型：80打款 */
	public final static Integer STEP_TRANSFER_ACTION_TYPE=80;
	/**状态变化动作类型：90 打款前退货  */
	public final static Integer STEP_TRANSFER_B_RETURN_ACTION_TYPE=90;
	/**状态变化动作类型： 100打款后退货  */
	public final static Integer STEP_TRANSFER_A_RETURN_ACTION_TYPE=100;
	
	/**黑名单匹配类型：10手机 */
	public final static Integer BLACK_MATCH_TELPHONE_TYPE=10;
	/**黑名单匹配类型：20身份证  */
	public final static Integer BLACK_MATCH_IDCARD_TYPE=20;
	
	/**前海好信一鉴通-姓名与身份证匹配成功*/
	public final static String  QHZX_IDNO_NAME_MATCH_VAL="1";
	/**前海好信一鉴通-姓名与身份证匹配失败*/
	public final static String  QHZX_IDNO_NAME_NOT_MATCH_VAL="0";
	/**前海好信一鉴通-姓名与身份证无匹配信息*/
	public final static String  QHZX_IDNO_NAME_NO_MATCH_VAL="9";
	
	/**告警所属系统：0-秒趣分期 */
	public final static String  ALARM_SYSTEM_INS="0";
	/**告警所属系统：1-秒趣门户*/
	public final static String  ALARM_SYSTEM_PORT="1";
	/**告警所属系统：2-秒趣核心 */
	public final static String  ALARM_SYSTEM_CORE="2";
	/**告警所属系统：3-秒趣企信 */
	public final static String  ALARM_SYSTEM_CRM="3";
	/**告警所属系统：4-秒趣后台*/
	public final static String  ALARM_SYSTEM_BACK="4";
	
	/**告警所属级别：0-低*/
	public final static String  ALARM_LEVEL_LOW="0";
	/**告警所属级别： 1-中*/
	public final static String  ALARM_LEVEL_MID="1";
	/**告警所属级别：2-高*/
	public final static String  ALARM_LEVEL_HIGH="2";
	
	/**告警通知类型：0-邮件通知*/
	public final static String  ALARM_NOTICE_EMAIL_TYPE="0";
	/**告警通知类型： 1-短信通知*/
	public final static String  ALARM_NOTICE_SMS_TYPE="1";
	/**告警通知类型： 2-邮件并且短信通知*/
	public final static String  ALARM_NOTICE_EMS_TYPE="2";
	
	/**状态：0-未通知*/
	public final static int  SEND_STATUS_NO=0;
	/**状态：  1-已通知*/
	public final static int  SEND_STATUS_YES=1;
	
	/**规则类型：0-征信通过率*/
	public final static int  CREDIT_SUC_RATE=0;
	/**规则类型：1-绑卡中比率*/
	public final static int  BANDING_CARD_RATE=1;
	/**规则类型：2-下单完成率*/
	public final static int  ORDER_FINISH_RATE=2;
	
	/**邮件作用，1-发送业务告警邮件*/
	public final static int SEND_SYS_ALARM_EMAIL = 1;
	
	/**调用类型： 10用户征信 */
	public final static String  JY_CALL_TYPE_CREDIT="10";
	/**调用类型： 20用户91信息收集*/
	public final static String  JY_CALL_TYPE_DATA_COLLECTION="20";
	/**调用类型：30学生征信*/
	public final static String  JY_CALL_TYPE_STUDENT_CREDIT="30";
	
	/**邮件类型 0-用户征信报表 */
	public final static Integer EMAIL_TYPE_CREDIT_REPORT = 0;
	/**邮件类型 1-业务告警邮件*/
	public final static Integer EMAIL_TYPE_BUS_ALARM = 1;

	/**0-未命中百融特殊名单核查 */
	public final static Integer SPECIAL_LIST_C_IS_NOT_BLACKLIST = 0;
	/**1-命中百融特殊名单核查 */
	public final static Integer SPECIAL_LIST_C_IS_BLACKLIST = 1;
	
	/**不良类型 0-id 通过身份证进行核查 */
	public final static Integer SPECIAL_LIST_C_TYPE_ID = 0;
	/**不良类型 1-cell 通过手机号进行核查 */
	public final static Integer SPECIAL_LIST_C_TYPE_CELL = 1;
	/**不良类型 2-gid 通过gid进行核查 */
	public final static Integer SPECIAL_LIST_C_TYPE_GID = 2;
	
	/**学籍评分是否通过（0通过）*/
	public final static String EDUCATE_RESULT_PASS = "0";
	/**学籍评分是否通过（1不通过） */
	public final static String EDUCATE_RESULT_UNPASS = "1";
	
	/**  接口返回学籍核查一致  **/
	public final static String INTERFAECE_CHECK_SHCOOL_CORRECT = "一致";
	/**  接口返回学籍核查不一致  **/
	public final static String INTERFAECE_CHECK_SHCOOL_INCORRECT = "不一致";
	
	/**  学籍核查一致  **/
	public final static String CHECK_SHCOOL_CORRECT = "10";
	/**  学籍核查不一致  **/
	public final static String CHECK_SHCOOL_INCORRECT = "20";
	
	/**  学籍核查10查询成功  **/
	public final static String CHECK_SHCOOL_QUERY_SUCCESS = "10";
	/**  学籍核查20查询失败  **/
	public final static String CHECK_SHCOOL_QUERY_FAIL = "20";

}
