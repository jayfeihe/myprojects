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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.judicial.model.CollectionApplication;
import com.tera.collection.judicial.service.CollectionApplicationService;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionBaseInfo;
import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.model.CollectionRecord;
import com.tera.collection.phone.model.LateDaysCal;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.phone.service.CollectionDistributionService;
import com.tera.collection.phone.service.CollectionRecordService;
import com.tera.collection.reduce.service.RemissionHandlerService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.contract.service.PaymentManageService;
import com.tera.credit.model.CreditContact;
import com.tera.credit.model.CreditContactHistory;
import com.tera.credit.service.CreditContactService;
import com.tera.credit.service.CreditSignService;

import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;

import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;

/**
 * 
 * 催收信息基本表控制器
 * <b>功能：</b>CollectionBaseController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collectionBase/phone")
public class CollectionBaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollectionBaseController.class);
	
	/**
	 * CollectionBaseService
	 */
	@Autowired(required=false) //自动注入
	private CollectionBaseService collectionBaseService;
	@Autowired(required=false) //自动注入
	private CreditContactService creditContactService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService dataDictionaryService;
	@Autowired(required=false) //自动注入
	private CollectionRecordService collectionRecordService;
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	@Autowired(required=false) //自动注入
	private UserService userService;
	@Autowired(required=false) //自动注入
	CollectionApplicationService collectionApplicationService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	private PaymentManageService paymentManageService;
	@Autowired(required=false) //自动注入
	CreditSignService creditSignService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	/**页
	 * 跳转到催收分单信息基本表的查询条件面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collectionBaseQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/phone/collectionPhonePartQuery";
	}
	/**
	 * 跳转到催收逾期计算器页面"
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/lateDaysCalQuery.do")
	public String lateDaysCalQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/phone/LateDaysCal";
	}
	/**
	 * 跳转到催收列表信息基本表的查询条件面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return stringactionquery.do
	 */
	
	@RequestMapping("/actionquery.do")
	public String collectionBaseActionQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/phone/collectionPhoneQuery";
	}
	/**
	 * 逾期计算
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/lateDaysCalList.do")
	public String lateDaysCalList(String id,String loanDate,HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//如果存在
		if (null != id && !"".equals(id)) {
			Date repaymentDate= null;
			if(null != loanDate && !"".equals(loanDate)){
				repaymentDate=new SimpleDateFormat("yyyy-MM-dd").parse(loanDate); 
			}else{
				repaymentDate = new Date();
				loanDate=DateUtils.formatDate(new Date());
			}
			Contract contract  = this.contractService.queryByKey(id);
			map.put("contract", contract);
			Map<String, Object> dbMap=new HashMap<String, Object>();
			dbMap.put("contractNo", contract.getContractNo());
			dbMap.put("repaymentDate", repaymentDate);
			List<LoanRepayPlan> repayPlanList= loanRepayPlanService.queryLateDaysCallList(dbMap);
			List<LoanRepayPlan> loanRepayPlanList1 = new ArrayList<LoanRepayPlan>();//违约还款集合
			List<LoanRepayPlan> showRepayPlanList=new  ArrayList<LoanRepayPlan>();
			
			int yhkqs = 0; //已还款期数
			int ljwycs = 0; //累计违约次数
			double dbAllamount=0;//罚息总和
			double znAllAmount=0;//滞纳金
			double bqyhkhjAll=0;//还款总和
			if(null != repayPlanList && repayPlanList.size() > 0){
				Product product = productService.queryByName(contract.getLoanProduct());
				for (LoanRepayPlan loanRepayPlan : repayPlanList) {
					if(!"2".equals(loanRepayPlan.getState())){
						int sjDay = DateUtils.compareDate(DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN), loanRepayPlan.getRepaymentDate());
						if(sjDay > 0){
							if(!"2".equals(loanRepayPlan.getState())){
								//TODO 信用贷计算罚息和还款; 风险金补利息和本金需要拆分
								int dayRange = DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN));
								//罚息
								double db=creditSignService.getFxje(contract.getLoanAmount(), product, dayRange);
								//滞纳金
								dbAllamount+=db;
								double znAmount=creditSignService.getZnje(MathUtils.add(loanRepayPlan.getPrincipalReceivable(),loanRepayPlan.getInterestReceivable()), product);
								//滞纳金 不足100 按照100计算
								znAmount=znAmount>100?znAmount:100.0;
								znAllAmount+=znAmount;
								loanRepayPlan.setPenaltyReceivable(MathUtils.round(db, 2));		//罚息
								loanRepayPlan.setDelayReceivable(MathUtils.round(znAmount, 2));	//滞纳金		
								loanRepayPlan.setYqts(dayRange);
							}
						}
						if("1".equals(loanRepayPlan.getState()) || "3".equals(loanRepayPlan.getState())){
							loanRepayPlanList1.add(loanRepayPlan);
						}
						if(1 == loanRepayPlan.getPeriodNum()){
							map.put("hkqsrq", loanRepayPlan.getRepaymentDateStr());
						}
						if("2".equals(loanRepayPlan.getState())){//状态为已完成
							yhkqs++;
						}	
						if(loanRepayPlan.getPenaltyReceivable() > 0){
							ljwycs++;
						}
						if(sjDay != 1){
							if(!"2".equals(loanRepayPlan.getState()) && loanRepayPlan.getPenaltyReceivable() > 0){
								loanRepayPlan.setYqts(DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), new Date()));
							}						
						}
						double dyyshj = MathUtils.add(loanRepayPlan.getDelayReceivable(), MathUtils.add(loanRepayPlan.getPenaltyReceivable(), MathUtils.add(loanRepayPlan.getInterestReceivable(), MathUtils.add(loanRepayPlan.getSreviceFeeReceivable(), loanRepayPlan.getPrincipalReceivable()))));
						loanRepayPlan.setDyyshj(MathUtils.roundUp(dyyshj, 2));
						double dyshhj = MathUtils.add(loanRepayPlan.getDelayReceived(), MathUtils.add(loanRepayPlan.getPenaltyReceived(), MathUtils.add(loanRepayPlan.getInterestReceived(), MathUtils.add(loanRepayPlan.getSreviceFeeReceived(), loanRepayPlan.getPrincipalReceived()))));
						loanRepayPlan.setDyshhj(MathUtils.roundUp(dyshhj, 2));
						if("1".equals(loanRepayPlan.getState())){
							loanRepayPlan.setHkzt("未还");
						}
						if("2".equals(loanRepayPlan.getState())){
							loanRepayPlan.setHkzt("已还清");
						}
						if("3".equals(loanRepayPlan.getState())){
							loanRepayPlan.setHkzt("未还清");
						}
						if("1".equals(loanRepayPlan.getState()) || "3".equals(loanRepayPlan.getState())){
							double bqyhkhj=MathUtils.sub(MathUtils.sub(MathUtils.sub(dyyshj, dyshhj), loanRepayPlan.getPenaltyReduce()), loanRepayPlan.getDelayReduce());
							bqyhkhjAll+=bqyhkhj;
							loanRepayPlan.setBqyhkhj(bqyhkhj);
						}else{
							loanRepayPlan.setBqyhkhj(0.00);
						}
						showRepayPlanList.add(loanRepayPlan);
					}
					
				}
				double yhkje = MathUtils.add(repayPlanList.get(0).getPrincipalReceivable(), repayPlanList.get(0).getInterestReceivable());
				map.put("yhkje", yhkje);//月还款金额
				
				for (LoanRepayPlan loanRepayPlan : repayPlanList) {
					if(loanRepayPlan.getPenaltyReceivable() > 0){
						map.put("wyqsrq", DateUtils.toDateString(DateUtils.addDay(loanRepayPlan.getRepaymentDate(), 1)));
						break;
					}
				}
			}
			
			
			
			double ycxhkje = paymentManageService.getYcxhkje(loanRepayPlanList1, DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN));
			
			double maxjmje = paymentManageService.getMaxjmje(loanRepayPlanList1);
			if("HD".equals(contract.getChannelType())){
				double fhfwf = paymentManageService.getFhfwf(repayPlanList, contract, DateUtils.getDate(loanDate, DateUtils.DEFAULT_DATE_PATTERN));
				ycxhkje = MathUtils.sub(ycxhkje, fhfwf);				
			}
			if(ycxhkje > 0)
				map.put("ycxhkje", ycxhkje);//一次性还款金额
			map.put("maxjmje", maxjmje);//最高减免金额
			map.put("yhkqs", yhkqs);
			map.put("ljwycs", ljwycs);
			map.put("repayPlanList", showRepayPlanList);
			map.put("yjhkrq", loanDate);
			request.setAttribute("dbAllamount", dbAllamount);//罚息总和
			map.put("znAllAmount", znAllAmount);//滞纳金
			map.put("bqyhkhjAll", bqyhkhjAll);//还款总和
		}
		log.info(thisMethodName+":end");
		return "collection/phone/LateDaysCalList";
	}
	/**
	 * 跳转到催收信息基本表分配的查询结果页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collectionBaseList(HttpServletRequest request,HttpServletResponse response ,ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		response.setContentType("application/json;charset=UTF-8");
//		PrintWriter writer = response.getWriter();
		try {
			PageModel pm = new PageModel();
			Object bean = RequestUtils.getRequestBean(CollectionBase.class, request);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			if(!"86".equals(org.getOrgId())){
				queryMap.put("orgName", org.getOrgId());
			}
			ArrayList states=new ArrayList();
			states.add("1");//待催收
			states.add("5");//协催
			states.add("6");//协催
			states.add("11");//欺诈司法认定生效
			states.add("15");//欺诈司法认定生效
			queryMap.put("states", states);
			queryMap.put("collectionWayCur", 1);
			int rowsCount = this.collectionBaseService.queryPartCount(queryMap);
			pm.init(request, rowsCount, null, bean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			
			List<CollectionBaseInfo> collectionBaseList = this.collectionBaseService.queryPartList(queryMap);
			pm.setData(collectionBaseList);
			map.put("pm", pm);
//			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			// TODO: handle exception
			log.error(thisMethodName+":error", e);
//			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
//			writer.flush();
//			writer.close();
			throw e;
		}
//		writer.flush();
//		writer.close();
		log.info(thisMethodName+":end");
		return "collection/phone/collectionPhonePartList";
	}
	/**
	 * 显示所有催收信息基本表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/actionList.do")
	public String collectionBaseListAll(HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		response.setContentType("application/json;charset=UTF-8");
//		PrintWriter writer = response.getWriter();
		try {
			PageModel pm = new PageModel();
			Object bean = RequestUtils.getRequestBean(CollectionBase.class, request);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			Map<String, Object> userMap = new HashMap<String, Object>();
			String loginId=user.getLoginId();
			userMap.put("roleName", "电话催收主管");
			userMap.put("loginId", loginId);
//			List userList= this.userService.queryUserByGroup(userMap);
			
			queryMap.put("collectionWayCur", 1);
			if(0 == user.getIsAdmin()){
//				queryMap.put("orgName", org.getOrgId());
				queryMap.put("collectionUid", user.getLoginId()); 
			}
			
			queryMap.put("lessSysTime", "true");
			
			List<CollectionBaseInfo> collectionBaseSys1 = this.collectionBaseService.queryActionList(queryMap);
			queryMap.put("moreSysTimeUndo", "true");
			queryMap.put("lessSysTime", "");
			List<CollectionBaseInfo> collectionBaseState1 = this.collectionBaseService.queryActionList(queryMap);
			queryMap.put("moreSysTimeDone", "true");
			queryMap.put("moreSysTimeUndo", "");
			queryMap.put("lessSysTime", "");
			List<CollectionBaseInfo> collectionBaseState2 = this.collectionBaseService.queryActionList(queryMap);
			queryMap.put("moreSysTimeDone", "");
			queryMap.put("moreSysTimeUndo", "");
			queryMap.put("lessSysTime", "");
			queryMap.put("finshReturn", "true");
			ArrayList states=new ArrayList();
			states.add("1");//待催收除外
			states.add("2");//催收处理中
			states.add("5");//协催
			states.add("6");//协催
			states.add("11");//欺诈司法认定生效
			states.add("15");//欺诈司法认定生效
			queryMap.put("states", states);
			List<CollectionBaseInfo> collectionBaseFinish = this.collectionBaseService.queryActionList(queryMap);
			
			List<CollectionBaseInfo> collectionBaseList =new ArrayList();
			collectionBaseList.addAll(collectionBaseSys1);
			collectionBaseList.addAll(collectionBaseState1);
			collectionBaseList.addAll(collectionBaseState2);
			collectionBaseList.addAll(collectionBaseFinish);
			
			int rowsCount = this.collectionBaseService.queryActionCount(queryMap);
			pm.init(request, rowsCount, null, bean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			int rows,rowe=0;
				rows=pm.getRowS();
			if(collectionBaseList.size()<=pm.getPageSize()*pm.getCurPage()){
				rowe=collectionBaseList.size();
			}else{
				rowe=pm.getPageSize()*pm.getCurPage();
			}
			 
			
			collectionBaseList=collectionBaseList.subList(rows, rowe);
			pm.setData(collectionBaseList);
			map.put("pm", pm);
//			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			// TODO: handle exception
			log.error(thisMethodName+":error", e);
//			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
//			writer.flush();
//			writer.close();
			throw e;
		}
//		writer.flush();
//		writer.close();
		log.info(thisMethodName+":end");
		return "collection/phone/collectionPhoneList";
	}

	/**
	 * 跳转到更新催收信息基本表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String collectionBaseUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollectionBase bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.collectionBaseService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/phone/collectionBaseUpdate";
	}

	/**
	 * 保存催收信息基本表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	@Transactional
	public void collectionBaseSave(String submitType,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			this.collectionBaseService.collectionBaseSave(user, org, submitType, request);
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
	 * 删除催收信息基本表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collectionBaseDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			String appId=request.getParameter("appId");
			String contactname=request.getParameter("contactname");
			Map<String, Object> creditContactMap = new HashMap<String, Object>();
			creditContactMap.put("appId", appId);
			creditContactMap.put("type", CollectionConstant.CONTACT_TYPE_COLLECTION);//电催类型的联系人
			creditContactMap.put("name", contactname);
			List<CreditContact> creditContactList=this.creditContactService.queryList(creditContactMap);
			if(creditContactList.size()>0&&contactname!=null&&!"".equals(contactname)){
				CreditContact creditContact=creditContactList.get(0);
				this.creditContactService.delete(creditContact.getId());//相关联系人
			}
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
	 * 跳转到查看催收信息基本表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String collectionBaseRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		try {
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			map=this.collectionBaseService.collectionBaseRead(user,id,map);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		log.info(thisMethodName+":end");
		return "collection/phone/collectionBaseRead";
	}
	/**
	 * 电催分配
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/updateData.do")
	public void collectionBaseUpdateDate(String ids,String org_id,String collection_uid_cur, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Org sessionOrg = (Org) request.getSession().getAttribute(
					SysConstants.LOGIN_ORG);
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			this.collectionBaseService.collectionBaseUpdateDate(sessionOrg, user, ids, org_id, collection_uid_cur);

			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
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
	}
	/**
	 * 欺诈申请弹出框
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/cheatAply.do")
	public String cheatApply(String contractNo,String applyType, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", contractNo);
			CollectionBase bean = collectionBaseService.queryList(queryMap).get(0);
			map.put("bean", bean);
			request.setAttribute("applyType", applyType);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
		log.info(thisMethodName+":end");
		return "collection/phone/CheatApply";
	}
	/**
	 * 欺诈申请提交，陈情表插入数据同时更改coleectionBase状态
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/cheatAplyUpdate.do")
	public void cheatAplyUpdate(CollectionApplication collectionApplication, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			
			String loginId=String.valueOf(user.getLoginId());
			
			this.collectionBaseService.cheatAplyUpdate(collectionApplication,user,loginId);
			
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));	
		} catch (Exception e) {
			// TODO: handle exception
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		
		log.info(thisMethodName+":end");
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
	public String callFrontCollection(String contractNo,String creditContactId,String name,String lateDays,String relation ,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//检查String id是id还是appId
//		PrintWriter writer = response.getWriter();
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo",contractNo);
			//queryMap.put("type","3");
			queryMap.put("name",name);
//			queryMap.put("id",creditContactId);
			if(!"8".equals(relation)){
				List<CreditContactHistory> creditContactList = this.creditContactService.getRelationList(queryMap);
				CreditContactHistory bean = null;
				if(creditContactList.size()>0){
					bean = creditContactList.get(0);
				}
				map.put("creditContact",bean );
			}else{
				
				Map<String, Object> queryFirstMap = new HashMap<String, Object>();
				queryFirstMap.put("contractNo", contractNo);
				List<Contract> contactFirstList=this.contractService.queryList(queryFirstMap);//联系人本人
				CreditContactHistory creditContactFirst=new CreditContactHistory();
	//			List<CreditContactHistory> creditContactList=new ArrayList<CreditContactHistory>();
				if(contactFirstList.size()>0){
					Contract contactFirst=(Contract) contactFirstList.get(0); 
					creditContactFirst.setName(contactFirst.getLoanName());
					creditContactFirst.setRelation("8");
					creditContactFirst.setMobile(contactFirst.getBankMobile());
					
					Map<String, Object> collectionRecordMap = new HashMap<String, Object>();
					collectionRecordMap.put("contractNo", contractNo);
					collectionRecordMap.put("answerName", contactFirst.getLoanName());
					List<CollectionRecord> collectionRecordList = this.collectionRecordService.queryList(collectionRecordMap);//联系人本人历史记录最新数据
					CollectionRecord collectionRecord=new CollectionRecord();
					if(collectionRecordList.size()>0){
						collectionRecord=collectionRecordList.get(collectionRecordList.size()-1);
						creditContactFirst.setPhoneSummary(collectionRecord.getPhoneSummary());
						creditContactFirst.setRemark(collectionRecord.getRemark());
					}
				}	
				map.put("creditContact",creditContactFirst );
			}
			map.put("contractNo",contractNo );
			map.put("lateDays",lateDays );
			
//			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));	
		} catch (Exception e) {
			// TODO: handle exception
//			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
//			writer.flush();
//			writer.close();
			throw e;
		}
//		writer.flush();
//		writer.close();
		log.info(thisMethodName+":end");
		return "collection/phone/PhoneDetailCall";
	}
}
