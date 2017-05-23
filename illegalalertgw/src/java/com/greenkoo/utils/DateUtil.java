package com.greenkoo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);

	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_SS="yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_SS_NO_SYMBOL="yyyyMMddHHmmss";
	public static final String FORMAT_DD_NO_SYMBOL="yyyyMMdd";


	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getCurDate() {
		return new Date();
	}
	
	/**
	 * 
	 * Description：日期转为字符串
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String transDateToStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 
	 * Description：日期转为字符串
	 * 
	 * @return yyyyMMddHHmmss
	 * @throws ParseException
	 */
	public static Date transStrToDate(String source, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(source);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getCurDateStr(String pattern) {
		String strDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(getCurDate());
		} catch (Exception e) {
			log.error("Method getCurDateStr arises the error,parameters ---> pattern=" + pattern);
			log.error(e.toString(), e);
		}
		return strDate;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		String strDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(date);
		} catch (Exception e) {
			log.error("Method formatDate arises the error,parameters --->date=" + date + "pattern=" + pattern);
		}
		return strDate;
	}

	/**
	 * 比较开始时间和结束时间
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int compareDate(String beginDate, String endDate) {
		DateFormat df = new SimpleDateFormat(FORMAT_SS);// "yyyy-MM-dd"
		try {
			Date dateBegin = df.parse(beginDate);
			Date dateEnd = df.parse(endDate);

			if (dateBegin.getTime() > dateEnd.getTime()) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error(
					"Method compareDate arises the error,parameters --->begindate" + beginDate + ",enddate=" + endDate);
			return -1;
		}
	}

	/**
	 * 增加时间，根据条件
	 * 
	 * @param date
	 * @param field
	 *           Calendar.YEAR:年 Calendar.MONTH：月 Calendar.DATE： 日
	 * @param amount
	 * @return
	 */
	public static Date addDate(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 计算某日期与上几个月的同一日期相差的天数
	 * 
	 * @param date
	 *            某日期
	 * @param monthNum
	 *            需要减去的月份
	 * @throws ParseException
	 * 
	 */
	public static int coutDays(Date date, int monthNum) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -monthNum);
		return daysBetween(calendar.getTime(), date, true);
	}
	
	
		/**dayOfOrigin
		* 此方法描述的是：该日的0点0分0秒
		* @author: zhaohefei
		* @version: 2015年11月24日 下午8:36:37
		*/
		
	public static Date dayOfOrigin(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String dateStr = sdf.format(date);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 计算两个日期之间相差的天数 bdate-smdate
	 * 
	 * @param retPlus
	 *            是否返回绝对值 true 返回绝对值 false 返回正常值（可能为负数）
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate, boolean retPlus) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long between_days = 0l;
		try {
			smdate = sdf.parse(sdf.format(smdate));

			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();

			if (retPlus) {
				if (time2 > time1) {
					between_days = (time2 - time1) / (1000 * 3600 * 24);
				} else {
					between_days = (time1 - time2) / (1000 * 3600 * 24);
				}
			} else {
				between_days = (time2 - time1) / (1000 * 3600 * 24);
			}
		} catch (ParseException e) {
			log.error(
					"Method daysBetween (Date) arises the error,parameters --->smdate" + smdate + ",bdate=" + bdate+"是否返回绝对值："+retPlus);
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算 ( bdate-smdate) * @param retPlus 是否返回绝对值 true 返回绝对值 false
	 * 返回正常值（可能为负数）
	 */
	public static int daysBetween(String smdate, String bdate, boolean retPlus) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		long between_days = 0l;
		try {
			cal.setTime(sdf.parse(smdate));

			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			if (retPlus) {
				if (time2 > time1) {
					between_days = (time2 - time1) / (1000 * 3600 * 24);
				} else {
					between_days = (time1 - time2) / (1000 * 3600 * 24);
				}
			} else {
				between_days = (time2 - time1) / (1000 * 3600 * 24);
			}
		} catch (ParseException e) {
			log.error(
					"Method daysBetween (String) arises the error,parameters --->smdate" + smdate + ",bdate=" + bdate+"是否返回绝对值："+retPlus);
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.formatDate(DateUtil.addDate(new Date(), 2, 3), "yyyy-MM-dd HH:mm:ss"));
	}

}
