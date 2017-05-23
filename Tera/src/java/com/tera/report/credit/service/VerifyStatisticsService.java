package com.tera.report.credit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.report.credit.dao.VerifyStatisticsDao;
import com.tera.report.credit.model.VerifyStatisticsBean;
import com.tera.util.MathUtils;


/**
 * 审核统计Service
 * @author QYANZE
 * @date 2015-5-15
 */
@Service("verifylStatisticsService")
public class VerifyStatisticsService {

	@Autowired(required=false)
	private VerifyStatisticsDao dao;
	
	public List<VerifyStatisticsBean> queryStatisticsList(Map<String, Object> map) {
		List<VerifyStatisticsBean> statisticsList = dao.queryStatisticsList(map);
		if (statisticsList != null && statisticsList.size() > 0) {
			for (VerifyStatisticsBean tmpBean : statisticsList) {
				tmpBean.setLoanAmount(MathUtils.div(tmpBean.getLoanAmount(), 10000));
			}
		}
		return statisticsList;
	}
}
