package com.tera.collection.judicial.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.constant.Constants;
import com.tera.collection.judicial.dao.CollectionJudicialDao;
import com.tera.collection.judicial.model.CollectionApplication;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.phone.service.CollectionDistributionService;
import com.tera.collection.reduce.service.RemissionHandlerService;
import com.tera.sys.model.Org;
import com.tera.sys.model.Parameter;
import com.tera.sys.model.User;
import com.tera.sys.service.ParameterService;
import com.tera.sys.service.UserService;
import com.tera.util.DateUtils;
import com.tera.util.StringUtils;


/**
 * 
 * 司法复核服务类 <br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-6-11<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionReviewService")
public class CollectionReviewService {
	// 自动注入
	@Autowired
	private CollectionBaseService collectionBaseService;
	@Autowired
	private RemissionHandlerService remissionHandlerService;
	@Autowired
	private CollectionApplicationService collectionApplicationService;
	@Autowired //自动注入
	private ProcessService processService;
	@Autowired
	private UserService userService;
	@Autowired
	private ParameterService<Parameter> parameterService;
	@Autowired
	private CollectionDistributionService collectionDistributionService;
	

	@Autowired(required=false)
	private CollectionJudicialDao collectionJudicialDao;
	

	public CollectionJudicial queryBuId(Object object){
		return collectionJudicialDao.queryById(object);
	}
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
	 * 司法复核操作
	 * @param Judicial
	 */
	@Transactional
	public void creditReviewSave(CollectionJudicial Judicial,String loginId,Org sessionOrg)throws Exception{
		
		CollectionBase base=new CollectionBase();
		CollectionApplication app=new CollectionApplication();
		if(Judicial.getCheckResult().equals("1")){//1 通过
			//自动获取审批节点的审批人uid
			String approvalUid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_JUDICIAL_SFSPZY, "86");//86 总公司
			
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setState(CollectionConstant.JUDICIAL_APPROVAL);//状态标识  司法审批中
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			base.setUpdateUid(loginId);//修改人
			collectionBaseService.update(base);
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());
			queryMap.put("state", Judicial.getState());
			queryMap.put("applyType", "1");//申请类型  1司法
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
			app.setState(Constants.APPROVAL);//状态  3 司法审批中
			collectionApplicationService.update(app);
			
			//更新bmpTask表
		List<BpmTask>  bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
		if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
			//司法复核 走到下一个节点
			BpmTask bpm=bpms.get(0);
			bpm.setVariable("logContent1", "复核通过");
			bpm.setVariable("logContent2", Judicial.getCheckText());
			bpm.setOperator(loginId);
			processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_JUDICIAL_EXAMINE,approvalUid);
		}
			
		}else if(Judicial.getCheckResult().equals("2")){//2 否决
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());
			queryMap.put("state", Judicial.getState());
			queryMap.put("applyType", "1");//申请类型  1司法
			app=collectionApplicationService.queryList(queryMap).get(0);
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
				base.setState(CollectionConstant.COLLECTION_FINISH);
				base.setCollectionWayCur("0");//催收渠道 
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
			}
			if(base.getIsLate().equals("N") && base.getIsFinish().equals("N")){//isFinish N 未结清 isLate N 未逾期  单证进入逾期待分配
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
			}
			if(base.getIsLate().equals("Y") && base.getIsFinish().equals("N")){// isFinish N 未结清  isLate Y 已逾期 单证回退给原催收人
				String uid=base.getCollectionUidCur();//Base表的当前处理人
				User uidUser =userService.getUser(uid);
				
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
							uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_PHONE_DCZY,"");
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
					//司法复核 不通过 节点跳到催收节点下
					BpmTask bpm=bpms.get(0);
					bpm.setVariable("logContent1", "复核退回");
					bpm.setVariable("logContent2", Judicial.getCheckText());
					bpm.setOperator(loginId);
					processService.goNext(bpm,state,uid);
				}
			}
			
			
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
