/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.service;

import java.util.List;

import org.junit.Test;

import com.tera.bpm.model.BpmDefine;

/**
 * @author Administrator
 */
public class BpmFactoryTest {

	/**
	 * initBpmTest
	 */
	@Test
	public void initBpmTest() {
		BpmDefine bpmDefine = new BpmDefine();
		bpmDefine.setProcessName("调查流程");
		bpmDefine.setProcessDefFile("xml/调查流程.xml");
		BpmFactory.initBpm(bpmDefine);
		System.out.println(BpmFactory.isInitedBpm("调查流程"));
	}

	/**
	 * getStartStateNameTest
	 */
	@Test
	public void getStartStateNameTest() {
		BpmDefine bpmDefine = new BpmDefine();
		bpmDefine.setProcessName("调查流程");
		bpmDefine.setProcessDefFile("xml/调查流程.xml");
		BpmFactory.initBpm(bpmDefine);
		System.out.println(BpmFactory.getStartStateName("调查流程"));
	}

	/**
	 * getStartStateNameTest
	 */
	@Test
	public void getStateRolesTest() {
		BpmDefine bpmDefine = new BpmDefine();
		bpmDefine.setProcessName("HD抵押借款流程");
		bpmDefine.setProcessDefFile("config/bpm/HD抵押借款流程.xml");
		BpmFactory.initBpm(bpmDefine);
		List<String> roles = BpmFactory.getStateRoles("HD抵押借款流程", "特殊件审核");
		for (String role : roles) {
			System.out.println(role);
		}
	}

	/**
	 * getTransitionStateTest
	 */
	@Test
	public void getTransitionStateTest() {
		BpmDefine bpmDefine = new BpmDefine();
		bpmDefine.setProcessName("调查流程");
		bpmDefine.setProcessDefFile("xml/调查流程.xml");
		BpmFactory.initBpm(bpmDefine);
		List<String> list = BpmFactory.getTransitionStates("调查流程", "创建");
		for (String str : list) {
			System.out.println(str);
		}
	}
}
