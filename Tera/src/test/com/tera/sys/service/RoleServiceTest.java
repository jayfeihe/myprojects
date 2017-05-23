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

import com.tera.sys.model.Role;

/**
 * @author Wallace chu
 *
 */
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
public class RoleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	/**
	 * RoleService
	 */
	@Autowired
	@Qualifier("roleServiceImpl")
	private RoleService roleService;

	/**
	 * addRoleTest
	 */
	@Test
	public void addRoleTest() {
		Role role = new Role();
		role.setName("name1");
		role.setDescription("description1");
		roleService.addRole(role);
		System.out.println(roleService.getRoleCount());
	}

	/**
	 * getRoleByIDsTest
	 */
	@Test
	public void getRoleByIDsTest() {
		int[] ids = new int[]{1, 2, 3};
		List<Role> roles = roleService.getRoleByIDs(ids);
		for (Role role : roles) {
			System.out.println(role);
		}
	}

}
