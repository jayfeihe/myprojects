/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class TrascTestDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * mapper
	 */
	@Autowired
	private TrascTestDao dao;

	/**
	 * addBpmDefineTest
	 */
	@Test
	@Rollback(false)
	public void addDataTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "code2");
		map.put("value", "value2");
		dao.addData(map);
		if (true) {
			throw new RuntimeException("1234567898765432");
		}
		dao.addData(map);
	}

	/**
	 * addBpmDefineTest
	 */
	@Test
	@Rollback(false)
	public void deleteDataTest() {
		dao.deleteData();
	}

}
