/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.sys.model.Menu;

/**
 * @author Wallace chu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class MenuServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * MenuDao
	 */
	@Autowired
	@Qualifier("menuServiceImpl")
	private MenuService menuService;
	//private StringBuffer ccc;

	/**
	 * getmenuByIDTest
	 */
	@Test
	public void getAllMenuTest() {
		List<Menu> menus = menuService.getAllMenu();
		for (Menu menu : menus) {
			System.out.println(menu);
		}
	}
	/**
	 * getmenusbyuserId
	 */
//	@Test
//	public void getMenuByUserIdTest() {
//		int userId = 2;
//		List<Menu> menus = menuService.getMenuByUserId(userId);
//		for (Menu menu : menus) {
//			System.out.println(menu);
//		}
//	}

}
