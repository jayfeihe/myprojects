/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.collection.visit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionBaseInfo;
import com.tera.collection.phone.model.CollectionRecord;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.phone.service.CollectionRecordService;

import com.tera.collection.visit.model.VisitOrderBean;
import com.tera.collection.visit.model.VisitRecordFormBean;
import com.tera.collection.visit.model.VisitRelations;
import com.tera.collection.visit.service.CollectionBaseVisitService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditContact;
import com.tera.credit.model.CreditContactHistory;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditContactService;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.RoleService;

/**
 * 
 * 催收记录表控制器
 * <b>功能：</b>CollectionRecordController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:39:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/visit/record")
public class CollectionRecordVisitController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollectionRecordVisitController.class);
	 
	@Autowired(required=false)
	private CollectionBaseVisitService collectionBaseVisitService;
	
	@Autowired(required=false) //自动注入
	private CollectionBaseService collectionBaseService;
	@Autowired(required=false) //自动注入
	private CreditContactService creditContactService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService<?> dataDictionaryService;
	@Autowired(required=false) //自动注入
	private CollectionRecordService collectionRecordService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	/**
	 * 显示催收信息详细信息
	 * @param request request
	 * @param String id
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String collectionBaseRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollectionBaseInfo bean = null;
		
		Map<String, Object> infoMap=null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			infoMap=  new HashMap<String, Object>();
			infoMap.put("contractNo",id);
			bean = collectionBaseService.queryInfo(infoMap);
		}
		
	
		String contractNo=bean.getContractNo(); //合同编号
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", contractNo);
		//queryMap.put("type", "3");
		List<CreditContactHistory> commonContacts= new ArrayList<CreditContactHistory>();
		List<Contract> contactFirstList=contractService.queryList(queryMap);//联系人本人
	
		CreditApp creditApp  = this.creditAppService.queryByContractNo(id);
		if(creditApp!=null){
			
			if(creditApp.getMobile()!=null&&!"".equals(creditApp.getMobile())){
				CreditContactHistory creditContactFirst=new CreditContactHistory();
				creditContactFirst.setName(creditApp.getName());
				creditContactFirst.setRelation(CollectionConstant.CONTACT_RELATION_SELF);
				creditContactFirst.setMobile(creditApp.getMobile());
				creditContactFirst.setType(CollectionConstant.CONTACT_TYPE_SELF);
				commonContacts.add(creditContactFirst);
			}
			if(creditApp.getSpouseMobile()!=null&&!"".equals(creditApp.getSpouseMobile())){
				CreditContactHistory creditContactFirst=new CreditContactHistory();
				creditContactFirst.setName(creditApp.getSpouseName());
				creditContactFirst.setRelation(CollectionConstant.CONTACT_RELATION_SPOUSE);
				creditContactFirst.setMobile(creditApp.getSpouseMobile());
				creditContactFirst.setType(CollectionConstant.CONTACT_TYPE_SPOUSE);
				commonContacts.add(creditContactFirst);
			}
		}
		
	/*	
		if(contactFirstList.size()>0){
			CreditContactHistory creditContactFirst=new CreditContactHistory();
			Contract contactFirst=(Contract) contactFirstList.get(0); 
			creditContactFirst.setName(contactFirst.getLoanName());
			creditContactFirst.setRelation("8");
			creditContactFirst.setMobile(contactFirst.getBankMobile());
			creditContactFirst.setType("1");
			commonContacts.add(creditContactFirst);
		}*/
		List<CreditContactHistory> creditContactList= this.creditContactService.getRelationList(queryMap);
		if(creditContactList!=null){
			commonContacts.addAll(creditContactList);
		}
		Map<String, Object> dictionaryMap = new HashMap<String, Object>();
		dictionaryMap.put("keyName", "collectionsummary");//催收摘要
		List dictionarylist = this.dataDictionaryService.queryList(dictionaryMap);
		//end by wangyongliang 20150612
		
		/*Map<String, Object> historyMap =  new HashMap<String, Object>();
		historyMap.put("contractNo", contractNo);
		List<CollectionRecord> collectionHistoryList = this.collectionRecordService.queryList(historyMap);*/
		
		//添加登陆人员角色roleName
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Map<String, Object> queryMap1=new HashMap<String,Object>();
		queryMap1.put("loginId",user.getLoginId());
		queryMap1.put("orgId",org.getOrgId());
		List<Role> roles=roleService.getRoleByOrgLoginId(queryMap1);
		
		
		 
		 
			
	
		map.put("creditApp",creditApp);
		 
		map.put("roleName", roles.get(0).getName());
		
		map.put("bean", bean);
		map.put("commonContacts", commonContacts);
		map.put("dictionarylist", dictionarylist);
	/*	map.put("collectionHistoryList", collectionHistoryList);*/
		log.info(thisMethodName+":end");
		return "collection/visit/visitDetailRead";
	}
	/**
	 * 获取催收拨打记录
	 * @param contractNo contractNo
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/getRecordHistory.do")
	public String getRecordHistory(String contractNo, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Map<String, Object> historyMap =  new HashMap<String, Object>();
		historyMap.put("contractNo", contractNo);
		List<CollectionRecord> collectionHistoryList = this.collectionRecordService.queryList(historyMap);
		map.put("collectionHistoryList", collectionHistoryList);
		log.info(thisMethodName+":end");
		return "collection/visit/recordHistory";
	}
	
	/**
	 * 催收详情中进行拨打联系人号码
	 * @param request request
	 * @param map map
	 * @param String id
	 * @param String name
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/callRelation.do")
	public String callFrontCollection(String contractNo,String creditContactId,String relation,String name,String mobile,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//检查String id是id还是appId
		CreditContactHistory bean = new CreditContactHistory();
		bean.setId(Integer.parseInt(creditContactId));
		bean.setContractNo(contractNo);
		bean.setRelation(relation);
		bean.setName(name);
		bean.setMobile(mobile);
		map.put("bean",bean );
		log.info(thisMethodName+":end");
		return "collection/visit/visitDetailCall";
	}
	/**
	 * 添加催收记录
	 * @param request request
	 * @param VisitRelations visitRelations
	 * @param map map
	 * @throws IOException 
	 * @throws Exception exception
	 * @throws IOException 
	 */
	@RequestMapping("/saveRecord.do")
	 public void saveCollectionRecord( HttpServletRequest request,HttpServletResponse response, ModelMap map) throws IOException {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Object bean = RequestUtils.getRequestBean(VisitRecordFormBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		//联系人   联系   操作备注  摘要  联系方式   合同号（appId）  
		Timestamp time=new Timestamp(new Date().getTime());
		//修改base
		try{
		CollectionBase collectionBase=collectionBaseVisitService.queryByKey(queryMap.get("contractNo"));
		collectionBase.setHandleState("Y");//处理表示  Y:处理
		if(collectionBase.getCollectionWayCur().equals(CollectionConstant.COLLECTION_WAY_CUR_VISIT)){
			collectionBase.setFollowTime(time);//跟进时间
		}
		else
		{
			collectionBase.setHelpFollowTime(new Timestamp(new Date().getTime()));//协催跟进时间
		}
		collectionBaseService.update(collectionBase);//更新collectionBase表
		
		//System.out.println(collectionBase.getColllectionUid()+"====================================");
		
		CollectionRecord cr=new CollectionRecord();
		cr.setContractNo(queryMap.get("contractNo").toString());			//合同号
		cr.setCollectionUid(user.getLoginId() );	//催收人
		cr.setCollectionWay(collectionBase.getCollectionWayCur());	//催收渠道
		cr.setIsHelp(collectionBase.getIsHelp());					//是否协催
		cr.setCustomerId(collectionBase.getCustomerId());			//客户id
		cr.setIdType(collectionBase.getIdType());					//证件类型
		cr.setIdNo(collectionBase.getIdNo());						//证件号码
		cr.setLateDays(collectionBase.getLateDays());				//逾期天数
		cr.setCallTime(time);										//拨打时间
		cr.setTel(queryMap.get("mobile").toString());				//联系方式
		cr.setAnswerName(queryMap.get("name").toString());			//联系人姓名
		cr.setAnswerRelation(queryMap.get("relation").toString());
		cr.setCreateTime(time);										//创建时间
		cr.setCreateUid(user.getLoginId());							//创建人
		cr.setPhoneSummary(queryMap.get("phoneSummary").toString());//摘要
		cr.setRemark(queryMap.get("remark").toString());			//备注
		collectionRecordService.add(cr);
		writer.print(JsonUtil.object2json(new JsonMsg(true, "拨打成功")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writer.print(JsonUtil.object2json(new JsonMsg(false, "拨打失败")));
		}finally{
			writer.flush();
			writer.close();
		}
		
		
		log.info(thisMethodName+":end"); 
		 
	 }
		
	/**
	 * 催收详情添加联系人
	 * @param request request
	 * @param VisitRelations visitRelations
	 * @param map map
	 * @throws IOException 
	 * @throws Exception exception
	 * @throws IOException 
	 */
	@RequestMapping("/addRelation.do")
	public void addCollectionRelations(String submitType,VisitRelations visitRelations,BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
	//	System.out.println("===================提交还是保存================="+submitType);
		try{
		Timestamp time=new Timestamp(new Date().getTime());			//分配时间
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		List<CreditContact> commonContacts=visitRelations.getCommonContacts();
		 
		
		//修改基本表中的        预约时间           摘要  接听状态
		VisitOrderBean bean = (VisitOrderBean) RequestUtils.getRequestBean(VisitOrderBean.class, request);
		Map<String,Object> maps=ObjectUtils.describe(bean);
		if (bean.getId()!=null&&!"".equals(bean.getId())) {
			maps.put("updateUid",user.getLoginId());
			maps.put("updateTime",time);
			if(submitType!=null&&"submit".equals(submitType)){
				maps.put("submitType",submitType);
			}
			else
			{
				maps.put("submitType","");
			}
			System.out.println(submitType+"======================");
			collectionBaseVisitService.updateOnlyChanged(maps);//更新collectionBase表
		}
		for (CreditContact commonContact : commonContacts) {
			if(commonContact.getId()==0){// 添加
				if("1".equals(commonContact.getState())){	//只有在状态 是 1 的时候 才添加
					commonContact.setAppId(commonContact.getAppId());
					commonContact.setOrgId(org.getOrgId());
					commonContact.setOperator(user.getLoginId());
					commonContact.setCreateTime(time);
					commonContact.setUpdateTime(time);
					this.creditContactService.add(commonContact);
				}
			}else{
				commonContact.setOperator(user.getLoginId());
				commonContact.setUpdateTime(time);
				this.creditContactService.updateOnlyChanged(commonContact);
			}
		}
		
			writer.print(JsonUtil.object2json(new JsonMsg(true, submitType!=null&&"submit".equals(submitType)?"提交成功":"保存成功")));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writer.print(JsonUtil.object2json(new JsonMsg(false, submitType!=null&&"submit".equals(submitType)?"提交失败，程序异常。":"保存失败，程序异常。")));
			writer.flush();
			writer.close();
			log.info(thisMethodName+":end"); 
			
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end"); 
	}
	
	/**
	 * 删除催收添加的联系人
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collectionBaseDelete(String creditContactId, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.creditContactService.delete(creditContactId);//相关联系人
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
}
