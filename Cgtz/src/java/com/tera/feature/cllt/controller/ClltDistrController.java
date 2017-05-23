/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.cllt.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.feature.cllt.model.ClltDistr;
import com.tera.feature.cllt.service.IClltDistrService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.UserExtService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 催收分配表控制器
 * <b>功能：</b>ClltDistrController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-21 14:38:27<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/cllt/distr")
public class ClltDistrController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ClltDistrController.class);
	
	/**
	 * ClltDistrService
	 */
	@Autowired(required=false) //自动注入
	private IClltDistrService clltDistrService;
	
	@Autowired(required=false) //自动注入
	private UserExtService userExtService;
	
	@Autowired(required=false) //自动注入
	private IContractService contractService;
	
	/**
	 * 跳转到催收分配表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String clltDistrQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "cllt/distr/clltDistrQuery";
	}

	/**
	 * 显示催收分配表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String clltDistrList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(ClltDistr.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<ClltDistr> clltDistrList = this.clltDistrService.queryPageList(queryMap);
		pm.setData(clltDistrList);
		pm.initRowsCount(clltDistrList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "cllt/distr/clltDistrList";
	}

	/**
	 * 跳转到更新催收分配表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String clltDistrUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		ClltDistr bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.clltDistrService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "cllt/distr/clltDistrUpdate";
	}

	/**
	 * 保存催收分配表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void clltDistrSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			ClltDistr bean = (ClltDistr) RequestUtils.getRequestBean(ClltDistr.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.clltDistrService.update(bean);
			} else { //如果不存在
				this.clltDistrService.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 删除催收分配表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void clltDistrDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.clltDistrService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看催收分配表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String clltDistrRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		ClltDistr bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.clltDistrService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "cllt/distr/clltDistrRead";
	}
	@RequestMapping("/listRole.do")
	public void listWarehouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<UserExt> warehouses = this.userExtService.queryList(null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(warehouses));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	//修改分单(标识为1，已分到单的))
	@RequestMapping("/updateAssign.do")
	public void assignReviewUpdate(String[] appIds,String clltMan, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			String loginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);
			//根据选择的逾期合同编号,查找分单记录,更改催收人
			for (String appId : appIds) {
				ClltDistr cltd=clltDistrService.queryByConId(appId);
				//发生逾期，未分单；进行分单
				if(cltd==null){
					ClltDistr cllt=new ClltDistr();
					cllt.setContractId(appId);
					cllt.setClltUid(clltMan);
					cllt.setCreateTime(nowTime);
					cllt.setCreateUid(loginId);
					cllt.setIsCur("1");
					//插入记录
					clltDistrService.add(cllt);
				}else{
					cltd.setClltUid(clltMan);
					cltd.setUpdateTime(nowTime);
					cltd.setIsCur("1");
					clltDistrService.update(cltd);
				}			
				//更改合同信息
				Contract contract=contractService.queryByContractId(appId);
				contract.setIsLateDis("1");
				if(contract.getIsLateDis().equals("0")){
					contract.setIsLateDis("1");
				}
				contract.setUpdateTime(nowTime);
				contractService.update(contract);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "分配失败，请联系系统管理员")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

}
