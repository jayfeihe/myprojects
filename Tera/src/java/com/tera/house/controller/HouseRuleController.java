package com.tera.house.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 决策引擎Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/house/rule")
public class HouseRuleController {

	private static final Logger log = Logger.getLogger(HouseRuleController.class);
	
	@RequestMapping("/read.do")
	public String carRuleRead() {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "house/rule/houseRuleRead";
	}
}
