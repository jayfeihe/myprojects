/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.contract.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tera.contract.model.Seal;
import com.tera.contract.service.SealService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.SealImageUtil;

/**
 * 
 * 控制器
 * <b>功能：</b>SealController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-29 13:26:30<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/seal")
public class SealController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(SealController.class);
	
	/**
	 * SealService
	 */
	@Autowired(required=false) //自动注入
	private SealService sealService;
	
	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	
	/**
	 * 跳转到的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/getSealImage.do")
	public void sealQuery(String contractNo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OutputStream out = response.getOutputStream();
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		Seal seal = new Seal();
		seal.setCreateTime(new Timestamp(System.currentTimeMillis()));
		seal.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		seal.setState("1");
		seal.setContractNo(contractNo);
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		seal.setSealNo(uuid);
		this.sealService.add(seal);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String companyName = parameterService.queryByParamName("companyName").getParamValue();
		BufferedImage img = SealImageUtil.bufferedImageUtil(companyName, "电子专用章", sdf.format(new Date()), uuid);
//		PNGImageWriter writer = PNGImageWriter.PNGImageWriter(img)
//		ImageIO.write(img, "png", response.getOutputStream());
		ImageIO.write(img, "png", out); 
		out.flush();
		out.close();
		log.info(thisMethodName+":end");
	}



}
