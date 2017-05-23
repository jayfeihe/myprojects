package com.tera.rule;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tera.rule.model.RuleInfo;
import com.tera.rule.model.RuleProlessLog;
import com.tera.rule.model.common.AssignTaskBean;
import com.tera.rule.model.common.AssignUserInfoBean;
import com.tera.rule.model.credit.scorecard.CreditScoreCard;
import com.tera.rule.model.credit.scorecard.Formula;
import com.tera.rule.service.BrmsService;

public class RuleTest {

	@Test
	public void compileDtable() {
		testAssign();
//		testScoreCard();
//		testAssign1();
//		try {			
//			BrmsService.compileDtable("./src/rule/com/tera/credit/scorecard/scoreCard.xls", "doc");
//			BrmsService.compileDtable("./src/rule/com/tera/credit/scorecard/scoreLevel.xls", "doc");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		List list = new ArrayList();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(4);
//	    Random random = new Random();
//	    while (list.size()>0) {
//	    	int ii = random.nextInt(list.size());
//			System.out.println(list.get(ii));
//			list.remove(ii);
//		}

	}
	
	@Test
	public void testAssign1() {

		List<AssignUserInfoBean> assignUserInfoBeans = new ArrayList<AssignUserInfoBean>();
		AssignUserInfoBean a= new AssignUserInfoBean();
		a.setUserId("1");
		a.setOverstockDay(2);
		a.setStatus(1);
		a.setSurplus(15);
		a.setProcessed(4);
		assignUserInfoBeans.add(a);
		a= new AssignUserInfoBean();
		a.setUserId("2");
		a.setOverstockDay(2);
		a.setStatus(1);
		a.setSurplus(14);
		a.setProcessed(5);
		assignUserInfoBeans.add(a);
		a= new AssignUserInfoBean();
		a.setUserId("3");
		a.setOverstockDay(2);
		a.setStatus(1);
		a.setSurplus(15);
		a.setProcessed(5);
		assignUserInfoBeans.add(a);
		
		List<AssignTaskBean> assignTaskBeans = new ArrayList<AssignTaskBean>();
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		assignTaskBeans.add(new AssignTaskBean());
		Map<String,List> map = new HashMap<String, List>();
		map.put("assignUserInfoBeans", assignUserInfoBeans);
		map.put("assignTaskBeans", assignTaskBeans);
		// 调用分单方法
		List<Object> objs = new ArrayList<Object>();
		objs.add(map);
		
		BrmsService service = new BrmsService();
		Map<String, Object> globals = new HashMap<String, Object>();
//		Map<String, Object> ruleResult = new HashMap<String, Object>();
		List ruleResult = new ArrayList();
		globals.put("ruleResult", ruleResult);
		RuleProlessLog ruleProlessLog = new RuleProlessLog();
		globals.put("ruleProlessLog", ruleProlessLog);
		try {
			service.callRuleEnging(BrmsService
					.getClassPathInstance("com/tera/common/assign-change-set.xml"),
					globals, objs, "assignRuleFlowID");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ruleResult:"+ruleResult);
		
		// 过去规则结果
		if( ruleResult!=null && !ruleResult.isEmpty() ){
			for (Object object : ruleResult) {
				System.out.println(((AssignTaskBean)object).getUserId());
			}
		}
		System.out.println(ruleProlessLog.toString());
		List<RuleInfo> ruleInfoList = ruleProlessLog.getRuleInfoList();
		for (RuleInfo ruleInfo : ruleInfoList) {
			System.out.println(ruleInfo.toString());
		}
		System.out.println("=========================");
	}

	@Test
	public void testAssign() {

		List<AssignUserInfoBean> assignInfos = new ArrayList<AssignUserInfoBean>();
		AssignUserInfoBean a= new AssignUserInfoBean();
		a.setUserId("1");
		a.setOverstockDay(2);
		a.setStatus(1);
		a.setSurplus(14);
		a.setProcessed(4);
		assignInfos.add(a);
		a= new AssignUserInfoBean();
		a.setUserId("2");
		a.setOverstockDay(2);
		a.setStatus(1);
		a.setSurplus(14);
		a.setProcessed(3);
		assignInfos.add(a);
		a= new AssignUserInfoBean();
		a.setUserId("3");
		a.setOverstockDay(2);
		a.setStatus(1);
		a.setSurplus(15);
		a.setProcessed(5);
		assignInfos.add(a);
		
		// 调用分单方法
		List<Object> objs = new ArrayList<Object>();
		objs.add(assignInfos);
		
		Integer maxTaskNum = 15;
		objs.add(maxTaskNum);
		
		
		BrmsService service = new BrmsService();
		Map<String, Object> globals = new HashMap<String, Object>();
//		Map<String, Object> ruleResult = new HashMap<String, Object>();
		List<String> ruleResult = new ArrayList<String>();
		globals.put("ruleResult", ruleResult);
		RuleProlessLog ruleProlessLog = new RuleProlessLog();
		globals.put("ruleProlessLog", ruleProlessLog);
		try {
			service.callRuleEnging(BrmsService
					.getClassPathInstance("com/tera/common/assign-change-set.xml"),
					globals, objs, "assignRuleFlowID");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ruleResult:"+ruleResult);
		
		// 过去规则结果
		if( ruleResult!=null && !ruleResult.isEmpty() ){
			System.out.println(ruleResult.get(0));
		}
		System.out.println(ruleProlessLog.toString());
		List<RuleInfo> ruleInfoList = ruleProlessLog.getRuleInfoList();
		for (RuleInfo ruleInfo : ruleInfoList) {
			System.out.println(ruleInfo.toString());
		}
		System.out.println("=========================");
	}

	@Test
	public void testScoreCard() {
		// TODO Auto-generated method stub
		CreditScoreCard creditScoreCard = new CreditScoreCard();
		creditScoreCard.setWorkYear(8);
		creditScoreCard.setHasChild(1);
		creditScoreCard.setHouseStatus(1);
		creditScoreCard.setSex(0);
		creditScoreCard.setCorporate(1);
		creditScoreCard.setIsMarried(1);
		List<Object> objs = new ArrayList<Object>();
		objs.add(creditScoreCard);
		Formula formula = new Formula();
		objs.add(formula);
		BrmsService service = new BrmsService();
		Map<String, Object> globals = new HashMap<String, Object>();
		RuleProlessLog ruleProlessLog = new RuleProlessLog();
		globals.put("ruleProlessLog", ruleProlessLog);
		try {
			service.callRuleEnging(
					BrmsService
							.getClassPathInstance("com/tera/credit/scorecard/scorecard-change-set.xml"),
							globals, objs, "scoreCardRuleFlowID");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(formula.getScore()+"    "+formula.getLevel());
		System.out.println(ruleProlessLog.toString());
		List<RuleInfo> ruleInfoList = ruleProlessLog.getRuleInfoList();
		for (RuleInfo ruleInfo : ruleInfoList) {
			System.out.println(ruleInfo.toString());
		}
		System.out.println("=========================");
//		System.out.println(creditScoreCard.toString());
//		List<String> ruleRepList = creditScoreCard.getResults();
//		if(ruleRepList!=null&&!ruleRepList.isEmpty()){
//			System.out.println(ruleRepList.get(0));
//			System.out.println(ruleRepList.get(1));
//		}
	}
}
