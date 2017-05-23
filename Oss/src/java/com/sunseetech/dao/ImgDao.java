package com.sunseetech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sunseetech.model.Img;

public interface ImgDao {	
		
	public List<Img> queryByAppIdAndCategory(@Param("appIds") String[] appIds,@Param("category") String category);
}
