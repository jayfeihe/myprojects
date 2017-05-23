package com.tera.collection.phone.service;

import java.sql.Timestamp;
import java.util.Collections;
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
import com.tera.bpm.dao.BpmTaskDao;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.model.CollectionResult;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.User;
import com.tera.sys.service.ParameterService;
import com.tera.sys.service.UserService;
import com.tera.util.DateUtils;
/**
 * 催收系统批处理相关服务
 * @author Jesse
 *
 */
@Service("collectionBatchService")
public class CollectionBatchService {
	
	private final static Logger log = Logger.getLogger(LoanRepayPlanService.class);

	@Autowired(required=false)
    private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false)
	private CollectionBaseService collectionBaseService;
	@Autowired(required=false)
	private ContractService contractService;
	@Autowired(required=false)
	private ParameterService<Parameter> parameterService;
	@Autowired(required=false)
	private CollectionDistributionService collectionDistributionService;
	@Autowired(required=false)
	private UserService userService;
	@Autowired(required=false)
	private ProcessService processService;
	@Autowired(required=false)
	private CollectionResultService collectionResultService;
	@Autowired
	BpmTaskDao bpmTaskDao;

	
	/**
	 * 催收基本数据的初始化，每天数据的同步更新处理
	 * 执行时间要在计算完罚息和滞纳金之后
	 * @throws Exception 
	 * 
	 */
	@Transactional
	public void collectionBasicIni() throws Exception {
		
		//无逾期保留一个账龄后从新处理催收渠道，继续无逾期则渠道为0
		Map<String, Object> map3=new HashMap<String, Object>();
		map3.put("applyState", "0");
		map3.put("isFinish", "N");
		map3.put("isLate", "N");
		List<CollectionBase> listBases3=collectionBaseService.queryListOfWay(map3);
		for (CollectionBase cBase : listBases3) {
			//判断保留时间是否到期
			if (0==cBase.getLateAge()) {//未逾期
				int n=0;
				if (cBase.getKeepDate()!=null) {
					n=DateUtils.compareDate(DateUtils.getDateNow(), cBase.getKeepDate());
				}
				
				if (cBase.getKeepDate()==null||n>=0) {
					cBase.setCollectionWayCur("0");
					cBase.setCollectionUid("");	
					cBase.setDistributionState("N");
					//处理分配表
					
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", cBase.getContractNo());
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
					
					for (CollectionDistribution collectionDistribution : listDistributions) {
						collectionDistribution.setIsCur("N");
						collectionDistributionService.update(collectionDistribution);	
					}
				}
			}
		}
		
		//获取所有的逾期数据
		Map<String, Object> mapTmp=new HashMap<String, Object>();
		List<LoanRepayPlan> lateList=loanRepayPlanService.queryListLateStatistics(mapTmp);
		for (int i = 0; i < lateList.size(); i++) {
			
			LoanRepayPlan loanRepayPlan=lateList.get(i);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("contractNo", loanRepayPlan.getContractNo());
			List<CollectionBase> listBases=collectionBaseService.queryList(map);
			//获取合同的基本信息
			Contract contract =new Contract();
//			Map<String, Object> mapCon=new HashMap<String, Object>();
//			mapCon.put("contractNo", loanRepayPlan.getContractNo());
			List<Contract> listContracts=contractService.queryList(map);
			if (listContracts==null||listContracts.size()==0) {
				continue;
			}
			contract=listContracts.get(0);
			
			//获取逾期的数据，账龄，逾期天数
			
			Map<String, Object> mapDays=new HashMap<String, Object>();
			mapDays.put("contractNo", loanRepayPlan.getContractNo());
		
			List<LoanRepayPlan> listDays=loanRepayPlanService.queryListLateByNo(mapDays);
			
			int lateDays=DateUtils.getDayRange(listDays.get(0).getRepaymentDate(), DateUtils.getDateNow());
			//获取当前期的有关信息
			List<LoanRepayPlan> listCur=loanRepayPlanService.queryCurInfo(mapDays);
			LoanRepayPlan planCur=new LoanRepayPlan();
			if ( listCur==null || listCur.size()==0 ) {
				//获取最后一期
				LoanRepayPlan lastRepayPlan=loanRepayPlanService.queryLastInfo(map);
				planCur=lastRepayPlan;
				planCur=lastRepayPlan;			
			}else {
				planCur=listCur.get(0);	
			}
			
			//判定是电催还是落地催
			Parameter para=parameterService.queryByParamName(CollectionConstant.COLLECTION_AGE_LIMIT);
			
			if (listBases.size()==0||listBases==null) {
				//基本表中没有记录
				CollectionBase collectionBase=new CollectionBase();
				collectionBase.setContractNo(loanRepayPlan.getContractNo());
				collectionBase.setIdType(contract.getLoanIdType());
				collectionBase.setIdNo(contract.getLoanIdNo());
				collectionBase.setCustomerName(contract.getLoanName());
				collectionBase.setCustomerTel(contract.getBankMobile());
				collectionBase.setContractAmount(contract.getLoanAmount());
				collectionBase.setProduct(contract.getLoanProduct());
				collectionBase.setLateAge(listDays.size());
				collectionBase.setLateDays(lateDays);
				collectionBase.setLoanPlatform(contract.getChannelType());
				collectionBase.setOrgId(contract.getOrgId());
				collectionBase.setRepaymentDate(loanRepayPlan.getRepaymentDate());
				collectionBase.setIsFinish("N");
				collectionBase.setIsLate("Y");
				collectionBase.setPenalty(loanRepayPlan.getPenaltyReceivable()-loanRepayPlan.getPenaltyReceived());
				collectionBase.setDefaultFee(loanRepayPlan.getDefaultReceivable()-loanRepayPlan.getDefaultReceived());
				collectionBase.setDelay(loanRepayPlan.getDelayReceivable()-loanRepayPlan.getDelayReceived());
				collectionBase.setSreviceFee(loanRepayPlan.getSreviceFeeReceivable()-loanRepayPlan.getSreviceFeeReceived());
				collectionBase.setPrincipal(loanRepayPlan.getPrincipalReceivable()-loanRepayPlan.getPrincipalReceived());
				collectionBase.setInterest(loanRepayPlan.getInterestReceivable()-loanRepayPlan.getInterestReceived());
				collectionBase.setReduce(loanRepayPlan.getPenaltyReduce()+loanRepayPlan.getDelayReduce());
				collectionBase.setMonthAmountAll(loanRepayPlan.getPrincipalReceivable()+loanRepayPlan.getInterestReceivable());
				//计算总额
				double dbAmount=collectionBase.getPenalty()+collectionBase.getDefaultFee()+collectionBase.getDelay()+collectionBase.getSreviceFee()+collectionBase.getPrincipal()+collectionBase.getInterest()-collectionBase.getReduce();
				collectionBase.setAmountAll(dbAmount);
				//计算期数
				int periodAll=DateUtils.getRoundUpMonthRange(loanRepayPlan.getStartDate(), loanRepayPlan.getEndDate());
				collectionBase.setPeriodAll(periodAll);
				collectionBase.setPeriodCur(planCur.getPeriodNum());
				collectionBase.setPeriodLateHis(listDays.size());
				collectionBase.setPeriodFinish(planCur.getPeriodNum()-listDays.size()-1);
				
				collectionBase.setDistributionState("N");
				//判定是电催还是落地催
				int ageLimit=Integer.valueOf(para.getParamValue());
				if (collectionBase.getLateAge()>ageLimit) {
					collectionBase.setCollectionWayCur("2");
					collectionBase.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);
				}else {
					collectionBase.setCollectionWayCur("1");
					collectionBase.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
				}
				collectionBase.setAnswerState("N");
				collectionBase.setHandleState("N");
				collectionBase.setApplyState("0");
				collectionBase.setCreateUid("sysauto");
				collectionBase.setCreateTime(new Timestamp(System.currentTimeMillis()));
				collectionBaseService.add(collectionBase);
				//流程的问题!!!
				//开启催收流程
				BpmTask bpmTask=processService.startProcessInstanceByName(CollectionConstant.COLLECTION_PROCESS_NAME,collectionBase.getContractNo() );
				bpmTask.setOperator(BpmConstants.SYSTEM_SYS);
				String[] roleNames = null;
				if ("1".equals(collectionBase.getCollectionWayCur())) {
					roleNames = new String[]{CollectionConstant.COLLECTION_ROLE_PHONE_DCZG};// "电话催收主管
					List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(null,roleNames,null);
					String strProcessor="";
					if (listUsers!=null&&listUsers.size()!=0) {
						strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
					}
					processService.goNext(bpmTask,CollectionConstant.COLLECTION_STATE_PHONE_WAIT, strProcessor);// "逾期待分配"
				}else {
					roleNames = new String[]{CollectionConstant.COLLECTION_ROLE_VISIT_LDZG};//"落地催收主管
					List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(collectionBase.getOrgId(),roleNames,null);
					String strProcessor="";
					if (listUsers!=null&&listUsers.size()!=0) {
						strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
					}
					processService.goNext(bpmTask,CollectionConstant.COLLECTION_STATE_VISIT_WAIT, strProcessor);// "落地催收待分配"
				}
				
				continue;
			}
			//更新现有的数据
			CollectionBase cBase=listBases.get(0);
			cBase.setLateAge(listDays.size());
			cBase.setLateDays(lateDays);
			//cBase.setIsLate("Y");
			cBase.setRepaymentDate(loanRepayPlan.getRepaymentDate());
			cBase.setPenalty(loanRepayPlan.getPenaltyReceivable()-loanRepayPlan.getPenaltyReceived());
			cBase.setDefaultFee(loanRepayPlan.getDefaultReceivable()-loanRepayPlan.getDefaultReceived());
			cBase.setDelay(loanRepayPlan.getDelayReceivable()-loanRepayPlan.getDelayReceived());
			cBase.setSreviceFee(loanRepayPlan.getSreviceFeeReceivable()-loanRepayPlan.getSreviceFeeReceived());
			cBase.setPrincipal(loanRepayPlan.getPrincipalReceivable()-loanRepayPlan.getPrincipalReceived());
			cBase.setInterest(loanRepayPlan.getInterestReceivable()-loanRepayPlan.getInterestReceived());
			cBase.setReduce(loanRepayPlan.getPenaltyReduce()+loanRepayPlan.getDelayReduce());
			cBase.setMonthAmountAll(loanRepayPlan.getPrincipalReceivable()+loanRepayPlan.getInterestReceivable());
			//计算总额
			double dbAmount=cBase.getPenalty()+cBase.getDefaultFee()+cBase.getDelay()+cBase.getSreviceFee()+cBase.getPrincipal()+cBase.getInterest()-cBase.getReduce();
			cBase.setAmountAll(dbAmount);
			//计算期数
			cBase.setPeriodCur(planCur.getPeriodNum());
			//逾期期数
			int lateNum=loanRepayPlanService.queryLateHisNum(mapDays);
			cBase.setPeriodLateHis(lateNum);
			cBase.setPeriodFinish(planCur.getPeriodNum()-listDays.size()-1);
			cBase.setUpdateUid("sysauto");
			cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			
			//处理新逾期
			if ("0".equals(cBase.getCollectionWayCur())) {
				int ageLimit=Integer.valueOf(para.getParamValue());
				if (cBase.getLateAge()>ageLimit) {
					cBase.setCollectionWayCur("2");
					cBase.setDistributionState("N");
					cBase.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);
				}else {
					cBase.setCollectionWayCur("1");
					cBase.setDistributionState("N");
					cBase.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
				}
				
				//重新启动流程
				
				BpmTask bpmTask=processService.startProcessInstanceByName(CollectionConstant.COLLECTION_PROCESS_NAME,cBase.getContractNo() );
				bpmTask.setEndFlag("0");
				bpmTask.setState("开始");
				bpmTaskDao.updateBpmTask(bpmTask);
				
				bpmTask.setOperator(BpmConstants.SYSTEM_SYS);
				String[] roleNames = null;
				if ("1".equals(cBase.getCollectionWayCur())) {
					roleNames = new String[]{CollectionConstant.COLLECTION_ROLE_PHONE_DCZG}; // 电话催收主管
					List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(null,roleNames,null);
					String strProcessor="";
					if (listUsers!=null&&listUsers.size()!=0) {
						strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
					}
					processService.goNext(bpmTask, CollectionConstant.COLLECTION_STATE_PHONE_WAIT, strProcessor);
				}else {
					roleNames = new String[]{CollectionConstant.COLLECTION_ROLE_VISIT_LDZG}; //落地催收主管
					List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(cBase.getOrgId(),roleNames,null);
					String strProcessor="";
					if (listUsers!=null&&listUsers.size()!=0) {
						strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
					}
					processService.goNext(bpmTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, strProcessor);
				}
				
			}
			collectionBaseService.update(cBase);
		}
	}
	
	/**
	 * 处理催收渠道，电催升期到落地催,落地催降期,
	 * 时间在初始化数据后
	 * @throws Exception
	 */
	@Transactional
	public void handleCollectionWay () throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("collectionWayCur", "1");//电催
		map.put("applyState", "0");
		map.put("isFinish", "N");
		map.put("isLate", "Y");
		List<CollectionBase> listBases=collectionBaseService.queryList(map);
		//判定是电催还是落地催
		Parameter para=parameterService.queryByParamName(CollectionConstant.COLLECTION_AGE_LIMIT);
		int ageLimit=Integer.valueOf(para.getParamValue());
		for (CollectionBase collectionBase : listBases) {
			
			if (collectionBase.getLateAge()>ageLimit) {
				//判断是否有保留时间
				int n=0;
				if (collectionBase.getKeepDate()!=null) {
					n=DateUtils.compareDate(DateUtils.getDateNow(), collectionBase.getKeepDate());
				}
				
				if (collectionBase.getKeepDate()==null||n>0) {
					//由电催升为落地催处理
					collectionBase.setCollectionWayCur("2");
					collectionBase.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);
					collectionBase.setAnswerState("N");
					collectionBase.setPhoneSummary("0");
					collectionBase.setFollowTime(null);
					collectionBase.setOrderTime(null);
					collectionBase.setDistributionState("N");
					collectionBase.setCollectionUidCur("");
					collectionBase.setHandleState("N");
					collectionBase.setKeepDate(null);
					collectionBase.setSubmitTime(new Timestamp(System.currentTimeMillis()));
					collectionBase.setHelpFollowTime(null);
					collectionBase.setUpdateUid("sysauto");
					collectionBase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					collectionBaseService.update(collectionBase);
					//修改分配表中的数据
					
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", collectionBase.getContractNo());
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
					
					for (CollectionDistribution collectionDistribution : listDistributions) {
						collectionDistribution.setIsCur("N");
						collectionDistributionService.update(collectionDistribution);
						
					}
					//流程处理,
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, collectionBase.getContractNo());
					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						List<User> listUsers = userService.getUserByOrgAndRoleAndDepart(collectionBase.getOrgId(),
								new String[] { CollectionConstant.COLLECTION_ROLE_VISIT_LDZG }, null);
						if (listUsers!=null&&listUsers.size()!=0) {
							String strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
							processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, strProcessor);
						}else {
							processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, "");
						}	
					}
				}
			}
		}
		//////////////////////落地催降期处理
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("collectionWayCur", "2");//落地催
		map2.put("applyState", "0");
		map2.put("isFinish", "N");
		map2.put("isLate", "Y");
		List<CollectionBase> listBases2=collectionBaseService.queryList(map2);
		for (CollectionBase collectionBase : listBases2) {
			if (collectionBase.getLateAge()<=ageLimit) {
				//判断是否有保留时间
				int n=0;
				if (collectionBase.getKeepDate()!=null) {
					n=DateUtils.compareDate(DateUtils.getDateNow(), collectionBase.getKeepDate());
				}
				
				if (collectionBase.getKeepDate()==null||n>0) {
					//没有保留时间限制，可以改变催收方式
					collectionBase.setCollectionWayCur("1");
					collectionBase.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
					collectionBase.setAnswerState("N");
					collectionBase.setPhoneSummary("0");
					collectionBase.setFollowTime(null);
					collectionBase.setOrderTime(null);
					collectionBase.setDistributionState("N");
					collectionBase.setCollectionUidCur("");
					collectionBase.setHandleState("N");
					collectionBase.setSubmitTime(new Timestamp(System.currentTimeMillis()));
					collectionBase.setHelpFollowTime(null);
					collectionBase.setKeepDate(null);
					collectionBase.setUpdateUid("sysauto");
					collectionBase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					collectionBaseService.update(collectionBase);
					//修改分配表中的数据
					
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", collectionBase.getContractNo());
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
					
					for (CollectionDistribution collectionDistribution : listDistributions) {
						collectionDistribution.setIsCur("N");
						collectionDistribution.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						collectionDistributionService.update(collectionDistribution);
						
					}
					
					//流程处理，
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, collectionBase.getContractNo());
					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(null,new String[]{CollectionConstant.COLLECTION_ROLE_PHONE_DCZG},null);
						String strProcessor="";
						if (listUsers!=null&&listUsers.size()!=0) {
							strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
						}
						processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_PHONE_WAIT, strProcessor);
					}
				}
			}
		}
		
}
		
		
		
		/////////////////协催处理
