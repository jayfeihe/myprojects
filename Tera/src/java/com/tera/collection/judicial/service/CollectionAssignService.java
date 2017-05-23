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
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.sys.model.Org;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.service.SysLogService;


@Service("collectionAssignService")
public class CollectionAssignService {
	
	@Autowired(required=false)
	private CollectionJudicialDao collectionJudicialDao;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private CollectionApplicationService collectionApplicationService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private CollectionBaseService collectionBaseService;
	/**
	 * 司法条数查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	 
	public int queryCount(Map<String, Object> map)throws Exception {
		return collectionJudicialDao.queryCount(map);
	}
	/**
	 * 司法条件查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CollectionJudicial> queryList(Map<String, Object> map) throws Exception {
		return collectionJudicialDao.queryList(map);
	}
	/**
	 * 司法分单处理
	 * @param contractNos
	 * @param user
	 * @param org
	 * @param ipAddress
	 * @param newprocesser
	 * @throws Exception
	 */
	@Transactional
	public void assignSave(String[] contractNos, User user,Org org,String ipAddress, String newprocesser)throws Exception{
		for (String contractNo : contractNos) { //contractNos勾选的合同号数组
			
			Map<String, Object> updateMap=new HashMap<String, Object>();
			updateMap.put("contractNo", contractNo);
			
			//对司法流程表进行操作（t_collection_application） 更新状态、分配状态、复核人、修改人、修改时间 
			List<CollectionApplication> colApps=collectionApplicationService.queryList(updateMap);
			CollectionApplication colapp=colApps.get(0);
			
			
			//对催收 Base表进行修改(t_collection_base)   更新 状态标识、当前处理人、修改人、修改时间
			List<CollectionBase> colBases=collectionBaseService.queryList(updateMap);
			CollectionBase base=colBases.get(0);
			if(colapp.getDistributionState().equals("0")){//未分配
				base.setState(CollectionConstant.JUDICIAL_REVIEW);// 司法复核处理中
			}
			base.setUpdateUid(user.getLoginId());//修改人 
			base.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间
			collectionBaseService.update(base);
			//更新BPMTask表，添加syslog表，添加bmpLog表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,contractNo);//通过业务key 查询流程实例 操作bmp表
			if (bpms!=null && bpms.size()>0 && contractNo!=null) {
				//流程实例 中的待处理人
				String operator = null != bpms.get(0).getProcesser() ? bpms.get(0).getProcesser() : "";//上一处理人
				if(!"".equals(operator)){
					operator = "由" + operator;
				}
				//记录用户操作  到t_syslog表
				this.sysLogService.addSysLog(new SysLog(ipAddress, user, org.getOrgId(), "司法分单", bpms.get(0).getBizKey(), operator + "司法分单给" + newprocesser));
				//司法分配 bpms最多为1条记录
				//记录用户操作 到t_bpm_log表
				//司法待复核  走到下一个节点
				bpms.get(0).setOperator(user.getLoginId());
				if(colapp.getDistributionState().equals("0")){//未分配
					processService.goNext(bpms.get(0),CollectionConstant.COLLECTION_STATE_JUDICIAL_REVIEW,newprocesser);
				}
				if(colapp.getDistributionState().equals("1")){//已分配
					processService.reAssignTask(bpms.get(0), newprocesser);
				}
			}
			
			if(colapp.getDistributionState().equals("0")){//未分配
				colapp.setState(Constants.REVIEW);//司法复核中
				colapp.setDistributionState("1");//分配状态 1已分配
			}
			colapp.setCheckUid(newprocesser);
			colapp.setUpdateUid(user.getLoginId());
			colapp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			collectionApplicationService.update(colapp);
		}
	
	}
	
	
}
