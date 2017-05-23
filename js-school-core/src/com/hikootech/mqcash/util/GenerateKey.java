package com.hikootech.mqcash.util;

import java.util.Date;
import java.util.Hashtable;

import com.hikootech.mqcash.constants.CommonConstants;


public class GenerateKey {
	
	public static long seed = 0;
	public static String mdcPrefix = "mdc"; 
	
	/** 默认id前缀 */
	private final static String DEFAULT = "DFT";
	/** 默认id最追序列号长度 */
	private final static String DEFAULT_NUMBERS = "5";
	private static Hashtable<String, Object> idHashtable;
	
	static {
		idHashtable = new Hashtable<String, Object>();
		
		/** 默认id*/
		SeqCreateVo dDeqVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), DEFAULT);
		idHashtable.put(DEFAULT, dDeqVo);
		
		/** 还款记录项id*/
		SeqCreateVo seqVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX);
		idHashtable.put(CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX, seqVo);
		
		/**还款订单表主键id前缀*/
		SeqCreateVo seqPaymentOrderVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PAYMENT_ORDER_ID_PREFIX);
		idHashtable.put(CommonConstants.PAYMENT_ORDER_ID_PREFIX, seqPaymentOrderVo);
		
		/**还款订单成功表主键id*/
		SeqCreateVo seqRepaymentOrderVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.REPAYMENT_ORDER_ID_PREFIX);
		idHashtable.put(CommonConstants.REPAYMENT_ORDER_ID_PREFIX, seqRepaymentOrderVo);
		
		/**逾期罚息计算日志表id*/
		SeqCreateVo seqOverDueLogVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.OVERDUE_LOG_ID_PREFIX);
		idHashtable.put(CommonConstants.OVERDUE_LOG_ID_PREFIX, seqOverDueLogVo);
		
		/**用户征信信息表id*/
		SeqCreateVo seqCreditInfoVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.CREDIT_INFO_ID_PREFIX);
		idHashtable.put(CommonConstants.CREDIT_INFO_ID_PREFIX, seqCreditInfoVo);
		
		/**用户征信请求记录表id*/
		SeqCreateVo seqCreditRequestRecordVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.CREDIT_REQUEST_RECORD_ID_PREFIX);
		idHashtable.put(CommonConstants.CREDIT_REQUEST_RECORD_ID_PREFIX, seqCreditRequestRecordVo);
		
		/**用户手机信用信息表id*/
		SeqCreateVo seqMobileCreditInfoVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.MOBILE_CREDIT_INFO_ID_PREFIX);
		idHashtable.put(CommonConstants.MOBILE_CREDIT_INFO_ID_PREFIX, seqMobileCreditInfoVo);
		
		/**用户手机信用明细信息表id*/
		SeqCreateVo seqMobileCreditDetailInfoVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.MOBILE_CREDIT_DETAIL_INFO_ID_PREFIX);
		idHashtable.put(CommonConstants.MOBILE_CREDIT_DETAIL_INFO_ID_PREFIX, seqMobileCreditDetailInfoVo);
	
		/**发送给第三方支付流水号id前缀*/
		SeqCreateVo seqPaymentSeqVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PAYMENT_SEQ_PREFIX);
		idHashtable.put(CommonConstants.PAYMENT_SEQ_PREFIX, seqPaymentSeqVo);
		
		
		/**前海征信交易流水号*/
		SeqCreateVo seqQhzxTransNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX);
		idHashtable.put(CommonConstants.QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX, seqQhzxTransNo);
		
		/**前海征信子批数序列号*/
		SeqCreateVo seqQhzxSeqNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX);
		idHashtable.put(CommonConstants.QHZX_REQUEST_VALIDATE_SEQ_NO_PREFIX, seqQhzxSeqNo);
	
		/**记录发送短信的相关信息前缀*/
		SeqCreateVo seqSMSSeqNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.SM_ORDER_ID_PREFIX);
		idHashtable.put(CommonConstants.SM_ORDER_ID_PREFIX, seqSMSSeqNo);
		
		/**各渠道来源下单成功的分期单统计记录id前缀*/
		SeqCreateVo seqOrderStatisticVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.RECORD_PARTNER_ORDER_STATISTIC_ID_PREFIX);
		idHashtable.put(CommonConstants.RECORD_PARTNER_ORDER_STATISTIC_ID_PREFIX, seqOrderStatisticVo);
		
		/**各渠道来源下单成功的分期单统计记录id前缀*/
		SeqCreateVo seqCreditRecordVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.CREDIT_RECORD_ID_PREFIX);
		idHashtable.put(CommonConstants.CREDIT_RECORD_ID_PREFIX, seqCreditRecordVo);
		
		/**记录发送短信的相关信息前缀*/
		SeqCreateVo seqTotalInfoNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.TOTAL_INFO_ID_PREFIX);
		idHashtable.put(CommonConstants.TOTAL_INFO_ID_PREFIX, seqTotalInfoNo);
	
		/**记录电信提交的所有的用户数据信息*/
		SeqCreateVo seqUserOrderStatusLogNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.USER_ORDER_STATUS_LOG_ID_PREFIX);
		idHashtable.put(CommonConstants.USER_ORDER_STATUS_LOG_ID_PREFIX, seqUserOrderStatusLogNo);
	
		/**各征信模块征信结果表信息*/
		SeqCreateVo seqUsercreidtModelRecordLogNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.USER_CREIDT_MODEL_RECORD_ID_PREFIX);
		idHashtable.put(CommonConstants.USER_CREIDT_MODEL_RECORD_ID_PREFIX, seqUsercreidtModelRecordLogNo);
	
		/**告警日志表信息*/
		SeqCreateVo seqSysAlarmNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.SYS_ALARM_ID_PREFIX);
		idHashtable.put(CommonConstants.SYS_ALARM_ID_PREFIX, seqSysAlarmNo);
		
		/**还款订单批次号*/
		SeqCreateVo seqPaymentBatchNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PAYMENT_BATCH_NO_PREFIX);
		idHashtable.put(CommonConstants.PAYMENT_BATCH_NO_PREFIX, seqPaymentBatchNo);
	
		/**百融信贷请求id*/
		SeqCreateVo seqBaiRongNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.USER_BR_REQ_ID_PREFIX);
		idHashtable.put(CommonConstants.USER_BR_REQ_ID_PREFIX, seqBaiRongNo);

		/**百融信贷请求特殊名单查询id*/
		SeqCreateVo seqBarRongSpecNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.USER_BR_REQ_SPECIALLIST_ID_PREFIX);
		idHashtable.put(CommonConstants.USER_BR_REQ_SPECIALLIST_ID_PREFIX, seqBarRongSpecNo);
		
		/**百融信贷多次申请核查ID*/
		SeqCreateVo seqApplyLoanNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.USER_BR_REQ_APPLYLOAD_ID_PREFIX);
		idHashtable.put(CommonConstants.USER_BR_REQ_APPLYLOAD_ID_PREFIX, seqApplyLoanNo);
		
		/**学历评分ID*/
		SeqCreateVo seqEduScoreNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.EDUCATION_SCORE_ID_PREFIX);
		idHashtable.put(CommonConstants.EDUCATION_SCORE_ID_PREFIX, seqEduScoreNo);
		
		/**学籍核查数据ID*/
		SeqCreateVo educationNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.CHECK_SCHOOL_ID_PREFIX);
		idHashtable.put(CommonConstants.CHECK_SCHOOL_ID_PREFIX, seqEduScoreNo);


		/**银联数据报告ID*/
		SeqCreateVo seqUnipayReportNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.UNIPAY_REPORT);
		idHashtable.put(CommonConstants.UNIPAY_REPORT, seqUnipayReportNo);
		
		SeqCreateVo seqUnipayCategoryNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.UNIPAY_REPORT_CATEGORY);
		idHashtable.put(CommonConstants.UNIPAY_REPORT_CATEGORY, seqUnipayReportNo);
		SeqCreateVo seqUnipayCityNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.UNIPAY_REPORT_CITY);
		idHashtable.put(CommonConstants.UNIPAY_REPORT_CITY, seqUnipayReportNo);
		SeqCreateVo seqUnipayConsumeNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.UNIPAY_REPORT_CONSUME);
		idHashtable.put(CommonConstants.UNIPAY_REPORT_CONSUME, seqUnipayReportNo);
		SeqCreateVo seqUnipayCreditNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.UNIPAY_REPORT_CREDIT);
		idHashtable.put(CommonConstants.UNIPAY_REPORT_CREDIT, seqUnipayReportNo);
		
	}
	
	
	/**
	 * 生成序号
	 * @param prefix 前缀
	 * @param numbers 序号位数(数列号总长度)
	 * @return
	 */
	public synchronized static String getSeqId(String prefix, String numbers) {
		if (EntityUtils.isEmpty(prefix)) {
			prefix = DEFAULT;// 开头字母为空时，默认使用
		}
		String id = "";
		SeqCreateVo seqVo = (SeqCreateVo) idHashtable.get(prefix);
		String curDate = DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL);
		if (curDate.equals(seqVo.getCurDate())) {
			long num = seqVo.getNum() + 1;
			if (String.valueOf(num).length() > Integer.valueOf(numbers)) {
				num = 1;
			}
			seqVo.setNum(num);
			id = String.format("%0" + numbers + "d", num);
		} else {
			seqVo.setNum(1);
			seqVo.setCurDate(curDate);
			id = String.format("%0" + numbers + "d", seqVo.getNum());
		}
		idHashtable.put(prefix, seqVo);
		return id;
	}
	
	public synchronized static String getSeqId(String prefix) {
		return GenerateKey.getSeqId(prefix, DEFAULT_NUMBERS);
	}
	
	
	/**
	 * 分期订单号id
	 */
	public synchronized static String getId(String prefix, String dbNum) {
		String strCurDate = DateUtil.formatDate(new Date(), DateUtil.FORMAT_SS_NO_SYMBOL);
		if (EntityUtils.isEmpty(prefix)) {
			prefix = DEFAULT;// 开头字母为空时，默认使用
		}
		// String dbNum = dbRouteInfo.getDbNumber();
		// 生成5位随机数
		String rd = GenerateKey.getSeqId(prefix);

		String id = prefix + strCurDate + dbNum + rd;

		return id;
	}
	
	public static String generateMdcKey() {
		String ramdomNum = get8Num();
		return "[" + ramdomNum + "]";
	}

	private synchronized static String get8Num() {
		if (seed > 99999999) {
			seed = 0;
		}
		seed = seed + 1;

		return String.format("%08d", seed);
	}
	
	public static void main(String[] args) {
//		System.out.println(generate());
//		System.out.println(generate());
//		System.out.println(generate());
//		System.out.println(generate());
		System.out.println(GenerateKey.getId(null, "T1"));
		System.out.println(GenerateKey.getId(CommonConstants.QHZX_REQUEST_VALIDATE_TRANS_NO_PREFIX, "T1"));
	}
	
}
