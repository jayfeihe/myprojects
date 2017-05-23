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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.bpm.model.BpmDefine;

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
public class BpmDefineDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * mapper
	 */
	@Autowired
	private BpmDefineDao dao;

	/**
	 * addBpmDefineTest
	 */
	@Test
	@Rollback(false)
	public void addBpmDefineTest() {
		BpmDefine def = new BpmDefine();
		def.setProcessDefFile("xml/测试流程.xml");
		def.setProcessName("测试流程");
		def.setRemark("备注");
		dao.addBpmDefine(def);
		System.out.println(def);
	}

	/**
	 * getBpmDefineByIdTest
	 */
	@Test
	public void getBpmDefineByIdTest() {
		BpmDefine def = dao.getBpmDefineById(3);
		System.out.println(def);
	}

	/**
	 * getBpmDefineByNameTest
	 */
	@Test
	@Rollback(false)
	public void getBpmDefineByNameTest() {
		BpmDefine def = dao.getBpmDefineByName("SIU调查流程");
		System.out.println(def);
	}
}
