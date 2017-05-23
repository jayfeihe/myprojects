package com.tera.rule.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.rule.constant.Constants;
import com.tera.rule.dao.RuleInfoDao;
import com.tera.rule.dao.RuleProlessLogDao;
import com.tera.rule.model.RuleInfo;
import com.tera.rule.model.RuleProlessLog;
import com.tera.rule.model.common.AssignUserInfoBean;
import com.tera.rule.model.common.AssignTaskBean;
import com.tera.util.DateUtils;

@Service
public class AssignService {

	// 规则调用
	@Autowired(required = false)
	private BrmsService brmsService;
	// 规则日志
	@Autowired(required = false)
	private RuleProlessLogDao ruleProlessLogDao;
	// 规则执行信息
	@Autowired(required = false)
	private RuleInfoDao ruleInfoDao;

	/**
	 * 获取最合适的用户ID
	 * 用户手中未审核/审批单子数量最少的
	 * 用户手中未审核/审批单子数量相同，当日已审核/审批单子数量少的
	 * @param assignInfos  分配人员信息集合
	 * @param maxTaskNum  用户手中未审核/审批单子数量 最大值
	 * @return
	 */
	public String lowestAssign(String appno, List<AssignUserInfoBean> assignUserInfos, Integer maxTaskNum) {
		long l1 = System.currentTimeMillis();
		String userId = "";
		// 调用分单方法
		List<Object> objs = new ArrayList<Object>();
		objs.add(assignUserInfos);
		if(maxTaskNum==null){
			maxTaskNum = Constants.ASSIGN_MAXTASKNUM;
		}
		objs.add(maxTaskNum);
		Map<String, Object> globals = new HashMap<String, Object>();
		List<String> ruleResult = new ArrayList<String>();
		globals.put("ruleResult", ruleResult);
		RuleProlessLog ruleProlessLog = new RuleProlessLog();
		ruleProlessLog.setCallpoint("assign");
		globals.put("ruleProlessLog", ruleProlessLog);
		try {
			brmsService
					.callRuleEnging(
							BrmsService
									.getClassPathInstance("com/tera/common/assign-change-set.xml"),
							globals, objs, "assignRuleFlowID");
		} catch (Exception e) {
			e.printStackTrace();
			ruleProlessLog.setException("1");
			ruleProlessLog.setExceptiontype("E1");
		}
		if (ruleResult != null && !ruleResult.isEmpty()) {
			userId = ruleResult.get(0);
		}

		long l2 = System.currentTimeMillis();
		try {
			ruleProlessLog.setBegintime(DateUtils.formatDate(new Date(l1), "yyyy-MM-dd HH:mm:ss:SSS"));
			ruleProlessLog.setEndtime(DateUtils.formatDate(new Date(l2), "yyyy-MM-dd HH:mm:ss:SSS"));
			ruleProlessLog.setTimelong(l2-l1);
			ruleProlessLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			ruleProlessLogDao.add(ruleProlessLog);
			List<RuleInfo> ruleInfoList = ruleProlessLog.getRuleInfoList();
			if(ruleInfoList!=null&&!ruleInfoList.isEmpty()){
				for (RuleInfo ruleInfo : ruleInfoList) {
					ruleInfo.setLogid(ruleProlessLog.getId());
					ruleInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
					ruleInfoDao.add(ruleInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userId;
	}

	/**
	 * 分配用户任务
	 * 用户随机排序，任务按蛇形顺序分配
	 * @param assignUserInfoBeans 分配人员信息集合
	 * @param assignTaskBeans 
	 * @return
	 */
	public List<AssignTaskBean> snakeAssign(List<AssignUserInfoBean> assignUserInfoBeans,List<AssignTaskBean> assignTaskBeans) {
		long l1 = System.currentTimeMillis();
		Map<String,List> map = new HashMap<String, List>();
		map.put("assignUserInfoBeans", assignUserInfoBeans);
		map.put("assignTaskBeans", assignTaskBeans);
		// 调用分单方法
		List<Object> objs = new ArrayList<Object>();
		objs.add(map);
		Map<String, Object> globals = new HashMap<String, Object>();
		List<AssignTaskBean> ruleResult = new ArrayList<AssignTaskBean>();
		globals.put("ruleResult", ruleResult);
		RuleProlessLog ruleProlessLog = new RuleProlessLog();
		globals.put("ruleProlessLog", ruleProlessLog);
		try {
			brmsService
					.callRuleEnging(
							BrmsService
									.getClassPathInstance("com/tera/common/assign-change-set.xml"),
							globals, objs, "assignRuleFlowID");
		} catch (Exception e) {
			e.printStackTrace();
			ruleProlessLog.setException("1");
			ruleProlessLog.setExceptiontype("E1");
		}

		long l2 = System.currentTimeMillis();
		try {
			ruleProlessLog.setBegintime(DateUtils.formatDate(new Date(l1), "yyyy-MM-dd HH:mm:ss:SSS"));
			ruleProlessLog.setEndtime(DateUtils.formatDate(new Date(l2), "yyyy-MM-dd HH:mm:ss:SSS"));
			ruleProlessLog.setTimelong(l2-l1);
			ruleProlessLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			ruleProlessLogDao.add(ruleProlessLog);
			List<RuleInfo> ruleInfoList = ruleProlessLog.getRuleInfoList();
			if(ruleInfoList!=null&&!ruleInfoList.isEmpty()){
				for (RuleInfo ruleInfo : ruleInfoList) {
					ruleInfo.setLogid(ruleProlessLog.getId());
					ruleInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
					ruleInfoDao.add(ruleInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ruleResult;
	}
}
