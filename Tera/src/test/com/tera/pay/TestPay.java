package com.tera.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.payment.model.Payment;
import com.tera.payment.pay.PayService;
import com.tera.payment.service.PaymentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class TestPay  extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired(required=false)
	private PaymentService<Payment> paymentService;
	@Autowired(required=false)
	private PayService defaultPayService;
	
	@Rollback(false)
	@Test
	public void excuteBatchDaifu () throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("contractNo", "L86201505110002");
		map.put("inOut", "1");
		List<Payment> pmtList = paymentService.queryList(map);
		defaultPayService.batchDaishou(pmtList);
	}
}
