package com.tera.credit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.credit.dao.CreditAntifraudDao;
import com.tera.credit.model.CreditAntifraud;

/**
 * 
 * 信用贷款反欺诈表服务类
 * <b>功能：</b>CreditAntifraudDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditAntifraudService")
public class CreditAntifraudService {

	private final static Logger log = Logger.getLogger(CreditAntifraudService.class);

	@Autowired(required=false)
    private CreditAntifraudDao dao;

	@Transactional
	public void add(CreditAntifraud... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditAntifraud obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditAntifraud obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditAntifraud obj)  throws Exception {
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
	
	public List<CreditAntifraud> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditAntifraud queryByKey(Object id) throws Exception {
		return (CreditAntifraud)dao.queryByKey(id);
	}
	
	/**
	 * 根据appId和state查找最新的欺诈审核信息
	 * @param appId
	 * @param state 1:未解决,2:已解决
	 * @return
	 */
	public CreditAntifraud queryLatestByAppId(String appId,String state) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("appId", appId);
		map.put("state", state);
		return dao.queryLatestByAppId(map);
	}
}
