package com.tera.collection.reduce.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.collection.reduce.dao.CollectionRemissionDetailDao;
import com.tera.collection.reduce.model.CollectionRemissionDetail;

/**
 * 
 * 减免明细记录表服务类
 * <b>功能：</b>CollectionRemissionDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:49:16<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionRemissionDetailService")
public class CollectionRemissionDetailService {

	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(CollectionRemissionDetailService.class);

	@Autowired(required=false)
    private CollectionRemissionDetailDao dao;

	@Transactional
	public void add(CollectionRemissionDetail... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollectionRemissionDetail obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CollectionRemissionDetail obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CollectionRemissionDetail obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<CollectionRemissionDetail> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CollectionRemissionDetail queryByKey(Object id) throws Exception {
		return (CollectionRemissionDetail)dao.queryByKey(id);
	}

	/**
	 * 记录一次减免明细
	 * 
	 * @author QYANZE
	 * 
	 * @param contractNo 合同编号
	 * @param loanRepayPlanId 还款计划id
	 * @param remissionId 减免申请id
	 * @param remark 备注
	 * @param state 状态
	 * @param loginId 操作人
	 * @throws Exception 
	 */
	public void recordRemissionDetail(String contractNo, int loanRepayPlanId,
			int remissionId, String loginId) throws Exception {
		CollectionRemissionDetail remissionDetail = new CollectionRemissionDetail();
		remissionDetail.setContractNo(contractNo);
		remissionDetail.setLoanRepayPlanId(loanRepayPlanId);
		remissionDetail.setRemissionId(remissionId);
		remissionDetail.setRemark("");
		remissionDetail.setState("1");
		remissionDetail.setCreateUid(loginId); 
		remissionDetail.setUpdateUid(loginId);
		this.add(remissionDetail);
	}
	
}
