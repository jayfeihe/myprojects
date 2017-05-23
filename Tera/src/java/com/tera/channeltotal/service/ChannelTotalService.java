package com.tera.channeltotal.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.channeltotal.dao.ChannelTotalDao;
import com.tera.channeltotal.model.ChannelTotal;

/**
 * 
 * 渠道汇总管理表服务类
 * <b>功能：</b>ChannelTotalDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 15:35:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("channelTotalService")
public class ChannelTotalService {

	private final static Logger log = Logger.getLogger(ChannelTotalService.class);

	@Autowired(required=false)
    private ChannelTotalDao dao;

	@Transactional
	public void add(ChannelTotal... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(ChannelTotal obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(ChannelTotal obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(ChannelTotal obj)  throws Exception {
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
	
	public List<ChannelTotal> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public ChannelTotal queryByKey(Object id) throws Exception {
		return dao.queryByKey(id);
	}
	
}
