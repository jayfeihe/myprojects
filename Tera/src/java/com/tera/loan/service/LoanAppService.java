package com.tera.loan.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.customer.dao.CustomerDao;
import com.tera.customer.model.Customer;
import com.tera.loan.constant.LoanConstants;
import com.tera.loan.dao.LoanAppCollateralDao;
import com.tera.loan.dao.LoanAppContactDao;
import com.tera.loan.dao.LoanAppDao;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanAppCollateral;
import com.tera.loan.model.LoanAppContact;
import com.tera.loan.model.form.LoanAppBean;
import com.tera.loan.model.form.LoanUpdFBean;
import com.tera.loan.util.LoanUpdAppUtil;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Loan2matchService;
import com.tera.match.service.MatchResultService;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.Org;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 18:50:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanAppService")
public class LoanAppService<T> {

	private final static Logger log = Logger.getLogger(LoanAppService.class);
	
	@Autowired(required=false)
    private LoanAppDao dao;	
	
	@Autowired(required=false)
    private UserDao userdao;
	//借款抵押物
	@Autowired
	private LoanAppCollateralDao collateralDao;
	//借款申请
	@Autowired
	private LoanAppContactDao contactDao;
	
	@Autowired
    private CustomerDao customerDao;
	//财富客户信息
	@Autowired(required=false)
	private ProcessService processService;

	@Autowired(required=false) //自动注入
	private UserService userService;
	
	@Autowired(required=false) //自动注入
	private Loan2matchService<Loan2match> loan2matchService;
	
	
	
	@Autowired(required=false) //自动注入
	private LoanAppCollateralService<LoanAppCollateral> loanAppCollateralService;
	
	@Autowired(required=false) //自动注入
	private LoanAppContactService<LoanAppContact> loanAppContactService;
	
	@Autowired(required=false) //自动注入
	private MatchResultService<MatchResult> matchResultService;
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	
	
