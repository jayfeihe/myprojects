package com.tera.report.service;

import com.tera.report.model.interform.RepayParam;
import com.tera.report.model.interform.ResultInfo;

public interface IOfflineRepayReportService {

	/**
	 * 获取线下公司还本计划表信息
	 * @param param
	 * @return
	 */
	ResultInfo getInfos(RepayParam param);
}