package com.greenkoo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);

	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_HM = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_SS_NO_SYMBOL = "yyyyMMddHHmmss";
	public static final String FORMAT_DD_NO_SYMBOL = "yyyyMMdd";

	// 用来全局控制 上一周，本周，下一周的周数变化
	private static int weeks = 0;
	private static int MaxYear;// 一年最大天数

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
	 *            Calendar.YEAR:年 Calendar.MONTH：月 Calendar.DATE： 日
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

	/**
	 * dayOfOrigin 此方法描述的是：该日的0点0分0秒
	 * 
	 * @author: zhaohefei
	 * @version: 2015年11月24日 下午8:36:37
	 */

	public static Date dayOfOrigin(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String dateStr = sdf.format(date);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
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
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
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
			log.error("Method daysBetween (Date) arises the error,parameters --->smdate" + smdate + ",bdate=" + bdate
					+ "是否返回绝对值：" + retPlus);
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算 ( bdate-smdate) * @param retPlus 是否返回绝对值 true 返回绝对值 false
	 * 返回正常值（可能为负数）
	 */
	public static int daysBetween(String smdate, String bdate, boolean retPlus) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
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
			log.error("Method daysBetween (String) arises the error,parameters --->smdate" + smdate + ",bdate=" + bdate
					+ "是否返回绝对值：" + retPlus);
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取日期间的所日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isContainsBoundDate
	 *            是否包含范围日期（开始日期和结束日期）
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getAllDate(String startDate, String endDate, boolean isContainsBoundDate) {
		List<String> dates = new ArrayList<String>();
		try {
			if (startDate.equals(endDate)) {
				dates.add(startDate);
				return dates;
			}

			String tmp;
			if (startDate.compareTo(endDate) > 0) { // 确保 date1的日期不晚于date2
				tmp = startDate;
				startDate = endDate;
				endDate = tmp;
			}
			SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
			tmp = format.format(transStrToDate(startDate, FORMAT_DATE).getTime() + 3600 * 24 * 1000);

			int num = 0;

			while (tmp.compareTo(endDate) < 0) {
				dates.add(tmp);
				num++;
				tmp = format.format(transStrToDate(tmp, FORMAT_DATE).getTime() + 3600 * 24 * 1000);
			}

			if (isContainsBoundDate) {
				dates.add(startDate);
				dates.add(endDate);
			} else {
				if (num == 0) {
					dates.add(startDate);
					dates.add(endDate);
				}
			}
			Collections.sort(dates);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dates;
	}

	/**
	* 得到指定月后（前）的日期 参数传负数即可

	*/

	public static String getAfterMonth(int month) {
	    Calendar c = Calendar.getInstance();//获得一个日历的实例  
	    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);  
	    Date date = null;  
	    try{  
	      date = sdf.parse("2009-11-04");//初始日期  
	    }catch(Exception e){ 

	    }  
	    c.setTime(date);//设置日历时间  
	    c.add(Calendar.MONTH,month);//在日历的月份上增加6个月  
	    String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期  
	    return strDate;

	 }
	
	// 计算当月最后一天,返回字符串
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1 号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1 号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public static String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public static String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public static String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public static String getPreviousWeekMonday() {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public static String getNextMonday() {
		weeks = 0;
		weeks++;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public static String getNextSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	// 获得上月最后一天的日期
	public static String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public static String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public static String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public static String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public static String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preYearDay = sdf.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public static String getPreviousYearEnd() {
		weeks = 0;
		weeks--;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		String preYearDay = sdf.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	// 获得本季度
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
				+ "-" + end_days;
		return seasonDate;
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	public static void main(String[] args) {
		System.out.println("得到6个月后的日期:"+DateUtil.getAfterMonth(6));
		System.out.println("获取本周一日期:"+DateUtil.getMondayOFWeek());
		System.out.println("获取本周日的日期~:"+DateUtil.getCurrentWeekday());
		System.out.println("获取上周一日期:"+DateUtil.getPreviousWeekMonday());
		System.out.println("获取上周日日期:"+DateUtil.getPreviousWeekSunday());
		System.out.println("获取下周一日期:"+DateUtil.getNextMonday());
		System.out.println("获取下周日日期:"+DateUtil.getNextSunday());
		System.out.println("获取本月第一天日期:"+DateUtil.getFirstDayOfMonth());
		System.out.println("获取本月最后一天日期:"+DateUtil.getDefaultDay());
		System.out.println("获取上月第一天日期:"+DateUtil.getPreviousMonthFirst());
		System.out.println("获取上月最后一天的日期:"+DateUtil.getPreviousMonthEnd());
		System.out.println("获取下月第一天日期:"+DateUtil.getNextMonthFirst());
		System.out.println("获取下月最后一天日期:"+DateUtil.getNextMonthEnd());
		System.out.println("获取本年的第一天日期:"+DateUtil.getCurrentYearFirst());
		System.out.println("获取本年最后一天日期:"+DateUtil.getCurrentYearEnd());
		System.out.println("获取去年的第一天日期:"+DateUtil.getPreviousYearFirst());
		System.out.println("获取去年的最后一天日期:"+DateUtil.getPreviousYearEnd());
		System.out.println("获取明年第一天日期:"+DateUtil.getNextYearFirst());
		System.out.println("获取明年最后一天日期:"+DateUtil.getNextYearEnd());
		System.out.println("获取本季度第一天到最后一天:"+DateUtil.getThisSeasonTime(11));
		
		System.out.println("==========================");
		
		List<String> allDate = getAllDate("2016-10-3", "2016-10-9", true);
		
		for (String s : allDate) {
			System.out.println(s);
		}
		
		try {
			Date date = DateUtil.transStrToDate("2016-11-30 23:02",FORMAT_HM);
			System.out.println("date:"+DateUtil.formatDate(date, FORMAT_SS));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