	/**
	 * @see 根据
	 * @data 2014-6-16
	 * @param list
	 * @param org 
	 * @return void
	 * @throws Exception 
	 */
	@Transactional
	public void updateLoanApp(List<LoanAppBean> list,Org org,
			String loginId,
			List<LoanAppCollateral> collList,
			List<LoanAppContact> perConList,
			List<LoanAppContact> orgConList) throws Exception{
		//借款端申请
		long dqDate=System.currentTimeMillis();
		String appId= "";
		if( null != list.get(0).getLoanapp().getAppId() && !"".equals(list.get(0).getLoanapp().getAppId())){
			appId=list.get(0).getLoanapp().getAppId();
		}else{
			//创建申请的时候 启动流程
			appId= this.getAppIdCode(org);
			BpmTask bpmTask=processService.startProcessInstanceByName(LoanConstants.PROCESS_NAME_B, appId);
			bpmTask.setProcesser(loginId);
			processService.goNext(bpmTask,loginId);
		}
		//保存抵押物   与 抵押物人
		this.CollteralAdd(collList, perConList, orgConList, appId,dqDate,loginId,org.getOrgId());
		
		//财富客户信息修改、新增
		Customer customer = null;
		if("01".equals(list.get(0).getLoanapp().getAppType())){ //个人
			customer =list.get(0).getCustomer();
		}else{
			customer= list.get(0).getOrgCustomer();
		}
		Map<String , Object> rcmap=new HashMap<String , Object>();
		rcmap.put("idNo", customer.getIdNo());
		rcmap.put("idType", customer.getIdType());
		rcmap.put("customerType", customer.getCustomerType());
		List<Customer> customerList = customerDao.queryList(rcmap);
		if(customerList ==null || customerList.size() ==0){
			//更新个人用户信息
			customer.setCreateTime(new java.sql.Timestamp(dqDate));
			customerDao.add(customer);
		}
		for (LoanAppBean bean : list) {
			//借款信息修改、新增
			LoanApp app = bean.getLoanapp(); //个人
			LoanApp orgapp = bean.getOrgLoanApp();// 机构
			//个人
			if(app.getId() != 0){
				app.setUpdateTime(new java.sql.Timestamp(dqDate));
				dao.updateOnlyChanged(app);
			}else {
				app.setCustomerNo(String.valueOf(customer.getId())); // 客户ID与财富客户绑定
				app.setAppId(appId);//申请ID
				app.setState("1");
				app.setCreateTime(new java.sql.Timestamp(dqDate));
				dao.add(app);
			}
			//机构
			if(orgapp.getId()!=0){ 
				orgapp.setUpdateTime(new java.sql.Timestamp(dqDate));
				dao.updateOnlyChanged(orgapp);
			}else{
				orgapp.setAppId(appId);
				orgapp.setState("1");
				orgapp.setCustomerNo(String.valueOf(customer.getId()));
				orgapp.setCreateTime(new java.sql.Timestamp(dqDate));
				dao.add(orgapp);
			}
			updateContact(bean,appId,org);
		}
		
		
	}
	/**
	 * @see 插入人员信息
	 * @data 2014-6-6
	 * @param bean 
	 * @return void
	 */
	public void updateContact(LoanAppBean bean,String appId,Org org){
		long dqDate=System.currentTimeMillis();
		// 存入法定人信息
		LoanAppContact fdContact = bean.getFdOrgContact();
		if(fdContact.getId() != 0){
			fdContact.setUpdateTime(new java.sql.Timestamp(dqDate));
			contactDao.updateOnlyChanged(fdContact);
		}else{
			fdContact.setContactType("02");
			fdContact.setMainFlag(bean.getLoanapp().getMainFlag());
			fdContact.setOperator(bean.getLoanapp().getOperator());
			fdContact.setOrgId(org.getOrgId());
			fdContact.setAppId(appId);
			fdContact.setType(bean.getLoanapp().getAppType());
			fdContact.setCreateTime(new java.sql.Timestamp(dqDate));
			fdContact.setUpdateTime(new java.sql.Timestamp(dqDate));
			contactDao.add(fdContact);
		}
		//授权代理人
		LoanAppContact sqContact = bean.getSqOrgContact();
		if(sqContact.getId() != 0){
			sqContact.setUpdateTime(new java.sql.Timestamp(dqDate));
			contactDao.updateOnlyChanged(sqContact);
		}else{
			sqContact.setOperator(bean.getLoanapp().getOperator());
			sqContact.setOrgId(org.getOrgId());
			sqContact.setAppId(appId);
			sqContact.setMainFlag(bean.getLoanapp().getMainFlag());
			sqContact.setContactType("03");
			sqContact.setType(bean.getLoanapp().getAppType());
			sqContact.setCreateTime(new java.sql.Timestamp(dqDate));
			contactDao.add(sqContact);
		}
		// 财务人员信息
		LoanAppContact cwContact = bean.getCwOrgContact();
		if(cwContact.getId() != 0){
			cwContact.setUpdateTime(new java.sql.Timestamp(dqDate));
			contactDao.updateOnlyChanged(cwContact);
		}else{
			cwContact.setAppId(appId);
			cwContact.setOperator(bean.getLoanapp().getOperator());
			cwContact.setMainFlag(bean.getLoanapp().getMainFlag());
			cwContact.setOrgId(org.getOrgId());
			cwContact.setContactType("04");
			cwContact.setType(bean.getLoanapp().getAppType());
			cwContact.setCreateTime(new java.sql.Timestamp(dqDate));
			contactDao.add(cwContact);
		}
		// 配偶信息
		LoanAppContact poContact = bean.getPoContact();
		if(poContact.getId() != 0){
			poContact.setUpdateTime(new java.sql.Timestamp(dqDate));
			contactDao.updateOnlyChanged(poContact);
		}else{
			poContact.setOperator(bean.getLoanapp().getOperator());
			poContact.setMainFlag(bean.getLoanapp().getMainFlag());
			poContact.setOrgId(org.getOrgId());
			poContact.setAppId(appId);
			poContact.setCreateTime(new java.sql.Timestamp(dqDate));
			poContact.setType(bean.getLoanapp().getAppType());
			poContact.setContactType("01");
			contactDao.add(poContact);
		}
	}
	/**
	 * @see 类型1位+机构12位+时间8位+序号4位
	 * @data 2014-6-6
	 * @param org
	 * @return String
	 */
	@Transactional
	public String getAppIdCode(Org org){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return "B"+org.getOrgId()+format.format(new Date())+dao.getAppIdCode();
	}
	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(T t)  throws Exception {
		dao.updateOnlyChanged(t);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<T> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}
	
