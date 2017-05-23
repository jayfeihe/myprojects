package com.tera.collection.visit.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 
 
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.visit.dao.CollectionBaseVisitDao;
import com.tera.collection.visit.dao.CollectionDistributionVisitDao;
 
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.sys.model.User;

/**
 * 
 * 催收信息基本表服务类
 * <b>功能：</b>CollectionBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionBaseVisitService")
public class CollectionBaseVisitService {

	private final static Logger log = Logger.getLogger(CollectionBaseVisitService.class);

	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired(required=false)
    private CollectionBaseVisitDao collectionBaseDao;
	 
	@Autowired(required=false)
    private CollectionDistributionVisitDao collectionDisDao;
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return collectionBaseDao.queryCount(map);
	}
	
	public List<CollectionBase> queryList(Map<String, Object> map) throws Exception {
		return collectionBaseDao.queryList(map);
	}

	public CollectionBase queryByKey(Object id) throws Exception {
		return (CollectionBase)collectionBaseDao.queryByKey(id);
	}
	@Transactional
	public void updateVisitCollectionMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		//事务               三个方法同步
		collectionBaseDao.updateVisitCollectionMap(map);
		collectionDisDao.updateVisitDistributionMap(map);
		collectionDisDao.addVisitDistributionMap(map);
	}

	public void updateOnlyChanged(Map<String, Object> maps) {
		// TODO Auto-generated method stub
		collectionBaseDao.updateVisitCollectionMap(maps);
	}
	@Transactional
	public void updateVisitCollectionMap(String[] appIds, String newProcesser,
			User user, Map<String, Object> map) throws Exception {
		
		for (String appId : appIds)  
		{
			map.put("submitType","");//此字段再分单时不使用，需要要添加一个空值
			/*落地催主管 分单*/	
			Timestamp time=new Timestamp(new Date().getTime());			//分配时间
			map.put("distributionState","Y");					 		//base 分配状态	"Y"已分配  "N" 未分配
			map.put("updateId",user);									//base 修改人
			map.put("updateTime",time);	//base 修改时间
			CollectionBase collectionBase=this.queryByKey(appId);
			String oldState=collectionBase.getState();
			//System.out.println("============================================"+oldState+"-------------"+"3".equals(oldState));
			//如果是协催 collectionBase的当前渠道为 1 电催  2为落地催
			if(!collectionBase.getCollectionWayCur().equals(CollectionConstant.COLLECTION_WAY_CUR_VISIT)){// 协催
				map.put("state",CollectionConstant.COLLECTION_HELP);				//base 状态     协催处理中
				map.put("collectionUidCur",collectionBase.getCustomerId());			//base 当前处理人   （协催时base表中不修改）
				map.put("isHelp","Y");												//distribution 是否协催   是:"Y",否:"N";
			}
			else//落地催
			{
				if(!CollectionConstant.COLLECTION_BEFORE_VISIT.equals(oldState))//判断是否是重新分配                  oldstate不等于3 表示重新分配
				{
					map.put("redistribute_","Y");
				}
				map.put("state",CollectionConstant.COLLECTION_VISIT);				//base 状态	 落地催处理中
				map.put("collectionUidCur",newProcesser);			 				//base 当前处理人
				map.put("isHelp","N");												//distribution 是否协催   是:"Y",否:"N";
			}
			/*collectionDistribution分配表 添加一条记录*/
			map.put("contractNo",collectionBase.getContractNo());					//基础表id
			map.put("collectionWay",CollectionConstant.COLLECTION_WAY_CUR_VISIT);					//催收渠道    落地催
			map.put("collectionUid",newProcesser);			//当前催收人	（落地催分配的对象）
			map.put("isCur","Y");							//当前标识
			map.put("isDone","N");							//完结标识
			map.put("distributionDate",time);				//分配时间
			map.put("createUid",user.getLoginId());			//创建人（催收主管或具有落地催分单权限的用户）
			map.put("createTime",time);	 					//创建时间
			map.put("updateUid",user.getLoginId());			//修改
			map.put("updateTime",time);	 					//修改时间
			map.put("distributionState","Y");							//状态      Y 已分配 N 未分配
			this.updateVisitCollectionMap(map);
			map.clear();
			//更新bmpTask表
			List<BpmTask> bpms=processService.getProcessInstanceByBizKey(CollectionConstant.COLLECTION_PROCESS_NAME,appId);
			System.out.println("------------------落地催分单更新bmpTask-----------------"+bpms.size());
			
				if (bpms!=null && bpms.size()>0 && appId!=null) {
					BpmTask curTask=bpms.get(0);
					curTask.setOperator(user.getLoginId());
					//待分配到 处理中
					if(CollectionConstant.COLLECTION_BEFORE_VISIT.equals(oldState)){//分配                   3表示落地催待分配
						System.out.println("3".equals(oldState));
						if(!collectionBase.getCollectionWayCur().equals(CollectionConstant.COLLECTION_WAY_CUR_VISIT)){// 协催      
							processService.goNext(curTask,CollectionConstant.COLLECTION_STATE_HELP_REVIEW,newProcesser);
						}
						else//落地催
						{
							processService.goNext(curTask,CollectionConstant.COLLECTION_STATE_VISIT_REVIEW,newProcesser);
						}
					}
					else//重新分配
					{
						processService.reAssignTask(curTask,newProcesser);
					}
					
				 
			}
		}
		
	}
	
}
