package com.tera.rule.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.rule.dao.RuleInfoDao;
import com.tera.rule.dao.RuleProlessLogDao;
import com.tera.rule.model.RuleInfo;
import com.tera.rule.model.RuleProlessLog;
import com.tera.rule.model.credit.scorecard.CreditScoreCard;
import com.tera.rule.model.credit.scorecard.Formula;
import com.tera.util.DateUtils;

@Service
public class ScoreCardService {

	// 规则调用
	@Autowired(required = false)
	private BrmsService brmsService;
	// 规则日志
	@Autowired(required = false)
	private RuleProlessLogDao ruleProlessLogDao;
	// 规则执行信息
	@Autowired(required = false)
	private RuleInfoDao ruleInfoDao;
	

	public CreditScoreCard creditScoreCard(CreditScoreCard creditScoreCard) {
		long l1 = System.currentTimeMillis();
//		creditScoreCard = new CreditScoreCard();
//		creditScoreCard.setWorkYear(5);
//		creditScoreCard.setHasChild(0);
//		creditScoreCard.setHouseStatus(1);
//		creditScoreCard.setSex(0);
//		creditScoreCard.setCorporate(1);
//		creditScoreCard.setIsMarried(1);
		List<Object> objs = new ArrayList<Object>();
		objs.add(creditScoreCard);
		Formula formula = new Formula();
		objs.add(formula);
		Map<String, Object> globals = new HashMap<String, Object>();
		RuleProlessLog ruleProlessLog = new RuleProlessLog();
		ruleProlessLog.setAppno(creditScoreCard.getAppno());
		ruleProlessLog.setOperator(creditScoreCard.getOperator());
		ruleProlessLog.setOrgId(creditScoreCard.getOrgId());
		ruleProlessLog.setCallpoint("creditScoreCard");
		
		globals.put("ruleProlessLog", ruleProlessLog);
		try {
			brmsService.callRuleEnging(
					BrmsService
							.getClassPathInstance("com/tera/credit/scorecard/scorecard-change-set.xml"),
							globals, objs, "scoreCardRuleFlowID");
		} catch (Exception e) {
			e.printStackTrace();
			ruleProlessLog.setException("1");
			ruleProlessLog.setExceptiontype("E1");
		}
		creditScoreCard.setScore(formula.getScore());
		creditScoreCard.setLevel(formula.getLevel());

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
		
		return creditScoreCard;
	}
}
