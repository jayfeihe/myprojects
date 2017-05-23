package com.tera.match.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.exolab.castor.types.DateTime;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.lend.model.LendApp;
import com.tera.lend.model.form.Lend2MatchQBean;
import com.tera.lend.model.form.MatchManageQBean;
import com.tera.lend.service.LendAppService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.service.LoanAppService;
import com.tera.match.dao.Lend2matchDao;
import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;
import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>Lend2matchDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 16:45:21<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("lend2matchService")
public class Lend2matchService<T> {

	private final static Logger log = Logger.getLogger(Lend2matchService.class);

	@Autowired(required=false)
    private Lend2matchDao<T> dao;
	
	@Autowired(required=false)
    private UserDao userdao;
	
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;
	
	@Autowired(required=false) //自动注入
	private Loan2matchService<Loan2match> loan2matchService;
	
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;
	@Autowired(required=false) //自动注入
	private UserService userService;
	@Autowired(required=false)
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private LoanAppService<LoanApp> loanAppService;

	@Autowired(required=false) //自动注入
	private RoleService roleService;
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
	/**
	 * 根据appid查询状态为剩余的&&类型为1记录
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public T queryBasicByKey(String appId) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lendAppId", appId);
		List<T> list=dao.queryList(map);
		if (list.size()==0||list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	   
	}
	/**
	 * 加锁处理。根据appid查询状态为剩余的&&类型为1记录
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public T queryLockBasicByKey(String appId) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lendAppId", appId);
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
	public List<T> queryLendBasicValueAutoList() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		return dao.queryBasicLockList(map);
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
	
	public int queryBpmManualMatchCount(Map<String, Object> map)throws Exception {
		String loginId = (String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
		}
		//return dao.queryBpmLendAppCount(map);
		return dao.queryLend2MatchCount(map);
	}
	
	/**
	 * 查询状态为1或者2的list
	 */
	public List<T> queryUnFinishList(){
		
		return dao.queryUnFinishList();
	}
	