	/**
	 * 根据 流程节点  用户  机构 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 			bpmStates  	流程节点  字符串数组
	 * 			userLoginId 用户登录名         找到 安排给次用户的 待处理信息
	 * 			orgId		机构编码
	 * 			states		状态 字符串 数组
	 * 			name 	姓名/机构全称
	 * 			idNo 	证件号码
	 * 			mobile 	手机号
	 * 			amountMin 	金额下限
	 * 			amountMax 	金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限
	 * @return
	 */
	public List<LoanApp> queryBpmLoanAppList(Map<String, Object> map)throws Exception{
		
		String loginId=(String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
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
			User us=userdao.getUser(loginId);
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
	@Transactional
	public void updateAppByAppId(LoanApp app){
		dao.updateAppByAppId(app);
	}
	@Transactional
	public List<LoanApp> getAppByAppId(String appId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		return dao.getAppByAppId(map);
	}

	/**
	 * @see 更新回显申请信息页面Value
	 * @data 2014-6-17
	 * @param appId
	 * @param mainFlag
	 * @return LoanUpdFBean
	 */
	@Transactional
	public LoanUpdFBean getUpdLoanBean(String appId,String mainFlag){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("mainFlag", mainFlag); 
//		map.put("state", "1");
		map.put("collateralId", "0");
		//ex:updaFBean为借款申请信息封装回显Value
		//1： 根据唯一Value 申请Id,借款人表示 查询LoanApp借款申请表中信息存放到updateFBean
		//1： 根据唯一Value 申请Id,借款人表示 查询人员表中信息存放到updateFBean
		LoanUpdFBean updaFBean = new LoanUpdFBean();
		List<LoanApp> listApp= dao.queryList(map);
		List<LoanAppContact> contactList =contactDao.queryList(map);
		updaFBean =LoanUpdAppUtil.rtsLoanApp(updaFBean, listApp);
		updaFBean =LoanUpdAppUtil.rtsContact(updaFBean, contactList);
		//end @return updateFBean;
		return updaFBean;
	}
	public List<String> getMainFlagListByAppId(String appId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		return dao.getMainFlagListByAppId(map);
	}
	@Transactional
	public void deleteLoanApp(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	/**
	 * 提交流程
	 * @data 2014-6-16
	 * @param appId 申请ID
	 * @param loginId 登录用户
	 * @param orgId 机构
	 * @param isStart 表示是保存还是提交
	 * @return void
	 */
	@Transactional
	public void submitLoanApp(String appId,String loginId,String orgId,String isStart){
		//启动流程
		if("start".equals(isStart)){
			BpmTask bpmTask=processService.getProcessInstanceByBizKey(appId).get(0);
			/*String[] roleId={"风险专员"};
			List<User> uss=userService.getUserByOrgAndRole(orgId, roleId);
			if(uss.size()>0){
				int key= (int)(Math.random()*(uss.size()));
				String nextUser= uss.get(key).getName();
				for (User us : uss) {
					if(us.getLoginId().equals(loginId)){
						nextUser=loginId;
						break;
					}
				}
				bpmTask=processService.goNext(bpmTask,"风险专员初核",nextUser);
			}*/
			bpmTask=processService.goNext(bpmTask,"风险专员初核",loginId);
		}
	}
	
	/**
	 * 客户经理 删除借款申请  流程结束
	 * @param appId
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Transactional
	public void deleteLoanApp(String appId, String loginId) throws Exception{
		LoanApp app=new LoanApp();
		app.setAppId(appId);
		app.setState("0");
		this.updateAppByAppId(app);
		BpmTask bpmTask = null;
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
		if (taskList != null && taskList.size() > 0) {
			bpmTask = taskList.get(0);
		}
		//如果未查询到流程，返回
		if (bpmTask == null) {
			return;
		}
		bpmTask.setOperator(loginId);
		processService.goNext(bpmTask, "结束", BpmConstants.SYSTEM_SYS); //跳转到放弃
//		processService.goNext(bpmTask, BpmConstants.SYSTEM_SYS); //跳转到结束
		
	}
	

	/**
	 * 放弃的逻辑实现 （按照申请号）
	 * 1，调用小毕的撮合回退流程
	 * 2，T_LOAN_2MATCH表的STATE置为0；无效
	 * 3，T_LOAN_APP的STATE置为0；无效，然后流程结束
	 * @param appId 申请ID
	 * @param loginId 登录用户
	 * @param orgId 登录机构
	 * @throws Exception 异常
	 */
	@Transactional
	public void giveupLoanApp(String appId, String loginId, String orgId) throws Exception {
		//查询流程
		BpmTask bpmTask = null;
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
		if (taskList != null && taskList.size() > 0) {
			bpmTask = taskList.get(0);
		}
		//如果未查询到流程，返回
		if (bpmTask == null) {
			return;
		}
		//1，调用小毕的撮合回退流程
		matchResultService.giveupApp(appId);

		//2,T_LOAN_2MATCH表的STATE置为0；无效
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanAppId", appId);
		List<Loan2match> loan2matchList = loan2matchService.queryList(map);
		for (Loan2match loan2match : loan2matchList) {
			loan2match.setState("0"); //无效
			loan2match.setOperator(loginId);
			loan2match.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			loan2matchService.update(loan2match);
		}
		//3，T_LOAN_APP的STATE置为0；无效，然后流程结束
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("appId", appId);
		List<LoanApp> loanApps=dao.getAppByAppId(map);
		if (loanApps.size()>0) {
			LoanApp loanApp=loanApps.get(0);
			loanApp.setState("0");//无效
			loanApp.setOperator(loginId);
			loanApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			dao.updateAppByAppId(loanApp);
		}
		
		//风险专员放弃
		if (bpmTask.getState() .equals("风险专员初核")) {
			bpmTask.setOperator(loginId);
			processService.goNext(bpmTask, "放弃", BpmConstants.SYSTEM_SYS); //跳转到放弃
			processService.goNext(bpmTask, BpmConstants.SYSTEM_SYS); //跳转到结束
		}
		//签约放弃
		if (bpmTask.getState() .equals("自动撮合") || bpmTask.getState() .equals("人工撮合")) {
			bpmTask.setOperator(loginId);
			processService.goNext(bpmTask, "签约", loginId); //跳转到放弃
			processService.goNext(bpmTask, "放弃", BpmConstants.SYSTEM_SYS); //跳转到放弃
			processService.goNext(bpmTask, BpmConstants.SYSTEM_SYS); //跳转到结束
		}
		
	}
	/**
	 * @data 2014-6-19
	 * @param appId 申请ID
	 * @param mainFlg 主借款人
	 * @param personInfo //人员+机构信息ID
	 * @param contactId // 附加人员ID
	 * @throws Exception 
	 * @return void
	 */
	@Transactional
	public void deleteLoanPer(String appId,String mainFlg,String personInfo,String contactId) throws Exception{
		String[] perOrg = personInfo.split(",");
		//根据主键Id更新LoanApp表状态
		for (String id : perOrg) {
			LoanApp app=new LoanApp();
			app.setId(Integer.valueOf(id));
			app.setState("0");
			dao.updateOnlyChanged(app);
		}
		//取得LoanApp表中的客户Id 更新Customer 表状态
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("mainFlg", mainFlg);
		List<LoanApp> loanList= dao.getAppByAppId(map);
		if(loanList!= null){
			Map<String, Object> cusMap = new HashMap<String, Object>();
			Customer ccc = new Customer();
			cusMap.put("id", Integer.valueOf(loanList.get(0).getCustomerNo()));
			cusMap.put("state", "0");
			customerDao.updateOnlyChanged(cusMap);
		}*/
		//删除人员
		String[] peronId = contactId.split(",");
		for (String id : peronId) {
			contactDao.delete(id);
		}
	}

	
	// 保存 抵押物相关
	public void CollteralAdd(List<LoanAppCollateral> collList,List<LoanAppContact> perConList,
			List<LoanAppContact> orgConList,String appId,
			long sysTImestamp,String loginId,String orgId) throws Exception{
		//添加 担保物
		if(collList!=null&&collList.size()>0){
			for (LoanAppCollateral loanAppCollateral : collList) {
				loanAppCollateral.setUpdateTime(new Timestamp(sysTImestamp));
				loanAppCollateral.setOperator(loginId);
				loanAppCollateral.setOrgId(orgId);
				if(loanAppCollateral.getAppId()==null||loanAppCollateral.getAppId().equals("")){
					loanAppCollateral.setAppId(appId);
				}
				if(loanAppCollateral.getId()==0){
					loanAppCollateral.setCreateTime(new Timestamp(sysTImestamp));
					loanAppCollateralService.add(loanAppCollateral);
				}else{
					loanAppCollateralService.update(loanAppCollateral);
				}
				
			}
		}
		//添加个人联系人
		if(perConList!=null&&perConList.size()>0){
			for (LoanAppContact loanAppContact : perConList) {
				
				loanAppContact.setOperator(loginId);
				loanAppContact.setOrgId(orgId);
				loanAppContact.setUpdateTime(new Timestamp(sysTImestamp));
				
				if(loanAppContact.getAppId()==null||loanAppContact.getAppId().equals("")){
					loanAppContact.setAppId(appId);
				}
				if(loanAppContact.getId()==0){
					loanAppContact.setCreateTime(new Timestamp(sysTImestamp));
					loanAppContactService.add(loanAppContact);
				}else{
					loanAppContactService.update(loanAppContact);
				}
				
			}
		}
		//添加机构联系人
		if(orgConList!=null&&orgConList.size()>0){
			for (LoanAppContact loanAppContact : orgConList) {
				
				loanAppContact.setOperator(loginId);
				loanAppContact.setOrgId(orgId);
				loanAppContact.setUpdateTime(new Timestamp(sysTImestamp));
				
				if(loanAppContact.getAppId()==null||loanAppContact.getAppId().equals("")){
					loanAppContact.setAppId(appId);
				}
				if(loanAppContact.getId()==0){
					loanAppContact.setCreateTime(new Timestamp(sysTImestamp));
					loanAppContactService.add(loanAppContact);
				}else{
					loanAppContactService.update(loanAppContact);
				}
				
			}
		}
		
	}
	public List<LoanApp> getLoanListByContractNo(String contractNo) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		return dao.getLoanListByContractNo(map);
	}

	
	
	
}
