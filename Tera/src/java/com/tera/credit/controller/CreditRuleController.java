package com.tera.credit.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 信贷决策引擎Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/credit/rule")
public class CreditRuleController {

	private static final Logger log = Logger.getLogger(CreditRuleController.class);
	
	@RequestMapping("/read.do")
	public String creditRuleRead() {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "credit/rule/creditRuleRead";
	}
}
