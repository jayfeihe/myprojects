package com.tera.collection.outsourcing.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tera.collection.constant.Constants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.judicial.model.CollectionApplication;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.judicial.service.CollectionApplicationService;
import com.tera.collection.outsourcing.model.CollectionOutsourcing;
import com.tera.collection.outsourcing.dao.CollectionOutsourcingDao;
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


@Service("collectionOutsourcingService")
public class CollectionOutsourcingService {
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
	private CollectionDistributionService collectionDistributionService;
	@Autowired(required=false)
    private CollectionOutsourcingDao CollectionOutsourcingDao;
	
	public int queryCount(Map<String, Object> map) throws Exception {
		return CollectionOutsourcingDao.queryCount(map);
	}
	public List<CollectionOutsourcing> queryList(Map<String, Object> map) throws Exception {
		return CollectionOutsourcingDao.queryList(map);
	}
	
	public CollectionOutsourcing queryBuId(Object object){
		return CollectionOutsourcingDao.queryById(object);
	}
	/**
	 * 外包催收审核操作
	 * @param Judicial
	 */
	@Transactional
	public void creditReviewSave(CollectionJudicial Judicial,String loginId,Org sessionOrg)throws Exception{
		
		CollectionBase base=new CollectionBase();
		CollectionApplication app=new CollectionApplication();
		if(Judicial.getCheckResult().equals("1")){//1 通过
			
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setCollectionWayCur("3");//3外包催收
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			base.setUpdateUid(loginId);//修改人
			collectionBaseService.update(base);
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());
			queryMap.put("state", Judicial.getState());
			queryMap.put("applyType", "3");//申请类型  3外包催收
			app=collectionApplicationService.queryList(queryMap).get(0);
			app.setCheckUid(loginId);//复核人
			app.setCheckText(Judicial.getCheckText());//复核意见
			app.setCheckResult(Judicial.getCheckResult());//复核结论
			app.setCheckTime(new Timestamp(System.currentTimeMillis()));//复核时间
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.REVIEW);//状态  2外包审核中
			collectionApplicationService.update(app);
			
			//更新bmpTask表
		List<BpmTask>  bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_OUTSOURCING_PASS,Judicial.getContractNo());
		if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
			//司法复核 走到下一个节点
			BpmTask bpm=bpms.get(0);
			bpm.setVariable("logContent1", "审核通过");
			bpm.setVariable("logContent2", Judicial.getCheckText());
			bpm.setOperator(loginId);
			processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_OUTCOURCING_REVIEW,"");
		}
			
		}else if(Judicial.getCheckResult().equals("2")){//2 否决
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());
			queryMap.put("state", Judicial.getState());
			queryMap.put("applyType", "3");//申请类型  3外包催收
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
					bpm.setVariable("logContent1", "审核退回");
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
				String bpmStr="";
				if(base.getKeepDate()==null||datePare>0){//保留时间 失效
					base.setState(CollectionConstant.COLLECTION_BEFORE_PHONE);
					bpmStr=CollectionConstant.COLLECTION_STATE_PHONE_WAIT;
				}else{
					base.setState(CollectionConstant.COLLECTION_FINISH);
					bpmStr=CollectionConstant.COLLECTION_STATE_END;
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
					processService.goNext(bpm,bpmStr,"");
				}
			}
			if(base.getIsLate().equals("Y") && base.getIsFinish().equals("N")){// isFinish N 未结清  isLate Y 已逾期 单证回退给原催收人
				String uid=base.getCollectionUidCur();//Base表的当前处理人
				User uidUser =userService.getUser(uid);
				
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
				bpm.setVariable("logContent1", "审核退回");
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
