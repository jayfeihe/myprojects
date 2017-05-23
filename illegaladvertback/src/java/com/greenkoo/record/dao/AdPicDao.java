package com.greenkoo.record.dao;

import java.util.List;

import com.greenkoo.record.model.AdPic;

public interface AdPicDao {

	int update(AdPic pic) throws Exception;
	
	AdPic queryBydataId(String dataId);
	
	List<AdPic> queryByStatus(int status);
}
