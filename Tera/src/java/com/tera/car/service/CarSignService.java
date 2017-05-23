package com.tera.car.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.car.constant.Constants;
import com.tera.car.model.CarApp;
import com.tera.car.model.CarDecision;
import com.tera.car.model.form.SignFBean;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.img.service.ImgService;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchInfo;
import com.tera.match.service.Loan2matchService;
import com.tera.match.service.MatchResultService;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.product.model.Product;
import com.tera.product.model.ProductFeeRate;
import com.tera.product.service.ProductService;
import com.tera.sys.model.ResultObj;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

/**
 * 
 * 信用贷款申请影像摘要服务类
 * <b>功能：</b>CarSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carSignService")
public class CarSignService {

	private final static Logger log = Logger.getLogger(CarSignService.class);
	
	@Autowired(required=false)
    private ContractService contractService;
	
	@Autowired(required=false)
	private CarAppService carAppService;
	@Autowired(required=false) //自动注入
	private UserService userService;
	@Autowired(required=false) //自动注入
	ProcessService processService;
	@Autowired(required=false) //自动注入
	CarDecisionService carDecisionService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	MatchResultService matchResultService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false)
	private Loan2matchService<Loan2match> loan2matchService;
	
	
	
	
	/**
	 * 审核流程跳转
	 * @param appId			申请ID
	 * @param nextState		下一个节点名称
	 * @param operator		当前流程 实际处理人（当前登录用户）
	 * @param processer		下个流程的 待处理人
	 */
	@Transactional
	public void contract(String appId,String nextState,String operator,String processer,
				String logContent1,String logContent2,String logContent3,String logContent4,String logContent5){
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
		BpmTask task=taskList.get(0);
		task.setOperator(operator);
		task.setVariable("logContent1",logContent1);
		task.setVariable("logContent2",logContent2);
		task.setVariable("logContent3",logContent3);
		task.setVariable("logContent4",logContent4);
		task.setVariable("logContent5",logContent5);
		task = processService.goNext(task, nextState, processer);
		
	}
	
	/***
	 * 生成合同、提交
	 * @param signFBean
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 * @return 1-撮合成功; 2-撮合失败; 3-提交成功; 4-签约失败; 5-已撮合过状态，只保存了合同信息; 6-需要撮合成功才能提交签约; 7-请上传合同后再提交签约(缺少M类文件)
	 */
	@Transactional
	public ResultObj saveContract(SignFBean signFBean, String loginId, String orgId) throws Exception {
		Contract contract = signFBean.getContract();
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("appId", contract.getLoanAppId());
		CarApp carApp = carAppService.queryList(appMap).get(0);
		//保存合同信息
		saveContract(carApp, contract, loginId, orgId);
		
		if("submit".equals(signFBean.getButtonType())){
			if("1".equals(contract.getState())){
				String yz = imgService.imgVerify(contract.getLoanAppId(), new String[]{"M"});
				if("".equals(yz)){
					//调流程
					this.contract(contract.getLoanAppId(), Constants.PROCESS_STATE_REVIEW, loginId, "", null, null, null, null, null);
					carApp.setState("17");
					carApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					carAppService.updateOnlyChanged(carApp);
					return new ResultObj("3", "提交成功！", true);//提交成功！				
				}else{
					return new ResultObj("7", "请上传合同后再提交签约(缺少" + yz +"类文件)！", false);//请上传合同后再提交签约(缺少M类文件)
				}
			}else{
				return new ResultObj("6", "需要撮合成功后才能提交签约！", false);//需要撮合成功才能提交签约
			}
		}else if("fail".equals(signFBean.getButtonType())){
			/**
			 * 签约失败
			 * 1.撮合回退
			 * 		1)释放撮合的结果(调用撮合实时放弃算法)
			 * 				t_contract	       当前合同状态 置为 0
			 * 				t_loan_repay_plan 还款计划 状态置为 0（ 无效）
			 * 				t_car_app   当前申请状态 置为 0
			 *		2)t_contract	       当前合同状态 置为 0
			 * 		3)t_loan_repay_plan 还款计划 状态置为 0（ 无效）
			 * 		4)t_car_app   当前申请状态 置为 0
			 * 流程跳转到  放弃   结束本次借款申请
			 * 2.跳转流程
			 */
			//撮合回退调用方法
			matchResultService.giveUpRealTimeMatch(contract.getLoanAppId());
			contract.setState("0");
			contractService.updateOnlyChanged((Contract) contract);
			loanRepayPlanService.deleteByContractNo(contract.getContractNo());
			carApp.setState("0");
			carAppService.updateOnlyChanged(carApp);
			//签约失败-跳转流程
			this.contract(contract.getLoanAppId(), Constants.PROCESS_STATE_GIVEUP, loginId, BpmConstants.SYSTEM_SYS, null, null, null, null, null);
			this.contract(contract.getLoanAppId(), Constants.PROCESS_END_APP, loginId, BpmConstants.SYSTEM_SYS, null, null, null, null, null);
			return new ResultObj("4", "签约失败，操作成功！", true);//签约失败
		}else{//撮合
			if(!"1".equals(contract.getState())){
				Map<String, Object> fmap=new HashMap<String, Object>();
				fmap.put("appId", contract.getLoanAppId());
				fmap.put("state", "1");
				fmap.put("type", "0");
				List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
				CarDecision decision=carDecisionList.get(0);
				Product pro = productService.queryByName(decision.getProduct());
				if(null != pro){
					contract.setLoanInterestRate(pro.getInterestRate());
					contract.setLoanServiceRate(pro.getSreviceFeeRate());
					contract.setLoanServiceRate2(pro.getSreviceFeeRate2());				
					contract.setLoanAmount(MathUtils.div(decision.getAmount(),MathUtils.sub(1,MathUtils.div(pro.getSreviceFeeRate(), 100.0))));//合同借款金额
					contractService.updateOnlyChanged((Contract) contract);
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("loanAppId", contract.getLoanAppId());
					map.put("type", "1");
					map.put("matchType", "0");
			    	List<Loan2match> listLoan = loan2matchService.queryList(map);//撮合队列
			    	
			    	if(listLoan!=null&&listLoan.size()>0){
			    		Loan2match loan2match = listLoan.get(0);
			    		loan2match.setLoanAmount(contract.getLoanAmount());
			    		loan2matchService.update(loan2match);
			    	}else{
			    		//撮合队列 添加 借款信息
			    		Loan2match loan2Match=new Loan2match();
			    		loan2Match.setLoanAppId(carApp.getAppId());
			    		loan2Match.setType("1"); // 1 新增  2差额
			    		loan2Match.setMatchType("0");
			    		loan2Match.setAppTime(carApp.getInputTime());
			    		loan2Match.setLoanAmount(contract.getLoanAmount()); //借款金额
			    		loan2Match.setLoanProduct(decision.getProduct());
			    		loan2Match.setLoanPeriod(decision.getPeriod());
			    		loan2Match.setLoanInterestRate(pro.getInterestRate());
//			    		loan2Match.setLoanServiceRate(pro.getServiceRate());
			    		loan2Match.setOrgId(carApp.getOrgId());
//			    		loan2Match.setUseage();
			    		loan2Match.setState("1");//初始状态 带撮合
			    		loan2Match.setOrgId2(orgId);
			    		loan2matchService.add(loan2Match);
			    		log.info(loan2Match);
			    	}
				}
				//去撮合
				MatchInfo result = matchResultService.realTimeMatch(contract.getLoanAppId());
				//调撮合
				if(result.isFlag()){
					//撮合成功后
					//   t_contract	       当前合同状态 置为 1
					//   t_car_app   当前申请状态 置为 14（撮合已完成）
					//   t_loan_repay_plan 生成还款计划
					
					//如果撮合成功 合同状态改为1重新生成合同编号；申请状态改为14已撮合
					String contractNo = getContractNo(contract.getLoanAppId(), contract);
					contract.setContractNo(contractNo);
					contract.setState("1");
					contractService.updateOnlyChanged((Contract) contract);
					carApp.setState("14");
					carApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					carAppService.updateOnlyChanged(carApp);
					//实时撮合后状态的同步===毕桃杨==
					matchResultService.handleAfterRealTimeMatch(contractNo);
					//如果撮合成功 要生成还款计划；===张悦==
					loanRepayPlanService.addCreditRepayPlan(contractNo);
					return new ResultObj("1", "撮合成功！", true);//撮合成功
				}else{
					//如果撮合失败 ，页面提示撮合失败；申请状态改为13未撮合
					carApp.setState("13");
					carAppService.updateOnlyChanged(carApp);
					return new ResultObj("2", "撮合失败！", true);//撮合失败
				}
			}else{
				//更新还款计划
				loanRepayPlanService.updateRepayPlanDate(contract);
				return new ResultObj("5", "保存合同成功！", true);//已撮合过状态，只保存了合同信息
			}
		}
	}

	/***
	 * 生成合同、提交  JM操作
	 * @param signFBean
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 * @return 1-撮合成功; 2-撮合失败; 3-提交成功; 4-签约失败; 5-已撮合过状态，只保存了合同信息; 6-需要撮合成功才能提交签约; 7-请上传合同后再提交签约(缺少M类文件)
	 */
	@Transactional
	public ResultObj saveContractJM(SignFBean signFBean, String loginId, String orgId) throws Exception {
		Contract contract = signFBean.getContract();
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("appId", contract.getLoanAppId());
		CarApp carApp = carAppService.queryList(appMap).get(0);
		//保存合同信息
		saveContract(carApp, contract, loginId, orgId);
		
		if("submit".equals(signFBean.getButtonType())){//提交
			if("1".equals(contract.getState())){
				String yz = imgService.imgVerify(contract.getLoanAppId(), new String[]{"M"});
				if("".equals(yz)){
					//调流程
					this.contract(contract.getLoanAppId(), Constants.PROCESS_STATE_REVIEW, loginId, "", null, null, null, null, null);
					carApp.setState("17");
					carApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					carAppService.updateOnlyChanged(carApp);
					return new ResultObj("3", "提交成功！", true);//提交成功！				
				}else{
					return new ResultObj("7", "请上传合同后再提交签约(缺少" + yz +"类文件)！", false);//请上传合同后再提交签约(缺少M类文件)
				}
			}else{
				return new ResultObj("6", "还未生成合同，无法提交！", false);//成合同为生成
			}
		}else if("fail".equals(signFBean.getButtonType())){//客户放弃
			contract.setState("0");
			contractService.updateOnlyChanged((Contract) contract);
			loanRepayPlanService.deleteByContractNo(contract.getContractNo());
			carApp.setState("0");
			carAppService.updateOnlyChanged(carApp);
			//签约失败-跳转流程
			this.contract(contract.getLoanAppId(), Constants.PROCESS_STATE_GIVEUP, loginId, BpmConstants.SYSTEM_SYS, "客户放弃", null, null, null, null);
			this.contract(contract.getLoanAppId(), Constants.PROCESS_END_APP, loginId, BpmConstants.SYSTEM_SYS,  "客户放弃", null, null, null, null);
			return new ResultObj("4", "签约失败，操作成功！", true);//签约失败
		}else{//生成合同
			if(!"1".equals(contract.getState())){ //合同状态 0 计算合同金额 生成合同编号
				Map<String, Object> fmap=new HashMap<String, Object>();
				fmap.put("appId", contract.getLoanAppId());
				fmap.put("state", "1");
				fmap.put("type", "0");
				List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
				CarDecision decision=carDecisionList.get(0);
				Product pro = productService.queryByName(decision.getProduct());
				if(null != pro){
					contract.setLoanInterestRate(pro.getInterestRate());
					contract.setLoanServiceRate(pro.getSreviceFeeRate());
					contract.setLoanServiceRate2(pro.getSreviceFeeRate2());
					double lAmount=MathUtils.div(decision.getAmount(),MathUtils.sub(1,MathUtils.div(pro.getSreviceFeeRate(), 100.0)));
					if(lAmount%100>0){//取整百
						int quzheng=((int)((lAmount+100)/100))*100;
						lAmount=quzheng;
					}
					contract.setLoanAmount(lAmount);//合同借款金额
					contractService.updateOnlyChanged((Contract) contract);
				}
				String contractNo = getContractNo(contract.getLoanAppId(), contract);
				contract.setContractNo(contractNo);
				contract.setState("1");
				contractService.updateOnlyChanged((Contract) contract);
				carApp.setState("13");
				carAppService.updateOnlyChanged(carApp);
				// 要生成还款计划
				loanRepayPlanService.addCreditRepayPlan(contractNo);
				return new ResultObj("5", "保存合同成功！", true);//撮合成功
			}else{
				//更新还款计划
				loanRepayPlanService.updateRepayPlanDate(contract);
				return new ResultObj("5", "保存合同成功！", true);//已撮合过状态，只保存了合同信息
			}
		}
	}

	
	
	/**
	 * 保存合同信息
	 * @param carApp
	 * @param contract
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 */
	@Transactional
	public Contract saveContract(CarApp carApp, Contract contract, String loginId, String orgId) throws Exception{
		Timestamp newTime=new Timestamp(System.currentTimeMillis());
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", contract.getLoanAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
		if(carDecisionList.size() > 0){
			contract.setLoanProduct(carDecisionList.get(0).getProduct());
			contract.setChannelType(carDecisionList.get(0).getBelongChannel());
			contract.setLoanPeriod(carDecisionList.get(0).getPeriod());
			Product pro = productService.queryByName(carDecisionList.get(0).getProduct());
			contract.setStartDate(newTime);//合同开始时间
			contract.setSignDate(newTime);//签约时间
			contract.setDownloadTime(newTime);//合同下载时间
			contract.setEndDate(endDate(newTime, pro.getPeriod()));//合同结束时间
			contract.setRepayMethod(pro.getRepayMethod());//还款方式
		}
		contract.setLoanName(carApp.getName());
		contract.setLoanIdType("01");//身份证
		contract.setLoanIdNo(carApp.getIdNo());
		contract.setContractClass("01");//车贷
		contract.setContractType("01");//合同类型线上线下
		Map<String, Object> cQuerymap = new HashMap<String, Object>();
		cQuerymap.put("loanAppId", contract.getLoanAppId());
		cQuerymap.put("contractClass", "01");
		List<Contract> contractList = contractService.queryList(cQuerymap);
		if(null != contractList && contractList.size() > 0){
			contract.setId(contractList.get(0).getId());
			contract.setContractNo(contractList.get(0).getContractNo());
			contract.setState(contractList.get(0).getState());
			contract.setOperator(loginId);
			contract.setOrgId(orgId);
			contract.setUpdateTime(newTime);
			contractService.updateOnlyChanged((Contract) contract);
		}else{
			if(contract.getId() == 0 && "0".equals(contract.getState())){//插入合同表 状态0
				contract.setOperator(loginId);
				contract.setContractNo("");
				contract.setOrgId(orgId);
				contract.setCreateTime(newTime);
				contract.setUpdateTime(newTime);
				contractService.add((Contract) contract);
			}else{//修改合同表 
				contract.setOperator(loginId);
				contract.setUpdateTime(newTime);
				contractService.updateOnlyChanged((Contract) contract);
			}			
		}
		return contract;
	}
	
	/**
	 * 签约提交时获取复核人员
	 * @return
	 */
	public String getNextUser(){
		List<User> users = userService.getUserByOrgAndRoleAndDepart(Constants.ORG_CODE,new String[]{Constants.ROLE_FHZY},null);
		String nextUser = null;
		if (users.size() > 0) {
			User user = users.get(new Random().nextInt(users.size()));
			nextUser = user.getLoginId();
		}
		return nextUser;
	}
	
	/**
	 * 获取合同号
	 * @param appId
	 * @return
	 * @throws Exception 
	 */
	public String getContractNo(String appId, Contract contract) throws Exception{
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("appId", appId);
		fmap.put("state", "1");
		fmap.put("type", "0");
		List<CarDecision> carVerityList = carDecisionService.queryList(fmap);
		String s = "";
		if(carVerityList.size() > 0){
			CarDecision carDecision = carVerityList.get(0);
			if(carDecision.getProduct().contains("业主贷"))
				s = "Y";
			else if(carDecision.getProduct().contains("精英贷"))
				s = "J";
			else if(carDecision.getProduct().contains("工薪贷"))
				s = "G";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return s + contract.getSignCity() + format.format(new Date()) + contractService.getContractCode();
	}
	
	/**
	 * 生成合同结束日期和还款开始日期和还款结束日期调用方法
	 * @param dtStart，合同开始日期
	 * @return
	 */
	public Date endDate(Date dtStart, int period){
		Date dtNext=DateUtils.addMonth(dtStart, period);
		int month=DateUtils.getMonth(dtNext);
		int day=DateUtils.getDay(dtNext);
		if (month==2 && day==29) {
			dtNext=DateUtils.addDay(dtNext, -1);
		}
		
		if (day==31) {
			dtNext=DateUtils.addDay(dtNext, -1);
		}
		return dtNext;
	}
	
	/**
	 * 获取合同借款金额
	 * @param carDecision
	 * @param product
	 * @return
	 */
	public double getHtJkje(CarDecision carDecision, Product product){
		double htjkje = MathUtils.div(carDecision.getAmount(),MathUtils.sub(1,MathUtils.div(product.getSreviceFeeRate(), 100.0)));
		
//		if (product.getName().contains("JM") || product.getName().contains("DX")) {
//		if("JM".equals(product.getBelongChannel()) || "DX".equals(product.getBelongChannel())){
		if(!"HD".equals(product.getBelongChannel())){
				if (htjkje % 100 > 0) {// 取整百
				htjkje = ((int) ((htjkje + 100) / 100)) * 100;
			}
		}
		return htjkje;
	}
	
	/**
	 * 获取月还款金额
	 * @param htjkje
	 * @param product
	 * @return
	 */
	public double getYhkje(double htjkje, Product product){
		double MAmount=MathUtils.div(
				MathUtils.mul(MathUtils.mul(htjkje, MathUtils.div(product.getInterestRate(), 100.0)),Math.pow(MathUtils.add(1.0,MathUtils.div(product.getInterestRate(), 100.0)), product.getPeriod())), 
				MathUtils.sub(Math.pow(MathUtils.add(1.0,MathUtils.div(product.getInterestRate(), 100.0)), product.getPeriod()),1.0)
				);
		return MAmount;
	}
	
	/**
	 * 获取罚款金额
	 * @param htjkje
	 * @param product
	 * @param days
	 * @return
	 */
	public double getFxje(double htjkje, Product product, int days){
		double fxje = MathUtils.mul(htjkje, MathUtils.mul(days, MathUtils.div(product.getPenaltyRate(), 100.0)));
		return fxje;
	}
	
	/**
	 * 获取滞纳金
	 * @param MAmount
	 * @param product
	 * @return
	 */
	public double getZnje(double MAmount, Product product){
		double wyje = MathUtils.div(MAmount, product.getDelayRate());
		return wyje;
	}
	
	/**
	 * 获取趸交服务费总额
	 * @param htjkje
	 * @param fkje
	 * @return
	 */
	public double getDjfwfje(double htjkje, double fkje){
		double djfwfje = MathUtils.sub(htjkje, fkje);
		return djfwfje;
	}
	
	/**
	 * 获取返回服务费金额
	 * @param djfwfje
	 * @param productFeeRate
	 * @return
	 */
	public double getFhfwfje(double djfwfje, ProductFeeRate productFeeRate){
		double fhfwfje = MathUtils.mul(djfwfje, MathUtils.div(productFeeRate.getSreviceFeeReduceRate(), 100.0));
		return fhfwfje;
	}
	
	/**
	 * 获取月还款金额根据放款金额和产品名称
	 * @param fkje
	 * @param productName
	 * @return
	 */
	public double getYhkje(double fkje, String productName){
		CarDecision carDecision = new CarDecision();
		carDecision.setAmount(fkje);
		if(null == productName || "".endsWith(productName))
			return 0.0;
		Product product = productService.queryByName(productName);
		if(null == product)
			return 0.0;
		double htjkje = MathUtils.div(carDecision.getAmount(),MathUtils.sub(1,MathUtils.div(product.getSreviceFeeRate(), 100.0)));
		double yhkje = MathUtils.div(
				MathUtils.mul(MathUtils.mul(htjkje, MathUtils.div(product.getInterestRate(), 100.0)),Math.pow(MathUtils.add(1.0,MathUtils.div(product.getInterestRate(), 100.0)), product.getPeriod())), 
				MathUtils.sub(Math.pow(MathUtils.add(1.0,MathUtils.div(product.getInterestRate(), 100.0)), product.getPeriod()),1.0)
				);
		return MathUtils.round(yhkje, 2);
	}
	
	/**
	 * 获取放款金额根据月还款额和产品
	 * @param yhkje
	 * @param product
	 * @return
	 */
	public double getFkje(double yhkje, String productName){
		Product product = productService.queryByName(productName);
		//（1+实际月利率）^[期限（月）-1]）*（1-服务费）
		double fz = MathUtils.mul(
				MathUtils.sub(Math.pow(MathUtils.add(1.0, MathUtils.div(product.getInterestRate(), 100)), product.getPeriod()), 1), 
						MathUtils.sub(1, product.getSreviceFeeRate()/100));
		
		//（实际月利率*（1+实际月利率）^期限（月）
		double fm = MathUtils.mul(
				product.getInterestRate()/100, 
				Math.pow(MathUtils.add(1, product.getInterestRate()/100),
						product.getPeriod()));
		//反推放款额=每月还款额*（（1+实际月利率）^[期限（月）-1]）*（1-服务费）/（实际月利率*（1+实际月利率）^期限（月））
		double fkje = MathUtils.mul(yhkje, MathUtils.div(fz, fm));
		return MathUtils.round(fkje, 2);
	}
}
