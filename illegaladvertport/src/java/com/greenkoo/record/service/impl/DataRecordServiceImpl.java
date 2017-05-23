package com.greenkoo.record.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.record.dao.DataRecordDao;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.model.form.RelatedBean;
import com.greenkoo.record.service.IDataRecordService;

@Service("dataRecordService")
public class DataRecordServiceImpl implements IDataRecordService {

	@Autowired
	private DataRecordDao dao;

	@Override
	public DataRecord queryById(String id) {
		return this.dao.queryById(id);
	}

	@Override
	public int queryCount(Map<String, Object> paramMap) {
		return this.dao.queryCount(paramMap);
	}

	@Override
	public List<DataRecord> queryList(Map<String, Object> paramMap) {
		return this.dao.queryList(paramMap);
	}

	@Override
	public int queryAdvertCount(Map<String, Object> paramMap) {
		return this.dao.queryAdvertCount(paramMap);
	}

	@Override
	public List<DataRecord> queryAdvertList(Map<String, Object> paramMap) {
		return this.dao.queryAdvertList(paramMap);
	}

	@Override
	public Integer countAdvert(Map<String, Object> paramMap) {
		return this.dao.countAdvert(paramMap);
	}

	@Override
	public Integer countAdvertiser(Map<String, Object> paramMap) {
		return this.dao.countAdvertiser(paramMap);
	}
	
	@Override
	public Integer countMedia(Map<String, Object> paramMap) {
		return this.dao.countMedia(paramMap);
	}

	@Override
	public Integer countAllCorrect(Map<String, Object> paramMap) {
		return this.dao.countAllCorrect(paramMap);
	}

	@Override
	public Integer countSelfCorrect(Map<String, Object> paramMap) {
		return this.dao.countSelfCorrect(paramMap);
	}

	@Override
	public int countAdvertProportion(Map<String, Object> paramMap) {
		return this.dao.countAdvertProportion(paramMap);
	}

	@Override
	public int queryRelatedMediaCount(String advertiserUrl, String landingUrl, String keyWord) {
		return this.dao.queryRelatedMediaCount(advertiserUrl, landingUrl, keyWord);
	}

	@Override
	public List<RelatedBean> queryRelatedMediaList(String advertiserUrl, String landingUrl, String keyWord) {
		return this.dao.queryRelatedMediaList(advertiserUrl, landingUrl, keyWord);
	}

	@Override
	public int queryRelatedAdvertiserCount(String mediaUrl, String keyWord) {
		return this.dao.queryRelatedAdvertiserCount(mediaUrl, keyWord);
	}

	@Override
	public List<RelatedBean> queryRelatedAdvertiserList(String mediaUrl, String keyWord) {
		return this.dao.queryRelatedAdvertiserList(mediaUrl, keyWord);
	}
}
