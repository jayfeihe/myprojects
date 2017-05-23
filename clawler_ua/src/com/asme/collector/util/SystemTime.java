package com.asme.collector.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * 系统时间工具类
 * @author asme
 *
 * 2012-11-9 下午11:30:57
 */
@Component("systemTime")
public class SystemTime {

	// 计时间隔
	private static final int TICKMILLIS = 1000;

	// 毫秒时间
	private volatile long time;

	// GMT时间
	private volatile String gmt;

	// 年月日时分秒
	private volatile int yyyyMMdd;
	private volatile String yyyyMMddHHmmss;
	private volatile int year;
	private volatile int month;
	private volatile int date;
	private volatile int hour;
	private volatile int minute;
	private volatile int second;

	// 星期(0 - 6表示星期一到日)
	private volatile int week;

	// 当前小时还剩下多少秒
	private volatile int hourLeftSeconds;

	// 今天还剩下多少秒
	private volatile int dayLeftSeconds;

	// 计时器
	@Resource(name = "timer")
	private Timer timer;

	// 初始化
	@PostConstruct
	public void init() {

		// 日期格式化器
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		final SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final SimpleDateFormat gmtFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		gmtFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));

		// 初始化时间
		time = System.currentTimeMillis();
		yyyyMMddHHmmss = sformat.format(time);
		long now = Long.parseLong(format.format(time));
		gmt = gmtFormat.format(time);
		yyyyMMdd = (int) (now / 1000000L);
		year = (int) (now / 10000000000L);
		month = (int) ((now % 10000000000L) / 100000000L);
		date = (int) ((now % 100000000L) / 1000000L);
		hour = (int) ((now % 1000000L) / 10000L);
		minute = (int) ((now % 10000L) / 100L);
		second = (int) ((now % 100L));
		hourLeftSeconds = 60 * (59 - minute) + 60 - second;
		dayLeftSeconds = 3600 * (23 - hour) + hourLeftSeconds;

		// 初始化week
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		
		// 初始化finishDate
		Calendar c1 = Calendar.getInstance();
		c1.set(year, month, date, 0, 0, 0);
		c1.add(Calendar.DATE, 1); 

		// dayOfWeek用1-7表示周日到周六, week用0-6表示周一到周日
		week = dayOfWeek - 2;
		if (week < 0) week = 6;

		// 用计时器每秒更新时间
		timer.timing(new TimerTask() {

			// 间隔,触发方式,任务类型
			public long delayOrIntervalMillis() {return TICKMILLIS;}
			public boolean isTriggerIndependently() {return false;}
			public Type type() { return Type.INTERVAL; }

			/** 
			 * (non-Javadoc)
			 *
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				time = System.currentTimeMillis();
				long now = Long.parseLong(format.format(time));
				gmt = gmtFormat.format(time);
				yyyyMMddHHmmss = sformat.format(time);
				yyyyMMdd = (int) (now / 1000000L);
				year = (int) (now / 10000000000L);
				month = (int) ((now % 10000000000L) / 100000000L);
				int t = (int) ((now % 100000000L) / 1000000L);
				if (t != date) {

					// 日期发生了变化,更新week
					week = week == 6 ? 0 : week + 1;
				}
				date = t;
				hour = (int) ((now % 1000000L) / 10000L);
				minute = (int) ((now % 10000L) / 100L);
				second = (int) ((now % 100L));
				hourLeftSeconds = 60 * (59 - minute) + 60 - second;
				dayLeftSeconds = 3600 * (23 - hour) + hourLeftSeconds;
			}
		});
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @return the yyyyMMdd
	 */
	public int getYyyyMMdd() {
		return yyyyMMdd;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * @param timer
	 *            the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * @return the hourLeftSeconds
	 */
	public int getHourLeftSeconds() {
		return hourLeftSeconds;
	}

	/**
	 * @return the dayLeftSeconds
	 */
	public int getDayLeftSeconds() {
		return dayLeftSeconds;
	}

	/**
	 * @return the week
	 */
	public int getWeek() {
		return week;
	}

	/**
	 * @return the gmt
	 */
	public String getGmt() {
		return gmt;
	}

	/**
	 * @return the yyyyMMddHHmmss
	 */
	public String getYyyyMMddHHmmss() {
		return yyyyMMddHHmmss;
	}
}
