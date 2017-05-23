/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.audit.common.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.common.model.NodeOrder;
import com.tera.audit.common.service.INodeOrderService;
import com.tera.util.JsonUtil;

/**
 * 
 * 流程环节表控制器
 * <b>功能：</b>NodeOrderController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-09 14:19:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/common/nodeorder")
public class NodeOrderController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(NodeOrderController.class);
	
	/**
	 * NodeOrderService
	 */
	@Autowired(required=false) //自动注入
	private INodeOrderService nodeOrderService;
	
	/**
	 * 根据节点名称和类型查找相应节点
	 * @param nodeName
	 * @param type
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/listNode.do")
	public void listNode(String name,String type,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("name", name);
		queryMap.put("type", type);
		List<NodeOrder> nodes = this.nodeOrderService.queryList(queryMap);
		
		if (nodes != null && nodes.size() > 0) {
			NodeOrder curNode = nodes.get(0);
			queryMap.remove("name");
			queryMap.put("lNum", curNode.getNum());
			List<NodeOrder> nodeList = this.nodeOrderService.queryList(queryMap);
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print(JsonUtil.object2json(nodeList));
			writer.flush();
			writer.close();
		}
		
		log.info(thisMethodName+":end");
	}

}
