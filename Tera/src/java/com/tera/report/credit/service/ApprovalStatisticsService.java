package com.tera.report.credit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.report.credit.dao.ApprovalStatisticsDao;
import com.tera.report.credit.model.ApprovalStatisticsBean;


/**
 * 审批统计Service
 * @author QYANZE
 * @date 2015-5-15
 */
@Service("approvalStatisticsService")
public class ApprovalStatisticsService {

	@Autowired(required=false)
	private ApprovalStatisticsDao dao;
	
	public List<ApprovalStatisticsBean> queryStatisticsList(Map<String, Object> map) {
		return dao.queryStatisticsList(map);
	}
}
