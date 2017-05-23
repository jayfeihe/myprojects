package com.tera.collection.cheat.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.BpmLogDao;
import com.tera.bpm.dao.BpmTaskDao;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.BpmFactory;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.constant.Constants;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.judicial.dao.CollectionJudicialDao;
import com.tera.collection.judicial.model.CollectionApplication;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.judicial.service.CollectionApplicationService;
import com.tera.collection.judicial.service.CollectionReviewService;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.phone.service.CollectionBatchService;
import com.tera.collection.phone.service.CollectionDistributionService;
import com.tera.collection.reduce.service.RemissionHandlerService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.sys.model.Org;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.service.ParameterService;
import com.tera.sys.service.SysLogService;
import com.tera.sys.service.UserService;
import com.tera.util.DateUtils;
import com.tera.util.StringUtils;
@Service("collectionCheatService")
public class CollectionCheatService {
	/**
	 * bpmLogMapper
	 */
	@Autowired
	BpmLogDao bpmLogDao;
	/**
	 * bpmTaskMapper
	 */
	@Autowired
	BpmTaskDao bpmTaskDao;
	@Autowired
	private RemissionHandlerService remissionHandlerService;
	@Autowired
	private CollectionApplicationService collectionApplicationService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private CollectionBaseService collectionBaseService;
	@Autowired
	private CollectionReviewService collectionReviewService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired
	private CollectionJudicialDao collectionJudicialDao;
	@Autowired
	private UserService userService;
	@Autowired
	private CollectionBatchService collectionBatchService;
	@Autowired
	private ParameterService<Parameter> parameterService;
	@Autowired
	private CollectionDistributionService collectionDistributionService;
	
	/**
	 * 查询登陆人已处理的单证总数
	 * @param queryMap
	 * @return
	 */
	public int queryDoneCount(Map<String, Object> queryMap){
		return collectionJudicialDao.queryDoneCount(queryMap);
	}
	/**
	 * 查询登陆人已处理的单证列表
	 * @param queryMap
	 * @return
	 */
	public List<CollectionJudicial> queryDoneList(Map<String, Object> queryMap){
		return collectionJudicialDao.queryDoneList(queryMap);
	}
	
