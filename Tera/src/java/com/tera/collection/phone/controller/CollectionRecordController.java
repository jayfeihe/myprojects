/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.collection.phone.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.collection.phone.model.CollectionRecord;
import com.tera.collection.phone.service.CollectionRecordService;
import com.tera.credit.model.CreditContact;
import com.tera.credit.service.CreditContactService;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;

/**
 * 
 * 催收记录表控制器
 * <b>功能：</b>CollectionRecordController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:39:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collectionRecord/phone")
public class CollectionRecordController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollectionRecordController.class);
	
	/**
	 * CollectionRecordService
	 */
	@Autowired(required=false) //自动注入
	private CollectionRecordService collectionRecordService;
	@Autowired(required=false) //自动注入
	private CreditContactService creditContactService;
	
	/**
	 * 跳转到催收记录表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collectionRecordQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/phone/collectionRecordQuery";
	}

	/**
	 * 显示催收记录表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collectionRecordList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(CollectionRecord.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.collectionRecordService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CollectionRecord> collectionRecordList = this.collectionRecordService.queryList(queryMap);
		pm.setData(collectionRecordList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/phone/collectionRecordList";
	}

	/**
	 * 新增联系人记录历史
	 * @param id id 
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public void collectionRecordUpdate(CollectionRecord bean,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String loginId=user.getLoginId();
			//如果存在
			if(bean!=null){
				String lateDays=request.getParameter("lateDays_str"); 
				Timestamp timestamp=new Timestamp(new Date().getTime());
//				SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				bean.setCollectionWay("1");//电催渠道
//				bean.setContractNo(contractNo);//合同编号
				bean.setCreateUid(loginId);
				bean.setCallTime(timestamp);
				bean.setCreateTime(timestamp);
//				bean.setUpdateUid(loginId);
//				bean.setUpdateTime(timestamp);
				bean.setCollectionUid(loginId);
				bean.setLateDays(Integer.parseInt(lateDays));
				this.collectionRecordService.add(bean);
				
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			// TODO: handle exception
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		
//		map.put("bean", bean);
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
//		return "collection/phone/collectionRecordUpdate";
	}
	/**
	 * 新增联系人
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/creditContactUpdate.do")
	public void creditContactUpdate(CollectionRecord bean,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String loginId=user.getLoginId();
			//如果存在
			if(bean!=null){
				String contactname=request.getParameter("contactname");
				String contactrelation=request.getParameter("contactrelation");
				String contactmobile=request.getParameter("contactmobile");
				String appId=request.getParameter("appId");
				
//				Map<String, Object> creditContactMap = new HashMap<String, Object>();;
//				creditContactMap.put("appId", appId);
//				creditContactMap.put("name", contactname);
//				List<CreditContact> creditContactList=this.creditContactService.queryList(creditContactMap);
				
//				if(creditContactList.size()>0){
//					CreditContact creditContactOld=creditContactList.get(0);
//					creditContactOld.setType("3");					//类型
//					creditContactOld.setAppId(appId);
//					creditContactOld.setName(contactname);
//					creditContactOld.setRelation(contactrelation);
//					creditContactOld.setMobile(contactmobile);
//					creditContactOld.setOperator(loginId);
//					creditContactOld.setOrgId(org.getOrgId());
//					creditContactOld.setUpdateTime(new Timestamp(new Date().getTime()));
//					this.creditContactService.updateOnlyChanged(creditContactOld);//相关联系人
//				}else{
					CreditContact creditContact=new CreditContact();
					creditContact.setType("3");					//类型
					creditContact.setAppId(appId);
					creditContact.setName(contactname);
					creditContact.setRelation(contactrelation);
					creditContact.setMobile(contactmobile);
					creditContact.setOperator(loginId);
					creditContact.setOrgId(org.getOrgId());
					creditContact.setCreateTime(new Timestamp(new Date().getTime()));
					this.creditContactService.add(creditContact);//相关联系人
//				}
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			// TODO: handle exception
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		
//		map.put("bean", bean);
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
//		return "collection/phone/collectionRecordUpdate";
	}
	/**
	 * 保存催收记录表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void collectionRecordSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			CollectionRecord bean = (CollectionRecord) RequestUtils.getRequestBean(CollectionRecord.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.collectionRecordService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.collectionRecordService.add(bean);
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
	 * 删除催收记录表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collectionRecordDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.collectionRecordService.delete(id);
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
	 * 跳转到查看催收记录表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String collectionRecordRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollectionRecord bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.collectionRecordService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/phone/collectionRecordRead";
	}

}
