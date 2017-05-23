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
import com.tera.bpm.model.XmlProcess;

/**
 * @author Administrator
 */
public class BpmFactoryTest {

	/**
	 * getStartStateNameTest
	 */
	@Test
	public void getBpmInstanceTest() {
		BpmDefine bpmDefine = new BpmDefine();
		bpmDefine.setProcessName("并行分支流程");
		bpmDefine.setProcessDefFile("config/bpm/并行分支.xml");
		XmlProcess process = BpmFactory.getBpmInstance(bpmDefine);
		System.out.println(process);
		XmlProcess process1 = BpmFactory.getBpmInstance(bpmDefine);
		System.out.println(process1);
		List<String> roles = process.getStateRoles("审核");
		for (String role : roles) {
			System.out.println(role);
		}
	}


}
