package com.tera.car.service;

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
import com.tera.car.constant.BusinessConstants;
import com.tera.car.constant.Constants;
import com.tera.car.dao.CarAppDao;
import com.tera.car.model.CarApp;
import com.tera.car.model.CarContact;
import com.tera.car.model.CarDecision;
import com.tera.car.model.CarExt;
import com.tera.car.model.CarInfo;
import com.tera.car.model.CarRepeatDetail;
import com.tera.car.model.form.AppFBean;
import com.tera.car.model.form.CarJsonMsg;
import com.tera.car.model.form.RepeatCheckQBean;
import com.tera.customer.dao.CustomerDao;
import com.tera.customer.model.Customer;
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
 * <b>功能：</b>CarAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carAppService")
public class CarAppService {

	private final static Logger log = Logger.getLogger(CarAppService.class);

	@Autowired(required=false)
    private CarAppDao dao;
	@Autowired(required=false)
    private UserService userService;
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	@Autowired(required=false) //自动注入
	CarExtService carExtService;
	@Autowired(required=false) //自动注入
	CarContactService carContactService;
	@Autowired(required=false)
	private ProcessService processService;
	
	@Autowired(required=false)
	private CarRepeatDetailService carRepeatDetailService;

	//规则调用
	@Autowired(required=false)
	private ScoreCardService scoreCardService;
	
	@Autowired(required=false)
	ImgService imgService;
	
	@Autowired(required=false) //自动注入
	private CustomerDao<Customer> customerDao;
	@Autowired(required=false) //自动注入
	CarDecisionService carDecisionService;
	
