package com.tera.payment.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.payment.model.Payment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class PaymentServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	private final static Logger log = Logger.getLogger(PaymentServiceTest.class);

	@Autowired
	private PaymentService<Payment> paymentService;
	
	@Test
	@Rollback(false)
	public void insert() throws Exception {
		Payment pay = new Payment();
		pay.setContractNo("测试");
		pay.setInOut("1");
		pay.setPlanAmount(23);
		pay.setCreateTime(new Timestamp(System.currentTimeMillis()));
		paymentService.add(pay);
	}
	
	@Test
	@Rollback(false)
	public void update() throws Exception {
		Payment pay = new Payment();
		pay.setId(2);
		pay.setContractNo("测试1");
		pay.setInOut("1");
		pay.setPlanAmount(0);
		pay.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		paymentService.updateOnlyChanged(pay);
	}

}