//		Map<String, Object> mapHelp=new HashMap<String, Object>();
//		String[] states={CollectionConstant.COLLECTION_BEFORE_HELP,CollectionConstant.COLLECTION_HELP};
//		mapHelp.put("collectionWayCur", "1");//落地催
//		mapHelp.put("isFinish", "N");
//		mapHelp.put("states", states);
//		List<CollectionBase> listBasesHelp=collectionBaseService.queryList(mapHelp);
//		for (CollectionBase collectionBase : listBasesHelp) {
//			if (collectionBase.getLateAge()>ageLimit) {
//				//协催期内发生升期，成为落地催
//				
//				if (CollectionConstant.COLLECTION_BEFORE_HELP.equals(collectionBase.getState())) {
//					collectionBase.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);
//					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, collectionBase.getContractNo());
//					if (lisTasks!=null&&lisTasks.size()!=0) {
//						BpmTask curTask=lisTasks.get(0);
//						curTask.setOperator(BpmConstants.SYSTEM_SYS);
//						Map<String, Object> mapUser=new HashMap<String, Object>();
//						mapUser.put("state", "1");
//						mapUser.put("roleName", CollectionConstant.COLLECTION_ROLE_VISIT_LDZG);
//						mapUser.put("orgId", collectionBase.getOrgId());
//						List<User> listUsers=userService.queryUserByGroup(mapUser);
//						if (listUsers!=null&&listUsers.size()!=0) {
//							String strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
//							processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, strProcessor);
//						}else {
//							processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, "");
//						}
//						
//					}
//				
//				}else {
//					collectionBase.setState(CollectionConstant.COLLECTION_VISIT);
//					//更改分配表
//					Map<String, Object> mapDis=new HashMap<String, Object>();
//					mapDis.put("contractNo", collectionBase.getContractNo());
//					mapDis.put("isCur", "Y");
//					List<CollectionDistribution> listDis=collectionDistributionService.queryList(mapDis);
//					CollectionDistribution distribution=listDis.get(0);
//					distribution.setIsHelp("N");
//					collectionDistributionService.update(distribution);
//					
//					//流程
//					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, collectionBase.getContractNo());
//					if (lisTasks!=null&&lisTasks.size()!=0) {
//						BpmTask curTask=lisTasks.get(0);
//						curTask.setOperator(BpmConstants.SYSTEM_SYS);
//						
//						processService.goNext(curTask, "落地催收处理中", distribution.getCollectionUid());
//						
//					}
//				}
//				collectionBase.setCollectionWayCur("2");
//				collectionBase.setAnswerState("N");
//				collectionBase.setPhoneSummary("0");
//				collectionBase.setFollowTime(null);
//				collectionBase.setOrderTime(null);
//				collectionBase.setDistributionState("N");
//				collectionBase.setCollectionUidCur("");
//				collectionBase.setHandleState("N");
//				collectionBase.setSubmitTime(new Timestamp(System.currentTimeMillis()));
//				collectionBase.setHelpFollowTime(null);
//				collectionBase.setApplyState("0");//申请状态置为无申请
//				collectionBase.setUpdateUid("sysauto");
//				collectionBase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//				collectionBaseService.update(collectionBase);
//	
//				
//				//更改申请表
//				Map<String, Object> mapApp=new HashMap<String, Object>();
//				map.put("contractNo", collectionBase.getContractNo());
//				map.put("state", "2");
//				List<CollectionApplication> listApps=collectionApplicationService.queryList(mapApp);
//				CollectionApplication collectionApplication=listApps.get(0);
//				collectionApplication.setState("0");
//				collectionApplicationService.update(collectionApplication);
//				
//				
//			}
//		}
		

	
	/**
	 * 自动分配
	 * @throws Exception
	 */
	@Transactional
	public void autoDistribution () throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("collectionWayCur", "1");//电催
		map.put("applyState", "0");
		map.put("isFinish", "N");
		map.put("isLate", "Y");
		map.put("distributionState", "N");
		List<CollectionBase> listBases=collectionBaseService.queryListOrderByAmount(map);
		if (listBases.size()==0||listBases==null) {
			return;
		}
		//String[] states={"1"}; //状态只查询 在职  在岗
		List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(null,new String[]{CollectionConstant.COLLECTION_ROLE_PHONE_DCZY},null);//"电话催收专员
		//用户随机排序
		Collections.shuffle(listUsers);
		boolean flag=true;
		int num=0;
		int order=1;//1正 2反
		int max=listBases.size()-1;
		
		while (flag) {
			//正序
			if (order==1) {
				for (int i = 0; i < listUsers.size(); i++) {
					
					
					//执行分配
					CollectionBase collectionBase=new CollectionBase();
					CollectionDistribution distribution=new CollectionDistribution();
					collectionBase=listBases.get(num);
					collectionBase.setDistributionState("Y");
					collectionBase.setCollectionUidCur(listUsers.get(i).getLoginId());
					collectionBase.setSubmitTime(new Timestamp(System.currentTimeMillis()) );
					collectionBase.setState(CollectionConstant.COLLECTION_PHONE);
					collectionBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
					collectionBaseService.update(collectionBase);
					
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", collectionBase.getContractNo());
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
					
					for (CollectionDistribution collectionDistribution : listDistributions) {
						collectionDistribution.setIsCur("N");
						collectionDistributionService.update(collectionDistribution);	
					}
					distribution.setContractNo(collectionBase.getContractNo());
					distribution.setCollectionWay("1");
					distribution.setCollectionUid(listUsers.get(i).getLoginId());
					distribution.setIsCur("Y");
					distribution.setIsDone("N");
					distribution.setIsHelp("N");
					distribution.setState("1");
					distribution.setDistributionDate(DateUtils.getDateNow());
					distribution.setCreateTime(new Timestamp(System.currentTimeMillis()));
					distribution.setCreateUid("sysauto");
					collectionDistributionService.add(distribution);
					
					
					//流程
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, collectionBase.getContractNo());
					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_PHONE_REVIEW, listUsers.get(i).getLoginId());//"电话催收处理中"
					}
					if (num==max) {
						flag=false;
						break;
					}
					if (i==listUsers.size()-1) {
						order=2;
					}
					num++;
				}
			}
			if (order==2) {
				for (int i = listUsers.size()-1; i >=0; i--) {
					
					
					//执行分配
					CollectionBase collectionBase=new CollectionBase();
					CollectionDistribution distribution=new CollectionDistribution();
					collectionBase=listBases.get(num);
					collectionBase.setDistributionState("Y");
					collectionBase.setCollectionUidCur(listUsers.get(i).getLoginId());
					collectionBase.setSubmitTime(new Timestamp(System.currentTimeMillis()) );
					collectionBase.setState(CollectionConstant.COLLECTION_PHONE);
					collectionBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
					collectionBaseService.update(collectionBase);
					
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", collectionBase.getContractNo());
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
					
					for (CollectionDistribution collectionDistribution : listDistributions) {
						collectionDistribution.setIsCur("N");
						collectionDistributionService.update(collectionDistribution);	
					}
					distribution.setContractNo(collectionBase.getContractNo());
					distribution.setCollectionWay("1");
					distribution.setCollectionUid(listUsers.get(i).getLoginId());
					distribution.setIsCur("Y");
					distribution.setIsDone("N");
					distribution.setIsHelp("N");
					distribution.setState("1");
					distribution.setDistributionDate(DateUtils.getDateNow());
					distribution.setCreateTime(new Timestamp(System.currentTimeMillis()));
					distribution.setCreateUid("sysauto");
					collectionDistributionService.add(distribution);
					
					//流程
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, collectionBase.getContractNo());
					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						processService.goNext(curTask,  CollectionConstant.COLLECTION_STATE_PHONE_REVIEW, listUsers.get(i).getLoginId());
					}
					if (num==max) {
						flag=false;
						break;
					}
					if (i==0) {
						order=1;
					}
					num++;
				}
			}
			
		}
	}
                                                      
	/**
	 * 扣款成功后处理催收数据
	 * @throws Exception 
	 */
	@Transactional
	public void  handleCollectionAfterPayment(String contractNo) throws Exception {
		//先设置保留时间为一个账龄
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		//map.put("isLate", "Y");
		List<CollectionBase> listBases=collectionBaseService.queryList(map);
		if (listBases==null ||listBases.size()==0) {//正常还款。无逾期
			return;	
		}
		//获取逾期的数据
		List<LoanRepayPlan> listDays=loanRepayPlanService.queryListLateByNo(map);
		
		//获取当前期的有关信息
		List<LoanRepayPlan> listCur=loanRepayPlanService.queryCurInfo(map);
		LoanRepayPlan planCur=new LoanRepayPlan();
		boolean lastFlag=false;//标识是否最后一期
		if (listCur.size()!=0&&listCur!=null) {
			planCur=listCur.get(0);	
		}else {
			//获取最后一期
			LoanRepayPlan lastRepayPlan=loanRepayPlanService.queryLastInfo(map);
			planCur=lastRepayPlan;
			lastFlag=true;
		}
		CollectionBase cBase=listBases.get(0);
		
		if (listDays==null||listDays.size()==0) {//是否有逾期
			
			if ("Y".equals(cBase.getIsLate())) {
				//获取催收分配表信息
				Map<String, Object> mapDis=new HashMap<String, Object>();
				mapDis.put("contractNo", contractNo);
				mapDis.put("isCur", "Y");
				List<CollectionDistribution> listDis=collectionDistributionService.queryList(mapDis);
				int intDis=0;
				if (listDis!=null||listDis.size()>0) {
					CollectionDistribution cDistribution=listDis.get(0);
					if ("Y".equals(cDistribution.getIsDone())) {//已经添加过了催收绩效信息
						return;
					}
					intDis=cDistribution.getId();
					cDistribution.setIsDone("Y");//完成
					//如果是最后一期，合同完成，分配无效
//					if (lastFlag) {
//						cDistribution.setIsCur("N");
//					}
					cDistribution.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					collectionDistributionService.update(cDistribution);
					
					//逾期催收，记录催收绩效信息
					CollectionResult cResult=new CollectionResult();
					cResult.setDistributionId(intDis);
					cResult.setCollectionUid(cBase.getCollectionUidCur());
					cResult.setContractNo(cBase.getContractNo());
					cResult.setIdType(cBase.getIdType());
					cResult.setIdNo(cBase.getIdNo());
					cResult.setCustomerName(cBase.getCustomerName());
					cResult.setCustomerTel(cBase.getCustomerTel());
					cResult.setContractAmount(cBase.getContractAmount());
					cResult.setProduct(cBase.getProduct());
					cResult.setLateAge(cBase.getLateAge());
					cResult.setLateDays(cBase.getLateDays());
					cResult.setRepaymentDate(cBase.getRepaymentDate());
					cResult.setLoanPlatform(cBase.getLoanPlatform());
					cResult.setOrgName(cBase.getOrgId());
					cResult.setPenalty(cBase.getPenalty());
					cResult.setDefaultFee(cBase.getDefaultFee());
					cResult.setDelay(cBase.getDelay());
					cResult.setSreviceFee(cBase.getSreviceFee());
					cResult.setPrincipal(cBase.getPrincipal());
					cResult.setInterest(cBase.getInterest());
					cResult.setReduce(cBase.getReduce());
					cResult.setAmountAll(cBase.getAmountAll());
					cResult.setPeriodAll(cBase.getPeriodAll());
					cResult.setPeriodCur(cBase.getPeriodCur());
					cResult.setPeriodLateHis(cBase.getPeriodLateHis());
					cResult.setActualRepaymentDate(DateUtils.getDateNow());
					cResult.setCollectionWay(cBase.getCollectionWayCur());
					cResult.setCreateUid("sysauto");
					cResult.setCreateTime(new Timestamp(System.currentTimeMillis()));
					collectionResultService.add(cResult);
				}
				
				
			}
			if (lastFlag||"N".equals(cBase.getIsLate())) {//合同结束或者之前没有逾期，正常还款
				//
				//获取催收分配表信息
				Map<String, Object> mapDis=new HashMap<String, Object>();
				mapDis.put("contractNo", contractNo);
				mapDis.put("isCur", "Y");
				List<CollectionDistribution> listDis=collectionDistributionService.queryList(mapDis);
				for (CollectionDistribution collectionDistribution : listDis) {
					collectionDistribution.setIsCur("N");
					collectionDistribution.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					collectionDistributionService.update(collectionDistribution);
				}
				
				cBase.setKeepDate(null);//初始化
				cBase.setCollectionWayCur("0");
				cBase.setCollectionUid("");
				cBase.setDistributionState("N");
			}else {
				cBase.setKeepDate(planCur.getRepaymentDate());//保留一个账龄
			}
			
			cBase.setIsLate("N");
			//在电催处理中和落地崔处理中,落地催待分配可以改为催收完成显示，其他的不变
			if ("0".equals(cBase.getApplyState())) {
				cBase.setState(CollectionConstant.COLLECTION_FINISH);//催收完成
				//流程
				List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,contractNo);
				if (lisTasks!=null&&lisTasks.size()!=0) {
					BpmTask curTask=lisTasks.get(0);
					curTask.setOperator(BpmConstants.SYSTEM_SYS);
					processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_END,BpmConstants.SYSTEM_SYS);//"结束"
				}
			}
			cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
			collectionBaseService.update(cBase);
			
		}else {
			//对比保留时间，强制落地催只有全部催回来才能正常
			int n=0;
			if (cBase.getKeepDate()!=null) {
				n=DateUtils.compareDate(planCur.getRepaymentDate(), cBase.getKeepDate());
			}
			
			if (cBase.getKeepDate()==null||n>0) {
				cBase.setKeepDate(planCur.getRepaymentDate());//保留一个账龄
			}
			collectionBaseService.update(cBase);
		}
		
		
		//同步数据
		syncCollectionBaseData(contractNo);
		
	}
	/**
	 * 司法欺诈解除生效后进行的操作
	 * @param contractNo
	 * @throws Exception 
	 */
	@Transactional
	public void  handleCollectionAfterDischarge(String contractNo) throws Exception{
		//需要处理催收状态，所处的流程节点等
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		List<CollectionBase> listBases=collectionBaseService.queryList(map);
		
		if (listBases==null ||listBases.size()==0) {
			return;	
		}
		CollectionBase cBase=listBases.get(0);
		cBase.setApplyState("0");
		//获取逾期的数据
		List<LoanRepayPlan> listDays=loanRepayPlanService.queryListLateByNo(map);

		if (listDays==null||listDays.size()==0){//无逾期
			cBase.setIsLate("N");
			cBase.setCollectionUidCur("");
			cBase.setCollectionWayCur("0");//无催收渠道
			cBase.setKeepDate(null);
			cBase.setDistributionState("N");
			cBase.setPhoneSummary("");
			cBase.setAnswerState("N");
			cBase.setFollowTime(null);
			cBase.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
			cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
			collectionBaseService.update(cBase);
			
//			//更新分配表
			Map<String, Object> mapDis=new HashMap<String, Object>();
			mapDis.put("contractNo", contractNo);
			mapDis.put("isCur", "Y");
			List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
			
			for (CollectionDistribution collectionDistribution : listDistributions) {
				collectionDistribution.setIsCur("N");
				collectionDistributionService.update(collectionDistribution);	
			}
			//走流程，处于开始状态，合同完成，结束
			BpmTask bpmTask=processService.startProcessInstanceByName(CollectionConstant.COLLECTION_PROCESS_NAME,cBase.getContractNo() );
			bpmTask.setEndFlag("1");
			bpmTask.setState("结束");
			bpmTask.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
			bpmTaskDao.updateBpmTask(bpmTask);
			
		}else {
			//判定是电催还是落地催
			Parameter para=parameterService.queryByParamName(CollectionConstant.COLLECTION_AGE_LIMIT);
			//区分是电催还是落地催，分别走流程
			//判定是电催还是落地催
			int ageLimit=Integer.valueOf(para.getParamValue());
			if (listDays.size()>ageLimit) {//落地催
				if ("2".equals(cBase.getCollectionWayCur())) {//原来就是落地催，还是给原来的
					cBase.setKeepDate(null);
					cBase.setState(CollectionConstant.COLLECTION_VISIT);
					cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
					collectionBaseService.update(cBase);
					//走流程
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,contractNo);
					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						
						BpmTask nextTask=processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, BpmConstants.SYSTEM_SYS);
						
						nextTask.setOperator(BpmConstants.SYSTEM_SYS);
						processService.goNext(nextTask,CollectionConstant.COLLECTION_STATE_VISIT_REVIEW, cBase.getCollectionUidCur());// "落地催收处理中"
					}
				}else {//需要给落地催主管
					cBase.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);
					cBase.setCollectionWayCur("2");
					cBase.setCollectionUidCur("");
					cBase.setPhoneSummary("");
					cBase.setAnswerState("N");
					cBase.setFollowTime(null);
					cBase.setSubmitTime(new Timestamp(System.currentTimeMillis()));
					cBase.setKeepDate(null);
					cBase.setDistributionState("N");
					cBase.setHandleState("N");
					cBase.setHelpFollowTime(null);
					cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
					collectionBaseService.update(cBase);
					
					//更改分配表
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", contractNo);
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
					
					for (CollectionDistribution collectionDistribution : listDistributions) {
						collectionDistribution.setIsCur("N");
						collectionDistribution.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
						collectionDistributionService.update(collectionDistribution);	
					}
					 //流程
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, contractNo);
					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(cBase.getOrgId(),new String[]{CollectionConstant.COLLECTION_ROLE_VISIT_LDZG},null);
						if (listUsers!=null&&listUsers.size()!=0) {
							String strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
							processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, strProcessor);//"落地催收待分配"
						}else {
							processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_VISIT_WAIT, "");//"落地催收待分配"
						}	
					}
					
				}
			}else {//电催
				if ("1".equals(cBase.getCollectionWayCur())) {//从电催提交的，保持不变
					cBase.setKeepDate(null);
					cBase.setState(CollectionConstant.COLLECTION_PHONE);
					cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
					collectionBaseService.update(cBase);
					//走流程
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,contractNo);
					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						
						BpmTask nextTask=processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_PHONE_WAIT, BpmConstants.SYSTEM_SYS);
						
						nextTask.setOperator(BpmConstants.SYSTEM_SYS);
						processService.goNext(nextTask, CollectionConstant.COLLECTION_STATE_PHONE_REVIEW,  cBase.getCollectionUidCur());//"电话催收处理中"
					}
				}else {
					cBase.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
					cBase.setCollectionWayCur("1");
					cBase.setCollectionUidCur("");
					cBase.setPhoneSummary("");
					cBase.setAnswerState("N");
					cBase.setFollowTime(null);
					cBase.setSubmitTime(new Timestamp(System.currentTimeMillis()));
					cBase.setKeepDate(null);
					cBase.setDistributionState("N");
					cBase.setHandleState("N");
					cBase.setHelpFollowTime(null);
					cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
					collectionBaseService.update(cBase);
					
					//更改分配表
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", contractNo);
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
					
					for (CollectionDistribution collectionDistribution : listDistributions) {
						collectionDistribution.setIsCur("N");
						collectionDistribution.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
						collectionDistributionService.update(collectionDistribution);	
					}
					 //流程
					List<BpmTask> lisTasks=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME, contractNo);

					if (lisTasks!=null&&lisTasks.size()!=0) {
						BpmTask curTask=lisTasks.get(0);
						curTask.setOperator(BpmConstants.SYSTEM_SYS);
						List<User> listUsers=userService.getUserByOrgAndRoleAndDepart(null,new String[]{CollectionConstant.COLLECTION_ROLE_PHONE_DCZG},null);
						String strProcessor="";
						if (listUsers!=null&&listUsers.size()!=0) {
							strProcessor=listUsers.get(new Random().nextInt(listUsers.size())).getLoginId();
						}
						processService.goNext(curTask, CollectionConstant.COLLECTION_STATE_PHONE_WAIT, strProcessor);
					}
		
				}
			}
		}
		
		//手动同步数据
		syncCollectionBaseData(contractNo);
	}
	
	
	/**
	 * 同步催收基本表中的数据（其他的操作引起了变动）
	 *
	 * @throws Exception 
	 */
	@Transactional
	public void syncCollectionBaseData(String contractNo) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		List<CollectionBase> listBases=collectionBaseService.queryList(map);
		if (listBases.size()==0||listBases==null) {
			return;
		}
		//获取合同的基本信息
		Contract contract =new Contract();
		List<Contract> listContracts=contractService.queryList(map);
		if (listContracts==null||listContracts.size()==0) {
			return;
		}
		contract=listContracts.get(0);
		//获取逾期的数据
		List<LoanRepayPlan> listDays=loanRepayPlanService.queryListLateByNo(map);
		
		//获取当前期的有关信息
		List<LoanRepayPlan> listCur=loanRepayPlanService.queryCurInfo(map);
		LoanRepayPlan planCur=new LoanRepayPlan();
		
		if (listCur.size()!=0&&listCur!=null) {
			planCur=listCur.get(0);	
		}else {
			//获取最后一期
			LoanRepayPlan lastRepayPlan=loanRepayPlanService.queryLastInfo(map);
			planCur=lastRepayPlan;
			
		}
		List<LoanRepayPlan> lateList=loanRepayPlanService.queryListLateStatistics(map);
		if (lateList.size()==0||lateList==null) {//数据为空，没有逾期数据
		
			CollectionBase collectionBase=listBases.get(0);
			collectionBase.setIsLate("N");
			collectionBase.setLateAge(0);
			collectionBase.setLateDays(0);
			collectionBase.setPenalty(0);
			collectionBase.setDefaultFee(0);
			collectionBase.setDelay(0);
			collectionBase.setSreviceFee(0);
			collectionBase.setPrincipal(0);
			collectionBase.setInterest(0);
			collectionBase.setReduce(0);
			collectionBase.setMonthAmountAll(0);
			collectionBase.setAmountAll(0);
			collectionBase.setRepaymentDate(planCur.getRepaymentDate());
			collectionBase.setPeriodCur(planCur.getPeriodNum());
			collectionBase.setPeriodLateHis(listDays.size());
			collectionBase.setPeriodFinish(planCur.getPeriodNum()-listDays.size()-1);
		
			collectionBase.setAnswerState("N");
			collectionBase.setPhoneSummary("");
			collectionBase.setFollowTime(null);
//				collectionBase.setDistributionState("N");
//				collectionBase.setCollectionWayCur("0");//无催收渠道
//				collectionBase.setCollectionUidCur("");
			collectionBase.setHandleState("N");
			collectionBase.setHelpFollowTime(null);
			collectionBase.setSubmitTime(null);
//				
			collectionBase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			collectionBase.setUpdateUid("sysauto");
			if ("3".equals(contract.getState())||"4".equals(contract.getState())) {
				collectionBase.setIsFinish("Y");
			}
			//collectionBase.setState(CollectionConstant.COLLECTION_FINISH);
			collectionBaseService.update(collectionBase);
//				//更新分配表
//				Map<String, Object> mapDis=new HashMap<String, Object>();
//				mapDis.put("contractNo", collectionBase.getContractNo());
//				mapDis.put("isCur", "Y");
//				List<CollectionDistribution> listDistributions=collectionDistributionService.queryList(mapDis);
//				
//				for (CollectionDistribution collectionDistribution : listDistributions) {
//					collectionDistribution.setIsCur("N");
//					collectionDistributionService.update(collectionDistribution);	
//				}
			return;
							
		}
		//获取逾期天数
		int lateDays=DateUtils.getDayRange(listDays.get(0).getRepaymentDate(), DateUtils.getDateNow());
		LoanRepayPlan loanRepayPlan=new LoanRepayPlan();
		loanRepayPlan=lateList.get(0);
		//更新现有的数据
		CollectionBase cBase=listBases.get(0);
		
		cBase.setLateAge(listDays.size());
		cBase.setLateDays(lateDays);
		cBase.setRepaymentDate(lateList.get(0).getRepaymentDate());
		cBase.setPenalty(loanRepayPlan.getPenaltyReceivable()-loanRepayPlan.getPenaltyReceived());
		cBase.setDefaultFee(loanRepayPlan.getDefaultReceivable()-loanRepayPlan.getDefaultReceived());
		cBase.setDelay(loanRepayPlan.getDelayReceivable()-loanRepayPlan.getDelayReceived());
		cBase.setSreviceFee(loanRepayPlan.getSreviceFeeReceivable()-loanRepayPlan.getSreviceFeeReceived());
		cBase.setPrincipal(loanRepayPlan.getPrincipalReceivable()-loanRepayPlan.getPrincipalReceived());
		cBase.setInterest(loanRepayPlan.getInterestReceivable()-loanRepayPlan.getInterestReceived());
		cBase.setReduce(loanRepayPlan.getPenaltyReduce()+loanRepayPlan.getDelayReduce());
		cBase.setMonthAmountAll(loanRepayPlan.getPrincipalReceivable()+loanRepayPlan.getInterestReceivable());
		//计算总额
		double dbAmount=cBase.getPenalty()+cBase.getDefaultFee()+cBase.getDelay()+cBase.getSreviceFee()+cBase.getPrincipal()+cBase.getInterest()-cBase.getReduce();
		cBase.setAmountAll(dbAmount);
		cBase.setIsLate("Y");
		//计算期数
		cBase.setPeriodCur(planCur.getPeriodNum());
		//逾期期数
		int lateNum=loanRepayPlanService.queryLateHisNum(map);
		cBase.setPeriodLateHis(lateNum);
		cBase.setPeriodFinish(planCur.getPeriodNum()-listDays.size()-1);
		cBase.setUpdateUid("sysauto");
		cBase.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		collectionBaseService.update(cBase);
	}
	
	/**
	 * 修改催收升降期参数后执行的操作，保留一个账龄再根据新的参数进行修改
	 * @throws Exception 
	 */
	@Transactional
	public void changeAgeLimit() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isFinish", "N");
		map.put("isLate", "Y");
		List<CollectionBase> listBases=collectionBaseService.queryList(map);
		
		Map<String, Object> mapCur=new HashMap<String, Object>();
		for (CollectionBase collectionBase : listBases) {
			//获取当前期的有关信息
			mapCur.put("contractNo", collectionBase.getContractNo());//电催
			List<LoanRepayPlan> listCur=loanRepayPlanService.queryCurInfo(mapCur);
			
			Date dtKeep;
			if (listCur.size()!=0&&listCur!=null) {
				dtKeep=listCur.get(0).getRepaymentDate();
			}else {
				//获取最后一期的还款日
				List<Contract> listconContracts=contractService.queryList(mapCur);
				int num=DateUtils.getRoundUpMonthRange(listconContracts.get(0).getEndDate(), DateUtils.getDateNow());
				dtKeep=DateUtils.addMonth(listconContracts.get(0).getEndDate(), num);
			}
			//判断原来的保留日期
			int n=0;
			if (collectionBase.getKeepDate()!=null) {
				n=DateUtils.compareDate(dtKeep, collectionBase.getKeepDate());
			}
			
			if (collectionBase.getKeepDate()==null||n>0) {
				collectionBase.setKeepDate(dtKeep);
				collectionBase.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
				collectionBaseService.update(collectionBase);
			}
		}	
	}
	
	/**
	 * 实时扣款，对公还款，如果处于催收队列，进行绩效记录
	 * @param contractNo
	 * @param amount
	 * @throws Exception 
	 */
	public void addCollectionResult(String contractNo,double amount) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		List<CollectionBase> listBases=collectionBaseService.queryList(map);
		if (listBases==null||listBases.size()==0) {
			return;
		}
		CollectionBase cBase=listBases.get(0);
		//有逾期并且已经分配
		if ("Y".equals(cBase.getIsLate())&&"Y".equals(cBase.getDistributionState())) {
			if (amount<cBase.getAmountAll()&&amount>0) {//部分还款
				//保留一个账龄
				//获取当前期的有关信息
				List<LoanRepayPlan> listCur=loanRepayPlanService.queryCurInfo(map);
				LoanRepayPlan planCur=new LoanRepayPlan();
				if (listCur.size()!=0&&listCur!=null) {
					planCur=listCur.get(0);	
				}else {
					//获取最后一期
					LoanRepayPlan lastRepayPlan=loanRepayPlanService.queryLastInfo(map);
					planCur=lastRepayPlan;
				}
				cBase.setKeepDate(planCur.getRepaymentDate());
				collectionBaseService.update(cBase);
				//获取催收分配表信息
				Map<String, Object> mapDis=new HashMap<String, Object>();
				mapDis.put("contractNo", contractNo);
				mapDis.put("isCur", "Y");
				List<CollectionDistribution> listDis=collectionDistributionService.queryList(mapDis);
				int intDis=0;
				if (listDis==null||listDis.size()==0) {
					return;
				}
				
				CollectionDistribution cDistribution=listDis.get(0);
//					if ("Y".equals(cDistribution.getIsDone())) {//已经添加过了催收绩效信息
//						return;
//					}
				intDis=cDistribution.getId();
				
				//逾期催收，记录催收绩效信息
				CollectionResult cResult=new CollectionResult();
				cResult.setDistributionId(intDis);
				cResult.setCollectionUid(cBase.getCollectionUidCur());
				cResult.setContractNo(cBase.getContractNo());
				cResult.setIdType(cBase.getIdType());
				cResult.setIdNo(cBase.getIdNo());
				cResult.setCustomerName(cBase.getCustomerName());
				cResult.setCustomerTel(cBase.getCustomerTel());
				cResult.setContractAmount(cBase.getContractAmount());
				cResult.setProduct(cBase.getProduct());
				cResult.setLateAge(cBase.getLateAge());
				cResult.setLateDays(cBase.getLateDays());
				cResult.setRepaymentDate(cBase.getRepaymentDate());
				cResult.setLoanPlatform(cBase.getLoanPlatform());
				cResult.setOrgName(cBase.getOrgId());
				
				cResult.setAmountAll(amount);
				cResult.setPeriodAll(cBase.getPeriodAll());
				cResult.setPeriodCur(cBase.getPeriodCur());
				cResult.setPeriodLateHis(cBase.getPeriodLateHis());
				cResult.setActualRepaymentDate(DateUtils.getDateNow());
				cResult.setCollectionWay(cBase.getCollectionWayCur());
				cResult.setCreateUid("sysauto");
				cResult.setCreateTime(new Timestamp(System.currentTimeMillis()));
				collectionResultService.add(cResult);
				
			}else {
				handleCollectionAfterPayment(contractNo);//先记录逾期的
				//多还部分，继续记录
				if (amount>cBase.getAmountAll()) {
					//获取催收分配表信息
					Map<String, Object> mapDis=new HashMap<String, Object>();
					mapDis.put("contractNo", contractNo);
					mapDis.put("isCur", "Y");
					List<CollectionDistribution> listDis=collectionDistributionService.queryList(mapDis);
					int intDis=0;
					if (listDis==null||listDis.size()==0) {
						return;
					}
					
					CollectionDistribution cDistribution=listDis.get(0);
					
					intDis=cDistribution.getId();
					
					//逾期催收，记录催收绩效信息
					CollectionResult cResult=new CollectionResult();
					cResult.setDistributionId(intDis);
					cResult.setCollectionUid(cBase.getCollectionUidCur());
					cResult.setContractNo(cBase.getContractNo());
					cResult.setIdType(cBase.getIdType());
					cResult.setIdNo(cBase.getIdNo());
					cResult.setCustomerName(cBase.getCustomerName());
					cResult.setCustomerTel(cBase.getCustomerTel());
					cResult.setContractAmount(cBase.getContractAmount());
					cResult.setProduct(cBase.getProduct());
					cResult.setLateAge(cBase.getLateAge());
					cResult.setLateDays(cBase.getLateDays());
					cResult.setRepaymentDate(cBase.getRepaymentDate());
					cResult.setLoanPlatform(cBase.getLoanPlatform());
					cResult.setOrgName(cBase.getOrgId());
					
					cResult.setAmountAll(amount-cBase.getAmountAll());
					cResult.setPeriodAll(cBase.getPeriodAll());
					cResult.setPeriodCur(cBase.getPeriodCur());
					cResult.setPeriodLateHis(cBase.getPeriodLateHis());
					cResult.setActualRepaymentDate(DateUtils.getDateNow());
					cResult.setCollectionWay(cBase.getCollectionWayCur());
					cResult.setCreateUid("sysauto");
					cResult.setCreateTime(new Timestamp(System.currentTimeMillis()));
					collectionResultService.add(cResult);
				}
			}
			
		}
	
		//同步数据
		syncCollectionBaseData(contractNo);
	}
		
		
	
	
}
