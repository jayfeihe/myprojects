package com.tera.sys.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.util.DateUtils;

/**
 * @author Wallace chu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
// 测试的数据源配置：请修改daoTestContext.xml文件
"file:WebRoot/WEB-INF/daoTestContext.xml" })
public class WorkdayServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * UserDao
	 */
	@Autowired
	@Qualifier("workdayService")
	private WorkdayService workdayService;

	/**
	 * getUserByIdTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void afterWorkDayTest() throws Exception {
		System.out.print("2014-10-16 stop<<<<<");
		Date date = DateUtils.getDate("2014-10-16");
		date = workdayService.afterWorkDay(date, 4);
		System.out.println(DateUtils.formatDate(date));

		System.out.print("2014-10-17 stop<<<<<");
		date = DateUtils.getDate("2014-10-17");
		date = workdayService.afterWorkDay(date, 4);
		System.out.println(DateUtils.formatDate(date));

		System.out.print("2014-10-18 stop<<<<<");
		date = DateUtils.getDate("2014-10-18");
		date = workdayService.afterWorkDay(date, 4);
		System.out.println(DateUtils.formatDate(date));

		System.out.print("2014-10-19 stop<<<<<");
		date = DateUtils.getDate("2014-10-19");
		date = workdayService.afterWorkDay(date, 4);
		System.out.println(DateUtils.formatDate(date));

		System.out.print("2014-10-20 stop<<<<<");
		date = DateUtils.getDate("2014-10-20");
		date = workdayService.afterWorkDay(date, 4);
		System.out.println(DateUtils.formatDate(date));

		System.out.print("2014-10-21 stop<<<<<");
		date = DateUtils.getDate("2014-10-21");
		date = workdayService.afterWorkDay(date, 4);
		System.out.println(DateUtils.formatDate(date));
		

		System.out.print("2014-10-22 stop<<<<<");
		date = DateUtils.getDate("2014-10-22");
		date = workdayService.afterWorkDay(date, 4);
		System.out.println(DateUtils.formatDate(date));
	}
}
