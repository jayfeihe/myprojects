package com.greenkoo.inter.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.inter.service.IAdvertInterService;
import com.greenkoo.utils.SecurityUtil;

/**
 * 广告监测接口服务
 * 
 * @author QYANZE
 *
 */
@Controller
public class AdvertInterController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAdvertInterService advertInterService;
	
	@RequestMapping("/illegalAdvert")
	@ResponseBody
	public String advertServer(HttpServletRequest request) {
		String param = request.getParameter("param");
		
		logger.info("广告接口服务接收参数：" + SecurityUtil.URLDecode(param, "UTF-8"));
		
		String returnStr = this.advertInterService.busProcess(param);
		
		return returnStr;
	}
}
