package com.greenkoo.echarts.service;

import java.util.List;
import java.util.Map;

import com.greenkoo.echarts.model.form.CountBean;

/**
 * 首页图表数据查询服务
 * 
 * @author QYANZE
 *
 */
public interface IEChartService {

	/**
	 * 查询广告活动最大值
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer queryMaxAdvertCount(Map<String, Object> paramMap);

	/**
	 * 查询创意最大值
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer queryMaxCreativeCount(Map<String, Object> paramMap);

	/**
	 * 查询媒体最大值
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer queryMaxMediaCount(Map<String, Object> paramMap);
	
	/**
	 * 查询广告主最大值
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer queryMaxAdvertiserCount(Map<String, Object> paramMap);

	/**
	 * 查询各违法程度占比最大值
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer queryMaxPropByLevel(Map<String, Object> paramMap);
	
	/**
	 * 查询总体已整改最大值
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer queryMaxAllCorrectCount(Map<String, Object> paramMap);
	
	/**
	 * 查询我方已整改最大值
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer queryMaxSelfCorrectCount(Map<String, Object> paramMap);
	
	/**
	 * 查询广告图表数据
	 * 
	 * @param paramMap
	 * @return
	 */
	List<CountBean> queryAdvertCountList(Map<String, Object> paramMap);

	/**
	 * 查询创意图表数据
	 * 
	 * @param paramMap
	 * @return
	 */
	List<CountBean> queryCreativeCountList(Map<String, Object> paramMap);

	/**
	 * 查询媒体图表数据
	 * 
	 * @param paramMap
	 * @return
	 */
	List<CountBean> queryMediaCountList(Map<String, Object> paramMap);
	
	/**
	 * 查询广告主图表数据
	 * 
	 * @param paramMap
	 * @return
	 */
	List<CountBean> queryAdvertiserCountList(Map<String, Object> paramMap);
	
	/**
	 * 查询各违法程度占比图表数据
	 * 
	 * @param paramMap
	 * @return
	 */
	List<CountBean> queryPropByLevel(Map<String, Object> paramMap);

	/**
	 * 查询总体已整改图表数据
	 * 
	 * @param paramMap
	 * @return
	 */
	List<CountBean> queryAllCorrectCountList(Map<String, Object> paramMap);

	/**
	 * 查询我方已整改图表数据
	 * 
	 * @param paramMap
	 * @return
	 */
	List<CountBean> querySelfCorrectCountList(Map<String, Object> paramMap);
}
