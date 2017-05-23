package com.greenkoo;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.greenkoo.inter.service.IAdFeedBackInterService;
import com.greenkoo.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/applicationContext-dao.xml", 
		"classpath:spring/applicationContext-service.xml"})
public class TestFeedBackInter extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private IAdFeedBackInterService adFeedBackInterService;
	
	@Test
	public void test() {
		this.adFeedBackInterService.feedBack();
	}
	
	public static void main(String[] args) {
		Date beforeFeedBackDay = DateUtil.addDate(DateUtil.getCurDate(), Calendar.DATE, Integer.valueOf("-3").intValue());
		
		System.out.println(DateUtil.formatDate(beforeFeedBackDay, DateUtil.FORMAT_DATE));
	}
}
