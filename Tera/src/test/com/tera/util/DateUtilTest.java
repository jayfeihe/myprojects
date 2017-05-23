/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.util;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
/**
 * @author admin
 *
 */
public class DateUtilTest {
	/**
	 * getDateTest
	 */
	@Test
	public void getDateTest() {
		Date date = DateUtils.getDate("2014-6-16");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		System.out.println("年：" + calendar.get(Calendar.YEAR));
		System.out.println("月：" + (calendar.get(Calendar.MONTH) + 1));
		System.out.println("日：" + calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("星期：" + (calendar.get(Calendar.DAY_OF_WEEK)-1));
		System.out.println("时：" + calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("分：" + calendar.get(Calendar.MINUTE));
		System.out.println("秒：" + calendar.get(Calendar.SECOND));
		System.out.println("毫秒：" + calendar.get(Calendar.MILLISECOND));
		calendar.add(Calendar.MONTH, 1);
		System.out.println(DateUtils.toDateString(calendar.getTime()));
	}
	
	@Test
	public void addMonthTest() {
		Date date = DateUtils.getDate("2004-2-29");
		date = DateUtils.addMonth(date, 1);
		date = DateUtils.addDay(date, -1);
		System.out.println(DateUtils.toTimeString(date));
		date = DateUtils.getDateTimeNow();
		System.out.println(DateUtils.toTimeString(date));
		date = DateUtils.getDateNow();
		System.out.println(DateUtils.toTimeString(date));
	}
	
	@Test
	public void getURI() {
		String url = "http://localhost/LifeClaimWar/";
		System.out.println(StringUtils.hash("111111"));
		
	}
	
	@Test
	public  void getDayRangeTest() {
		Date date1 = DateUtils.getDate("2014-7-28");
		Date date2 = DateUtils.getDate("2014-8-1");
		System.out.println(DateUtils.getDayRange(date1, date2));
	}
	@Test
	public void addDayTest(){
		long dqDate=System.currentTimeMillis();
		

		System.out.println(		DateUtils.formatDate(DateUtils.addDay(new java.sql.Timestamp(dqDate), 5)));
		
	}
	@Test
	public void addMonth2Test(){
		long dqDate=System.currentTimeMillis();
		
		System.out.println(DateUtils.formatDate(new java.sql.Timestamp(dqDate)));
		System.out.println(	DateUtils.formatDate(DateUtils.addMonth(new java.sql.Timestamp(dqDate), -3)));
		
	}
	@Test
	public  void getMonthRangeTest() {
		Date date1 = DateUtils.getDate("2014-12-28");
		Date date2 = DateUtils.getDate("2015-01-28");
		System.out.println(DateUtils.getMonthRange(date1, date2));
		System.out.println(DateUtils.getRoundUpMonthRange(date1, date2));
	}
}
