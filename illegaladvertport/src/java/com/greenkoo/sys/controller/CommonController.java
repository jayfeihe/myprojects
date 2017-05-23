package com.greenkoo.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author QYANZE
 *
 */
@Controller
public class CommonController {

	@RequestMapping({"/","/index"})
	public String index(Model model) {
		return "index";
	}
}
