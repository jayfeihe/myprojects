package com.tera.house.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.customer.dao.CustomerDao;
import com.tera.customer.model.Customer;
import com.tera.house.constant.BusinessConstants;
import com.tera.house.constant.Constants;
import com.tera.house.dao.HouseAppDao;
import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseContact;
import com.tera.house.model.HouseDecision;
import com.tera.house.model.HouseExt;
import com.tera.house.model.HouseInfo;
import com.tera.house.model.HouseRepeatDetail;
import com.tera.house.model.form.AppFBean;
import com.tera.house.model.form.HouseJsonMsg;
import com.tera.house.model.form.RepeatCheckQBean;
import com.tera.img.service.ImgService;
import com.tera.rule.model.credit.scorecard.CreditScoreCard;
import com.tera.rule.service.ScoreCardService;
import com.tera.sys.model.Org;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;
import com.tera.util.DateUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 信用贷款申请表服务类
 * <b>功能：</b>HouseAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseAppService")
public class HouseAppService {

	private final static Logger log = Logger.getLogger(HouseAppService.class);

	@Autowired(required=false)
    private HouseAppDao dao;
	@Autowired(required=false)
    private UserService userService;
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	@Autowired(required=false) //自动注入
	HouseExtService houseExtService;
	@Autowired(required=false) //自动注入
	HouseContactService houseContactService;
	@Autowired(required=false)
	private ProcessService processService;
	
	@Autowired(required=false)
	private HouseRepeatDetailService houseRepeatDetailService;

	//规则调用
	@Autowired(required=false)
	private ScoreCardService scoreCardService;
	
	@Autowired(required=false)
	ImgService imgService;
	
	@Autowired(required=false) //自动注入
	private CustomerDao<Customer> customerDao;
	@Autowired(required=false) //自动注入
	HouseDecisionService houseDecisionService;
	
