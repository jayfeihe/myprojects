package com.tera.report.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.credit.model.VerifyStatisticsBean;

public interface VerifyStatisticsDao {

	List<VerifyStatisticsBean> queryStatisticsList(Map<String, Object> map);
}
