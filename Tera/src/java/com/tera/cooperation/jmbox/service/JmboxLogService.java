package com.tera.cooperation.jmbox.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.cooperation.jmbox.dao.JmboxLogDao;
import com.tera.cooperation.jmbox.model.JmboxLog;
import com.tera.cooperation.jmbox.model.form.DefaultCustomersInfo;

/**
 * 
 * 积木盒子交互日志表服务类
 * <b>功能：</b>JmboxLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-11-13 15:53:59<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("jmboxLogService")
public class JmboxLogService {

	private final static Logger log = Logger.getLogger(JmboxLogService.class);

	@Autowired(required=false)
    private JmboxLogDao dao;

	@Transactional
	public void add(JmboxLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(JmboxLog obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(JmboxLog obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(JmboxLog obj)  throws Exception {
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
	
	public List<JmboxLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public JmboxLog queryByKey(Object id) throws Exception {
		return dao.queryByKey(id);
	}
	
	/**
	 * 客户违约信息  JM违约 信息推送接口专用
	 * @param map
	 * @return
	 */
	public List<DefaultCustomersInfo> getDefaultCustomersList(Map<String, Object> map){
		return dao.getDefaultCustomersList(map);
	}	
	public int getDefaultCustomersCount(Map<String, Object> map){
		return dao.getDefaultCustomersCount(map);
	}
	
	
}
