/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.sys.model.Bulletin;
import com.tera.sys.model.Menu;
import com.tera.sys.service.BulletinService;

/**
 * @author Wallace chu
 *
 */
@Controller

public class CommonController {

	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(CommonController.class);

	@Autowired
	private BulletinService bulletinService;
	
	/**
	 * @param request request
	 * @param map map
	 * @return login
	 */
	@RequestMapping("/index.do")
	public String goIndex(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/login";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return frame
	 */
	@RequestMapping("/frame.do")
	public String goFrame(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/frame";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return top
	 */
	@RequestMapping("/top.do")
	public String goTop(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/top";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return left
	 */
	@RequestMapping("/left.do")
	public String goLeft(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Menu> menus = (List<Menu>) request.getSession().getAttribute("login_menus");
		String treeString="";
		if(menus!=null){
			treeString+="[";
			for(int i=0;i<menus.size();i++){
				Menu mu=menus.get(i);
				if(mu.getType()==2|| mu.getParentId()==0){
					continue;
				}
				treeString+="{";
				treeString=treeString+"id:"+mu.getId()+",";
				treeString=treeString+"pId:"+mu.getParentId()+",";
				treeString=treeString+"sortNum:"+mu.getOrdernum()+","; 
				treeString=treeString+"file:\""+mu.getUrl()+"\",";
				treeString=treeString+(mu.getType()==0?"open:true,isParent:true,":"");
//		 		treeString=treeString+(mu.getType()==2?"icon:\"js/zTree_v3/css/zTreeStyle/img/diy/2.png\",":"");
				treeString=treeString+"name:\""+mu.getName()+"\"";
				treeString+="}";
				treeString+=i<menus.size()-1?",\n":"\n";
			}
			treeString+="]";
			map.put("treeString", treeString);
		}
		log.info(thisMethodName+":end");
		return "sys/left";
	}
	/**
	 * @param request request
	 * @param map map
	 * @return navigation
	 */
	@RequestMapping("/navigation.do")
	public String goNavigation(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/navigation";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return navigation
	 */
	@RequestMapping("/main.do")
	public String goMain(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Bulletin bulletin = this.bulletinService.getBulletinByLast();
		map.put("bulletin", bulletin);
		log.info(thisMethodName+":end");
		return "sys/main";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return navigation
	 */
	@RequestMapping("/main-table.do")
	public String goMainTable(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/main-table";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return navigation
	 */
	@RequestMapping("/main-tabs.do")
	public String goMainTabs(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/main-tabs";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return navigation
	 */
	@RequestMapping("/main-frame-tabs.do")
	public String goMainFrameTabs(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/main-frame-tabs";
	}

	/**
	 * @param request request
	 * @param map map
	 * @return navigation
	 */
	@RequestMapping("/message.do")
	public String goMessage(String message, HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("message", message);
		log.info(thisMethodName+":end");
		return "sys/message";
	}

}
