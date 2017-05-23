package com.tera.lend.service;

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
import com.tera.img.service.ImgService;
import com.tera.lend.constant.LendConstants;
import com.tera.lend.dao.LendAppDao;
import com.tera.lend.model.LendApp;
import com.tera.match.model.Lend2match;
import com.tera.match.service.Lend2matchService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>LendAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-04 18:02:45<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("lendAppService")
public class LendAppService{

	private final static Logger log = Logger.getLogger(LendAppService.class);

	@Autowired(required=false)
    private LendAppDao dao;

	@Autowired(required=false)
    private Lend2matchService<Lend2match> lend2matchService;
	@Autowired(required=false)
	ProcessService processService;
	@Autowired(required=false)
	ProductService<Product> productService;
	@Autowired(required=false)
	PaymentService<Payment> paymentService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	UserService userService;
	@Autowired(required=false) //自动注入
	private CustomerDao<Customer> customerDao;
	
	@Transactional
	public void add(LendApp... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(LendApp t : ts ){
			String idcode=dao.getAppIdCode();
			t.setAppId(t.getAppId()+idcode);
			dao.add(t);
		}
	}
	
	/**
	 * 添加 并启动流程 到 录入申请
	 * @param app
	 * @param longinId
	 */
	@Transactional
	public void addAndBpm(LendApp app, String longinId){
		app.setAppId(this.getAppIdCode(app.getCreateTime().getTime(),app.getOrgId()));		
		dao.add(app);
		BpmTask bpmTask=processService.startProcessInstanceByName(LendConstants.PROCESS_NAME_L, app.getAppId());
		bpmTask.setProcesser(longinId);
		processService.goNext(bpmTask,longinId);
			
			
	}
	
