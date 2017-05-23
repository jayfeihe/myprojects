package com.tera.house.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.house.constant.Constants;
import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseDecision;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.cooperation.jmbox.model.JmboxLog;
import com.tera.cooperation.jmbox.service.JmboxLogService;
import com.tera.cooperation.jmbox.service.JmboxService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.model.ResultObj;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;
import com.tera.util.MathUtils;

/**
 * 
 * 信用贷款放款服务类
 * <b>功能：</b>HouseReviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-25 10:37:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseLendingService")
public class HouseLendingService {

	private final static Logger log = Logger.getLogger(HouseLendingService.class);
	
	@Autowired(required=false) //自动注入
	ContractService contractService;
	@Autowired(required=false) //自动注入
	PaymentService<Payment> paymentService;
	@Autowired(required=false) //自动注入
	AccounttingService<Accountting> accounttingService;
	@Autowired(required=false) //自动注入
	HouseAppService houseAppService;
	@Autowired(required=false) //自动注入
	ProcessService processService;
	@Autowired(required=false)
	LoanRepayPlanService loanRepayPlanService;

	@Autowired(required=false) //自动注入
	ProductService<Product> productService;
	
	@Autowired(required=false) //决策表服务
	HouseDecisionService houseDecisionService;
	@Autowired(required=false) //自动注入
	JmboxService jmboxService;
	@Autowired(required=false) //自动注入
	UserService userService;
	
	@Autowired(required=false) //自动注入
	private JmboxLogService jmboxLogService;
	
	
	/**
	 * 确认放款
	 * @param bean			   申请
	 * @param contract		   合同
	 * @param houseDecision 决策
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Transactional
	public ResultObj confirmLending(HouseApp bean,Contract contract,HouseDecision houseDecision,String loginId,String orgId)throws Exception{
		
	
		//得到产品
		Product product=productService.queryByName(houseDecision.getProduct());
		// 风险金 费率  5.6%
		double fxRate=0.056;
		//合同金额
		double htAmount=contract.getLoanAmount();
		//放款金额
		double fkAmount=houseDecision.getAmount();
		//风险金
		double fxAmount=MathUtils.div(MathUtils.mul(MathUtils.mul(htAmount,fxRate), product.getPeriod()),12);
		//服务费    合同金额 - 放款金额 = 趸交金额  ；  趸交金额 - 风险金= 服务费
		double fwAmount=MathUtils.sub(MathUtils.sub(htAmount, fkAmount), fxAmount);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		
		
		ResultObj jgobj=null;
		
		//第二步取得需要的各个数据
//		if(product.getName().indexOf("JM")>0){//特殊流程不用放款
		if("JM".equals(product.getBelongChannel())){//特殊流程不用放款
			//调换用积木盒子放款接口
			jgobj=this.jimuBoxLending(bean.getAppId(),loginId,orgId);
			if(!jgobj.isSuccess()){//放款失败 直接推出
				return jgobj;
			}
//		}else if(product.getName().indexOf("DX")>0){//特殊流程不用放款
		}else if("DX".equals(product.getBelongChannel())){//特殊流程不用放款
				//鼎轩 放款逻辑
//				contract.setChannelType("2");// 鼎轩
				contract.setChannelKeyId("");// 鼎轩没系统 没有关联
				contract.setChannelState("1");//已经确认放款  等 鼎轩处理
				contractService.updateOnlyChanged(contract);
				jgobj=new ResultObj("确认放款操作成功。", null, true);
//		}else if(product.getName().indexOf("MD")>0){//特殊流程不用放款
		}else if("MD".equals(product.getBelongChannel())){//特殊流程不用放款
			//鼎轩 放款逻辑
//			contract.setChannelType("3");// MD
			contract.setChannelKeyId("");// 鼎轩没系统 没有关联
			contract.setChannelState("1");//已经确认放款  等 鼎轩处理
			contractService.updateOnlyChanged(contract);
			jgobj=new ResultObj("确认放款操作成功。", null, true);
		}else if("RY".equals(product.getBelongChannel())){//特殊流程不用放款
			//鼎轩 放款逻辑
//			contract.setChannelType("3");// MD
			contract.setChannelKeyId("");// 鼎轩没系统 没有关联
			contract.setChannelState("1");//已经确认放款  等 鼎轩处理
			contractService.updateOnlyChanged(contract);
			jgobj=new ResultObj("确认放款操作成功。", null, true);
		}else{//正常流程
			Payment payment = new Payment();
			payment.setContractNo(contract.getContractNo());
			payment.setInOut("2");//2表示“付”
			payment.setSubject("放款");
			payment.setPlanAmount(fkAmount);
			payment.setActualAmount(fkAmount);
			payment.setSource("0");
			payment.setPeriodNum(contract.getLoanPeriod());
			payment.setSendFlag("0");//0表示未发盘
			payment.setState("1");//1表示准备放款
			payment.setOperator(loginId);
			payment.setOrgId(orgId);
			
			payment.setCreateTime(ts);
			payment.setUpdateTime(ts);
			//第三步，想t_payment表中插入数据
			paymentService.add(payment);
			jgobj=new ResultObj("确认放款操作成功。", null, true);
		}
		
		bean.setState("20");
		bean.setOperator(loginId);
		bean.setUpdateTime(ts);
		houseAppService.updateOnlyChanged(bean);	//更新 申请 状态
		
//		TODO  需要添加二期记账
		//第四步，向t_account表中插入一条数据
		Accountting accountting = new Accountting();
		accountting.setInOut("1"); //1 收
		accountting.setAccount("风险金");
		accountting.setContractNo(contract.getContractNo());
		accountting.setSubject("业务往来-放款风险金");
		accountting.setActualAmount(fxAmount);
		accountting.setPlanAmount(fxAmount);
		accountting.setSource("0");
		//期数原先定的是0，后来改为Contract表中的期限是多少就是多少
		//accountting.setPeriodNum(0);
		accountting.setPeriodNum(contract.getLoanPeriod());
		accountting.setState("1");
		accountting.setOperator(loginId);
		accountting.setOrgId(orgId);
		accountting.setCreateTime(ts);
		accountting.setUpdateTime(ts);
		accounttingService.add(accountting);
		
		accountting.setAccount("服务费");
		accountting.setSubject("业务往来-放款服务费");
		accountting.setActualAmount(fwAmount);
		accountting.setPlanAmount(fwAmount);
		accounttingService.add(accountting);
		
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("contractNo", contract.getContractNo());
//		fmap.put("state", "1");
//		fmap.put("type", "0");
		//还款计划
		List<LoanRepayPlan> rpList = loanRepayPlanService.queryList(fmap);
		//在职附表 添加 收款 明细
		for (int i = 0; i < rpList.size(); i++) {
			LoanRepayPlan repayPlan=rpList.get(i);
			Payment pm =new Payment();
			pm.setContractNo(contract.getContractNo());
			pm.setInOut("1");
			pm.setSubject("收本息");
			double souAm=MathUtils.add(repayPlan.getInterestReceivable(),repayPlan.getPrincipalReceivable());
			pm.setPlanAmount(souAm);
			pm.setActualAmount(souAm);
			pm.setPeriodNum(repayPlan.getPeriodNum());
			pm.setRepaymentDate(repayPlan.getRepaymentDate());
			pm.setState("0");	// 放款成功后 状态 置为 1
			pm.setCreateTime(new Timestamp(System.currentTimeMillis()));
			paymentService.add(pm);
		}
		return jgobj;
	}
	
	/**
	 * 退回复核
	 * @param bean
	 * @param loginid
	 * @param msg
	 */
	@Transactional
	public void backReview(HouseApp bean,String loginId, String msg)throws Exception{
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		bean.setState("18");
		bean.setOperator(loginId);
		bean.setUpdateTime(ts);
		houseAppService.updateOnlyChanged(bean);	//更新 申请 状态
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(bean.getAppId());
		BpmTask task=taskList.get(0);
		String zhlx=null,nextUser=null;
		//不通过，打回复核
		zhlx="复核";
		List<BpmLog> bpmLogs = processService.getProcessHistoryLog(bean.getAppId(), zhlx);
		BpmLog bm = bpmLogs.get(0);
		//判断bm.getOperator()是否可用。可用，退回。不可用，随机给复核人（万一没有复核人呢）。
		//联合t_user表判断state, 0不可用,替换nextUser
		nextUser = bm.getOperator();
		String state = userService.getUser(nextUser).getState();
		if("0".equals(state)){//离职   随机分配给复核人员（挂起不变）
			List<User> users = userService.getUserByOrgAndRoleAndDepart(Constants.ORG_CODE,new String[]{Constants.ROLE_FHZY},null);
			if (users.size() > 0) {
				User user = users.get(new Random().nextInt(users.size()));
				nextUser = user.getLoginId();
			}
		}
		//在职 退回给原复核人员
		task.setVariable("logContent1", "放款退回");
		task.setVariable("logContent2", msg);	
		task.setOperator(loginId);
		task = processService.goNext(task, zhlx, nextUser);
		log.info(task);
	}
	
	/**积木盒子 产品放款请求 接口
	 * @param appId		申请ID
	 * @throws Exception
	 */
	public ResultObj jimuBoxLending(String appId,String loginId,String orgId) throws Exception{

    		Map<String, Object> paMap=new HashMap<String, Object>();
    		paMap.put("appId",appId);
    		List<HouseApp> appList  = houseAppService.queryList(paMap);
    		//得到申请
    		HouseApp houseApp=appList.get(0);
    		paMap.put("state", "1");
    		paMap.put("type", "0");
    		List<HouseDecision> houseDecisionList = houseDecisionService.queryList(paMap);
    		//得到最终决策
    		HouseDecision decision=houseDecisionList.get(0);
    		//得到产品
    		//Product pro = productService.queryByName(decision.getProduct());
    		Map<String, Object> fmap=new HashMap<String, Object>();
    		fmap.put("loanAppId", appId);
    		fmap.put("contractClass", "01");		//房贷合同
    		fmap.put("contractType", "01");			//线上合同
    		fmap.put("state", "1");
    		//合同信息
    		Contract contract=contractService.queryList(fmap).get(0);
    		//调用 积木盒子 连接请求
//    		return jmboxService.reqJmBox(houseApp, decision, contract, loginId, orgId);
    		return null;
	}
	
	/**
	 * JM盒子放款操作 出现异常需要 无需重新推出 只走 流程以及相关入库处理的  方法，其他地方不要乱用
	 * @param bean			   申请
	 * @param contract		   合同
	 * @param houseDecision 决策
	 * @param loginId
	 * @param orgId
	 * @throws Exception
	 */
	@Transactional
	public void jMErrorconfirmLending(HouseApp bean,Contract contract,HouseDecision houseDecision,
			String loginId,String orgId,
			String jmId)throws Exception{
		
		//得到产品
		Product product=productService.queryByName(houseDecision.getProduct());
		// 风险金 费率  5.6%
		double fxRate=0.056;
		//合同金额
		double htAmount=contract.getLoanAmount();
		//放款金额
		double fkAmount=houseDecision.getAmount();
		//风险金
		double fxAmount=MathUtils.div(MathUtils.mul(MathUtils.mul(htAmount,fxRate), product.getPeriod()),12);
		//服务费    合同金额 - 放款金额 = 趸交金额  ；  趸交金额 - 风险金= 服务费
		double fwAmount=MathUtils.sub(MathUtils.sub(htAmount, fkAmount), fxAmount);

		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		bean.setState("20");
		bean.setOperator(loginId);
		bean.setUpdateTime(ts);
		houseAppService.updateOnlyChanged(bean);	//更新 申请 状态
		//第四步，向t_account表中插入一条数据
		Accountting accountting = new Accountting();
		accountting.setInOut("1"); //1 收
		accountting.setAccount("风险金");
		accountting.setContractNo(contract.getContractNo());
		accountting.setSubject("业务往来-放款风险金");
		accountting.setActualAmount(fxAmount);
		accountting.setPlanAmount(fxAmount);
		accountting.setSource("0");
		accountting.setPeriodNum(contract.getLoanPeriod());
		accountting.setState("1");
		accountting.setOperator(loginId);
		accountting.setOrgId(orgId);
		accountting.setCreateTime(ts);
		accountting.setUpdateTime(ts);
		accounttingService.add(accountting);
		
		accountting.setAccount("服务费");
		accountting.setSubject("业务往来-放款服务费");
		accountting.setActualAmount(fwAmount);
		accountting.setPlanAmount(fwAmount);
		accounttingService.add(accountting);
		
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("contractNo", contract.getContractNo());
//		fmap.put("state", "1");
//		fmap.put("type", "0");
		//还款计划
		List<LoanRepayPlan> rpList = loanRepayPlanService.queryList(fmap);
		//在职附表 添加 收款 明细
		for (int i = 0; i < rpList.size(); i++) {
			LoanRepayPlan repayPlan=rpList.get(i);
			Payment pm =new Payment();
			pm.setContractNo(contract.getContractNo());
			pm.setInOut("1");
			pm.setSubject("收本息");
			double souAm=MathUtils.add(repayPlan.getInterestReceivable(),repayPlan.getPrincipalReceivable());
			pm.setPlanAmount(souAm);
			pm.setActualAmount(souAm);
			pm.setPeriodNum(repayPlan.getPeriodNum());
			pm.setRepaymentDate(repayPlan.getRepaymentDate());
			pm.setState("0");
			pm.setCreateTime(new Timestamp(System.currentTimeMillis()));
			paymentService.add(pm);
		}
		//添加 日志
		JmboxLog jmLog=new JmboxLog();
    	jmLog.setAppId(bean.getAppId());
    	jmLog.setContractNo(contract.getContractNo());
    	jmLog.setType("1");		//项目推送 类型
    	jmLog.setOperator(loginId);
    	jmLog.setOrgId(orgId);
    	jmLog.setCreateTime(ts);
    	jmLog.setUpdateTime(ts);
    	jmLog.setState("1");
    	jmLog.setJmProjectId(jmId);
    	jmLog.setJmChineseName(bean.getName());
    	jmLog.setJmIdentityNumber(bean.getIdNo());
    	jmLog.setJmStatus("Error人工处理");
    	jmLog.setJmMessage("通讯出现异常，人工手动确认处理。");
    	jmboxLogService.add(jmLog);
    	
    	//更新合同
    	contract.setChannelType(houseDecision.getBelongChannel());
		contract.setChannelKeyId(jmId);
		contract.setChannelState("1");
		contractService.updateOnlyChanged(contract);
		
	}
	
	/**
	 * 积木盒子 手动确认 放款成功 
	 * @param contract			合同
	 * @param houseDecision	最总决策
	 * @param channelDate		实际放款时间
	 * @param orgId
	 * @param loginId
	 * @throws Exception
	 */
	@Transactional
	public void manualJMLendOk(Contract contract,HouseDecision houseDecision,Date channelDate,String orgId,String loginId) throws Exception{
		//放款成功，更新 合同
		contract.setChannelTime(new Timestamp(channelDate.getTime()));
		contract.setState("2");
		contract.setChannelState("5");
    	contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    	contractService.update(contract);
		//更新 还款计划 与 支付信息状态
		paymentService.paymentSuccessUpdate(contract.getContractNo(), channelDate);
		//流程跳转
		List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(contract.getLoanAppId());
		BpmTask task = bpmTasks.get(0);
		if (task!=null && "放款".equals(task.getState())) {
			task.setOperator(loginId);
    		task = processService.goNext(task, Constants.PROCESS_END_APP, task.getProcesser());
		}
		//更新 APP 状态
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("appId", contract.getLoanAppId());
		HouseApp houseApp = houseAppService.queryList(appMap).get(0);
		houseApp.setState("23");
		houseApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		houseAppService.update(houseApp);
		//记账
		Accountting account=new Accountting();
		account.setInOut("2");
		account.setContractNo(contract.getContractNo());
		account.setSource("");
		account.setPeriodNum(0);
		account.setState("1");
		account.setOperator(loginId);
		account.setOrgId(orgId);
		long time=System.currentTimeMillis();
		account.setCreateTime(new Timestamp(time));
		account.setUpdateTime(new Timestamp(time));
		account.setId(0);
		account.setAccount("出借金额");
		account.setSubject("业务往来-放款本金");
		account.setPlanAmount(houseDecision.getAmount());
		account.setActualAmount(houseDecision.getAmount());
		accounttingService.add(account);
	}
	
	
}
