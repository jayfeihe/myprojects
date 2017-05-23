package com.tera.car.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.BpmLogDao;
import com.tera.bpm.model.BpmLog;
import com.tera.car.dao.CarRepeatDetailDao;
import com.tera.car.model.CarRepeatDetail;

/**
 * 
 * 信用贷款申请查重信息详细表服务类
 * <b>功能：</b>CarRepeatDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-29 16:03:00<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carRepeatDetailService")
public class CarRepeatDetailService {

	private final static Logger log = Logger.getLogger(CarRepeatDetailService.class);

	@Autowired(required=false)
    private CarRepeatDetailDao dao;
	@Autowired(required=false)
	private BpmLogDao bpmLogDao;
	

	@Transactional
	public void add(CarRepeatDetail... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarRepeatDetail obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarRepeatDetail obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarRepeatDetail obj)  throws Exception {
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
	
	public List<CarRepeatDetail> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public List<CarRepeatDetail> queryListGroupBy(Map<String, Object> map) throws Exception {
		return dao.queryListGroupBy(map);
	}
	
	public CarRepeatDetail queryByKey(Object id) throws Exception {
		return (CarRepeatDetail)dao.queryByKey(id);
	}
	/**
	 *  身份证  重复查询
	 * @param appId	  当前申请ID
	 * @param idNo	 查重 证件号码
	 * @return
	 */
	public List<CarRepeatDetail> repeatQueryByIdNo(String appId,String idNo){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("idNo", idNo);
		map.put("type", "5");	//设置返回结果的 类型  5 身份证
		return dao.repeatQueryByNoId(map);
	}
	/**
	 *  联系方式  重复查询
	 * @param appId	  当前申请ID
	 * @param idNo	 查重 证件号码
	 * @return
	 */
	public List<CarRepeatDetail> repeatQueryByContact(String appId,String[] contacts){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("contacts", contacts);
		map.put("type", "4");	//设置返回结果的 类型  4 联系方式
		return dao.repeatQueryByContact(map);
	}
	
	/**
	 * 重复查询
	 * @param appId 
	 * @param type    单位名称 1,单位地址 2,居住地址 3
	 * @param key   
	 * 			address 		居住地址
	 * 			comAddress 		单位地址
	 * 			comName			单位名称
	 * @param value  需要校验的 值 
	 * @return
	 */
	public List<CarRepeatDetail> repeatQueryByKeyValue(String appId,String type,String key,String value){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		map.put(key, value);
		map.put("type", type);	//设置返回结果的 类型  4 联系方式
		return dao.repeatQueryByKeyValue(map);
	}
	
	/**
	 * 根据 APP_ID 清空 当前申请的 重复记录
	 * @param appId
	 * @throws Exception
	 */
	@Transactional
	public void deleteByAppId(String appId) throws Exception {
			dao.deleteByAppId(appId);
	}

	/**
	 * 从bpmLog获取流程状态（用于查重页面链接跳转判断）
	 * 
	 * @param bizKey
	 * @return
	 */
	public String getCurrentBpmState(String bizKey) {
		String state = "录入申请";
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("bizKey", bizKey);
		List<BpmLog> bpmLog = bpmLogDao.getBpmLog(queryMap);
		for (BpmLog tmpLog : bpmLog) {
			if (tmpLog.getState().equals("审批")) {
				state = "审批";
				return state;
			}
		}
		for (BpmLog tmpLog : bpmLog) {
			if (tmpLog.getState().equals("审核")) {
				state = "审核";
				return state;
			}
		}
		return state;
	}
	
	
}
