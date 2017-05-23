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
import com.tera.collection.judicial.dao.CollectionApplicationDao;
import com.tera.collection.judicial.dao.CollectionJudicialDao;
import com.tera.collection.judicial.model.CollectionApplication;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.phone.service.CollectionDistributionService;
import com.tera.collection.reduce.service.RemissionHandlerService;
import com.tera.sys.model.Org;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

@Service("collectionApplicationService")
public class CollectionApplicationService {
	@Autowired
	private CollectionApplicationDao collectionApplicationDao;
	@Autowired
	private CollectionBaseService collectionBaseService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private RemissionHandlerService remissionHandlerService;
	@Autowired
	private UserService userService;
	@Autowired
	private CollectionJudicialDao collectionJudicialDao;
	@Autowired
	private CollectionDistributionService collectionDistributionService;
	
	@Transactional
	public void add(CollectionApplication obj) {
		collectionApplicationDao.add(obj);
	}

	public void update(CollectionApplication obj) {
		collectionApplicationDao.update(obj);
	}
	@Transactional
	public void updateOnlyChanged(CollectionApplication obj) {
		collectionApplicationDao.updateOnlyChanged(obj);
	}

	public void delete(Object obj) {
		collectionApplicationDao.delete(obj);
	}

	public int queryCount(Map<String, Object> map) {
		return collectionApplicationDao.queryCount(map);
	}

	public List<CollectionApplication> queryList(Map<String, Object> map) {
		return collectionApplicationDao.queryList(map);
	}

	public CollectionApplication queryByKey(Object obj) {
		return collectionApplicationDao.queryByKey(obj);
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
	 * 司法审批操作
	 * @param Judicial
	 */
	@Transactional
	public void creditReviewSave(CollectionJudicial Judicial,String loginId,Org sessionOrg)throws Exception{
		CollectionBase base=new CollectionBase();
		CollectionApplication app=new CollectionApplication();
		if(Judicial.getApprovalResult().equals("1")){//1 通过
			
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setState(CollectionConstant.JUDICIAL_PASS);//状态标识  司法认定生效
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
			queryMap.put("applyType", "1");//申请类型  1司法
			app=queryList(queryMap).get(0);
			app.setApprovalResult(Judicial.getApprovalResult());//审批结果
			app.setApprovalText(Judicial.getApprovalText());//审批意见
			app.setApprovalTime(new Timestamp(System.currentTimeMillis()));//审批时间
			app.setApprovalUid(loginId);//审批人
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.FINISH);//状态  4 司法审批通过 司法生效
			update(app);
			
			//更新bmpTask表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
			if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
				BpmTask bpm=bpms.get(0);
				bpm.setVariable("logContent1", "审批通过");
				bpm.setVariable("logContent2", Judicial.getApprovalText());
				bpm.setOperator(loginId);
				//司法复核 走到下一个节点
				processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_END,"");
			}
			
		}else if(Judicial.getApprovalResult().equals("2")){//2 退回
			
			//更新collection_application表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("contractNo", Judicial.getContractNo());//合同编号
			queryMap.put("state", Judicial.getState());//状态 
			queryMap.put("applyType", "1");//申请类型  1司法
			app=queryList(queryMap).get(0);
			app.setApprovalResult(Judicial.getApprovalResult());//审批结果
			app.setApprovalText(Judicial.getApprovalText());//审批意见
			app.setApprovalTime(new Timestamp(System.currentTimeMillis()));//审批时间
			app.setApprovalUid(loginId);//审批人
			app.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			app.setUpdateUid(loginId);//修改人
			app.setState(Constants.REVIEW);//状态  司法复核处理中
			update(app);
			
			//联合t_user表判断state, 0不可用,替换uid
			String uid=app.getCheckUid();
			User uidUser =userService.getUser(uid);
			String userState = uidUser.getState();
			if("0".equals(userState)){//离职   随机分配给复核人员（挂起不变）
				uid=remissionHandlerService.generateRandProcesser(CollectionConstant.COLLECTION_ROLE_JUDICIAL_SFFHZY, "");
			}
			
			//更新Base表信息
			base=collectionBaseService.queryByKey(Judicial.getId());
			base.setState(CollectionConstant.JUDICIAL_REVIEW);//状态标识  司法复核处理中
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			base.setUpdateUid(loginId);//修改人
			collectionBaseService.update(base);
			
			//更新bmpTask表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,Judicial.getContractNo());
			if (bpms!=null && bpms.size()>0 && Judicial.getContractNo()!=null) {
				//司法审批 不通过 节点跳到催收节点下
				BpmTask bpm=bpms.get(0);
				bpm.setVariable("logContent1", "审批退回");
				bpm.setVariable("logContent2", Judicial.getApprovalText());
				bpm.setOperator(loginId);
				processService.goNext(bpm,CollectionConstant.COLLECTION_STATE_JUDICIAL_REVIEW,uid);
			}
		}
	}
}