	@Autowired(required=false) //自动注入
	private CarAppHandlerService carAppHandlerService;
	@Autowired(required=false)
	private CarInfoService carInfoService;
	
	
	@Transactional
	public void add(CarApp... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarApp obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarApp obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarApp obj)  throws Exception {
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
	
	public List<CarApp> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CarApp queryByKey(Object id) throws Exception {
		return (CarApp)dao.queryByKey(id);
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
	public List<CarApp> queryBpmLoanAppList(Map<String, Object> map)throws Exception{
		
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
	public List<CarApp> queryBpmAppAndContractList(Map<String, Object> map)throws Exception{
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
		return "V"+org.getOrgId()+format.format(new Date())+dao.getAppIdCode();
	}
	
	/**
	 * 添加更新 或者提交申请
	 * @param appFBean
	 * @param org
	 * @param loginId
	 * @throws Exception
	 */
	@Transactional
	public CarJsonMsg appUpdate(AppFBean appFBean,Org org,String loginId)throws Exception{
		Timestamp time=new Timestamp(System.currentTimeMillis());
		//添加APP
		CarApp carApp=appFBean.getCarApp();
		String customerId = saveCustomer(carApp, loginId, org);
		if(null != customerId)
			carApp.setCustomerId(customerId);
		
		if(carApp.getId()==0){
			carApp.setAppId(getAppIdCode(org));
			carApp.setOrgId(org.getOrgId());
			carApp.setCustomerManager(loginId);
			carApp.setOperator(loginId);
			carApp.setIdType("01");//证件类型统一为 身份证
			carApp.setState("1");
			carApp.setCreateTime(time);
			carApp.setUpdateTime(time);
			this.add(carApp);
			BpmTask bpmTask=processService.startProcessInstanceByName(Constants.PROCESS_NAME_CAR, carApp.getAppId());
			bpmTask.setProcesser(loginId);
			processService.goNext(bpmTask,loginId);
		}else{
			carApp.setOperator(loginId);
			carApp.setUpdateTime(time);
		}
		
		/*
		 * 精英贷测试方案
		 * 规则： 1)	如判断客户性别为女性但是上传影像资料中不包括H类，则显示“女性”
		 * 		 2)	如判断客户性别为女性并且上传影像中包括H类学历证明资料则显示“女性、提供学历证明”
		 *   	 3)	判断客户性别为男性但是上传影像中包括H类学历证明资料则显示“提供学历证明“
		 */
		String gradeRemind = "";
		if (carApp.getProduct().contains("精英贷")) {
			gradeRemind = carAppHandlerService.getGradeRemind(carApp);
		}
		carApp.setGradeRemind(gradeRemind);
		this.updateOnlyChanged(carApp);
		
		//扩展信息
		 List<CarExt> carExts=appFBean.getCarExts();
		 for (CarExt carExt : carExts) {
			if(carExt.getId()==0){// 添加
				carExt.setAppId(carApp.getAppId());
				carExt.setOrgId(org.getOrgId());
				carExt.setOperator(loginId);
				carExt.setState("1");
				carExt.setCreateTime(time);
				carExt.setUpdateTime(time);
				this.carExtService.add(carExt);
			}else{
				carExt.setOperator(loginId);
				carExt.setUpdateTime(time);
				this.carExtService.updateOnlyChanged(carExt);
			}
		}
		//常用联系人
		List<CarContact> commonContacts=appFBean.getCommonContacts();
		for (CarContact commonContact : commonContacts) {
			if(commonContact.getId()==0){// 添加
				if("1".equals(commonContact.getState())){	//只有在状态 是 1 的时候 才添加
					commonContact.setAppId(carApp.getAppId());
					commonContact.setOrgId(org.getOrgId());
					commonContact.setOperator(loginId);
					commonContact.setCreateTime(time);
					commonContact.setUpdateTime(time);
					this.carContactService.add(commonContact);
				}
			}else{
				commonContact.setOperator(loginId);
				commonContact.setUpdateTime(time);
				this.carContactService.updateOnlyChanged(commonContact);
			}
		}
		// 经营往来联系人
		if(appFBean.getCarApp().getProduct()!=null&&
				appFBean.getCarApp().getProduct().contains("业主贷")){
			List<CarContact> dealingsContacts=appFBean.getDealingsContacts();
			for (CarContact commonContact : dealingsContacts) {
				if(commonContact.getId()==0){// 添加
					if("1".equals(commonContact.getState())){	//只有在状态 是 1 的时候 才添加
						commonContact.setAppId(carApp.getAppId());
						commonContact.setOrgId(org.getOrgId());
						commonContact.setOperator(loginId);
						commonContact.setCreateTime(time);
						commonContact.setUpdateTime(time);
						this.carContactService.add(commonContact);
					}
				}else{
					commonContact.setOperator(loginId);
					commonContact.setUpdateTime(time);
					this.carContactService.updateOnlyChanged(commonContact);
				}
			}
		}
		
		// 车辆信息
		CarInfo carInfo = appFBean.getCarInfo();
		if (carInfo != null) {
			if (carInfo.getId() == 0) {
				carInfo.setAppId(carApp.getAppId());
				carInfo.setOperator(loginId);
				carInfo.setState("1");
				carInfo.setOrgId(org.getOrgId());
				carInfo.setCreateTime(time);
				carInfo.setUpdateTime(time);
				carInfoService.add(carInfo);
			} else {
				carInfo.setOperator(loginId);
				carInfo.setUpdateTime(time);
				carInfoService.updateOnlyChanged(carInfo);
			} 
		}
		String buttonType=appFBean.getButtonType();
		CarJsonMsg msg = null;
		//提交流程
		if("submit".equals(buttonType)){//yz,返回值  为  null 的时候 说明没附件   为 "" 的时候说明 满足条件
			Map<String, Object> carDecisionQMap=new HashMap<String, Object>();
			carDecisionQMap.put("appId", carApp.getAppId());
			carDecisionQMap.put("type", "1");
			List<CarDecision> carDecisionList = carDecisionService.queryList(carDecisionQMap);
			ArrayList<String> imgArrayList = new ArrayList<String>();
			
			//2015-03-30，分产品类别验证
			String productType = carApp.getProduct();
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
			if(null != carDecisionList && carDecisionList.size() > 0){
				String imageClass = carDecisionList.get(0).getImageSummarys();
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
			String yz=imgService.imgVerify(carApp.getAppId(), imgs);
			if("".equals(yz)){
				String appState=null;
				if(carApp.getState()==null||carApp.getState().equals("")){
					//判断数据库内 申请状态
					CarApp carApp1=this.queryByKey(carApp.getId());
					appState=carApp1.getState();
				}else{
					appState=carApp.getState();
				}
				if("1".equals(appState)){	//申请状态 为录入申请 提交时 设置进件时间
					carApp.setInputTime(time);
				}
				
	////////////>>>> 规则调用   <<<<//////////////
				//调用规则评分
				CreditScoreCard s2 = new CreditScoreCard();
				s2.setIsMarried(Integer.valueOf(carApp.getMarriage()));//婚姻状态
				
				//算出工作年限
				try {
					Date rzsj=carApp.getComAddDate();
					Date jjsj=new Date();
					int workAge = DateUtils.getYearRange(rzsj,jjsj);//进件时间减去comAddDate入职时间
					s2.setWorkYear(workAge);
				}catch (Exception e) {
					s2.setSex(1);
				}
				//计算并设置性别
				String appIdNo = carApp.getIdNo();
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
				s2.setHasChild(carApp.getWithoutChildren());//有无子女
			 	s2.setCorporate(Integer.valueOf(carApp.getComType()));//单位类型
			 	s2.setHouseStatus(Integer.valueOf(carApp.getAddNature()));// 居住性质
//			 	s2.setAppId(carApp.getAppId());
//			 	s2.setOperateUser(carApp.getOperator());
			 	s2.setAppno(carApp.getAppId());
			 	s2.setOperator(carApp.getOperator());
			 	s2.setOrgId(carApp.getOrgId());
			 	// 调用评分服务
			 	scoreCardService.creditScoreCard(s2);

				carApp.setRuleGrade(s2.getLevel());//等级
				carApp.setRuleScore(s2.getScore()+"");//评分
//				List<String> ruleRepList=s2.getResults();
//				if(ruleRepList!=null&&!ruleRepList.isEmpty()){
//					//返回结果，入库
//					carApp.setRuleGrade(ruleRepList.get(0));//评分
//					carApp.setRuleScore(ruleRepList.get(1));//等级
//				}
	////////////>>>> 规则调用END   <<<<//////////////
				
				//提交，根据log表判断是否退回件，是退回件，取退回时的操作人，不设置进件时间；不是退回的话，直接提交。
				List<BpmLog> bpmLogList = null;
				String nextState = "";
				if("2".equals(appState)) {// 审核退回
					bpmLogList = processService.getProcessHistoryLog(carApp.getAppId(), Constants.PROCESS_STATE_VERIFY);
					nextState = Constants.PROCESS_STATE_VERIFY;
					carApp.setState("3"); // 审核状态
				}
				if("5".equals(appState)) {// 核价退回
					bpmLogList = processService.getProcessHistoryLog(carApp.getAppId(), Constants.PROCESS_STATE_PRICE);
					nextState = Constants.PROCESS_STATE_PRICE;
					carApp.setState("8"); // 核价状态
				}
				if (bpmLogList != null && bpmLogList.size() != 0) {// 是退回件
					//判断	bpmLogList.get(0).getOperator()	是否可用，若不可用，提交至分单
					String nextUser = bpmLogList.get(0).getOperator();
			        String state = userService.getUser(nextUser).getState();
					if("0".equals(state)){//离职，不接受新任务。（挂起不变）
						nextUser = "";
					}
					this.appBpmNext(carApp.getAppId(),loginId, nextUser, nextState);
					msg = new CarJsonMsg(true, "提交成功。", carApp.getAppId(), carApp.getId());
					
				} else {
					// 不是退回件
					if (!carApp.getProduct().contains("精英贷")) {
						// 夫妻分别借款判断
						boolean isExist = isExistSpouseLoaning(carApp);
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

							this.sysRejectBpmNext(carApp, params);

							msg = new CarJsonMsg(true, "系统拒贷：客户配偶放款未结清。",
									carApp.getAppId(), carApp.getId());
							return msg;
						}
					}
					
					this.appBpmNext(carApp.getAppId(), loginId, "",Constants.PROCESS_STATE_PRICE);
					msg = new CarJsonMsg(true, "提交成功。", carApp.getAppId(), carApp.getId());
					carApp.setState("8"); // 核价状态
					// 积木查重
					/*String name = carApp.getName();
					String idCard = carApp.getIdNo();
					String appId = carApp.getAppId();
					JMChannalDuplicateResponseBean bean = jmboxService
							.JMChannalDuplicate(name,idCard,appId);
					if (bean == null) {
						this.appBpmNext(carApp.getAppId(), loginId, "",
								Constants.PROCESS_STATE_VERIFY);
						msg = new CarJsonMsg(true, "提交审核成功（积木查重接口调用失败）。", carApp
								.getAppId(), carApp.getId());
					} else {
						// 客户在积木上已存在，进行拒贷
						if (bean.isCarloaned() || bean.isCarloaning()) {
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
							
							this.sysRejectBpmNext(carApp,params);
							
							msg = new CarJsonMsg(true, "系统拒贷：客户在积木其他渠道已融资。",
									carApp.getAppId(), carApp.getId());
							return msg;
						} else {
							this.appBpmNext(carApp.getAppId(), loginId, "",
									Constants.PROCESS_STATE_VERIFY);
							msg = new CarJsonMsg(true, "提交成功。", carApp
									.getAppId(), carApp.getId());
						}
					}*/
				}
				this.updateOnlyChanged(carApp);
				
				// 数据查重处理
				carRepeatDetailService.deleteByAppId(carApp.getAppId());
				repeatCheckProcess(appFBean,org,loginId,time);
				
			}else if(yz==null){ //未找到图片的情况
				msg=new CarJsonMsg(false, "提交失败，请先上传必传资料。",carApp.getAppId(),carApp.getId());
			}else{//必传图片缺少的情况
				msg=new CarJsonMsg(false,"提交失败，请先上传缺少资料：（"+yz+"）。",carApp.getAppId(),carApp.getId());
			}
		}else if("waive".equals(buttonType)){
			carApp.setState("0");
			this.updateOnlyChanged(carApp);
			this.appBpmNext(appFBean.getCarApp().getAppId(),loginId, "", Constants.PROCESS_STATE_GIVEUP);
			this.appBpmNext(appFBean.getCarApp().getAppId(), loginId, BpmConstants.SYSTEM_SYS, Constants.PROCESS_END_APP);
			msg=new CarJsonMsg(true, "放弃成功",carApp.getAppId(),carApp.getId());
		}else{
			msg=new CarJsonMsg(true, "保存成功",carApp.getAppId(),carApp.getId());
		}
		return msg;
	}
	
	/**
	 * 判断是否有配偶在借款中
	 * @param spouseName
	 * @param spouseIdNo
	 */
	private boolean isExistSpouseLoaning(CarApp carApp) {
		boolean isExist = false;
		String name = carApp.getName();
		String idNo = carApp.getIdNo();
		String spouseName = carApp.getSpouseName(); 
		String spouseIdNo = carApp.getSpouseIdNo();
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
					if (tmpBean.getSpouseIdNo().equals(carApp.getIdNo()) && tmpBean
							.getSpouseName().equals(carApp.getName())) {
						isExist = true;
						break;
					}
				}
				
				// tmpBean：借款人自己是历史借款人配偶
				if ("1".equals(tmpBean.getFlag())) {
					if (tmpBean.getIdNo().equals(carApp.getSpouseIdNo()) && tmpBean
							.getName().equals(carApp.getSpouseName())) {
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
		CarApp carApp = appFBean.getCarApp();
		List<CarContact> commonContacts = appFBean.getCommonContacts();
		List<CarContact> dealingContacts = appFBean.getDealingsContacts();
		
		List<CarContact> contacts = new ArrayList<CarContact>();
		contacts.addAll(commonContacts);
		contacts.addAll(dealingContacts);
		
		// 参数Map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", org.getOrgId());
		params.put("loginId", loginId);
		params.put("time", time);
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		
		// 1、单位名称查重
		if (StringUtils.isNotNullAndEmpty(carApp.getComName())) {
			comNameAndAddressRepeatCheck(queryMap, params, carApp,
					BusinessConstants.CAR_REPEAT_TYPE_COM_NAME);
			queryMap.clear();
		}
		
		// 2、单位地址查重
		if (StringUtils.isNotNullAndEmpty(carApp.getComAddress())) {
			comNameAndAddressRepeatCheck(queryMap, params, carApp,
					BusinessConstants.CAR_REPEAT_TYPE_COM_ADD);
			queryMap.clear();
		}
		
		// 3、居住地址查重
		if (StringUtils.isNotNullAndEmpty(carApp.getAddress())) {
			comNameAndAddressRepeatCheck(queryMap, params, carApp,
					BusinessConstants.CAR_REPEAT_TYPE_HOME_ADD);
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
		loanContactRepeatCheck(queryMap, params, carApp,
				BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_LOAN);
		
		// 4.2、借款人联系方式同历史库配偶联系方式比较
		loanContactRepeatCheck(queryMap, params, carApp,
				BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_SPOUSE);
		
		// 4.3、借款人联系方式同历史库联系人联系方式比较
		loanContactRepeatCheck(queryMap, params, carApp,
				BusinessConstants.REPEAT_TYPE_CONTACT_LOAN_TO_LINKMAN);
		
		// 4.4、借款人联系方式同面审调查表中新添加的数据进行比较
		loanContactRepeatCheck(queryMap, params, carApp,
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
		idNoRepeatCheck(queryMap, params, carApp,
				BusinessConstants.REPEAT_IDNO_TYPE_BORROWER);
		
		// 5.2、同借款人配偶身份证查重
		idNoRepeatCheck(queryMap, params, carApp,
				BusinessConstants.REPEAT_IDNO_TYPE_SPOUSE);
		
		queryMap.clear();
		params.clear();
	}
	
	/**
	 * 单位名称以及借款人地址（单位地址和居住地址）查重
	 * 
	 * @param queryMap
	 * @param params
	 * @param carApp
	 * @param type
	 * @throws Exception
	 */
	@Transactional
	private void comNameAndAddressRepeatCheck(Map<String, Object> queryMap,
			Map<String, Object> params, CarApp carApp, String type)
			throws Exception {
		queryMap.put("repeatType", BusinessConstants.REPEAT_TYPE_NAME_OR_ADDRESS);
		queryMap.put("appId", carApp.getAppId());
		params.put("type", type);
		
		if (BusinessConstants.CAR_REPEAT_TYPE_COM_NAME.equals(type)) {
			queryMap.put("comName", carApp.getComName());
			params.put("comment", "单位名称");
			params.put("value", carApp.getComName());
			saveCarRepeatDetailInfo(queryMap,params);
		}
		
		if (BusinessConstants.CAR_REPEAT_TYPE_COM_ADD.equals(type)) {
			queryMap.put("comProvince", carApp.getComProvince());
			queryMap.put("comCity", carApp.getComCity());
			queryMap.put("comCounty", carApp.getComCounty());
			queryMap.put("comAddress", carApp.getComAddress());
			params.put("comment", "单位地址");
			params.put("value", carApp.getComAddress());
			saveCarRepeatDetailInfo(queryMap,params);
		}
		
		if (BusinessConstants.CAR_REPEAT_TYPE_HOME_ADD.equals(type)) {
			queryMap.put("addProvice", carApp.getAddProvince());
			queryMap.put("addCity", carApp.getAddCity());
			queryMap.put("addCounty", carApp.getAddCounty());
			queryMap.put("address", carApp.getAddress());
			params.put("comment", "居住地址");
			params.put("value", carApp.getAddress());
			saveCarRepeatDetailInfo(queryMap,params);
		}
	}

	/**
	 * 借款人联系方式查重
	 * 
	 * @param queryMap
	 * @param params
	 * @param carApp
	 * @param contactType
	 * @throws Exception
	 */
	@Transactional
	private void loanContactRepeatCheck(Map<String, Object> queryMap,
			Map<String, Object> params, CarApp carApp, String repeatType)
			throws Exception {
		
		queryMap.put("repeatType", repeatType);
		queryMap.put("appId", carApp.getAppId());
		
		if (StringUtils.isNotNullAndEmpty(carApp.getMobile())) {
			queryMap.put("mobile", carApp.getMobile());
			params.put("comment", "借款人手机号码");
			params.put("type", BusinessConstants.CAR_REPEAT_TYPE_CONTACT);
			params.put("value", carApp.getMobile());
			saveCarRepeatDetailInfo(queryMap, params);
			queryMap.remove("mobile");
		}
		
		if (StringUtils.isNotNullAndEmpty(carApp.getPhone())) {
			queryMap.put("phone", carApp.getPhone());
			params.put("comment", "借款人住宅电话");
			params.put("type", BusinessConstants.CAR_REPEAT_TYPE_CONTACT);
			params.put("value", carApp.getPhone());
			saveCarRepeatDetailInfo(queryMap, params);
			queryMap.remove("phone");
		}
		
		if (StringUtils.isNotNullAndEmpty(carApp.getComPhone())) {
			queryMap.put("comPhone", carApp.getComPhone());
			params.put("comment", "借款人单位电话");
			params.put("type", BusinessConstants.CAR_REPEAT_TYPE_CONTACT);
			params.put("value", carApp.getComPhone());
			saveCarRepeatDetailInfo(queryMap, params);
			queryMap.remove("comPhone");
		}
		
		if (StringUtils.isNotNullAndEmpty(carApp.getSpouseMobile())) {
			queryMap.put("spouseMobile", carApp.getSpouseMobile());
			params.put("comment", "借款人配偶联系方式");
			params.put("type", BusinessConstants.CAR_REPEAT_TYPE_CONTACT);
			params.put("value", carApp.getSpouseMobile());
			saveCarRepeatDetailInfo(queryMap, params);
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
			Map<String, Object> params, List<CarContact> contacts,
			String repeatType) throws Exception {
		
		queryMap.put("repeatType", repeatType);
		
		for (CarContact contact : contacts) {
				
			queryMap.put("appId", contact.getAppId());
			if (StringUtils.isNotNullAndEmpty(contact.getMobile())) {
				queryMap.put("contactMobile", contact.getMobile());
				params.put("comment", "常用联系人[" + contact.getName()
						+ "]的手机号码");
				params.put("type",
						BusinessConstants.CAR_REPEAT_TYPE_CONTACT);
				params.put("value", contact.getMobile());
				saveCarRepeatDetailInfo(queryMap, params);
				queryMap.remove("contactMobile");
			}
			if (StringUtils.isNotNullAndEmpty(contact.getPhone())) {
				queryMap.put("contactPhone", contact.getPhone());
				params.put("comment", "经营联系人[" + contact.getName()
						+ "]的联系方式");
				params.put("type",
						BusinessConstants.CAR_REPEAT_TYPE_CONTACT);
				params.put("value", contact.getPhone());
				saveCarRepeatDetailInfo(queryMap, params);
				queryMap.remove("contactPhone");
			}
		}
	}
	
	/**
	 * 借款人身份证查重
	 * 
	 * @param queryMap
	 * @param params
	 * @param carApp
	 * @param idNoType
	 * @throws Exception
	 */
	@Transactional
	private void idNoRepeatCheck(Map<String, Object> queryMap,
			Map<String, Object> params, CarApp carApp, String idNoType)
			throws Exception {
		
		queryMap.put("repeatType", BusinessConstants.REPEAT_TYPE_CONTACT_IDCARD);
		queryMap.put("idNoType", idNoType);
		queryMap.put("appId", carApp.getAppId());
		
		if (BusinessConstants.REPEAT_IDNO_TYPE_BORROWER.equals(idNoType)) {
			params.put("idNoType", BusinessConstants.REPEAT_IDNO_TYPE_BORROWER);
		}
		if (BusinessConstants.REPEAT_IDNO_TYPE_SPOUSE.equals(idNoType)) {
			params.put("idNoType", BusinessConstants.REPEAT_IDNO_TYPE_SPOUSE);
		}
		
		if (StringUtils.isNotNullAndEmpty(carApp.getIdNo())) {
			queryMap.put("idNO", carApp.getIdNo());
			params.put("comment", "借款人身份证号[" + carApp.getIdNo() + "]");
			params.put("type", BusinessConstants.CAR_REPEAT_TYPE_ID_NO);
			params.put("value", carApp.getIdNo());
			saveCarRepeatDetailInfo(queryMap, params);
			queryMap.remove("idNO");
		}
		
		if (StringUtils.isNotNullAndEmpty(carApp.getSpouseIdNo())) {
			queryMap.put("spouseIdNO", carApp.getSpouseIdNo());
			params.put("comment", "借款人配偶身份证号[" + carApp.getIdNo() + "]");
			params.put("type", BusinessConstants.CAR_REPEAT_TYPE_ID_NO);
			params.put("value", carApp.getSpouseIdNo());
			saveCarRepeatDetailInfo(queryMap, params);
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
	private void saveCarRepeatDetailInfo(Map<String, Object> queryMap,
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
				CarApp tmpApp = null;
				List<CarApp> apps = this.queryList(tmpMap);
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
					if (BusinessConstants.CAR_REPEAT_TYPE_ID_NO.equals(type)) {
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
					CarRepeatDetail carRepeatDetail = new CarRepeatDetail();
					carRepeatDetail.setAppId(appId);
					carRepeatDetail.setRepeatAppId(repeatAppId);
					carRepeatDetail.setType(type);
					carRepeatDetail.setValue(value);
					carRepeatDetail.setComment(comment + "与申请(APP_ID:"
							+ repeatAppId + ")" + commentTail + "重复");
					carRepeatDetail
							.setState(BusinessConstants.BUSINESS_STATUS_Y_VALID);
					carRepeatDetail.setOrgId(orgId);
					carRepeatDetail.setOperator(loginId);
					carRepeatDetail.setCreateTime(time);
					carRepeatDetail.setUpdateTime(time);
					carRepeatDetailService.add(carRepeatDetail);
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
		CarApp carApp = appFBean.getCarApp();
		
		// 身份证 重复信息
		List<CarRepeatDetail> repeatIdNoList = carRepeatDetailService
				.repeatQueryByIdNo(carApp.getAppId(), carApp.getIdNo());
		
		// 联系信息重复信息
		List<CarContact> lxrList = appFBean.getCommonContacts();
		if (carApp.getProduct() != null
				&& carApp.getProduct().contains("业主贷")) {
			lxrList.addAll(appFBean.getDealingsContacts());
		}
		String[] contacts = new String[lxrList.size()];
		for (int i = 0; i < contacts.length; i++) {
			CarContact con = lxrList.get(i);
			if (con.getMobile() != null && !"".equals(con.getMobile())) {
				contacts[i] = con.getMobile();
			} else {
				contacts[i] = con.getPhone();
			}
		}
		List<CarRepeatDetail> repeatContactList = carRepeatDetailService
				.repeatQueryByContact(carApp.getAppId(), contacts);
		
		// 单位名称重复信息
		List<CarRepeatDetail> repeatComNameList = carRepeatDetailService
				.repeatQueryByKeyValue(carApp.getAppId(), "1", "comName",
						carApp.getComName());
		
		// 单位地址重复信息
		List<CarRepeatDetail> repeatComAddressList1 = carRepeatDetailService
				.repeatQueryByKeyValue(carApp.getAppId(), "2", "comAddress",
						carApp.getComAddress());
		List<CarRepeatDetail> repeatComAddressList2 = carRepeatDetailService
				.repeatQueryByKeyValue(
						carApp.getAppId(),
						"2",
						"comAddress",
						carApp.getComProvince() + carApp.getComCity()
								+ carApp.getComCounty()
								+ carApp.getComAddress());
		
		List<CarRepeatDetail> repeatComAddressList = new ArrayList<CarRepeatDetail>();
		if (repeatComAddressList1 != null && !repeatComAddressList1.isEmpty()) {
			repeatComAddressList.addAll(repeatComAddressList1);
		}
		if (repeatComAddressList2 != null && !repeatComAddressList2.isEmpty()) {
			repeatComAddressList.addAll(repeatComAddressList2);
		}
		
		// 居住地址重复信息
		List<CarRepeatDetail> repeatAddressList1 = carRepeatDetailService
				.repeatQueryByKeyValue(carApp.getAppId(), "3", "address",
						carApp.getAddress());
		List<CarRepeatDetail> repeatAddressList2 = carRepeatDetailService
				.repeatQueryByKeyValue(
						carApp.getAppId(),
						"3",
						"address",
						carApp.getAddProvince() + carApp.getAddCity()
								+ carApp.getAddCounty()
								+ carApp.getAddress());
		
		List<CarRepeatDetail> repeatAddressList = new ArrayList<CarRepeatDetail>();
		if (repeatAddressList1 != null && !repeatAddressList1.isEmpty()) {
			repeatAddressList.addAll(repeatAddressList1);
		}
		if (repeatAddressList2 != null && !repeatAddressList2.isEmpty()) {
			repeatAddressList.addAll(repeatAddressList2);
		}

		try {
			// 如果存在重复信息，先清空
			carRepeatDetailService.deleteByAppId(carApp.getAppId());

			// 入库操作 身份证重复信息
			if (repeatIdNoList != null && !repeatIdNoList.isEmpty()) {
				for (CarRepeatDetail idNo : repeatIdNoList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					carRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 联系信息重复信息
			if (repeatContactList != null && !repeatContactList.isEmpty()) {
				for (CarRepeatDetail idNo : repeatContactList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					carRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 单位名称重复信息
			if (repeatComNameList != null && !repeatComNameList.isEmpty()) {
				for (CarRepeatDetail idNo : repeatComNameList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					carRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 单位地址重复信息
			if (repeatComAddressList != null && !repeatComAddressList.isEmpty()) {
				for (CarRepeatDetail idNo : repeatComAddressList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					carRepeatDetailService.add(idNo);
				}
			}
			// 入库操作 单位地址重复信息
			if (repeatAddressList != null && !repeatAddressList.isEmpty()) {
				for (CarRepeatDetail idNo : repeatAddressList) {
					idNo.setState("1");
					idNo.setOrgId(org.getOrgId());
					idNo.setOperator(loginId);
					idNo.setCreateTime(time);
					idNo.setUpdateTime(time);
					carRepeatDetailService.add(idNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 添加客户档案信息返回档案Id
	 * @param carApp
	 * @param loginId
	 * @param org
	 * @return
	 */
	@Transactional
	private String saveCustomer(CarApp carApp, String loginId, Org org) {
		Map<String , Object> rcmap=new HashMap<String , Object>();
		rcmap.put("idNo", carApp.getIdNo());
		rcmap.put("idType", "01");
		rcmap.put("customerType", "01");
		rcmap.put("name", carApp.getName());
		List<Customer> beanList = this.customerDao.queryList(rcmap);
		if(beanList.size() <= 0){
			Customer customer = new Customer();
			customer.setIdNo(carApp.getIdNo());
			customer.setName(carApp.getName());
			customer.setMobile(carApp.getMobile());
			customer.setEducation(carApp.getDiploma());
			customer.setEmail(carApp.getEmail());
			customer.setMarriage(carApp.getMarriage());
			customer.setAddProvince(carApp.getAddProvince());
			customer.setAddCity(carApp.getAddCity());
			customer.setAddCounty(carApp.getAddCounty());
			customer.setAddress(carApp.getAddress());
			customer.setPhone(carApp.getPhone());
			customer.setCompanyName(carApp.getComName());
			customer.setJobDuty(carApp.getComDuty());
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
	private void sysRejectBpmNext(CarApp carApp,
			Map<String, Object> params) throws Exception {
		String appId = carApp.getAppId();
		
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
		carApp.setState("24");
		this.updateOnlyChanged(carApp);
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
	public List<CarApp> queryCarQueryList(Map<String, Object> map){
		return dao.queryCarQueryList(map);
	}
	
	public int queryCarQueryCount(Map<String, Object> map){
		return dao.queryCarQueryCount(map);
	}

	//add  zhangguo 20150724 催收根据合同单号查产品为业主贷的相关信息
	public CarApp queryByContractNo(String contractNo) {
		return (CarApp)dao.queryByContractNo(contractNo);
	}
	
}
