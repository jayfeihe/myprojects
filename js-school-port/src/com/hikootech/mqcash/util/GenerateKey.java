package com.hikootech.mqcash.util;

import java.util.Date;
import java.util.Hashtable;

import com.hikootech.mqcash.constants.CommonConstants;


public class GenerateKey {
	
	public static long seed = 0;
	
	/** 默认id前缀 */
	private final static String DEFAULT = "DEFAULT";
	/** 默认id最追序列号长度 */
	private final static String DEFAULT_NUMBERS = "5";
	private static Hashtable<String, Object> idHashtable;
	
	static {
		idHashtable = new Hashtable<String, Object>();
		
		/** 还款记录项id*/
		SeqCreateVo seqVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX);
		idHashtable.put(CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX, seqVo);
		
		/**还款订单表主键id前缀*/
		SeqCreateVo seqPaymentOrderVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PAYMENT_ORDER_ID_PREFIX);
		idHashtable.put(CommonConstants.PAYMENT_ORDER_ID_PREFIX, seqPaymentOrderVo);
		
		/**还款订单成功表主键id*/
		SeqCreateVo seqRepaymentOrderVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.REPAYMENT_ORDER_ID_PREFIX);
		idHashtable.put(CommonConstants.REPAYMENT_ORDER_ID_PREFIX, seqRepaymentOrderVo);
		
		/**发送给第三方支付订单号前缀*/
		SeqCreateVo seqPaymentOrderNoVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.PAYMENT_ORDER_NO_PREFIX);
		idHashtable.put(CommonConstants.PAYMENT_ORDER_NO_PREFIX, seqPaymentOrderNoVo);
		
		
		/**用户登录记录表id*/
		SeqCreateVo seqLoginRecordVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.LOGIN_RECORD_ID_PREFIX);
		idHashtable.put(CommonConstants.LOGIN_RECORD_ID_PREFIX, seqLoginRecordVo);
		
		
		/**用户银行卡绑定表id前缀*/
		SeqCreateVo seqBankCardVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.BANK_CARD_ID_PREFIX);
		idHashtable.put(CommonConstants.BANK_CARD_ID_PREFIX, seqBankCardVo);
		
		/**用户验证银行卡限制表id前缀*/
		SeqCreateVo seqLogValidBankVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.LOG_REQUEST_VALIDATE_BANK_ID_PREFIX);
		idHashtable.put(CommonConstants.LOG_REQUEST_VALIDATE_BANK_ID_PREFIX, seqLogValidBankVo);
		
		/**记录发送短信的相关信息前缀*/
		SeqCreateVo seqSMSSeqNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.SM_ORDER_ID_PREFIX);
		idHashtable.put(CommonConstants.SM_ORDER_ID_PREFIX, seqSMSSeqNo);
		
		/**记录用户操作记录信息前缀*/
		SeqCreateVo seqLogUserInfoSeqNo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), DateUtil.FORMAT_DD_NO_SYMBOL), CommonConstants.LOG_USER_INFO_PREFIX);
		idHashtable.put(CommonConstants.LOG_USER_INFO_PREFIX, seqLogUserInfoSeqNo);
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
		
	}
	
}
