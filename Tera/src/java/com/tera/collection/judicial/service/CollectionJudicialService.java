package com.tera.collection.judicial.service;

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
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.constant.Constants;
import com.tera.collection.judicial.model.CollectionApplication;
import com.tera.collection.judicial.model.CollectionJudicial;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.phone.service.CollectionBatchService;
import com.tera.sys.model.Org;
import com.tera.util.StringUtils;
/**
 * 
 * 司法客户服务类 <br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-6-11<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionJudicialService")
public class CollectionJudicialService {
	
	// 自动注入
	@Autowired
	BpmTaskDao bpmTaskDao;
	@Autowired
	private CollectionReviewService collectionReviewService;
	@Autowired
	private CollectionApplicationService collectionApplicationService;
	@Autowired
	private CollectionBatchService collectionBatchService;
	@Autowired
	private CollectionBaseService collectionBaseService;
	
	@Autowired
	BpmLogDao bpmLogDao;
	/**
	 * 司法解除操作
	 * @param Judicial
	 */
	@Transactional
	public void judicialSave(String id,String loginId,Org sessionOrg)throws Exception{
		
		CollectionApplication app = new CollectionApplication();
		CollectionBase base=new CollectionBase();
		//通过id 查询
		CollectionJudicial bean = new CollectionJudicial();
		if (StringUtils.isNotNullAndEmpty(id)) {
			bean.setId(id);
			bean = collectionReviewService.queryBuId(bean);
		}
		
		// 更新collection_application表
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", bean.getContractNo());
		queryMap.put("state", bean.getState());
		queryMap.put("applyType", "1");// 申请类型 1司法
		app = collectionApplicationService.queryList(queryMap).get(0);
		app.setUpdateTime(new Timestamp(System.currentTimeMillis()));// 修改时间
		app.setUpdateUid(loginId);// 修改人
		app.setState(Constants.REMOVE);// 状态 5 司法解除
		collectionApplicationService.update(app);
		
		// 更新base
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
	
}
