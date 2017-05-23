package com.tera.match.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.lend.model.form.Loan2MatchQBean;
import com.tera.lend.model.form.MatchManageQBean;
import com.tera.loan.model.Lending;
import com.tera.match.dao.Loan2matchDao;
import com.tera.match.model.Loan2match;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.User;

/**
 * 
 * <br>
 * <b>功能：</b>Loan2matchDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-10 09:54:58<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loan2matchService")
public class Loan2matchService<T> {

	private final static Logger log = Logger.getLogger(Loan2matchService.class);

	@Autowired(required=false)
    private Loan2matchDao<T> dao;
	
	@Autowired(required=false)
    private UserDao userdao;

	
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
	public List<T> queryLockList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}

    @Transactional
	public List<Loan2match> getLoan2matchByAppId(String loanAppId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loanAppId", loanAppId);
		return dao.getLoan2matchByAppId(map);
	}
	
	//杨长收添加
	public int queryLoan2MatchByConditionCount(Map<String, Object> map)throws Exception {
		return dao.queryLoan2MatchByConditionCount(map);
	}
	
	//杨长收添加
	public List<T> queryLoan2MatchByConditionList(Map<String, Object> map) throws Exception {
		return dao.queryLoan2MatchByConditionList(map);
	}
	
	//杨长收添加（人工撮合审批和人工撮合收费）
	public int queryManualMatchVerifyCount(Map<String, Object> map)throws Exception {
		String loginId=(String) map.get("processer");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("processer");
			}
		}
		return dao.queryManualMatchVerifyCount(map);
	}
	
	//杨长收添加（人工撮合审批和人工撮合收费）
	public List<T> queryManualMatchVerifyList(Map<String, Object> map) throws Exception {
		String loginId=(String) map.get("processer");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("processer");
			}
		}
		return dao.queryManualMatchVerifyList(map);
	}

	/**
	 * 根据appid查询记录
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public T queryBasicByKey(String appId) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loanAppId", appId);
		List<T> list=dao.queryList(map);
		if (list.size()==0||list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	   
	}
	/**
	 * 加锁查询。根据appid查询记录
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public T queryLockBasicByKey(String appId) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loanAppId", appId);
		List<T> list=dao.queryLockList(map);
		if (list.size()==0||list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	   
	}
	
	/**
	 * 获取当前列表中撮合类型为自动匹配,状态为剩余，类型为未结束，根据申请时间排序倒序
	 * @return
	 * @throws Exception
	 */
	public List<T> queryLoanBasicValueAutoList() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		return dao.queryBasicLockList(map);
	}
	
	
	
	
	/**
	 * 获取 可以转人工的 列表
	 * @param map
	 * @return
	 * @throws Exception
	 * 
	 * ssx
	 */
	public List<MatchManageQBean> queryMatchManageList(Map<String, Object> map) throws Exception {
		List<MatchManageQBean> rtList=new ArrayList<MatchManageQBean>();
		List<Loan2match> lmList=dao.queryMatchManageList(map);
		for (Loan2match lm : lmList) {
			MatchManageQBean mqb=new MatchManageQBean();
			mqb.setId(lm.getId());
			mqb.setAppId(lm.getLoanAppId());
			mqb.setType(lm.getType());
			mqb.setMatchType(lm.getMatchType());
			mqb.setAppTimeStr(lm.getAppTimeStr());
			mqb.setAmount(lm.getLoanAmount());
			mqb.setProduct(lm.getLoanProduct());
			mqb.setInterestRate(lm.getLoanInterestRate());
			mqb.setServiceRate(lm.getLoanServiceRate());
			mqb.setPeriod(lm.getLoanPeriod());
			mqb.setStartDateStr(lm.getStartDateStr());
			mqb.setEndDateStr(lm.getEndDateStr());
			mqb.setOrgId(lm.getOrgId());
			mqb.setUseage(lm.getUseage());
			mqb.setContractAmount(lm.getContractAmount());
			mqb.setContractStartDateStr(lm.getContractStartDateStr());
			mqb.setContractEndDateStr(lm.getContractEndDateStr());
			mqb.setState(lm.getState());
			mqb.setTimes(lm.getTimes());
			mqb.setLockFlag(lm.getLockFlag());
			mqb.setOperator(lm.getOperator());
			mqb.setOrgId2(lm.getOrgId2());
			mqb.setCreateTimeStr(lm.getCreateTimeStr());
			mqb.setUpdateTimeStr(lm.getUpdateTimeStr());
			rtList.add(mqb);
		}
		return rtList;
	}
	public int queryMatchManageCount(Map<String, Object> map){
		return dao.queryMatchManageCount(map);
	}
	
	
	
}
