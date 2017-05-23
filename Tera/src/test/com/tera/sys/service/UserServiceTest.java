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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.sys.model.User;

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
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * UserDao
	 */
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	/**
	 * getUserByIDTest
	 */
	@Test
	public void getUserByIDTest() {
		int[] ids = new int[]{1, 2};
		List<User> users = userService.getUserByIDs(ids);
		for (User user : users) {
			System.out.println(user);
		}
	}

	/**
	 * getUserTest
	 */
	@Test
	public void getUserTest() {
		System.out.println(userService.getUser("admin"));
	}

	/**
	 * addUserTest
	 */
	@Test
	@Rollback(false)
	public void addUserTest() {
		int n = userService.getUserCount(null);
		User user = new User();
		user.setLoginId("test" + n);
		user.setName("normaluser");
		user.setPassword("test" + n);
		user.setGender("1");
//		user.setOrgId("1");
		user.setEmail("user1@tera.com");
		user.setPhone("010-234234");
		user.setMobile("18611384028");
		user.setIsAdmin(1);
		userService.addUser(user);
		System.out.println(userService.getUserCount(null));
	}

	/**
	 * getUserCountTest
	 */
	@Test
	public void getUserCountTest() {
		System.out.println(userService.getUserCount(null));
	}
	/**
	 * queryUserTest
	 */
	@Test
	public void queryUserTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowS", 1);
		map.put("rowE", 3);
		map.put("loginID", "fds");
		List<User> users = userService.queryUser(map);
		for (User user : users) {
			System.out.println(user);
		}
	}
	/**
	 * getUserByIdTest
	 */
	@Test
	public void getUserByIdTest() {
		User user = userService.getUserById(27);
			System.out.println(user);
	}
}