	public List<T> queryBpmManualMatchList(Map<String, Object> map) throws Exception {
		String loginId=(String) map.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				map.remove("userLoginId");
			}
		}
		//return dao.queryBpmLendAppList(map);
		return dao.queryLend2MatchList(map);
	}
	
	//杨长收添加(人工撮合审批时添加，根据传过来的loan2matchId查询matchresult表中的出借申请号，同时state状态为“1”)
	public int queryManualMatchVerifyLend2MatchCount(Map<String, Object> map)throws Exception {
		
		return dao.queryManualMatchVerifyLend2MatchCount(map);
	}
	
	//杨长收添加(人工撮合审批时添加，根据传过来的loan2matchId查询matchresult表中的出借申请号，同时state状态为“1”)
	public List<T> queryManualMatchVerifyLend2MatchList(Map<String, Object> map) throws Exception {
		
		return dao.queryManualMatchVerifyLend2MatchList(map);
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
		List<Lend2match> lmList=dao.queryMatchManageList(map);
		for (Lend2match lm : lmList) {
			MatchManageQBean mqb=new MatchManageQBean();
			mqb.setId(lm.getId());
			mqb.setAppId(lm.getLendAppId());
			mqb.setType(lm.getType());
			mqb.setMatchType(lm.getMatchType());
			mqb.setAppTimeStr(lm.getAppTimeStr());
			mqb.setAmount(lm.getLendAmount());
			mqb.setProduct(lm.getLendProduct());
			mqb.setInterestRate(lm.getLendInterestRate());
			mqb.setServiceRate(lm.getLendServiceRate());
			mqb.setPeriod(lm.getLendPeriod());
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
	
	
	@Transactional
	public String matchManage(String id,String matchState,String loginId) throws Exception{
		if("lend".equals(matchState)){
			Lend2match lm=(Lend2match) dao.queryByKey(id);
			if(lm.getLockFlag()!=1){
				LendApp la=lendAppService.queryByAppId(lm.getLendAppId());
				// 人工撮合岗位 ID
				String[] roleId={"人工撮合专员"};
				List<User> uss=userService.getUserByOrgAndRoleAndDepart("86", roleId,null);
				if(uss.size()>0){
					int key= (int)(Math.random()*(uss.size()));
					String nxitUsername= uss.get(key).getName();
					List<BpmTask> taskList = processService.getProcessInstanceByBizKey(lm.getLendAppId());
					BpmTask bpmTask=bpmTask=taskList.get(0);
					bpmTask.setProcesser(loginId);
					bpmTask=processService.goNext(bpmTask,"人工撮合",nxitUsername);		//流程跳转 自动撮合

					lm.setMatchType("1");
					dao.update((T)lm);	//更新撮合队列
					la.setMatchType("1");
					lendAppService.update(la);		//更新 申请信息
					// 转人工，更新 付款 表付款信息 状态 改成 删除 状态0
					paymentService.updateByLendAppId(lm.getLendAppId(), "申请转人工");
					return "操作成功！";
				 }else{
					 return "提交失败，没有找到人工撮合专员。";
				 }
			}else{
				return "更改失败，此撮合正在处理中，请稍候处理。";
			}
		}else{
			Loan2match lm=loan2matchService.queryByKey(id);
			if(lm.getLockFlag()!=1){
				LoanApp la=loanAppService.getAppByAppId(lm.getLoanAppId()).get(0);
				
				String nxitUsername=null,chType=null,rtmsg="";
				if("0".equals(lm.getMatchType())){
					chType="人工撮合";
					String[] roleId={"人工撮合专员"};
					List<User> uss=userService.getUserByOrgAndRoleAndDepart("86", roleId,null);
					if(uss.size()>0){
						int key= (int)(Math.random()*(uss.size()));
						nxitUsername= uss.get(key).getName();
					 }else{
						
						return "提交失败，没有找到人工撮合专员。";
					 }
					lm.setMatchType("1");
					la.setMatchType("1");
				}else{
					chType="自动撮合";
					nxitUsername=BpmConstants.SYSTEM_SYS;
					lm.setMatchType("0");
					la.setMatchType("0");
				}
				List<BpmTask> taskList = processService.getProcessInstanceByBizKey(lm.getLoanAppId());
				BpmTask bpmTask=bpmTask=taskList.get(0);
				bpmTask.setProcesser(loginId);
				bpmTask=processService.goNext(bpmTask,chType,nxitUsername);		//流程跳转 自动撮合
				loan2matchService.update(lm);
				loanAppService.update(la);
				return "操作成功！";
			}else{
				return "更改失败，此撮合正在处理中，请稍候处理。";
			}
		}
	}

	public int queryDivestCount(Map<String, Object> queryMap) {
		String loginId=(String) queryMap.get("userLoginId");
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				queryMap.remove("userLoginId");
			}
			//判断是否是组长，组长可以查看 其他人的案子 以及更改待处理人
			Map<String, Object> beanMap=new HashMap<String, Object>();
			beanMap.put("loginId",loginId);
			beanMap.put("orgId",queryMap.get("orgId"));
			List<Role> loginRoles=roleService.getRoleByOrgLoginId(beanMap);
			for (Role role : loginRoles) {
				if("1".equals(role.getFlag())){
					queryMap.remove("userLoginId");
					break;
				}
			}
		}
		return lendAppService.queryDivestCount(queryMap);
	}

	public List<LendApp> queryDivestList(Map<String, Object> queryMap) {
		String loginId=(String) queryMap.get("userLoginId");
		//如果所查询用户 为 超级管理员  将能够 查看所有 人员的 申请信息
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				queryMap.remove("userLoginId");
			}
			//判断是否是组长，组长可以查看 其他人的案子 以及更改待处理人
			Map<String, Object> beanMap=new HashMap<String, Object>();
			beanMap.put("loginId",loginId);
			beanMap.put("orgId",queryMap.get("orgId"));
			List<Role> loginRoles=roleService.getRoleByOrgLoginId(beanMap);
			for (Role role : loginRoles) {
				if("1".equals(role.getFlag())){
					queryMap.remove("userLoginId");
					break;
				}
			}
		}
		return lendAppService.queryDivestList(queryMap);
	}
	
}
