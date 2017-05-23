package com.greenkoo.record.service;

import java.util.List;
import java.util.Map;

import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.model.form.RelatedBean;

/**
 * 告警记录Service接口
 * 
 * @author QYANZE
 *
 */
public interface IDataRecordService {

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	DataRecord queryById(String id);
	
	int queryCount(Map<String, Object> paramMap);
	
	List<DataRecord> queryList(Map<String, Object> paramMap);
	
	/**
	 * 查询确认违法广告数(确认违法页违法广告列表数量)
	 * 
	 * @param paramMap
	 * @return
	 */
	int queryAdvertCount(Map<String, Object> paramMap);
	
	/**
	 * 查询确认违法广告列表(确认违法页违法广告列表)
	 * 
	 * @param paramMap
	 * @return
	 */
	List<DataRecord> queryAdvertList(Map<String, Object> paramMap);
	
	/**
	 * 首页统计广告数量
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer countAdvert(Map<String, Object> paramMap);
	
	/**
	 * 首页统计广告主数量
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer countAdvertiser(Map<String, Object> paramMap);
	
	/**
	 * 首页统计媒体数量
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer countMedia(Map<String, Object> paramMap);
	
	/**
	 * 首页统计总体已整改广告创意数量
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer countAllCorrect(Map<String, Object> paramMap);

	/**
	 * 首页统计我方已整改广告创意数量
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer countSelfCorrect(Map<String, Object> paramMap);
	
	/**
	 * 根据违法程度以及时间条件统计广告数，计算占比（首页数据统计占比）
	 * 
	 * @param paramMap
	 * @return
	 */
	int countAdvertProportion(Map<String, Object> paramMap);
	
	/**
	 * 查询涉及媒体数量
	 * 
	 * @param advertiserUrl 广告主url
	 * @param landingUrl 广告url
	 * @return
	 */
	int queryRelatedMediaCount(String advertiserUrl, String landingUrl, String keyWord);
	
	/**
	 * 查询涉及媒体列表(广告主登录详情页查询和确认违法页的投放媒体)
	 * 
	 * @param advertiserUrl 广告主url
	 * @param landingUrl 广告url
	 * @return
	 */
	List<RelatedBean> queryRelatedMediaList(String advertiserUrl, String landingUrl, String keyWord);
	
	/**
	 * 查询涉及广告主数量
	 * 
	 * @param mediaUrl 媒体url
	 * @return
	 */
	int queryRelatedAdvertiserCount(String mediaUrl, String keyWord);
	
	/**
	 * 查询涉及广告主列表（媒体登录确认违法页涉及广告主）
	 * 
	 * @param mediaUrl 媒体url
	 * @return
	 */
	List<RelatedBean> queryRelatedAdvertiserList(String mediaUrl, String keyWord);
}
