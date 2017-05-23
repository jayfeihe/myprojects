package com.greenkoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greenkoo.echarts.model.form.CountBean;
import com.greenkoo.utils.DateUtil;

public class TestCommon {

	static String dateFormat = "yyyy-MM-dd";
	static SimpleDateFormat format = new SimpleDateFormat(dateFormat);
 
	/**
	 * 获取两个日期之间所有的日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static ArrayList days(String date1, String date2) {
		ArrayList L = new ArrayList();
		if (date1.equals(date2)) {
			System.out.println("两个日期相等!");
			return L;
		}
 
		String tmp;
		if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
			tmp = date1;
			date1 = date2;
			date2 = tmp;
		}
 
		tmp = format.format(str2Date(date1).getTime() + 3600 * 24 * 1000);
 
		int num = 0;
		while (tmp.compareTo(date2) < 0) {
			L.add(tmp);
			num++;
			tmp = format.format(str2Date(tmp).getTime() + 3600 * 24 * 1000);
		}
 
		if (num == 0)
			System.out.println("两个日期相邻!");
		return L;
	}
 
	private static Date str2Date(String str) {
		if (str == null)
			return null;
 
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
	}
 
	/**
	 * 一个月有几天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int dayInMonth(int year, int month) {
		boolean yearleap = isLeapYear(year);
		int day;
		if (yearleap && month == 2) {
			day = 29;
		} else if (!yearleap && month == 2) {
			day = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			day = 30;
		} else {
			day = 31;
		}
		return day;
	}
 
	public static void date() {
		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.WEEK_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
		System.out.println(DateUtil.formatDate(calendar.getTime(), DateUtil.FORMAT_DATE));
		
		calendar .add(Calendar.MONTH, -2);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println(DateUtil.formatDate(calendar.getTime(), DateUtil.FORMAT_DATE));
		calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		System.out.println(DateUtil.formatDate(calendar.getTime(), DateUtil.FORMAT_DATE));
	}
	
	public static void  testDateData() {
		List<String> times = new ArrayList<>();
		times.add("2016-10-03");
		times.add("2016-10-04");
		times.add("2016-10-05");
		times.add("2016-10-06");
		times.add("2016-10-07");
		
		List<CountBean> datas = new ArrayList<>(times.size());
		datas.add(new CountBean(1, "2016-10-04"));
		datas.add(new CountBean(2, "2016-10-05"));
		
		List<Map<String, String>> all = new ArrayList<>();
		for (String t : times) {
		time : for (CountBean bean : datas) {
				Map<String, String> map = new HashMap<>();
				if (t.equals(bean.getConfirmTime())) {
					map.put(bean.getConfirmTime(), String.valueOf(bean.getCount()));
					all.add(map);
				} else {
					map.put(t, "0");
					all.add(map);
				}
				break time;
			}
		}
		
		for (Map<String, String> map : all) {
			System.out.println(map);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String start = DateUtil.formatDate(
				DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -7), DateUtil.FORMAT_DATE);
		
		String end = DateUtil.formatDate(
				DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE);
		
		List<String> allDate = DateUtil.getAllDate(start, end, true);
		
		for (String date : allDate) {
			System.out.print(date+"  ");
		}
		
		System.out.println();
		
		String start2 = DateUtil.formatDate(
				DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -14), DateUtil.FORMAT_DATE);
		
		String end2 = DateUtil.formatDate(
				DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -8), DateUtil.FORMAT_DATE);
		
		List<String> allDate2 = DateUtil.getAllDate(start2, end2, true);
		
		for (String date : allDate2) {
			System.out.print(date+"  ");
		}
	}
	
	
	
	
 
}