	/**
	 * 欺诈申请操作
	 * @param baseId
	 * @param collectionApplication
	 * @param loginId
	 * @throws Exception
	 */
	@Transactional
	public void applySave(String baseId,CollectionApplication collectionApplication,String loginId) throws Exception{
		//base表操作
		CollectionBase base=collectionBaseService.queryByKey(baseId);//通过ID获取Base
		base.setState(CollectionConstant.JUDICIAL_BEFORE_REVIEW);//司法待复核
		base.setApplyState("1");//申请状态:  欺诈，司法共用  初始0 无申请或者退回  1 申请中  2 申请通过
		base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
		base.setUpdateUid(loginId);//修改人
		collectionBaseService.update(base);
		
		//collectionApplication表操作
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", collectionApplication.getContractNo());
		List<CollectionApplication> appList=collectionApplicationService.queryList(map);
		collectionApplication.setState(Constants.WAIT_REVIEW);//状态 欺诈待复核
		collectionApplication.setApplyType("2");//申请类型  1 司法申请  2欺诈申请
		collectionApplication.setDistributionState("0");//分配状态  0 未分配 1 已分配
		collectionApplication.setApplyUid(loginId);//申请人
		collectionApplication.setApplyTime(new Timestamp(System.currentTimeMillis()));//申请时间
		collectionApplication.setCreateUid(loginId);//创建人
		collectionApplication.setCreateTime(new Timestamp(System.currentTimeMillis()));//创建时间
		if(appList.size()>0){//该合同历史存在，修改操作；不存在 新增操作
			collectionApplication.setId(appList.get(0).getId());
			collectionApplicationService.update(collectionApplication);
		}else{
			collectionApplicationService.add(collectionApplication);
		}
		
		//更新bmpTask表
		//自动获取欺诈主管的uid
		String uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_CHEAT_QZZG, "");//86 总公司
		List<BpmTask>  bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,collectionApplication.getContractNo());
		if (bpms!=null && bpms.size()>0 && collectionApplication.getContractNo()!=null) {
			//走到下一个节点 欺诈待复核
			BpmTask bpm=bpms.get(0);
			bpm.setOperator(loginId);
			processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_CHEAT_WAIT,uid);
		}
		
	}
	
	/**
	 * 欺诈分单操作
	 * @param contractNos
	 * @param loginId
	 * @param sessionOrg
	 * @throws Exception
	 */
	@Transactional
	public void assignSave(String[] contractNos,User user,Org org,String ipAddress,String newprocesser) throws Exception{
		for (String contractNo : contractNos) { //contractNos勾选的合同号数组
		
			Map<String, Object> updateMap=new HashMap<String, Object>();
			updateMap.put("contractNo", contractNo);
			
			//对欺诈流程表进行操作（t_collection_application） 更新状态、分配状态、复核人、修改人、修改时间 
			List<CollectionApplication> colApps=collectionApplicationService.queryList(updateMap);
			CollectionApplication colapp=colApps.get(0);
			
			
			//对催收 Base表进行修改(t_collection_base)   更新 状态标识、当前处理人、修改人、修改时间
			List<CollectionBase> colBases=collectionBaseService.queryList(updateMap);
				CollectionBase base=colBases.get(0);
			if(colapp.getDistributionState().equals("0")){
				base.setState(CollectionConstant.CHEAT_REVIEW);// 欺诈复核处理中
			}
			base.setUpdateUid(user.getLoginId());//修改人 
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			collectionBaseService.update(base);
			
			//更新BPMTask表，添加syslog表，添加bmpLog表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,contractNo);
			if (bpms!=null&&bpms.size()!=0) {
				//流程实例 中的待处理人
				String operator = null != bpms.get(0).getProcesser() ? bpms.get(0).getProcesser() : "";//上一处理人
				if(!"".equals(operator)){
					operator = "由" + operator;
				}
				//记录用户操作  到t_syslog表
				this.sysLogService.addSysLog(new SysLog(ipAddress, user, org.getOrgId(), "欺诈分单", bpms.get(0).getBizKey(), operator + "欺诈分单给" + newprocesser));
				//欺诈待复核  走到下一个节点
				bpms.get(0).setOperator(user.getLoginId());
				if(colapp.getDistributionState().equals("0")){//未分配
					processService.goNext(bpms.get(0),CollectionConstant.COLLECTION_STATE_CHEAT_REVIEW,newprocesser);
				}
				if(colapp.getDistributionState().equals("1")){//已分配
					processService.reAssignTask(bpms.get(0), newprocesser);
				}
			}
			
			if(colapp.getDistributionState().equals("0")){//当前欺诈单未分单
				colapp.setState(Constants.REVIEW);//欺诈复核中
				colapp.setDistributionState("1");//分配状态 1已分配
			}
			colapp.setCheckUid(newprocesser);
			colapp.setUpdateUid(user.getLoginId());
			colapp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			collectionApplicationService.update(colapp);
			
		}
	}
	
	
	/**
	 * 欺诈复核操作
	 * @param Judicial
	 * @param loginId
	 * @param sessionOrg
	 * @throws Exception
	 */
	@Transactional
	public void reviewSave(CollectionJudicial Judicial,String loginId,Org sessionOrg)throws Exception{
		CollectionBase base=new CollectionBase();
		CollectionApplication app=new CollectionApplication();
		if(Judicial.getCheckResult().equals("1")){//1 通过
			//自动获取审批节点的审批人uid
			String approvalUid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_CHEAT_QZSPZY, "");//86 总公司
			
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setState(CollectionConstant.CHEAT_APPROVAL);//状态标识  欺诈审批中
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			base.setUpdateUid(loginId);//修改人
			collectionBaseService.update(base);
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());
			queryMap.put("state", Judicial.getState());
			queryMap.put("applyType", "2");//申请类型 1 司法 2 欺诈
			app=collectionApplicationService.queryList(queryMap).get(0);
			app.setCheckUid(loginId);//复核人
			app.setCheckText(Judicial.getCheckText());//复核意见
			app.setCheckResult(Judicial.getCheckResult());//复核结论
			app.setCheckTime(new Timestamp(System.currentTimeMillis()));//复核时间
			if(Judicial.getApprovalUid().length()==0){//审批退回 复核后审批人不变
				app.setApprovalUid(approvalUid);//审批人
			}
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.APPROVAL);//状态  3 欺诈审批中
			collectionApplicationService.update(app);
			
			//更新bmpTask表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
			if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
				//欺诈复核 走到下一个节点
				BpmTask bpm=bpms.get(0);
				bpm.setVariable("logContent1", "复核通过");
				bpm.setVariable("logContent2", Judicial.getCheckText());
				bpm.setOperator(loginId);
				processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_CHEAT_EXAMINE,approvalUid);
			}
			
		}else if(Judicial.getCheckResult().equals("2")){//2 退回
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());
			queryMap.put("state", Judicial.getState());
			queryMap.put("applyType", "2");//申请类型  2欺诈
			app=collectionApplicationService.queryList(queryMap).get(0);
			app.setCheckUid(loginId);
			app.setCheckText(Judicial.getCheckText());//复核意见
			app.setCheckResult(Judicial.getCheckResult());//复核结论
			app.setCheckTime(new Timestamp(System.currentTimeMillis()));//复核时间
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.INVALID);//状态  0失效
			collectionApplicationService.update(app);
			
			//更新Base表信息
			String state="";
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setApplyState("0");
			
			if(base.getIsFinish().equals("Y")){//IsFinish 是否结清   Y 已结清   单证失效
				base.setApplyState("0");
				base.setState(CollectionConstant.COLLECTION_FINISH);
				base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
				base.setUpdateUid(loginId);//修改人
				collectionBaseService.update(base);
				
				//更新bmpTask表
				List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
				if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
					//司法复核 不通过 节点跳到催收节点下
					BpmTask bpm=bpms.get(0);
					bpm.setVariable("logContent1", "复核退回");
					bpm.setVariable("logContent2", Judicial.getCheckText());
					bpm.setOperator(loginId);
					processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_END,"");
				}
			}else if(base.getIsLate().equals("N") && base.getIsFinish().equals("N")){//isFinish N 未结清  isLate N 未逾期 单证进入逾期待分配
				int datePare=0;
				if(base.getKeepDate()!=null){
					datePare=DateUtils.compareDate(DateUtils.getDateNow(), base.getKeepDate());
				}
				if(base.getKeepDate()==null||datePare>0){//保留时间 失效
					base.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
				}else{
					base.setState(CollectionConstant.COLLECTION_FINISH);
				}
				base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
				base.setUpdateUid(loginId);//修改人
				collectionBaseService.update(base);
				
				//更新bmpTask表
				List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
				if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
					//司法复核 不通过 节点跳到催收节点下
					BpmTask bpm=bpms.get(0);
					bpm.setVariable("logContent1", "复核退回");
					bpm.setVariable("logContent2", Judicial.getCheckText());
					bpm.setOperator(loginId);
					processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_END,"");
				}
			}else if(base.getIsLate().equals("Y") && base.getIsFinish().equals("N")){//isFinish N 未结清  isLate Y 已逾期 单证回退给原催收人
				String uid = base.getCollectionUidCur();
				User uidUser =userService.getUser(uid);//根据uid过去User对象
				
				
				if(app.getCollectionWay().equals("1")){//app催收渠道  1 电催 2 落地催（申请时，合同的催收渠道）
					//判定是电催还是落地催
					Parameter para=parameterService.queryByParamName(CollectionConstant.COLLECTION_AGE_LIMIT);
					//判定是电催还是落地催
					int ageLimit=Integer.valueOf(para.getParamValue());
					if (base.getLateAge()>ageLimit) {
						base.setCollectionWayCur("2");
						base.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);
					}else {
						base.setCollectionWayCur("1");
						base.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
					}
					
					if(base.getCollectionWayCur().equals("1")){//base 催收渠道  1 电催  2 落地催
						//联合t_user表判断state, 0不可用,替换uid
						String userState = uidUser.getState();
						if("0".equals(userState)||"2".equals(userState)){//0离职  2挂起  随机分配给复核人员
							uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_PHONE_DCZY, "");
							base.setCollectionUidCur(uid);
						}
						base.setState(CollectionConstant.COLLECTION_PHONE);//状态标识  2电催处理中
						state=CollectionConstant.COLLECTION_STATE_PHONE_REVIEW;
					}else if(base.getCollectionWayCur().equals("2")){
						uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_VISIT_LDZG, base.getOrgId());
						base.setCollectionUidCur(uid);
						base.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);//状态标识  3落地催待分配
						base.setDistributionState("N");
						state=CollectionConstant.COLLECTION_STATE_VISIT_WAIT;
					}
				}
				if(app.getCollectionWay().equals("2")){//app催收渠道  1 电催 2 落地催（申请时，合同的催收渠道）
					//联合t_user表判断state, 0不可用,替换uid
					String userState = uidUser.getState();
					if("0".equals(userState)||"2".equals(userState)){//0离职  2挂起   随机分配给复核人员
						uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_VISIT_LDZY, base.getOrgId());
						base.setCollectionUidCur(uid);
					}
					if(StringUtils.isNotNullAndEmpty(uid)){//落地催专员不存在，分配给落地催主管
						base.setState(CollectionConstant.COLLECTION_VISIT);//状态标识  4落地催处理中
						state=CollectionConstant.COLLECTION_STATE_VISIT_REVIEW;
					}else{
						uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_VISIT_LDZG, base.getOrgId());
						base.setCollectionUidCur(uid);
						base.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);//状态标识  3落地催待分配
						base.setDistributionState("N");
						state=CollectionConstant.COLLECTION_STATE_VISIT_WAIT;
					}
				}
				base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
				base.setUpdateUid(loginId);//修改人
				collectionBaseService.update(base);
				
				//操作CollectionDistribution表
				Map<String, Object> mapDis =new HashMap<String, Object>();
				mapDis.put("contractNo", base.getContractNo());
				updateDis(mapDis, base, uid, loginId);
				
				//更新bmpTask表
				List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
				if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
					//欺诈复核 不通过 节点跳到催收节点下
					BpmTask bpm=bpms.get(0);
					bpm.setVariable("logContent1", "复核退回");
					bpm.setVariable("logContent2", Judicial.getCheckText());
					bpm.setOperator(loginId);
					processService.goNext(bpm,state,uid);
				}
			}
		}else if(Judicial.getCheckResult().equals("3")){//提交强制落地
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());
			queryMap.put("state", Judicial.getState());
			queryMap.put("applyType", "2");//申请类型  2欺诈
			app=collectionApplicationService.queryList(queryMap).get(0);
			app.setCheckUid(loginId);
			app.setCheckText(Judicial.getCheckText());//复核意见
			app.setCheckResult(Judicial.getCheckResult());//复核结论
			app.setCheckTime(new Timestamp(System.currentTimeMillis()));//复核时间
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.INVALID);//状态  0失效
			collectionApplicationService.update(app);
			
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			//自动获取落地催收主管
			String collectionUidCur=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_VISIT_LDZG, base.getOrgId());//86 总公司
			base.setCollectionUidCur(collectionUidCur);//分配给落地催收主管
			base.setState(CollectionConstant.COLLECTION_BEFORE_VISIT);//状态标识  3落地催待分配
			base.setDistributionState("N");//分配状态设置为初始化状态
			base.setCollectionWayCur("2");//当前渠道 2落地催
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			base.setUpdateUid(loginId);//修改人
			//将Contract的结束时间endDate，赋给Base的保留时间keepDate
			Map<String, Object> contractMap=new HashMap<String, Object>();
			contractMap.put("contractNo", base.getContractNo());
			List<Contract> contractList =contractService.queryList(contractMap);
			base.setKeepDate(contractList.get(0).getEndDate());
			collectionBaseService.update(base);
			
			//更新bmpTask表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
			if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
				//欺诈复核 不通过 节点跳到催收节点下
				BpmTask bpm=bpms.get(0);
				bpm.setVariable("logContent1", "提交强制落地");
				bpm.setVariable("logContent2", Judicial.getCheckText());
				bpm.setOperator(loginId);
				processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_VISIT_WAIT,collectionUidCur);
			}
		}
	}
	
	/**
	 * 欺诈审批操作
	 * @param Judicial
	 * @param loginId
	 * @param sessionOrg
	 * @throws Exception
	 */
	@Transactional
	public void approvalSave(CollectionJudicial Judicial,String loginId,Org sessionOrg)throws Exception{
		CollectionBase base=new CollectionBase();
		CollectionApplication app=new CollectionApplication();
		if(Judicial.getApprovalResult().equals("1")){//1 通过
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setState(CollectionConstant.CHEAT_PASS);//状态标识  欺诈认定生效
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			base.setUpdateUid(loginId);//修改人
			base.setCollectionWayCur("0");//当前渠道  0 无
			base.setCollectionUidCur("");//当前处理人
			base.setDistributionState("N");//分配状态
			base.setHandleState("N");//处理状态
			base.setApplyState("2");//申请状态 2申请通过
			base.setPhoneSummary("");//电催摘要
			base.setSubmitTime(null);//提交时间
			base.setKeepDate(null);//保留时间
			collectionBaseService.update(base);
			
			//操作CollectionDistribution表（催收分配表实体类）
			Map<String, Object> mapDis=new HashMap<String, Object>();
			mapDis.put("contractNo", base.getContractNo());
			List<CollectionDistribution> disList=collectionDistributionService.queryList(mapDis);//通过contractNo 查询CollectionDistribution
			for (int i = 0; i < disList.size(); i++) {
				CollectionDistribution dis=disList.get(i);
				dis.setIsCur("N");//是否当前标识 N 否
				dis.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				dis.setUpdateUid(loginId);
				collectionDistributionService.update(dis);
			}
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());//合同编号
			queryMap.put("state", Judicial.getState());//状态  
			queryMap.put("applyType", "2");//申请类型  2欺诈
			app=collectionApplicationService.queryList(queryMap).get(0);
			app.setApprovalResult(Judicial.getApprovalResult());//审批结果
			app.setApprovalText(Judicial.getApprovalText());//审批意见
			app.setApprovalTime(new Timestamp(System.currentTimeMillis()));//审批时间
			app.setApprovalUid(loginId);//审批人
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.FINISH);//状态  4 欺诈审批通过 欺诈生效
			collectionApplicationService.update(app);
			
