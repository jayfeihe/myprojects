package com.greenkoo.echarts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.echarts.dao.EChartDao;
import com.greenkoo.echarts.model.form.CountBean;
import com.greenkoo.echarts.service.IEChartService;

@Service("eChartService")
public class EChartServiceImpl implements IEChartService {

	@Autowired
	private EChartDao dao;
	
	@Override
	public Integer queryMaxAdvertCount(Map<String, Object> paramMap) {
		return this.dao.queryMaxAdvertCount(paramMap);
	}

	@Override
	public Integer queryMaxCreativeCount(Map<String, Object> paramMap) {
		return this.dao.queryMaxCreativeCount(paramMap);
	}

	@Override
	public Integer queryMaxMediaCount(Map<String, Object> paramMap) {
		return this.dao.queryMaxMediaCount(paramMap);
	}
	
	@Override
	public Integer queryMaxAdvertiserCount(Map<String, Object> paramMap) {
		return this.dao.queryMaxAdvertiserCount(paramMap);
	}

	@Override
	public Integer queryMaxPropByLevel(Map<String, Object> paramMap) {
		return this.dao.queryMaxPropByLevel(paramMap);
	}

	@Override
	public Integer queryMaxAllCorrectCount(Map<String, Object> paramMap) {
		return this.dao.queryMaxAllCorrectCount(paramMap);
	}

	@Override
	public Integer queryMaxSelfCorrectCount(Map<String, Object> paramMap) {
		return this.dao.queryMaxSelfCorrectCount(paramMap);
	}

	@Override
	public List<CountBean> queryAdvertCountList(Map<String, Object> paramMap) {
		return this.dao.queryAdvertCountList(paramMap);
	}

	@Override
	public List<CountBean> queryCreativeCountList(Map<String, Object> paramMap) {
		return this.dao.queryCreativeCountList(paramMap);
	}

	@Override
	public List<CountBean> queryMediaCountList(Map<String, Object> paramMap) {
		return this.dao.queryMediaCountList(paramMap);
	}
	
	@Override
	public List<CountBean> queryAdvertiserCountList(Map<String, Object> paramMap) {
		return this.dao.queryAdvertiserCountList(paramMap);
	}

	@Override
	public List<CountBean> queryPropByLevel(Map<String, Object> paramMap) {
		return this.dao.queryPropByLevel(paramMap);
	}

	@Override
	public List<CountBean> queryAllCorrectCountList(Map<String, Object> paramMap) {
		return this.dao.queryAllCorrectCountList(paramMap);
	}

	@Override
	public List<CountBean> querySelfCorrectCountList(Map<String, Object> paramMap) {
		return this.dao.querySelfCorrectCountList(paramMap);
	}
}
