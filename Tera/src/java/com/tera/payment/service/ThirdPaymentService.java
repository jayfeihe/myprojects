package com.tera.payment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.payment.dao.ThirdPaymentLogDao;
import com.tera.payment.model.ThirdPaymentLog;

/**
 * 
 * <br>
 * <b>功能：</b>ThirdPaymentLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-20 14:54:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("thirdPaymentService")
public class ThirdPaymentService {

	private final static Logger log = Logger.getLogger(ThirdPaymentService.class);

	@Autowired(required=false)
    private ThirdPaymentLogDao<ThirdPaymentLog> dao;

	@Transactional
	public void add(ThirdPaymentLog... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(ThirdPaymentLog t : ts ){
			dao.add(t);
		}
	}

	@Transactional
	public void update(ThirdPaymentLog t)  throws Exception {
		dao.update(t);
	}

	@Transactional
	public void updateOnlyChanged(ThirdPaymentLog t)  throws Exception {
		dao.updateOnlyChanged(t);
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

	public List<ThirdPaymentLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public ThirdPaymentLog queryByKey(Object id) throws Exception {
		return (ThirdPaymentLog)dao.queryByKey(id);
	}
	public List<ThirdPaymentLog> queryUnfinishedList(){
		return dao.queryUnfinishedList();
	}
	
	public ThirdPaymentLog queryByOrderNo(String orderNo) throws Exception {
		return dao.queryByOrderNo(orderNo);
	}
	/**
	 *  根据收付ID 得到  请求的 列表
	 * @param paymentId
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public List<ThirdPaymentLog> queryBypaymentId(int paymentId,String state) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("paymentId", paymentId);
		map.put("state", state);
		return dao.queryList(map);
	}
	
	


}
