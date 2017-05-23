package com.tera.img.dao;

import java.util.List;
import java.util.Map;

import com.tera.img.model.Img;

/**
 * 
 * <br>
 * <b>功能：</b>ImgDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 12:58:14<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ImgDao {	
		
	public void add(Img t);	
	
	public void update(Img t);
	
	public void updateOnlyChanged(Img t);
		
	public void delete(String id);	

	public int queryCount(Map<String, Object> map);
	
	public List<Img> queryList(Map<String, Object> map);

	public Img queryByKey(String id);

	public List<Img> queryCategoryCount(Map<String, Object> map);
}