	String getAppIdCode(long time,String orgId){
		String jg="";
		jg+="L";
		jg+=orgId;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		jg+=formatter.format(new Date(time));
		jg+=dao.getAppIdCode();
		return jg;
	}
	
	
	@Transactional
	public void update(LendApp t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(LendApp t)  throws Exception {
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
	
	@Transactional
	public void deleteLendApp(int id,String loginId) throws Exception {
		LendApp app=this.queryByKey(id);
		if(app!=null&&app.getId()>0){
			LendApp app1=new LendApp();
			app1.setId(id);
			app1.setState("0");
			this.updateOnlyChanged(app1);
			BpmTask bpmTask = null;
			List<BpmTask> taskList = processService.getProcessInstanceByBizKey(app.getAppId());
			if (taskList != null && taskList.size() > 0) {
				bpmTask = taskList.get(0);
			}
			//如果未查询到流程，返回
			if (bpmTask == null) {
				return;
			}
			bpmTask.setOperator(loginId);
			processService.goNext(bpmTask, "结束", BpmConstants.SYSTEM_SYS); //跳转到放弃
			
		}
		
	}
	
	
	public List<LendApp> getLendListByAppId(Object t){
		return dao.getLendListByAppId(t);
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<LendApp> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public LendApp queryByKey(Object id) throws Exception {
		return (LendApp)dao.queryByKey(id);
	}

	public LendApp queryByAppId(String appId) throws Exception {
		return dao.queryByAppId(appId);
	}
	
	@Transactional
	public void startFlow(String appId,String username,String matchType,String loginId,LendApp bean,Org org) throws Exception{
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
		BpmTask bpmTask=bpmTask=taskList.get(0);
		bpmTask.setProcesser(loginId);
		bpmTask=processService.goNext(bpmTask,matchType,username);
		Product pt=productService.queryByName(bean.getProduct());
		long timeLong=System.currentTimeMillis();
		if("人工撮合".equals(matchType)){
			//根据撮合类型得到 工作流程继续
			//入库 入库到 带撮合列表
			Lend2match l2=new Lend2match();
			l2.setLendAppId(appId);
			l2.setType("1"); // 1 新增  2差额
			l2.setMatchType("1");	//撮合类型 为1 人工撮合
			l2.setAppTime(new Timestamp(timeLong));
			l2.setLendAmount(bean.getAmount());
			l2.setLendProduct(bean.getProduct());
			l2.setLendPeriod(pt.getPeriod());
			l2.setLendInterestRate(pt.getInterestRate());
			l2.setLendServiceRate(pt.getSreviceFeeRate());
			l2.setOrgId(bean.getOrgId());
			l2.setState("1");//初始状态 带撮合
			l2.setOrgId2(org.getOrgId());
			l2.setCreateTime(new Timestamp(timeLong));
			l2.setUpdateTime(new Timestamp(timeLong));
			lend2matchService.add(l2);
			log.info(l2);
		}else{
			Payment pm=new Payment();
			pm.setContractNo(bean.getAppId());		//合同号
			pm.setInOut("1");						//收
			pm.setSubject("出借资金");					//科目
			pm.setPlanAmount(bean.getAmount());		//计划金额
			pm.setActualAmount(bean.getAmount());	//实际金额
			pm.setSource("");						//来源
			pm.setPeriodNum(0);						//期数
			pm.setSendFlag("");						// 这里用不到
			pm.setState("1");						//状态 : 1 未支付
			pm.setOperator(loginId);				//操作员为 当然录入人员
			pm.setOrgId(org.getOrgId());			//所属机构，为当前登录人机构
			pm.setOrgId(org.getOrgId());			//所属机构，为当前登录人机构
			pm.setOrgId(org.getOrgId());			//所属机构，为当前登录人机构
			pm.setCreateTime(new Timestamp(timeLong));
			pm.setUpdateTime(new Timestamp(timeLong));
			paymentService.add(pm);
		}
	}

	/**
	 * 根据 流程节点  用户  机构 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 			states      状态列表
	 * 			bpmStates   	流程节点列表
	 * 			userLoginId 用户登录名         找到 安排给次用户的 待处理信息
	 * 			orgId		机构编码
	 * 			roleLever 角色等级
	 * 			customerManager 客户经理
	 * 			name 	姓名/机构全称
	 * 			idNo 	证件号码
	 * 			mobile 	手机号
	 * 			amountMin 	金额下限
	 * 			amountMax 	金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限
	 * @return
	 */
	public List<LendApp> queryBpmLendAppList(Map<String, Object> map) throws Exception {
		String loginId = (String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if (loginId != null) {
			User us = userService.getUser(loginId);
			if (us != null && us.getIsAdmin() == 1) {
				map.remove("userLoginId");
			}
		}
		return dao.queryBpmLendAppList(map);
	}

	public int queryBpmLendAppCount(Map<String, Object> map) throws Exception {
		String loginId = (String) map.get("userLoginId");
		if (loginId != null){
			User us = userService.getUser(loginId);
			if (us != null && us.getIsAdmin() == 1){
				map.remove("userLoginId");
			}
		}
		return dao.queryBpmLendAppCount(map);
	}

	public List<LendApp> getLendListByContractNo(String contractNo) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		return dao.getLendListByContractNo(map);
	}

	
	/**
	 * 提前撤资，出借方收回自己的钱
	 * @param lendAppId
	 */
	public void  callBackLend(String lendAppId) {
		
		/*
		 * 1.获得的金额=本金+天数/360*年化利率*本金-违约金
		 * 插入到payment表中
		 * 2交易支付成功之后处理撮合中的记录
		 * 3处理lend和loan的状态
		 * 4记账
		 * 
		 */
	}

	public int queryDivestCount(Map<String, Object> queryMap) {
		return dao.queryDivestCount(queryMap);
	}

	public List<LendApp> queryDivestList(Map<String, Object> queryMap) {
		return dao.queryDivestList(queryMap);
	}
	
	@Transactional
	public Map<String, Object> lendAppSave(LendApp bean, String loginid, Org org, String isStart) throws Exception{
		//返回消息对象
		String rtType="";  //操作类型：添加，保存，提交
		String rtMss=""; //提示消息
		boolean rtBool=true; //操作成功标志
		boolean beanex=false; //申请是否存在
		String customerId = saveCustomer(bean, loginid, org);
		if(null != customerId)
			bean.setCustomerNo(customerId);
		if (bean.getId() != 0) {
			beanex=true;
			bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			this.updateOnlyChanged(bean);
			bean  = this.queryByKey(bean.getId());
			rtMss="修改成功";
		} else { //如果不存在
			//TODO service操作 需要修改
			long dqDate=System.currentTimeMillis();
			bean.setOrgId(org.getOrgId());
			bean.setOperator(loginid);
			bean.setCustomerManager(loginid); //添加客户经理
			bean.setCreateTime(new java.sql.Timestamp(dqDate));
			bean.setUpdateTime(new java.sql.Timestamp(dqDate));
			bean.setState("1");
			this.addAndBpm(bean,loginid);
			rtMss="添加成功";
		}
		
		
		//判断是否提交流程
		if("start".equals(isStart)){
			//验证图片是否上传
			String yz=imgService.imgVerify(bean.getAppId(), new String[]{"A01","B01"});
			//不缺少的情况
			if("".equals(yz)){
				String zhlx="0".equals(bean.getMatchType())?"自动撮合":"人工撮合";
				String clren="";
				if(zhlx.equals("自动撮合")){
					clren=BpmConstants.SYSTEM_SYS;
					zhlx="结算";
				}else{
					// 人工撮合岗位 ID
					String[] roleId={"人工撮合专员"};
					List<User> uss=userService.getUserByOrgAndRoleAndDepart("86", roleId,null);
					if(uss.size()>0){
						int key= (int)(Math.random()*(uss.size()));
						clren= uss.get(key).getName();
					 }else{
						rtMss= "提交失败，没有找到人工撮合专员。";
						rtBool=false;
					 }
				}
				if(rtBool){
					this.startFlow(bean.getAppId(),clren ,zhlx,loginid,bean,org);
					rtMss="提交成功";
				}
			}else if(yz==null){ //未找到图片的情况
				rtMss= "提交流程失败，请先上传必须资料。";
				rtBool=false;
			}else{
				//必传图片缺少的情况
				rtMss="提交流程失败，请先上传缺少资料：（"+yz+"）。";
				rtBool=false;
			}
		}
		
		if(!beanex){
			rtType="add";
		}else{
			rtType="update";
		}
		if("start".equals(isStart)){
			rtType+="submit";
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("rtBool", rtBool);
		returnMap.put("rtMss", rtMss);
		returnMap.put("rtType", rtType);
		returnMap.put("bean", bean);
		return returnMap;
	}
	
	/**
	 * 添加客户档案信息返回档案Id
	 * @param bean
	 * @param loginId
	 * @param org
	 * @return
	 */
	private String saveCustomer(LendApp bean, String loginId, Org org) {
		Map<String , Object> rcmap=new HashMap<String , Object>();
		rcmap.put("idNo", bean.getIdNo());
		rcmap.put("idType", bean.getIdType());
		rcmap.put("customerType", "01");
		rcmap.put("name", bean.getName());
		List<Customer> beanList = this.customerDao.queryList(rcmap);
		if(beanList.size() <= 0){
			Customer customer = new Customer();
			customer.setIdNo(bean.getIdNo());
			customer.setName(bean.getName());
			customer.setMobile(bean.getMobile());
			customer.setPhone(bean.getPhone());
			customer.setIdType(bean.getIdType());
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
	
}
