package com.tera.house.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.house.dao.HouseExtDao;
import com.tera.house.model.HouseExt;

/**
 * 
 * 信用贷款申请信息扩展表服务类
 * <b>功能：</b>HouseExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:25:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseExtService")
public class HouseExtService {

	private final static Logger log = Logger.getLogger(HouseExtService.class);

	@Autowired(required=false)
    private HouseExtDao dao;

	@Transactional
	public void add(HouseExt... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseExt obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseExt obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseExt obj)  throws Exception {
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
	
	public List<HouseExt> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public HouseExt queryByKey(Object id) throws Exception {
		return (HouseExt)dao.queryByKey(id);
	}
	
}
