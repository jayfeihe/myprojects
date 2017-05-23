package com.tera.audit;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.batch.service.ClltBatchService;
import com.tera.sys.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoContext.xml"
				}
		)
public class RetPlanServiceTest extends AbstractTransactionalJUnit4SpringContextTests  {

	@Autowired
	private IRetPlanService retPlanService;
	
	@Autowired
	private ClltBatchService clltBatchService;
	@Rollback(false)
	@Test
	public void testCreateRetPlan() throws Exception {
		
		retPlanService.createRetPlan("A0003");
	}
	@Rollback(false)
	@Test
	public void testCreateRetPlanSplit() throws Exception {
		clltBatchService.autoCreateCheckTask();
	}

	@Test
	public void testRepaymentInt() {
		fail("Not yet implemented");
	}

}
