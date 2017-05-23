package com.greenkoo.echarts.dao;

import java.util.List;
import java.util.Map;

import com.greenkoo.echarts.model.form.CountBean;

public interface EChartDao {

	Integer queryMaxAdvertCount(Map<String, Object> paramMap);
	
	Integer queryMaxCreativeCount(Map<String, Object> paramMap);
	
	Integer queryMaxMediaCount(Map<String, Object> paramMap);
	
	Integer queryMaxAdvertiserCount(Map<String, Object> paramMap);
	
	Integer queryMaxPropByLevel(Map<String, Object> paramMap);
	
	Integer queryMaxAllCorrectCount(Map<String, Object> paramMap);

	Integer queryMaxSelfCorrectCount(Map<String, Object> paramMap);
	
	List<CountBean> queryAdvertCountList(Map<String, Object> paramMap);
	
	List<CountBean> queryCreativeCountList(Map<String, Object> paramMap);
	
	List<CountBean> queryMediaCountList(Map<String, Object> paramMap);
	
	List<CountBean> queryAdvertiserCountList(Map<String, Object> paramMap);
	
	List<CountBean> queryPropByLevel(Map<String, Object> paramMap);

	List<CountBean> queryAllCorrectCountList(Map<String, Object> paramMap);

	List<CountBean> querySelfCorrectCountList(Map<String, Object> paramMap);
}
