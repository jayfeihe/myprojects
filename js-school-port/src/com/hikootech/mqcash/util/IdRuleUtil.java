package com.hikootech.mqcash.util;

import java.util.Calendar;
import java.util.Random;

/**  
 *   
 * IdRuleUtil  
 *   
 * @function:(生成id规则工具类)  
 * @create time:2016年7月16日 下午9:14:48   
 * @version 1.0.0  
 * @author:张海达    
 */
public class IdRuleUtil {
	
	public static long seed = 0;
	
	
 /**  
	 * get4Num(生成从0001开始的4位数)  
	 * @return   
	 * String 
	 * @create time： 2016年7月16日 下午8:54:27 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	private synchronized static String get4Num() {
			if (seed > 99999999) {
				seed = 0;
			}
			seed = seed + 1;

			return String.format("%04d", seed);
	}
	   
	 /**  
	 * getSeq(生成一下规则的流水号) 
	 *  第1位：随机数
	 *	2-4位：从1月1日的天数
	 *	第5-9位：从0点0分的秒数
	 *	第10位：随机数
	 *	第11-14位：从0001开始的顺序数
	 *	第15-16位：两位随机数 
	 * @return   
	 * String 
	 * @create time： 2016年7月16日 下午8:54:46 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static String getSeq(){
		//第1-5位：渠道ID
	   String channelId=ConfigUtils.getProperty("payment.orderid.channelid");
		
	   Random random = new Random();
	   //第6位：随机数
	   int num1 = random.nextInt(10) % 9 + 1;
	   
	   Calendar cal = Calendar.getInstance();
	 //第7-8位：2位的年份
	   String year=""+cal.get(Calendar.YEAR);
	   year=year.substring(2);
	   //第9-11位：从1月1日的天数
	   String num2 = String.format("%03d", cal.get(Calendar.DAY_OF_YEAR));
	   //第12位：1位随机数
	   int num3=random.nextInt(10) % 9 + 1;
	   //第13-17位：从0点0分的秒数
	   int hour = cal.get(Calendar.HOUR_OF_DAY);
	   int minute = cal.get(Calendar.MINUTE);
	   int second = cal.get(Calendar.SECOND);
	   int n4 = hour * 60 * 60 + minute * 60 + second;
	   String num4=String.format("%05d", n4);
	   //第18位：1位随机数
	   int num5=random.nextInt(10) % 9 + 1;
	   //第19-22位：从0001开始的顺序数
	   String num6 = get4Num();
		//第23-24位：两位随机数
	   String num7 = String.format("%02d", random.nextInt(100));
	   
	   String seqNo = channelId+ num1 + year + num2 + num3 + num4 + num5 +  num6 + num7;
		
	   return seqNo ;
	  }
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DATE, 12);
		cal.set(Calendar.HOUR, 23);
		String dayOfYear = String.format("%03d", cal.get(Calendar.DAY_OF_YEAR));
		System.out.println("dayOfYear="+dayOfYear+","+cal.get(Calendar.DAY_OF_YEAR));
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int secondOfTime = hour * 60 * 60 + minute * 60 + second;
		System.out.println("secondOfTime="+secondOfTime);
	}

}
