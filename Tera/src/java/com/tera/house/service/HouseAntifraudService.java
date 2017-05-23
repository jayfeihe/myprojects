package com.tera.house.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.house.dao.HouseAntifraudDao;
import com.tera.house.model.HouseAntifraud;

/**
 * 
 * 信用贷款反欺诈表服务类
 * <b>功能：</b>HouseAntifraudDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseAntifraudService")
public class HouseAntifraudService {

	private final static Logger log = Logger.getLogger(HouseAntifraudService.class);

	@Autowired(required=false)
    private HouseAntifraudDao dao;

	@Transactional
	public void add(HouseAntifraud... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseAntifraud obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseAntifraud obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseAntifraud obj)  throws Exception {
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
	
	public List<HouseAntifraud> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public HouseAntifraud queryByKey(Object id) throws Exception {
		return (HouseAntifraud)dao.queryByKey(id);
	}
	
	/**
	 * 根据appId和state查找最新的欺诈审核信息
	 * @param appId
	 * @param state 1:未解决,2:已解决
	 * @return
	 */
	public HouseAntifraud queryLatestByAppId(String appId,String state) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("appId", appId);
		map.put("state", state);
		return dao.queryLatestByAppId(map);
	}
}
