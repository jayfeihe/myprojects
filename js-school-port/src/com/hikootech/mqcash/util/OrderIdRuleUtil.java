package com.hikootech.mqcash.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * 生成订单号工具类
 * 
 * @author QYANZE
 *
 */
public class OrderIdRuleUtil {

	public static long seed = 0;
	
	private synchronized static String get4SeqNum() {
		if (seed >= 9999) {
			seed = 0;
		}
		seed = seed + 1;
		return String.format("%04d", seed);
	}
	
	/**
	 * 中金订单号生成规则
	 * 
	 * 第1-5位：渠道ID
	 * 第6位：1位随机数
	 * 第7-8位：2位的年份
	 * 第9-11位：从1月1日的天数
	 * 第12位：1位随机数
	 * 第13-17位：从0点0分的秒数
	 * 第18位：1位随机数
	 * 第19-22位：从0001开始的顺序数
	 * 第23-24位：两位随机数
	 * 
	 * @param paymentChannelId 渠道ID
	 * @return
	 */
	public static String getId(String paymentChannelId) {
		// 流水号
		String paymentOrderId = null;
		
		StringBuffer buffer = new StringBuffer(paymentChannelId);
		
		// 生成一位随机数
		Random random = new Random();
		int six = random.nextInt(10) % 9 + 1;
		buffer.append(six);
		
		// 生成两位年份
		String yearLast = new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance().getTime());
		buffer.append(yearLast);
		
		// 生成从1月1日的天数（3位）
		Calendar cal = Calendar.getInstance();
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		buffer.append(String.format("%03d", dayOfYear));
		
		// 生成1位随机数
		int twelve = random.nextInt(10) % 9 + 1;
		buffer.append(twelve);
		
		// 生成从0点0分的秒数（5位）
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int secondOfTime = hour * 60 * 60 + minute * 60 + second;
		buffer.append(String.format("%05d", secondOfTime));
		
		// 生成1位随机数
		int eighteen = random.nextInt(10) % 9 + 1;
		buffer.append(eighteen);
		
		// 生成从0001开始的顺序数
		String fourSeqNum = get4SeqNum();
		buffer.append(fourSeqNum);
		
		// 生成2位随机数
		String twoRandomNum = String.format("%02d", random.nextInt(100));
		buffer.append(twoRandomNum);
		
		paymentOrderId = buffer.toString();
		
		return paymentOrderId;
	} 
}
