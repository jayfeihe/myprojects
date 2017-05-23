/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.util;

import junit.framework.TestCase;

import org.junit.Test;

import com.tera.sys.model.Role;
import com.tera.sys.model.User;

/**
 * @author Wallace chu
 *
 */
public class JsonUtilTest extends TestCase {

	/**
	 * checkUserTest
	 */
	@Test
	public void object2jsonTest() {
		User user = new User();
		user.setLoginId("储月华");
		user.setName("normaluser");
		user.setPassword("test1");
		user.setGender("1");
//		user.setOrgId("1");
		user.setEmail("user1@tera.com");
		user.setPhone("010-234234");
		user.setMobile("18611384028");
		user.setIsAdmin(1);
		Role role1 = new Role();
		role1.setId(1);
		role1.setName("role1");
		role1.setDescription("desc1");
		Role role2 = new Role();
		role2.setId(2);
		role2.setName("role2");
		role2.setDescription("desc2");
//		user.addAssignRoles(role1);
//		user.addAssignRoles(role2);
		System.out.println(JsonUtil.object2json(user));
	}
	/**
	 * string2jsonTest
	 */
	@Test
	public void string2jsonTest() {
		System.out.println("---------");
		System.out.println(JsonUtil.object2json("abc"));
	}
}
