package com.tera.loan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.loan.dao.LoanAppCollateralDao;
import com.tera.loan.model.LoanAppCollateral;
import com.tera.loan.model.LoanAppContact;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppCollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-07 12:18:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanAppCollateralService")
public class LoanAppCollateralService<T> {

	private final static Logger log = Logger.getLogger(LoanAppCollateralService.class);

	@Autowired(required=false)
    private LoanAppCollateralDao<T> dao;
	
	@Autowired(required=false)
    private LoanAppContactService<LoanAppContact> loanAppContactService;

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
	 * 得到申请的抵押物
	 * @param appId   申请ID 
	 * @param type    车、房、 股权
	 * @return		担保物 集合
	 * @throws Exception
	 */
	public List<LoanAppCollateral> queryListByAppId(String appId ,String type) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		List<LoanAppCollateral> collateralList= null;
		if(appId!=null&& !appId.equals("")){
			map.put("appId", appId);
			collateralList = dao.queryListByAppId(map);
		}
		if(type!=null&& !type.equals(""))
			map.put("type", type);
			
		return collateralList;
	}
	
	/**
	 * 删除抵押物 ，同时 删除 与抵押物相关的  产权人
	 * @param collId
	 * @param appId
	 * @param collateralSeqFlag
	 * @throws Exception
	 */
	@Transactional
	public void deleteAndContactS(String collId,String appId,String collateralSeqFlag) throws Exception {
		
		dao.delete(collId);
		loanAppContactService.deleteByAppIdAndCollateralSeqFlag(appId, collateralSeqFlag);
	}
	
	
	
}
