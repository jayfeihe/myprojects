package com.tera.loan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.loan.dao.LoanAppContactDao;
import com.tera.loan.model.LoanAppContact;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 18:58:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanAppContactService")
public class LoanAppContactService<T> {

	private final static Logger log = Logger.getLogger(LoanAppContactService.class);

	@Autowired(required=false)
    private LoanAppContactDao<T> dao;

	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(T t)  throws Exception {
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
	
	public List<T> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}
	
	/**
	 * 得到 担保物的 联系人  appId  毕传 其他可以 为 NULL
	 * @param appId			
	 * @param contactType	类型   01 个人 ，02 机构
	 * @param collateralId	抵押物序号   
	 * @return
	 * @throws Exception
	 */
	public List<LoanAppContact> queryCollList(String appId,String contactType,String collateralId) throws Exception {
		Map<String, Object>map=new HashMap<String, Object>();
		
		if(contactType!=null &&!contactType.equals(""))
			map.put("contactType", contactType);
		if(appId!=null &&!appId.equals(""))
			map.put("appId", appId);
		if(collateralId!=null &&!collateralId.equals(""))
			map.put("collateralId", collateralId);
		
		return dao.queryCollList(map);
	}
	
	/**
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@Transactional
	public void deleteByAppIdAndCollateralSeqFlag(String appId,String collateralSeqFlag) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("collateralSeqFlag", collateralSeqFlag);
		dao.deleteByAppIdAndCollateralSeqFlag(map);
	}
	
	
}