	@Autowired(required=false) //自动注入
	private HouseAppHandlerService houseAppHandlerService;
	@Autowired(required=false)
	private HouseInfoService houseInfoService;
	
	
	@Transactional
	public void add(HouseApp... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseApp obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseApp obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseApp obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(int... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(int id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<HouseApp> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public HouseApp queryByKey(Object id) throws Exception {
		return (HouseApp)dao.queryByKey(id);
	}
	
	
	
	/**
	 * 根据 流程节点  用户  机构 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 	 		appId			申请编号
	 * 			appCode			申请编码
	 * 			bpmStates  		包含流程节点  字符串数组
	 * 			nonBpmStates 	不包含流程节点  字符串数组
	 * 			processer 		流程待处理人
	 * 			nonProcesser 	非流程待处理人
	 * 			operator		操作员
	 * 			customerManager	客户经理
	 * 			orgId			机构编码
	 * 			states			包含状态 字符串 数组 
	 * 			nonStates		不包含状态 字符串 数组 
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			mobile 			手机号
	 * 			amountMin 		金额下限
	 * 			amountMax 		金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限
	 * 			inputTimeMin 	申请时间下限
	 * 			inputTimeMax 	申请时间上限
	 * 			staffNo			营销人员 工号
	 *			bpmLogStates	日志的状态
	 *			bpmLogOperator	日志实际处理人
	 * @return
	 */
	public List<HouseApp> queryBpmLoanAppList(Map<String, Object> map)throws Exception{
		
		String loginId=(String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userService.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
			//判断是否是组长，组长可以查看 其他人的案子 以及更改待处理人
			Map<String, Object> beanMap=new HashMap<String, Object>();
			beanMap.put("loginId",loginId);
			beanMap.put("orgId",map.get("orgId"));
			List<Role> loginRoles=roleService.getRoleByOrgLoginId(beanMap);
			for (Role role : loginRoles) {
				if("1".equals(role.getFlag())){
					map.remove("userLoginId");
					break;
				}
			}
		}
		return dao.queryBpmLoanAppList(map);
	}
	public int queryBpmLoanAppCount(Map<String, Object> map)throws Exception{
		String loginId=(String) map.get("userLoginId");
		if(loginId!=null){
			User us=userService.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
			//判断是否是组长，组长可以查看 其他人的案子 以及更改待处理人
			Map<String, Object> beanMap=new HashMap<String, Object>();
			beanMap.put("loginId",loginId);
			beanMap.put("orgId",map.get("orgId"));
			List<Role> loginRoles=roleService.getRoleByOrgLoginId(beanMap);
			for (Role role : loginRoles) {
				if("1".equals(role.getFlag())){
					map.remove("userLoginId");
					break;
				}
			}
		}
		return dao.queryBpmLoanAppCount(map);
	}
	
	
	
	
	
	/**
	 * 根据 流程节点  用户  机构 合同 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 * 			contractNo		合同编码
	 *			decisionAmount	最终决策金额
	 *			decisionProduct 最终决策产品
	 *			decisionPeriod  最终决策期数
	 *			decisionOperator最终决策人
	 * 			bpmStates  		包含流程节点  字符串数组
	 * 			nonBpmStates 	不包含流程节点  字符串数组
	 * 			processer 		流程待处理人
	 * 			nonProcesser 	非流程待处理人
	 * 			operator		操作员
	 * 			customerManager	客户经理
	 * 			orgId			机构编码
	 * 			states			包含状态 字符串 数组 
	 * 			nonStates		不包含状态 字符串 数组 
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			mobile 			手机号
	 * 			amountMin 		金额下限
	 * 			amountMax 		金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限
	 * 			inputTimeMin 	申请时间下限
	 * 			inputTimeMax 	申请时间上限
	 * 			staffNo			营销人员 工号
	 * @return
	 */
	public List<HouseApp> queryBpmAppAndContractList(Map<String, Object> map)throws Exception{
		/*String loginId=(String) map.get("processer");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userService.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("processer");
			}
			//判断是否是组长，组长可以查看 其他人的案子 以及更改待处理人
			Map<String, Object> beanMap=new HashMap<String, Object>();
			beanMap.put("loginId",loginId);
			beanMap.put("orgId",map.get("orgId"));
			List<Role> loginRoles=roleService.getRoleByOrgLoginId(beanMap);
			for (Role role : loginRoles) {
				if("1".equals(role.getFlag())){
					map.remove("processer");
					break;
				}
			}
		}*/
		return dao.queryBpmAppAndContractList(map);
	}
	public int queryBpmAppAndContractCount(Map<String, Object> map)throws Exception{
		/*String loginId=(String) map.get("processer");
		if(loginId!=null){
			User us=userService.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("processer");
			}
			//判断是否是组长，组长可以查看 其他人的案子 以及更改待处理人
			Map<String, Object> beanMap=new HashMap<String, Object>();
			beanMap.put("loginId",loginId);
			beanMap.put("orgId",map.get("orgId"));
			List<Role> loginRoles=roleService.getRoleByOrgLoginId(beanMap);
			for (Role role : loginRoles) {
				if("1".equals(role.getFlag())){
					map.remove("processer");
					break;
				}
			}
		}*/
		return dao.queryBpmAppAndContractCount(map);
	}
	
	/**
	 * 信息查重判断
	 * 
	 * @param map
	 * @return
	 */
	public List<RepeatCheckQBean> repeatCheckQuery(Map<String, Object> map) {
		return dao.repeatCheckQuery(map);
	}
	
	/**
	 * 夫妻分别借款
	 * @param map
	 * @return
	 */
	public List<RepeatCheckQBean> coupleSeparateLoan(Map<String, Object> map) {
		return dao.coupleSeparateLoan(map);
	}
	
	
	
	@Transactional
	public String getAppIdCode(Org org){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return "H"+org.getOrgId()+format.format(new Date())+dao.getAppIdCode();
	}
	
	/**
	 * 添加更新 或者提交申请
	 * @param appFBean
	 * @param org
	 * @param loginId
	 * @throws Exception
	 */
	@Transactional
	public HouseJsonMsg appUpdate(AppFBean appFBean,Org org,String loginId)throws Exception{
		Timestamp time=new Timestamp(System.currentTimeMillis());
		//添加APP
		HouseApp houseApp=appFBean.getHouseApp();
		String customerId = saveCustomer(houseApp, loginId, org);
		if(null != customerId)
			houseApp.setCustomerId(customerId);
		
		if(houseApp.getId()==0){
			houseApp.setAppId(getAppIdCode(org));
			houseApp.setOrgId(org.getOrgId());
			houseApp.setCustomerManager(loginId);
			houseApp.setOperator(loginId);
			houseApp.setIdType("01");//证件类型统一为 身份证
			houseApp.setState("1");
			houseApp.setCreateTime(time);
			houseApp.setUpdateTime(time);
			this.add(houseApp);
			BpmTask bpmTask=processService.startProcessInstanceByName(Constants.PROCESS_NAME_HOUSE, houseApp.getAppId());
			bpmTask.setProcesser(loginId);
			processService.goNext(bpmTask,loginId);
		}else{
			houseApp.setOperator(loginId);
			houseApp.setUpdateTime(time);
		}
		
		/*
		 * 精英贷测试方案
		 * 规则： 1)	如判断客户性别为女性但是上传影像资料中不包括H类，则显示“女性”
		 * 		 2)	如判断客户性别为女性并且上传影像中包括H类学历证明资料则显示“女性、提供学历证明”
		 *   	 3)	判断客户性别为男性但是上传影像中包括H类学历证明资料则显示“提供学历证明“
		 */
		String gradeRemind = "";
		if (houseApp.getProduct().contains("精英贷")) {
			gradeRemind = houseAppHandlerService.getGradeRemind(houseApp);
		}
		houseApp.setGradeRemind(gradeRemind);
		this.updateOnlyChanged(houseApp);
		
		//扩展信息
		 List<HouseExt> houseExts=appFBean.getHouseExts();
		 for (HouseExt houseExt : houseExts) {
			if(houseExt.getId()==0){// 添加
				houseExt.setAppId(houseApp.getAppId());
				houseExt.setOrgId(org.getOrgId());
				houseExt.setOperator(loginId);
				houseExt.setState("1");
				houseExt.setCreateTime(time);
				houseExt.setUpdateTime(time);
				this.houseExtService.add(houseExt);
			}else{
				houseExt.setOperator(loginId);
				houseExt.setUpdateTime(time);
				this.houseExtService.updateOnlyChanged(houseExt);
			}
		}
		//常用联系人
		List<HouseContact> commonContacts=appFBean.getCommonContacts();
		for (HouseContact commonContact : commonContacts) {
			if(commonContact.getId()==0){// 添加
				if("1".equals(commonContact.getState())){	//只有在状态 是 1 的时候 才添加
					commonContact.setAppId(houseApp.getAppId());
					commonContact.setOrgId(org.getOrgId());
					commonContact.setOperator(loginId);
					commonContact.setCreateTime(time);
					commonContact.setUpdateTime(time);
					this.houseContactService.add(commonContact);
				}
			}else{
				commonContact.setOperator(loginId);
				commonContact.setUpdateTime(time);
				this.houseContactService.updateOnlyChanged(commonContact);
			}
		}
		// 经营往来联系人
		if(appFBean.getHouseApp().getProduct()!=null&&
				appFBean.getHouseApp().getProduct().contains("业主贷")){
			List<HouseContact> dealingsContacts=appFBean.getDealingsContacts();
			for (HouseContact commonContact : dealingsContacts) {
				if(commonContact.getId()==0){// 添加
					if("1".equals(commonContact.getState())){	//只有在状态 是 1 的时候 才添加
						commonContact.setAppId(houseApp.getAppId());
						commonContact.setOrgId(org.getOrgId());
						commonContact.setOperator(loginId);
						commonContact.setCreateTime(time);
						commonContact.setUpdateTime(time);
						this.houseContactService.add(commonContact);
					}
				}else{
					commonContact.setOperator(loginId);
					commonContact.setUpdateTime(time);
					this.houseContactService.updateOnlyChanged(commonContact);
				}
			}
		}
		
		// 房屋信息
		HouseInfo houseInfo = appFBean.getHouseInfo();
		if (houseInfo != null) {
			if (houseInfo.getId() == 0) {
				houseInfo.setAppId(houseApp.getAppId());
				houseInfo.setOperator(loginId);
				houseInfo.setState("1");
				houseInfo.setOrgId(org.getOrgId());
				houseInfo.setCreateTime(time);
				houseInfo.setUpdateTime(time);
				houseInfoService.add(houseInfo);
			} else {
				houseInfo.setOperator(loginId);
				houseInfo.setUpdateTime(time);
				houseInfoService.updateOnlyChanged(houseInfo);
			} 
		}
		String buttonType=appFBean.getButtonType();
		HouseJsonMsg msg = null;
		//提交流程
		if("submit".equals(buttonType)){//yz,返回值  为  null 的时候 说明没附件   为 "" 的时候说明 满足条件
			Map<String, Object> houseDecisionQMap=new HashMap<String, Object>();
			houseDecisionQMap.put("appId", houseApp.getAppId());
			houseDecisionQMap.put("type", "1");
			List<HouseDecision> houseDecisionList = houseDecisionService.queryList(houseDecisionQMap);
			ArrayList<String> imgArrayList = new ArrayList<String>();
			
			//2015-03-30，分产品类别验证
			String productType = houseApp.getProduct();
			imgArrayList.add("A02");
			imgArrayList.add("B01");
			imgArrayList.add("C");
			imgArrayList.add("E01");
			if(productType.startsWith("业主贷")){
				imgArrayList.add("F");
				imgArrayList.add("G12");
				//加G01或G02
				imgArrayList.add("G01_G02");
				//加G20或G21
				imgArrayList.add("G20_G21");
			}else if(productType.startsWith("精英贷")){
				imgArrayList.add("D");
			}else if(productType.startsWith("工薪贷")){
				imgArrayList.add("D");
				imgArrayList.add("F");
			}
			//审核退回需要补充的资料类型
			if(null != houseDecisionList && houseDecisionList.size() > 0){
				String imageClass = houseDecisionList.get(0).getImageSummarys();
				if(null != imageClass && imageClass.length() > 0){
					String[] imageClasss = imageClass.split(",");
					for (String s : imageClasss) {
						imgArrayList.add(s);
					}
				}
			}
			String[] imgs = new String[imgArrayList.size()];
			for (int i = 0; i < imgArrayList.size(); i++) {
				imgs[i] = imgArrayList.get(i);
			}
			String yz=imgService.imgVerify(houseApp.getAppId(), imgs);
			if("".equals(yz)){
				String appState=null;
				if(houseApp.getState()==null||houseApp.getState().equals("")){
					//判断数据库内 申请状态
					HouseApp houseApp1=this.queryByKey(houseApp.getId());
					appState=houseApp1.getState();
				}else{
					appState=houseApp.getState();
				}
				if("1".equals(appState)){	//申请状态 为录入申请 提交时 设置进件时间
					houseApp.setInputTime(time);
				}
				
	////////////>>>> 规则调用   <<<<//////////////
				//调用规则评分
				CreditScoreCard s2 = new CreditScoreCard();
				s2.setIsMarried(Integer.valueOf(houseApp.getMarriage()));//婚姻状态
				
				//算出工作年限
				try {
					Date rzsj=houseApp.getComAddDate();
					Date jjsj=new Date();
					int workAge = DateUtils.getYearRange(rzsj,jjsj);//进件时间减去comAddDate入职时间
					s2.setWorkYear(workAge);
				}catch (Exception e) {
					s2.setSex(1);
				}
				//计算并设置性别
				String appIdNo = houseApp.getIdNo();
				int sex;
				if (appIdNo.length() == 15){
					try {
						sex = Integer.parseInt(appIdNo.substring(14,15)) % 2 == 1?1:0;
						s2.setSex(sex);
					} catch (Exception e) {
						s2.setSex(1);
					}
				}else if(appIdNo.length() == 18){
					try {
						sex = Integer.parseInt(appIdNo.substring(16,17)) % 2 == 1?1:0;
						s2.setSex(sex);
					} catch (Exception e) {
						s2.setSex(1);
					}
				}else{
					s2.setSex(1);
				}
				s2.setHasChild(houseApp.getWithoutChildren());//有无子女
			 	s2.setCorporate(Integer.valueOf(houseApp.getComType()));//单位类型
			 	s2.setHouseStatus(Integer.valueOf(houseApp.getAddNature()));// 居住性质
//			 	s2.setAppId(houseApp.getAppId());
//			 	s2.setOperateUser(houseApp.getOperator());
			 	s2.setAppno(houseApp.getAppId());
			 	s2.setOperator(houseApp.getOperator());
			 	s2.setOrgId(houseApp.getOrgId());
			 	// 调用评分服务
			 	scoreCardService.creditScoreCard(s2);

				houseApp.setRuleGrade(s2.getLevel());//等级
				houseApp.setRuleScore(s2.getScore()+"");//评分
//				List<String> ruleRepList=s2.getResults();
//				if(ruleRepList!=null&&!ruleRepList.isEmpty()){
//					//返回结果，入库
//					houseApp.setRuleGrade(ruleRepList.get(0));//评分
//					houseApp.setRuleScore(ruleRepList.get(1));//等级
//				}
	////////////>>>> 规则调用END   <<<<//////////////
				
				//提交，根据log表判断是否退回件，是退回件，取退回时的操作人，不设置进件时间；不是退回的话，直接提交。
				List<BpmLog> bpmLogList = null;
				String nextState = "";
				if("2".equals(appState)) {// 审核退回
					bpmLogList = processService.getProcessHistoryLog(houseApp.getAppId(), Constants.PROCESS_STATE_VERIFY);
					nextState = Constants.PROCESS_STATE_VERIFY;
					houseApp.setState("3"); // 审核状态
				}
				if("5".equals(appState)) {// 核价退回
					bpmLogList = processService.getProcessHistoryLog(houseApp.getAppId(), Constants.PROCESS_STATE_PRICE);
					nextState = Constants.PROCESS_STATE_PRICE;
					houseApp.setState("8"); // 核价状态
				}
				if (bpmLogList != null && bpmLogList.size() != 0) {// 是退回件
					//判断	bpmLogList.get(0).getOperator()	是否可用，若不可用，提交至分单
					String nextUser = bpmLogList.get(0).getOperator();
			        String state = userService.getUser(nextUser).getState();
					if("0".equals(state)){//离职，不接受新任务。（挂起不变）
						nextUser = "";
					}
					this.appBpmNext(houseApp.getAppId(),loginId, nextUser, nextState);
					msg = new HouseJsonMsg(true, "提交成功。", houseApp.getAppId(), houseApp.getId());
					
				} else {
					// 不是退回件
					if (!houseApp.getProduct().contains("精英贷")) {
						// 夫妻分别借款判断
						boolean isExist = isExistSpouseLoaning(houseApp);
						if (isExist) {
							String operator = loginId;
							String logContent1 = "系统拒贷";
							String logContent2 = "客户配偶放款未结清";
							String logContent3 = "拒贷码";
							String logContent4 = "拒贷码1：D04——拒贷码2：D0409";
							String logContent5 = "";
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("rejectType", "spouseJD");
							params.put("operator", operator);
							params.put("logContent1", logContent1);
							params.put("logContent2", logContent2);
							params.put("logContent3", logContent3);
							params.put("logContent4", logContent4);
							params.put("logContent5", logContent5);

							this.sysRejectBpmNext(houseApp, params);

							msg = new HouseJsonMsg(true, "系统拒贷：客户配偶放款未结清。",
									houseApp.getAppId(), houseApp.getId());
							return msg;
						}
					}
					
					this.appBpmNext(houseApp.getAppId(), loginId, "",Constants.PROCESS_STATE_PRICE);
					msg = new HouseJsonMsg(true, "提交成功。", houseApp.getAppId(), houseApp.getId());
					houseApp.setState("8"); // 核价状态
					// 积木查重
					/*String name = houseApp.getName();
					String idHoused = houseApp.getIdNo();
					String appId = houseApp.getAppId();
					JMChannalDuplicateResponseBean bean = jmboxService
							.JMChannalDuplicate(name,idHoused,appId);
					if (bean == null) {
						this.appBpmNext(houseApp.getAppId(), loginId, "",
								Constants.PROCESS_STATE_VERIFY);
						msg = new HouseJsonMsg(true, "提交审核成功（积木查重接口调用失败）。", houseApp
								.getAppId(), houseApp.getId());
					} else {
						// 客户在积木上已存在，进行拒贷
						if (bean.isHouseloaned() || bean.isHouseloaning()) {
							String operator = loginId;
							String logContent1 = "系统拒贷";
							String logContent2 = "客户在积木其他渠道已融资";
							String logContent3 = "拒贷码";
							String logContent4 = "拒贷码1：D04——拒贷码2：D0413";
							String logContent5 = "";
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("rejectType", "JMJD");
							params.put("operator", operator);
							params.put("logContent1", logContent1);
							params.put("logContent2", logContent2);
							params.put("logContent3", logContent3);
							params.put("logContent4", logContent4);
							params.put("logContent5", logContent5);
							
							this.sysRejectBpmNext(houseApp,params);
							
							msg = new HouseJsonMsg(true, "系统拒贷：客户在积木其他渠道已融资。",
									houseApp.getAppId(), houseApp.getId());
							return msg;
						} else {
							this.appBpmNext(houseApp.getAppId(), loginId, "",
									Constants.PROCESS_STATE_VERIFY);
							msg = new HouseJsonMsg(true, "提交成功。", houseApp
									.getAppId(), houseApp.getId());
						}
					}*/
				}
				this.updateOnlyChanged(houseApp);
				
				// 数据查重处理
				houseRepeatDetailService.deleteByAppId(houseApp.getAppId());
				repeatCheckProcess(appFBean,org,loginId,time);
				
			}else if(yz==null){ //未找到图片的情况
				msg=new HouseJsonMsg(false, "提交失败，请先上传必传资料。",houseApp.getAppId(),houseApp.getId());
			}else{//必传图片缺少的情况
				msg=new HouseJsonMsg(false,"提交失败，请先上传缺少资料：（"+yz+"）。",houseApp.getAppId(),houseApp.getId());
			}
		}else if("waive".equals(buttonType)){
			houseApp.setState("0");
			this.updateOnlyChanged(houseApp);
			this.appBpmNext(appFBean.getHouseApp().getAppId(),loginId, "", Constants.PROCESS_STATE_GIVEUP);
			this.appBpmNext(appFBean.getHouseApp().getAppId(), loginId, BpmConstants.SYSTEM_SYS, Constants.PROCESS_END_APP);
			msg=new HouseJsonMsg(true, "放弃成功",houseApp.getAppId(),houseApp.getId());
		}else{
			msg=new HouseJsonMsg(true, "保存成功",houseApp.getAppId(),houseApp.getId());
		}
		return msg;
	}
	
	/**
	 * 判断是否有配偶在借款中
	 * @param spouseName
	 * @param spouseIdNo
	 */
	private boolean isExistSpouseLoaning(HouseApp houseApp) {
		boolean isExist = false;
		String name = houseApp.getName();
		String idNo = houseApp.getIdNo();
		String spouseName = houseApp.getSpouseName(); 
		String spouseIdNo = houseApp.getSpouseIdNo();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name",name);
		queryMap.put("idNo",idNo);
		queryMap.put("spouseName",spouseName);
		queryMap.put("spouseIdNo",spouseIdNo);
		List<RepeatCheckQBean> spouseLoanList = this.coupleSeparateLoan(queryMap);
		if (spouseLoanList != null && spouseLoanList.size() > 0 ) {
			for (RepeatCheckQBean tmpBean : spouseLoanList) {
				// tmpBean：借款人配偶是历史借款人
				if ("0".equals(tmpBean.getFlag())) {
					if (tmpBean.getSpouseIdNo().equals(houseApp.getIdNo()) && tmpBean
							.getSpouseName().equals(houseApp.getName())) {
						isExist = true;
						break;
					}
				}
				
				// tmpBean：借款人自己是历史借款人配偶
				if ("1".equals(tmpBean.getFlag())) {
					if (tmpBean.getIdNo().equals(houseApp.getSpouseIdNo()) && tmpBean
							.getName().equals(houseApp.getSpouseName())) {
						isExist = true;
						break;
					}
				}
			}
		}
		return isExist;
	}

	/**
	 * 信息查重
	 * 
	 * @param appFBean
	 * @param org
	 * @param loginId
	 * @param time
	 * @throws Exception
	 */
	@Transactional
	public void repeatCheckProcess(AppFBean appFBean, Org org, String loginId,
			Timestamp time) throws Exception {
		HouseApp houseApp = appFBean.getHouseApp();
		List<HouseContact> commonContacts = appFBean.getCommonContacts();
		List<HouseContact> dealingContacts = appFBean.getDealingsContacts();
		
		List<HouseContact> contacts = new ArrayList<HouseContact>();
		contacts.addAll(commonContacts);
		contacts.addAll(dealingContacts);
		
		// 参数Map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", org.getOrgId());
		params.put("loginId", loginId);
		params.put("time", time);
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		// 1、单位名称查重
		if (StringUtils.isNotNullAndEmpty(houseApp.getComName())) {
			comNameAndAddressRepeatCheck(queryMap, params, houseApp,
					BusinessConstants.HOUSE_REPEAT_TYPE_COM_NAME);
			queryMap.clear();
		}
		
		// 2、单位地址查重
		if (StringUtils.isNotNullAndEmpty(houseApp.getComAddress())) {
			comNameAndAddressRepeatCheck(queryMap, params, houseApp,
					BusinessConstants.HOUSE_REPEAT_TYPE_COM_ADD);
			queryMap.clear();
		}
		
		// 3、居住地址查重
		if (StringUtils.isNotNullAndEmpty(houseApp.getAddress())) {
			comNameAndAddressRepeatCheck(queryMap, params, houseApp,
					BusinessConstants.HOUSE_REPEAT_TYPE_HOME_ADD);
			queryMap.clear();
		}
		
		/*
		 *  4、联系方式查重（借款人联系方式包括：借款人手机号码、居住电话、单位电话、配偶电话）
		 *  4.1、借款人联系方式同历史库借款人联系方式比较
		 *  4.2、借款人联系方式同历史库配偶联系方式比较
		 *  4.3、借款人联系方式同历史库常用联系人联系方式比较
		 *  4.4、借款人联系方式同面审调查中新添加的数据进行比较
		 */
		// 4.1、 借款人联系方式同历史库借款人联系方式
		loanContactRepeatCheck(queryMap, params, houseApp,
				BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_LOAN);
		
		// 4.2、借款人联系方式同历史库配偶联系方式比较
		loanContactRepeatCheck(queryMap, params, houseApp,
				BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_SPOUSE);
		
		// 4.3、借款人联系方式同历史库联系人联系方式比较
		loanContactRepeatCheck(queryMap, params, houseApp,
				BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_LINKMAN);
		
		// 4.4、借款人联系方式同面审调查表中新添加的数据进行比较
		loanContactRepeatCheck(queryMap, params, houseApp,
				BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_INTERVIEW);
		
		// 4.5 联系人方式同历史中的借款人联系方式查重
		linkContactRepeatCheck(queryMap, params, contacts,
				BusinessConstants.REPEAT_TYPE_CONTACT_LINKMAN_TO_LOAN);
		
		// 4.6 联系人方式同历史中的联系人联系方式查重
		linkContactRepeatCheck(queryMap, params, contacts,
				BusinessConstants.REPEAT_TYPE_CONTACT_LINKMAN_TO_LINKMAN);
		
		// 4.6 联系人方式同面审调查表中新添加联系方式查重
		linkContactRepeatCheck(queryMap, params, contacts,
				BusinessConstants.REPEAT_TYPE_CONTACT_LINKMAN_TO_INTERVIEW);
		
		queryMap.clear();
		
		// 5.1、同借款人身份证查重
		idNoRepeatCheck(queryMap, params, houseApp,
				BusinessConstants.REPEAT_IDNO_TYPE_BORROWER);
		
		// 5.2、同借款人配偶身份证查重
		idNoRepeatCheck(queryMap, params, houseApp,
				BusinessConstants.REPEAT_IDNO_TYPE_SPOUSE);
		
		queryMap.clear();
		params.clear();
	}
	
	/**
	 * 单位名称以及借款人地址（单位地址和居住地址）查重
	 * 
	 * @param queryMap
	 * @param params
	 * @param houseApp
	 * @param type
	 * @throws Exception
	 */
	@Transactional
	private void comNameAndAddressRepeatCheck(Map<String, Object> queryMap,
			Map<String, Object> params, HouseApp houseApp, String type)
			throws Exception {
		queryMap.put("repeatType", BusinessConstants.REPEAT_TYPE_NAME_OR_ADDRESS);
		queryMap.put("appId", houseApp.getAppId());
		params.put("type", type);
		
		if (BusinessConstants.HOUSE_REPEAT_TYPE_COM_NAME.equals(type)) {
			queryMap.put("comName", houseApp.getComName());
			params.put("comment", "单位名称");
			params.put("value", houseApp.getComName());
			saveHouseRepeatDetailInfo(queryMap,params);
		}
		
		if (BusinessConstants.HOUSE_REPEAT_TYPE_COM_ADD.equals(type)) {
			queryMap.put("comProvince", houseApp.getComProvince());
			queryMap.put("comCity", houseApp.getComCity());
			queryMap.put("comCounty", houseApp.getComCounty());
			queryMap.put("comAddress", houseApp.getComAddress());
			params.put("comment", "单位地址");
			params.put("value", houseApp.getComAddress());
			saveHouseRepeatDetailInfo(queryMap,params);
		}
		
		if (BusinessConstants.HOUSE_REPEAT_TYPE_HOME_ADD.equals(type)) {
			queryMap.put("addProvice", houseApp.getAddProvince());
			queryMap.put("addCity", houseApp.getAddCity());
			queryMap.put("addCounty", houseApp.getAddCounty());
			queryMap.put("address", houseApp.getAddress());
			params.put("comment", "居住地址");
			params.put("value", houseApp.getAddress());
			saveHouseRepeatDetailInfo(queryMap,params);
		}
	}

	/**
	 * 借款人联系方式查重
	 * 
	 * @param queryMap
	 * @param params
	 * @param houseApp
	 * @param contactType
	 * @throws Exception
	 */
	@Transactional
	private void loanContactRepeatCheck(Map<String, Object> queryMap,
			Map<String, Object> params, HouseApp houseApp, String repeatType)
			throws Exception {
		
		queryMap.put("repeatType", repeatType);
		queryMap.put("appId", houseApp.getAppId());
		
		if (StringUtils.isNotNullAndEmpty(houseApp.getMobile())) {
			queryMap.put("mobile", houseApp.getMobile());
			params.put("comment", "借款人手机号码");
			params.put("type", BusinessConstants.HOUSE_REPEAT_TYPE_CONTACT);
			params.put("value", houseApp.getMobile());
			saveHouseRepeatDetailInfo(queryMap, params);
			queryMap.remove("mobile");
		}
		
		if (StringUtils.isNotNullAndEmpty(houseApp.getPhone())) {
			queryMap.put("phone", houseApp.getPhone());
			params.put("comment", "借款人住宅电话");
			params.put("type", BusinessConstants.HOUSE_REPEAT_TYPE_CONTACT);
			params.put("value", houseApp.getPhone());
			saveHouseRepeatDetailInfo(queryMap, params);
			queryMap.remove("phone");
		}
		
		if (StringUtils.isNotNullAndEmpty(houseApp.getComPhone())) {
			queryMap.put("comPhone", houseApp.getComPhone());
			params.put("comment", "借款人单位电话");
			params.put("type", BusinessConstants.HOUSE_REPEAT_TYPE_CONTACT);
			params.put("value", houseApp.getComPhone());
			saveHouseRepeatDetailInfo(queryMap, params);
			queryMap.remove("comPhone");
		}
		
		if (StringUtils.isNotNullAndEmpty(houseApp.getSpouseMobile())) {
			queryMap.put("spouseMobile", houseApp.getSpouseMobile());
			params.put("comment", "借款人配偶联系方式");
			params.put("type", BusinessConstants.HOUSE_REPEAT_TYPE_CONTACT);
			params.put("value", houseApp.getSpouseMobile());
			saveHouseRepeatDetailInfo(queryMap, params);
			queryMap.remove("spouseMobile");
		}
		params.remove("commentTail");
	}
	
	/**
	 * 联系人联系方式查重
	 * @param queryMap
	 * @param params
	 * @param contacts
	 * @param contactType
	 * @throws Exception
	 */
	@Transactional
	private void linkContactRepeatCheck(Map<String, Object> queryMap,
			Map<String, Object> params, List<HouseContact> contacts,
			String repeatType) throws Exception {
		
		queryMap.put("repeatType", repeatType);
		
		for (HouseContact contact : contacts) {
				
			queryMap.put("appId", contact.getAppId());
			if (StringUtils.isNotNullAndEmpty(contact.getMobile())) {
				queryMap.put("contactMobile", contact.getMobile());
				params.put("comment", "常用联系人[" + contact.getName()
						+ "]的手机号码");
				params.put("type",
						BusinessConstants.HOUSE_REPEAT_TYPE_CONTACT);
				params.put("value", contact.getMobile());
				saveHouseRepeatDetailInfo(queryMap, params);
				queryMap.remove("contactMobile");
			}
			if (StringUtils.isNotNullAndEmpty(contact.getPhone())) {
				queryMap.put("contactPhone", contact.getPhone());
				params.put("comment", "经营联系人[" + contact.getName()
						+ "]的联系方式");
				params.put("type",
						BusinessConstants.HOUSE_REPEAT_TYPE_CONTACT);
				params.put("value", contact.getPhone());
				saveHouseRepeatDetailInfo(queryMap, params);
				queryMap.remove("contactPhone");
			}
		}
	}
	
	/**
	 * 借款人身份证查重
	 * 
	 * @param queryMap
	 * @param params
	 * @param houseApp
	 * @param idNoType
	 * @throws Exception
	 */
	@Transactional
	private void idNoRepeatCheck(Map<String, Object> queryMap,
			Map<String, Object> params, HouseApp houseApp, String idNoType)
			throws Exception {
		
		queryMap.put("repeatType", BusinessConstants.REPEAT_TYPE_CONTACT_IDHOUSED);
		queryMap.put("idNoType", idNoType);
		queryMap.put("appId", houseApp.getAppId());
		
		if (BusinessConstants.REPEAT_IDNO_TYPE_BORROWER.equals(idNoType)) {
			params.put("idNoType", BusinessConstants.REPEAT_IDNO_TYPE_BORROWER);
		}
		if (BusinessConstants.REPEAT_IDNO_TYPE_SPOUSE.equals(idNoType)) {
			params.put("idNoType", BusinessConstants.REPEAT_IDNO_TYPE_SPOUSE);
		}
		
		if (StringUtils.isNotNullAndEmpty(houseApp.getIdNo())) {
			queryMap.put("idNO", houseApp.getIdNo());
			params.put("comment", "借款人身份证号[" + houseApp.getIdNo() + "]");
			params.put("type", BusinessConstants.HOUSE_REPEAT_TYPE_ID_NO);
			params.put("value", houseApp.getIdNo());
			saveHouseRepeatDetailInfo(queryMap, params);
			queryMap.remove("idNO");
		}
		
		if (StringUtils.isNotNullAndEmpty(houseApp.getSpouseIdNo())) {
			queryMap.put("spouseIdNO", houseApp.getSpouseIdNo());
			params.put("comment", "借款人配偶身份证号[" + houseApp.getIdNo() + "]");
			params.put("type", BusinessConstants.HOUSE_REPEAT_TYPE_ID_NO);
			params.put("value", houseApp.getSpouseIdNo());
			saveHouseRepeatDetailInfo(queryMap, params);
			queryMap.remove("spouseIdNO");
		}
		params.remove("commentTail");
	}

	/**
	 * 保存查重数据
	 * @param queryMap
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	private void saveHouseRepeatDetailInfo(Map<String, Object> queryMap,
			Map<String, Object> params) throws Exception {
		String type = (String) params.get("type");
		String value = (String) params.get("value");
		String comment = (String) params.get("comment");
		String commentTail = (String) params.get("commentTail") == null ? ""
				: (String) params.get("commentTail");
		String idNoType = (String) params.get("idNoType");
		String orgId = (String) params.get("orgId");
		String loginId = (String) params.get("loginId");
		Timestamp time = (Timestamp) params.get("time");
		String repeatType = (String) queryMap.get("repeatType");
		
		String appId = (String) queryMap.get("appId");
		List<RepeatCheckQBean> repeatApps = this.repeatCheckQuery(queryMap);
		if (repeatApps != null && repeatApps.size() > 0) {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			for (RepeatCheckQBean repeatApp : repeatApps) {
				tmpMap.put("appId", repeatApp.getAppId());
				HouseApp tmpApp = null;
				List<HouseApp> apps = this.queryList(tmpMap);
				if (apps != null && apps.size() > 0) {
					tmpApp = this.queryList(tmpMap).get(0);
				} else {
					continue;
				}
				if (!"1".equals(tmpApp.getState())) {
					String repeatAppId = repeatApp.getAppId();
					String repeatAppName = repeatApp.getName();
					String repeatAppSpouseName = repeatApp.getSpouseName();
					// 联系方式comment信息
					if (BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_LOAN
							.equals(repeatType)) {
						if (StringUtils.isNotNullAndEmpty(repeatAppName)) {
							commentTail = "借款人[" + repeatAppName + "]联系方式";
						} else {
							commentTail = "借款人联系方式";
						}
					}
					if (BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_SPOUSE
							.equals(repeatType)) {
						if (StringUtils.isNotNullAndEmpty(repeatAppName)) {
							commentTail = "借款人配偶[" + repeatAppSpouseName
									+ "]联系方式";
						} else {
							commentTail = "借款人配偶联系方式";
						}
					}
					if (BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_LINKMAN
							.equals(repeatType)) {
						if (StringUtils.isNotNullAndEmpty(repeatAppName)) {
							commentTail = "常用联系人[" + repeatAppName + "]联系方式";
						} else {
							commentTail = "常用联系人联系方式";
						}
					}
					if (BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_INTERVIEW
							.equals(repeatType)) {
						if (StringUtils.isNotNullAndEmpty(repeatAppName)) {
							commentTail = "面审调查新添[" + repeatAppName + "]联系方式";
						} else {
							commentTail = "面审调查新添联系方式";
						}
					}
					if (BusinessConstants.REPEAT_TYPE_CONTACT_LINKMAN_TO_LOAN
							.equals(repeatType)) {
						if (StringUtils.isNotNullAndEmpty(repeatAppName)) {
							commentTail = "借款人[" + repeatAppName + "]联系方式";
						} else {
							commentTail = "借款人联系方式";
						}
					}
					if (BusinessConstants.REPEAT_TYPE_CONTACT_LINKMAN_TO_LINKMAN
							.equals(repeatType)) {
						if (StringUtils.isNotNullAndEmpty(repeatAppName)) {
							commentTail = "联系人[" + repeatAppName + "]联系方式";
						} else {
							commentTail = "联系人联系方式";
						}
					}
					if (BusinessConstants.REPEAT_TYPE_CONTACT_LINKMAN_TO_INTERVIEW
							.equals(repeatType)) {
						if (StringUtils.isNotNullAndEmpty(repeatAppName)) {
							commentTail = "面审调查新添[" + repeatAppName + "]联系方式";
						} else {
							commentTail = "面审调查新添联系方式";
						}
					}
					// 身份证comment信息
					if (BusinessConstants.HOUSE_REPEAT_TYPE_ID_NO.equals(type)) {
						if (BusinessConstants.REPEAT_IDNO_TYPE_BORROWER
								.equals(idNoType)) {
							commentTail = "借款人[" + repeatAppName + "]身份证号";
						}
						if (BusinessConstants.REPEAT_IDNO_TYPE_SPOUSE
								.equals(idNoType)) {
							commentTail = "借款人配偶[" + repeatAppSpouseName
									+ "]身份证号";
						}
					}
					HouseRepeatDetail houseRepeatDetail = new HouseRepeatDetail();
					houseRepeatDetail.setAppId(appId);
					houseRepeatDetail.setRepeatAppId(repeatAppId);
					houseRepeatDetail.setType(type);
					houseRepeatDetail.setValue(value);
					houseRepeatDetail.setComment(comment + "与申请(APP_ID:"
							+ repeatAppId + ")" + commentTail + "重复");
					houseRepeatDetail
							.setState(BusinessConstants.BUSINESS_STATUS_Y_VALID);
					houseRepeatDetail.setOrgId(orgId);
					houseRepeatDetail.setOperator(loginId);
					houseRepeatDetail.setCreateTime(time);
					houseRepeatDetail.setUpdateTime(time);
					houseRepeatDetailService.add(houseRepeatDetail);
				}
			}
		}
	}
	
	/**
	 * 旧信息查重
	 * @param appFBean
	 * @param org
	 * @param loginId
	 * @param time
	 * @throws Exception 
	 */
	@Transactional
	@Deprecated
	private void oldRepeatCheakProcess(AppFBean appFBean, Org org, String loginId,
			Timestamp time) throws Exception {
		HouseApp houseApp = appFBean.getHouseApp();
		
		// 身份证 重复信息
		List<HouseRepeatDetail> repeatIdNoList = houseRepeatDetailService
				.repeatQueryByIdNo(houseApp.getAppId(), houseApp.getIdNo());
		
		// 联系信息重复信息
		List<HouseContact> lxrList = appFBean.getCommonContacts();
		if (houseApp.getProduct() != null
				&& houseApp.getProduct().contains("业主贷")) {
			lxrList.addAll(appFBean.getDealingsContacts());
		}
		String[] contacts = new String[lxrList.size()];
		for (int i = 0; i < contacts.length; i++) {
			HouseContact con = lxrList.get(i);
			if (con.getMobile() != null && !"".equals(con.getMobile())) {
				contacts[i] = con.getMobile();
			} else {
				contacts[i] = con.getPhone();
			}
		}
		List<HouseRepeatDetail> repeatContactList = houseRepeatDetailService
				.repeatQueryByContact(houseApp.getAppId(), contacts);
		
		// 单位名称重复信息
		List<HouseRepeatDetail> repeatComNameList = houseRepeatDetailService
				.repeatQueryByKeyValue(houseApp.getAppId(), "1", "comName",
						houseApp.getComName());
		
		// 单位地址重复信息
		List<HouseRepeatDetail> repeatComAddressList1 = houseRepeatDetailService
				.repeatQueryByKeyValue(houseApp.getAppId(), "2", "comAddress",
						houseApp.getComAddress());
		List<HouseRepeatDetail> repeatComAddressList2 = houseRepeatDetailService
				.repeatQueryByKeyValue(
						houseApp.getAppId(),
						"2",
						"comAddress",
						houseApp.getComProvince() + houseApp.getComCity()
								+ houseApp.getComCounty()
								+ houseApp.getComAddress());
		
		List<HouseRepeatDetail> repeatComAddressList = new ArrayList<HouseRepeatDetail>();
		if (repeatComAddressList1 != null && !repeatComAddressList1.isEmpty()) {
			repeatComAddressList.addAll(repeatComAddressList1);
		}
		if (repeatComAddressList2 != null && !repeatComAddressList2.isEmpty()) {
			repeatComAddressList.addAll(repeatComAddressList2);
		}
		
		// 居住地址重复信息
		List<HouseRepeatDetail> repeatAddressList1 = houseRepeatDetailService
				.repeatQueryByKeyValue(houseApp.getAppId(), "3", "address",
						houseApp.getAddress());
		List<HouseRepeatDetail> repeatAddressList2 = houseRepeatDetailService
				.repeatQueryByKeyValue(
						houseApp.getAppId(),
						"3",
						"address",
						houseApp.getAddProvince() + houseApp.getAddCity()
								+ houseApp.getAddCounty()
								+ houseApp.getAddress());
		
		List<HouseRepeatDetail> repeatAddressList = new ArrayList<HouseRepeatDetail>();
		if (repeatAddressList1 != null && !repeatAddressList1.isEmpty()) {
			repeatAddressList.addAll(repeatAddressList1);
		}
		if (repeatAddressList2 != null && !repeatAddressList2.isEmpty()) {
			repeatAddressList.addAll(repeatAddressList2);
		}

		try {
			// 如果存在重复信息，先清空
			houseRepeatDetailService.deleteByAppId(houseApp.getAppId());

			// 入库操作 身份证重复信息
			if (repeatIdNoList != null && !repeatIdNoList.isEmpty()) {
				for (HouseRepeatDetail idNo : repeatIdNoList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					houseRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 联系信息重复信息
			if (repeatContactList != null && !repeatContactList.isEmpty()) {
				for (HouseRepeatDetail idNo : repeatContactList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					houseRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 单位名称重复信息
			if (repeatComNameList != null && !repeatComNameList.isEmpty()) {
				for (HouseRepeatDetail idNo : repeatComNameList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					houseRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 单位地址重复信息
			if (repeatComAddressList != null && !repeatComAddressList.isEmpty()) {
				for (HouseRepeatDetail idNo : repeatComAddressList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					houseRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 单位地址重复信息
			if (repeatAddressList != null && !repeatAddressList.isEmpty()) {
				for (HouseRepeatDetail idNo : repeatAddressList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					houseRepeatDetailService.add(idNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 添加客户档案信息返回档案Id
	 * @param houseApp
	 * @param loginId
	 * @param org
	 * @return
	 */
	@Transactional
	private String saveCustomer(HouseApp houseApp, String loginId, Org org) {
		Map<String , Object> rcmap=new HashMap<String , Object>();
		rcmap.put("idNo", houseApp.getIdNo());
		rcmap.put("idType", "01");
		rcmap.put("customerType", "01");
		rcmap.put("name", houseApp.getName());
		List<Customer> beanList = this.customerDao.queryList(rcmap);
		if(beanList.size() <= 0){
			Customer customer = new Customer();
			customer.setIdNo(houseApp.getIdNo());
			customer.setName(houseApp.getName());
			customer.setMobile(houseApp.getMobile());
			customer.setEducation(houseApp.getDiploma());
			customer.setEmail(houseApp.getEmail());
			customer.setMarriage(houseApp.getMarriage());
			customer.setAddProvince(houseApp.getAddProvince());
			customer.setAddCity(houseApp.getAddCity());
			customer.setAddCounty(houseApp.getAddCounty());
			customer.setAddress(houseApp.getAddress());
			customer.setPhone(houseApp.getPhone());
			customer.setCompanyName(houseApp.getComName());
			customer.setJobDuty(houseApp.getComDuty());
			customer.setIdType("01");
//			customer.setIdTypeName("身份证");
			customer.setCustomerType("01");
//			customer.setCustomerTypeName("个人客户");
			customer.setState("1"); //第一次插入的状态是有效的
			customer.setCreateTime(new Timestamp(System.currentTimeMillis()));
			customer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			customer.setOperator(loginId);
			customer.setCustomerManager(loginId); //客户经理
			customer.setOrgId(org.getOrgId());
			customerDao.add(customer);
		}
		return null;
	}

	/**
	 * 走流程
	 * @param appId		借款申請ID
	 * @param operator	当前操作人
	 * @param processer	待处理人
	 * @param bpmState	下一个节点的名字
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String appBpmNext(String appId,String operator,String processer,String bpmState)throws Exception{
		String msg= null;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		BpmTask bpmTask=processService.getProcessInstanceByBizKey(appId).get(0);
		bpmTask.setOperator(operator);
		bpmTask=processService.goNext(bpmTask,bpmState,processer);
		msg="流程操作成功。";
		return msg;
	}

	/**
	 * 系统拒贷（渠道查重）
	 * @param appId
	 * @param operator
	 * @param logContent1
	 * @param logContent2
	 * @param logContent3
	 * @param logContent4
	 * @throws Exception
	 */
	@Transactional
	private void sysRejectBpmNext(HouseApp houseApp,
			Map<String, Object> params) throws Exception {
		String appId = houseApp.getAppId();
		
		String rejectType = (String) params.get("rejectType");
		String operator = (String) params.get("operator");
		String logContent1 = (String) params.get("logContent1");
		String logContent2 = (String) params.get("logContent2");
		String logContent3 = (String) params.get("logContent3");
		String logContent4 = (String) params.get("logContent4");
		String logContent5 = (String) params.get("logContent5");
		
		BpmTask bpmTask = processService.getProcessInstanceByBizKey(appId).get(0);
		bpmTask.setOperator(operator);
		
		log.info("================ 申请单：" + appId + " ==================");
		// 积木查重拒贷
		if ("JMJD".equals(rejectType)) {
			log.info("================ 积木渠道查重--->系统拒件 ==================");
		} else 
		// 夫妻分别借款拒贷
		if("spouseJD".equals(rejectType)) {
			log.info("================ 夫妻分别借款（非精英贷）查重--->系统拒件 ==================");
			
		}
		
		processService.goNext(bpmTask, Constants.PROCESS_STATE_REJECT,
				BpmConstants.SYSTEM_SYS);
		
		bpmTask.setVariable("logContent1", logContent1);
		bpmTask.setVariable("logContent2", logContent2);
		bpmTask.setVariable("logContent3", logContent3);
		bpmTask.setVariable("logContent4", logContent4);
		bpmTask.setVariable("logContent5", logContent5);
		
		// 更改申请状态为0（放弃）
		houseApp.setState("24");
		this.updateOnlyChanged(houseApp);
		log.info("================ 系统拒件--->结束 ==================");
		processService.goNext(bpmTask, Constants.PROCESS_END_APP,
				BpmConstants.SYSTEM_SYS);
	}
	
	/**
	 *  查询信用贷款列表(功能区模块的)
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			inputTimeMin	进件时间开始
	 * 			inputTimeMax	进件时间结束
	 *			product 		产品
	 *			orgId  			营业部
	 *			stateTask		流程状态
	 * @return
	 */
	public List<HouseApp> queryHouseQueryList(Map<String, Object> map){
		return dao.queryHouseQueryList(map);
	}
	
	public int queryHouseQueryCount(Map<String, Object> map){
		return dao.queryHouseQueryCount(map);
	}

	//add  zhangguo 20150724 催收根据合同单号查产品为业主贷的相关信息
	public HouseApp queryByContractNo(String contractNo) {
		return (HouseApp)dao.queryByContractNo(contractNo);
	}
	
}
