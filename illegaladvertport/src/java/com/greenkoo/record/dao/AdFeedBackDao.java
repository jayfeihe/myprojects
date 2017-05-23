package com.greenkoo.record.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greenkoo.record.model.AdFeedBack;

public interface AdFeedBackDao {

	int update(AdFeedBack feedBack) throws Exception;

	AdFeedBack getByInfoIdAndRoleUrl(@Param("infoId") String infoId, @Param("roleUrl") String roleUrl);
	
	List<AdFeedBack> getByInfoIdAndNoRoleUrl(@Param("infoId") String infoId, @Param("roleUrl") String roleUrl);
}