//			更新bmpTask表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
			if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
				//欺诈审批 走到下一个节点
				BpmTask bpm=bpms.get(0);
				bpm.setVariable("logContent1", "审批通过");
				bpm.setVariable("logContent2", Judicial.getApprovalText());
				bpm.setOperator(loginId);
				processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_END,"");
			}
			
		}else if(Judicial.getApprovalResult().equals("2")){//2 退回
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());//合同编号
			queryMap.put("state", Judicial.getState());//状态 
			queryMap.put("applyType", "2");//申请类型  2欺诈
			app=collectionApplicationService.queryList(queryMap).get(0);
			app.setApprovalResult(Judicial.getApprovalResult());//审批结果
			app.setApprovalText(Judicial.getApprovalText());//审批意见
			app.setApprovalTime(new Timestamp(System.currentTimeMillis()));//审批时间
			app.setApprovalUid(loginId);//审批人
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.REVIEW);//状态  欺诈复核处理中
			collectionApplicationService.update(app);
			
			//联合t_user表判断state, 0不可用,替换uid
			String uid=app.getCheckUid();
			User uidUser =userService.getUser(uid);//根据uid过去User对象
			String userState = uidUser.getState();
			if("0".equals(userState)){//离职   随机分配给复核人员（挂起不变）
				uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_CHEAT_QZFHZY, "");
			}
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setState(CollectionConstant.CHEAT_REVIEW);//状态标识  欺诈复核处理中
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			base.setUpdateUid(loginId);//修改人
			collectionBaseService.update(base);
			
			//更新bmpTask表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
			if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
				//欺诈审批 不通过 节点跳到催收节点下
				BpmTask bpm=bpms.get(0);
				bpm.setVariable("logContent1", "审批退回");
				bpm.setVariable("logContent2", Judicial.getApprovalText());
				bpm.setOperator(loginId);
				processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_CHEAT_REVIEW,uid);
			}
		}
	}
	/**
	 * 欺诈解除操作
	 * @param Judicial
	 * @param loginId
	 * @param sessionOrg
	 * @throws Exception
	 */
	@Transactional
	public void cheatSave(String id,String loginId,Org sessionOrg)throws Exception{
		CollectionApplication app = new CollectionApplication();
		//通过id 查询
		CollectionJudicial bean = new CollectionJudicial();
		CollectionBase base=new CollectionBase();
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean.setId(id);
			bean = collectionReviewService.queryBuId(bean);
		}
		// 更新collection_application表
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", bean.getContractNo());
		queryMap.put("state", bean.getState());
		queryMap.put("applyType", "2");// 申请类型 2欺诈
		app = collectionApplicationService.queryList(queryMap).get(0);
		app.setUpdateTime(new Timestamp(System.currentTimeMillis()));// 修改时间
		app.setUpdateUid(loginId);// 修改人
		app.setState(Constants.REMOVE);// 状态 5 欺诈解除
		collectionApplicationService.update(app);
		//修改base表
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("contractNo", app.getContractNo());
		List<CollectionBase> baseList=collectionBaseService.queryList(map);
		if(baseList.size()>0){
			base=baseList.get(0);
			base.setApplyState("0");
			base.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			base.setUpdateUid(loginId);
			collectionBaseService.update(base);
		}
		
	}
	
	/**
	 * 修改CollectionDistribution表（催收分配表实体类）
	 * @param mapDis
	 * @param base
	 * @param uid
	 * @param loginId
	 * @throws Exception
	 */
	public void updateDis (Map<String,Object> mapDis,CollectionBase base,String uid,String loginId)throws Exception{
		List<CollectionDistribution> disList=collectionDistributionService.queryList(mapDis);//通过contractNo 查询CollectionDistribution
		for (int i = 0; i < disList.size(); i++) {
			CollectionDistribution dis=disList.get(i);
			dis.setIsCur("N");//是否当前标识 N 否
			dis.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			dis.setUpdateUid(loginId);
			collectionDistributionService.update(dis);
		}
		CollectionDistribution dis=new CollectionDistribution();
		dis.setContractNo(base.getContractNo());
		dis.setCollectionWay(base.getCollectionWayCur());
		dis.setCollectionUid(uid);
		dis.setIsCur("Y");
		dis.setIsHelp("N");
		dis.setIsDone("N");
		dis.setState("1");
		dis.setDistributionDate(new Timestamp(System.currentTimeMillis()));
		dis.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dis.setCreateUid(loginId);
		collectionDistributionService.add(dis);
	}
}
