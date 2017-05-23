package com.greenkoo.record.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.model.form.RelatedBean;

public interface DataRecordDao {

	DataRecord queryById(String id);

	int queryCount(Map<String, Object> paramMap);

	List<DataRecord> queryList(Map<String, Object> paramMap);

	int queryAdvertCount(Map<String, Object> paramMap);

	List<DataRecord> queryAdvertList(Map<String, Object> paramMap);

	Integer countAdvert(Map<String, Object> paramMap);

	Integer countAdvertiser(Map<String, Object> paramMap);
	
	Integer countMedia(Map<String, Object> paramMap);
	
	Integer countAllCorrect(Map<String, Object> paramMap);

	Integer countSelfCorrect(Map<String, Object> paramMap);

	int countAdvertProportion(Map<String, Object> paramMap);

	int queryRelatedMediaCount(@Param("advertiserUrl") String advertiserUrl, @Param("landingUrl") String landingUrl,
			@Param("keyWord") String keyWord);

	List<RelatedBean> queryRelatedMediaList(@Param("advertiserUrl") String advertiserUrl,
			@Param("landingUrl") String landingUrl, @Param("keyWord") String keyWord);

	int queryRelatedAdvertiserCount(@Param("mediaUrl") String mediaUrl, @Param("keyWord") String keyWord);

	List<RelatedBean> queryRelatedAdvertiserList(@Param("mediaUrl") String mediaUrl, @Param("keyWord") String keyWord);
}
