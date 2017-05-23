package com.tera.report.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.credit.model.ApprovalStatisticsBean;

public interface ApprovalStatisticsDao {

	List<ApprovalStatisticsBean> queryStatisticsList(Map<String, Object> map);
}
