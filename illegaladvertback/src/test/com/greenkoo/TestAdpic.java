package com.greenkoo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.greenkoo.record.service.IAdPicService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/applicationContext-dao.xml", 
		"classpath:spring/applicationContext-service.xml"})
public class TestAdpic extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private IAdPicService adPicService;
	
	@Test
	@Rollback(false)
	public void test() {
		this.adPicService.adPicProccess();;
	}
}
